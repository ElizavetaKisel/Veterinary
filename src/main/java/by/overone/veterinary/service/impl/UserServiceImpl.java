package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.impl.UserDAOImpl;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.util.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<UserDataDTO> getAllUsers() throws ServiceException {
        List<UserDataDTO> usersDataDTO;

        try {
            usersDataDTO = userDAO.getUsers().stream()
                    .map(user -> new UserDataDTO(user.getId(), user.getLogin(), user.getEmail(), user.getRole()))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            throw new ServiceException(e);
        }
        return usersDataDTO;
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException, ServiceExistException {
        UserValidator.validateRegistrationData(userRegistrationDTO);

        User user = new User();
        user.setLogin(userRegistrationDTO.getLogin());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));

        try {
            userDAO.addUser(user);
        } catch (DaoExistException e) {
            throw new ServiceExistException("User already exist", e);
        } catch (DaoException e) {
            throw new ServiceException("User not added", e);
        }
    }

    @Override
    public void addUserDetails(String login, UserDetails userDetails) {

    }

    @Override
    public UserData getUserData(String login) {
        return null;
    }
}
