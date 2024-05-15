package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.model.entity.user.Role;
import lombok.Builder;

import java.util.Set;

@Builder
/*@NoArgsConstructor
@AllArgsConstructor*/
public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        Role role,
        Set<Subscription> subscriptions,
        Set<UrlBinding> bindings) {

}
