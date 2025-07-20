package com.synechisveltiosi.documentpostprocessingsolution.helper.processor;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DocumentEnums {
    @Getter
    @RequiredArgsConstructor
    public enum StackType {
        AR("AR", "AgentRegular"), AE("AE", "Agent Express"), BS("BS", "Branch Special"), US("US", "USPS Shipping");
        final String code;
        final String description;
        }

    @RequiredArgsConstructor
    @Getter
    public enum RecipientCopyType {
        AGENT('A', "Agent"), INSURED('I', "Insured");
        final char code;
        final String description;
        }
}
