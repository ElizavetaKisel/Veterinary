package by.overone.veterinary.dao;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(long id);

    UserInfoDTO getUserInfo(long id);

    User addUser(User user);

    boolean deleteUser(long id);

    UserDataDTO updateUser(long id, UserUpdateDTO userUpdateDTO);

    UserDetails updateUserDetails(long id, UserDetails userDetails);

}
