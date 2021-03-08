package kr.devbox.adadmin.config;

import org.springframework.http.HttpHeaders;
import kr.devbox.adadmin.dto.RestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestDTO> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        RestDTO result = new RestDTO(false, null, errors);
        return new ResponseEntity<RestDTO>(
                result, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
}
