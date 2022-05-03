package heavynimbus.backend.db.attributeOption;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.configuration.Configuration;
import java.util.List;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attribute_option")
public class AttributeOption extends UUIDBasedEntity {
  @Column(name = "value")
  private String value;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private AttributeOptionType type;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attribute.class)
  @JoinColumn(name = "attribute_id", referencedColumnName = "id")
  private Attribute attribute;

  @ManyToMany(mappedBy = "options", cascade = CascadeType.ALL)
  private List<Command> commands;

  @ManyToMany(mappedBy = "options", cascade = CascadeType.ALL)
  private List<Configuration> configurations;

  public String getAttributeName() {
    return attribute.getName();
  }
}
