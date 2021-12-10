package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.DBConnect;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static Connection connection;

    private final static String GET_USERS_QUERY ="SELECT * FROM users WHERE status = 'ACTIVE'";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=? and status = 'ACTIVE'";
    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE (0, ?, ?, ?, ?, ?)";
    private final static String ADD_USER_DETAILS_ID_QUERY = "INSERT INTO user_details (users_user_id) VALUE (?)";
    private final static String ADD_USER_DETAILS_QUERY = "UPDATE user_details JOIN users ON user_id = users_user_id" +
            " SET name=?, surname=?, address=?, phone_number=? WHERE login=?";
    private final static String UPDATE_USER_QUERY = "UPDATE users SET login=?, password=?, email=? WHERE user_id=?";
    private final static String DELETE_USER_QUERY = "UPDATE users SET status=? WHERE user_id=?";
    private final static String GET_USER_DATA_QUERY = "SELECT * FROM users JOIN user_details " +
            "ON user_id = users_user_id WHERE user_login=? and status = 'ACTIVE'";

    @Override
    public List<User> getUsers() throws DaoException {
        List<User> users = new ArrayList<>();
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setEmail(resultSet.getString("role"));
                users.add(user);
            }
        }catch (SQLException e) {
            throw new DaoException("Dao error", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User getUserById(long id) {

        User user = new User();
        try{
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setEmail(resultSet.getString("role"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public UserData getUserData(String login) {
        UserData userData = new UserData();
        try{
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_DATA_QUERY);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userData.setLogin(resultSet.getString("login"));
                userData.setEmail(resultSet.getString("email"));
                userData.setEmail(resultSet.getString("name"));
                userData.setEmail(resultSet.getString("surname"));
                userData.setEmail(resultSet.getString("address"));
                userData.setEmail(resultSet.getString("phone_number"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return userData;
    }

    @Override
    public User addUser(User user) throws DaoException, DaoExistException {
        try {
            connection = DBConnect.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, Role.USER.toString());
            preparedStatement.setString(5, Status.ACTIVE.toString());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

            preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_ID_QUERY);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();

            connection.commit();

        }catch (SQLIntegrityConstraintViolationException ex){
            throw new DaoExistException("Duplicate user", ex);
        }catch (SQLException e){
            try {
                connection.rollback();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DaoException("User not added", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public UserDetails addUserDetails(String login, UserDetails userDetails) throws DaoException {
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_QUERY);

            preparedStatement.setString(1, userDetails.getName());
            preparedStatement.setString(2, userDetails.getSurname());
            preparedStatement.setString(3, userDetails.getPhoneNumber());
            preparedStatement.setString(4, userDetails.getAddress());
            preparedStatement.setString(5,login);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Details not added", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return userDetails;
    }

    @Override
    public User updateUser(long id, User user) {
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            
            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setLong(4, id);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean deleteUser(long id) {
        try{
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setString(1,Status.DELETED.toString());
            preparedStatement.setLong(2,id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            try {
                connection.rollback();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
