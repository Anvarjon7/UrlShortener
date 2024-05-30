package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.exception.UserNotFoundException;
import de.telran.urlshortener.mapper.UserMapper;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
//        this.userRepository = userRepository;
//    }

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
    public User register(UserRequestDto userRequestDto) {
        User user = User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole())
                .build();
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User update(Long id, UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findById(id);

        User existingUser = user.get();
        existingUser.setFirstName(userRequestDto.getFirstName());
        existingUser.setLastName(userRequestDto.getLastName());
        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        existingUser.setRole(userRequestDto.getRole());
        User savedUser = userRepository.save(existingUser);
        return savedUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> getById(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);

        return byId;
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with " + email)); // #ToDo create own Exception
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}


