package ch.noseryoung.pixelcollectbackend.domain.giftcard;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "gift_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gift_card_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 16)
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9-]+$")
    private String code;

    @Column(nullable = false)
    @NotNull(message = "Value cannot be null")
    @PositiveOrZero(message = "Value cant be negative")
    @Max(value = Integer.MAX_VALUE, message = "Value exceeds the maximum allowed value")
    private Integer value;

    @Column(nullable = false)
    @NotNull(message = "Redeemed status cannot be null")
    private Boolean isRedeemed;
}