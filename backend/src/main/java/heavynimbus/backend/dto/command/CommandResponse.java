package heavynimbus.backend.dto.command;

import heavynimbus.backend.db.command.CommandStatus;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandResponse {
    private UUID id;
    private CommandStatus status;
    private int quantity;
    private Map<String, String> values;
}
