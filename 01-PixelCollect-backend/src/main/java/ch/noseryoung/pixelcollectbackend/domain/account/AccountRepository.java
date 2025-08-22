package ch.noseryoung.pixelcollectbackend.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(UUID id);

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Account> findBySessionKey(UUID sessionKey);

    List<Account> getOwnedProducts();
}