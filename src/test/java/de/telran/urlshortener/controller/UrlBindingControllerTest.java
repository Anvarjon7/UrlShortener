//package de.telran.urlshortener.controller;
//
//import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
//import de.telran.urlshortener.dto.UrlBindingResponseDto;
//import de.telran.urlshortener.mapper.Mapper;
//import de.telran.urlshortener.model.entity.binding.UrlBinding;
//import de.telran.urlshortener.service.UrlBindingService;
//import de.telran.urlshortener.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDate;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = UrlBindingController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockitoExtension.class)

package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void createUrlBinding() throws Exception {
        UrlBindingCreateRequestDto requestDto = new UrlBindingCreateRequestDto("http://google.com", "bmw", LocalDate.now());
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "http://g.com", 0L, LocalDate.now(), LocalDate.now().plusDays(30));

        given(urlBindingService.create(requestDto)).willReturn(new UrlBinding());
        given(mapper.toDto(new UrlBinding())).willReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/url-bindings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"http://google.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.shortUrl").value("http://g.com/abc"))
                .andExpect(jsonPath("$.originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$.closed").value(false));
    }

    @Test
    void closeUrlBinding() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/url-bindings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUrlBinding() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/url-bindings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getByUserId() throws Exception {
        Long userId = 1L;
        Set<UrlBindingResponseDto> responseSet = Set.of(
                new UrlBindingResponseDto(1L,
                        "http://g.com/abc",
                        "http://google.com",
                        0L,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)),
                new UrlBindingResponseDto(2L,
                        "http://g.com/abc",
                        "http://google.com",
                        0L,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)));

        given(userService.getCurrentUserId()).willReturn(userId);
        given(urlBindingService.getByUserId(userId)).willReturn(Set.of(new UrlBinding()));
        given(mapper.toDtoSet(Set.of(new UrlBinding()))).willReturn(responseSet);

        mockMvc.perform(get("/api/url-bindings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].shortUrl").value("http://g.com/abc"))
                .andExpect(jsonPath("$[0].originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$[0].closed").value(false))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].shortUrl").value("http://g.com/def"))
                .andExpect(jsonPath("$[1].originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$[1].closed").value(false));
    }

    @Test
    void getByUid() throws Exception {
        String uid = "/uid";
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "http://g.com", 0L, LocalDate.now(), LocalDate.now().plusDays(30));

        given(urlBindingService.getByUid(uid)).willReturn(Optional.of(new UrlBinding()));
        given(mapper.toDto(new UrlBinding())).willReturn(responseDto);

        mockMvc.perform(get("/api/url-bindings/uid/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.shortUrl").value("http://g.com/abc"))
                .andExpect(jsonPath("$.originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$.closed").value(false));
    }

    @Test
    void getByShortUrl() throws Exception {
        String shortUrl = "_shortUrl";
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "_shortUrl", 0L, LocalDate.now(), LocalDate.now().plusDays(30));
        UrlBinding urlBinding = new UrlBinding();
//        UrlBinding urlBinding = new UrlBinding().toBuilder()
//                .id(1L)
//                .uid("_shortUrl")
//                .pathPrefix("")
//                .baseUrl("")
//                .user(USER1)
//                .originalUrl("http://google.com")
//                .build();
        given(urlBindingService.getByShortUrl(shortUrl, true)).willReturn(new UrlBinding());
//        given(mapper.toDto(urlBinding)).willReturn(responseDto);

        ResultActions resultActions = mockMvc.perform(get("/api/url-bindings/shortUrl/{shortUrl}", shortUrl));
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.shortUrl").value("http://g.com"))
//                .andExpect(jsonPath("$.originalUrl").value("http://google.com"))
//                .andExpect(jsonPath("$.closed").value(false));
        // Получение результата
        MvcResult mvcResult = resultActions.andReturn();

        // Преобразование JSON ответа в объект
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        UrlBindingResponseDto resultDto = objectMapper.readValue(jsonResponse, UrlBindingResponseDto.class);

    }

    @Test
    void getByShortUrl1() throws Exception {
        String shortUrl = "_shortUrl";
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "_shortUrl", 0L, LocalDate.now(), LocalDate.now().plusDays(30));
        UrlBinding urlBinding = new UrlBinding();
        given(urlBindingService.getByShortUrl(shortUrl, true)).willReturn(new UrlBinding());

        ResultActions resultActions = mockMvc.perform(get("/api/url-bindings/shortUrl/{shortUrl}", shortUrl));
        // Получение результата
        MvcResult mvcResult = resultActions.andReturn();

        // Преобразование JSON ответа в объект
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        UrlBindingResponseDto resultDto = objectMapper.readValue(jsonResponse, UrlBindingResponseDto.class);

    }


    @Test
    void testGetByShortUrl() throws Exception {
        String shortUrl = "_shortUrl";
        UrlBindingResponseDto responseDto = new UrlBindingResponseDto(1L, "http://google.com", "_shortUrl", 0L, LocalDate.now(), LocalDate.now().plusDays(30));
        UrlBinding urlBinding = new UrlBinding();

        // Настройка мока для сервиса и маппера
        given(urlBindingService.getByShortUrl(shortUrl, true)).willReturn(urlBinding);
        given(mapper.toDto(urlBinding)).willReturn(responseDto);

        // Выполнение запроса
        ResultActions resultActions = mockMvc.perform(get("/api/url-bindings/shortUrl/{shortUrl}", shortUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.shortUrl").value("_shortUrl"))
                .andExpect(jsonPath("$.originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$.closed").value(false));

        // Получение результата
        MvcResult mvcResult = resultActions.andReturn();

        // Преобразование JSON ответа в объект
        String jsonResponse = mvcResult.getResponse().getContentAsString();

        // Проверка содержимого JSON-ответа
        ObjectMapper objectMapper = new ObjectMapper();
        UrlBindingResponseDto resultDto = objectMapper.readValue(jsonResponse, UrlBindingResponseDto.class);

//        assertEquals(1L, resultDto.getId());
//        assertEquals("_shortUrl", resultDto.getShortUrl());
//        assertEquals("http://google.com", resultDto.getOriginalUrl());
//        assertEquals(0L, resultDto.getUsageCount());
//        assertEquals(LocalDate.now(), resultDto.getCreatedDate());
//        assertEquals(LocalDate.now().plusDays(30), resultDto.getExpirationDate());
//        assertFalse(resultDto.isClosed());
    }


}
