package de.telran.urlshortener.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.UrlShortenerApplication;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.Role;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.security.AuthenticationService;
import de.telran.urlshortener.security.model.JwtAuthenticationResponse;
import de.telran.urlshortener.security.model.SignInRequest;
import de.telran.urlshortener.service.UserService;
import de.telran.urlshortener.testData.UserTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static de.telran.urlshortener.repository.RepositoryTestData.USER1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {UserTestData.class, UrlShortenerApplication.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AuthenticationService authenticationService;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void createTest() {
        UserRequestDto userRequestDto = new UserRequestDto(
                "A", "B", "1@1.ua", "1", Role.USER);

        when(userService.register(any(UserRequestDto.class))).thenReturn(User.builder().build());

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertNotNull(actual.id());
        assertEquals(userRequestDto.getFirstName(), actual.firstName());
        assertEquals(userRequestDto.getLastName(), actual.lastName());
        assertEquals(userRequestDto.getEmail(), actual.email());
    }

    @Test
    @SneakyThrows
    void updateTest() {
        Long userId = null;
        UserRequestDto userRequestDto = new UserRequestDto(
                "A2", "B2", "2@2.ua", "2", Role.USER);

        when(userService.update(any(Long.class), any(UserRequestDto.class))).thenReturn(User.builder().build());

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertNotNull(actual.id());
        assertEquals(userId, actual.id());
        assertEquals(userRequestDto.getFirstName(), actual.firstName());
        assertEquals(userRequestDto.getLastName(), actual.lastName());
        assertEquals(userRequestDto.getEmail(), actual.email());
    }

    @Test
    @SneakyThrows
    void deleteTest() {
        Long userId = null;

        doNothing().when(userService).delete(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).delete(userId);
    }

    @Test
    @SneakyThrows
    void loginTest() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setLogin("1@1.ua");
        signInRequest.setPassword("1");
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse("fake-jwt-token");

        when(authenticationService.authenticate(any(SignInRequest.class))).thenReturn(jwtAuthenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "3@3.ua", roles = {"USER"})
    void getCurrentUserTest() {
        UserResponseDto userResponseDto = new UserResponseDto(
                null, "A2", "B2", "2@2.ua");

        when(userService.getCurrentUserId()).thenReturn(userResponseDto.id());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/current")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(1L))
                .andExpect((ResultMatcher) jsonPath("$.firstName").value("A2"))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value("B2"))
                .andExpect((ResultMatcher) jsonPath("$.email").value("2@2.ua"));
    }

    @Test
    @SneakyThrows
    void getByIdTest() {
        User expected = USER1;
        mockMvc.perform(get("/api/users/id/{id}" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void getByEmailTest() {
        UserResponseDto expectedUser = new UserResponseDto(null, "A", "B", "3@3.ua");

        when(userService.getByEmail("3@3.ua")).thenReturn(User.builder().build());

        mockMvc.perform(get("/api/users/3@3.ua"))

                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUser.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(expectedUser.firstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(expectedUser.lastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedUser.email()));
    }

    @Test
    @SneakyThrows
    void getAllTest() {
        List<User> users = List.of(
                User.builder()
                        .id(null)
                        .firstName("A3")
                        .lastName("B3")
                        .email("3@3.ua")
                        .password("3")
                        .role(Role.valueOf("USER"))
                        .subscriptions(null)
                        .bindings(null)
                        .build(),
                User.builder()
                        .id(null)
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

        mockMvc.perform(get("/api/users/"))

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
}