package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

import lombok.Builder;

@Builder
public record BatchFileData(String batchNo, String batchSequenceNumber, String fileName, String bucketPath) {
}
