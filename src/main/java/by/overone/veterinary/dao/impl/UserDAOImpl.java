package by.overone.veterinary.dao.impl;


import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final static String DELETE_USER_QUERY = "UPDATE users SET status=? WHERE user_id=? and status = 'ACTIVE'";
    private final static String GET_USER_INFO_QUERY = "SELECT * FROM users JOIN user_details " +
            "ON user_id = users_user_id WHERE user_id=? and status = 'ACTIVE'";
    private final static String START_UPDATE_USER_QUERY = "UPDATE users SET ";
    private final static String END_UPDATE_USER_QUERY = " WHERE user_id=?";

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
            PreparedStatement ps = connection.prepareStatement(ADD_USER_QUERY, new String[]{"user_id"});
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, Role.USER.toString());
            ps.setString(5, Status.ACTIVE.toString());
            return ps;
        }, keyHolder);
         user.setUser_id(keyHolder.getKey().longValue());
         jdbcTemplate.update(ADD_USER_DETAILS_ID_QUERY,user.getUser_id());
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

    @Override
    public User updateUser(long id, UserUpdateDTO user) {
        if (user.getLogin() != null) {
            String additionalQuery1 = START_UPDATE_USER_QUERY + "login=?" + END_UPDATE_USER_QUERY;
            jdbcTemplate.update(additionalQuery1, user.getLogin(), id);
        }
        if (user.getPassword() != null) {
            String additionalQuery2 = START_UPDATE_USER_QUERY + "password=?" + END_UPDATE_USER_QUERY;
            jdbcTemplate.update(additionalQuery2, user.getPassword(), id);
        }
        if (user.getEmail() != null) {
            String additionalQuery3 = START_UPDATE_USER_QUERY + "email=?" + END_UPDATE_USER_QUERY;
            jdbcTemplate.update(additionalQuery3, user.getEmail(), id);
        }
        if (user.getRole() != null) {
            String additionalQuery4 = START_UPDATE_USER_QUERY + "role=?" + END_UPDATE_USER_QUERY;
            jdbcTemplate.update(additionalQuery4, user.getRole(), id);
        }

        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));

    }
}
