package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<SubscriptionResponseDto> create(@PathVariable Long userId) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.create(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> getById(@PathVariable Long id) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getById(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity setPaidStatus(@PathVariable Long id) {
        subscriptionService.setPaidStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<SubscriptionResponseDto>> getByUserId(@PathVariable Long userId) {
        Set<SubscriptionResponseDto> subscriptions = subscriptionService.getByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }
}



