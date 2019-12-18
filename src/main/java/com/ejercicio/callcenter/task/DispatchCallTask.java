package com.ejercicio.callcenter.task;

import com.ejercicio.callcenter.domain.Empleado;
import com.ejercicio.callcenter.domain.Llamada;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatchCallTask implements Runnable {

    private final BlockingQueue<Empleado> empleados;
    private final BlockingQueue<Llamada> llamadas;

    public DispatchCallTask(BlockingQueue<Empleado> empleados, BlockingQueue<Llamada> llamadas) {
        this.empleados = empleados;
        this.llamadas = llamadas;
    }

    @Override
    public void run() {
        try {
            log.info("Empleados disponibles: {}", empleados.size());
            Empleado empleado = empleados.take();
            Llamada llamada = llamadas.take();
            llamada.setEmpleado(empleado);
            log.info("La llamada {} fue atendida por {}", llamada.getId(), empleado.getNombre());
            Thread.sleep(TimeUnit.SECONDS.toMillis(llamada.getDuracion()));
            log.info("La llamada {} atendida por {} duró {} segundos", llamada.getId(), empleado.getNombre(), llamada.getDuracion());
            empleados.put(empleado);
        } catch (InterruptedException ex) {
            Logger.getLogger(DispatchCallTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
