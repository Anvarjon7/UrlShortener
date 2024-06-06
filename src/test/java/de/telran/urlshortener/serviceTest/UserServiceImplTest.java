package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.exception.UserAlreadyExistsException;
import de.telran.urlshortener.exception.UserNotFoundException;
import de.telran.urlshortener.model.entity.user.Role;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UserService;
import de.telran.urlshortener.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    private UserRequestDto userRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);

        userRequestDto = UserRequestDto.builder()
                .firstName("Test")
                .lastName("Mock")
                .email("test@gmail.com")
                .password("test2024")
                .role(Role.USER)
                .build();

        user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Mock")
                .email("test@gmail.com")
                .password("test2024")
                .role(Role.USER)
                .build();
    }

    @Test
    void findByIdWithSubscriptions() {

        given(userRepository.findByIdWithSubscriptions(1L)).willReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByIdWithSubscriptions(1L);
        assertThat(foundUser).isPresent().isEqualTo(Optional.of(user));
    }

    @Test
    void findById() {

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User foundUser = userService.findById(1L);
        verify(userRepository).findById(1L);
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void getCurrentUserId() {

    }

    @Test
    void findByIdWithBindings() {
        given(userRepository.findByIdWithBindings(1L)).willReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByIdWithBindings(1L);
        assertThat(foundUser).isPresent().isEqualTo(Optional.of(user));
    }

    @Test
    void findByIdWithUrlBindingsAndSubscriptions() {
        given(userRepository.findByIdWithUrlBindingsAndSubscriptions(1L)).willReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByIdWithUrlBindingsAndSubscriptions(1L);
        assertThat(foundUser).isPresent().isEqualTo(Optional.of(user));
    }

    @Test
    void registerTest() {
        userService.register(userRequestDto);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getFirstName()).isEqualTo(userRequestDto.getFirstName());
        assertThat(capturedUser.getLastName()).isEqualTo(userRequestDto.getLastName());
        assertThat(capturedUser.getEmail()).isEqualTo(userRequestDto.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo(userRequestDto.getPassword());
        assertThat(capturedUser.getRole()).isEqualTo(userRequestDto.getRole());
    }

    @Test
    void willThrowWhenEmailIsTaken() {

        given(userRepository.existsByEmail(userRequestDto.getEmail()))
                .willReturn(true);

        assertThatThrownBy(() -> userService.register(userRequestDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User already exists with email " + userRequestDto.getEmail() + "!");

        verify(userRepository, never()).save(any());
    }

    @Test
    void update() {
        Long id = 1L;

        given(userRepository.findById(id)).willReturn(Optional.of(user));

        UserRequestDto userRequestDto1 = UserRequestDto.builder()
                .firstName("Test")
                .lastName("Mock")
                .email("test@gmail.com")
                .password("test2024")
                .role(Role.USER)
                .build();

        userService.update(id, userRequestDto);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getFirstName()).isEqualTo(userRequestDto1.getFirstName());
        assertThat(capturedUser.getLastName()).isEqualTo(userRequestDto1.getLastName());
        assertThat(capturedUser.getEmail()).isEqualTo(userRequestDto1.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo(userRequestDto1.getPassword());
        assertThat(capturedUser.getRole()).isEqualTo(userRequestDto1.getRole());
    }

    @Test
    void getAllTest() {
        userService.getAll();

        verify(userRepository).findAll();
    }

    @Test
    void getById() {
        userService.getById(1L);

        verify(userRepository).findById(1L);
    }

    @Test
    void testGetByEmail() {
        String email = "test@gmail.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        User foundUser = userService.getByEmail(email);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEqualTo(user);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testWillThrowWhenEmailDoesNotExist() {
        String email = "test@gmail.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getByEmail(email))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with email " + email + " does not exist!");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUserWhenExists() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);
        userService.delete(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void willThrownWhenIdDoesNotExist() {

        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> userService.delete(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id " + id);
        verify(userRepository, never()).deleteById(anyLong());
    }
}