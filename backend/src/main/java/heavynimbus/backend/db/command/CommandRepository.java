package heavynimbus.backend.db.command;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, String> {
  List<Command> findAllByAccount_UsernameAndStatus(String username, CommandStatus status);

  List<Command> findAllByAccount_Username(String username);
  Optional<Command> findByIdAndAccount_Username(String id, String username);
}
