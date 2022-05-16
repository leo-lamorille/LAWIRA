package heavynimbus.backend.dto.account;

import heavynimbus.backend.db.accountRole.AccountRoleEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
  private UUID id;
  private String username;
  private boolean enabled;
  private AccountRoleEnum role;
  private long nbCommands;
  private long nbConfigurations;
}
