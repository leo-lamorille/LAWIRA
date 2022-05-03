package heavynimbus.backend.dto.configuration;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateConfigurationRequest {
  @NotBlank(message = "name cannot be null or blank")
  private String name;

  @NotNull(message = "options cannot be null")
  private List<UUID> options;
}
