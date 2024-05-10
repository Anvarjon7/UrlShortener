package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByIdWithSubscriptions() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findByIdWithSubscriptions(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByIdWithSubscriptions(userId);
        assertEquals(user, result.orElse(null));
        verify(userRepository, times(1)).findByIdWithSubscriptions(userId);
    }

    @Test
    public void testFindByIdWithBindings() {

        Long userId = 1L;
        User user = new User();
        when(userRepository.findByIdWithBindings(userId)).thenReturn(Optional.of(user));


        Optional<User> result = userService.findByIdWithBindings(userId);


        assertEquals(user, result.orElse(null));
        verify(userRepository, times(1)).findByIdWithBindings(userId);
    }

    @Test
    public void testFindByIdWithUrlBindingsAndSubscriptions() {

        Long userId = 1L;
        User user = new User();
        when(userRepository.findByIdWithUrlBindingsAndSubscriptions(userId)).thenReturn(Optional.of(user));


        Optional<User> result = userService.findByIdWithUrlBindingsAndSubscriptions(userId);


        assertEquals(user, result.orElse(null));
        verify(userRepository, times(1)).findByIdWithUrlBindingsAndSubscriptions(userId);
    }
}
