package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
import de.telran.urlshortener.service.impl.UrlBindingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static de.telran.urlshortener.serviceTest.ServiceTestData.USER1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UrlBindingServiceImplTest {
    @Mock private UrlBindingRepository urlBindingRepository;
    private UrlBindingService urlBindingService;
    private UrlBindingCreateRequestDto urlBindingCreateRequestDto;
    private UrlBinding urlBinding;
    @Mock
    private UserService userService;
    @Mock
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        urlBindingService = new UrlBindingServiceImpl(urlBindingRepository,userService,subscriptionService);


        urlBindingCreateRequestDto = new UrlBindingCreateRequestDto(
                "https://google.com",
                "abc",
                LocalDate.now());

        urlBinding = UrlBinding.builder()
                .id(1L)
                .originalUrl("https://google.com")
                .pathPrefix("abc")
                .user(USER1)
                .build();

    }
    @Test
    void findActualByUid() {
        given(urlBindingRepository.findActualByUid("Uid")).willReturn(Optional.of(urlBinding));

        Optional<UrlBinding> foundUrlBinding = Optional.of(urlBindingService.getByUid("Uid"));
        assertThat(foundUrlBinding).isPresent().isEqualTo(Optional.of(urlBinding));
    }

    @Test
    void create() {
//        urlBindingService.create(urlBindingCreateRequestDto);
//
//        ArgumentCaptor<UrlBinding> urlBindingArgumentCaptor =
//                ArgumentCaptor.forClass(UrlBinding.class);
//
//        verify(urlBindingRepository)
//                .save(urlBindingArgumentCaptor.capture());
//
//        UrlBinding capturedUrlBinding = urlBindingArgumentCaptor.getValue();
//
//        assertThat(capturedUrlBinding.getOriginalUrl().equals((urlBindingCreateRequestDto.originalUrl());
//        assertThat(capturedUrlBinding.getExpirationDate().isEqual(urlBindingCreateRequestDto.expirationDate());
//        assertThat(capturedUrlBinding.getPathPrefix().equals(urlBindingCreateRequestDto.pathPrefix();


    }

    @Test
    void close() {
    }

    @Test
    void getByUid() {
        urlBindingService.getByUid("Uid");

        verify(urlBindingRepository).findByUid("Uid");
    }

    @Test
    void getByUserId() {
//        Long userId = 1L;
//        given(urlBindingRepository.findByUser_Id(userId)).willReturn((Set<UrlBinding>));
//        assertThat(actualUrlBindings).hasSameSizeAs(expectedUrlBindings);
//        assertThat(actualUrlBindings).containsExactlyInAnyOrderElementsOf(expectedUrlBindings);
//        //        given(urlBindingRepository.findByShortUrl(shortUrl)).willReturn(Optional.of(urlBinding));
//
//        UrlBinding foundUrlBinding = urlBindingService.getByShortUrl(shortUrl,false);
//        assertThat(foundUrlBinding).isNotNull();
//        assertThat(foundUrlBinding).isEqualTo(urlBinding);
//        verify(urlBindingRepository).findByShortUrl(shortUrl);
    }

    @Test
    void delete() {
    }

    @Test
    void getByShortUrl() {
        String shortUrl = "ShortUrl";
        given(urlBindingRepository.findByShortUrl(shortUrl)).willReturn(Optional.of(urlBinding));

        UrlBinding foundUrlBinding = urlBindingService.getByShortUrl(shortUrl,false);
        assertThat(foundUrlBinding).isNotNull();
        assertThat(foundUrlBinding).isEqualTo(urlBinding);
        verify(urlBindingRepository).findByShortUrl(shortUrl);
    }

    @Test
    void findById() {
        urlBindingService.findById(1L);

        verify(urlBindingRepository).findById(1L);
    }
}