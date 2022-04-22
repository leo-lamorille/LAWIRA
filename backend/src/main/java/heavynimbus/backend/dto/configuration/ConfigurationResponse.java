package heavynimbus.backend.dto.configuration;

import heavynimbus.backend.dto.product.AttributeOptionDetailResponse;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationResponse {
  private UUID id;
  private String name;
  private List<AttributeOptionDetailResponse> options;
}
