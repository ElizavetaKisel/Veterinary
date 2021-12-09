package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.DBConnect;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.model.Role;
import by.overone.veterinary.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static Connection connection;

    private final static String GET_USERS_QUERY ="SELECT * FROM user";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id=?";
    private final static String ADD_USER_QUERY = "INSERT INTO user VALUE (0, ?, ?, ?, ?, null)";
    private final static String ADD_USER_DETAILS_QUERY = "INSERT INTO user_details (users_user_id) VALUE (?)";

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User addUser(User user) throws DaoException {
        try {
            connection = DBConnect.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, Role.USER.toString());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

            preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_QUERY);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();

            connection.commit();

        }catch (SQLIntegrityConstraintViolationException ex){
            throw new DaoException("Duplicate user", ex);
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
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(long id) {
        return null;
    }
}
