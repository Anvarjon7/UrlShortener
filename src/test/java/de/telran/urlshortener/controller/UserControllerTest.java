package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.mapper.UserMapper;
import de.telran.urlshortener.model.entity.user.Role;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.security.AuthenticationService;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.security.model.JwtAuthenticationResponse;
import de.telran.urlshortener.security.model.SignInRequest;
import de.telran.urlshortener.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper mapper;
    @MockBean
    private JwtService jwtService;
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @SneakyThrows
    @WithMockUser
    void createTest() {
        objectMapper = new ObjectMapper();
        UserRequestDto requestDto = new UserRequestDto(
                "A",
                "B",
                "1@1.ua",
                "1",
                Role.USER);
        User user = User.builder()
                .id(1l)
                .firstName("A")
                .lastName("B")
                .email("1@1.ua")
                .password("1")
                .role(Role.USER)
                .build();
        UserResponseDto responseDto = new UserResponseDto(
                1l,
                "A",
                "B",
                "1@1.ua"
        );

        when(userService.register(any(UserRequestDto.class))).thenReturn(user);
        when(mapper.toDto(any(User.class))).thenReturn(responseDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertNotNull(actual.id());
        assertEquals(requestDto.getEmail(), actual.email());
        assertEquals(responseDto.email(), actual.email());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void updateTest() {
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "A",
                "B",
                "1@1.ua"
        );

        when(userService.register(ArgumentMatchers.any(UserRequestDto.class))).thenReturn(User.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
                        .content(objectMapper.writeValueAsString(userResponseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value("1"))
                .andExpect((ResultMatcher) jsonPath("$.firstName").value("A"))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value("B"))
                .andExpect((ResultMatcher) jsonPath("$.email").value("1@1.ua"))
                .andExpect((ResultMatcher) jsonPath("$.password").value("2"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "1@1.ua")
    public void getCurrentUserTest() {
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "A",
                "B",
                "1@1.ua");

        when(userService.getByEmail("1@1.ua")).thenReturn(User.builder().build());
        when(mapper.toDto(User.builder().build())).thenReturn(userResponseDto);

        // Выполнение и проверка
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/current")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(userResponseDto.id()))
                .andExpect((ResultMatcher) jsonPath("$.firstName").value(userResponseDto.firstName()))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value(userResponseDto.lastName()))
                .andExpect((ResultMatcher) jsonPath("$.email").value(userResponseDto.email()));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void updateCurrentUserTest() {
        UserRequestDto userRequestDto = new UserRequestDto(
                "A",
                "B",
                "1@1.ua",
                "1",
                Role.USER
        );

        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "A",
                "B",
                "1@1.ua");

        when(userService.getCurrentUserId()).thenReturn(1L);
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userService.update(1L, userRequestDto)).thenReturn(User.builder().build());
        when(mapper.toDto(ArgumentMatchers.any())).thenReturn(userResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(userResponseDto.id()))
                .andExpect((ResultMatcher) jsonPath("$.firstName").value(userResponseDto.firstName()))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value(userResponseDto.lastName()))
                .andExpect((ResultMatcher) jsonPath("$.email").value(userResponseDto.email()));
    }

    @Test
    public void loginTest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("jwt-token");

        when(authenticationService.authenticate(ArgumentMatchers.any(SignInRequest.class)))
                .thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.token").value(jwtResponse.getToken()));
    }

    @Test
    @SneakyThrows
    void deleteTest() {
        Long id = 1L;
        doNothing().when(userService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).delete(id);
    }

    @Test
    @SneakyThrows
    void getAllTest() {
        List<User> users = List.of(
                User.builder()
                        .id(1l)
                        .firstName("A3")
                        .lastName("B3")
                        .email("3@3.ua")
                        .password("3")
                        .role(Role.valueOf("USER"))
                        .subscriptions(null)
                        .bindings(null)
                        .build(),
                User.builder()
                        .id(4l)
                        .firstName("A4")
                        .lastName("B4")
                        .email("4@4.ua")
                        .password("4")
                        .role(Role.valueOf("USER"))
                        .subscriptions(null)
                        .bindings(null)
                        .build()
        );

        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"))

                .andExpect(status().isOk())

                .andExpect((ResultMatcher) jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect((ResultMatcher) jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect((ResultMatcher) jsonPath("$[0].email").value(users.get(0).getEmail()))

                .andExpect((ResultMatcher) jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect((ResultMatcher) jsonPath("$[1].firstName").value(users.get(1).getFirstName()))
                .andExpect((ResultMatcher) jsonPath("$[1].lastName").value(users.get(1).getLastName()))
                .andExpect((ResultMatcher) jsonPath("$[1].email").value(users.get(1).getEmail()));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getByIdTest() {
        User user = new User();
        when(userService.findById(1l)).thenReturn(User.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value("1"));

    }

    @Test
    @SneakyThrows
    void getByEmailTest() {
        String email = "1@1.ua";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).getByEmail(email);
    }
}





