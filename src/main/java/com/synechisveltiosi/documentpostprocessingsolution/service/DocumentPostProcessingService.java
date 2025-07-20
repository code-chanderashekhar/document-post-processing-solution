package com.synechisveltiosi.documentpostprocessingsolution.service;

import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.StackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.impl.AEStackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.impl.ARStackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.impl.BSStackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.impl.USStackProcessor;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;
import com.synechisveltiosi.documentpostprocessingsolution.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.synechisveltiosi.documentpostprocessingsolution.helper.processor.DocumentEnums.StackType;

@Service
@Slf4j
public class DocumentPostProcessingService {

    private final DocumentRepository documentRepository;
    private final ARStackProcessor arStackProcessor;
    private final AEStackProcessor aeStackProcessor;
    private final BSStackProcessor bsStackProcessor;
    private final USStackProcessor usStackProcessor;
    private final Map<StackType, StackProcessor> deliveryMethodProcessors;


    public DocumentPostProcessingService(
            DocumentRepository documentRepository,
            ARStackProcessor arStackProcessor,
            AEStackProcessor aeStackProcessor,
            BSStackProcessor bsStackProcessor,
            USStackProcessor usStackProcessor) {
        this.documentRepository = documentRepository;
        this.arStackProcessor = arStackProcessor;
        this.aeStackProcessor = aeStackProcessor;
        this.bsStackProcessor = bsStackProcessor;
        this.usStackProcessor = usStackProcessor;
        this.deliveryMethodProcessors = initializeProcessors();
    }

    private Map<StackType, StackProcessor> initializeProcessors() {
        return Map.of(
                StackType.AR, arStackProcessor,
                StackType.AE, aeStackProcessor,
                StackType.BS, bsStackProcessor,
                StackType.US, usStackProcessor
        );
    }


    public void processDocuments() {
        log.info("Processing documents");
        try {
            List<Document> documents = fetchDocuments();
            if (documents.isEmpty()) {
                log.info("No documents found");
                return;
            }

            processDocumentsByStack(documents);
        } catch (RuntimeException e) {
            log.error("Error processing documents", e);
        }
    }

    private void processDocumentsByStack(List<Document> documents) {
        Map<String, List<Document>> documentsByStack = groupDocumentsByStack(documents);
        documentsByStack.forEach(this::processStack);
    }


    private void processStack(String stackName, List<Document> documentsInStack) {
        log.info("Processing documents in stack {}", stackName);

        if (!isValidStackType(stackName)) {
            log.warn("Stack {} not found", stackName);
            return;
        }

        StackProcessor processor = deliveryMethodProcessors.get(StackType.valueOf(stackName.toUpperCase()));
        processor.process(documentsInStack.size(), stackName, documentsInStack);
    }


    private Map<String, List<Document>> groupDocumentsByStack(List<Document> documents) {
        return documents.stream()
                .collect(Collectors.groupingBy(Document::getStackName));
    }


    private boolean isValidStackType(String stackName) {
        return stackName != null
                && !stackName.isBlank()
                && deliveryMethodProcessors.containsKey(StackType.valueOf(stackName.toUpperCase()));
    }


    private List<Document> fetchDocuments() {
        List<Document> documents = documentRepository.findAll();
        log.info("Found {} documents", documents.size());
        return documents;
    }


}
