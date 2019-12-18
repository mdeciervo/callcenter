package com.ejercicio.callcenter.domain;

import com.ejercicio.callcenter.domain.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado implements Comparable<Empleado> {

    private String nombre;
    private Tipo tipo;

    @Override
    public int compareTo(Empleado otroEmpleado) {
        if (this.tipo.prioridad() < otroEmpleado.tipo.prioridad()) {
            return -1;
        } else if (this.tipo.prioridad() > otroEmpleado.tipo.prioridad()) {
            return 1;
        }
        return 0;
    }

}
