package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.dao.impl.UserDAOImpl;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;
import by.overone.veterinary.util.validator.UserValidator;
import by.overone.veterinary.util.validator.exception.ValidationException;
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
        try {
            UserValidator.validateRegistrationData(userRegistrationDTO);
            User user = new User();
            user.setLogin(userRegistrationDTO.getLogin());
            user.setEmail(userRegistrationDTO.getEmail());
            user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
            userDAO.addUser(user);
        } catch (ValidationException e) {
            e.getMessage();
        } catch (DaoExistException e) {
            throw new ServiceExistException("User already exist", e);
        } catch (DaoException e) {
            throw new ServiceException("User not added", e);
        }
    }

    @Override
    public void addUserDetails(String login, UserDetails userDetails) throws ServiceException {

        try {
//            UserValidator.validateUserDetails(userDetails);
            userDAO.addUserDetails(login, userDetails);
//        } catch (ValidationException e) {
//            e.printStackTrace();
        } catch (DaoException e) {
           throw new ServiceException("Details not added", e);
        }

    }

    @Override
    public UserInfoDTO getUserInfo(long id) throws ServiceNotFoundException, ServiceException {
        UserInfoDTO userInfoDTO;
        try {
            userInfoDTO = userDAO.getUserInfo(id);
        } catch (DaoNotFoundException e) {
            throw new ServiceNotFoundException("User not found", e);
        }catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return userInfoDTO;
    }

    @Override
    public void deleteUser(long id) throws ServiceNotFoundException {

        try {
            userDAO.deleteUser(id);
        } catch (DaoNotFoundException e) {
            throw new ServiceNotFoundException("User not found", e);
        }

    }

    @Override
    public UserDataDTO getUserById(long id) throws ServiceNotFoundException, ServiceException {
        UserDataDTO userDataDTO = new UserDataDTO();
        try {
            User user = userDAO.getUserById(id);
            userDataDTO.setId(user.getId());
            userDataDTO.setLogin(user.getLogin());
            userDataDTO.setEmail(user.getEmail());
            userDataDTO.setRole(user.getRole());
        } catch (DaoNotFoundException e) {
            throw new ServiceNotFoundException("User not found", e);
        }catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return userDataDTO;
    }

}
