package by.overone.veterinary.dao;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getUsers();

    Optional<User> getUserById(long id);

    Optional<User> getUserInfo(long id);

    User addUser(User user);

    boolean deleteUser(long id);

    UserDataDTO updateUser(UserUpdateDTO userUpdateDTO);

    UserDetails updateUserDetails(UserDetails userDetails);

    List<PetDataDTO> getPetsByUserId(long user_id);

    boolean deletePetByUserId(long user_id);

}