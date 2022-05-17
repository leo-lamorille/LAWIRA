package heavynimbus.backend.service;

import heavynimbus.backend.db.contactMessage.ContactMessageRepository;
import heavynimbus.backend.db.contactMessage.ContactMessageStatus;
import heavynimbus.backend.dto.contactMessage.ContactMessageResponse;
import heavynimbus.backend.mapper.ContactMessageMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
  private final ContactMessageRepository contactMessageRepository;
  private final ContactMessageMapper contactMessageMapper;

  public List<ContactMessageResponse> findAllByStatusIn(List<ContactMessageStatus> statuses) {
    return contactMessageRepository.findAllByStatusIn(statuses).stream()
        .map(contactMessageMapper::contactMessageToContactMessageResponse)
        .collect(Collectors.toList());
  }
}
