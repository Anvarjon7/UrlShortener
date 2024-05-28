package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UrlBindingMapper {

     public UrlBindingResponseDto toUrlBindingResponseDto(UrlBinding urlBinding) {
        return new UrlBindingResponseDto(
                urlBinding.getId(),
                urlBinding.getOriginalUrl(),
                urlBinding.getShort(),
                urlBinding.getCount(),
                urlBinding.getCreationDate(),
                urlBinding.getExpirationDate()
        );
    }

    public Set<UrlBindingResponseDto> toUrlBindingResponseDtoSet(Set<UrlBinding> urlBindings){
        return urlBindings.stream()
                .map(this::toUrlBindingResponseDto)
                .collect(Collectors.toSet());
    }
}
