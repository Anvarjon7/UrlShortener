package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.TopRecord;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.StatisticsRepository;
import de.telran.urlshortener.service.UserService;
import de.telran.urlshortener.service.impl.StatisticsServiceImpl;
import de.telran.urlshortener.testData.TestData;
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
import static org.mockito.Mockito.*;

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
                TestData.URLBINDING1
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
        int topParam = 5;
        List<TopRecord> bindings = List.of(
                new TopRecord(1L,1L,100L),
                new TopRecord(2L,2L,200L)
        );

        TopBindingStatisticsResponse expectedResponse = new TopBindingStatisticsResponse(bindings);

        when(statisticsRepository.getTopUrlBinding(topParam)).thenReturn(expectedResponse);

        TopBindingStatisticsResponse actualResponse = statisticsService.getBindingTop(topParam);
        assertEquals(expectedResponse, actualResponse);
        verify(statisticsRepository,times(1)).getTopUrlBinding(topParam);
    }

    @Test
    void getAll() {
        List<User> users = List.of(
                TestData.USER1,
                TestData.USER2
        );

        when(userService.getAll()).thenReturn(users);

        List<UserStatisticsResponse> expectedResponse = users.stream()
                .map(user -> new UserStatisticsResponse(user.getId(),user.getBindings().stream()
                        .collect(Collectors.toMap(UrlBinding::getId,UrlBinding::getCount))))
                .toList();

        List<UserStatisticsResponse> actualResponse = statisticsService.getAll();
        assertEquals(expectedResponse, actualResponse);
        verify(userService,times(1)).getAll();
    }

    @Test
    void getByCurrentUser() {
        Long userId = 1L;
        List<UrlBinding> bindings = List.of(
                TestData.URLBINDING1
        );
        User user = new User();
        user.setId(userId);
        user.setBindings(new HashSet<>(bindings));

        when(userService.getCurrentUserId()).thenReturn(userId);
        when(userService.findById(userId)).thenReturn(user);

        Map<Long, Long> expected = bindings.stream()
                .collect(Collectors.toMap(UrlBinding::getId, UrlBinding::getCount));

        UserStatisticsResponse expectedResponse = new UserStatisticsResponse(userId, expected);
        UserStatisticsResponse actualResponse = statisticsService.getByCurrentUser();

        assertEquals(expectedResponse, actualResponse);

        verify(userService,times(1)).getCurrentUserId();
        verify(userService,times(1)).findById(userId);
    }
}