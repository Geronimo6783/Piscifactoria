package simulador;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import simulador.pez.filtrador.ArenqueDelAtlantico;

public class Simulador {
    
    public static void main(String[] args) {
        ArenqueDelAtlantico arenque = new ArenqueDelAtlantico(false);
        arenque.setAlimentado(true);
        arenque.showStatus();
        arenque.grow();
        arenque.showStatus();
    }
}
