package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.SubscriptionDto;
import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController_dzxd {

    private final SubscriptionService subscriptionService;


    @PostMapping("/create/{userid}")
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@PathVariable Long userid) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.create(userid);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity setPaidStatus(@PathVariable Long id) {
        subscriptionService.setPaidStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubscription(@PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getbyuserid/{id}")
    public ResponseEntity<Set<SubscriptionResponseDto>> getByUserId(@PathVariable Long id) {
        Set<SubscriptionResponseDto> subscriptions = subscriptionService.getByUserId(id);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<SubscriptionResponseDto> getById(@PathVariable Long id) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getById(id);
        return ResponseEntity.ok(subscriptionResponseDto);

    }
}
