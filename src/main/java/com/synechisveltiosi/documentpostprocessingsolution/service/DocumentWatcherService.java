package com.synechisveltiosi.documentpostprocessingsolution.service;

import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;
import com.synechisveltiosi.documentpostprocessingsolution.repository.DocumentRepository;
import com.synechisveltiosi.documentpostprocessingsolution.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Order(1)
@Slf4j
public class DocumentWatcherService implements CommandLineRunner {
    private final DocumentRepository documentRepository;
    private final DocumentPostProcessingService documentPostProcessingService;

    @Override
    public void run(String... args) {
        List<Document> documentAR = DataUtils.getDocumentAR();
        List<Document> documentAE = DataUtils.getDocumentAE();
        List<Document> documentBS = DataUtils.getDocumentBS();
        List<Document> documentUS = DataUtils.getDocumentUS();
        List<Document> documents = Stream.of(documentAR, documentAE, documentBS, documentUS).flatMap(List::stream).toList();
        List<Document> documentList = documentRepository.saveAll(documents);
        log.info("Saved {} documents", documentList.size());
        documentPostProcessingService.processDocuments();
        log.info("Finished processing documents");
    }
}
