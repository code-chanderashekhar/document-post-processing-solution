package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

import com.synechisveltiosi.documentpostprocessingsolution.model.entity.Document;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record DocumentHierarchy(Map<String, Map<String, Map<String, Map<String, List<Document>>>>> hierarchy) {
    public Map<String, Map<String, Map<String, List<Document>>>> getAgentDocuments(String agentNumber) {
        return hierarchy.getOrDefault(agentNumber, Collections.emptyMap());
    }

    public Map<String, Map<String, List<Document>>> getDistributionBranchDocuments(String agentNumber, String distributionBranch) {
        return getAgentDocuments(agentNumber).getOrDefault(distributionBranch, Collections.emptyMap());
    }

    public Map<String, List<Document>> getAccountDocuments(String agentNumber, String distributionBranch, String accountNumber) {
        return getDistributionBranchDocuments(agentNumber, distributionBranch).getOrDefault(accountNumber, Collections.emptyMap());
    }

    public List<Document> getDocuments(String agentNumber, String distributionBranch, String accountNumber, String policyNumber) {
        return getAccountDocuments(agentNumber, distributionBranch, accountNumber).getOrDefault(policyNumber, Collections.emptyList());
    }

}
