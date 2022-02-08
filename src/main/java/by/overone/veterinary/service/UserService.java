package by.overone.veterinary.service;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserService {
//    /**
//     *
//     * @return
//     */

    List<UserDataDTO> getAllUsers();

    List<UserInfoDTO> getUsersByParams(UserInfoDTO userInfoDTO);

    void addUser(UserRegistrationDTO userRegistrationDTO);

    UserInfoDTO getUserInfo(long id);

    void deleteUser(long id);

    UserDataDTO getUserById(long id);

    UserInfoDTO updateUser(long id, UserUpdateDTO userUpdateDTO);

    UserDataDTO updateUserRole(long id, String role);

    List<PetDataDTO> getUserPets(long id);
}
