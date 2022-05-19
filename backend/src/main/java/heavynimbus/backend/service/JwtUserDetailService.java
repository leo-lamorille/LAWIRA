package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.account.AccountRepository;
import heavynimbus.backend.db.accountRole.AccountRole;
import heavynimbus.backend.db.accountRole.AccountRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account =
        accountRepository
            .findByUsername(username)
            .filter(Account::getEnabled)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        "User with username " + username + " not found or disabled"));
    return new User(
        account.getUsername(),
        account.getPassword(),
        account.getEnabled(),
        true,
        true,
        true,
        getAuthorities(account));
  }

  private static List<GrantedAuthority> getAuthorities(Account account) {
    return account.getRoles().stream()
        .map(AccountRole::getRole)
        .map(AccountRoleEnum::toString)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
