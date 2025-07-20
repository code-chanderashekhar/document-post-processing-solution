package com.synechisveltiosi.documentpostprocessingsolution.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentBatch extends BaseEntity implements Serializable {

    private String batchNo;

    private String errorStage;
    private String errorDescription;

}
