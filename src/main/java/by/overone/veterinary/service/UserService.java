package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;

import java.util.List;

public interface UserService {

    List<UserDataDTO> getAllUsers();

    void addUser(UserRegistrationDTO userRegistrationDTO);

    void addUserDetails(String login, UserDetails userDetails);

    UserData getUserData(String login);
}
