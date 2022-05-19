package heavynimbus.backend.controller.admin;

import heavynimbus.backend.controller.doc.AdminContactMessageControllerDocumentation;
import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.ContactMessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/contact")
@RequiredArgsConstructor
public class AdminContactMessageController implements AdminContactMessageControllerDocumentation {

  private final ContactMessageService contactMessageService;

  @GetMapping
  public List<ContactMessageResponse> findAllByStatus(
      @RequestParam(required = false) List<ContactMessageStatus> statuses) {
    return contactMessageService.findAllByStatusIn(statuses);
  }

  @GetMapping("/{contactMessageId}")
  public ContactMessageResponse findById(@PathVariable UUID contactMessageId)
      throws NotFoundException {
    return contactMessageService.findById(contactMessageId);
  }

  @DeleteMapping("/{contactMessageId}")
  public void deleteById(@PathVariable UUID contactMessageId) throws NotFoundException {
    contactMessageService.delete(contactMessageId);
  }
}
