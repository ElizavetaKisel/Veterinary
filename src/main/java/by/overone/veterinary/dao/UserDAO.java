package by.overone.veterinary.dao;

import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(long id);

    User addUser(User user) throws DaoException;

    User updateUser(long id, User user);

    boolean deleteUser(long id);

}
