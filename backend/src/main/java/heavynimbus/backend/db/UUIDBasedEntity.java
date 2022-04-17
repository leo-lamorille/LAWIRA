package heavynimbus.backend.db;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
public class UUIDBasedEntity {

    @Id
    @Column(name = "id")
    protected UUID id;

    @PrePersist
    public void prePersist(){
        if (this.id == null) this.id = UUID.randomUUID();
    }
}
