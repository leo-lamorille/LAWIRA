package heavynimbus.backend.dto.command;

import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.product.AttributeOptionDetailResponse;
import java.util.List;
import lombok.*;

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
    private List<AttributeOptionDetailResponse> values;
}
