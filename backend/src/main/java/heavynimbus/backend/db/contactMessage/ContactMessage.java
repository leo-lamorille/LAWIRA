package heavynimbus.backend.db.contactMessage;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.account.Account;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity(name = "contact_message")
public class ContactMessage extends UUIDBasedEntity {
  private String firstname;
  private String lastname;
  private String email;
  private String subject;
  private String content;
  @Enumerated(EnumType.STRING)
  private ContactMessageStatus status;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
}
