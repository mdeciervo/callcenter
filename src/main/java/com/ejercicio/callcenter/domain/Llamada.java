package com.ejercicio.callcenter.domain;

import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Llamada {

    private static final int MAX_DURATION = 5;
    private static final int MIN_DURATION = 10;

    private Long id;
    private int duracion;
    private Empleado empleado;

    public Llamada(Long id) {
        this.id = id;
        Random random = new Random();
        this.duracion = random.nextInt(MIN_DURATION - MAX_DURATION) + MAX_DURATION;
    }

}
