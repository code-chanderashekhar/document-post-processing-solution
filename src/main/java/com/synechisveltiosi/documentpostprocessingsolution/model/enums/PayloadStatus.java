package com.synechisveltiosi.documentpostprocessingsolution.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PayloadStatus {
    CREATED('C', "CREATED"),
    IN_PROGRESS('I', "IN PROGRESS"),
    RETRY('R', "RETRY"),
    QUEUED('Q', "QUEUED"),
    CANCELLED('X', "CANCELLED"),
    FAILED('F', "FAILED"),
    PROCESSED('P', "PROCESSED"), NONE('N',"NONE" );

    private final char characterCode; // Renamed for clarity
    private final String description;

    private static final String INVALID_CODE_MSG = "Invalid code: "; // Introduced constant

    public static PayloadStatus fromCode(char code) { // Changed parameter type to char
        return Arrays.stream(PayloadStatus.values())
                .filter(status -> status.characterCode == code)
                .findFirst()
                .orElseThrow(() -> createInvalidCodeException(code)); // Extracted method for exception
    }

    private static IllegalArgumentException createInvalidCodeException(char code) { // Extracted method
        return new IllegalArgumentException(INVALID_CODE_MSG + code);
    }
}
