package heavynimbus.backend.dto.product;

import heavynimbus.backend.db.attributeOption.AttributeOptionType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeOptionDetailResponse {
  private UUID attributeId;
  private String attributeName;
  private UUID optionId;
  private String optionValue;
  private AttributeOptionType optionType;
}
