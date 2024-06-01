package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UrlBindingMapper implements Mapper<UrlBinding,UrlBindingResponseDto>{
    @Override
    public UrlBindingResponseDto toDto(UrlBinding urlBinding) {
        return new UrlBindingResponseDto(
                urlBinding.getId(),
                urlBinding.getOriginalUrl(),
                urlBinding.getShort(),
                urlBinding.getCount(),
                urlBinding.getCreationDate(),
                urlBinding.getExpirationDate()
        );
    }

    @Override
    public Set<UrlBindingResponseDto> toDtoSet(Set<UrlBinding> urlBindings) {
        return urlBindings.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UrlBinding toEntity(UrlBindingResponseDto urlBindingResponseDto) {
        return null;
    }

    @Override
    public Set<UrlBinding> toEntitySet(Set<UrlBindingResponseDto> urlBindingResponseDtos) {
        return null;
    }
}
