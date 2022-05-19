package heavynimbus.backend.db.contactMessage;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.account.Account;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contact_message")
public class ContactMessage extends UUIDBasedEntity {
  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email")
  private String email;

  @Column(name = "subject")
  private String subject;

  @Column(name = "content")
  private String content;

  @Column(name = "sent_at")
  private Timestamp sentAt;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ContactMessageStatus status;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
}
