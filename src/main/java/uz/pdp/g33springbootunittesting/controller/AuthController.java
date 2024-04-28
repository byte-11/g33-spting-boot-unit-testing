package uz.pdp.g33springbootunittesting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.g33springbootunittesting.domain.User;
import uz.pdp.g33springbootunittesting.dto.UserRegistrationDTO;
import uz.pdp.g33springbootunittesting.service.UserService;

@RestController("/auth")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(userService.saveUser(dto));
    }
}
