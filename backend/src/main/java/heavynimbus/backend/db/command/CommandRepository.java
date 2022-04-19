package heavynimbus.backend.db.command;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, UUID> {
  List<Command> findAllByAccount_UsernameAndStatus(String username, CommandStatus status);

  Optional<Command> findByIdAndAccount_Username(UUID id, String username);
}
