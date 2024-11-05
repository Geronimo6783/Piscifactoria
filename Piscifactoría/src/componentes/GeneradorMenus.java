package componentes;

/**
 * Clase que contiene utilidades para generar menús.
 */
public class GeneradorMenus{

    /**
     * Genera un menú a partir de unas opciones.
     * @param opciones Opciones del menú.
     * @param numeroOpcionInicial Número de la opción inicial.
     */
    public static void generarMenu(String[] opciones, int numeroOpcionInicial){
        int numeroOpcion = numeroOpcionInicial;

        for(String opcion : opciones){
            System.out.println(numeroOpcion + ". " + opcion);
            numeroOpcion++;
        }
    }

    /**
     * Genera un menú con cabecera y unas opciones.
     * @param cabecera Cabecera del menú.
     * @param opciones Opciones del menú.
     * @param numeroOpcionInicial Número de la opción inicial.
     */
    public static void generarMenu(String[] cabecera, String[] opciones, int numeroOpcionInicial){
        for(String elemento : cabecera){
            System.out.println(elemento);
        }

        generarMenu(opciones, numeroOpcionInicial);
    }

    /**
     * Genera un menú operativo a partir de unas opciones.
     * @param opciones Opciones del menú.
     * @param numeroOpcionInicial Número de la opción inicial.
     * @param numeroOpcionFinal Número de la opción final.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenuOperativo(String[] opciones, int numeroOpcionInicial, int numeroOpcionFinal){
        generarMenu(opciones, numeroOpcionInicial);
        return SistemaEntrada.entradaOpcionNumerica(numeroOpcionInicial, numeroOpcionFinal);
    }

    /**
     * Genera un menú operativo con cabecera y unas opciones.
     * @param cabecera Cabecera del menú.
     * @param opciones Opciones del menú.
     * @param numeroOpcionInicial Número de la opción inicial.
     * @param numeroOpcionFinal Número de la opción final.
     * @return Opción elegida por el usuario.
     */
    public static int generarMenuOperativo(String[] cabecera, String[] opciones, int numeroOpcionInicial, int numeroOpcionFinal){
        generarMenu(cabecera, opciones, numeroOpcionInicial);
        return SistemaEntrada.entradaOpcionNumerica(numeroOpcionInicial, numeroOpcionFinal);
    }

    /**
     * Devuelve un string con información relevante de la clase.
     * @return String con información relevante de la clase.
     */
    public String toString(){
        return "Clase con utilidades para generar menús.";
    }
}