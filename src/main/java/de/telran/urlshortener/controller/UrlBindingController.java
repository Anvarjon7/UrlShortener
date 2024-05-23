package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.service.UrlBindingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/urlbinding")
@RequiredArgsConstructor
public class UrlBindingController {

    private final UrlBindingService urlBindingService;


    @PostMapping("/create")
    public ResponseEntity<UrlBindingResponseDto> createUrlBinding(@RequestBody @Valid UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        UrlBindingResponseDto urlBindingResponseDto = urlBindingService.createUrlBinding(urlBindingCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlBindingResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity closeUrlBinding(@PathVariable Long id) {
        urlBindingService.closeUrlBinding(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUrlBinding(@PathVariable Long id) {
        urlBindingService.deleteUrlBinding(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getbyuserid/{id}")
    public ResponseEntity<Set<UrlBindingResponseDto>> getByUserId(@PathVariable Long id) {
        Set<UrlBindingResponseDto> urlBinding = urlBindingService.getByUserId(id);
        return ResponseEntity.ok(urlBinding);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UrlBindingResponseDto> getByUid(@PathVariable String uid) {
        UrlBindingResponseDto urlBindingResponseDto = urlBindingService.getByUid(uid);
        return new ResponseEntity<>(urlBindingResponseDto, HttpStatus.OK);
    }
}
