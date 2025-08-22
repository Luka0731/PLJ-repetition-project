package ch.noseryoung.pixelcollectbackend.domain.account;

import ch.noseryoung.pixelcollectbackend.domain.account.dto.AccountAuthDTO;
import ch.noseryoung.pixelcollectbackend.domain.product.Rarity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PutMapping(" accounts/me/products")
    public ResponseEntity<List<Account>> getOwnedProducts(
            @RequestHeader("X-Session-Key") UUID key,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Rarity rarity,
            @RequestParam(required = false) Boolean price,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) throws Exception {
        return ResponseEntity.ok(accountService.getOwnedProducts(key));
    }

    @PutMapping("shop/buy/{productId}")
    public ResponseEntity<Account> buyProduct(@RequestHeader("X-Session-Key") UUID key, @PathVariable UUID productId) throws Exception {
        return ResponseEntity.ok(accountService.buyProduct(key, productId));
    }

    @PutMapping("shop/sell/{productId}")
    public ResponseEntity<Account> sellProduct(@RequestHeader("X-Session-Key") UUID key, @PathVariable UUID productId) throws Exception {
        return ResponseEntity.ok(accountService.sellProduct(key, productId));
    }
}