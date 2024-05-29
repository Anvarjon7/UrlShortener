package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.UrlBindingMapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.service.UrlBindingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

//tinyurl.com/sdfsdfdf
//localhost:8080/sdfsdfsgsdf

@RestController
@RequestMapping(value = "/api/url-bindings")
@RequiredArgsConstructor
public class UrlBindingController {

    private final UrlBindingService urlBindingService;
    private final UrlBindingMapper urlBindingMapper;

    @PostMapping("/create")
    public ResponseEntity<UrlBindingResponseDto> create(@RequestBody @Valid UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        UrlBinding urlBinding = urlBindingService.create(urlBindingCreateRequestDto);
        UrlBindingResponseDto urlBindingResponseDto = urlBindingMapper.toUrlBindingResponseDto(urlBinding);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlBindingResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity close(@PathVariable Long id) {
        urlBindingService.close(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        urlBindingService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Set<UrlBindingResponseDto>> getByUserId(@PathVariable Long id) {
        Set<UrlBinding> urlBindings = urlBindingService.getByUserId(id);
        Set<UrlBindingResponseDto> urlBindingResponseDtos = urlBindingMapper.toUrlBindingResponseDtoSet(urlBindings);
        return ResponseEntity.ok(urlBindingResponseDtos);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UrlBindingResponseDto> getByUid(@PathVariable String uid) {
        UrlBinding urlBinding = urlBindingService.getByUid(uid);
        UrlBindingResponseDto urlBindingResponseDto = urlBindingMapper.toUrlBindingResponseDto(urlBinding);
        return new ResponseEntity<>(urlBindingResponseDto, HttpStatus.OK);
    }

    @GetMapping("/shortUrl/{shortUrl}")
    public ResponseEntity<UrlBindingResponseDto> getByShortUrl(@PathVariable String shortUrl) {
        UrlBinding urlBinding = urlBindingService.getByShortUrl(shortUrl);
        UrlBindingResponseDto urlBindingResponseDto = urlBindingMapper.toUrlBindingResponseDto(urlBinding);

        return new ResponseEntity<>(urlBindingResponseDto, HttpStatus.OK);
    }
}
