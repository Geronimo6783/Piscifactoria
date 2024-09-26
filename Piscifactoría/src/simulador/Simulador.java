package simulador;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;

public class Simulador {
    
    public static void main(String[] args) {
        SistemaEntrada.entradaOpcionNumerica( 0, 6);
        SistemaEntrada.entradaTexto();
        SistemaEntrada.close();
    }
}
