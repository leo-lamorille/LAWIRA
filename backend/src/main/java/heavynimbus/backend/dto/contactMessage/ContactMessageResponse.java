package heavynimbus.backend.dto.contactMessage;

import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
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
public class ContactMessageResponse {
  private UUID id;
  private String firstname;
  private String lastname;
  private String email;
  private String subject;
  private String content;
  private ContactMessageStatus status;
  private String account;
}
