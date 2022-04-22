package heavynimbus.backend.db.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {

  Optional<Configuration> findByIdAndAccount_Username(UUID id, String username);

  List<Configuration> findAllByAccount_Username(String username);
}
