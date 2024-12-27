package componentes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Clase con utilidades para la creación de transcripciones de una partida del sistema.
 */
public class Transcripciones {

    /**
     * Archivo donde se realizarán las trancripciones.
     */
    private File archivoTranscripciones;

    /**
     * Constructor de objetos.
     * @param archivoTranscripciones Archivo donde se realizarán las transcripciones.
     */
    public Transcripciones(File archivoTranscripciones){
        this.archivoTranscripciones = archivoTranscripciones;
    }

    /**
     * Inicia el informe con información inicial de la partida.
     * @param nombrePartida Nombre de la partida.
     * @param dinero Dinero del que se dispone inicialmente.
     * @param pecesRio Peces de río implementados en la simulación.
     * @param pecesMar Peces de mar implementados en la simulación.
     * @param piscifactoriaInicial Piscifactoría inicial con la que se inicia la simulación.
     */
    public void iniciarTranscripciones(String nombrePartida, int dinero, String[] pecesRio, String[] pecesMar, String piscifactoriaInicial){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones), "UTF-8"));
            buferEscritura.write("=============== Aranque ===============");
            buferEscritura.flush();
            buferEscritura.write("Inicio de la simulación " + nombrePartida + ".");
            buferEscritura.flush();
            buferEscritura.write("============== Dinero ===============");
            buferEscritura.flush();
            buferEscritura.write("Dinero: " + dinero + ".");
            buferEscritura.flush();
            buferEscritura.write("=============== Peces ===============");
            buferEscritura.flush();
            buferEscritura.write("Río:");
            buferEscritura.flush();
            for(String pez : pecesRio){
                buferEscritura.write("-" + pez);
                buferEscritura.flush();
            }
            buferEscritura.write("Mar:");
            buferEscritura.flush();
            for(String pez : pecesMar){
                buferEscritura.write("-" + pez);
                buferEscritura.flush();
            }
            buferEscritura.write("----------------------------------------------------------");
            buferEscritura.flush();
            buferEscritura.write("Piscifactoría inicial: " + piscifactoriaInicial);
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de iniciar las transcripciones.");
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
     * Inicia el informe con información inicial de la partida.
     * @param nombrePartida Nombre de la partida.
     * @param dinero Dinero del que se dispone inicialmente.
     * @param pecesRio Peces de río implementados en la simulación.
     * @param pecesMar Peces de mar implementados en la simulación.
     * @param extras Extras implementados en la simulación.
     * @param piscifactoriaInicial Piscifactoría inicial con la que se inicia la simulación.
     */
    public void iniciarTranscripciones(String nombrePartida, int dinero, String[] pecesRio, String[] pecesMar, String[] extras, String piscifactoriaInicial){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones), "UTF-8"));
            buferEscritura.write("=============== Aranque ===============");
            buferEscritura.flush();
            buferEscritura.write("Inicio de la simulación " + nombrePartida + ".");
            buferEscritura.flush();
            buferEscritura.write("============== Dinero ===============");
            buferEscritura.flush();
            buferEscritura.write("Dinero: " + dinero + ".");
            buferEscritura.flush();
            buferEscritura.write("=============== Peces ===============");
            buferEscritura.flush();
            buferEscritura.write("Río:");
            buferEscritura.flush();
            for(String pez : pecesRio){
                buferEscritura.write("-" + pez);
                buferEscritura.flush();
            }
            buferEscritura.write("Mar:");
            buferEscritura.flush();
            for(String pez : pecesMar){
                buferEscritura.write("-" + pez);
                buferEscritura.flush();
            }
            buferEscritura.write("========= Extras =========");
            buferEscritura.flush();
            for(String extra : extras){
                buferEscritura.write(extra);
                buferEscritura.flush();
            }
            buferEscritura.write("----------------------------------------------------------");
            buferEscritura.flush();
            buferEscritura.write("Piscifactoría inicial: " + piscifactoriaInicial);
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de iniciar las transcripciones.");
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
     * Resgistra la compra de una cantidad de comida para una piscifactoría.
     * @param cantidadComida Cantidad de comida comprada.
     * @param tipoComida Tipo de comida comprada.
     * @param monedas Monedas por las que se compró la comida.
     * @param piscifactoria Piscifactoría a la que se le compró la comida.
     */
    public void registrarCompraComida(int cantidadComida, String tipoComida, int monedas, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append(cantidadComida + " de comida de tipo " + tipoComida + "comprada por " + monedas + ". Se almacena en la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema al registrar la compra de la comida.");
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
     * Resgistra la compra de una cantidad de comida para el almacén central.
     * @param cantidadComida Cantidad de comida comprada.
     * @param tipoComida Tipo de comida comprada.
     * @param monedas Monedas por las que se compró la comida.
     */
    public void registrarCompraComida(int cantidadComida, String tipoComida, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append(cantidadComida + " de comida de tipo " + tipoComida + "comprada por " + monedas + ". Se almacena en el almacén central.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema al registrar la compra de la comida.");
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
     * Registra la compra de un pez en el archivo de transcripciones.
     * @param pez Raza del pez comprado.
     * @param sexo Sexo del pez comprado.
     * @param monedas Monedas por las que se compró el pez.
     * @param tanque Tanque donde el pez fue incorporado.
     * @param piscifactoria Piscifactoria donde el pez fue incorporado.
     */
    public void registrarCompraPeces(String pez, boolean sexo, int monedas, int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append(pez + " " + ((sexo) ? "H" : "M") + " comprado por " + monedas + " monedas. Añadido al tanque " + tanque + " de la piscifacotría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la compra de los peces.");
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
     * Registra una venta de peces en el archivo de transcripciones.
     * @param pecesVendidos Número de peces vendidos.
     * @param piscifactoria Piscifactoría de la que se venden los peces.
     * @param monedas Monedas obtenidas con la venta.
     */ 
    public void registrarVentaPeces(int pecesVendidos, String piscifactoria, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Vendidos " + pecesVendidos + " peces de la piscifactoría " + piscifactoria + " de forma manual por " + monedas + " monedas.");
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
     * Registra la limpieza de peces de un tanque.
     * @param tanque Tanque que se ha limpiado.
     * @param piscifactoria Piscifactoría a la que pertenece el tanque que se ha limpiado.
     */
    public void registrarLimpiezaTanque(int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Limpiado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".");
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
     * Registrar el vaciado de un tanque.
     * @param tanque Tanque que se ha vaciado.
     * @param piscifactoria Piscifactoría a la que pertenece el tanque que se ha vaciado.
     */
    public void registrarVaciadoTanque(int tanque, String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Vaciado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el vacio del tanque");
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
     * @param tipo Tipo de la piscifactoría si es 0 es de río y si es 1 es de mar.
     * @param piscifactoria Nombre de la piscifactoría comprada.
     * @param monedas Monedas por las que se compró la piscifactoría.
     */
    public void resgistrarCompraPiscifactoria(int tipo, String piscifactoria, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Comprada la piscifactoría de " + ((tipo == 0) ? "río " : "mar ") + piscifactoria + "por " + monedas + " monedas.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de rigistrar la compra de la piscifactoría.");
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
     * Registrar la compra de un tanque.
     * @param numeroTanque Número del tanque comprado.
     * @param piscifactoria Piscifactoría para la que se compró el tanque.
     * @param monedas Monedas por las que se compró el tanque.
     */
    public void registrarCompraTanque(int numeroTanque, String piscifactoria, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Comprado un tanque número " + numeroTanque + " de la piscifactoría " + piscifactoria + " por " + monedas + "monedas.");
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
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Comprado el almacén central por 2000 monedas.");
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
     * Registra la mejora de una piscifactoría.
     * @param piscifactoria Piscifactoría a la que se le realiza la mejora.
     * @param nuevaCapacidad Nueva capacidad de comida de la piscifactoría.
     * @param monedas Monedas que costó la realización de la mejora.
     */
    public void registrarMejoraPiscifactoria(String piscifactoria, int nuevaCapacidad, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Mejorada la piscifactoría " + piscifactoria + " aumentando su capacidad de comida hasta un total de " + nuevaCapacidad + " por " + monedas + " monedas.");
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
     * Registrar la mejora del almacén central.
     * @param nuevaCapacidad Nueva capacidad de comida del almacén central.
     * @param monedas Monedas por las que se realizó la mejora.
     */
    public void registrarMejoraAlmacenCentral(int nuevaCapacidad, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Mejorado el almacén central aumentando su capacidad de comida hasta un total de " + nuevaCapacidad + " por " + monedas + " monedas.");
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
     * @param pecesDeRio Peces de río que hay en la simulación después de pasar el día.
     * @param pecesDeMar Peces de mar que hay en la simulación después de pasar el día.
     * @param monedas Monedas obtenidas por la venta de peces al pasar el día.
     * @param pecesVendidos Número de peces vendidos al pasar el día.
     */
    public void registrarPasoDia(int dia, int pecesDeRio, int pecesDeMar, int monedas, int pecesVendidos){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Fin del día " + dia + ".");
            buferEscritura.flush();
            buferEscritura.append("Peces actuales, " + pecesDeRio + " de río " + pecesDeMar + " de mar.");
            buferEscritura.flush();
            buferEscritura.append(monedas + " monedas ganadas por un total de " + pecesVendidos + ".");
            buferEscritura.flush();
            buferEscritura.append("-------------------------");
            buferEscritura.flush();
            buferEscritura.append(">>>Inicio del día " + (dia + 1) + ".");
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
     * Registra el uso de la opción oculta para añadir peces.
     * @param piscifactoria Piscifactoría a la que se añadieron los peces mediante la opción oculta.
     */
    public void registrarAnadirPecesOculto(String piscifactoria){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true) , "UTF-8"));
            buferEscritura.append("Añadidos peces mediante la opción oculta a la piscifactoría " + piscifactoria + ".");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar la adición de peces mediante la opción oculta.");
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
     * @param recompensa Recompensa creada.
     */
    public void registrarCreacionRecompensa(String recompensa){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Recompensa " + recompensa + " creada.");
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
     * Registra el uso de una recompensa.
     * @param recompensa Recompensa usada.
     */
    public void registrarUsoRecompensa(String recompensa){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Recompensa " + recompensa + " creada.");
            buferEscritura.flush();
        }
        catch(IOException e){
            System.out.println("Hubo un problema a la hora de registrar el uso de la recompensa.");
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
     * Registra el añadido de monedas mediante la opción oculta.
     * @param monedasActuales Monedas de las que se dispone tras el añadido de monedas.
     */
    public void registrarAnadirMonedasOculta(int monedasActuales){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones, true), "UTF-8"));
            buferEscritura.append("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, " + monedasActuales + ".");
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
}
