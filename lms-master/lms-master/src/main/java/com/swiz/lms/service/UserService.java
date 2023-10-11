package com.swiz.lms.service;

import com.swiz.lms.dto.UserProfileUpdateRequest;
import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.repository.UserRepository;
import com.swiz.lms.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade; // You'll need to inject this to get the current user


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
    }

    public void registerUser(UserRegistrationRequest registrationRequest) {
        // Validate the request
        if (registrationRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        // Check if the email is already used
        if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create a new user entity
        AppUser user = new AppUser();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        // Set other fields as needed
        // Save the user to the database
        userRepository.save(user);
    }

    public void updateProfile(UserProfileUpdateRequest updateRequest) {
        // Get the current user from the authentication facade
        System.out.println("authenticationFacade.getCurrentUser() is "+ authenticationFacade.getCurrentUser());
        UserDetails currentUser = authenticationFacade.getCurrentUser();
        String username = currentUser.getUsername(); //or another unique identifier
        AppUser user = userRepository.findByUsername(username); //assuming you have a method to find by username
        System.out.println("userRepository.findByUsername(username) ->"+userRepository.findByUsername(username));
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Update the user's profile with the new information
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPassword(updateRequest.getPassword());

        // You might have other fields to update as well

        // Save the updated user back to the database
        userRepository.save(user);
    }



    // your business methods here, e.g., saveUser, findUserByEmail, etc.
}
