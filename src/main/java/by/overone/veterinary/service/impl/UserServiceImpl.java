package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.service.exception.EntityAlreadyExistException;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.ExceptionCode;
import by.overone.veterinary.model.Role;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.mapper.MyMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final MyMapper myMapper;

    @Override
    public List<UserDataDTO> getAllUsers() {

        return userDAO.getUsers().stream()
                .map(user -> new UserDataDTO(user.getLogin(), user.getEmail(), user.getRole().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInfoDTO> getUsersByParams(UserInfoDTO userInfoDTO) {

        return userDAO.getUsersByParams(userInfoDTO).stream()
                .map(myMapper::userToInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) {
        try {
            User user = new User();
            user.setLogin(userRegistrationDTO.getLogin());
            user.setEmail(userRegistrationDTO.getEmail());
            user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            user.setUserDetails(new UserDetails());
            userDAO.addUser(user);
        } catch (PersistenceException e) {
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_USER);
        }
    }

    @Override
    public UserInfoDTO getUserInfo(long id) {
        getUserById(id);
        User user = userDAO.getUserInfo(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        return myMapper.userToInfoDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        getUserById(id);
        userDAO.deleteUser(id);
        userDAO.deleteUserPets(id);
    }

    @Override
    public UserDataDTO getUserById(long id) {
        User user = userDAO.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        return myMapper.userToDataDTO(user);
    }

    @Override
    public UserInfoDTO updateUser(long id, UserUpdateDTO userUpdateDTO) {
        try {
            getUserById(id);
            if (userUpdateDTO.getPassword() != null) {
                userUpdateDTO.setPassword(DigestUtils.md5Hex(userUpdateDTO.getPassword()));
                if (userUpdateDTO.getOldPassword()!=null){
                    userUpdateDTO.setOldPassword(DigestUtils.md5Hex(userUpdateDTO.getOldPassword()));
                }else {
                    throw new EntityNotFoundException(ExceptionCode.EMPTY_PASSWORD);
                }
            }
            userDAO.updateUser(id, userUpdateDTO);
            return getUserInfo(id);
        } catch (PersistenceException e) {
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_USER);
        }
    }

    @Override
    public UserDataDTO updateUserRole(long id, String role) {
        getUserById(id);
        return myMapper.userToDataDTO(userDAO.updateUserRole(id, role.toUpperCase()));
    }

    @Override
    public List<PetDataDTO> getUserPets(long id) {
        getUserById(id);
        List<PetDataDTO> petsDataDTO;
        petsDataDTO = userDAO.getUserPets(id).stream()
                .map(myMapper::petToDTO)
                .collect(Collectors.toList());

        return petsDataDTO;
    }

}
