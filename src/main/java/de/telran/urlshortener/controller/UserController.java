package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.mapper.UserMapper;
import de.telran.urlshortener.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(userService.register(userRequestDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {

        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.update(id, userRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FullUserResponseDto>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll().stream().map(mapper::toFullUserResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.getById(id).get()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getByEmail(@Email @PathVariable String email) {

        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.getByEmail(email)));
    }
}
