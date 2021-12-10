package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    List<UserDataDTO> getAllUsers() throws ServiceException;

    void addUser(UserRegistrationDTO userRegistrationDTO);

    void addUserDetails(String login, UserDetails userDetails);

    UserData getUserData(String login);
}
