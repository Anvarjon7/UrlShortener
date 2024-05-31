package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.service.UrlBindingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
//localhost:8080/url/dfsdfsfsdf
public class UrlRedirectController {
    private final UrlBindingService urlBindingService;
    private final Mapper<UrlBinding, UrlBindingResponseDto> mapper;


    @GetMapping("/{url}")
    public ResponseEntity goToUrl(@PathVariable String url) {
        urlBindingService.incrementClickCount(url);
        UrlBindingResponseDto urlBinding = mapper.toDto(urlBindingService.getByShortUrl(url));
        String originalUrl = urlBinding.originalUrl();
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(URI.create("google.com"));
        httpHeaders.setLocation(URI.create(originalUrl));
        return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        //go to db , find by url original url
//        String original = "";
//        return new RedirectView(original);
    }


}
