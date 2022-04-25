package heavynimbus.backend.db;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class UUIDBasedEntity {

  @Id
  @Column(name = "id")
  protected String id;

  @PrePersist
  public void prePersist() {
    if (this.id == null) this.id = UUID.randomUUID().toString();
  }
}
