package com.synechisveltiosi.documentpostprocessingsolution.util;


import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;

import java.util.UUID;

public class DocumentParserUtils {
    public static final String DOCUMENT_FILE_NAME = "src/main/resources/post-processing-payload-to-smartcomm.json";

    public static DocumentParser getDocumentParser(DataUtils.Transaction transaction) {
        DocumentParser.Document document = JsonUtils.readFromJsonFile(DOCUMENT_FILE_NAME, DocumentParser.class).getDocument();
        document.setDocumentIdentifier(UUID.randomUUID().toString());
        document.setAgentNumber(transaction.agentNumber());
        document.setAccountNumber(transaction.accountNumber());
        document.setPolicyNumber(transaction.policyNumber());
        document.setStackName(transaction.stackName());
        document.setDistributionBranch(transaction.distributionNumber());
        return new DocumentParser(document);
    }
}
