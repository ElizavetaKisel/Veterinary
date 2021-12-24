package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.conection.DBConnect;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.*;
import by.overone.veterinary.util.constant.UserConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String GET_USERS_QUERY ="SELECT * FROM users WHERE status = 'ACTIVE'";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=? and status = 'ACTIVE'";
    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE (0, ?, ?, ?, ?, ?)";
    private final static String ADD_USER_DETAILS_ID_QUERY = "INSERT INTO user_details (users_user_id) VALUE (?)";
    private final static String ADD_USER_DETAILS_QUERY = "UPDATE user_details JOIN users ON user_id = users_user_id" +
            " SET name=?, surname=?, address=?, phone_number=? WHERE user_id=? and status = 'ACTIVE'";
    private final static String UPDATE_USER_QUERY = "UPDATE users SET login=?, password=?, email=?, role=? WHERE user_id=?";
    private final static String DELETE_USER_QUERY = "UPDATE users SET status=? WHERE user_id=? and status = 'ACTIVE'";
    private final static String GET_USER_INFO_QUERY = "SELECT * FROM users JOIN user_details " +
            "ON user_id = users_user_id WHERE user_id=? and status = 'ACTIVE'";

    @Override
    public List<User> getUsers() {

        List<User> users = jdbcTemplate.query(GET_USERS_QUERY, new BeanPropertyRowMapper<>(User.class));
        return users;
    }
//
//    @Override
    public User getUserById(long id) {

        User user = jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }
//
//    @Override
    public UserInfoDTO getUserInfo(long id) throws DaoNotFoundException, DaoException {
        UserInfoDTO userInfoDTO = jdbcTemplate.queryForObject(GET_USER_INFO_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(UserInfoDTO.class));
        return userInfoDTO;
    }
//
//    @Override
//    public User addUser(User user) throws DaoException, DaoExistException {
//        try {
//            connection = DBConnect.getConnection();
//            connection.setAutoCommit(false);
//            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
//
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setString(3, user.getEmail());
//            preparedStatement.setString(4, Role.USER.toString());
//            preparedStatement.setString(5, Status.ACTIVE.toString());
//
//            preparedStatement.executeUpdate();
//
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            while (resultSet.next()) {
//                user.setId(resultSet.getLong(1));
//            }
//
//            preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_ID_QUERY);
//            preparedStatement.setLong(1, user.getId());
//            preparedStatement.executeUpdate();
//
//            connection.commit();
//
//        }catch (SQLIntegrityConstraintViolationException ex){
//            throw new DaoExistException("Duplicate user", ex);
//        }catch (SQLException e){
//            try {
//                connection.rollback();
//            }catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            throw new DaoException("User not added", e);
//        }finally {
//            try {
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//        return user;
//    }
//
//    @Override
//    public UserDetails addUserDetails(long id, UserDetails userDetails) throws DaoException {
//        try {
//            connection = DBConnect.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_DETAILS_QUERY);
//
//            preparedStatement.setString(1, userDetails.getName());
//            preparedStatement.setString(2, userDetails.getSurname());
//            preparedStatement.setString(3, userDetails.getAddress());
//            preparedStatement.setString(4, userDetails.getPhoneNumber());
//            preparedStatement.setLong(5, id);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new DaoException("Details not added", e);
//        } finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return userDetails;
//    }
//
//    @Override
//    public User updateUser(long id, User user) throws DaoException {
//        try {
//            connection = DBConnect.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
//
//            if (user.getLogin() != null) {
//                preparedStatement.setString(1, user.getLogin());
//            }
//            if (user.getPassword() != null) {
//                preparedStatement.setString(2, user.getPassword());
//            }
//            if (user.getEmail() != null) {
//                preparedStatement.setString(3, user.getEmail());
//            }
//            if (user.getRole() != null) {
//                preparedStatement.setString(4, user.getRole());
//            }
//            preparedStatement.setLong(5, id);
//
//            preparedStatement.executeUpdate();
//        }catch (SQLException e) {
//            throw new DaoException("dao error", e);
//        }finally {
//            try {
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//        return user;
//    }
//
//    @Override
//    public boolean deleteUser(long id) throws DaoException {
//        try{
//            connection = DBConnect.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
//            preparedStatement.setString(1,Status.DELETED.toString());
//            preparedStatement.setLong(2,id);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new DaoException("dao error", e);
//        } finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
//    }
}
