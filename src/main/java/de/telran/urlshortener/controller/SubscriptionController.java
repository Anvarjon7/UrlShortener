package de.telran.urlshortener.controller;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.mapper.Mapper;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final Mapper<Subscription, SubscriptionResponseDto> mapper;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponseDto> create() {
        Long userId = userService.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(subscriptionService.create(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(subscriptionService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> setPaidStatus(@PathVariable Long id) {
        subscriptionService.setPaidStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<Set<SubscriptionResponseDto>> getByUserId() {
        Long userId = userService.getCurrentUserId();
        return ResponseEntity.ok(subscriptionService.getByUserId(userId)
                .stream().map(mapper::toDto)
                .collect(Collectors.toSet()));
    }

}



