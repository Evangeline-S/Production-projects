package com.swiz.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiz.lms.component.JwtTokenProvider;
import com.swiz.lms.dto.UserLoginRequest;
import com.swiz.lms.dto.UserProfileUpdateRequest;
import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.repository.UserRepository;
import com.swiz.lms.security.AuthenticationFacade;
import com.swiz.lms.security.CustomUserDetailsService;
import com.swiz.lms.service.AuthService;
import com.swiz.lms.service.UserService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//mention the test class
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtTokenProvider  jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @InjectMocks
    private UserController userController;

    
    @Test
    
    public void testResgisterEndPoint() throws Exception{
        
        //setup the dada
        UserRegistrationRequest userRegistrationRequest=UserRegistrationRequest.builder()
                .username("Eva")
                .email("evapaul@gmail.com")
                .password("nimmu123")
                .build();
        
        AppUser appUser=AppUser.builder()
                .id(1L)
                .username("Eva")
                .email("evapaul@gmail.com")
                .password("nimmu123")
                .build();
        //execute
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(appUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        //.andExpect(jsonPath("$.username", is(user.getUsername())));

        verify(userService).registerUser(userRegistrationRequest);
    }
    @Test
    public void testLogin_Success() throws Exception {
        UserLoginRequest loginRequest = UserLoginRequest.builder()
                .email("testUser")
                .password("testPassword")
                .build();

        String token = "sampleToken";
        when(authService.authenticate(loginRequest)).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/login")
                        .content(asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));
    }

    // Similar tests for other endpoints can be added here...

    // Helper method to convert objects to JSON string
    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testUpdateProfile_Success() throws Exception {
        UserProfileUpdateRequest updateRequest = UserProfileUpdateRequest.builder()
                .username("testUser")
                .email("updated@example.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/profile")
                        .content(asJsonString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).updateProfile(updateRequest);
    }

}

