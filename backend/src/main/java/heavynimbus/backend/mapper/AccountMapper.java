package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.accountRole.AccountRoleEnum;
import heavynimbus.backend.db.accountRole.AccountRoleRepository;
import heavynimbus.backend.dto.login.LoginRequest;
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
        .roles(List.of(accountRoleRepository.findByRole(AccountRoleEnum.USER)))
        .build();
  }
}
