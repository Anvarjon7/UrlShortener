package de.telran.urlshortener.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
   /* @Autowired
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
                "A", "B", "k@example.com", "1", Role.ADMIN);

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
                "Artur", "Sin", "on12@example.com", "password555", Role.ADMIN);

        when(userService.update(any(Long.class), any(UserRequestDto.class))).thenReturn(User.builder().build());

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins/{id}", userId)
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
        Long userId = 2L;

        doNothing().when(userService).delete(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admins/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).delete(userId);
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

                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))

                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].firstName").value(users.get(1).getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(users.get(1).getLastName()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()));
    }

    @Test
    @SneakyThrows
    void getByIdTest() {
        UserResponseDto expected = USER_RESPONSE_DTO1;
        mockMvc.perform(get("/api/admins/" + expected.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.id()))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @SneakyThrows
    void getByEmailTest() {
        UserResponseDto expectedUser = new UserResponseDto(1L, "Linda", "Din", "san@example.com");

        when(userService.getByEmail("san@example.com")).thenReturn(User.builder().build());

        mockMvc.perform(get("/api/admins/san@example.com"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(expectedUser.id()))
                .andExpect(jsonPath("$.firstName").value(expectedUser.firstName()))
                .andExpect(jsonPath("$.lastName").value(expectedUser.lastName()))
                .andExpect(jsonPath("$.email").value(expectedUser.email()));
    }*/
}