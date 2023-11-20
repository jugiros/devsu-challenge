package com.challenge.CuentaMovimientos.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CuentaNotFoundException.class)
    public ResponseEntity<Object> handleCuentaNotFoundException(CuentaNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(TipoMovimientoNotFoundException.class)
    public ResponseEntity<Object> handleTipoMovimientoNotFoundException(TipoMovimientoNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(BalanceLessThanZeroException.class)
    public ResponseEntity<Object> handleBalanceLessThanZeroException(BalanceLessThanZeroException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
