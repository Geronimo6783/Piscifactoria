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
       Tanque tanque = new Tanque(1, 25, 25);
       tanque.showStatus();
       tanque.getPeces().add(new Sargo(false));
       tanque.getPeces().add(new Sargo(true));
       tanque.showFishStatus();
       tanque.alimentar();
       tanque.showFishStatus();
       tanque.showStatus();
       for(int i = 0; i < 12; i++){
        tanque.alimentar();
        tanque.nextDay();
        tanque.showFishStatus();
        tanque.showStatus();
       }
    }
}
