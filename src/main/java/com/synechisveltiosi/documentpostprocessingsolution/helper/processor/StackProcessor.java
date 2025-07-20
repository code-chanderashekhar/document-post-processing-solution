package com.synechisveltiosi.documentpostprocessingsolution.helper.processor;


import com.synechisveltiosi.documentpostprocessingsolution.helper.model.DocumentHierarchy;
import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface StackProcessor {
    void process(int batchSize, String stackCode, List<Document> documents);


    default DocumentHierarchy groupDocument(List<Document> documents, Function<Document, String> keyExtractorFunc) {
        return new DocumentHierarchy(documents.stream()
                .collect(Collectors.groupingBy(
                        keyExtractorFunc,
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                Document::getDistributionBranch,
                                LinkedHashMap::new,
                                Collectors.groupingBy(
                                        Document::getAccountNumber,
                                        LinkedHashMap::new,
                                        Collectors.groupingBy(
                                                Document::getPolicyNumber,
                                                LinkedHashMap::new,
                                                Collectors.toList()
                                        )
                                )
                        )
                )));
    }
    default List<DocumentHierarchy> splitDocuments(int batchSize, DocumentHierarchy documentHierarchy) {

        AtomicReference<List<DocumentHierarchy>> finalBatch = new AtomicReference<>(new ArrayList<>());
        AtomicReference<DocumentHierarchy> currentBatch = new AtomicReference<>(new DocumentHierarchy(Map.of()));
        AtomicInteger currentSize = new AtomicInteger();

        documentHierarchy.hierarchy().forEach((agentNumber, distributionBranchMap) -> {
            documentHierarchy.getAgentDocuments(agentNumber).forEach((branch, accountNumberMapMap) -> {
                documentHierarchy.getDistributionBranchDocuments(agentNumber, branch).forEach((account, policyNumberMapMap) -> {
                    documentHierarchy.getAccountDocuments(agentNumber, branch, account).forEach((policy, documents) -> {
                        currentSize.addAndGet(documents.size());

                        if (currentSize.get() >= batchSize) {
                            // Get current list, add current batch, and update the reference
                            List<DocumentHierarchy> currentList = finalBatch.get();
                            currentList.add(currentBatch.get());
                            finalBatch.set(currentList);

                            // Reset for next batch
                            currentBatch.set(new DocumentHierarchy(new LinkedHashMap<>()));
                            currentSize.set(documents.size());
                        }

                        // Add documents to current batch
                        Map<String, Map<String, Map<String, Map<String, List<Document>>>>> batchHierarchy =
                                new LinkedHashMap<>(currentBatch.get().hierarchy());

                        // Create the hierarchy structure
                        batchHierarchy.computeIfAbsent(agentNumber, k -> new LinkedHashMap<>())
                                .computeIfAbsent(branch, k -> new LinkedHashMap<>())
                                .computeIfAbsent(account, k -> new LinkedHashMap<>())
                                .put(policy, documents);

                        currentBatch.set(new DocumentHierarchy(batchHierarchy));
                    });
                });
            });
        });

        // Add the last batch if it's not empty
        if (currentBatch.get().hierarchy() != null && !currentBatch.get().hierarchy().isEmpty()) {
            List<DocumentHierarchy> currentList = finalBatch.get();
            currentList.add(currentBatch.get());
            finalBatch.set(currentList);
        }

        return finalBatch.get();


    }
}