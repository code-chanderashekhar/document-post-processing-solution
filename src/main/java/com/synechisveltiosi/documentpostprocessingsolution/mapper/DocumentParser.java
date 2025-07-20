package com.synechisveltiosi.documentpostprocessingsolution.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor // Required by Jackson for deserialization
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentParser {
    private Document document;

    @JsonCreator
    public DocumentParser(@JsonProperty("Document") Document document) {
        this.document = document;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static final class Document {
        @JsonIgnore
        private String stackName;
        @JsonIgnore
        private String agentNumber;
        @JsonIgnore
        private String distributionBranch;
        @JsonIgnore
        private String accountNumber;
        @JsonIgnore
        private String policyNumber;

        private Account account;
        private String documentIdentifier;
        private String dpsFilename_Acc;
        private Job job;
        private Policy policy;
        private List<PolicyPeriod> policyPeriods;
        private Status status;
        private Type type;
        @JsonProperty("printMetadata")
        private PrintMetadata printMetadata;

        @Builder
        public record Account(
                AccountHolder accountHolder,
                String accountNumber,
                AccountStatus accountStatus,
                String createdDate,
                boolean frozen,
                String id,
                IndustryCode industryCode,
                String numberOfContacts,
                OrganizationType organizationType,
                String originationDate,
                Currency preferredCoverageCurrency,
                Currency preferredSettlementCurrency,
                Language primaryLanguage,
                Locale primaryLocale,
                PrimaryLocation primaryLocation,
                List<ProducerCode> producerCodes
        ) {
            public record AccountHolder(String displayName, String id) {
            }

            public record AccountStatus(String code, String name) {
            }

            public record IndustryCode(String code, String description, String id) {
            }

            public record OrganizationType(String code, String name) {
            }

            public record Currency(String code, String name) {
            }

            public record Language(String code, String name) {
            }

            public record Locale(String code, String name) {
            }

            public record PrimaryLocation(String displayName, String id) {
            }

            public record ProducerCode(String displayName, String id) {
            }
        }

        public record Job() {
        }

        public record Policy(
                LossHistory lossHistory
        ) {
            public record LossHistory(
                    LossHistoryType lossHistoryType
            ) {
                public record LossHistoryType(String code, String name) {
                }
            }
        }

        @Builder
        public record PolicyPeriod(
                BaseState baseState,
                String ccPolicySystemId,
                List<Contact> contacts,
                List<Cost> costs,
                String createdDate,
                String editEffectiveDate,
                List<Form> forms,
                String id,
                String jobEffectiveDate,
                String jobNumber,
                JobStatus jobStatus,
                JobType jobType,
                Lines lines,
                List<Location> locations,
                Organization organization,
                Organization organizationOfService,
                PaymentInfo paymentInfo,
                String periodEnd,
                String periodStart,
                String policyNumber,
                PrimaryInsured primaryInsured,
                PrimaryLocation primaryLocation,
                ProducerCode producerCode,
                ProducerCode producerCodeOfService,
                Product product,
                QuoteType quoteType,
                String sliceDate,
                TaxesAndSurcharges taxesAndSurcharges,
                TermType termType,
                TotalCost totalCost,
                TotalPremium totalPremium,
                UwCompany uwCompany
        ) {
            public record BaseState(String code, String name) {
            }

            public record Contact(
                    AccountContact accountContact,
                    List<Address> addresses,
                    String authorizationID,
                    String ccPolicySystemId,
                    String companyName,
                    String contactSubtype,
                    String displayName,
                    String externalId,
                    String id,
                    IndustryCode industryCode,
                    OfficialIds officialIds,
                    List<PolicyContactRole> policyContactRoles,
                    PrimaryAddress primaryAddress
            ) {
                public record AccountContact(
                        String displayName,
                        String id
                ) {
                }

                public record Address(
                        String addressLine1,
                        AddressType addressType,
                        String city,
                        String country,
                        String displayName,
                        String id,
                        String internalId,
                        String postalCode,
                        boolean primary,
                        SpatialPoint spatialPoint,
                        State state
                ) {
                    public record AddressType(
                            String code,
                            String name
                    ) {
                    }

                    public record SpatialPoint(
                            String latitude,
                            String longitude
                    ) {
                    }

                    public record State(
                            String code,
                            String name
                    ) {
                    }
                }

                public record IndustryCode(
                        String code,
                        String description,
                        String id
                ) {
                }

                public record OfficialIds() {
                }

                public record PolicyContactRole(
                        String code,
                        String name
                ) {
                }

                public record PrimaryAddress(
                        String addressLine1,
                        Address.AddressType addressType,
                        String city,
                        String country,
                        String displayName,
                        String postalCode,
                        Address.SpatialPoint spatialPoint,
                        Address.State state
                ) {
                }
            }

            public record Cost(
                    String adjustedRate,
                    Amount amount,
                    String baseRate,
                    String basis,
                    boolean basisScalable,
                    ChargePattern chargePattern,
                    CostCode costCode,
                    Coverable coverable,
                    Coverage coverage,
                    String effectiveDate,
                    String expirationDate,
                    String id,
                    String internalId,
                    Jurisdiction jurisdiction,
                    int numDaysInRatedTerm,
                    boolean overridable,
                    OverrideSource overrideSource,
                    PolicyLine policyLine,
                    ProrationMethod prorationMethod,
                    RateAmountType rateAmountType,
                    int roundingLevel,
                    RoundingMode roundingMode,
                    String standardAdjustedRate,
                    Amount standardAmount,
                    String standardBaseRate,
                    Amount standardTermAmount,
                    boolean subjectToReporting,
                    Amount termAmount
            ) {
                public record Amount(String amount, String currency) {
                }

                public record ChargePattern(String code, String name) {
                }

                public record CostCode(String code, String description, String id) {
                }

                public record Coverable(String displayName, String id) {
                }

                public record Coverage(String displayName, String id) {
                }

                public record Jurisdiction(String code, String name) {
                }

                public record OverrideSource(String code, String name) {
                }

                public record PolicyLine(String displayName, String id) {
                }

                public record ProrationMethod(String code, String name) {
                }

                public record RateAmountType(String code, String name) {
                }

                public record RoundingMode(String code, String name) {
                }
            }

            public record Form(
                    String changeType,
                    String createTime,
                    String effectiveDate,
                    String expirationDate,
                    String formDescription,
                    String formEdition,
                    String formNumber,
                    String formPatternCode,
                    String formType,
                    String inferenceTime,
                    String updateTime
            ) {
            }

            public record JobStatus(String code, String name) {
            }

            public record JobType(String code, String name) {
            }

            public record Lines(@JsonProperty("TechnologyEOLine") TechnologyEOLine technologyEOLine) {
                public record TechnologyEOLine(
                        AreaPracticeIndustry areaPracticeIndustry,
                        String ccPolicySystemId,
                        CoverableJurisdiction coverableJurisdiction,
                        CoverageFormType coverageFormType,
                        List<CoveragesExt> coverages_ext,
                        boolean deregulation,
                        String internalId,
                        JobTypeDerived jobTypeDerived,
                        List<Location> locations,
                        Modifiers modifiers,
                        Pattern pattern,
                        String patternCode,
                        ProductEdition productEdition,
                        int yearsBusiness
                ) {
                    public record AreaPracticeIndustry(String code, String name) {
                    }

                    public record CoverableJurisdiction(String code, String name) {
                    }

                    public record CoverageFormType(String code, String name) {
                    }

                    public record CoveragesExt(
                            String clauseType,
                            String id,
                            Pattern pattern,
                            boolean selected,
                            List<Term> terms
                    ) {
                        public record Pattern(String displayName, String id) {
                        }

                        public record Term(
                                ChoiceValue choiceValue,
                                String covTermType,
                                String displayName,
                                String displayValue,
                                String id,
                                String dateTimeValue,
                                String dateValue,
                                String directValue
                        ) {
                            public record ChoiceValue(String code, String name) {
                            }
                        }
                    }

                    public record JobTypeDerived(String code, String name) {
                    }

                    public record Location(
                            String ccPolicySystemId,
                            String id,
                            String internalId,
                            PrimaryLocation location,
                            ProductEdition productEdition
                    ) {
                        public record PrimaryLocation(String displayName, String id) {
                        }

                        public record ProductEdition(String editionCode, String worksetUid) {
                        }
                    }

                    public record Modifiers(
                            Modifier TEOCommissionReduction,
                            Modifier TEOEORateObjectiveModifier,
                            Modifier TEOTechEORateSubjectiveModifier
                    ) {
                        public record Modifier(
                                boolean eligible,
                                String id,
                                ModifierType modifierType,
                                Pattern pattern,
                                RateFactors rateFactors,
                                String rateModifier,
                                String referenceDate,
                                State state,
                                boolean valueFinal
                        ) {
                            public record ModifierType(String code, String name) {
                            }

                            public record Pattern(String displayName, String id) {
                            }

                            public record RateFactors(RateFactor TEOCommissionReductionModifier) {
                                public record RateFactor(
                                        String assessment,
                                        String internalId,
                                        String justification,
                                        Pattern pattern
                                ) {
                                    public record Pattern(String displayName, String id) {
                                    }
                                }
                            }

                            public record State(String code, String name) {
                            }
                        }
                    }

                    public record Pattern(String displayName, String id) {
                    }

                    public record ProductEdition(String editionCode, String worksetUid) {
                    }
                }
            }

            public record Location(
                    AccountLocation accountLocation,
                    String addressLine1,
                    AddressType addressType,
                    String city,
                    String country,
                    String displayName,
                    String id,
                    String internalId,
                    int locationNum,
                    String postalCode,
                    boolean primary,
                    State state
            ) {
                public record AccountLocation(String displayName, String id) {
                }

                public record AddressType(String code, String name) {
                }

                public record State(String code, String name) {
                }
            }

            public record Organization(String displayName, String id) {
            }

            public record PaymentInfo(
                    BillingMethod billingMethod,
                    SelectedPaymentPlan selectedPaymentPlan
            ) {
                public record BillingMethod(String code, String name) {
                }

                public record SelectedPaymentPlan(
                        String billingId,
                        String id,
                        InvoicingFrequency invoicingFrequency,
                        String planName
                ) {
                    public record InvoicingFrequency(String code, String name) {
                    }
                }
            }

            public record PrimaryInsured(String displayName, String id) {
            }

            public record PrimaryLocation(String displayName, String id) {
            }

            public record ProducerCode(String displayName, String id) {
            }

            public record Product(String displayName, String id) {
            }

            public record QuoteType(String code, String name) {
            }

            public record TaxesAndSurcharges(String amount, String currency) {
            }

            public record TermType(String code, String name) {
            }

            public record TotalCost(String amount, String currency) {
            }

            public record TotalPremium(String amount, String currency) {
            }

            public record UwCompany(
                    String addressLine1_Ext,
                    String city_Ext,
                    Code code,
                    String displayName,
                    String id,
                    String internalId,
                    String postalCode,
                    State state
            ) {
                public record Code(String code, String name) {
                }

                public record State(String code, String name) {
                }
            }
        }

        public record Status(String code, String name) {
        }

        public record Type(String code, String name) {
        }
    }
}