package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.contactMessage.ContactMessage;
import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.dto.contactMessage.CreateContactMessageRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ContactMessageMapper {

  public ContactMessage createContactMessageRequestToContactMessage(
      CreateContactMessageRequest createContactMessageRequest, Optional<Account> account) {
    return ContactMessage.builder()
        .firstname(createContactMessageRequest.getFirstname())
        .lastname(createContactMessageRequest.getLastname())
        .email(createContactMessageRequest.getEmail())
        .subject(createContactMessageRequest.getSubject())
        .content(createContactMessageRequest.getContent())
        .status(ContactMessageStatus.NEW)
        .account(account.orElse(null))
        .build();
  }

  public ContactMessageResponse contactMessageToContactMessageResponse(ContactMessage contactMessage) {
    String username =
        contactMessage.getAccount() != null ? contactMessage.getAccount().getUsername() : null;
    return ContactMessageResponse.builder()
        .id(UUID.fromString(contactMessage.getId()))
        .firstname(contactMessage.getFirstname())
        .lastname(contactMessage.getLastname())
        .subject(contactMessage.getSubject())
        .email(contactMessage.getEmail())
        .content(contactMessage.getContent())
        .account(username)
        .build();
  }
}
