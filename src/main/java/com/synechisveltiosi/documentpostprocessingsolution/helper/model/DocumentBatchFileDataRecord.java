package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;

public record DocumentBatchKey(Document document, BatchFileData batchFileData) {
}
