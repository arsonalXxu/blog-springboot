package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper mockMapper;
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @InjectMocks
    UserService userService;

    @Test
    void testSave() {
        Mockito.when(mockEncoder.encode("myPassword")).thenReturn("myEncodedPassword");
        userService.save("myUser", "myPassword");
        Mockito.verify(mockMapper).save("myUser", "myEncodedPassword");
    }

    @Test
    void testGetUserByUsername() {
        userService.getUserByUsername("myUser");
        Mockito.verify(mockMapper).getUserByUsername("myUser");
    }

    @Test
    void throwExceptionWhenUserNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    void returnUserDetailsWhenUserFound() {
        Mockito.when(mockMapper.getUserByUsername("myUser"))
                .thenReturn(new User(123L, "myUser", "myEncodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("myUser");
        assertEquals("myUser", userDetails.getUsername());
        assertEquals("myEncodedPassword", userDetails.getPassword());
    }

}