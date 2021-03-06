package kr.devbox.adadmin.config;

import org.springframework.http.HttpHeaders;
import kr.devbox.adadmin.dto.RestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<RestDTO> handleAnyException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        RestDTO result = new RestDTO(false, null, ex.getMessage());

        return new ResponseEntity<RestDTO>(
                result, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED
        );
    }
}
