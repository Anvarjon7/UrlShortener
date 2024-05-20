package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByIdWithSubscriptions(Long id) {
        return userRepository.findByIdWithSubscriptions(id);
    }

    public Optional<User> findByIdWithBindings(Long id) {
        return userRepository.findByIdWithBindings(id);
    }

    public Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id) {
        return userRepository.findByIdWithUrlBindingsAndSubscriptions(id);
    }


    @Override
    public User registerUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .role(userRequestDto.getRole())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserRequestDto userRequestDto) {
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
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


