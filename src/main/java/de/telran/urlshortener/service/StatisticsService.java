package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;

import java.util.List;

public interface StatisticsService {

    UserStatisticsResponse getUserStatistics(Long userId);

    TopBindingStatisticsResponse getBindingTop(int topParam);

    List<UserStatisticsResponse> getAllUserStatistics();
}
