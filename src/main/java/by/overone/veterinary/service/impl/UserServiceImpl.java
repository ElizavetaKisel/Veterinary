package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.exception.ExceptionCode;
import by.overone.veterinary.model.Role;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public List<UserDataDTO> getAllUsers() {
        List<UserDataDTO> usersDataDTO;

        usersDataDTO = userDAO.getUsers().stream()
                .map(user -> new UserDataDTO(user.getUser_id(), user.getLogin(), user.getEmail(), user.getRole().toString()))
                .collect(Collectors.toList());

        return usersDataDTO;
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setLogin(userRegistrationDTO.getLogin());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.ACTIVE);
        user.setDetails_id(new UserDetails());
        userDAO.addUser(user);
    }

    @Override
    public UserInfoDTO getUserInfo(long id) {
        User user = userDAO.getUserInfo(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        return new UserInfoDTO(user.getUser_id(), user.getLogin(), user.getEmail(), user.getRole().toString(), user.getDetails_id());
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userDAO.deleteUser(id);
        userDAO.deletePetByUserId(id);
    }

    @Override
    public UserDataDTO getUserById(long id) {
        UserDataDTO userDataDTO = new UserDataDTO();
        User user = userDAO.getUserById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        userDataDTO.setUser_id(user.getUser_id());
        userDataDTO.setLogin(user.getLogin());
        userDataDTO.setEmail(user.getEmail());
        userDataDTO.setRole(user.getRole().toString());
        return userDataDTO;
    }

    @Override
    public UserDataDTO updateUser(UserUpdateDTO userUpdateDTO) {
        getUserById(userUpdateDTO.getUser_id());
        if (userUpdateDTO.getPassword() != null) {
            userUpdateDTO.setPassword(DigestUtils.md5Hex(userUpdateDTO.getPassword()));
        }
        if (userUpdateDTO.getRole() != null) {
            userUpdateDTO.setPassword(userUpdateDTO.getRole().toUpperCase());
        }
        return userDAO.updateUser(userUpdateDTO);
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails) {
        getUserById(userDetails.getDetails_id());
        return userDAO.updateUserDetails(userDetails);
    }

    @Override
    public List<PetDataDTO> getPetsByUserId(long user_id) {
        userDAO.getUserById(user_id);
        return userDAO.getPetsByUserId(user_id);
    }

}
