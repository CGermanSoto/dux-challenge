package com.duxsoftware.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(EquipoException.class)
    public ResponseEntity<Object> manejarEquipoException(EquipoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getCodigo());
        HttpStatus status = HttpStatus.valueOf(ex.getCodigo());
        return new ResponseEntity<>(errorResponse, status);
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
