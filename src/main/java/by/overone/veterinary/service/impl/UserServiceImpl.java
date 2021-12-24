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
import by.overone.veterinary.util.validator.UserDetailsValidator;
import by.overone.veterinary.util.validator.UserValidator;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
                    .map(user -> new UserDataDTO(user.getId(), user.getLogin(), user.getEmail(), user.getRole()))
                    .collect(Collectors.toList());

        return usersDataDTO;
    }

//    @Override
//    public void addUser(UserRegistrationDTO userRegistrationDTO) throws ServiceException, ServiceExistException, ValidationException {
//        try {
//            UserValidator.validateRegistrationData(userRegistrationDTO);
//            User user = new User();
//            user.setLogin(userRegistrationDTO.getLogin());
//            user.setEmail(userRegistrationDTO.getEmail());
//            user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
//            userDAO.addUser(user);
//        } catch (DaoExistException e) {
//            throw new ServiceExistException("User already exist", e);
//        } catch (DaoException e) {
//            throw new ServiceException("User not added", e);
//        }
//    }
//
//    @Override
//    public void addUserDetails(long id, UserDetails userDetails) throws ServiceException, ServiceNotFoundException, ValidationException {
//        getUserById(id);
//        try {
//            UserDetailsValidator.validateUserDetails(userDetails);
//            userDAO.addUserDetails(id, userDetails);
//        } catch (DaoException e) {
//           throw new ServiceException("Details not added", e);
//        }
//
//    }
//
//    @Override
//    public UserInfoDTO getUserInfo(long id) throws ServiceNotFoundException, ServiceException {
//        UserInfoDTO userInfoDTO;
//        try {
//            userInfoDTO = userDAO.getUserInfo(id);
//        } catch (DaoNotFoundException e) {
//            throw new ServiceNotFoundException("User not found", e);
//        }catch (DaoException ex){
//            throw new ServiceException(ex);
//        }
//        return userInfoDTO;
//    }
//
//    @Override
//    public void deleteUser(long id) throws ServiceNotFoundException, ServiceException {
//        getUserById(id);
//        try {
//            userDAO.deleteUser(id);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//
//    }
//
//    @Override
//    public UserDataDTO getUserById(long id) throws ServiceNotFoundException, ServiceException {
//        UserDataDTO userDataDTO = new UserDataDTO();
//        try {
//            User user = userDAO.getUserById(id);
//            userDataDTO.setId(user.getId());
//            userDataDTO.setLogin(user.getLogin());
//            userDataDTO.setEmail(user.getEmail());
//            userDataDTO.setRole(user.getRole());
//        } catch (DaoNotFoundException e) {
//            throw new ServiceNotFoundException("User not found", e);
//        }catch (DaoException ex){
//            throw new ServiceException(ex);
//        }
//        return userDataDTO;
//    }
//
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
