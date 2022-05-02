package heavynimbus.backend.dto.attributeOption;

import heavynimbus.backend.db.attributeOption.AttributeOptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttributeOptionRequest {
  private String value;
  private AttributeOptionType type;
}
