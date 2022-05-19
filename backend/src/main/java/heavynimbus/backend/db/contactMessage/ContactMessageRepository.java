package heavynimbus.backend.db.contactMessage;

import heavynimbus.backend.db.account.Account;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, String> {
  List<ContactMessage> findAllByStatusInOrderBySentAtAsc(List<ContactMessageStatus> statuses);

  void deleteAllByAccount(Account account);
}
