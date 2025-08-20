package ch.noseryoung.pixelcollectbackend.domain.giftcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {
}