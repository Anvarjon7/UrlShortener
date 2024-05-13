package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Invalid firstName : Empty name")
    private String firstName;

    @NotBlank(message = "Invalid lastName : Empty lastName")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 6,max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    private Role role;
}
