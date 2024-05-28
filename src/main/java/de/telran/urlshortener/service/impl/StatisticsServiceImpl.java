package de.telran.urlshortener.service.impl;


import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.StatisticsRepository;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.StatisticsService;
import de.telran.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private UserRepository userRepository;

    private final StatisticsRepository statisticsRepository;

    private final UserService userService;


    @Override
    public UserStatisticsResponse getUserStatistics(Long userId) {
        return new UserStatisticsResponse(userId, userService.findById(userId).getBindings().stream()
                .collect(Collectors.toMap(UrlBinding::getId, UrlBinding::getCount)));
    }

    @Override
    public TopBindingStatisticsResponse getBindingTop(int topParam) {
        return statisticsRepository.getTopUrlBinding(topParam);
    }

    @Override
    public List<UserStatisticsResponse> getAllUserStatistics() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserStatisticsResponse(user.getId(), user.getBindings().stream()
                        .collect(Collectors.toMap(UrlBinding::getId, UrlBinding::getCount))))
                .collect(Collectors.toList());
    }
}
