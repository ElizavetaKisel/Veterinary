package by.overone.veterinary.service;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.util.validator.exception.ValidationException;

import java.util.List;

public interface UserService {
//    /**
//     *
//     * @return
//     */

    List<UserDataDTO> getAllUsers();

    void addUser(UserRegistrationDTO userRegistrationDTO) throws ValidationException;

    UserInfoDTO getUserInfo(long id);

    void deleteUser(long id);

    UserDataDTO getUserById(long id);

    UserDataDTO updateUser(UserUpdateDTO userUpdateDTO) throws ValidationException;

    UserDetails updateUserDetails(UserDetails userDetails) throws ValidationException;

    List<PetDataDTO> getPetsByUserId(long user_id);
}
