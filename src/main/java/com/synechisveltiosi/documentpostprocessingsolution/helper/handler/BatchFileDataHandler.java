package com.synechisveltiosi.documentpostprocessingsolution.helper.handler;

import com.synechisveltiosi.documentpostprocessingsolution.helper.model.BatchFileData;
import com.synechisveltiosi.documentpostprocessingsolution.helper.model.DocumentGroupColumn;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class BatchFileDataHandler {
    public static final String JSON_FILE_EXTENSION = ".json";
    private final String folderStackName;
    private final int batchSequenceNumber;
    private final AtomicLong lastTs = new AtomicLong(0);


    public String formatBucketPath(String rootFolderPath, String batchFileFolderPath) {
        return String.format("%s/%s", rootFolderPath, batchFileFolderPath);
    }

    public BatchFileData createBatchFileData(String rootFolderPath, DocumentGroupColumn documentGroupColumn, String copyType) {
        return BatchFileData.builder()
                .batchNo(formatBatchNumber(folderStackName, batchSequenceNumber))
                .batchSequenceNumber(String.valueOf(batchSequenceNumber))
                .fileName(formatBatchFileName(folderStackName, batchSequenceNumber, documentGroupColumn, copyType))
                .bucketPath(formatBucketPath(rootFolderPath, formatBatchFilesFolderPath(folderStackName, batchSequenceNumber)))
                .build();
    }

    private String formatBatchFilesFolderPath(String folderStackName, int batchSequenceNumber) {
        return String.format("%s/%s", folderStackName, formatBatchSequence(batchSequenceNumber));
    }

    private Object formatBatchSequence(int batchSequenceNumber) {
        return String.format("%02d", batchSequenceNumber);
    }

    private String formatBatchFileName(String folderStackName, int batchSequenceNumber, DocumentGroupColumn documentGroupColumn, String copyType) {
        long now = System.currentTimeMillis();
        long last = lastTs.get();
        if (now <= last) now = last + 1;
        lastTs.set(now);
        return String.format("%s-%s-%s-%s-%s-%s-%s%s", LocalDateTime.ofEpochSecond(now / 1000, (int) (now % 1000) * 1_000_000, ZoneOffset.UTC),
                documentGroupColumn.accountNumber(), documentGroupColumn.distributionBranch(), documentGroupColumn.accountNumber(), documentGroupColumn.policyNumber(),
                folderStackName, batchSequenceNumber, JSON_FILE_EXTENSION);

    }

    private String formatBatchNumber(String folderStackName, int batchSequenceNumber) {
        return String.format("%s-%03d", folderStackName, batchSequenceNumber);
    }
}
