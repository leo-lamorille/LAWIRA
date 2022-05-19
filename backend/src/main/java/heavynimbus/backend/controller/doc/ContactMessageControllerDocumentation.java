package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.dto.contactMessage.CreateContactMessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[PUBLIC] Contact", description = "Actions related to contact administration")
public interface ContactMessageControllerDocumentation {

  @Operation(summary = "Send a contact message to the administrator", description = """
          You can send a contact message by using this endpoint, if you are authenticated this will
          link the message with your account
          """)
  ContactMessageResponse sendMessage(
      Authentication authentication,
      CreateContactMessageRequest contactMessageRequest);
}
