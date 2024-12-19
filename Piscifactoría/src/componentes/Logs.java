package componentes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;

/**
 * Clase que se encarga del registro de los logs de una partida del sistema.
 */
public class Logs {

    /**
     * Archivo donde se escriben los logs.
     */
    private File archivoLogs;

    /**
     * Constructor de objetos de la clase.
     * @param archivoLogs Archivo donde se escriben los logs.
     */
    public Logs(File archivoLogs){
        this.archivoLogs = archivoLogs;
    }

    /**
     * Inicia el log de una partida del sistema.
     * @param partida Nombre de la partida.
     * @param piscifactoria Piscifactoría inicial de la partida.
     */
    public void inicioLog(String partida, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs), "UTF-8"));
            buferEscritura.write(Logs.obtenerFechaHora() + " Inicio de la simulación " + partida + ".");
            buferEscritura.flush();
            buferEscritura.write(Logs.obtenerFechaHora() + " Piscifactoría inicial: " + piscifactoria + ".");
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de iniciar el log de la partida.");
        }
        finally{
            if(buferEscritura != null){
                try{
                    buferEscritura.close();
                }
                catch(IOException e){

                }
            }
        }
    }

    /**
     * Permite obtener la fecha y hora del sistema en formato [aaaa-mm-dd hh:mm:ss].
     * @return Fecha y hora del sistema en formato [aaaa-mm-dd hh:mm:ss].
     */
    private static String obtenerFechaHora(){
        LocalDateTime tiempoFechaActual = LocalDateTime.now();
        return "[" + tiempoFechaActual.getYear() + "-" + tiempoFechaActual.getMonthValue() + "-" + tiempoFechaActual.getDayOfMonth() 
        + " " + tiempoFechaActual.getHour() + ":" + tiempoFechaActual.getMinute() + ":" + tiempoFechaActual.getSecond() + "]";
    }
}