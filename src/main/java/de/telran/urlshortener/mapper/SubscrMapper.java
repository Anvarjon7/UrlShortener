package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscrMapper implements Mapper<Subscription, SubscriptionResponseDto> {

    @Override
    public SubscriptionResponseDto toDto(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getCreationDate(),
                subscription.getExpirationDate(),
                subscription.getStatus(),
                subscription.getUser()
        );
    }

    @Override
    public Subscription toEntity(SubscriptionResponseDto subscriptionResponseDto) {
        return null;
    }
}
