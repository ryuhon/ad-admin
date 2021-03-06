package kr.devbox.adadmin.config;

import org.springframework.http.HttpHeaders;
import kr.devbox.adadmin.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<RestResponseDTO> handleAnyException( Exception ex,  WebRequest request) {
        ex.printStackTrace();
        RestResponseDTO result = new RestResponseDTO(false, null, ex.getMessage());

        return new ResponseEntity<RestResponseDTO>(
                result, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED
        );
    }
}
