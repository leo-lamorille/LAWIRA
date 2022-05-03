package heavynimbus.backend.db.command;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "command")
public class Command extends UUIDBasedEntity {
  private int quantity;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private CommandStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private Account account;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "command_attribute_options",
      joinColumns = @JoinColumn(name = "command_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "attribute_option_id", referencedColumnName = "id"))
  private List<AttributeOption> options;
}
