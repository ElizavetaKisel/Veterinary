package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.exception.EntityAlreadyExistException;
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

import javax.persistence.PersistenceException;
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
                .map(user -> new UserDataDTO(user.getLogin(), user.getEmail(), user.getRole().toString()))
                .collect(Collectors.toList());

        return usersDataDTO;
    }

    @Override
    public List<UserInfoDTO> getUsersByParams(UserInfoDTO userInfoDTO) {
        List<UserInfoDTO> usersInfoDTO;

        usersInfoDTO = userDAO.getUsersByParams(userInfoDTO).stream()
                .map(user -> new UserInfoDTO(user.getLogin(), user.getEmail(), user.getRole().toString(), user.getUserDetails().getName(),
                user.getUserDetails().getSurname(), user.getUserDetails().getAddress(), user.getUserDetails().getPhoneNumber()))
                .collect(Collectors.toList());

        return usersInfoDTO;
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) {
        try{
            User user = new User();
            user.setLogin(userRegistrationDTO.getLogin());
            user.setEmail(userRegistrationDTO.getEmail());
            user.setPassword(DigestUtils.md5Hex(userRegistrationDTO.getPassword()));
            user.setRole(Role.CUSTOMER);
            user.setStatus(Status.ACTIVE);
            user.setUserDetails(new UserDetails());
            userDAO.addUser(user);
        }catch (PersistenceException e){
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_USER);
        }
    }

    @Override
    public UserInfoDTO getUserInfo(long id) {
        getUserById(id);
        User user = userDAO.getUserInfo(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        return new UserInfoDTO(user.getLogin(), user.getEmail(), user.getRole().toString(), user.getUserDetails().getName(),
                user.getUserDetails().getSurname(), user.getUserDetails().getAddress(), user.getUserDetails().getPhoneNumber());
    }

    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userDAO.deleteUser(id);
        userDAO.deleteUserPets(id);
    }

    @Override
    public UserDataDTO getUserById(long id) {
        UserDataDTO userDataDTO = new UserDataDTO();
        User user = userDAO.getUserById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        userDataDTO.setLogin(user.getLogin());
        userDataDTO.setEmail(user.getEmail());
        userDataDTO.setRole(user.getRole().toString());
        return userDataDTO;
    }

    @Override
    public UserInfoDTO updateUser(long id, UserUpdateDTO userUpdateDTO) {
        getUserById(id);
        if (userUpdateDTO.getPassword() != null) {
            userUpdateDTO.setPassword(DigestUtils.md5Hex(userUpdateDTO.getPassword()));
        }
        userDAO.updateUser(id, userUpdateDTO);
        return getUserInfo(id);
    }

    @Override
    public User updateUserRole(long id, String role) {
        getUserById(id);
        return userDAO.updateUserRole(id, role.toUpperCase());
    }

    @Override
    public List<PetDataDTO> getUserPets(long id) {
        getUserById(id);
        List<PetDataDTO> petsDataDTO;
        petsDataDTO = userDAO.getUserPets(id).stream()
                .map(pet -> new PetDataDTO(pet.getName(), pet.getType(), pet.getBreed(), pet.getAge(), null))
                .collect(Collectors.toList());

        return petsDataDTO;
    }

}
