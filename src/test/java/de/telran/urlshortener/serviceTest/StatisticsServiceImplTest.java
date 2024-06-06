package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.StatisticsRepository;
import de.telran.urlshortener.service.UserService;
import de.telran.urlshortener.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private StatisticsServiceImpl statisticsService;


    @Test
    void getByUser() {
        Long userId = 1L;

        List<UrlBinding> bindings = List.of(
                ServiceTestData.URLBINDING1
        );

        User user = new User();
        user.setId(userId);
        user.setBindings(new HashSet<>(bindings));

        when(userService.findById(userId)).thenReturn(user);

        Map<Long, Long> expected = bindings.stream()
                .collect(Collectors.toMap(UrlBinding::getId, UrlBinding::getCount));

        UserStatisticsResponse expectedResponse = new UserStatisticsResponse(userId, expected);
        UserStatisticsResponse actualResponse = statisticsService.getByUser(userId);

        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void getBindingTop() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByCurrentUser() {
    }
}