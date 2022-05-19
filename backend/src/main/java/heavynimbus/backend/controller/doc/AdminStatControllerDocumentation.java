package heavynimbus.backend.controller.doc;

import heavynimbus.backend.db.stats.AttributeOptionStatsResponse;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[ADMIN] Commands", description = "Actions related to statistics for administrator")
public interface AdminStatControllerDocumentation {
  @Operation(summary = "Get command option statistics by attribute")
  List<AttributeOptionStatsResponse> getCommandOptionStatsByAttribute(UUID attributeId)
      throws NotFoundException;
}
