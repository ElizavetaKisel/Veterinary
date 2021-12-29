package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.validator.UserDetailsValidator;
import by.overone.veterinary.util.validator.UserValidator;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public List<UserDataDTO> getAllUsers() {
        List<UserDataDTO> usersDataDTO;

            usersDataDTO = userDAO.getUsers().stream()
                    .map(user -> new UserDataDTO(user.getUser_id(), user.getLogin(), user.getEmail(), user.getRole()))
                    .collect(Collectors.toList());

        return usersDataDTO;
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) throws ValidationException {
        UserValidator.validateRegistrationData(userRegistrationDTO);
        User user = new User();
        user.setLogin(userRegistrationDTO.getLogin());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    public void addUserDetails(long id, UserDetails userDetails) throws ValidationException {
        getUserById(id);
        UserDetailsValidator.validateUserDetails(userDetails);
        userDAO.addUserDetails(id, userDetails);
    }

    @Override
    public UserInfoDTO getUserInfo(long id) {
        UserInfoDTO userInfoDTO;
        userInfoDTO = userDAO.getUserInfo(id);
        return userInfoDTO;
    }

    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userDAO.deleteUser(id);
    }

    @Override
    public UserDataDTO getUserById(long id) {
        UserDataDTO userDataDTO = new UserDataDTO();
        User user = userDAO.getUserById(id);
        userDataDTO.setId(user.getUser_id());
        userDataDTO.setLogin(user.getLogin());
        userDataDTO.setEmail(user.getEmail());
        userDataDTO.setRole(user.getRole());
        return userDataDTO;
    }

//    @Override
//    public void updateUser(long id, User user) throws ServiceNotFoundException, ServiceException {
//        getUserById(id);
//        try {
//            userDAO.updateUser(id, user);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//    }

}
