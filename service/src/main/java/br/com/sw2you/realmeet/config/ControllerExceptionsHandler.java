package br.com.sw2you.realmeet.config;

import br.com.sw2you.realmeet.api.model.ResponseError;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionsHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, e);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception e) {
        return new ResponseEntity<>(
            new ResponseError().status(httpStatus.getReasonPhrase()).code(httpStatus.value()).message(e.getMessage()),
            httpStatus
        );
    }
}
