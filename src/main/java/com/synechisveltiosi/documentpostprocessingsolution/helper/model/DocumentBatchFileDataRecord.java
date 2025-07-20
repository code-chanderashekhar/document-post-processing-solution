package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;

import java.util.UUID;

public record DocumentBatchFileDataRecord(UUID id, DocumentParser payload, BatchFileData batchFileData) {
}
