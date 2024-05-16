package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.UrlBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;

    @Autowired
    public UrlBindingServiceImpl(UrlBindingRepository urlBindingRepository) {
        this.urlBindingRepository = urlBindingRepository;
    }

    public Optional<UrlBinding> findActualByUid(String uid) {
        return urlBindingRepository.findActualByUid(uid);
    }

    @Override
    public UrlBindingResponseDto createUrlBinding(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {

        return null;
    }

    @Override
    public void closeUrlBinding(Long bindingId) {

    }

    @Override
    public UrlBindingResponseDto getByUid(String uid) {
        return null;
    }

    @Override
    public Set<UrlBindingResponseDto> getByUserId(Long userId) {
        return null;
    }

    @Override
    public void deleteUrlBinding(Long bindingId) {

    }
}

