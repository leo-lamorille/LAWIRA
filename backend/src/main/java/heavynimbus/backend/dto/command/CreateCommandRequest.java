package heavynimbus.backend.dto.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommandRequest {
  @Min(value = 1, message = "minimum value of quantity is 1")
  @NotNull(message = "quantity cannot be null")
  private int quantity;

  @NotNull(message = "options cannot be null")
  private List<UUID> options;
}
