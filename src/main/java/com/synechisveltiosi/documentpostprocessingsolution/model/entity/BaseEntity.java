package com.synechisveltiosi.documentpostprocessingsolution.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @UuidGenerator
    protected UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
