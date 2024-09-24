package componentes;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Clase que gestiona la entrada de datos introducidos por el usuario.
 */
public class SistemaEntrada {

    /**
     * Gestiona la entrada de opciones numéricas.
     * @param br Búfer de entrada de datos.
     * @param minimo Valor mínimo aceptado.
     * @param maximo Valor máximo aceptado.
     * @return Opción escogida por el usuario.
     */
    public static int entradaOpcionNumerica(BufferedReader br, int minimo, int maximo){
        int opcion = 0;
        boolean error;

        do{
            try{
                opcion = Integer.parseInt(br.readLine());
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
    public static String entradaTexto(BufferedReader br){
        String texto = "";

        try{
            texto = br.readLine();
        }
        catch(IOException e){
            System.out.println("Hubo un problema al introducir el texto.");
        }

        return texto;
    }
}
