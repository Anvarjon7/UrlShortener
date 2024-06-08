package de.telran.urlshortener.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.exception.UrlBindingNotFoundException;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Override
    public UrlBinding create(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();
        final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        UrlBinding urlBinding = UrlBinding.builder()
                .baseUrl("http://localhost:8090/")
                .originalUrl(urlBindingCreateRequestDto.originalUrl())
                .expirationDate(urlBindingCreateRequestDto.expirationDate())
                .user(userService.findById(userService.getCurrentUserId()))
                .pathPrefix(urlBindingCreateRequestDto.pathPrefix())
                .uid(NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 5))
                .count(0L)
                .build();

        return urlBindingRepository.save(urlBinding);
    }

    @Override
    public void close(Long bindingId) {
        UrlBinding urlBinding = findById(bindingId).get();
        urlBinding.setIsClosed(true);
        urlBindingRepository.save(urlBinding);
    }

    @Override
    public Optional<UrlBinding> getByUid(String uid) {
        return urlBindingRepository.findByUid(uid);
//                .orElseThrow(() -> new UrlBindingNotFoundException("UrlBinding not found with such uid " + uid));
    }

    @Override
    public Set<UrlBinding> getByUserId(Long userId) {
        return urlBindingRepository.findByUser_Id(userId);
    }

    @Override
    public void delete(Long bindingId) {
        Optional<UrlBinding> urlBinding = findById(bindingId);
        urlBindingRepository.delete(urlBinding.get());
    }

    @Override
    public UrlBinding getByShortUrl(String shortUrl, boolean isRedirect) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlBindingNotFoundException("Not found urlBinding with " + shortUrl));
        if (isRedirect) {
            urlBinding.setCount(urlBinding.getCount() + 1);
            urlBinding = urlBindingRepository.save(urlBinding);
        }

        return urlBinding;
    }

    @Override
    public Optional<UrlBinding> findById(@PathVariable Long id) {
        return urlBindingRepository.findById(id);
    }
}

