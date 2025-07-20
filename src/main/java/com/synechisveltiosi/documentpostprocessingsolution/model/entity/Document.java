package com.synechisveltiosi.documentpostprocessingsolution.model.entity;

import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.model.converter.DocumentPayloadConverter;
import com.synechisveltiosi.documentpostprocessingsolution.model.converter.PayloadStatusConverter;
import com.synechisveltiosi.documentpostprocessingsolution.model.embed.Address;
import com.synechisveltiosi.documentpostprocessingsolution.model.embed.PrintOption;
import com.synechisveltiosi.documentpostprocessingsolution.model.enums.PayloadStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity implements Serializable {

    private String agentNumber;
    private String requestorId;
    private String distributionBranch;
    private String accountNumber;
    private String policyNumber;
    private String stackName;
    @Transient
    private String recipient;

    private LocalDateTime timestamp;

    @Convert(converter = PayloadStatusConverter.class)
    @Column(name = "payload_status", length = 1, nullable = false)
    private PayloadStatus payloadStatus = PayloadStatus.NONE;

    @Embedded
    private PrintOption printOption;

    @Embedded
    private Address address;

    @Column(name="payload" , columnDefinition = "json", nullable = false)
    @Convert(converter = DocumentPayloadConverter.class)
    private DocumentParser payload;

    public Document(Document other){
        this.id = other.id;
        this.agentNumber = other.agentNumber;
        this.requestorId = other.requestorId;
        this.distributionBranch = other.distributionBranch;
        this.accountNumber = other.accountNumber;
        this.policyNumber = other.policyNumber;
        this.stackName = other.stackName;
        this.recipient = other.recipient;
        this.timestamp = other.timestamp;
        this.payloadStatus = other.payloadStatus;
        this.printOption = other.printOption;
        this.address = other.address;
        this.payload = other.payload;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }

    public static Document copyOf(Document other){
        return new Document(other);
    }
}
