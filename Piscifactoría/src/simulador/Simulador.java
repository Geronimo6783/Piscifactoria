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
       AlmacenCentral almacen = new AlmacenCentral();
       System.out.println(almacen.getCapacidadComidaAnimal());
       System.out.println(almacen.getCapacidadComidaVegetal());
       almacen.setCapacidadComidaAnimal(400);
       almacen.setCapacidadComidaVegetal(400);
       System.out.println(almacen.getCapacidadComidaAnimal());
       System.out.println(almacen.getCapacidadComidaVegetal());
    }
}
