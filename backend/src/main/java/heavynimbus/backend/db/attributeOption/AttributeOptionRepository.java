package heavynimbus.backend.db.attributeOption;

import heavynimbus.backend.db.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttributeOptionRepository extends JpaRepository<AttributeOption, String> {
  List<AttributeOption> findAllByAttribute(Attribute attribute);
}
