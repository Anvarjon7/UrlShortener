package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
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
    private final Mapper<UrlBinding, UrlBindingResponseDto> mapper;
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UrlBindingResponseDto> create(@RequestBody @Valid UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(urlBindingService.create(urlBindingCreateRequestDto)));
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

    @GetMapping()
    public ResponseEntity<Set<UrlBindingResponseDto>> getByUserId() {
          return ResponseEntity.ok(mapper.toDtoSet(urlBindingService.getByUserId(userService.getCurrentUserId())));
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UrlBindingResponseDto> getByUid(@PathVariable String uid) {
        UrlBindingResponseDto urlBindingResponseDto = mapper.toDto(urlBindingService.getByUid(uid));
        return new ResponseEntity<>(urlBindingResponseDto, HttpStatus.OK);
    }

    @GetMapping("/shortUrl/{shortUrl}")
    public ResponseEntity<UrlBindingResponseDto> getByShortUrl(@PathVariable String shortUrl) {
        UrlBindingResponseDto urlBindingResponseDto = mapper.toDto(urlBindingService.getByShortUrl(shortUrl));
        return new ResponseEntity<>(urlBindingResponseDto, HttpStatus.OK);
    }
}
