package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.service.StatisticsService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/getStatistics/{userId}")
    public ResponseEntity<UserStatisticsResponse> getStatistics(@PathVariable Long userId) {
        UserStatisticsResponse userStatistics = statisticsService.getUserStatistics(userId);
        return ResponseEntity.ok(userStatistics);
    }

    @GetMapping("/top/{top}")
    public ResponseEntity<TopBindingStatisticsResponse> getTopStatistics(@PathVariable int top) {
        TopBindingStatisticsResponse topBindingStatistics = statisticsService.getBindingTop(top);
        return ResponseEntity.ok(topBindingStatistics);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserStatisticsResponse>> getUserStatistics() {
        List<UserStatisticsResponse> userStatistics = statisticsService.getAllUserStatistics();
        return ResponseEntity.ok(userStatistics);
    }

}
