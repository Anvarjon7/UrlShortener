package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.exception.UserNotFoundException;
import de.telran.urlshortener.mapper.UserMapper;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> findByIdWithSubscriptions(Long id) {
        return userRepository.findByIdWithSubscriptions(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found with id " + id));
    }

    public Optional<User> findByIdWithBindings(Long id) {
        return userRepository.findByIdWithBindings(id);
    }

    public Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id) {
        return userRepository.findByIdWithUrlBindingsAndSubscriptions(id);
    }


    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .role(userRequestDto.getRole())
                .build();
        User saveUser = userRepository.save(user);
        return userMapper.toUserResponseDto(saveUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Not found with id " + id);
        }
        User existingUser = user.get();
        existingUser.setFirstName(userRequestDto.getFirstName());
        existingUser.setLastName(userRequestDto.getLastName());
        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setPassword(userRequestDto.getPassword());
        existingUser.setRole(userRequestDto.getRole());
        User savedUser = userRepository.save(existingUser);
        return userMapper.toUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> user = userRepository.findAll();
        return user.stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getById(@PathVariable Long id) {
        Optional<User> byUserId = userRepository.findById(id);

        UserResponseDto userResponseDto = null;
        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userResponseDto = userMapper.toUserResponseDto(user.get());
            } else {
                throw new EntityNotFoundException("User not found with " + id);
            }
        }
        return userResponseDto;
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with " + email));
        return userMapper.toUserResponseDto(user);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


