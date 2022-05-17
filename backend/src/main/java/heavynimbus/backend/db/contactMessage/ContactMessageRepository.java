package heavynimbus.backend.db.contactMessage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, String> {
  List<ContactMessage> findAllByStatusIn(List<ContactMessageStatus> statuses);
}
