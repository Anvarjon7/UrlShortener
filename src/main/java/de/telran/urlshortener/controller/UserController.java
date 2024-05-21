package de.telran.urlshortener.controller;


import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("api/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User userResponseDto = userService.registerUser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        User userResponseDto = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<FullUserResponseDto>> getAllUsers() {
        List<FullUserResponseDto> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/by-email")
    public ResponseEntity<FullUserResponseDto> getByEmail(@RequestParam @Email String email) {
        FullUserResponseDto userResponseDto = userService.getByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }
}
