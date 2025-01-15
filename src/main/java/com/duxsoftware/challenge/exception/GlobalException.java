package com.duxsoftware.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(EquipoException.class)
    public ResponseEntity<Object> manejarEquipoException(EquipoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getCodigo());
        HttpStatus status = HttpStatus.valueOf(ex.getCodigo());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> manejarTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String mensaje = String.format("El valor '%s' debe ser un número.", ex.getValue());
        ErrorResponse errorResponse = new ErrorResponse(mensaje, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    public static class ErrorResponse {
        private String mensaje;
        private int codigo;

        public ErrorResponse(String mensaje, int codigo) {
            this.mensaje = mensaje;
            this.codigo = codigo;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
    }
}
