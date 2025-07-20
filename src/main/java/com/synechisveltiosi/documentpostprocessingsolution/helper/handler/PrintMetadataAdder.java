package com.synechisveltiosi.documentpostprocessingsolution.helper.handler;

import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.PrintMetadata;

import java.util.List;
import java.util.Set;

public sealed interface PrintMetadataAdder permits PrintMetadataHandler {
    PrintMetadata.Mailer buildMailer(DocumentParser.Document.PolicyPeriod.Contact.PrimaryAddress primaryAddress);

    record DistributionParams(String stackCode,
                              boolean payPlanForInsured,
                              boolean payPlanWithCommissionForAgent,
                              boolean ratingWorksheetForAgent) {
    }

    PrintMetadata.Distribution buildDistribution(DistributionParams params);

    PrintMetadata.Gunter buildStartGunter(String ddName, String barcode);

    PrintMetadata.Gunter buildEndGunter(String ddName, String barcode);

    PrintMetadata.Banner buildBanner(String accountNumber, Set<String> policyNumbers);

    PrintMetadata.RequestorRoutingSheet buildRequestorRoutingSheet(String agentNumber, String policyNumber);

    PrintMetadata.BarcodeRest buildBarcodeRest(int batchSequenceNo, String copyType, String barcode);

    default PrintMetadata.Distribution distribution(DistributionParams params) {
        return MetadataBuilderHelper.createDistribution(params);
    }

    default PrintMetadata.Gunter startGunter(String ddName, String barcode) {
        return MetadataBuilderHelper.createGunter(ddName, barcode, true, false);
    }

    default PrintMetadata.Gunter endGunter(String ddName, String barcode) {
        return MetadataBuilderHelper.createGunter(ddName, barcode, false, true);
    }

    default PrintMetadata.Banner banner(String accountNumber, List<String> policyNumbers) {
        return MetadataBuilderHelper.createBanner(accountNumber, policyNumbers);
    }

    default PrintMetadata.Mailer mailer(DocumentParser.Document.PolicyPeriod.Contact.PrimaryAddress address) {
        return MetadataBuilderHelper.createMailer(address);
    }

    default PrintMetadata.RequestorRoutingSheet requestorRoutingSheet(String agentNumber, String policyNumber) {
        return MetadataBuilderHelper.createRequestorRoutingSheet(agentNumber, policyNumber);
    }

    default PrintMetadata.BarcodeRest barcodeRest(String batchSeqNo, String copyType, String barcode) {
        return MetadataBuilderHelper.createBarcodeRest(batchSeqNo, copyType, barcode);
    }
}

