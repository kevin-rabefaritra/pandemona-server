package studio.startapps.pandemona.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Base entity, stores id and audit dates.
 * Mainly used to store the following data:
 * (1) id
 * (2) creation date
 * (3) last update date
 * (4) deletion date
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @CreatedDate
    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDDATE")
    private LocalDateTime lastModifiedDate;

    @Column(name = "DELETEDDATE")
    private LocalDateTime deletedDate = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
