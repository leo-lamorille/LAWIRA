package heavynimbus.backend.dto.attributeOption;

import heavynimbus.backend.db.attributeOption.AttributeOptionType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeOptionResponse {
  private UUID id;
  private AttributeOptionType type;
  private String value;
}
