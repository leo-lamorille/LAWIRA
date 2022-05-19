package heavynimbus.backend.controller.admin;

import heavynimbus.backend.controller.doc.AdminAccountControllerDocumentation;
import heavynimbus.backend.dto.account.AccountResponse;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/accounts")
public class AdminAccountController implements AdminAccountControllerDocumentation {
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponse createAdministrator(@RequestBody LoginRequest loginRequest)
      throws AlreadyExistsException {
    return accountService.createAdministrator(loginRequest);
  }

  @DeleteMapping("/{accountId}")
  public void deleteAccount(@PathVariable UUID accountId) throws NotFoundException {
    accountService.deleteAccount(accountId);
  }
}
