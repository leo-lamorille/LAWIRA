package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.contactMessage.ContactMessage;
import heavynimbus.backend.db.contactMessage.ContactMessageRepository;
import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.dto.contactMessage.CreateContactMessageRequest;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.ContactMessageMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
  private final ContactMessageRepository contactMessageRepository;
  private final ContactMessageMapper contactMessageMapper;
  private final AccountService accountService;

  public List<ContactMessageResponse> findAllByStatusIn(List<ContactMessageStatus> statuses) {
    statuses =
        statuses == null ? List.of(ContactMessageStatus.NEW, ContactMessageStatus.SEEN) : statuses;
    return contactMessageRepository.findAllByStatusInOrderBySentAtAsc(statuses).stream()
        .map(contactMessageMapper::contactMessageToContactMessageResponse)
        .collect(Collectors.toList());
  }

  public ContactMessage findContactMessageById(UUID id) throws NotFoundException {
    return contactMessageRepository
        .findById(id.toString())
        .orElseThrow(() -> new NotFoundException("Contact message", "id", id.toString()));
  }

  public ContactMessageResponse findById(UUID id) throws NotFoundException {
    ContactMessage contactMessage = findContactMessageById(id);
    contactMessage.setStatus(ContactMessageStatus.SEEN);
    contactMessage = contactMessageRepository.save(contactMessage);
    return contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
  }

  public ContactMessageResponse create(
      Optional<Authentication> auth, CreateContactMessageRequest contactMessageRequest) {
    Optional<Account> account =
        auth.map(Authentication::getName).map(accountService::findByUsername);
    ContactMessage contactMessage =
        contactMessageMapper.createContactMessageRequestToContactMessage(
            contactMessageRequest, account);
    System.out.println("contactMessage = " + contactMessage);
    contactMessage = contactMessageRepository.save(contactMessage);
    return contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
  }

  public void delete(UUID id) throws NotFoundException {
    ContactMessage contactMessage = findContactMessageById(id);
    contactMessageRepository.delete(contactMessage);
  }
}
