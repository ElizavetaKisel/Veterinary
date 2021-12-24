package by.overone.veterinary.service;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.dto.UserInfoDTO;
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

    void addUserDetails(long id, UserDetails userDetails) throws ValidationException;

    UserInfoDTO getUserInfo(long id);

    void deleteUser(long id);

    UserDataDTO getUserById(long id);

//    void updateUser(long id, User user) throws ServiceNotFoundException, ServiceException;
}
