package by.overone.veterinary.dao.impl;


import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

//    private final JdbcTemplate jdbcTemplate;
//
//    private final static String GET_USERS_QUERY ="SELECT * FROM users WHERE status = 'ACTIVE'";
//    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=? and status = 'ACTIVE'";
//    private final static String ADD_USER_QUERY = "INSERT INTO users VALUE (0, ?, ?, ?, ?, ?)";
//    private final static String ADD_USER_DETAILS_ID_QUERY = "INSERT INTO user_details (users_user_id) VALUE (?)";
//    private final static String DELETE_USER_QUERY = "UPDATE users SET status=? WHERE user_id=? and status = 'ACTIVE'";
//    private final static String GET_USER_INFO_QUERY = "SELECT * FROM users JOIN user_details " +
//            "ON user_id = users_user_id WHERE user_id=? and status = 'ACTIVE'";
//    private final static String GET_USER_DETAILS_QUERY = "SELECT * FROM user_details WHERE user_id=?";
//    private static final String GET_PETS_BY_USER_ID_QUERY = "SELECT * FROM pets join pets_has_users" +
//            " ON pet_id = pets_pet_id WHERE pets_pet_id=? and status= 'ACTIVE'";
//    private final static String DELETE_PET_BY_USER_ID_QUERY = "UPDATE pets join pets_has_users " +
//            "ON pet_id = pets_pet_id SET status=? WHERE users_user_id=? and status = 'ACTIVE'";

    @PersistenceContext
    private EntityManager entityManager;


    public List<User> getUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.from(User.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

//    @Override
//    public List<User> getUsers() {
//        List<User> users = jdbcTemplate.query(GET_USERS_QUERY, new BeanPropertyRowMapper<>(User.class));
//        return users;
//    }

//    @Override
//    public Optional<User> getUserById(long id) {
//        return jdbcTemplate.query(GET_USER_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(User.class)).stream().findAny();
//    }
//
//    @Override
//    public Optional<UserInfoDTO> getUserInfo(long id) {
//        return  jdbcTemplate.query(GET_USER_INFO_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(UserInfoDTO.class)).stream().findAny();
//    }
//
//    @Override
//    @Transactional
//    public User addUser(User user){
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(ADD_USER_QUERY, new String[]{"user_id"});
//            ps.setString(1, user.getLogin());
//            ps.setString(2, user.getPassword());
//            ps.setString(3, user.getEmail());
//            ps.setString(4, Role.USER.toString());
//            ps.setString(5, Status.ACTIVE.toString());
//            return ps;
//        }, keyHolder);
//         user.setUser_id(keyHolder.getKey().longValue());
//         jdbcTemplate.update(ADD_USER_DETAILS_ID_QUERY,user.getUser_id());
//        return user;
//    }
//
//    @Override
//    public boolean deleteUser(long id) {
//        jdbcTemplate.update(DELETE_USER_QUERY, Status.DELETED.toString(), id);
//        return true;
//    }
//
//    @Override
//    public List<PetDataDTO> getPetsByUserId(long user_id) {
//        return jdbcTemplate.query(GET_PETS_BY_USER_ID_QUERY, new Object[]{user_id}, new BeanPropertyRowMapper<>(PetDataDTO.class));
//    }
//
//    @Override
//    public UserDataDTO updateUser(UserUpdateDTO user) {
//        List <String> sql = new ArrayList<>();
//        if (user.getPassword() != null) {
//            sql.add("password='" + user.getPassword() + "'");
//        }
//        if (user.getEmail() != null) {
//            sql.add("email=" + user.getEmail() + "'");
//        }
//        if (user.getRole() != null) {
//            sql.add("role='" + user.getRole());
//        }
//        String UPDATE_USER_QUERY = "UPDATE users SET " + String.join(", ", sql)
//                + " WHERE user_id=" + user.getUser_id();
//        jdbcTemplate.update(UPDATE_USER_QUERY);
//        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, new Object[]{user.getUser_id()},
//                new BeanPropertyRowMapper<>(UserDataDTO.class));
//    }
//
//    @Override
//    public UserDetails updateUserDetails(UserDetails user) {
//        List <String> sql = new ArrayList<>();
//        if (user.getName() != null) {
//            sql.add("name=" + user.getName() + "'");
//        }
//        if (user.getSurname() != null) {
//            sql.add("surname='" + user.getSurname() + "'");
//        }
//        if (user.getAddress() != null) {
//            sql.add("address=" + user.getAddress() + "'");
//        }
//        if (user.getPhone_number() != null) {
//            sql.add("phone_number=" + user.getPhone_number() + "'");
//        }
//        String UPDATE_PET_QUERY = "UPDATE user_details JOIN users ON user_id = users_user_id SET "
//                + sql.stream().collect(Collectors.joining(", "))
//                + " WHERE user_id=" + user.getUsers_user_id();
//        jdbcTemplate.update(UPDATE_PET_QUERY);
//        return jdbcTemplate.queryForObject(GET_USER_DETAILS_QUERY, new Object[]{user.getUsers_user_id()},
//                new BeanPropertyRowMapper<>(UserDetails.class));
//    }
//
//    @Override
//    public boolean deletePetByUserId(long user_id) {
//        jdbcTemplate.update(DELETE_PET_BY_USER_ID_QUERY, Status.DELETED.toString(), user_id);
//        return true;
//    }
}
