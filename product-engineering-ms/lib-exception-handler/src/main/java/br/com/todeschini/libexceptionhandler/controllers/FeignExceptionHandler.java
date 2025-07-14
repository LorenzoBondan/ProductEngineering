package br.com.todeschini.libexceptionhandler.controllers;

import br.com.todeschini.libexceptionhandler.domain.FeignCustomError;
import br.com.todeschini.libexceptionhandler.utils.FeignErrorResponseBuilder;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@ConditionalOnClass(FeignException.class)
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<FeignCustomError> handleFeignUnauthorized(FeignException.Unauthorized e, HttpServletRequest request) {
        return FeignErrorResponseBuilder.buildFeignErrorResponse(
                e,
                HttpStatus.UNAUTHORIZED,
                "Unauthorized from external service",
                e.contentUTF8(),
                request
        );
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<FeignCustomError> handleFeignForbidden(FeignException.Forbidden e, HttpServletRequest request) {
        return FeignErrorResponseBuilder.buildFeignErrorResponse(
                e,
                HttpStatus.FORBIDDEN,
                "Forbidden from external service",
                e.contentUTF8(),
                request
        );
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<FeignCustomError> handleFeignNotFound(FeignException.NotFound e, HttpServletRequest request) {
        return FeignErrorResponseBuilder.buildFeignErrorResponse(
                e,
                HttpStatus.NOT_FOUND,
                "Resource not found (from another service)",
                e.contentUTF8(),
                request
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<FeignCustomError> handleGenericFeign(FeignException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.resolve(e.status());
        if (status == null) status = HttpStatus.BAD_GATEWAY;

        String message = status.is5xxServerError()
                ? "Server error from external service"
                : "Client error when calling external service";

        return FeignErrorResponseBuilder.buildFeignErrorResponse(
                e,
                status,
                message,
                e.contentUTF8(),
                request
        );
    }
}
