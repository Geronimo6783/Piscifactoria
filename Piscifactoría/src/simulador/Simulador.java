package simulador;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import simulador.pez.filtrador.ArenqueDelAtlantico;
import simulador.pez.filtrador.TilapiaDelNilo;
import simulador.pez.carnivoro.*;
import simulador.pez.omnivoro.*;

public class Simulador {
    
    public static void main(String[] args) {
        SalmonChinook abadejo = new SalmonChinook(false);
        abadejo.showStatus();
        for(int i = 0; i < 5; i++){
            abadejo.setAlimentado(true);
            abadejo.grow();
            abadejo.showStatus();
        }
        abadejo.setFertil(false);
        abadejo.showStatus();
        for(int i = 0; i < 5; i++){
            abadejo.setAlimentado(true);
            abadejo.grow();
            abadejo.showStatus();
        }
        abadejo.reset();
        abadejo.showStatus();
    }
}
