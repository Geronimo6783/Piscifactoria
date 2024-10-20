package simulador;

import java.util.ArrayList;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import propiedades.PecesProps;
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
    private static final String[] opcionesMenuPrincipal = {"Estado general", "Estado piscifactoría", "Estado tanques", "Informes", "Ictiopedia", "Pasar día",
                                                    "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanque", "Mejorar",
                                                    "Pasar varios días", "Salir"};

    /**
     * Opciones de todos los peces disponibles.
     */
    private static final String[] opcionesPecesDisponibles = {"Cancelar", "Abadejo", "Arenque del Atlántico", "Caballa", "Carpín Tres Espinas", "Dorada",
                                                                "Pejerrey", "Perca Europea", "Robalo", "Salmón Atlántico", "Salmón Chinook", "Sargo",
                                                                "Tilapia del Nilo"};

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

    /**
     * Muestra la información relativa a un pez seleccionado por el usuario.
     */
    private static void showIctio(){
        int opcion = GeneradorMenus.generarMenuOperativo(opcionesPecesDisponibles);

        switch(opcion){
            case 2 -> mostrarInformacionPez(AlmacenPropiedades.ABADEJO);
            case 3 -> mostrarInformacionPez(AlmacenPropiedades.ARENQUE_ATLANTICO);
            case 4 -> mostrarInformacionPez(AlmacenPropiedades.CABALLA);
            case 5 -> mostrarInformacionPez(AlmacenPropiedades.CARPIN_TRES_ESPINAS);
            case 6 -> mostrarInformacionPez(AlmacenPropiedades.DORADA);
            case 7-> mostrarInformacionPez(AlmacenPropiedades.PEJERREY);
            case 8 -> mostrarInformacionPez(AlmacenPropiedades.PERCA_EUROPEA);
            case 9 -> mostrarInformacionPez(AlmacenPropiedades.ROBALO);
            case 10 -> mostrarInformacionPez(AlmacenPropiedades.SALMON_ATLANTICO);
            case 11 -> mostrarInformacionPez(AlmacenPropiedades.SALMON_CHINOOK);
            case 12 -> mostrarInformacionPez(AlmacenPropiedades.SARGO);
            case 13 -> mostrarInformacionPez(AlmacenPropiedades.TILAPIA_NILO);
        }
    }

    /**
     * Imprime por pantalla los datos relativos a un pez.
     * @param datosDelPez Datos relativos a un pez a mostrar.
     */
    private static void mostrarInformacionPez(PecesDatos datosDelPez){
        System.out.println("Coste: " + datosDelPez.getCoste() + " monedas");
        System.out.println("Precio de venta: " + datosDelPez.getMonedas() + " monedas");
        System.out.println("Huevos que pone por pez: " + datosDelPez.getHuevos());
        System.out.println("Ciclo de reproducción: " + datosDelPez.getCiclo() + " días");
        System.out.println("Madurez: " + datosDelPez.getMadurez() + " días");
        System.out.println("Edad óptima para la venta: " + datosDelPez.getOptimo() + " días");
        
        System.out.print("Propiedades: ");
        for(PecesProps propiedad : datosDelPez.getPropiedades()){
            System.out.print(propiedad + " ");
        }

        System.out.println("\nTipo: " + datosDelPez.getTipo());
        
    }

    public static void main(String[] args) {
        System.out.println(AlmacenPropiedades.CABALLA.getNombre());
        showIctio();
    }
}
