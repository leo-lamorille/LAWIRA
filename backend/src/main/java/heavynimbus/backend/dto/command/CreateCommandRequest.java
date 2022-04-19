package heavynimbus.backend.dto.command;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommandRequest {
  private int quantity;
  private List<UUID> options;
}
