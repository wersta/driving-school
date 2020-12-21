package pl.polsl.staneczek.controllers;

import pl.polsl.staneczek.service.LoginService;
import pl.polsl.staneczek.service.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@CrossOrigin("http://localhost:8080")
@ResponseBody
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(HttpServletRequest request, @RequestParam String email, @RequestParam String password) {
        LoginDto loginDto=loginService.login(request, email, password);

        if (loginDto!=null) {
            return new ResponseEntity<>(loginDto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
