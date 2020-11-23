package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    public User createUser(UserDto userDto) {
        User user = null;
        if (confirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setUserRole(userDto.getUserRole());
            user.setAddress(userDto.getAddress());

            addressService.saveAddress(user.getAddress());
            userRepository.save(user);
        }
        return user;
    }

    private boolean confirmPassword(String password, String password2) {
        return password.equals(password2);
    }



}
