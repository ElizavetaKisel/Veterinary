package by.overone.veterinary.dao;

import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(long id);

    UserInfoDTO getUserInfo(long id);

    User addUser(User user);

    UserDetails addUserDetails(long id, UserDetails userDetails);

    boolean deleteUser(long id);

//    User updateUser(long id, User user) throws DaoException;

}
