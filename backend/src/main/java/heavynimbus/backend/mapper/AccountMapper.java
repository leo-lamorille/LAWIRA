package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.accountRole.AccountRole;
import heavynimbus.backend.db.accountRole.AccountRoleEnum;
import heavynimbus.backend.db.accountRole.AccountRoleRepository;
import heavynimbus.backend.dto.account.AccountResponse;
import heavynimbus.backend.dto.login.LoginRequest;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record AccountMapper (AccountRoleRepository accountRoleRepository, PasswordEncoder passwordEncoder){
  public Account loginRequestToAccount(LoginRequest loginRequest) {
    return Account.builder()
        .username(loginRequest.getUsername())
        .password(passwordEncoder.encode(loginRequest.getPassword()))
        .enabled(true)
        .commands(new ArrayList<>())
        .configurations(new ArrayList<>())
        .roles(List.of(accountRoleRepository.findByRole(AccountRoleEnum.USER)))
        .build();
  }
  public Account loginRequestToAdminAccount(LoginRequest loginRequest) {
    return Account.builder()
        .username(loginRequest.getUsername())
        .password(passwordEncoder.encode(loginRequest.getPassword()))
        .enabled(true)
        .commands(new ArrayList<>())
        .configurations(new ArrayList<>())
        .roles(List.of(accountRoleRepository.findByRole(AccountRoleEnum.ADMIN)))
        .build();
  }


  public AccountResponse accountToAccountResponse(Account account){
    return AccountResponse
        .builder()
        .id(UUID.fromString(account.getId()))
        .username(account.getUsername())
        .enabled(account.getEnabled())
        .role(
            account.getRoles()
                .stream()
                .findFirst()
                .map(AccountRole::getRole)
                .orElseThrow())
        .nbCommands(account.getCommands().size())
        .nbConfigurations(account.getConfigurations().size())
        .build();
  }
}
