package simulador;

import java.util.ArrayList;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import simulador.pez.filtrador.ArenqueDelAtlantico;
import simulador.pez.filtrador.TilapiaDelNilo;
import simulador.pez.carnivoro.*;
import simulador.pez.omnivoro.*;

public class Simulador {
    
    /**
     * Indica el número de días que han pasado en la simulación.
     */
    private static int diasPasados = 0;

    /**
     * Piscifactorías de la simulación.
     */
    private static ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();

    /**
     * Nombre de la entidad, empresa o partida des la simulación.
     */
    private static String nombre;

    /**
     * Sistema de monedas de la simulación.
     */
    private static SistemaMonedas sistemaMonedas;

    /**
     * Almacén central de comida usado en la simulación.
     */
    private static AlmacenCentral almacenCentral = null;

    /**
     * Opciones del menú principal.
     */
    private static String[] opcionesMenuPrincipal = {"Estado general", "Estado piscifactoría", "Estado tanques", "Informes", "Ictiopedia", "Pasar día",
                                                    "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanque", "Mejorar",
                                                    "Pasar varios días", "Salir"};

    /**
     * Método que inicializa la simulación pidiendo una serie de datos al usuario.
     */
    private static void init(){
        System.out.print("Introduzca el nombre de la entidad, empresa o partida de la simulación: ");
        nombre = SistemaEntrada.entradaTexto();
        System.out.print("Introduzca el nombre de la primera piscifactoría: ");
        String nombrePiscifactoria = SistemaEntrada.entradaTexto();
        sistemaMonedas = new SistemaMonedas(100);
        piscifactorias.add(new Piscifactoria(false, nombrePiscifactoria));
    }

    /**
     * Imprime por pantalla el menú principal,
     */
    private static void menu(){
        GeneradorMenus.generarMenu(opcionesMenuPrincipal);
    }

    /**
     * Muestra el estado general de la simulación.
     */
    private static void showGeneralStatus(){
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.showStatus();
        }
        System.out.println("Día actual: " + (diasPasados + 1));
        System.out.println("Monedas: " + sistemaMonedas.getMonedas());
        if(almacenCentral != null){
            int cantidadComidaAnimalAlmacen = almacenCentral.getCantidadComidaAnimal();
            int cantidadComidaVegetalAlmacen = almacenCentral.getCantidadComidaVegetal();
            int capacidadComidaAlmacen = almacenCentral.getCapacidadComida();
            System.out.println("Comida animal disponible en el almacén central: " + cantidadComidaAnimalAlmacen);
            System.out.println("Comida vegetal disponible en el almacén central: " + cantidadComidaVegetalAlmacen);
            System.out.println("Capacidad máxima de comida animal en el almacén central: " + capacidadComidaAlmacen);
            System.out.println("Capacidad máxima de comida vegetal en el almacén central: " + capacidadComidaAlmacen);
            System.out.println("% de capacidad de la comida animal del almacén central " + String.format("%.2f", ((float) cantidadComidaAnimalAlmacen) / (float) capacidadComidaAlmacen) + "%");
            System.out.println("% de capacidad de la comida vegetal del almacén central " + String.format("%.2f", ((float) cantidadComidaVegetalAlmacen / (float) capacidadComidaAlmacen)) + "%");
        }
    }

    public static void main(String[] args) {
        Tanque tanque = new Tanque(1,  25);
        tanque.showCapacity("Piscifactoría");

    }
}
