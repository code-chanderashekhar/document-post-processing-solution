package com.synechisveltiosi.documentpostprocessingsolution.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BatchTransactionStatus {
    CREATED('I', "CREATED"),
    IN_PROGRESS('P', "IN PROGRESS"),
    COMPLETED('C', "COMPLETED"),
    FAILED('F', "FAILED"), NONE('N',"NONE" );

    private final char code;
    private final String description;

     BatchTransactionStatus(char code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BatchTransactionStatus fromCode(char code) {
      return   Arrays.stream(BatchTransactionStatus.values())
                .filter(status -> status.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
