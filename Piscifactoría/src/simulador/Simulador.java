package simulador;

import java.util.ArrayList;

import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import estadisticas.Estadisticas;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import propiedades.PecesProps;
import simulador.pez.filtrador.ArenqueDelAtlantico;
import simulador.pez.filtrador.TilapiaDelNilo;
import simulador.pez.carnivoro.*;
import simulador.pez.omnivoro.*;
import simulador.piscifactoria.*;
import simulador.piscifactoria.Piscifactoria.AlmacenComida;

public class Simulador {
    
    /**
     * Indica el número de días que han pasado en la simulación.
     */
    private static int diasPasados = 0;

    /**
     * Piscifactorías de la simulación.
     */
    public static ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();

    /**
     * Nombre de la entidad, empresa o partida de la simulación.
     */
    private static String nombre;

    /**
     * Sistema de monedas de la simulación.
     */
    public static SistemaMonedas sistemaMonedas;

    /**
     * Almacén central de comida usado en la simulación.
     */
    public static AlmacenCentral almacenCentral = null;

    /**
     * Estadísticas de los peces de la simualción.
     */
    public static Estadisticas estadisticas;

    /**
     * Método que inicializa la simulación pidiendo una serie de datos al usuario.
     */
    private static void init(){
        System.out.print("Introduzca el nombre de la entidad, empresa o partida de la simulación: ");
        nombre = SistemaEntrada.entradaTexto();
        System.out.print("Introduzca el nombre de la primera piscifactoría: ");
        String nombrePiscifactoria = SistemaEntrada.entradaTexto();
        sistemaMonedas = new SistemaMonedas(100);
        piscifactorias.add(new PiscifactoriaRio(nombrePiscifactoria));
        String[] pecesDisponibles = {AlmacenPropiedades.ABADEJO.getNombre(), AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
                                    AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.PEJERREY.getNombre(),
                                    AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                                    AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre()};
        estadisticas = new Estadisticas(pecesDisponibles);
    }

    /**
     * Imprime por pantalla el menú principal,
     */
    private static void menu(){
        String[] opcionesMenuPrincipal = {"Estado general", "Estado piscifactoría", "Estado tanques", "Informes", "Ictiopedia", "Pasar día",
                                                    "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanque", "Mejorar",
                                                    "Pasar varios días", "Salir"};
        GeneradorMenus.generarMenu(opcionesMenuPrincipal, 1);
    }

    /**
     * Imprime un menú para seleccionar una piscifactoría de la simulación.
     */
    private static void menuPisc(){
        String[] cabeceraMenuPiscifactoria = {"Seleccione una opción:", "--------------------------- Piscifactorías ---------------------------", 
                                                                "[Peces vivos/ Peces totales/ Espacio total]"};
        String[] opciones = new String[piscifactorias.size() + 1];
        opciones[0] = "Cancelar";

        for(int i = 0; i < piscifactorias.size(); i++){
            Piscifactoria piscifactoria = piscifactorias.get(i);
            opciones[i + 1] = piscifactoria.getNombre() + " [" + piscifactoria.getPecesVivos() + "/" + piscifactoria.getPecesTotales() + "/" + piscifactoria.getEspacioPeces() + "]";
        }

        GeneradorMenus.generarMenu(cabeceraMenuPiscifactoria, opciones, 0);
    }

