package by.overone.veterinary.service.impl;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.UserData;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {


    @Override
    public List<UserDataDTO> getAllUsers() {
        return null;
    }

    @Override
    public void addUser(UserRegistrationDTO userRegistrationDTO) {

    }

    @Override
    public void addUserDetails(String login, UserDetails userDetails) {

    }

    @Override
    public UserData getUserData(String login) {
        return null;
    }
}
