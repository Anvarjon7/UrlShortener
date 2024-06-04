package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

//@ExtendWith
@SpringBootTest
class UrlBindingServiceImplTest {
    @Mock
    private UrlBindingRepository urlBindingRepository;

    @Mock
    private UserService userService;
    @Mock
    private SubscriptionService subscriptionService;

    @Test
    void findActualByUid() {
    }

    @Test
    void create() {
    }

    @Test
    void close() {
    }

    @Test
    void getByUid() {
    }

    @Test
    void getByUserId() {
    }

    @Test
    void delete() {
    }

    @Test
    void getByShortUrl() {
    }

    @Test
    void findById() {
    }
}