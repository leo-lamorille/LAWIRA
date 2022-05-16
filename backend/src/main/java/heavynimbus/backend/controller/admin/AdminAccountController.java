package heavynimbus.backend.controller.admin;

import heavynimbus.backend.dto.account.AccountResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "jwt_auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/accounts")
public class AdminAccountController {
  private final AccountService accountService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AccountResponse> findAllAccounts() {
    return accountService.findAllAccounts();
  }

  @PutMapping("/{accountId}/enable")
  public AccountResponse enableAccount(@PathVariable UUID accountId) throws NotFoundException {
    return accountService.enable(accountId);
  }

  @PutMapping("/{accountId}/disable")
  public AccountResponse disableAccount(@PathVariable UUID accountId) throws NotFoundException {
    return accountService.disable(accountId);
  }
}
