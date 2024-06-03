package de.telran.urlshortener.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.exception.UrlBindingNotFoundException;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;

    private final UserService userService;

    public Optional<UrlBinding> findActualByUid(String uid) {
        return urlBindingRepository.findActualByUid(uid);
    }

    @Override
    public UrlBinding create(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();
        final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        Long userId = urlBindingCreateRequestDto.userId();
        User user = userService.findById(userId);
        UrlBinding urlBinding = UrlBinding.builder()
                .baseUrl("http://localhost:8090/")
                .originalUrl(urlBindingCreateRequestDto.originalUrl())
                .expirationDate(urlBindingCreateRequestDto.expirationDate())
                .user(user)
                .pathPrefix(urlBindingCreateRequestDto.pathPrefix())
                .uid(NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 5))
                .count(0L)
                .build();

        UrlBinding saveUrlBinding = urlBindingRepository.save(urlBinding);
        return saveUrlBinding;
    }

    @Override
    public void close(Long bindingId) {
        UrlBinding urlBinding = urlBindingRepository.findById(bindingId).get();
        urlBinding.setIsClosed(true);
        urlBindingRepository.save(urlBinding);
    }

    @Override
    public UrlBinding getByUid(String uid) {
        UrlBinding urlBinding = urlBindingRepository.findByUid(uid)
                .orElseThrow(() -> new UrlBindingNotFoundException("UrlBinding not found with such " + uid));
        return urlBinding;
    }

    @Override
    public Set<UrlBinding> getByUserId(Long userId) {
        Set<UrlBinding> urlBindings = urlBindingRepository.findByUser_Id(userId);
        return urlBindings;
    }

    @Override
    public void delete(Long bindingId) {
        urlBindingRepository.deleteById(bindingId);
    }

    @Override
    public UrlBinding getByShortUrl(String shortUrl) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlBindingNotFoundException("Not found urlBinding with " + shortUrl));

        return urlBinding;
    }

    @Override
    public UrlBinding findById(Long id) {

        return urlBindingRepository.findById(id).orElseThrow(() -> new UrlBindingNotFoundException("Not found urlBinding with " + id));// #TODO create own Exception
    }

    @Override
    public void incrementClickCount(String uid) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl(uid)
                .orElseThrow(() -> new UrlBindingNotFoundException("Not found urlBinding with " + uid));

        urlBinding.setCount(urlBinding.getCount() + 1);
        urlBindingRepository.save(urlBinding);
    }
}

