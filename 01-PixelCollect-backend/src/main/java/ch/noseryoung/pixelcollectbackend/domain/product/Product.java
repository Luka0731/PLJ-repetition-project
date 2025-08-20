package ch.noseryoung.pixelcollectbackend.domain.product;

import ch.noseryoung.pixelcollectbackend.domain.account.Account;
import ch.noseryoung.pixelcollectbackend.domain.image.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    @NotBlank(message = "Name cannot be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9 .,;:!?'\"\\-_%&$€#()\\[\\]{}+/\\n]+$",
            message = "Name may only contain letters, numbers, spaces, and common punctuation. Symbols like < and > are not allowed."
    )
    @Length(max = 254, message = "The name can't be longer than 254 characters")
    private String name;

    @Column(nullable = false, unique = true, length = 4000)
    @NotBlank(message = "Description cannot be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9 .,;:!?'\"\\-_%&$€#()\\[\\]{}+/\\n]+$",
            message = "Description may only contain letters, numbers, spaces, and common punctuation. Symbols like < and > are not allowed."
    )
    @Length(max = 4000, message = "The description can't be longer than 4000 characters")
    private String description;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_image", referencedColumnName = "image_id")
    private Image image;

    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price cant be negative")
    @Max(value = Integer.MAX_VALUE, message = "Price exceeds the maximum allowed value")
    private Integer price;

    @Column(nullable = false, name = "rarity")
    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Account owner;
}