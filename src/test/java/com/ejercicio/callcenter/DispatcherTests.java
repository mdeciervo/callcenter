package com.ejercicio.callcenter;

import com.ejercicio.callcenter.dispatcher.Dispatcher;
import com.ejercicio.callcenter.domain.Empleado;
import com.ejercicio.callcenter.domain.Llamada;
import com.ejercicio.callcenter.domain.enums.Tipo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class DispatcherTests {

    private ExecutorService executorService;
    private Dispatcher dispatcher;
    
     private static final int MAX_CANTIDAD_LLAMADAS = 10;

    @Before
    public void setUp() {
        BlockingQueue<Empleado> empleados = new PriorityBlockingQueue<>();
        empleados.add(new Empleado("Juan - Supervisor", Tipo.SUPERVISOR));
        empleados.add(new Empleado("Jose - Operador", Tipo.OPERADOR));
        empleados.add(new Empleado("Pedro - Director", Tipo.DIRECTOR));
        empleados.add(new Empleado("Octavio - Operador", Tipo.OPERADOR));
        executorService = Executors.newCachedThreadPool();
        dispatcher = new Dispatcher(executorService, empleados, new ArrayBlockingQueue(MAX_CANTIDAD_LLAMADAS));
    }

    @Test
    public void dispatchCall_ConUnicaLlamadaYConEmpleadosDisponibles_AsignaLlamadaAOperador() throws InterruptedException {
        Llamada llamada = new Llamada(1L);

        dispatcher.dispatchCall(llamada);

        assertThat(Tipo.OPERADOR).isEqualTo(llamada.getEmpleado().getTipo());
    }

    @Test
    public void dispatchCall_Con2LlamadasYConEmpleadosDisponibles_AsignaLlamadaAOperadorYSupervisor() throws InterruptedException {
        Llamada llamada = new Llamada(1L);
        Llamada llamada2 = new Llamada(2L);
        Llamada llamada3 = new Llamada(3L);
        dispatcher.dispatchCall(llamada);
        dispatcher.dispatchCall(llamada2);
        dispatcher.dispatchCall(llamada3);

        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);

        assertThat(llamada).hasNoNullFieldsOrProperties();
        assertThat(llamada2).hasNoNullFieldsOrProperties();
        assertThat(llamada3).hasNoNullFieldsOrProperties();
        assertThat(llamada.getEmpleado()).isNotEqualTo(llamada2.getEmpleado());
        assertThat(llamada.getEmpleado()).isNotEqualTo(llamada3.getEmpleado());
        assertThat(llamada2.getEmpleado()).isNotEqualTo(llamada3.getEmpleado());
    }

//  * Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
//  - La solucion es que al utilizar una Cola de tipo PriorityBlockingQueue, y ademas 
//    usar los metodos take y put que son bloqueantes, cuando la cola de empleados esta vacia (es decir,
//    no hay ningun empleado libre), el hilo espera a que se agregue un nuevo empleado para continuar.

     
    @Test
    public void dispatchCall_Con10LlamadasYConEmpleadosDisponibles_modificaLlamadasConDiferentesEmpleados() throws InterruptedException {
        List<Llamada> llamadas = new ArrayList();
        for (long i = 1; i <= 10; i++) {
            Llamada llamada = new Llamada(i);
            llamadas.add(llamada);
            dispatcher.dispatchCall(llamada);
        }
        executorService.shutdown();
        executorService.awaitTermination(40, TimeUnit.SECONDS);

        assertThat(llamadas).extracting("empleado")
                .hasOnlyElementsOfType(Empleado.class);
    }
    
//  * Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
//  - La solucion es que al utilizar una Cola de tipo ArrayBlockingQueue de un tamaño fijo de 10 para 
//    las llamadas, al insertar una llamada con el metodo put, se asegura de bloquear al hilo y esperar 
//    a que se libere un lugar para insertarlo correctamente.

    @Test
    public void dispatchCall_Con11LlamadasYConEmpleadosDisponibles_modificaLlamadasConDiferentesEmpleados() throws InterruptedException {
        List<Llamada> llamadas = new ArrayList();
        for (long i = 1; i <= 11; i++) {
            Llamada llamada = new Llamada(i);
            llamadas.add(llamada);
            dispatcher.dispatchCall(llamada);
        }
        executorService.shutdown();
        executorService.awaitTermination(40, TimeUnit.SECONDS);

        assertThat(llamadas).extracting("empleado")
                .hasOnlyElementsOfType(Empleado.class);
    }

}
