package heavynimbus.backend.db.account;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.accountRole.AccountRole;
import heavynimbus.backend.db.command.Command;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account")
public class Account extends UUIDBasedEntity {

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "account_roles",
      joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<AccountRole> roles;

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  private List<Command> commands;
}
