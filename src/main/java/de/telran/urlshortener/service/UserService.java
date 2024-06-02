package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(UserRequestDto userRequestDto);

    User update(Long id, UserRequestDto userRequestDto);

    List<User> getAll();

    Optional<User> getById(Long id);

    User getByEmail(String email);

    void delete(Long id);

    Optional<User> findByIdWithBindings(Long id);

    Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id);

    Optional<User> findByIdWithSubscriptions(Long id);

    User findById(Long id);
    Long getCurrentUserId();
}
