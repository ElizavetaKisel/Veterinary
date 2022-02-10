package by.overone.veterinary.dao;

import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getUsers();

    List<User> getUsersByParams(UserInfoDTO userInfoDTO);

    Optional<User> getUserById(long id);

    Optional<User> getUserInfo(long id);

    User addUser(User user);

    User deleteUser(long id);

    User updateUser(long id, UserUpdateDTO userUpdateDTO);

    User updateUserRole(long id,String role);

    List<Pet> getUserPets(long id);

    boolean deleteUserPets(long id);

    List<Appointment> getAppointmentsByUserId(long users_user_id);

}