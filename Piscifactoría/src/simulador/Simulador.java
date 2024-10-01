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
       CarpinTresEspinas pez = new CarpinTresEspinas(false);
       System.out.println(pez.comer());
    }
}
