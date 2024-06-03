package de.telran.urlshortener.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.exception.SubscriptionExpiredException;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.SubscriptionService;
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
    private final SubscriptionService subscriptionService;

    public Optional<UrlBinding> findActualByUid(String uid) {
        return urlBindingRepository.findActualByUid(uid);
    }

    @Override
    public UrlBinding create(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();
        final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        if (!subscriptionService.isValid()) {
            throw new SubscriptionExpiredException("Your subscription has expired. Please renew your subscription to continue using the service.");
        }
        UrlBinding urlBinding = UrlBinding.builder()
                .baseUrl("http://localhost:8090/")
                .originalUrl(urlBindingCreateRequestDto.originalUrl())
                .expirationDate(urlBindingCreateRequestDto.expirationDate())
                .user(userService.findById(urlBindingCreateRequestDto.userId()))
                .pathPrefix(urlBindingCreateRequestDto.pathPrefix())
                .uid(NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 5))
                .count(0L)
                .build();

        return urlBindingRepository.save(urlBinding);
    }

    @Override
    public void close(Long bindingId) {
        UrlBinding urlBinding = findById(bindingId);
        urlBinding.setIsClosed(true);
        urlBindingRepository.save(urlBinding);
    }

    @Override
    public UrlBinding getByUid(String uid) {
        return urlBindingRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("UrlBinding not found"));
    }

    @Override
    public Set<UrlBinding> getByUserId(Long userId) {
        return urlBindingRepository.findByUser_Id(userId);
    }

    @Override
    public void delete(Long bindingId) {
        UrlBinding urlBinding = findById(bindingId);
        urlBindingRepository.delete(urlBinding);
    }

    @Override
    public UrlBinding getByShortUrl(String shortUrl, boolean isRedirect) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Not found urlBinding with " + id));
        if (isRedirect) {
            urlBinding.setCount(urlBinding.getCount() + 1);
            urlBinding = urlBindingRepository.save(urlBinding);
        }
        if (!subscriptionService.isValid()) {
            throw new SubscriptionExpiredException("Your subscription has expired. Please renew your subscription to continue using the service.");
        }

        return urlBinding;
    }

    @Override
    public UrlBinding findById(Long id) {
        return urlBindingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found urlBinding with " + id));// #TODO create own Exception
    }
}

