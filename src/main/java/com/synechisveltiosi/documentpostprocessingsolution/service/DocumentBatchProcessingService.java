package com.synechisveltiosi.documentpostprocessingsolution.service;

import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.BarcodeGunterHandler;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.PrintMetadataAdder;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.PrintMetadataHandler;
import com.synechisveltiosi.documentpostprocessingsolution.helper.model.*;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.DocumentEnums;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.PrintMetadata;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.DocumentBatch;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.DocumentBatchTransaction;
import com.synechisveltiosi.documentpostprocessingsolution.model.enums.BatchTransactionStatus;
import com.synechisveltiosi.documentpostprocessingsolution.repository.DocumentBatchTransactionRepository;
import com.synechisveltiosi.documentpostprocessingsolution.repository.DocumentRepository;
import com.synechisveltiosi.documentpostprocessingsolution.util.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DocumentBatchProcessingService {
    private final DocumentBatchTransactionRepository documentBatchTransactionRepository;
    private final DocumentRepository documentRepository;
    private final PrintMetadataHandler printMetadataHandler;

    public void processDocumentBatch(String stackCode, List<DocumentHierarchy> documentHierarchyList) {
        IntStream.range(0, documentHierarchyList.size())
                .forEach(batchIndex -> executeBatchProcessing(documentHierarchyList.get(batchIndex),
                        new DocumentProcessor(stackCode, batchIndex + 1, documentHierarchyList.get(batchIndex))));
    }

    private void executeBatchProcessing(DocumentHierarchy documentHierarchy, DocumentProcessor documentProcessor) {
        DocumentBatch documentBatch = DocumentBatch.builder()
                .batchNo(documentProcessor.getDDName() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .build();
        processAgents(documentHierarchy, documentProcessor);
        List<DocumentBatchFileDataRecord> documentBatchFileDataRecordList = documentProcessor.getDocuments();
        List<DocumentBatchTransaction> documentBatchTransactionList = documentBatchFileDataRecordList.stream()
                .map(r -> saveDocumentBatchTransaction(documentProcessor.getStackCode(),
                        documentProcessor.getBatchSeqNo(),
                        documentRepository.findById(r.id()).orElseThrow(),
                        r.payload(),
                        r.batchFileData(),
                        documentBatch)).toList();
        log.info("Saved {} document batch transactions", documentBatchTransactionList.size());

    }

    public DocumentBatchTransaction saveDocumentBatchTransaction(String stackCode, int batchSeqNo,
                                                                 Document document,
                                                                 DocumentParser documentParser,
                                                                 BatchFileData batchFileData,
                                                                 DocumentBatch documentBatch) {
        document.setPayload(documentParser);
        DocumentBatchTransaction newDocBatchTransaction = DocumentBatchTransaction.builder()
                .documentBatch(documentBatch)
                .document(document)
                .documentParser(document.getPayload())
                .batchSequenceNumber(batchSeqNo)
                .stackCode(stackCode)
                .bucketPath(batchFileData.bucketPath())
                .fileName(batchFileData.fileName())
                .recipient(document.getRecipient())
                .batchTransactionStatus(BatchTransactionStatus.CREATED)
                .build();

        DocumentBatchTransaction saveDocBatchTransaction = documentBatchTransactionRepository.save(newDocBatchTransaction);

        Optional.of(saveDocBatchTransaction)
                .map(DocumentBatchTransaction::getDocumentParser)
                .map(JsonUtils::convertObjectToJson)
                .ifPresentOrElse(
                        json -> JsonUtils.writeFileFromObject(
                                saveDocBatchTransaction.getStackCode(),
                                saveDocBatchTransaction.getBatchSequenceNumber(),
                                saveDocBatchTransaction.getFileName(),
                                json),
                        () -> log.error("Error saving document batch transaction"));

        return saveDocBatchTransaction;
    }

    private void processAgents(DocumentHierarchy documentHierarchy, DocumentProcessor documentProcessor) {
        documentHierarchy.hierarchy()
                .keySet()
                .forEach(agentNumber -> {
                            documentProcessor.incrementAgentNumberTransactionCounter();
                            documentHierarchy.getAgentDocuments(agentNumber).keySet()
                                    .forEach(distributionBranch ->
                                            processDistributionBranches(agentNumber, distributionBranch, documentHierarchy, documentProcessor));
                        }
                );
    }

    private void processDistributionBranches(String agentNumber, String distributionBranch, DocumentHierarchy documentHierarchy, DocumentProcessor documentProcessor) {
        documentHierarchy.getDistributionBranchDocuments(agentNumber, distributionBranch)
                .forEach((accountNumber, policyNumberDocumentListMap) -> {
                    documentProcessor.incrementAccountNumberTransactionCounter();
                    processAccounts(agentNumber, distributionBranch, accountNumber, policyNumberDocumentListMap.keySet(), documentHierarchy, documentProcessor);
                });
    }

    private void processAccounts(String agentNumber, String distributionBranch, String accountNumber, Set<String> policyNumbers, DocumentHierarchy documentHierarchy, DocumentProcessor documentProcessor) {
        policyNumbers.forEach(policyNumber -> {
            documentProcessor.incrementPolicyNumberTransactionCounter();
            List<Document> documents = documentHierarchy.getDocuments(agentNumber, distributionBranch, accountNumber, policyNumber);
            documents.forEach(document -> handleDocument(document, new DocumentGroupColumn(agentNumber, distributionBranch, accountNumber, policyNumbers, policyNumber), documentProcessor));
        });

    }

    private void handleDocument(Document document, DocumentGroupColumn documentGroupColumn, DocumentProcessor documentProcessor) {
        PrintMetadata.PrintMetadataBuilder printMetadataBuilder = attachPrintMetadataDocument(document, documentGroupColumn, documentProcessor);
        String barcodeRestBarcode = documentProcessor.generateBarcode();

        ProcessDocumentContext context = new ProcessDocumentContext(
                document, documentGroupColumn, documentProcessor, printMetadataBuilder, barcodeRestBarcode
        );

        if (shouldProcessAgentCopies(document)) {
            List<DocumentBatchFileDataRecord> agentDocBatchList =
                    processDocumentCopies(context, document.getPrintOption().getAgentCopyCount(),
                            documentProcessor::agentRecipient);
            documentProcessor.getDocuments().addAll(agentDocBatchList);
        }

        // barcode rest copy type override issue
        if (shouldProcessInsuredCopies(document)) {
            Document copyDocForInsured = Document.copyOf(document);
            List<DocumentBatchFileDataRecord> insuredDocBatchList =
                    processDocumentCopies(context, copyDocForInsured.getPrintOption().getInsuredCopyCount(),
                            documentProcessor::insuredRecipient);
            documentProcessor.getDocuments().addAll(insuredDocBatchList);
        }
    }

    private record ProcessDocumentContext(
            Document document,
            DocumentGroupColumn documentGroupColumn,
            DocumentProcessor documentProcessor,
            PrintMetadata.PrintMetadataBuilder printMetadataBuilder,
            String barcodeRestBarcode
    ) {}

    private boolean shouldProcessAgentCopies(Document document) {
        return document.getPrintOption().isInsuredCopyPayPlan()
                && document.getPrintOption().getAgentCopyCount() > 0;
    }

    private boolean shouldProcessInsuredCopies(Document document) {
        return document.getPrintOption().isInsuredCopyPayPlan()
                && document.getPrintOption().getInsuredCopyCount() > 0;
    }

    private List<DocumentBatchFileDataRecord> processDocumentCopies(
            ProcessDocumentContext context,
            int copyCount,
            Supplier<String> recipientSupplier
    ) {
        return IntStream.range(0, copyCount)
                .mapToObj(docIndex -> {
                    String recipient = recipientSupplier.get();
                    PrintMetadata printMetadata = context.printMetadataBuilder()
                            .barcodeRest(printMetadataHandler.buildBarcodeRest(
                                    context.documentProcessor().getBatchSeqNo(),
                                    recipient,
                                    context.barcodeRestBarcode()))
                            .build();
                    context.document().getPayload().getDocument().setPrintMetadata(printMetadata);
                    context.document().setRecipient(recipient);

                    return new DocumentBatchFileDataRecord(
                            context.document().getId(),
                            context.document().getPayload(),
                            context.documentProcessor().batchFileData(context.documentGroupColumn(), recipient)
                    );
                })
                .toList();
    }


    private PrintMetadata.PrintMetadataBuilder attachPrintMetadataDocument(Document document, DocumentGroupColumn documentGroupColumn, DocumentProcessor documentProcessor) {
        PrintMetadata.PrintMetadataBuilder printMetadataBuilder = PrintMetadata.builder();
        attachBasicMetadata(printMetadataBuilder, document, documentProcessor);
        attachGunterMetadata(printMetadataBuilder, documentProcessor);
        attachConditionalMetadata(printMetadataBuilder, document, documentGroupColumn, documentProcessor);
        return printMetadataBuilder;
    }

    private void attachBasicMetadata(PrintMetadata.PrintMetadataBuilder builder, Document document,
                                     DocumentProcessor documentProcessor) {
        builder.distribution(printMetadataHandler.buildDistribution(
                new PrintMetadataAdder.DistributionParams(
                        documentProcessor.getStackCode(),
                        document.getPrintOption().isInsuredCopyPayPlan(),
                        document.getPrintOption().isPayPlanWithCommissionForAgent(),
                        document.getPrintOption().isRatingWorksheetForAgent())));
    }


    private void attachGunterMetadata(PrintMetadata.PrintMetadataBuilder builder, DocumentProcessor documentProcessor) {
        boolean isStartGunter = documentProcessor.isFirstPolicyTransaction();
        boolean isEndGunter = documentProcessor.isLastPolicyTransaction();

        if (isStartGunter && !isEndGunter) {
            builder.gunter(printMetadataHandler.buildStartGunter(
                    documentProcessor.getDDName(),
                    documentProcessor.getGunterBarcode(BarcodeGunterHandler.POSiTION_1_2_START_PAGE)));
        } else if (isEndGunter && !isStartGunter) {
            builder.gunter(printMetadataHandler.buildEndGunter(
                    documentProcessor.getDDName(),
                    documentProcessor.getGunterBarcode(BarcodeGunterHandler.POSITION_1_2_END_PAGE)));
        }
    }

    private void attachConditionalMetadata(PrintMetadata.PrintMetadataBuilder builder, Document document,
                                           DocumentGroupColumn documentGroupColumn, DocumentProcessor documentProcessor) {
        if (documentProcessor.isFirstAccountTransaction()) {
            builder.banner(printMetadataHandler.buildBanner(
                    documentGroupColumn.accountNumber(), documentGroupColumn.policyNumbers()));
        }

        if (documentProcessor.isAgentNumberTransaction()) {
            builder.mailer(printMetadataHandler.buildMailer(document.getPayload().getDocument()
                    .getPolicyPeriods().getFirst().contacts().getFirst().primaryAddress()));
        }

        if (DocumentEnums.StackType.US.name().equals(documentProcessor.getStackCode())) {
            builder.requestorRoutingSheet(printMetadataHandler.buildRequestorRoutingSheet(
                    documentGroupColumn.agentNumber(), documentGroupColumn.policyNumber()));
        }
    }


}

