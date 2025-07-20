package com.synechisveltiosi.documentpostprocessingsolution.helper.processor.impl;

import com.synechisveltiosi.documentpostprocessingsolution.helper.model.DocumentHierarchy;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.StackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;
import com.synechisveltiosi.documentpostprocessingsolution.service.DocumentBatchProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class USStackProcessor implements StackProcessor {
    private final DocumentBatchProcessingService documentBatchProcessingService;

    @Override
    public void process(int batchSize, String stackCode, List< Document > documents) {
        //sort documents

        // group documents
        DocumentHierarchy groupDocument = groupDocument(documents, Document::getAgentNumber);
        // splits documents
        List<DocumentHierarchy> documentHierarchies = splitDocuments(batchSize, groupDocument);

        documentBatchProcessingService.processDocumentBatch(stackCode, documentHierarchies);

    }
}
