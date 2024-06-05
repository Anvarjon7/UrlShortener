package de.telran.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.TopRecord;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.service.StatisticsService;
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

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatisticsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticsService statisticsService;
    @MockBean
    private JwtService jwtService;

    @Test
    void getByUser() throws Exception {

        Long userId = 1L;
        UserStatisticsResponse response = new UserStatisticsResponse(userId, Map.of(1L, 100L, 2L, 200L));

        given(statisticsService.getByUser(userId)).willReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.bindingIdToCount.1").value(100))
                .andExpect(jsonPath("$.bindingIdToCount.2").value(200));
    }

    @Test
    void getByCurrentUser() throws Exception {
        Long userId = 1L;
        UserStatisticsResponse response = new UserStatisticsResponse(userId, Map.of(1L, 100L, 2L, 200L));
        given(statisticsService.getByCurrentUser()).willReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/current"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.bindingIdToCount.1").value(100))
                .andExpect(jsonPath("$.bindingIdToCount.2").value(200));
    }

    @Test
    void getTopStatistics() throws Exception {
        int top = 3;
        TopBindingStatisticsResponse topBindingStatisticsResponse = new TopBindingStatisticsResponse(List.of(
                new TopRecord(3L, 5L, 150L),
                new TopRecord(23L, 34L, 200L),
                new TopRecord(15L, 65L, 287L)
        ));

        given(statisticsService.getBindingTop(top)).willReturn(topBindingStatisticsResponse);

        mockMvc.perform(get("/api/statistics/top/{top}", top))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.top[0].bindingId").value(3L))
                .andExpect(jsonPath("$.top[0].userId").value(5L))
                .andExpect(jsonPath("$.top[0].count").value(150L))
                .andExpect(jsonPath("$.top[1].bindingId").value(23L))
                .andExpect(jsonPath("$.top[1].userId").value(34L))
                .andExpect(jsonPath("$.top[1].count").value(200L))
                .andExpect(jsonPath("$.top[2].bindingId").value(15L))
                .andExpect(jsonPath("$.top[2].userId").value(65L))
                .andExpect(jsonPath("$.top[2].count").value(287L));
    }

    @Test
    void getAll() throws Exception {

        List<UserStatisticsResponse> userStatisticsResponseList = List.of(
                new UserStatisticsResponse(2L, Map.of(1L, 150L, 2L, 250L)),
                new UserStatisticsResponse(4L, Map.of(5L, 300L, 7L, 500L))
        );

        given(statisticsService.getAll()).willReturn(userStatisticsResponseList);
        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].bindingIdToCount.1").value(150L))
                .andExpect(jsonPath("$[0].bindingIdToCount.2").value(250L))
                .andExpect(jsonPath("$[1].id").value(4L))
                .andExpect(jsonPath("$[1].bindingIdToCount.5").value(300L))
                .andExpect(jsonPath("$[1].bindingIdToCount.7").value(500L));
    }
}