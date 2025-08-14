package ch.noseryoung.pixelcollectbackend.domain.account;

import ch.noseryoung.pixelcollectbackend.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.*;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Length(max = 254, message = "The email can't be longer than 254 characters")
    private String email;

    @Column(nullable = false, length = 254)
    @NotBlank(message = "Country cannot be empty")
    @Length(min = 8, max = 254, message = "The password must be between 8 and 254 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character"
    )
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Account balance cannot be null")
    @PositiveOrZero(message = "Balance cant be negative")
    @Max(value = Integer.MAX_VALUE, message = "Balance exceeds the maximum allowed value")
    private Integer balance;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> ownedProducts = new ArrayList<>();
}