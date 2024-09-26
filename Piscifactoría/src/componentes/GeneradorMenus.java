package componentes;

/**
 * Clase que contiene utilidades para generar menús.
 */
public class GeneradorMenus{

    /**
     * Genera un menú a partir de unas opciones.
     * @param opciones Opciones del menú.
     */
    public static void generarMenu(String[] opciones){
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
    public static void generarMenu(String[] cabecera, String[] opciones){
        for(String elemento : cabecera){
            System.out.println(elemento);
        }

        generarMenu(opciones);
    }

    /**
     * Genera un menú operativo a partir de unas opciones.
     * @param opciones Opciones del menú.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenuOperativo(String[] opciones){
        generarMenu(opciones);
        return SistemaEntrada.entradaOpcionNumerica(1, opciones.length);
    }


    /**
     * Genera un menú operativo con cabecera y unas opciones.
     * @param cabecera Cabecera del menú.
     * @param opciones Opciones del menú.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenuOperativo(String[] cabecera, String[] opciones){
        generarMenu(cabecera, opciones);
        return SistemaEntrada.entradaOpcionNumerica(1, opciones.length);
    }
}