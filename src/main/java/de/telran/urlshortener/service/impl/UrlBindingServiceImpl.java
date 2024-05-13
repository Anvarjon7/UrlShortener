package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.UrlBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}

