package com.swiz.lms.repository;

import com.swiz.lms.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest


class UserRepositoryTest {
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        AppUser mockUser= new AppUser();
        mockUser.setUsername("Eva");
        AppUser mockemail=new AppUser();
        mockemail.setEmail("evapaul@gmail.com");

        //setup mock user
        when(userRepository.findByUsername("username")).thenReturn(mockUser);
        when(userRepository.findByEmail("email")).thenReturn(mockemail);

    }

    @Test
    public void testFindByUserName(){
        //setup data
        AppUser appUser = AppUser.builder()
                .id(1L)
                .username("Eva")
                .email("evapaul@gmail.com")
                .password("hello_eva")
                .build();

        //execute
        userRepository.save(appUser);
        AppUser foundUser= userRepository.findByUsername("username");
        assertNotNull(foundUser);
        assertEquals(appUser.getUsername(),foundUser.getUsername());
    }
    @Test
    public void testfindByEmail(){
        //setup data
        AppUser appUser = AppUser.builder()
                .id(1L)
                .username("Eva")
                .email("evapaul@gmail.com")
                .password("hello_eva")
                .build();

        //execute
        userRepository.save(appUser);
        AppUser foundmail=userRepository.findByEmail("email");
        assertNotNull(foundmail);
        assertEquals(appUser.getEmail(),foundmail.getEmail());
    }


}