package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;


    @PostMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> createSubscription(Long id) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.create(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> getById(@Valid @RequestBody Long id) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getById(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @PatchMapping("/{id}/paid")
    public ResponseEntity<SubscriptionResponseDto> setSubscriptionPaidStatus(@PathVariable Long id) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.setPaidStatus(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubscription(@PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<SubscriptionResponseDto>> getByUserId(@PathVariable Long userId) {
        Set<SubscriptionResponseDto> subscriptionResponseDto = subscriptionService.getByUserId(userId);
        return ResponseEntity.ok(subscriptionResponseDto);
    }
}



