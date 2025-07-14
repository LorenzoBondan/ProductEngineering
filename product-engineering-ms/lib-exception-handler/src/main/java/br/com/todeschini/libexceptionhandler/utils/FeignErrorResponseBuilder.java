package br.com.todeschini.libexceptionhandler.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.todeschini.libexceptionhandler.domain.FeignCustomError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class FeignErrorResponseBuilder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseEntity<FeignCustomError> buildFeignErrorResponse(Exception e, HttpStatus status, String message, String responseBodyRaw, HttpServletRequest request) {
        Map<String, Object> responseBodyMap = parseResponseBody(responseBodyRaw);

        FeignCustomError err = new FeignCustomError(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getRequestURI(),
                responseBodyMap
        );

        logError(e, request);
        return ResponseEntity.status(status).body(err);
    }

    private static Map<String, Object> parseResponseBody(String responseBodyRaw) {
        if (responseBodyRaw == null || responseBodyRaw.isBlank()) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(responseBodyRaw, Map.class);
        } catch (JsonProcessingException ex) {
            return Collections.singletonMap("rawResponse", responseBodyRaw);
        }
    }

    private static void logError(Exception e, HttpServletRequest request) {
        log.error("Error while processing @{} {} (Feign client error)", request.getMethod(), request.getRequestURI(), e);
    }
}
