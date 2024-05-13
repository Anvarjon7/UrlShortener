package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

@Component
    public class SubscriptionMapper {

        public SubscriptionResponseDto toSubscriptionResponseDto(Subscription subscription) {
        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto();
        subscriptionResponseDto.setId(subscription.getId());
        subscriptionResponseDto.setUser(subscription.getUser());
        subscriptionResponseDto.setStatus(subscription.getStatus());
        subscriptionResponseDto.setExpirationDate(subscription.getExpirationDate());
        subscriptionResponseDto.setCreationDate(subscription.getCreationDate());
        return subscriptionResponseDto;
        }
}
