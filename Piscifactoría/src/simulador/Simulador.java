package simulador;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.io.InputStreamReader;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;

public class Simulador {

    /**
     * Búfer de entrada por teclado.
     */
    private static final BufferedReader BUFER_ENTRADA = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
    
    public static void main(String[] args) {
        SistemaEntrada.entradaOpcionNumerica(BUFER_ENTRADA, 0, 6);
        SistemaEntrada.entradaTexto(BUFER_ENTRADA);
        try {
            BUFER_ENTRADA.close();
        } catch (IOException e) {
            System.out.println("Hubo un problema al cerrar el búfer.");
        }
    }
}
