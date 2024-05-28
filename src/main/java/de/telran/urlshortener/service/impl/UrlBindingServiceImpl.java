package de.telran.urlshortener.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UrlBindingService;
import de.telran.urlshortener.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public Optional<UrlBinding> findActualByUid(String uid) {
        return urlBindingRepository.findActualByUid(uid);
    }

    @Override
    public UrlBinding create(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {
        Long userId = urlBindingCreateRequestDto.userId();
        User user = userService.findById(userId);
        UrlBinding urlBinding = UrlBinding.builder()
                .baseUrl("baseUrl")
                .originalUrl(urlBindingCreateRequestDto.originalUrl())
                .expirationDate(urlBindingCreateRequestDto.expirationDate())
                .user(user)
                .pathPrefix("pathPref")
                .uid(NanoIdUtils.randomNanoId())
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
                .orElseThrow(() -> new RuntimeException("UrlBinding not found"));
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
                .orElseThrow(() -> new EntityNotFoundException("Not found urlBinding with " + id));

        return urlBinding;
    }

    @Override
    public UrlBinding findById(Long id) {

        return urlBindingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found urlBinding with " + id));// #TODO create own Exception
    }
}

