package com.machertecnologia.crudmarcher.usuario.infrastructure;

import com.machertecnologia.crudmarcher.usuario.exception.UsuarioJaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UsuarioJaExisteException.class)
    private ResponseEntity<RestErrorMessage> usuarioJaExisteHandler(UsuarioJaExisteException usuarioJaExisteException) {
        String exceptionMessage = usuarioJaExisteException.getMessage() + usuarioJaExisteException.getUsername();
        var errorMessage = setErrorMessage(HttpStatus.BAD_REQUEST, exceptionMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RestErrorMessage> runtimeErrorHandler(RuntimeException runtimeException) {
        var errorMessage = setErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, runtimeException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }


    private RestErrorMessage setErrorMessage(HttpStatus httpStatus, String message) {
        return new RestErrorMessage(httpStatus, message);
    }
}
