package com.synechisveltiosi.documentpostprocessingsolution.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class JsonUtils {

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }



    public static <T> T convertJsonToObject(String json, Class<T> documentParserClass) {
        try {
            return getObjectMapper().readValue(json, documentParserClass);
        } catch (Exception e) {
            log.error("Error converting JSON to DocumentParser: {}", json, e);
            throw new IllegalArgumentException("Error converting JSON to DocumentParser: " + json, e);
        }
    }

    public static String convertObjectToJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error converting Object to JSON: {}", object, e);
            throw new IllegalArgumentException("Error converting Object to JSON: " + object, e);
        }
    }

    private static void ensureDirectoryExists(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    public static void writeFileFromObject(String stackName, int batchSeqNo, String fileName, String payload) {
        try {
            boolean invalid = Stream.of(stackName, batchSeqNo, fileName).map(String::valueOf).anyMatch(String::isBlank);
            if (!invalid) {
                String baseDir = "src/main/resources/files";
                Path stackDir = Paths.get(baseDir, stackName);
                Path batchDir = stackDir.resolve(String.format("%02d", batchSeqNo));

                ensureDirectoryExists(batchDir);

                Path filePath = batchDir.resolve(fileName);

                Files.writeString(filePath, payload);
            } else {
                log.error("Invalid stackName, batchSeqNo or fileName: {} {} {}", stackName, batchSeqNo, fileName);
                throw new IllegalArgumentException("Invalid stackName, batchSeqNo or fileName: " + stackName + ", " + batchSeqNo + ", " + fileName);
            }
        } catch (IOException e) {
            log.error("Error writing file: {}", e.getMessage(), e);
            throw new RuntimeException("Error writing file: " + e.getMessage(), e);
        }

    }

    public static <T> T readFromJsonFile(String documentFileName, Class<T> documentParserClass) {
        try {
            File file = Paths.get(documentFileName).toFile();
            return getObjectMapper().readValue(file, documentParserClass);
        } catch (Exception e) {
            log.error("Error reading JSON file: {}", documentFileName, e);
            throw new IllegalArgumentException("Error reading JSON file: " + documentFileName, e);
        }
    }
}
