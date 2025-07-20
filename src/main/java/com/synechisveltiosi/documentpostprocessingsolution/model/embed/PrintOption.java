package com.synechisveltiosi.documentpostprocessingsolution.model.embed;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintOption {
    private String printId;
    private String stackCode;
    private int agentCopyCount;
    private int insuredCopyCount;
    private boolean agentCopyPayPlan;
    private boolean insuredCopyPayPlan;
    private boolean deliverInsuredSeparately;
    private boolean payPlanWithCommissionForAgent;
    private boolean ratingWorksheetForAgent;
    private boolean excludeFIValidation;
    private String suppressAutoID;
    private boolean suppressPrint;
    private boolean handlingInstruction;
    private boolean canPrintFullPolicyDec;
    private String endorsementSeqNo;


}
