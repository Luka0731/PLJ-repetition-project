package ch.noseryoung.pixelcollectbackend.domain.image;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    @NotBlank(message = "Name cannot be empty")
    @Length(max = 254, message = "The name can't be longer than 254 characters")
    private String name;

    @Column(nullable = false, length = 254)
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "image/(png|jpeg|jpg|gif|webp|bmp|svg\\+xml)")
    @Length(max = 254, message = "The name can't be longer than 254 characters")
    private String type;

    // must be bytea datatype (do NOT use @Lob)
    // otherwise there is a problem with the communication between Hibernate and Postgres driver
    @NotNull
    @Column(name = "image_data", length = 1000, nullable = false)
    private byte[] imageData;
}
