package by.overone.veterinary.dao.impl;


import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String GET_USERS_QUERY ="SELECT * FROM users WHERE status = 'ACTIVE'";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=? and status = 'ACTIVE'";
    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE (0, ?, ?, ?, ?, ?)";
    private final static String GET_USER_ID = "SELECT id FROM users WHERE login=?";
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

    @Override
    public User getUserById(long id) {
        User user = jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    @Override
    public UserInfoDTO getUserInfo(long id) {
        UserInfoDTO userInfoDTO = jdbcTemplate.queryForObject(GET_USER_INFO_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(UserInfoDTO.class));
        return userInfoDTO;
    }

    @Override
    @Transactional
    public User addUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_USER_QUERY);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, Role.USER.toString());
            ps.setString(5, Status.ACTIVE.toString());
            return ps;
        }, keyHolder);
         user.setId(keyHolder.getKey().longValue());
         jdbcTemplate.update(ADD_USER_DETAILS_ID_QUERY,user.getId());
        return user;
    }

    @Override
    public UserDetails addUserDetails(long id, UserDetails userDetails) {
        Object[] params = new Object[] {
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getAddress(),
                userDetails.getPhoneNumber(),
        };
        jdbcTemplate.update(ADD_USER_DETAILS_QUERY, params);
        return userDetails;
    }

    @Override
    public boolean deleteUser(long id) {
        jdbcTemplate.update(DELETE_USER_QUERY, Status.DELETED.toString(), id);
        return true;
    }
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
}
