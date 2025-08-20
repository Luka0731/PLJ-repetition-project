package ch.noseryoung.pixelcollectbackend.domain.account;

import ch.noseryoung.pixelcollectbackend.domain.account.dto.AccountAuthDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {

    @Autowired private AccountService accountService;


    // |--- authentication endpoints ---|

    @PostMapping("/signup")
    public ResponseEntity<Account> signup(@Valid @RequestBody AccountAuthDTO signup) {
        return ResponseEntity.ok(accountService.signup(signup));
    }

    @PutMapping("/login")
    public ResponseEntity<Account> login(@Valid @RequestBody AccountAuthDTO login) {
        return ResponseEntity.ok(accountService.login(login));
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("X-Session-Key") UUID key) {
        accountService.logout(key);
        return ResponseEntity.noContent().build();
    }


    // |--- account endpoints ---|

    @GetMapping("accounts/me")
    public ResponseEntity<Account> getMyself(@RequestHeader("X-Session-Key") UUID key) throws Exception {
        return ResponseEntity.ok(accountService.getAccountByKey(key));
    }

    @PutMapping("shop/buy/{productId}")
    public ResponseEntity<Account> buyProduct(@RequestHeader("X-Session-Key") UUID key, @PathVariable UUID productId) throws Exception {
        return ResponseEntity.ok(accountService.buyProduct(key, productId));
    }

    @PutMapping("shop/sell/{productId}")
    public ResponseEntity<Account> sellProduct(@RequestHeader("X-Session-Key") UUID key, @PathVariable UUID productId) throws Exception {
        return ResponseEntity.ok(accountService.sellProduct(key, productId));
    }



    // Testing -----------------------------------------------------------------------------------------------|

    @PutMapping("/balance/increase")
    public ResponseEntity<Account> increaseBalance(
            @RequestHeader("X-Session-Key") UUID key,
            @RequestParam int amount) throws Exception {

        Account updated = accountService.increaseBalance(key, amount);
        return ResponseEntity.ok(updated);
    }

}