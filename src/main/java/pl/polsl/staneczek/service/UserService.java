package pl.polsl.staneczek.service;

import pl.polsl.staneczek.model.User;
import pl.polsl.staneczek.repository.UserRepository;
import pl.polsl.staneczek.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

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
            String encodePassword = Base64.getEncoder().encodeToString((userDto.getPassword()).getBytes());
            user.setPassword(encodePassword);
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setUserRole(userDto.getUserRole());
            user.setAddress(userDto.getAddress());
            user.setLastLogin(null);
            addressService.saveAddress(user.getAddress());
            userRepository.save(user);
        }
        return user;
    }

    private boolean confirmPassword(String password, String password2) {
        return password.equals(password2);
    }

    public User findById(Integer userId) {
        return userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
    }

    public void save(User user) {
        userRepository.save(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get() : null;
    }
    public Integer findStudentOrInstructor(String email){
        User user = findByEmail(email);
        return user.getId();
    }
}
