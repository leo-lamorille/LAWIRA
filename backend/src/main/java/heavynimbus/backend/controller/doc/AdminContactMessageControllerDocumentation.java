package heavynimbus.backend.controller.doc;

import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name = "jwt_auth")
@Tag(
    name = "[ADMIN] Contact messages",
    description = "Actions related to message handling for administrator")
public interface AdminContactMessageControllerDocumentation {

  @Operation(summary = "Find all messages by status")
  List<ContactMessageResponse> findAllByStatus(List<ContactMessageStatus> statuses);

  @Operation(summary = "Find message by id")
  ContactMessageResponse findById(UUID contactMessageId)
      throws NotFoundException;

  @Operation(summary = "Delete contact message")
  void deleteById(UUID contactMessageId) throws NotFoundException;
}
