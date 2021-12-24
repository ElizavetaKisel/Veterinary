package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;
import by.overone.veterinary.util.validator.exception.ValidationException;

import java.util.List;

public interface UserService {
//    /**
//     *
//     * @return
//     */

    List<UserDataDTO> getAllUsers();

//    void addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException, ServiceExistException, ValidationException;
//
//    void addUserDetails(long id, UserDetails userDetails) throws ServiceException, ServiceNotFoundException, ValidationException;
//
//    UserInfoDTO getUserInfo(long id) throws ServiceNotFoundException, ServiceException;
//
//    void deleteUser(long id) throws ServiceNotFoundException, ServiceException;
//
//    UserDataDTO getUserById(long id) throws ServiceNotFoundException, ServiceException;
//
//    void updateUser(long id, User user) throws ServiceNotFoundException, ServiceException;
}
