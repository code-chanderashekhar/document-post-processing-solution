package com.synechisveltiosi.documentpostprocessingsolution.model.embed;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public record Address(String line1, String line2, String city, String state, String zip) {
}
