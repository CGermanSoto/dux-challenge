package com.duxsoftware.challenge.exception;

public class EquipoException extends RuntimeException {
    private final int codigo;

    public EquipoException(String mensaje, int codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
