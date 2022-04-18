package heavynimbus.backend.db.attributeOption;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.attribute.Attribute;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attribute_option")
public class AttributeOption extends UUIDBasedEntity {
  @Column(name = "value")
  private String value;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private AttributeOptionType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attribute_id", referencedColumnName = "id")
  private Attribute attribute;
}
