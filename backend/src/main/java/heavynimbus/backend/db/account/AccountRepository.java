package heavynimbus.backend.db.account;

import heavynimbus.backend.db.accountRole.AccountRole;
import heavynimbus.backend.db.accountRole.AccountRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByUsername(String username);
}
