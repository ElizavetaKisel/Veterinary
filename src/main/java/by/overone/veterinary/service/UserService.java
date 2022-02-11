package by.overone.veterinary.service;

import by.overone.veterinary.dto.*;

import java.util.List;

public interface UserService {

    List<UserDataDTO> getAllUsers();

    List<UserInfoDTO> getUsersByParams(UserInfoDTO userInfoDTO);

    void addUser(UserRegistrationDTO userRegistrationDTO);

    UserInfoDTO getUserInfo(long id);

    void deleteUser(long id);

    UserDataDTO getUserById(long id);

    UserInfoDTO updateUser(long id, UserUpdateDTO userUpdateDTO);

    UserDataDTO updateUserRole(long id, String role);

    List<PetDataDTO> getUserPets(long id);

    List<AppointmentDataDTO> getAppointmentsByUserId(long userId);

}
