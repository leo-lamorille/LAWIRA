package heavynimbus.backend.dto.attribute;

import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
  private UUID id;
  private String name;
  private String description;
  private List<AttributeOptionResponse> options;
}
