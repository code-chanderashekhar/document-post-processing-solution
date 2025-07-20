package com.synechisveltiosi.documentpostprocessingsolution.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.model.converter.BatchTransactionStatusConverter;
import com.synechisveltiosi.documentpostprocessingsolution.model.converter.DocumentPayloadConverter;
import com.synechisveltiosi.documentpostprocessingsolution.model.enums.BatchTransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentBatchTransaction extends BaseEntity implements Serializable {


    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_batch_ref_id", referencedColumnName = "id")
    @JsonBackReference
    private DocumentBatch documentBatch;


    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_ref_id", referencedColumnName = "id")
    @JsonBackReference
    private Document document;

    @Column(name = "payload", columnDefinition = "json", nullable = false)
    @Convert(converter = DocumentPayloadConverter.class)
    private DocumentParser documentParser;

    private String fileName;
    private String bucketPath;
    private String stackCode;
    private int batchSequenceNumber;

    @Convert(converter = BatchTransactionStatusConverter.class)
    @Column(name = "batch_transaction_status", length = 1, nullable = false)
    private BatchTransactionStatus batchTransactionStatus;
    private String recipient;
}

