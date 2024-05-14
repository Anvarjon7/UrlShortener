package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

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
}
