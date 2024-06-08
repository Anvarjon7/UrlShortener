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
public class UrlRedirectController {

    private final UrlBindingService urlBindingService;

    private final Mapper<UrlBinding, UrlBindingResponseDto> mapper;

    @GetMapping("/{url}")
    public ResponseEntity goToUrl(@PathVariable String url) {
        UrlBinding urlBinding = urlBindingService.getByShortUrl(url, true);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(urlBinding.getOriginalUrl()));
        return new ResponseEntity(httpHeaders, HttpStatus.FOUND);
    }
}