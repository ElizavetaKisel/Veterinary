package by.overone.veterinary.dao;

import by.overone.veterinary.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(long id);

    User addUser(User user);

    User updateUser(User user);

    User deleteUser(long id);

}
