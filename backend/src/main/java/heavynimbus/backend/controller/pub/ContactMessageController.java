package heavynimbus.backend.controller.pub;

import heavynimbus.backend.controller.doc.ContactMessageControllerDocumentation;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.dto.contactMessage.CreateContactMessageRequest;
import heavynimbus.backend.service.ContactMessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/contact")
public class ContactMessageController implements ContactMessageControllerDocumentation {

  private final ContactMessageService contactMessageService;

  @PostMapping
  public ContactMessageResponse sendMessage(
      Authentication authentication,
      @RequestBody CreateContactMessageRequest contactMessageRequest) {
    System.out.println("authentication = " + authentication);
    System.out.println("contactMessageRequest = " + contactMessageRequest);
    return contactMessageService.create(Optional.ofNullable(authentication), contactMessageRequest);
  }
}
