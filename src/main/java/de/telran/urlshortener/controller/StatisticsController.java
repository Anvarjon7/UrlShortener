package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserStatisticsResponse> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(statisticsService.getByUser(userId));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/current")
    public ResponseEntity<UserStatisticsResponse> getByCurrentUser() {
        return ResponseEntity.ok(statisticsService.getByCurrentUser());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top/{top}")
    public ResponseEntity<TopBindingStatisticsResponse> getTopStatistics(@PathVariable int top) {
        TopBindingStatisticsResponse topBindingStatistics = statisticsService.getBindingTop(top);
        return ResponseEntity.ok(topBindingStatistics);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserStatisticsResponse>> getAll() {
        return ResponseEntity.ok(statisticsService.getAll());
    }
}
