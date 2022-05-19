package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.account.AccountResponse;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[ADMIN] Accounts", description = "Actions related to account handling for administrator")
public interface AdminAccountControllerDocumentation {
  @Operation(summary = "Find all accounts")
  List<AccountResponse> findAllAccounts();

  @Operation(summary = "Enable an disabled account")
  AccountResponse enableAccount(UUID accountId) throws NotFoundException;

  @Operation(summary = "Disable an enabled account")
  AccountResponse disableAccount(UUID accountId) throws NotFoundException;

  @Operation(summary = "Create an administrator")
  AccountResponse createAdministrator(LoginRequest loginRequest) throws AlreadyExistsException;

  @Operation(summary = "Delete an account")
  void deleteAccount(UUID accountId) throws NotFoundException;
}
