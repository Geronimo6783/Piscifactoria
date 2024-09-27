package simulador;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import simulador.pez.filtrador.ArenqueDelAtlantico;

public class Simulador {
    
    public static void main(String[] args) {
        ArenqueDelAtlantico arenque = new ArenqueDelAtlantico(true);
        for(int i = 0; i < 5; i++){
            arenque.setAlimentado(true);
            arenque.grow();
            arenque.showStatus();
        }
        arenque.setFertil(false);
        for(int i = 0; i < 5; i++){
            arenque.setAlimentado(true);
            arenque.grow();
            arenque.showStatus();
        }
    }
}
