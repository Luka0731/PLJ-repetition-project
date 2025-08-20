package ch.noseryoung.pixelcollectbackend.domain.account;

import ch.noseryoung.pixelcollectbackend.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash; // todo: save the password as a hash

    @Column(nullable = false)
    @Check(constraints = "balance >= 0 AND balance <= 2147483647")
//    @NotNull(message = "Account balance cannot be null")
//    @PositiveOrZero(message = "Balance cant be negative")
//    @Max(value = Integer.MAX_VALUE, message = "Balance exceeds the maximum allowed value")
    private Integer balance;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true) // unique Index, kann NULL sein
    private List<Product> ownedProducts = new ArrayList<>();

    @Column(name = "session_key", unique = true) // unique Index, kann NULL sein
    private UUID sessionKey;

    @Column(name = "session_expires_at") // unique Index, kann NULL sein
    private Instant sessionExpiresAt;
}