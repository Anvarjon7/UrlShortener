package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.service.UrlBindingService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)

@WebMvcTest(UrlBindingController.class)
class UrlBindingControllerTest {

    @Autowired
    private MockMvc mockMvc;
//    @Mock
//    private AuthenticationService authenticationService;
    @MockBean
    private UrlBindingService urlBindingService;
    @InjectMocks
    private UrlBindingController urlBindingController;
    @Autowired
    private ObjectMapper objectMapper;
//
    @Test
    @SneakyThrows
    void create() throws Exception{

        UrlBindingCreateRequestDto urlBindingCreateRequestDto = new UrlBindingCreateRequestDto(
                "originalUrl",
                "pathPref",
                LocalDate.now());
        UrlBindingResponseDto urlBindingResponseDto = new UrlBindingResponseDto(
                1L,
                "orirnalUrl",
                "shortUrl",
                0L,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
                );
//        when(urlBindingService.register(any(UrlBindingCreateRequestDto.class))).thenReturn(UrlBinding.builder().build());
//      when(userService.register(any(UserRequestDto.class))).thenReturn(User.builder().build());
//
//
//        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequestDto))
//                        .characterEncoding("UTF-8"))
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);
//
//
//        assertNotNull(actual.id());
//        assertEquals(userRequestDto.getFirstName(), actual.firstName());
//        assertEquals(userRequestDto.getLastName(), actual.lastName());
//        assertEquals(userRequestDto.getEmail(), actual.email());
  }


    @Test
    void close() {
    }

    @Test
    void delete() {
    }

    @Test
    void getByUserId() throws Exception{
//        this.mockMvc.perform(MockMvcRequestBuilders.get(""))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Ok"));
    }

    @Test
    void getByUid() {
    }

    @Test
    void getByShortUrl() {
    }
}

//
//    @Test
//    @SneakyThrows
//    void createTest() throws Exception {
//
//        UserRequestDto userRequestDto = new UserRequestDto(
//                "Linda", "Din", "san@example.com", "password123", Role.ADMIN);
//        UserResponseDto userResponseDto = new UserResponseDto(1L, "Linda", "Din", "san@example.com");
//
//
//        when(userService.register(any(UserRequestDto.class))).thenReturn(User.builder().build());
//
//
//        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequestDto))
//                        .characterEncoding("UTF-8"))
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);
//
//
//        assertNotNull(actual.id());
//        assertEquals(userRequestDto.getFirstName(), actual.firstName());
//        assertEquals(userRequestDto.getLastName(), actual.lastName());
//        assertEquals(userRequestDto.getEmail(), actual.email());
//    }
//
//    @Test
//    @SneakyThrows
//    void updateTest() {
//        Long userId = 1L;
//        UserRequestDto userRequestDto = new UserRequestDto(
//                "Linda", "Din", "san@example.com", "password123", Role.ADMIN);
//        UserResponseDto userResponseDto = new UserResponseDto(userId, "Linda", "Din", "san@example.com");
//
//        when(userService.update(any(Long.class), any(UserRequestDto.class))).thenReturn(User.builder().build());
//
//        var result = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequestDto))
//                        .characterEncoding("UTF-8"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);
//
//        assertNotNull(actual.id());
//        assertEquals(userId, actual.id());
//        assertEquals(userRequestDto.getFirstName(), actual.firstName());
//        assertEquals(userRequestDto.getLastName(), actual.lastName());
//        assertEquals(userRequestDto.getEmail(), actual.email());
//    }
//
//
//    @Test
//    @SneakyThrows
//    void deleteTest() {
//        Long userId = 1L;
//
//        doNothing().when(userService).delete(userId);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(userService).delete(userId);
//    }
//
//    @Test
//    @SneakyThrows
//    void loginTest() {
//        SignInRequest signInRequest = new SignInRequest();
//        signInRequest.setLogin("san@example.com");
//        signInRequest.setPassword("password123");
//        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse("fake-jwt-token");
//
//        when(authenticationService.authenticate(any(SignInRequest.class))).thenReturn(jwtAuthenticationResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(signInRequest)))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.token").value("fake-jwt-token"));
//    }
//
//    @Test
//    @SneakyThrows
//    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
//    void getCurrentUserTest() {
//        UserResponseDto userResponseDto = new UserResponseDto(
//                1L, "Test", "User", "testuser@example.com");
//
//        when(userService.getCurrentUserId()).thenReturn(userResponseDto.id());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/current")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.id").value(1L))
//                .andExpect((ResultMatcher) jsonPath("$.firstName").value("Test"))
//                .andExpect((ResultMatcher) jsonPath("$.lastName").value("User"))
//                .andExpect((ResultMatcher) jsonPath("$.email").value("testuser@example.com"));
//    }
//}