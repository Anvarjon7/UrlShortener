package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.mapper.UserMapper;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userService.register(userRequestDto);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userService.update(id, userRequestDto);
        UserResponseDto updatedUser = userMapper.toUserResponseDto(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FullUserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        List<FullUserResponseDto> usersResponse = users.stream()
                .map(userMapper::toFullUserResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        Optional<User> userId = userService.getById(id);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(userId.get());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getByEmail(@Email @PathVariable String email) {
        User byEmail = userService.getByEmail(email);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(byEmail);

        return ResponseEntity.ok(userResponseDto);
    }
}
