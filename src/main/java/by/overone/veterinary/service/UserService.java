package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDataDTO> getAllUsers() throws ServiceException;

    void addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException, ServiceExistException;

    void addUserDetails(String login, UserDetails userDetails) throws ServiceException;

    UserData getUserData(String login) throws ServiceNotFoundException;
}
