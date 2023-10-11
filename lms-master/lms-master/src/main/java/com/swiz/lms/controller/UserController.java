package com.swiz.lms.controller;


import com.swiz.lms.dto.AuthenticationResponse;
import com.swiz.lms.dto.UserProfileUpdateRequest;
import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.dto.UserLoginRequest;
import com.swiz.lms.service.AuthService;
import com.swiz.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;



    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;

    }

    // your endpoint methods here, e.g., register, login, etc.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            userService.registerUser(registrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        try {
            System.out.println(loginRequest);
            String token = authService.authenticate(loginRequest);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (Exception e) {
            System.out.println("Exception occured");
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            System.out.println("Updating the user" + updateRequest);
            userService.updateProfile(updateRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}



