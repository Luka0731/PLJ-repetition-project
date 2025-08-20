package ch.noseryoung.pixelcollectbackend.domain.account;

import ch.noseryoung.pixelcollectbackend.domain.account.dto.AccountAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service

public class AccountService {

    @Autowired private AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // |--- authorization methods ---|

    public Account signup(AccountAuthDTO signup) {
        if (accountRepository.existsByEmail(signup.email())) {
            throw new IllegalArgumentException("An account with that email already exists"); // todo: make a different exception
        }

        Account account = new Account();
        account.setEmail(signup.email());
        account.setPasswordHash(passwordEncoder.encode(signup.password()));
        account.setBalance(0);
        account.setSessionKey(UUID.randomUUID());
        account.setSessionExpiresAt(Instant.now().plus(Duration.ofHours(2)));

        return accountRepository.save(account);
    }

    public Account login(AccountAuthDTO login) {
        Account account = accountRepository.findByEmail(login.email())
                .orElseThrow(() -> new IllegalArgumentException("There is no account with this email")); // todo: make a different exception

        if (!passwordEncoder.matches(login.password(), account.getPasswordHash())) {
            throw new IllegalArgumentException("Passwords don't match"); // todo: make a different exception
        }

        account.setSessionKey(UUID.randomUUID());
        account.setSessionExpiresAt(Instant.now().plus(Duration.ofHours(2)));

        return account;
    }

    public void logout(UUID key) {
        accountRepository.findBySessionKey(key).ifPresent(account -> {
            account.setSessionKey(null);
            account.setSessionExpiresAt(null);
            accountRepository.save(account);
        });
    }


    // |--- account methods ---|

    public Account getAccountByKey(UUID key) throws Exception {
        UUID id = assertSessionValid(key);
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no account with this id"));
    }

    public Account buyProduct(UUID accountKey, UUID productId) throws Exception {
        UUID accountID = assertSessionValid(accountKey);
        // todo: ME
        return null;
    }

    public Account sellProduct(UUID accountKey, UUID productId) throws Exception {
        UUID accountID = assertSessionValid(accountKey);
        // todo: ME
        return null;
    }


    // |--- guard ---|

    public UUID assertSessionValid(UUID key) throws Exception {
        Account account = accountRepository.findBySessionKey(key)
                .orElseThrow(() -> new IllegalArgumentException("Session Key is expired. Please Login"));

        Instant exp = account.getSessionExpiresAt();
        if (exp == null || Instant.now().isAfter(exp)) throw new Exception();  // todo: make that exception
        return account.getId();
    }



    // Testing -----------------------------------------------------------------------------------------------|

    public Account increaseBalance(UUID sessionKey, int amount) throws Exception {
        UUID id = assertSessionValid(sessionKey);

        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        acc.setBalance(acc.getBalance() + amount);
        return accountRepository.save(acc);
    }
}