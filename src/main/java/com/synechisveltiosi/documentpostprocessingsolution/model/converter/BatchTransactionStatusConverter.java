package com.synechisveltiosi.documentpostprocessingsolution.model.converter;

import com.synechisveltiosi.documentpostprocessingsolution.model.enums.BatchTransactionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BatchTransactionStatusConverter implements AttributeConverter<BatchTransactionStatus, String> {

    private static final String NULL_CONVERT_OUTPUT = null;

    @Override
    public String convertToDatabaseColumn(BatchTransactionStatus payloadStatus) {
        return payloadStatus != null ? String.valueOf(payloadStatus.getCode()) : NULL_CONVERT_OUTPUT;
    }

    @Override
    public BatchTransactionStatus convertToEntityAttribute(String databaseValue) {
        return isNullOrEmpty(databaseValue) ? BatchTransactionStatus.NONE
                : BatchTransactionStatus.fromCode(databaseValue.charAt(0));
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}