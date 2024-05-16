package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.stereotype.Component;

@Component
public class UrlBindingMapper {

     public UrlBindingResponseDto toUrlBindingResponseDto(UrlBinding urlBinding) {
        return new UrlBindingResponseDto(
                urlBinding.getId(),
                urlBinding.getOriginalUrl(),
                urlBinding.getBaseUrl(),
                urlBinding.getCount(),
                urlBinding.getCreationDate(),
                urlBinding.getExpirationDate()
        );
    }
}
