package com.ejercicio.callcenter.dispatcher;

import com.ejercicio.callcenter.domain.Empleado;
import com.ejercicio.callcenter.domain.Llamada;
import com.ejercicio.callcenter.task.DispatchCallTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class Dispatcher {

    private final ExecutorService executorService;
    private final BlockingQueue<Empleado> empleados;
    private final BlockingQueue<Llamada> llamadas;

    public void dispatchCall(Llamada llamada) throws InterruptedException {
        log.info("Se recibio la llamada " + llamada.getId());
        llamadas.put(llamada);
        executorService.execute(new DispatchCallTask(empleados, llamadas));
    }

}
