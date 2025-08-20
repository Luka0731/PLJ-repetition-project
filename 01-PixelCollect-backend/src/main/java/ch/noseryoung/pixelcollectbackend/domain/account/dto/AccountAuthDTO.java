package ch.noseryoung.pixelcollectbackend.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record AccountAuthDTO(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        @Length(max = 254, message = "The email can't be longer than 254 characters")
        String email,

        @NotBlank(message = "Country cannot be empty")
        @Length(min = 8, max = 254, message = "The password must be between 8 and 254 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_\\-])[A-Za-z\\d@$!%*?&_\\-]+$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character"
        )
        String password
) {}