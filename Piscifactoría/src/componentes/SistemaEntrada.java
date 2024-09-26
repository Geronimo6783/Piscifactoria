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
    private static final BufferedReader BUFER_ENTRADA = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));

    /**
     * Gestiona la entrada de opciones numéricas.
     * @param br Búfer de entrada de datos.
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
                System.out.println("Introduzca una opción c .");
                error = true;
            }
        }while(error);

        return opcion;
    }

    /**
     * Gestiona la entrada de texto introducido por el usuario.
     * @param br Búfer de entrada de datos.
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
}
