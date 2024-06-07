package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.security.JwtService;
import de.telran.urlshortener.service.UrlBindingService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UrlRedirectController.class)
@ExtendWith(MockitoExtension.class)
class UrlRedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UrlBindingService urlBindingService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private Mapper<UrlBinding, UrlBindingResponseDto> mapper;

    @Test
    @SneakyThrows
    @WithMockUser
    void goToUrl() {
        String shortUrl = "shortUrl";
        String originalUrl = "http://example.com";
        UrlBinding urlBinding = new UrlBinding();
        urlBinding.setBaseUrl("http://short.url/");
        urlBinding.setPathPrefix("/");
        urlBinding.setUid(shortUrl);
        urlBinding.setOriginalUrl(originalUrl);

        when(urlBindingService.getByShortUrl(shortUrl, true)).thenReturn(urlBinding);

        mockMvc.perform(MockMvcRequestBuilders.get("/{url}", shortUrl))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, originalUrl));
    }
}