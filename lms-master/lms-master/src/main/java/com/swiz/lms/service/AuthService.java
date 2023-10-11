package com.swiz.lms.service;

import com.swiz.lms.component.JwtTokenProvider;
import com.swiz.lms.dto.UserLoginRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.exception.UnauthorizedException;
import com.swiz.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider; // Assuming you're using JWT for tokens

    public String authenticate(UserLoginRequest loginRequest) {
        // Find the user by email
        System.out.println("***********************************************"+loginRequest.getEmail());
        AppUser user = userRepository.findByEmail(loginRequest.getEmail());
        System.out.println("***********************************************"+user);
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("exeption occured in authenticate");
            throw new UnauthorizedException("Invalid username or password");
        }

        // Generate a token for the user
        String token = tokenProvider.generateToken(user.getUsername());
        return token;
    }
}
