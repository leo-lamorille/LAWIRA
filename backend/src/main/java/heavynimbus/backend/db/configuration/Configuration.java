package heavynimbus.backend.db.configuration;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "configuration")
public class Configuration extends UUIDBasedEntity {
  private String name;

  @ManyToMany
  @JoinTable(
      name = "configuration_attribute_options",
      joinColumns = @JoinColumn(name = "configuration_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "attribute_option_id", referencedColumnName = "id"))
  private List<AttributeOption> options;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private Account account;
}
