package com.synechisveltiosi.documentpostprocessingsolution.model.converter;


import com.synechisveltiosi.documentpostprocessingsolution.mapper.DocumentParser;
import com.synechisveltiosi.documentpostprocessingsolution.util.JsonUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.text.StringEscapeUtils;


@Converter(autoApply = true)
public class DocumentPayloadConverter implements AttributeConverter<DocumentParser, String> {
    private static final String ERROR_CONVERTING_TO_JSON = "Error converting DocumentParser to JSON";
    private static final String ERROR_READING_JSON = "Error reading JSON to DocumentParser";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String ESCAPED_DOUBLE_QUOTE = "\\\"";
    private static final String DOUBLE_BACKSLASH = "\\\\";
    private static final String SINGLE_BACKSLASH = "\\";

    @Override
    public String convertToDatabaseColumn(DocumentParser documentPayload) {
        if (documentPayload == null) {
            return null;
        }
        try {
            return JsonUtils.convertObjectToJson(documentPayload);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_CONVERTING_TO_JSON, e);
        }
    }

    @Override
    public DocumentParser convertToEntityAttribute(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            String cleanedJson = cleanupJsonString(json);
            return JsonUtils.convertJsonToObject(StringEscapeUtils.unescapeJson(cleanedJson), DocumentParser.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_READING_JSON + ": " + json, e);
        }
    }

    private String cleanupJsonString(String json) {
        String cleanedJson = json;
        if (json.startsWith(SINGLE_BACKSLASH) && json.endsWith(SINGLE_BACKSLASH)) {
            cleanedJson = json.substring(1, json.length() - 1);
        }
        if (json.startsWith(DOUBLE_QUOTE) && json.endsWith(DOUBLE_QUOTE)) {
            cleanedJson = json.substring(1, json.length() - 1);
        }
        return cleanedJson
                .replace(ESCAPED_DOUBLE_QUOTE, DOUBLE_QUOTE)
                .replace(DOUBLE_BACKSLASH, SINGLE_BACKSLASH);
    }
}
