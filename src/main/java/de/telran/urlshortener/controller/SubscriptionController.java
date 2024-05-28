package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.mapper.SubscriptionMapper;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @PostMapping("/create/{userId}")
    public ResponseEntity<SubscriptionResponseDto> create(@PathVariable Long userId) {
        Subscription subscription = subscriptionService.create(userId);
        SubscriptionResponseDto subscriptionResponseDto = subscriptionMapper.toSubscriptionResponseDto(subscription);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionResponseDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> getById(@PathVariable Long id) {
        Optional<Subscription> subscription = subscriptionService.getById(id);
        SubscriptionResponseDto subscriptionResponseDto = subscriptionMapper.toSubscriptionResponseDto(subscription.get());
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionResponseDto);
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
        Set<Subscription> subscriptions = subscriptionService.getByUserId(userId);
        Set<SubscriptionResponseDto> subscriptionResponseDtos = subscriptionMapper.toSubscriptionResponseDtoSet(subscriptions);

        return ResponseEntity.ok(subscriptionResponseDtos);
    }

}



