package heavynimbus.backend.db.accountRole;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "role")
public class AccountRole {
  @Id
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private AccountRoleEnum role;

  @Column(name = "description", nullable = false)
  private String description;
}
