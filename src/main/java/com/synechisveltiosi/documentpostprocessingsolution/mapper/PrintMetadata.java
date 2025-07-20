package com.synechisveltiosi.springboottasks.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record PrintMetadata(
        Distribution distribution,
        Gunter gunter,
        Mailer mailer,
        Banner banner,
        RequestorRoutingSheet requestorRoutingSheet,
        BarcodeRest barcodeRest
) {
    @Builder
    public record Distribution(
            String deliveryMethod,
            boolean payPlanWithCommissionForAgent,
            boolean payPlanForInsured,
            boolean ratingWorksheetForAgent
    ) {
    }

    @Builder
    public record Gunter(boolean isOn, boolean start, String ddName, boolean end, String barcode) {
    }

    @Builder
    public record Mailer(boolean isOn, List<Address> address) {
        @Builder
        public record Address(
                String type,
                String name,
                String line1,
                String line2,
                String city,
                String state,
                String zip,
                String contactNumber
        ) {
        }
    }

    @Builder
    public record Banner(boolean isOn, String accountNumber, List<String> policyNumbers) {
    }

    @Builder
    public record RequestorRoutingSheet(
            boolean isOn,
            String operatorId,
            String policyNumber,
            String comments
    ) {
    }

    @Builder
    public record BarcodeRest(
            String barcodeDetails,
            @JsonProperty("copytype") String copyType,
            @JsonProperty("Seqnumber") String seqNumber
    ) {
    }
}