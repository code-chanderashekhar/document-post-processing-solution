package com.synechisveltiosi.documentpostprocessingsolution.helper.handler;

import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.mapper.PrintMetadata;
import com.synechisveltiosi.documentpostprocessingsolution.model.embed.Address;

import java.util.Collections;
import java.util.List;

final class MetadataBuilderHelper {
    private static final boolean ENABLED = true;
    private static final String EMPTY_COMMENTS = "";

    static PrintMetadata.Distribution createDistribution(PrintMetadataAdder.DistributionParams params) {
        return PrintMetadata.Distribution.builder()
                .deliveryMethod(params.stackCode())
                .payPlanForInsured(params.payPlanForInsured())
                .payPlanWithCommissionForAgent(params.payPlanWithCommissionForAgent())
                .ratingWorksheetForAgent(params.ratingWorksheetForAgent())
                .build();
    }

    static PrintMetadata.Gunter createGunter(String ddName, String barcode, boolean isStart, boolean isEnd) {
        return PrintMetadata.Gunter.builder()
                .isOn(ENABLED)
                .barcode(barcode)
                .ddName(ddName)
                .start(isStart)
                .end(isEnd)
                .build();
    }

    static PrintMetadata.Banner createBanner(String accountNumber, List<String> policyNumbers) {
        return PrintMetadata.Banner.builder()
                .isOn(ENABLED)
                .accountNumber(accountNumber)
                .policyNumbers(policyNumbers)
                .build();
    }

    static PrintMetadata.Mailer createMailer(DocumentParser.Document.PolicyPeriod.Contact.PrimaryAddress primaryAddress) {
        var mailerAddress = PrintMetadata.Mailer.Address.builder()
                .line1(primaryAddress.addressLine1())
                .state(primaryAddress.state().code())
                .zip(primaryAddress.postalCode())
                .city(primaryAddress.city())
                .zip(primaryAddress.postalCode())
                .build();

        return PrintMetadata.Mailer.builder()
                .isOn(ENABLED)
                .address(Collections.singletonList(mailerAddress))
                .build();
    }

    static PrintMetadata.RequestorRoutingSheet createRequestorRoutingSheet(String agentNumber, String policyNumber) {
        return PrintMetadata.RequestorRoutingSheet.builder()
                .isOn(ENABLED)
                .operatorId(agentNumber)
                .policyNumber(policyNumber)
                .comments(EMPTY_COMMENTS)
                .build();
    }

    static PrintMetadata.BarcodeRest createBarcodeRest(String batchSeqNo, String copyType, String barcode) {
        return PrintMetadata.BarcodeRest.builder()
                .barcodeDetails(barcode)
                .copyType(copyType)
                .seqNumber(batchSeqNo)
                .build();
    }

    private MetadataBuilderHelper() {}
}
