package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.mapper.UrlBindingMapper;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UrlBindingRepository;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UrlBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UrlBindingServiceImpl implements UrlBindingService {

    private final UrlBindingRepository urlBindingRepository;
    private final UrlBindingMapper urlBindingMapper;
    private final UserRepository userRepository;
    @Autowired
    public UrlBindingServiceImpl(UrlBindingRepository urlBindingRepository, UrlBindingMapper urlBindingMapper, UserRepository userRepository) {
        this.urlBindingRepository = urlBindingRepository;
        this.urlBindingMapper = urlBindingMapper;
        this.userRepository = userRepository;
    }

    public Optional<UrlBinding> findActualByUid(String uid) {
        return urlBindingRepository.findActualByUid(uid);
    }

    @Override
    public UrlBindingResponseDto createUrlBinding(UrlBindingCreateRequestDto urlBindingCreateRequestDto) {

        User user = userRepository.findById(urlBindingCreateRequestDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found")); //todo own Exception
        UrlBinding urlBinding = UrlBinding.builder()
                .baseUrl("baseUrl")
                .originalUrl(urlBindingCreateRequestDto.originalUrl())
                .expirationDate(urlBindingCreateRequestDto.expirationDate())
                .user(user)
                .pathPrefix("pathPref")
                .uid("")
                .build();

        UrlBinding saveUrlBinding = urlBindingRepository.save(urlBinding);
        user.addBinding(urlBinding);
        return urlBindingMapper.toUrlBindingResponseDto(saveUrlBinding);
    }

    @Override
    public void closeUrlBinding(Long bindingId) {
        UrlBinding urlBinding = urlBindingRepository.findById(bindingId)
                .orElseThrow(() -> new RuntimeException("UrlBinding not found"));
        urlBinding.setIsClosed(true);
        UrlBinding saveUrlBinding = urlBindingRepository.save(urlBinding);
    }

    @Override
    public UrlBindingResponseDto getByUid(String uid) {
        UrlBinding urlBinding = urlBindingRepository.findActualByUid(uid)
                .orElseThrow(() -> new RuntimeException("UrlBinding not found"));
        return urlBindingMapper.toUrlBindingResponseDto(urlBinding);
    }

    @Override
    public Set<UrlBindingResponseDto> getByUserId(Long userId) {
        Set<UrlBinding> urlBindings = urlBindingRepository.findByIdWithUser(userId);
        return urlBindingMapper.toUrlBindingResponseDtoSet(urlBindings);
    }

    @Override
    public void deleteUrlBinding(Long bindingId) {
        urlBindingRepository.deleteById(bindingId);
    }
}

