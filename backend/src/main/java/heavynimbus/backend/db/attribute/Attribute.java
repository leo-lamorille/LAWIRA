package heavynimbus.backend.db.attribute;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import javax.persistence.CascadeType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attribute")
public class Attribute extends UUIDBasedEntity {
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @OneToMany(
      mappedBy = "attribute",
      targetEntity = AttributeOption.class,
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  private List<AttributeOption> options;
}
