package componentes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * Permite la escritura de un error en el fichero de errores generales.
     * @param error Error a escribir.
     */
    public static void escribirError(String error){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("logs/0_errors.log"), true)));
            buferEscritura.append("\n" + obtenerFechaHora() + " " + error);
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("No se ha podido escribir en el fichero de errores.");
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
            buferEscritura.write("\n" + Logs.obtenerFechaHora() + " Piscifactoría inicial: " + piscifactoria + ".");
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
     * Registra la compra de una cantidad de comida a una piscifactoría determinada.
     * @param cantidad Cantidad de comida adquirida.
     * @param tipo Tipo de comida adquirida.
     * @param piscifactoria Piscifactoría a la que se le compró la comida.
     */
    public void registrarCompraComida(int cantidad, String tipo, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " " + cantidad + " de comida de tipo " + tipo + " comprada. Se almacena en la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra de la comida.");
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
     * Registra la compra de una cantidad de comida para el almacén central.
     * @param cantidad Cantidad de comida adquirida.
     * @param tipo Tipo de comida adquirida.
     */
    public void registrarCompraComida(int cantidad, String tipo){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " " + cantidad + " de comida de tipo " + tipo + " comprada. Se almacena en el almacén central.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra de la comida.");
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
     * Registra la compra de un pez.
     * @param pez Especie del pez que se ha comprado.
     * @param sexo Sexo del pez que se ha comprado. Si es true es hembra y si es false es macho.
     * @param tanque Número del tanque donde se incorpora el pez.
     * @param piscifactoria Piscifactoría a la que se incorpora el pez.
     */
    public void registrarCompraPez(String pez, boolean sexo, int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " " + pez + " " + ((sexo) ? "H" : "M") + " comprado. Añadido al tanque " + tanque + " de la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema al registrar la compra del pez.");
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
     * Registra la venta manual de peces de una piscifactoría.
     * @param pecesVendidos Número de peces vendidos.
     * @param piscifactoria Piscifactoría a la que pertenecían los peces.
     */
    public void registrarVentaPeces(int pecesVendidos, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Vendidos " + pecesVendidos + " de la piscifactoría " + piscifactoria + " de forma manual.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la venta de los peces.");
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
     * Registra la limpieza realizada en un tanque.
     * @param tanque Número del tanque al que se le realizó la limpieza.
     * @param piscifactoria Piscifactoría a la que pertenecía el tanque.
     */
    public void registrarLimpiezaTanque(int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Limpiado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la limpieza del tanque.");
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
     * Registra el vaciado de un tanque.
     * @param tanque Número del tanque vaciado.
     * @param piscifactoria Piscifactoría a la que pertenece el tanque.
     */
    public void registrarVaciadoTanque(int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Vaciado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el vaciado del tanque.");
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
     * Registra la compra de una piscifactoría.
     * @param tipo Tipo de la piscifactoría. Si es 0 es de río y si es 1 es de mar.
     * @param piscifactoria Nombre de la piscifactoría comprada.
     */
    public void registrarCompraPiscifactoria(int tipo, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Comprada la piscifactoría" + ((tipo == 0) ? "río " : "mar ") + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra de la piscifactoría.");
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
     * Registra la compra de un tanque para una piscifactoría.
     * @param piscifactoria Piscifactoría a la que se le compra el tanque.
     */
    public void registrarCompraTanque(String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Comprado un tanque para la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra del tanque.");
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
     * Registra la compra del almacén central.
     */
    public void registrarCompraAlmacenCentral(){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Comprado el almacén central.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra del almacén central.");
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
     * Registra una mejora realizada a una piscifactoría.
     * @param piscifactoria Piscifactoría a la que se le realiza la mejora.
     */
    public void registrarMejoraPiscifactoria(String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Mejorada la piscifactoría " + piscifactoria + " aumentando su capacidad de comida.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la mejora de la piscifactoría.");
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
     * Registra una mejora realizada al almacén central.
     */
    public void registrarMejoraAlmacenCentral(){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Mejorada el almacén central aumentando su capacidad de comida.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la mejora del almacén central.");
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
     * Registra el paso de un día.
     * @param dia Día que se acaba.
     */
    public void registrarPasoDia(int dia){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Fin del día " + dia + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el paso del día.");
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
     * Registra el uso de la opción oculta para añadir peces a una piscifactoría.
     * @param piscifactoria Piscifactoría a la que se le añaden los peces mediante la opción oculta.
     */
    public void registrarAnadirPecesOculto(String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Añadidos peces mediante la opción oculta a la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el añadido de peces mediante la opción oculta.");
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
     * Registra el uso de la opción oculta para obtener monedas.
     */
    public void registrarAnadirMonedasOculto(){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Añadidas monedas mediante la opción oculta.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el añadido de monedas mediante la opción oculta.");
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
     * Registra la salida de la partida.
     */
    public void registrarSalidaPartida(){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Cierre de la partida.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la salida de la partida.");
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
     * Registra la creación de una recompensa.
     */
    public void registrarCreacionRecompensa(){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Recompensa creada.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la creación de la recompensa.");
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
     * Registra la recepción de una recompensa.
     * @param receptor Nombre del receptor de la recompensa.
     */
    public void registrarRecompensaRecibida(String receptor){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Recompensa recibida por " + receptor + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la recepción de la recompensa.");
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
     * Registrar el uso de una recompensa.
     * @param recompensa Recompensa usada.
     */
    public void registrarUsoRecompensa(String recompensa){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Recompensa " + recompensa + " usada.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el uso de la recompensa..");
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

    public void registrarGeneracionPedido(int idPedido, String nombrePez){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Generado el pedido de " + nombrePez +" con referencia " + idPedido + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el nuevo pedido.");
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

    public void registrarPedidoCompletado(int idPedido, String nombrePez){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoLogs, true)));
            buferEscritura.append("\n" + Logs.obtenerFechaHora() + " Pedido de " + nombrePez + " con referencia " + idPedido + " enviado.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el pedido completado.");
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