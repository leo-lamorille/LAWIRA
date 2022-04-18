package heavynimbus.backend.db.order;

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
@Entity(name = "order")
public class Order extends UUIDBasedEntity {
  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private Account account;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
  private List<AttributeOption> values;
}