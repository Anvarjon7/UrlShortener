package de.telran.urlshortener.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.exception.UrlNotFoundException;
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

@Service
@RequiredArgsConstructor
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;
    //private final UserRepository userRepository;

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

        return urlBindingRepository.save(urlBinding);
    }

    @Override
    public void close(Long bindingId) {
        UrlBinding urlBinding = urlBindingRepository.findById(bindingId).get();
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
        urlBindingRepository.deleteById(bindingId);
    }

    @Override
    public UrlBinding getByShortUrl(String shortUrl) {

        return urlBindingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Not found url with such" + shortUrl));
    }

    @Override
    public UrlBinding findById(Long id) {

        return urlBindingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found urlBinding with " + id));// #TODO create own Exception
    }

    @Override
    public void incrementClickCount(String url) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl(url)
                .orElseThrow(() -> new UrlNotFoundException("Not found url with " + url));

        urlBinding.setCount(urlBinding.getCount() + 1);
        urlBindingRepository.save(urlBinding);
    }
}

