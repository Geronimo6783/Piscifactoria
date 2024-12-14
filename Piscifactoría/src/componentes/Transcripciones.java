package componentes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones), "UTF-8"));
            buferEscritura.write(leerTranscripciones());
            buferEscritura.flush();
            buferEscritura.write(cantidadComida + " de comida de tipo " + tipoComida + "comprada por " + monedas + ". Se almacena en la piscifactoría " + piscifactoria + ".");
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
     * Resgistra la compra de una cantidad de comida para  el almacén central.
     * @param cantidadComida Cantidad de comida comprada.
     * @param tipoComida Tipo de comida comprada.
     * @param monedas Monedas por las que se compró la comida.
     */
    public void registrarCompraComida(int cantidadComida, String tipoComida, int monedas){
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTranscripciones), "UTF-8"));
            buferEscritura.write(leerTranscripciones());
            buferEscritura.flush();
            buferEscritura.write(cantidadComida + " de comida de tipo " + tipoComida + "comprada por " + monedas + ". Se almacena en el almacén central.");
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
     * Lee las transcripciones escritas en el fichero de transcripciones.
     * @return Transcripciones escritas en el fichero de transcripciones.
     */
    private String leerTranscripciones(){
        BufferedReader buferLectura = null;
        StringBuilder constructorString = new StringBuilder();

        try{
            buferLectura = new BufferedReader(new InputStreamReader(new FileInputStream(archivoTranscripciones), "UTF-8"));
            String linea = buferLectura.readLine();

            while(linea != null){  
                if(constructorString.isEmpty()){
                    constructorString.append(linea);
                }
                else{
                    constructorString.append("\n" + linea);
                }
                linea = buferLectura.readLine();
            }
        }
        catch(IOException e){
            System.out.println("Hubo un problema al obtener la línea escritas en el fichero de transcripciones.");
        }
        finally{
            if(buferLectura != null){
                try{
                    buferLectura.close();
                }
                catch(IOException e){

                }
            }
        }

        return constructorString.toString();
    }

}