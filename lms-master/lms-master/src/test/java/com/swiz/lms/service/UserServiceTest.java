package com.swiz.lms.service;

import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.repository.UserRepository;
import com.swiz.lms.security.AuthenticationFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//going to run unit test by mock different services

class UserServiceTest {


        @InjectMocks
        private UserService userService;
        @Mock
        private UserRepository userRepository;
        @Mock
        private PasswordEncoder passwordEncoder;
        @Mock
        private AuthenticationFacade authenticationFacade;

        @Test
        public void testRegisterUser()
        {
            //Set up the data
            AppUser user=AppUser.builder()
                    .id(1L)
                    .username("Eva")
                    .email("evapaul@gmail.com")
                    .password("HelloEva")
                    .build();
            UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                    .username("Eva")
                    .email("evapaul@gmail.com")
                    .password("HelloEva")
                    .build();
            //Stub the mock using when and return
            when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(null);
            //when(userRepository.save(any(AppUser.class))).thenReturn(user);
            when(passwordEncoder.encode(userRegistrationRequest.getPassword())).thenReturn("encodePassword");
            ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

            //Execute
            userService.registerUser(userRegistrationRequest);
            //validate
            verify(userRepository).save(argumentCaptor.capture());
            AppUser saveUser = argumentCaptor.getValue();

            assertEquals("Eva",saveUser.getUsername(),"User name shuld match");
            assertEquals("evapaul@gmail.com",saveUser.getEmail(),"Email should match");
            assertEquals("encodePassword",saveUser.getPassword(),"Password should be same");
        }



    }

