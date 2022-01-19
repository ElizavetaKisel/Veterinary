package by.overone.veterinary.service;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserService {
//    /**
//     *
//     * @return
//     */

    List<UserDataDTO> getAllUsers();

    void addUser(UserRegistrationDTO userRegistrationDTO);

    UserInfoDTO getUserInfo(long id);

    void deleteUser(long id);

    UserDataDTO getUserById(long id);

    UserDataDTO updateUser(UserUpdateDTO userUpdateDTO);

    UserDetails updateUserDetails(UserDetails userDetails);

    List<PetDataDTO> getPetsByUserId(long user_id);
}
