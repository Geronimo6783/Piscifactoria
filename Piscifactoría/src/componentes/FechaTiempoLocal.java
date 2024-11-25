package componentes;

import java.time.LocalDateTime;

/**
 * Clase con utilidades relativas al tiempo y a la fecha.
 */
public class FechaTiempoLocal {

    /**
     * 
     * @return String con el tiempo y la fecha actual en formato [aaaa-mm-dd hh:mm:ss].
     */
    public static String obtenerFechaTiempoActual(){
        LocalDateTime tiempoFechaActual = LocalDateTime.now();
        return "[" + tiempoFechaActual.getYear() + "-" + tiempoFechaActual.getMonthValue() + "-" + tiempoFechaActual.getDayOfMonth() 
        + " " + tiempoFechaActual.getHour() + ":" + tiempoFechaActual.getMinute() + ":" + tiempoFechaActual.getSecond() + "]";
    }
}
