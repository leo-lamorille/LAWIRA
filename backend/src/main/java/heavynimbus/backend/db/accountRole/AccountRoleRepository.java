package heavynimbus.backend.db.accountRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    AccountRole findByRole(AccountRoleEnum role);
}
