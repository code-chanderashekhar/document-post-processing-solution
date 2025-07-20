package com.synechisveltiosi.documentpostprocessingsolution.helper.handler;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class BarcodeGunterHandler {
    private final int batchSeqNo;
    private final String ddName;

    public static final String POSITION_7_10 = "0000";
    public static final String POSiTION_1_2_START_PAGE = "S0";
    public static final String POSITION_1_2_END_PAGE = "Z0";
    public static final DateTimeFormatter CYCLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


    public String barcode(LocalDate localDate, String position1T2) {
        return position1T2 +
                position3T6() +
                POSITION_7_10 +
                position11T16(localDate) +
                position16T20();
    }

    public String position16T20() {
        return String.format("%04d", batchSeqNo);
    }

    public String position3T6() {
        return ddName;
    }

    public String position11T16(LocalDate localDate) {
        return localDate.format(CYCLE_DATE_FORMATTER);
    }
}
