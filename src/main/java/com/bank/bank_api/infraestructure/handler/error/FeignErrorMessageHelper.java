package com.bank.bank_api.infraestructure.handler.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FeignErrorMessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(FeignErrorMessageHelper.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String extractErrorMessage(FeignException ex) {
        String internalErrorMessage = null;
        String feignMessage = ex.getMessage();

        try {
            int startIndex = feignMessage.indexOf('{');
            int endIndex = feignMessage.lastIndexOf('}');
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String jsonString = feignMessage.substring(startIndex, endIndex + 1);
                JsonNode rootNode = objectMapper.readTree(jsonString);
                JsonNode messageNode = rootNode.get("message");
                if (messageNode != null && messageNode.isTextual()) {
                    internalErrorMessage = messageNode.asText();
                }
            }
        } catch (IOException e) {
            logger.warn("Error parsing Feign message body: {}", feignMessage, e);
        }
        return internalErrorMessage != null ? internalErrorMessage : feignMessage;
    }
}
