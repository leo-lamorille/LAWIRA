package heavynimbus.backend.dto.contactMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactMessageRequest {
  private String firstname;
  private String lastname;
  private String email;
  private String subject;
  private String content;
}
