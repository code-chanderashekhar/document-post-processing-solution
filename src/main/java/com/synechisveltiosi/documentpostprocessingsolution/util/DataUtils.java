package com.synechisveltiosi.springboottasks.util;

import com.synechisveltiosi.springboottasks.model.entity.Document;
import com.synechisveltiosi.springboottasks.model.entity.PrintOption;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class DataUtils {

    private static final Random random = new SecureRandom();

    private static List<Transaction> getTransactions(String stackName) {
        return List.of(
                new Transaction(stackName, "081755", "1600", "0130697712", "Agent", "2070997250", LocalDateTime.now()),
                new Transaction(stackName, "081755", "1600", "0130697712", "Insured", "2070997250", LocalDateTime.now()),
                new Transaction(stackName, "081755", "1600", "0130697712", "Agent", "2070998107", LocalDateTime.now()),
                new Transaction(stackName, "081755", "1600", "0130697712", "Insured", "2070998107", LocalDateTime.now()),
                new Transaction(stackName, "008998", "5400", "0118709810", "Agent", "2074977834", LocalDateTime.now()),
                new Transaction(stackName, "008998", "5400", "0118709810", "Insured", "2074977834", LocalDateTime.now()),
                new Transaction(stackName, "013791", "9059", "0121611015", "Agent", "5085005080", LocalDateTime.now()),
                new Transaction(stackName, "013791", "9059", "0121611015", "Insured", "5085005080", LocalDateTime.now()),
                new Transaction(stackName, "013791", "9059", "0121611015", "Agent", "5085005788", LocalDateTime.now()),
                new Transaction(stackName, "013791", "9059", "0121611015", "Insured", "5085005788", LocalDateTime.now()),
                new Transaction(stackName, "025431", "3200", "0145632189", "Agent", "2080123456", LocalDateTime.now()),
                new Transaction(stackName, "025431", "3200", "0145632189", "Insured", "2080123456", LocalDateTime.now()),
                new Transaction(stackName, "036542", "4800", "0156789012", "Agent", "2081234567", LocalDateTime.now()),
                new Transaction(stackName, "036542", "4800", "0156789012", "Insured", "2081234567", LocalDateTime.now()),
                new Transaction(stackName, "047653", "6400", "0167890123", "Agent", "2082345678", LocalDateTime.now()),
                new Transaction(stackName, "047653", "6400", "0167890123", "Insured", "2082345678", LocalDateTime.now()),
                new Transaction(stackName, "058764", "8000", "0178901234", "Agent", "2083456789", LocalDateTime.now()),
                new Transaction(stackName, "058764", "8000", "0178901234", "Insured", "2083456789", LocalDateTime.now()),
                new Transaction(stackName, "069875", "9600", "0189012345", "Agent", "2084567890", LocalDateTime.now()),
                new Transaction(stackName, "069875", "9600", "0189012345", "Insured", "2084567890", LocalDateTime.now()),
                new Transaction(stackName, "070986", "1100", "0190123456", "Agent", "2085678901", LocalDateTime.now()),
                new Transaction(stackName, "070986", "1100", "0190123456", "Insured", "2085678901", LocalDateTime.now()),
                new Transaction(stackName, "081097", "2700", "0201234567", "Agent", "2086789012", LocalDateTime.now()),
                new Transaction(stackName, "081097", "2700", "0201234567", "Insured", "2086789012", LocalDateTime.now()),
                new Transaction(stackName, "092108", "4300", "0212345678", "Agent", "2087890123", LocalDateTime.now()),
                new Transaction(stackName, "092108", "4300", "0212345678", "Insured", "2087890123", LocalDateTime.now()),

                new Transaction(stackName, "103219", "5900", "0223456789", "Agent", "2088901234", LocalDateTime.now()),
                new Transaction(stackName, "103219", "5900", "0223456789", "Insured", "2088901234", LocalDateTime.now()),
                new Transaction(stackName, "114320", "7500", "0234567890", "Agent", "2089012345", LocalDateTime.now()),
                new Transaction(stackName, "114320", "7500", "0234567890", "Insured", "2089012345", LocalDateTime.now()),
                new Transaction(stackName, "125431", "9100", "0245678901", "Agent", "2090123456", LocalDateTime.now()),
                new Transaction(stackName, "125431", "9100", "0245678901", "Insured", "2090123456", LocalDateTime.now()),
                new Transaction(stackName, "136542", "1700", "0256789012", "Agent", "2091234567", LocalDateTime.now()),
                new Transaction(stackName, "136542", "1700", "0256789012", "Insured", "2091234567", LocalDateTime.now()),
                new Transaction(stackName, "147653", "3300", "0267890123", "Agent", "2092345678", LocalDateTime.now()),
                new Transaction(stackName, "147653", "3300", "0267890123", "Insured", "2092345678", LocalDateTime.now()),
                new Transaction(stackName, "158764", "4900", "0278901234", "Agent", "2093456789", LocalDateTime.now()),
                new Transaction(stackName, "158764", "4900", "0278901234", "Insured", "2093456789", LocalDateTime.now()),
                new Transaction(stackName, "169875", "6500", "0289012345", "Agent", "2094567890", LocalDateTime.now()),
                new Transaction(stackName, "169875", "6500", "0289012345", "Insured", "2094567890", LocalDateTime.now()),
                new Transaction(stackName, "170986", "8100", "0290123456", "Agent", "2095678901", LocalDateTime.now()),
                new Transaction(stackName, "170986", "8100", "0290123456", "Insured", "2095678901", LocalDateTime.now()),
                new Transaction(stackName, "181097", "9700", "0301234567", "Agent", "2096789012", LocalDateTime.now()),
                new Transaction(stackName, "181097", "9700", "0301234567", "Insured", "2096789012", LocalDateTime.now()),
                new Transaction(stackName, "192108", "1300", "0312345678", "Agent", "2097890123", LocalDateTime.now()),
                new Transaction(stackName, "192108", "1300", "0312345678", "Insured", "2097890123", LocalDateTime.now()),
                new Transaction(stackName, "203219", "2900", "0323456789", "Agent", "2098901234", LocalDateTime.now()),
                new Transaction(stackName, "203219", "2900", "0323456789", "Insured", "2098901234", LocalDateTime.now()),
                new Transaction(stackName, "214320", "4500", "0334567890", "Agent", "2099012345", LocalDateTime.now()),
                new Transaction(stackName, "214320", "4500", "0334567890", "Insured", "2099012345", LocalDateTime.now()),
                new Transaction(stackName, "225431", "6100", "0345678901", "Agent", "2100123456", LocalDateTime.now()),
                new Transaction(stackName, "225431", "6100", "0345678901", "Insured", "2100123456", LocalDateTime.now()),
                new Transaction(stackName, "236542", "7700", "0356789012", "Agent", "2101234567", LocalDateTime.now()),
                new Transaction(stackName, "236542", "7700", "0356789012", "Insured", "2101234567", LocalDateTime.now()),
                new Transaction(stackName, "247653", "9300", "0367890123", "Agent", "2102345678", LocalDateTime.now()),
                new Transaction(stackName, "247653", "9300", "0367890123", "Insured", "2102345678", LocalDateTime.now()),
                new Transaction(stackName, "258764", "1900", "0378901234", "Agent", "2103456789", LocalDateTime.now()),
                new Transaction(stackName, "258764", "1900", "0378901234", "Insured", "2103456789", LocalDateTime.now()),
                new Transaction(stackName, "269875", "3500", "0389012345", "Agent", "2104567890", LocalDateTime.now()),
                new Transaction(stackName, "269875", "3500", "0389012345", "Insured", "2104567890", LocalDateTime.now()),
                new Transaction(stackName, "270986", "5100", "0390123456", "Agent", "2105678901", LocalDateTime.now()),
                new Transaction(stackName, "270986", "5100", "0390123456", "Insured", "2105678901", LocalDateTime.now()),
                new Transaction(stackName, "281097", "6700", "0401234567", "Agent", "2106789012", LocalDateTime.now()),
                new Transaction(stackName, "281097", "6700", "0401234567", "Insured", "2106789012", LocalDateTime.now()),
                new Transaction(stackName, "292108", "8300", "0412345678", "Agent", "2107890123", LocalDateTime.now()),
                new Transaction(stackName, "292108", "8300", "0412345678", "Insured", "2107890123", LocalDateTime.now()),
                new Transaction(stackName, "303219", "9900", "0423456789", "Agent", "2108901234", LocalDateTime.now()),
                new Transaction(stackName, "303219", "9900", "0423456789", "Insured", "2108901234", LocalDateTime.now()),
                new Transaction(stackName, "314320", "1500", "0434567890", "Agent", "2109012345", LocalDateTime.now()),
                new Transaction(stackName, "314320", "1500", "0434567890", "Insured", "2109012345", LocalDateTime.now()),
                new Transaction(stackName, "325431", "3100", "0445678901", "Agent", "2110123456", LocalDateTime.now()),
                new Transaction(stackName, "325431", "3100", "0445678901", "Insured", "2110123456", LocalDateTime.now()),
                new Transaction(stackName, "336542", "4700", "0456789012", "Agent", "2111234567", LocalDateTime.now()),
                new Transaction(stackName, "336542", "4700", "0456789012", "Insured", "2111234567", LocalDateTime.now()),
                new Transaction(stackName, "347653", "6300", "0467890123", "Agent", "2112345678", LocalDateTime.now()),
                new Transaction(stackName, "347653", "6300", "0467890123", "Insured", "2112345678", LocalDateTime.now()),
                new Transaction(stackName, "358764", "7900", "0478901234", "Agent", "2113456789", LocalDateTime.now()),
                new Transaction(stackName, "358764", "7900", "0478901234", "Insured", "2113456789", LocalDateTime.now()),
                new Transaction(stackName, "369875", "9500", "0489012345", "Agent", "2114567890", LocalDateTime.now()),
                new Transaction(stackName, "369875", "9500", "0489012345", "Insured", "2114567890", LocalDateTime.now()),
                new Transaction(stackName, "370986", "1100", "0490123456", "Agent", "2115678901", LocalDateTime.now()),
                new Transaction(stackName, "370986", "1100", "0490123456", "Insured", "2115678901", LocalDateTime.now()),
                new Transaction(stackName, "381097", "2700", "0501234567", "Agent", "2116789012", LocalDateTime.now()),
                new Transaction(stackName, "381097", "2700", "0501234567", "Insured", "2116789012", LocalDateTime.now()),
                new Transaction(stackName, "392108", "4300", "0512345678", "Agent", "2117890123", LocalDateTime.now()),
                new Transaction(stackName, "392108", "4300", "0512345678", "Insured", "2117890123", LocalDateTime.now()),
                new Transaction(stackName, "403219", "5900", "0523456789", "Agent", "2118901234", LocalDateTime.now()),
                new Transaction(stackName, "403219", "5900", "0523456789", "Insured", "2118901234", LocalDateTime.now()),
                new Transaction(stackName, "414320", "7500", "0534567890", "Agent", "2119012345", LocalDateTime.now()),
                new Transaction(stackName, "414320", "7500", "0534567890", "Insured", "2119012345", LocalDateTime.now()),
                new Transaction(stackName, "425431", "9100", "0545678901", "Agent", "2120123456", LocalDateTime.now()),
                new Transaction(stackName, "425431", "9100", "0545678901", "Insured", "2120123456", LocalDateTime.now()),
                new Transaction(stackName, "436542", "1700", "0556789012", "Agent", "2121234567", LocalDateTime.now()),
                new Transaction(stackName, "436542", "1700", "0556789012", "Insured", "2121234567", LocalDateTime.now()),
                new Transaction(stackName, "447653", "3300", "0567890123", "Agent", "2122345678", LocalDateTime.now()),
                new Transaction(stackName, "447653", "3300", "0567890123", "Insured", "2122345678", LocalDateTime.now()),
                new Transaction(stackName, "458764", "4900", "0578901234", "Agent", "2123456789", LocalDateTime.now()),
                new Transaction(stackName, "458764", "4900", "0578901234", "Insured", "2123456789", LocalDateTime.now()),
                new Transaction(stackName, "469875", "6500", "0589012345", "Agent", "2124567890", LocalDateTime.now()),
                new Transaction(stackName, "469875", "6500", "0589012345", "Insured", "2124567890", LocalDateTime.now()),
                new Transaction(stackName, "470986", "8100", "0590123456", "Agent", "2125678901", LocalDateTime.now()),
                new Transaction(stackName, "470986", "8100", "0590123456", "Insured", "2125678901", LocalDateTime.now()),
                new Transaction(stackName, "481097", "9700", "0601234567", "Agent", "2126789012", LocalDateTime.now()),
                new Transaction(stackName, "481097", "9700", "0601234567", "Insured", "2126789012", LocalDateTime.now()),
                new Transaction(stackName, "492108", "1300", "0612345678", "Agent", "2127890123", LocalDateTime.now()),
                new Transaction(stackName, "492108", "1300", "0612345678", "Insured", "2127890123", LocalDateTime.now()),
                new Transaction(stackName, "503219", "2900", "0623456789", "Agent", "2128901234", LocalDateTime.now()),
                new Transaction(stackName, "503219", "2900", "0623456789", "Insured", "2128901234", LocalDateTime.now()),
                new Transaction(stackName, "514320", "4500", "0634567890", "Agent", "2129012345", LocalDateTime.now()),
                new Transaction(stackName, "514320", "4500", "0634567890", "Insured", "2129012345", LocalDateTime.now())
        );

    }

    public static List<Document> getDocumentAR() {
        return getTransactions("AR")
                .stream().map(transaction -> mapToDocumentAgentNumber("AR", transaction)).toList();
    }

    public static List<Document> getDocumentAE() {
        return getTransactions("AE")
                .stream().map(transaction -> mapToDocumentAgentNumber("AE", transaction)).toList();
    }

    public static List<Document> getDocumentIH() {
        return getTransactions("IH")
                .stream().map(transaction -> mapToDocumentRequestorID("IH", transaction)).toList();
    }

    public static List<Document> getDocumentUS() {
        return getTransactions("US")
                .stream().map(transaction -> mapToDocumentAgentNumber("US", transaction)).toList();
    }

    public static List<Document> getDocumentBS() {
        return getTransactions("BS")
                .stream().map(transaction -> mapToDocumentRequestorID("BS", transaction)).toList();
    }

    private static Document mapToDocumentAgentNumber(String stackName, Transaction transaction) {
        return Document.builder()
                .agentNumber(transaction.agentNumber())
                .distributionBranch(transaction.distributionNumber())
                .accountNumber(transaction.accountNumber())
                .policyNumber(transaction.policyNumber())
                .payload(DocumentParserUtils.getDocumentParser(transaction))
                .createdDateTime(transaction.timestamp())
                .printOption(buildPrintOptionWithStackName(stackName))
                .build();
    }

    private static PrintOption buildPrintOptionWithStackName(String stackName) {
        return PrintOption.builder()
                .deliveryMethodStackCode(stackName)
                .payPlanForInsured(random.nextBoolean())
                .payPlanWithCommissionForAgent(random.nextBoolean())
                .ratingWorksheetForAgent(random.nextBoolean()).build();
    }

    private static Document mapToDocumentRequestorID(String stackName, Transaction transaction) {
        return Document.builder()
                .requestorId(transaction.accountNumber())
                .distributionBranch(transaction.distributionNumber())
                .accountNumber(transaction.accountNumber())
                .policyNumber(transaction.policyNumber())
                .payload(DocumentParserUtils.getDocumentParser(transaction))
                .createdDateTime(transaction.timestamp())
                .printOption(buildPrintOptionWithStackName(stackName))
                .build();
    }

    public static String generateAgentNumber() {
        return String.format("%06d", random.nextInt(1_000_000));
    }

    public static String generateDistributionBranch() {
        return String.format("%04d", random.nextInt(10_000));
    }

    public static String generateAccountNumber() {
        return String.format("%08d", random.nextInt(100_000_000));
    }

    public static String generatePolicyNumber() {
        return String.format("%08d", random.nextInt(100_000_000));
    }

    public record Transaction(String stackName, String agentNumber, String distributionNumber, String accountNumber,
                              String recipient, String policyNumber, LocalDateTime timestamp) {

    }

}
