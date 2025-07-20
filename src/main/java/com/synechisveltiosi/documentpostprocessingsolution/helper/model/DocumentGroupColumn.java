package com.synechisveltiosi.documentpostprocessingsolution.helper.model;

public record DocumentGroupColumn(String agentNumber, String distributionBranch, String accountNumber,
                                  java.util.Set<String> policyNumbers, String policyNumber) {
}