    /**
     * Permite seleccionar una piscifactoría del menú de piscifactoría.
     * @return Opción seleccionada.
     */
    private static int selectPisc(){
        menuPisc();

        return SistemaEntrada.entradaOpcionNumerica(0, piscifactorias.size());
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
     * Muestra por pantalla el estado de todos los tanque de una piscifactoría seleccionada por el usuario.
     */
    private static void showSpecificStatus(){
        int piscifactoriaSeleccionada = selectPisc();

        if(piscifactoriaSeleccionada != 0){
            piscifactorias.get(piscifactoriaSeleccionada - 1).showTankStatus();
        }
    }

    /**
     * Muestra un desglose de las estadísticas por cada tipo de pez.
     */
    private static void showStats(){
        estadisticas.mostrar();
    }

    /**
     * Muestra la información relativa a un pez seleccionado por el usuario.
     */
    private static void showIctio(){
        String[] opcionesPecesDisponibles = {"Cancelar", AlmacenPropiedades.ABADEJO.getNombre(), AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
        AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.PEJERREY.getNombre(),
        AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
        AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre()};
        int opcion = GeneradorMenus.generarMenuOperativo(opcionesPecesDisponibles, 0, 12);

        switch(opcion){
            case 1 -> mostrarInformacionPez(AlmacenPropiedades.ABADEJO);
            case 2 -> mostrarInformacionPez(AlmacenPropiedades.ARENQUE_ATLANTICO);
            case 3 -> mostrarInformacionPez(AlmacenPropiedades.CABALLA);
            case 4 -> mostrarInformacionPez(AlmacenPropiedades.CARPIN_TRES_ESPINAS);
            case 5 -> mostrarInformacionPez(AlmacenPropiedades.DORADA);
            case 6 -> mostrarInformacionPez(AlmacenPropiedades.PEJERREY);
            case 7 -> mostrarInformacionPez(AlmacenPropiedades.PERCA_EUROPEA);
            case 8 -> mostrarInformacionPez(AlmacenPropiedades.ROBALO);
            case 9 -> mostrarInformacionPez(AlmacenPropiedades.SALMON_ATLANTICO);
            case 10 -> mostrarInformacionPez(AlmacenPropiedades.SALMON_CHINOOK);
            case 11 -> mostrarInformacionPez(AlmacenPropiedades.SARGO);
            case 12 -> mostrarInformacionPez(AlmacenPropiedades.TILAPIA_NILO);
        }
    }

    /**
     * Avanza un día en todas las piscifactorías y muestra el número de peces vendidos y las monedas ganadas con ello.
     */
    private static void nextDay(){
        int pecesAntesDePasarDia;
        int dineroAntesDePasarDia;
        int pecesVendidos = 0;
        int monedasGanadas = 0;

        for(Piscifactoria piscifactoria : piscifactorias){
            pecesAntesDePasarDia = piscifactoria.getPecesTotales();
            dineroAntesDePasarDia = sistemaMonedas.getMonedas();
            piscifactoria.nextDay();
            System.out.println("Piscifactoría " + piscifactoria.getNombre() + ":" + (pecesAntesDePasarDia - piscifactoria.getPecesTotales()) + " peces vendidos por un total de " + (sistemaMonedas.getMonedas() - dineroAntesDePasarDia) + " monedas.");
            pecesVendidos += pecesAntesDePasarDia - piscifactoria.getPecesTotales();
            monedasGanadas += sistemaMonedas.getMonedas() - dineroAntesDePasarDia;
        }

        System.out.println(pecesVendidos + " peces vendidos por un total de " + monedasGanadas + " monedas.");
    }

    /**
     * Imprime por pantalla los datos relativos a un pez.
     * @param datosDelPez Datos relativos a un pez a mostrar.
     */
    private static void mostrarInformacionPez(PecesDatos datosDelPez){
        System.out.println("Nombre: " + datosDelPez.getNombre());
        System.out.println("Nombre científico: " + datosDelPez.getCientifico());
        System.out.println("Coste: " + datosDelPez.getCoste() + " monedas");
        System.out.println("Precio de venta: " + datosDelPez.getMonedas() + " monedas");
        System.out.println("Huevos que pone: " + datosDelPez.getHuevos());
        System.out.println("Ciclo de reproducción: " + datosDelPez.getCiclo() + " días");
        System.out.println("Madurez: " + datosDelPez.getMadurez() + " días");
        System.out.println("Edad óptima para la venta: " + datosDelPez.getOptimo() + " días");
        System.out.println("Piscifactoria: " + datosDelPez.getPiscifactoria());

        System.out.print("Propiedades: ");
        for(PecesProps propiedad : datosDelPez.getPropiedades()){
            System.out.print(propiedad + " ");
        }

        System.out.println("\nTipo: " + datosDelPez.getTipo());
        
    }

    public static void main(String[] args) {
        showIctio();
    }
}
