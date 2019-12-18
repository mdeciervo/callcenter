package com.ejercicio.callcenter.domain;

import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Llamada {

    private static final int DURACION_MINIMA = 5;
    private static final int DURACION_MAXIMA = 10;

    private Long id;
    private int duracion;
    private Empleado empleado;

    public Llamada(Long id) {
        this.id = id;
        Random random = new Random();
        this.duracion = random.nextInt(DURACION_MAXIMA - DURACION_MINIMA) + DURACION_MINIMA;
    }

}
