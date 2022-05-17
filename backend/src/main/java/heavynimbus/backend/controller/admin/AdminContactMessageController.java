package heavynimbus.backend.controller.admin;

import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.service.ContactMessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "jwt_auth")
@RequestMapping("/admin/contact")
@RequiredArgsConstructor
public class AdminContactMessageController {

  private final ContactMessageService contactMessageService;

  @GetMapping
  public List<ContactMessageResponse> findAllByStatus(
      @RequestParam List<ContactMessageStatus> statuses) {
    return contactMessageService.findAllByStatusIn(statuses);
  }
}
