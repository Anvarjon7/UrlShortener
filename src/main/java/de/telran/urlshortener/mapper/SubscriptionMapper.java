package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SubscriptionMapper {

    public SubscriptionResponseDto toSubscriptionResponseDto(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getCreationDate(),
                subscription.getExpirationDate(),
                subscription.getStatus(),
                subscription.getUser()
        );
    }
    public Set<SubscriptionResponseDto> toSubscriptionResponseDtoSet(Set<Subscription> subscriptions){
        return subscriptions.stream()
                .map(this::toSubscriptionResponseDto)
                .collect(Collectors.toSet());
    }
}
