package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;
import by.overone.veterinary.util.validator.exception.ValidationException;

import java.util.List;

public interface UserService {
    /**
     * 
     * @return
     * @throws ServiceException
     */

    List<UserDataDTO> getAllUsers() throws ServiceException;

    void addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException, ServiceExistException, ValidationException;

    void addUserDetails(String login, UserDetails userDetails) throws ServiceException;

    UserInfoDTO getUserInfo(long id) throws ServiceNotFoundException, ServiceException;

    void deleteUser(long id) throws ServiceNotFoundException, ServiceException;

    UserDataDTO getUserById(long id) throws ServiceNotFoundException, ServiceException;
}
