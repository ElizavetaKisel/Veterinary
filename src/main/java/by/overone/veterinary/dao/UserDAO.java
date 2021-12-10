package by.overone.veterinary.dao;

import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(long id);

    UserData getUserData(String login);

    User addUser(User user) throws DaoException;

    UserDetails addUserDetails(String login, UserDetails userDetails) throws DaoException;

    User updateUser(long id, User user);

    boolean deleteUser(long id);

}
