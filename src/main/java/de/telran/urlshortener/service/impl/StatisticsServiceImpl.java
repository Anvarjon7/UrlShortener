package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private UserRepository userRepository;

    @Autowired
    public StatisticsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserStatisticsResponse getUserStatistics(Long userId) {
        User user = userRepository.findByIdWithBindings(userId).orElseThrow(
                ()->new RuntimeException("Can not find user with id = " + userId) //todo заменить на свой Exception
        );

        return new UserStatisticsResponse(user.getBindings().stream()
                .collect(Collectors.toMap(UrlBinding::getId, UrlBinding::getCount)));
    }

    @Override
    public TopBindingStatisticsResponse getBindingTop(int topParam) {
        return null;
    }
}
