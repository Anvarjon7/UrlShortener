package de.telran.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.telran.urlshortener.model.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Invalid firstName : Empty name")
    @Size(max = 300)
    private String firstName;

    @NotBlank(message = "Invalid lastName : Empty lastName")
    @Size(max = 300)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,30}$",
            message = "Password must be between 8 and 30 characters, and include at least one digit, one lowercase letter, one uppercase letter, and one special character (!@#$%^&*)"
    )
    private String password;

    private Role role;
}
