package de.telran.urlshortener.mapper;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.stereotype.Component;

@Component
public class UrlBindingMapper {

    public de.telran.urlshortener.dto.UrlBindingResponseDto toUrlBindingResponseDto(UrlBinding urlBinding) {
        return new de.telran.urlshortener.dto.UrlBindingResponseDto(
                urlBinding.getId(),
                urlBinding.getOriginalUrl(),
                urlBinding.getBaseUrl(),
                urlBinding.getPathPrefix(),
                urlBinding.getOriginalUrl(),
                urlBinding.getCount(),
                urlBinding.getCreationDate(),
                urlBinding.getExpirationDate(),
                urlBinding.getIsClosed(),
                urlBinding.getUser()
        );
    }
}
