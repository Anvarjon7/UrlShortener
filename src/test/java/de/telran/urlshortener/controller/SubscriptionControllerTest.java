package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static de.telran.urlshortener.repositoryTest.RepositoryTestData.USER1;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserService userService;

    @MockBean
    private Mapper<Subscription, SubscriptionResponseDto> mapper;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create() {
        // Подготовка мока возвращаемого значения из сервиса
        Subscription subscription = Subscription.builder()
                .id(1L)
                .user(USER1)
                .status(Status.UNPAID)
                .creationDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(30))
                .build();

        // Подготовка мока возвращаемого значения из маппера
        SubscriptionResponseDto responseDto = new SubscriptionResponseDto(1L,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                Status.UNPAID);
        // Настройка моков

        when(subscriptionService.create(any())).thenReturn(subscription);
        when(mapper.toDto(any(Subscription.class))).thenReturn(responseDto);

        // Выполнение запроса и проверка статуса ответа
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/subscriptions/create")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString())
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();


        // Чтение тела ответа
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // Десериализация ответа
        SubscriptionResponseDto actual = objectMapper.readValue(jsonResponse, SubscriptionResponseDto.class);

        // Проверка полей десериализованного ответа
        assertNotNull(actual.id());

    }

    @Test
    @SneakyThrows
    void getById() {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subscriptions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subscriptionService).findById(id);
    }


    @Test
    @SneakyThrows
    void setPaidStatus() {
        Long id = 1L;

        // Мокируем метод сервиса
        doNothing().when(subscriptionService).setPaidStatus(id);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/subscriptions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Проверяем, что метод сервиса был вызван
        verify(subscriptionService).setPaidStatus(id);
    }

    @Test
    @SneakyThrows
    void delete() {
        Long Id = 1L;
        doNothing().when(subscriptionService).delete(Id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subscriptions/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subscriptionService).delete(Id);
    }

    @Test
    @SneakyThrows
    void getByUserId() {
        Long userId = userService.getCurrentUserId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subscriptions/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subscriptionService).getByUserId(userId);
    }
}