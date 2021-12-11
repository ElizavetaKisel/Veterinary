package by.overone.veterinary.dao;

import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserDAO {

    List<User> getUsers() throws DaoException;

    User getUserById(long id) throws DaoNotFoundException, DaoException;

    UserInfoDTO getUserInfo(long id) throws DaoNotFoundException, DaoException;

    User addUser(User user) throws DaoException, DaoExistException;

    UserDetails addUserDetails(String login, UserDetails userDetails) throws DaoException;

    User updateUser(long id, User user);

    boolean deleteUser(long id) throws DaoNotFoundException;

}
