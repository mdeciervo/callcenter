package com.ejercicio.callcenter.domain;

import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Llamada {
    
    private static final int MIN_DURATION = 5;
    private static final int MAX_DURATION = 10;

    private Long id;
    private int duracion;
    private Empleado empleado;
    

    public Llamada(Long id) {
        this.id = id;
        Random random = new Random();
        int low = MIN_DURATION;
        int high = MAX_DURATION;
        int result = random.nextInt(high - low) + low;
        this.duracion = result;
    }

}
