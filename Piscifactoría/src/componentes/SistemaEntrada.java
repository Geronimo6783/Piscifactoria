package componentes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Clase que gestiona la entrada de datos introducidos por el usuario.
 */
public class SistemaEntrada {

    /**
     * Búfer de entrada de datos.
     */
    private static final BufferedReader BUFER_ENTRADA = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

    /**
     * Gestiona la entrada de opciones numéricas.
     * @param minimo Valor mínimo aceptado.
     * @param maximo Valor máximo aceptado.
     * @return Opción escogida por el usuario.
     */
    public static int entradaOpcionNumerica(int minimo, int maximo){
        int opcion = 0;
        boolean error;

        do{
            try{
                opcion = Integer.parseInt(BUFER_ENTRADA.readLine());
                error = false;
                if(opcion < minimo || opcion > maximo){
                    System.out.println("Introduzca una opción válida.");
                    error = true;
                }
            }
            catch(IOException | NumberFormatException e){
                System.out.println("Introduzca una opción válida.");
                error = true;
            }
        }while(error);

        return opcion;
    }

    /**
     * Gestiona la entrada de opciones numéricas.
     * @param opciones Opciones numéricas aceptadas.
     * @return Opción escogida por el usuario.
     */
    public static int entradaOpcionNumerica(int[] opciones){
        int opcionIntroducida = 0;
        boolean error;

        do{
            try{
                opcionIntroducida = Integer.parseInt(BUFER_ENTRADA.readLine());
                error = true;

                for(int opcion : opciones){
                    if(opcionIntroducida == opcion){
                        error = false;
                    }
                }

                if(error){
                    System.out.println("Introduzca una opción válida.");
                }
            }
            catch(IOException | NumberFormatException e){
                System.out.println("Introduzca una opción válida.");
                error = true;
            }
        }while(error);

        return opcionIntroducida;
    }

    /**
     * Gestiona la entrada de texto introducido por el usuario.
     * @return Texto introducido por el usuario.
     */
    public static String entradaTexto(){
        String texto = "";

        try{
            texto = BUFER_ENTRADA.readLine();
        }
        catch(IOException e){
            System.out.println("Hubo un problema al introducir el texto.");
        }

        return texto;
    }

    /**
     * Gestiona la entrada de un número entero positivo introducido por el usuario.
     * @return Número entero positivo introducido por el usuario.
     */
    public static int entradaOpcionNumericaEnteraPositiva(){
        int opcion = 0;
        boolean error;

        do{
            try{
                opcion = Integer.parseInt(BUFER_ENTRADA.readLine());
                error = false;
                if(opcion < 0){
                    System.out.println("No introduzca valores negativos.");
                    error = true;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Introduzca un número entero.");
                error = true;
            }
            catch(IOException e){
                System.out.println("Hubo un problema a la hora de leer la entrada del usuario.");
                error = true;
            }
        }while(error);

        return opcion;
    }

    /**
     * Cierra el búfer de entrada de datos.
     */
    public static void close(){
        try{
            BUFER_ENTRADA.close();            
        }
        catch(IOException e){
            System.out.println("Hubo un problema al cerrar el búfer de lectura.");
        }
    }

    /**
     * Devuelve un string con información útil de la clase.
     * @return String con información útil de la clase.
     */
    public String toString(){
        return "Búfer de entrada con codificación UTF-8.";
    }
}
