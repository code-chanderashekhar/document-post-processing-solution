package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

import com.synechisveltiosi.documentpostprocessingsolution.constants.AppConstants;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.AEARUSGenerateBarcode;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.BSIHGenerateBarcode;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.BarcodeGunterHandler;
import com.synechisveltiosi.documentpostprocessingsolution.helper.handler.BatchFileDataHandler;
import com.synechisveltiosi.documentpostprocessingsolution.helper.processor.DocumentEnums;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@Getter
public class DocumentProcessor {
    private final String stackCode;
    private final int batchSeqNo;
    private final int totalPolicyTransactions;
    private final String rootFolderName;
    private final AtomicInteger agentNumberTransactionCounter = new AtomicInteger(0);
    private final AtomicInteger accountNumberTransactionCounter = new AtomicInteger(0);
    private final AtomicInteger policyNumberTransactionCounter = new AtomicInteger(0);
    private final BatchFileDataHandler batchFileDataHandler;
    private final List<DocumentBatchFileDataRecord> documents = new CopyOnWriteArrayList<>();

    public DocumentProcessor(String stackCode, int batchSeqNo, DocumentHierarchy documentHierarchy) {
        this.rootFolderName = generateDefaultRootFolderName();
        this.totalPolicyTransactions = countTotalPolicies(documentHierarchy);
        this.stackCode = stackCode;
        this.batchSeqNo = batchSeqNo;
        this.batchFileDataHandler = new BatchFileDataHandler(stackCode, batchSeqNo);

    }

    public void incrementAgentNumberTransactionCounter() {
        agentNumberTransactionCounter.incrementAndGet();
    }

    public void incrementAccountNumberTransactionCounter() {
        accountNumberTransactionCounter.incrementAndGet();
    }

    public void incrementPolicyNumberTransactionCounter() {
        policyNumberTransactionCounter.incrementAndGet();
    }

    public boolean isFirstPolicyTransaction() {
        return policyNumberTransactionCounter.get() == 1;
    }

    public boolean isLastPolicyTransaction() {
        return policyNumberTransactionCounter.get() == totalPolicyTransactions;
    }

    public boolean isFirstAccountTransaction() {
        return accountNumberTransactionCounter.get() == 1;
    }

    public boolean isAgentNumberTransaction() {
        return agentNumberTransactionCounter.get() == 1;
    }

    public String generateBarcode() {
        return switch (DocumentEnums.StackType.valueOf(stackCode)) {
            case AE, AR, US -> new AEARUSGenerateBarcode().getBarcode();
            case BS -> new BSIHGenerateBarcode().getBarcode();
        };
    }


    public String getGunterBarcode(String barcode1T2Position) {
        return new BarcodeGunterHandler(batchSeqNo, getDDName()).barcode(LocalDate.now(), barcode1T2Position);
    }

    public String getDDName() {
        return String.format("%s%02d", stackCode, batchSeqNo);
    }

    public int countTotalPolicies(DocumentHierarchy documentHierarchy) {
        var hierarchy = documentHierarchy.hierarchy();
        return flattenHierarchy(hierarchy).size();
    }

    private String generateDefaultRootFolderName() {
        return AppConstants.DEFAULT_ROOT_FOLDER_PREFIX + LocalDate.now().format(DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));
    }

    private List<Document> flattenHierarchy(Map<String, Map<String, Map<String, Map<String, List<Document>>>>> hierarchy) {
        return hierarchy.values().stream()
                .flatMap(agent -> agent.values().stream())
                .flatMap(distributionBranch -> distributionBranch.values().stream())
                .flatMap(account -> account.values().stream())
                .flatMap(Collection::stream)
                .toList();
    }

    public BatchFileData batchFileData(DocumentGroupColumn documentGroupColumn, String copyType) {
        return batchFileDataHandler.createBatchFileData(rootFolderName, documentGroupColumn, copyType);
    }

    public String agentRecipient() {
        return DocumentEnums.RecipientCopyType.AGENT.getDescription();
    }

    public String insuredRecipient() {
        return DocumentEnums.RecipientCopyType.INSURED.getDescription();
    }
}
