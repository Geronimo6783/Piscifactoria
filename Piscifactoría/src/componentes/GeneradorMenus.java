package componentes;

import java.io.BufferedReader;

public class GeneradorMenus{

    /**
     * Genera un menú a partir de unas opciones.
     * @param opciones Opciones del menú.
     */
    public static void generarMenus(String[] opciones){
        int numeroOpcion = 0;

        for(String opcion : opciones){
            numeroOpcion++;
            System.out.println(numeroOpcion + ". " + opcion);
        }
    }

    /**
     * Genera un menú con cabecera y unas opciones.
     * @param cabecera Cabecera del menú.
     * @param opciones Opciones del menú.
     */
    public static void generarMenus(String[] cabecera, String[] opciones){
        for(String elemento : cabecera){
            System.out.println(elemento);
        }

        generarMenus(opciones);
    }

    /**
     * Genera un menú operativo a partir de unas opciones.
     * @param br Búfer de entrada de datos.
     * @param opciones Opciones del menú.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenusOperativo(BufferedReader br, String[] opciones){
        generarMenus(opciones);
        return SistemaEntrada.entradaOpcionNumerica(br, 1, opciones.length);
    }


    /**
     * Genera un menú operativo con cabecera y unas opciones.
     * @param br Búfer de entrada de datos.
     * @param cabecera Cabecera del menú.
     * @param opciones Opciones del menú.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenusOperativo(BufferedReader br, String[] cabecera, String[] opciones){
        generarMenus(cabecera, opciones);
        return SistemaEntrada.entradaOpcionNumerica(br, 1, opciones.length);
    }
}