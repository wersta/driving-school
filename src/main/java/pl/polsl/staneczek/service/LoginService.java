package pl.polsl.staneczek.service;

import pl.polsl.staneczek.service.dto.LoginDto;
import pl.polsl.staneczek.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private  NotificationService notificationService;

    public LoginDto login(HttpServletRequest request, String email, String password) {

        User user = userService.findByEmail(email);

        LoginDto loginDto = new LoginDto(null,null);

        byte[] decodedBytes = Base64.getDecoder().decode(user.getPassword());
        String userPassword = new String(decodedBytes);
        if (user != null && userPassword.equals(password)) {
            request.getSession().setAttribute("email", email);
            String encodeBytes = Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
            String token="Basic ".concat(encodeBytes);
            notificationService.createNotifications(user.getId(), user.getUserRole());
            LocalDate date=LocalDate.now();
            user.setLastLogin(date);
            userService.save(user);
            loginDto.setToken(token);
            loginDto.setUserRole(user.getUserRole());
            return loginDto;
        }
        return null;
    }
}
