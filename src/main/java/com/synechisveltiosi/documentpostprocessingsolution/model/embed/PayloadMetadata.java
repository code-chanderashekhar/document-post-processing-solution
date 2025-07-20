package com.synechisveltiosi.documentpostprocessingsolution.model.embed;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadMetadata {
    private String jobId;
    private String pcJobNo;
}
