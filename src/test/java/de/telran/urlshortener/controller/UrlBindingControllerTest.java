package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.service.UrlBindingService;
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
import java.util.Optional;
import java.util.Set;

import static de.telran.urlshortener.testData.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UrlBindingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UrlBindingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlBindingService urlBindingService;

    @MockBean
    private UserService userService;

    @MockBean
    private Mapper<UrlBinding, UrlBindingResponseDto> mapper;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @SneakyThrows
    void createTest() {
        // Подготовка входных данных для теста
        UrlBindingCreateRequestDto requestDto = new UrlBindingCreateRequestDto("http://google.com", "bmw", LocalDate.now());

        // Подготовка мока возвращаемого значения из сервиса
        UrlBinding urlBinding = UrlBinding.builder()
                .id(1L)
                .originalUrl("http://google.com")
                .count(0L)
                .expirationDate(LocalDate.now().plusDays(30))
                .creationDate(LocalDate.now())
                .build();

        // Подготовка мока возвращаемого значения из маппера
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "http://g.com/shortened-url", 0L, LocalDate.now(), LocalDate.now().plusDays(30));

        // Настройка моков
        when(urlBindingService.create(any(UrlBindingCreateRequestDto.class))).thenReturn(urlBinding);
        when(mapper.toDto(any(UrlBinding.class))).thenReturn(responseDto);

        // Выполнение запроса и проверка статуса ответа
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/url-bindings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();

        // Чтение тела ответа
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // Десериализация ответа
        UrlBindingResponseDto actual = objectMapper.readValue(jsonResponse, UrlBindingResponseDto.class);

        // Проверка полей десериализованного ответа
        assertNotNull(actual.id());
        assertEquals(requestDto.originalUrl(), actual.originalUrl());
        assertEquals(responseDto.shortUrl(), actual.shortUrl());

    }


    @Test
    @SneakyThrows
    void closeTest() {
        Long Id = 1L;
        doNothing().when(urlBindingService).close(Id);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/url-bindings/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(urlBindingService).close(Id);
    }

    @Test
    @SneakyThrows
    void deleteTest() {
        Long Id = 1L;
        doNothing().when(urlBindingService).delete(Id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/url-bindings/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(urlBindingService).delete(Id);
    }

    @Test
    @SneakyThrows
    void getByUserId() {
        Long userId = 1L;
        Set<UrlBindingResponseDto> responseSet = Set.of(
                new UrlBindingResponseDto(1L,
                        "http://google.com",
                        "http://g.com/abc",
                        0L,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)),
                new UrlBindingResponseDto(2L,
                        "http://google.com",
                        "http://g.com/xyz",
                        0L,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30))
        );

        Set<UrlBinding> urlBindingSet = Set.of(
                UrlBinding.builder()
                        .id(1L)
                        .originalUrl("http://google.com")
                        .count(0L)
                        .user(USER1)
                        .expirationDate(LocalDate.now().plusDays(30))
                        .creationDate(LocalDate.now())
                        .build(),
                UrlBinding.builder()
                        .id(2L)
                        .originalUrl("http://google.com")
                        .count(0L)
                        .user(USER1)
                        .expirationDate(LocalDate.now().plusDays(30))
                        .creationDate(LocalDate.now())
                        .build()
        );

        given(userService.getCurrentUserId()).willReturn(userId);
        given(urlBindingService.getByUserId(userId)).willReturn(urlBindingSet);
        given(mapper.toDtoSet(urlBindingSet)).willReturn(responseSet);

        // Выполнение запроса и проверка статуса ответа
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/url-bindings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        // Чтение тела ответа
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // Десериализация ответа
        Set<UrlBindingResponseDto> actualSet = objectMapper.readValue(jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(Set.class, UrlBindingResponseDto.class));

        // Проверка полей десериализованного ответа
        assertEquals(responseSet.size(), actualSet.size());
        for (UrlBindingResponseDto actual : actualSet) {
            UrlBindingResponseDto expected = responseSet.stream()
                    .filter(dto -> dto.id().equals(actual.id()))
                    .findFirst()
                    .orElseThrow();
            assertEquals(expected.id(), actual.id());
            assertEquals(expected.originalUrl(), actual.originalUrl());
            assertEquals(expected.count(), actual.count());
            assertEquals(expected.creationDate(), actual.creationDate());
            assertEquals(expected.expirationDate(), actual.expirationDate());
        }
    }


    @Test
    @SneakyThrows
    void getByUid() {
        String uid = "uid";

        UrlBinding urlBinding = UrlBinding.builder()
                .id(1L)
                .originalUrl("http://google.com")
                .count(0L)
                .expirationDate(LocalDate.now().plusDays(30))
                .creationDate(LocalDate.now())
                .uid(uid)
                .build();

        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(
                1L,
                "http://google.com",
                "shortUrl",
                0L,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        given(urlBindingService.getByUid(uid)).willReturn(Optional.of(urlBinding));
        given(mapper.toDto(urlBinding)).willReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/url-bindings/uid/{uid}", uid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(urlBindingService).getByUid(uid);
    }

    @Test
    @SneakyThrows
    void getByShortUrl() {
        String shortUrl = "shortUrl";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/url-bindings/shortUrl/{id}", shortUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(urlBindingService).getByShortUrl(shortUrl, false);
    }


}
