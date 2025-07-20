package com.synechisveltiosi.documentpostprocessingsolution.model.converter;

import com.synechisveltiosi.documentpostprocessingsolution.model.enums.PayloadStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PayloadStatusConverter implements AttributeConverter<PayloadStatus, String> {

    private static final String NULL_CONVERT_OUTPUT = null;

    @Override
    public String convertToDatabaseColumn(PayloadStatus payloadStatus) {
        return payloadStatus != null ? String.valueOf(payloadStatus.getCharacterCode()) : NULL_CONVERT_OUTPUT;
    }

    @Override
    public PayloadStatus convertToEntityAttribute(String databaseValue) {
        return isNullOrEmpty(databaseValue) ? PayloadStatus.NONE
                : PayloadStatus.fromCode(databaseValue.charAt(0));
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}