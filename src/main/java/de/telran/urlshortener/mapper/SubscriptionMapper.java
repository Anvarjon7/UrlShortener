package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SubscriptionMapper implements Mapper<Subscription, SubscriptionResponseDto> {

    @Override
    public SubscriptionResponseDto toDto(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getCreationDate(),
                subscription.getExpirationDate(),
                subscription.getStatus()
        );
    }

    @Override
    public Set<SubscriptionResponseDto> toDtoSet(Set<Subscription> subscriptions) {
        return null;
    }

    @Override
    public Subscription toEntity(SubscriptionResponseDto subscriptionResponseDto) {
        return null;
    }

    @Override
    public Set<Subscription> toEntitySet(Set<SubscriptionResponseDto> subscriptionResponseDtos) {
        return null;
    }
}
