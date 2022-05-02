package heavynimbus.backend.dto.command;

import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.attributeOption.AttributeOptionDetailResponse;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCommandResponse {
  private UUID id;
  private String username;
  private CommandStatus status;
  private int quantity;
  private List<AttributeOptionDetailResponse> options;
}
