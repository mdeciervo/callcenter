package com.ejercicio.callcenter.domain.enums;

public enum Tipo {

    OPERADOR(1),
    SUPERVISOR(2),
    DIRECTOR(3);

    private final int prioridad;

    Tipo(int prioridad) {
        this.prioridad = prioridad;
    }

    public int prioridad() {
        return prioridad;
    }
}
