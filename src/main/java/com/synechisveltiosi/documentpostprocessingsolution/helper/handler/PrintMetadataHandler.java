package com.synechisveltiosi.documentpostprocessingsolution.helper.handler;

import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.PrintMetadata;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public non-sealed class PrintMetadataHandler implements PrintMetadataAdder {

    @Override
    public PrintMetadata.Mailer buildMailer(DocumentParser.Document.PolicyPeriod.Contact.PrimaryAddress primaryAddress) {
        return mailer(primaryAddress);
    }

    @Override
    public PrintMetadata.Distribution buildDistribution(DistributionParams params) {
        return distribution(params);
    }

    @Override
    public PrintMetadata.Gunter buildStartGunter(String ddName, String barcode) {
        return startGunter(ddName, barcode);
    }

    @Override
    public PrintMetadata.Gunter buildEndGunter(String ddName, String barcode) {
        return endGunter(ddName, barcode);
    }

    @Override
    public PrintMetadata.Banner buildBanner(String accountNumber, Set<String> policyNumbers) {
        return banner(accountNumber, policyNumbers.stream().toList());
    }


    @Override
    public PrintMetadata.RequestorRoutingSheet buildRequestorRoutingSheet(String agentNumber, String policyNumber) {
        return requestorRoutingSheet(agentNumber, policyNumber);
    }

    @Override
    public PrintMetadata.BarcodeRest buildBarcodeRest(int batchSequenceNo, String copyType, String barcode) {
        return barcodeRest(String.valueOf(batchSequenceNo), copyType, barcode);
    }
}
