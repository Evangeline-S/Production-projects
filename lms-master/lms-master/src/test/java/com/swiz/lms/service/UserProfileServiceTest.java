package com.swiz.lms.service;

import com.swiz.lms.dto.UserDetailsImpl;
import com.swiz.lms.dto.UserProfileUpdateRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.repository.UserRepository;
import com.swiz.lms.security.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserProfileServiceTest {
    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void testUpdateProfile() {

            UserDetails userDetails = new UserDetailsImpl("testuser", "password", true, true, true, true, null);
            UserProfileUpdateRequest updateRequest = new UserProfileUpdateRequest("newusername", "newemail", "newpassword");

            when(authenticationFacade.getCurrentUser()).thenReturn(userDetails);
            when(userRepository.findByUsername("testuser")).thenReturn(new AppUser("testuser", "password", "oldemail"));

            assertDoesNotThrow(() -> userService.updateProfile(updateRequest));
            verify(userRepository).save(any(AppUser.class));
        }

        @Test
        public void test_UpdateProfilesimpleway(){
        String expectedUsername = "testuser";
        System.out.println("Expected Username: " + expectedUsername);

        when(authenticationFacade.getCurrentUser()).thenReturn(new UserDetailsImpl(expectedUsername, "password", true, true, true, true, null));
        when(userRepository.findByUsername(expectedUsername)).thenReturn(new AppUser(expectedUsername, "password", "oldemail"));

    }

        @Test
        public void testUpdateProfile_UserNotFound() {
            UserDetails userDetails = new UserDetailsImpl("testuser", "password", true, true, true, true, null);
            UserProfileUpdateRequest updateRequest = new UserProfileUpdateRequest("newusername", "newemail", "newpassword");

            when(authenticationFacade.getCurrentUser()).thenReturn(userDetails);
            when(userRepository.findByUsername("testuser")).thenReturn(null);

            assertThrows(UsernameNotFoundException.class, () -> userService.updateProfile(updateRequest));
            verify(userRepository, never()).save(any(AppUser.class));
        }

    @Test
    public void testUpdateProfile1() {
        // Arrange
       UserDetails userDetails = new UserDetailsImpl("testuser", "password", true, true, true, true, null);
        UserProfileUpdateRequest updateRequest = new UserProfileUpdateRequest("newusername", "newemail", "newpassword");

        // Mock setup
        when(authenticationFacade.getCurrentUser()).thenReturn(userDetails);
        when(userRepository.findByUsername("testuser")).thenReturn(new AppUser("testuser", "password", "oldemail"));

        // Act
        userService.updateProfile(updateRequest);

        // Assert and Verify
        verify(userRepository).findByUsername(eq("testuser"));
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    }