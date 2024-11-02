package simulador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import componentes.GeneradorMenus;
import componentes.SistemaEntrada;
import componentes.SistemaMonedas;
import estadisticas.Estadisticas;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import propiedades.PecesProps;
import simulador.pez.filtrador.ArenqueDelAtlantico;
import simulador.pez.filtrador.TilapiaDelNilo;
import simulador.pez.Pez;
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
    private static void init() {
        System.out.print("Introduzca el nombre de la entidad, empresa o partida de la simulación: ");
        nombre = SistemaEntrada.entradaTexto();
        System.out.print("Introduzca el nombre de la primera piscifactoría: ");
        String nombrePiscifactoria = SistemaEntrada.entradaTexto();
        sistemaMonedas = new SistemaMonedas(100);
        piscifactorias.add(new PiscifactoriaRio(nombrePiscifactoria));
        String[] pecesDisponibles = { AlmacenPropiedades.ABADEJO.getNombre(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
                AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(),
                AlmacenPropiedades.PEJERREY.getNombre(),
                AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(),
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                AlmacenPropiedades.TILAPIA_NILO.getNombre() };
        estadisticas = new Estadisticas(pecesDisponibles);
    }

    /**
     * Imprime por pantalla el menú principal,
     */
    private static void menu() {
        String[] opcionesMenuPrincipal = { "Estado general", "Estado piscifactoría", "Estado tanques", "Informes",
                "Ictiopedia", "Pasar día",
                "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanque", "Mejorar",
                "Pasar varios días", "Salir" };
        GeneradorMenus.generarMenu(opcionesMenuPrincipal, 1);
    }

    /**
     * Imprime un menú para seleccionar una piscifactoría de la simulación.
     */
    private static void menuPisc() {
        String[] cabeceraMenuPiscifactoria = { "Seleccione una opción:",
                "--------------------------- Piscifactorías ---------------------------",
                "[Peces vivos/ Peces totales/ Espacio total]" };
        String[] opciones = new String[piscifactorias.size() + 1];
        opciones[0] = "Cancelar";

        for (int i = 0; i < piscifactorias.size(); i++) {
            Piscifactoria piscifactoria = piscifactorias.get(i);
            opciones[i + 1] = piscifactoria.getNombre() + " [" + piscifactoria.getPecesVivos() + "/"
                    + piscifactoria.getPecesTotales() + "/" + piscifactoria.getEspacioPeces() + "]";
        }

        GeneradorMenus.generarMenu(cabeceraMenuPiscifactoria, opciones, 0);
    }

    /**
     * Permite seleccionar una piscifactoría del menú de piscifactoría.
     * 
     * @return Opción seleccionada.
     */
    private static int selectPisc() {
        menuPisc();

        return SistemaEntrada.entradaOpcionNumerica(0, piscifactorias.size());
    }

    /**
     * Muestra el menú de tanques de una piscifactoría y permite al usuario seleccionar uno.
     * 
     * @param piscifactoria Piscifactoría de la cúal se muestra el menú de tanques.
     * @return El índice del tanque seleccionado.
     */
    private static int selectTank(Piscifactoria piscifactoria) {
        ArrayList<Tanque> tanques = piscifactoria.getTanques();

        String[] cabeceraMenuPiscifactoria = { "----------- Tanques de " + piscifactoria.getNombre() + " -----------" };
        String[] opcionesTanques = new String[tanques.size() + 1];
        opcionesTanques[0] = "Cancelar";

        for (int i = 0; i < tanques.size(); i++) {
            Tanque tanque = tanques.get(i);
            String nombrePez = tanque.getPeces().isEmpty() ? "Sin peces" : tanque.getPeces().get(0).getNombre();
            opcionesTanques[i + 1] = "Tanque " + (i + 1) + " - Ocupación: " + tanque.getPeces().size() + "/"
                    + tanque.getCapacidadMaximaPeces() + " - Pez: " + nombrePez;
        }

        return GeneradorMenus.generarMenuOperativo(cabeceraMenuPiscifactoria, opcionesTanques, 0, tanques.size());
    }

    /**
     * Muestra el estado general de la simulación.
     */
    private static void showGeneralStatus() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.showStatus();
        }
        System.out.println("Día actual: " + (diasPasados + 1));
        System.out.println("Monedas: " + sistemaMonedas.getMonedas());
        if (almacenCentral != null) {
            int cantidadComidaAnimalAlmacen = almacenCentral.getCantidadComidaAnimal();
            int cantidadComidaVegetalAlmacen = almacenCentral.getCantidadComidaVegetal();
            int capacidadComidaAlmacen = almacenCentral.getCapacidadComida();
            System.out.println("Comida animal disponible en el almacén central: " + cantidadComidaAnimalAlmacen);
            System.out.println("Comida vegetal disponible en el almacén central: " + cantidadComidaVegetalAlmacen);
            System.out.println("Capacidad máxima de comida animal en el almacén central: " + capacidadComidaAlmacen);
            System.out.println("Capacidad máxima de comida vegetal en el almacén central: " + capacidadComidaAlmacen);
            System.out.println("% de capacidad de la comida animal del almacén central "
                    + String.format("%.2f", ((float) cantidadComidaAnimalAlmacen) / (float) capacidadComidaAlmacen)
                    + "%");
            System.out.println("% de capacidad de la comida vegetal del almacén central "
                    + String.format("%.2f", ((float) cantidadComidaVegetalAlmacen / (float) capacidadComidaAlmacen))
                    + "%");
        }
    }

    /**
     * Muestra por pantalla el estado de todos los tanque de una piscifactoría
     * seleccionada por el usuario.
     */
    private static void showSpecificStatus() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            piscifactorias.get(piscifactoriaSeleccionada - 1).showTankStatus();
        }
    }

    /**
     * Muestra un menú para seleccionar un tanque de una piscifactoría y
     * posteriormente el estado de los peces en dicho tanque.
     * 
     * @param piscifactoria La piscifactoría que contiene los tanques disponibles.
     */
    public static void showTankStatus(Piscifactoria piscifactoria) {
        int opcionTanque = selectTank(piscifactoria);

        if (opcionTanque != 0) {
            Tanque tanqueSeleccionado = piscifactoria.getTanques().get(opcionTanque - 1);

            if (tanqueSeleccionado.getPeces().isEmpty()) {
                System.out.println("El tanque seleccionado no contiene peces.");
            } else {
                System.out.println("Estado de los peces en el tanque " + opcionTanque + ":");
                tanqueSeleccionado.showFishStatus();
            }
        }
    }

    /**
     * Muestra un desglose de las estadísticas por cada tipo de pez.
     */
    private static void showStats() {
        System.out.println("======================================== Estadísticas ========================================");
        estadisticas.mostrar();
    }

    /**
     * Muestra la información relativa a un pez seleccionado por el usuario.
     */
    private static void showIctio() {
        System.out.println("========== Ictiopedia ==========");

        String[] opcionesPecesDisponibles = { "Cancelar", AlmacenPropiedades.ABADEJO.getNombre(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
                AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(),
                AlmacenPropiedades.PEJERREY.getNombre(),
                AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(),
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                AlmacenPropiedades.TILAPIA_NILO.getNombre() };
        int opcion = GeneradorMenus.generarMenuOperativo(opcionesPecesDisponibles, 0, 12);

        switch (opcion) {
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
     * Avanza un día en todas las piscifactorías y muestra el número de peces
     * vendidos y las monedas ganadas con ello.
     */
    private static void nextDay() {
        int dineroAntesDePasarDia;
        int pecesVendidoPiscifactoria = 0;
        int pecesVendidos = 0;
        int monedasGanadas = 0;

        for (Piscifactoria piscifactoria : piscifactorias) {
            dineroAntesDePasarDia = sistemaMonedas.getMonedas();
            pecesVendidoPiscifactoria += piscifactoria.nextDay();
            pecesVendidos += pecesVendidoPiscifactoria;
            System.out.println("Piscifactoría " + piscifactoria.getNombre() + ": "
                    + pecesVendidoPiscifactoria + " peces vendidos por un total de "
                    + (sistemaMonedas.getMonedas() - dineroAntesDePasarDia) + " monedas.");
            monedasGanadas += sistemaMonedas.getMonedas() - dineroAntesDePasarDia;
        }

        System.out.println(pecesVendidos + " peces vendidos por un total de " + monedasGanadas + " monedas.");
        
        if(almacenCentral != null){
            repartirComida();
        }

        diasPasados++;
    }

    /**
     * Gestiona la compra de comida para una piscifactoría o para el almacén central.
     */
    private static void addFood() {
        if (almacenCentral != null) {
            int tipoComida = menuTipoComida();

            if (tipoComida != 0) {
                int cantidadAAgregar = menuCantidadComida();
                int capacidadComidaAlmacen = almacenCentral.getCapacidadComida();

                if (tipoComida == 1) {
                    int cantidadComidaAnimalAntes = almacenCentral.getCantidadComidaAnimal();
                    if (cantidadComidaAnimalAntes < capacidadComidaAlmacen) {
                        switch(cantidadAAgregar){
                            case 1 -> {cantidadAAgregar = 5;}
                            case 2 -> {cantidadAAgregar = 10;}
                            case 3 -> {cantidadAAgregar = 25;}
                            case 4 -> {cantidadAAgregar = (capacidadComidaAlmacen - cantidadComidaAnimalAntes);}
                        }

                        almacenCentral.setCantidadComidaAnimal(cantidadComidaAnimalAntes + cantidadAAgregar);

                        int costoAnimal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoAnimal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoAnimal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida animal.");
                            System.out.println("Depósito de comida animal en el almacén central " +
                                    almacenCentral.getCantidadComidaAnimal()+ "/" + almacenCentral.getCapacidadComida() +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaAnimalAntes + cantidadAAgregar) /
                                                    almacenCentral.getCapacidadComida() * 100)
                                    + "%.");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida animal, faltan " + (costoAnimal - sistemaMonedas.getMonedas()) + " monedas.");
                        }
                    } else {
                        System.out
                                .println("No se puede añadir comida animal, excede la capacidad del almacén central.");
                    }
                } else if (tipoComida == 2) {
                    int cantidadComidaVegetalAntes = almacenCentral.getCantidadComidaVegetal();
                    if (cantidadComidaVegetalAntes < capacidadComidaAlmacen) {
                        switch(cantidadAAgregar){
                            case 1 -> {cantidadAAgregar = 5;}
                            case 2 -> {cantidadAAgregar = 10;}
                            case 3 -> {cantidadAAgregar = 25;}
                            case 4 -> {cantidadAAgregar = (capacidadComidaAlmacen - cantidadComidaVegetalAntes);}
                        }

                        almacenCentral.setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

                        int costoVegetal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoVegetal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoVegetal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida vegetal.");
                            System.out.println("Depósito de comida vegetal en el almacén central " +
                                    almacenCentral.getCantidadComidaVegetal() + "/" + almacenCentral.getCapacidadComida() +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaVegetalAntes + cantidadAAgregar) /
                                                    almacenCentral.getCapacidadComida() * 100)
                                    + "%.");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida vegetal, faltan " + (costoVegetal - sistemaMonedas.getMonedas()) + " monedas.");
                        }
                    } else {
                        System.out
                                .println("No se puede añadir comida vegetal, excede la capacidad del almacén central.");
                    }
                }

                repartirComida();
            }
        } else {
            int piscifactoriaSeleccionada = selectPisc();
            if (piscifactoriaSeleccionada != 0) {
                Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
                int tipoComida = menuTipoComida();
                int cantidadAAgregar = 0;

                if (tipoComida != 0) {
                    cantidadAAgregar = menuCantidadComida();
                }

                if (cantidadAAgregar != 0) {
                    if (tipoComida == 1) {
                        int cantidadComidaAnimalAntes = piscifactoria.getAlmacenInicial().getCantidadComidaAnimal();

                        int cantidadMaximaAnimal = (piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() - cantidadComidaAnimalAntes);

                        switch(cantidadAAgregar){
                            case 1 -> {cantidadAAgregar = 5;}
                            case 2 -> {cantidadAAgregar = 10;}
                            case 3 -> {cantidadAAgregar = 25;}
                            case 4 -> {cantidadAAgregar = cantidadMaximaAnimal;}
                        }

                        if (cantidadAAgregar > cantidadMaximaAnimal) {
                            cantidadAAgregar = cantidadMaximaAnimal;
                        }

                        if (cantidadAAgregar != 0) {
                            piscifactoria.getAlmacenInicial()
                                    .setCantidadComidaAnimal(cantidadComidaAnimalAntes + cantidadAAgregar);

                            int costoAnimal = calcularCosto(cantidadAAgregar);
                            if (sistemaMonedas.getMonedas() >= costoAnimal) {
                                sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoAnimal);
                                System.out
                                        .println("Añadida " + cantidadAAgregar + " de comida animal a la piscifactoria "
                                                + piscifactoria.getNombre() + ".");
                                System.out.println("Depósito de comida animal en la piscifactoria "
                                        + piscifactoria.getNombre()
                                        + ": " + piscifactoria.getAlmacenInicial().getCantidadComidaAnimal() + "/"
                                        + piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() +
                                        " al " + String.format("%.2f",
                                                (float) (cantidadComidaAnimalAntes + cantidadAAgregar) /
                                                        piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                                                        * 100)
                                        + "%.");
                            } else {
                                System.out.println("No hay suficientes monedas para comprar la comida animal, faltan " + (costoAnimal - sistemaMonedas.getMonedas()) + " monedas.");
                            }
                        } else {
                            System.out.println(
                                    "No se puede añadir comida animal, la capacidad de la piscifactoría está llena.");
                        }
                    } else if (tipoComida == 2) {
                        int cantidadComidaVegetalAntes = piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();
                        int cantidadMaximaVegetal = piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() - cantidadComidaVegetalAntes;
                                       
                        switch(cantidadAAgregar){
                            case 1 -> {cantidadAAgregar = 5;}
                            case 2 -> {cantidadAAgregar = 10;}
                            case 3 -> {cantidadAAgregar = 25;}
                            case 4 -> {cantidadAAgregar = cantidadMaximaVegetal;}
                        }

                        if (cantidadAAgregar > cantidadMaximaVegetal) {
                            cantidadAAgregar = cantidadMaximaVegetal;
                        }

                        if (cantidadAAgregar > 0) {
                            piscifactoria.getAlmacenInicial().setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

                            int costoVegetal = calcularCosto(cantidadAAgregar);
                            if (sistemaMonedas.getMonedas() >= costoVegetal) {
                                sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoVegetal);
                                System.out.println(
                                        "Añadida " + cantidadAAgregar + " de comida vegetal a la piscifactoria "
                                                + piscifactoria.getNombre() + ".");
                                System.out.println("Depósito de comida vegetal en la piscifactoria "
                                        + piscifactoria.getNombre()
                                        + ": " + piscifactoria.getAlmacenInicial().getCantidadComidaVegetal() + "/"
                                        + piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() +
                                        " al " + String.format("%.2f",
                                                (float) (cantidadComidaVegetalAntes + cantidadAAgregar) /
                                                        piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                                                        * 100)
                                        + "%.");
                            } else {
                                System.out.println("No hay suficientes monedas para comprar la comida vegetal, faltan " + (costoVegetal - sistemaMonedas.getMonedas()) + " monedas.");
                            }
                        } else {
                            System.out.println(
                                    "No se puede añadir comida vegetal, la capacidad de la piscifactoría está llena.");
                        }
                    }
                }
            }
        }

    }

    /**
     * Muestra un menú para seleccionar el tipo de comida (animal o vegetal).
     * 
     * @return Opción seleccionada por el usuario.
     */
    private static int menuTipoComida() {
        System.out.println("========== Tipo de comida ==========");
        String[] opciones = { "Cancelar", "Comida animal", "Comida vegetal" };

        int seleccion = GeneradorMenus.generarMenuOperativo(opciones, 0, 2);;

        return seleccion;
    }

    /**
     * Muestra un menú para seleccionar la cantidad de comida a añadir.
     * 
     * @return Opción seleccionada.
     */
    private static int menuCantidadComida() {
        String[] opciones = { "Cancelar", "5", "10", "25", "Llenar" };
        GeneradorMenus.generarMenu(opciones, 0);

        return SistemaEntrada.entradaOpcionNumerica(0, 4);
    }

    /**
     * Calcula el costo de la comida a añadir, aplicando descuentos cada 25
     * unidades.
     * 
     * @param cantidad Cantidad de comida a añadir.
     * @return Costo total en monedas.
     */
    private static int calcularCosto(int cantidad) {
        int costo = cantidad;
        for (int i = 25; i <= cantidad; i += 25) {
            costo -= 5;
        }
        return Math.max(costo, 0);
    }

    /**
    * Añade un pez a una piscifactoría seleccionada por el usuario.
    */
    private static void addFish() {
        int piscifactoriaSeleccionada = selectPisc();

        if(piscifactoriaSeleccionada != 0){
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1); 
            
            if(piscifactoria instanceof PiscifactoriaMar){
                addFishMar(piscifactoria);
            }
            else{
                addFishRio(piscifactoria);
            }
        }
    }

    /**
    * Gestiona la lógica de añadir un pez a una piscifactoría de mar.
    * @param piscifactoria Piscifactoría donde se va a añadir el pez.
    */
    private static void addFishMar(Piscifactoria piscifactoria){
        String[] pecesDisponiblesMar = {
            "Cancelar",
            AlmacenPropiedades.ABADEJO.getNombre() + " - " + AlmacenPropiedades.ABADEJO.getCoste() + " monedas",
            AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre() + " - " + AlmacenPropiedades.ARENQUE_ATLANTICO.getCoste() + " monedas",
            AlmacenPropiedades.CABALLA.getNombre() + " - " + AlmacenPropiedades.CABALLA.getCoste() + " monedas",
            AlmacenPropiedades.DORADA.getNombre() + " - " + AlmacenPropiedades.DORADA.getCoste() + " monedas",
            AlmacenPropiedades.ROBALO.getNombre() + " - " + AlmacenPropiedades.ROBALO.getCoste() + " monedas",
            AlmacenPropiedades.SALMON_ATLANTICO.getNombre() + " - " + AlmacenPropiedades.SALMON_ATLANTICO.getCoste() + " monedas",
            AlmacenPropiedades.SARGO.getNombre() + " - " + AlmacenPropiedades.SARGO.getCoste() + " monedas",
        };

        int monedas = sistemaMonedas.getMonedas();
        System.out.println("Cartera: " +  monedas + " monedas.");
        int opcion = GeneradorMenus.generarMenuOperativo(pecesDisponiblesMar, 0, 7);

        if(opcion != 0){
            String nombrePez = pecesDisponiblesMar[opcion].split(" - ")[0];

            int indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(nombrePez);
            
            if(indiceTanqueConEspacioParaPez != -1){
                int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();

                if(monedas >= coste){
                    Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);

                    if(tanque.pecesMacho() >= tanque.pecesHembra()){
                        tanque.getPeces().add(crearPezMar(opcion, true));
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                    else{
                        tanque.getPeces().add(crearPezMar(opcion, false));
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                }
                else{
                    System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan " + (coste - monedas) + " monedas.");
                }
            }
            else{
                int indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                if(indiceTanqueVacio != -1){
                    int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();

                    if(monedas >= coste){
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueVacio);
                        tanque.getPeces().add(crearPezMar(opcion, true));
                        sistemaMonedas.setMonedas(monedas - coste);
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                    else{
                        System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan " + (coste - monedas) + " monedas.");
                    }
                }
                else{
                    System.out.println("No se puede añadir el pez a la piscifactoría.");
                }
            }
        }
    }

    /**
    * Gestiona la lógica de añadir un pez a una piscifactoría de río.
    * @param piscifactoria Piscifactoría donde se va a añadir el pez.
    */
    private static void addFishRio(Piscifactoria piscifactoria){
        String[] pecesDisponiblesRio = {
            "Cancelar",
            AlmacenPropiedades.DORADA.getNombre() + " - " + AlmacenPropiedades.DORADA.getCoste() + " monedas",
            AlmacenPropiedades.PEJERREY.getNombre() + " - " + AlmacenPropiedades.PEJERREY.getCoste() + " monedas",
            AlmacenPropiedades.PERCA_EUROPEA.getNombre() + " - " + AlmacenPropiedades.PERCA_EUROPEA.getCoste() + " monedas",
            AlmacenPropiedades.ROBALO.getNombre() + " - " + AlmacenPropiedades.ROBALO.getCoste() + " monedas",
            AlmacenPropiedades.SALMON_ATLANTICO.getNombre() + " - " + AlmacenPropiedades.SALMON_ATLANTICO.getCoste() + " monedas",
            AlmacenPropiedades.SALMON_CHINOOK.getNombre() + " - " + AlmacenPropiedades.SALMON_CHINOOK.getCoste() + " monedas",
            AlmacenPropiedades.TILAPIA_NILO.getNombre() + " - " + AlmacenPropiedades.TILAPIA_NILO.getCoste() + " monedas"
            };

        int monedas = sistemaMonedas.getMonedas();
        System.out.println("Cartera: " +  monedas + " monedas.");
        int opcion = GeneradorMenus.generarMenuOperativo(pecesDisponiblesRio, 0, 7);

        if(opcion != 0){
            String nombrePez = pecesDisponiblesRio[opcion].split(" - ")[0];

            int indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(nombrePez);
            
            if(indiceTanqueConEspacioParaPez != -1){
                int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();
                monedas = sistemaMonedas.getMonedas();

                if(monedas >= coste){
                    Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);

                    if(tanque.pecesMacho() >= tanque.pecesHembra()){
                        tanque.getPeces().add(crearPezRio(opcion, true));
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                    else{
                        tanque.getPeces().add(crearPezRio(opcion, false));
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                }
                else{
                    System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan " + (coste - monedas) + " monedas.");
                }
            }
            else{
                int indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                if(indiceTanqueVacio != -1){
                    int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();
                    monedas = sistemaMonedas.getMonedas();

                    if(monedas >= coste){
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueVacio);
                        tanque.getPeces().add(crearPezRio(opcion, true));
                        sistemaMonedas.setMonedas(monedas - coste);
                        tanque.showCapacity(piscifactoria.getNombre());
                    }
                    else{
                        System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan " + (coste - monedas) + " monedas.");
                    }
                }
                else{
                    System.out.println("No se puede añadir el pez a la piscifactoría.");
                }
            }
        }
    }

    /**
     * Crea un pez que puede vivir en una piscifactoría de mar.
     * 
     * @param pez  Código numérico del pez a crear.
     * @param sexo Sexo del pez a crear.
     * @return Pez creado.
     */
    private static Pez crearPezMar(int pez, boolean sexo) {
        switch (pez) {
            case 1 -> {
                return new Abadejo(sexo);
            }
            case 2 -> {
                return new ArenqueDelAtlantico(sexo);
            }
            case 3 -> {
                return new Caballa(sexo);
            }
            case 4 -> {
                return new Dorada(sexo);
            }
            case 5 -> {
                return new Robalo(sexo);
            }
            case 6 -> {
                return new SalmonAtlantico(sexo);
            }
            case 7 -> {
                return new Sargo(sexo);
            }
            default -> {
                throw new IllegalArgumentException("Pez no reconocido.");
            }
        }
    }

    /**
     * Crear un pez que puede vivir en una piscifactoría de río.
     * 
     * @param pez  Código numérico del pez a crear.
     * @param sexo Sexo del pez a crear.
     * @return Pez creado.
     */
    private static Pez crearPezRio(int pez, boolean sexo) {
        switch (pez) {
            case 1 -> {
                return new Dorada(sexo);
            }
            case 2 -> {
                return new Pejerrey(sexo);
            }
            case 3 -> {
                return new PercaEuropea(sexo);
            }
            case 4 -> {
                return new Robalo(sexo);
            }
            case 5 -> {
                return new SalmonAtlantico(sexo);
            }
            case 6 -> {
                return new SalmonChinook(sexo);
            }
            case 7 -> {
                return new TilapiaDelNilo(sexo);
            }
            default -> {
                throw new IllegalArgumentException("Pez no reconocido.");
            }
        }
    }

    /**
     * Imprime por pantalla los datos relativos a un pez.
     * 
     * @param datosDelPez Datos relativos a un pez a mostrar.
     */
    private static void mostrarInformacionPez(PecesDatos datosDelPez) {
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
        for (PecesProps propiedad : datosDelPez.getPropiedades()) {
            System.out.print(propiedad + " ");
        }

        System.out.println("\nTipo: " + datosDelPez.getTipo());

    }

    /**
     * Método que vende todos los peces adultos que estén vivos en una piscifactoría
     * seleccionada.
     */
    private static void sell() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

            piscifactoria.sellFish();

            System.out.println("Se han vendido todos los peces maduros y vivos de la piscifactoría "
                    + piscifactoria.getNombre() + ".");
        }

    }

    /**
     * Elimina los peces muertos de la piscifactoría escogida.
     */
    private static void cleanTank() {

        int piscifactoriaSeleccionada = selectPisc();
        Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

        ArrayList<Tanque> tanques = piscifactoria.getTanques();

        for (Tanque tanque : tanques) {
            tanque.eliminarPecesMuertos();
        }

        System.out.println("Se han eliminado todos los peces muertos de los tanque de la piscifactoría "
                + piscifactoria.getNombre() + ".");
    }

    /**
     * Elimina todos los peces de un tanque, estén vivos o muertos.
     */
    private static void emptyTank() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

            int tanqueSeleccionado = selectTank(piscifactoria);

            if (tanqueSeleccionado != 0) {
                Tanque tanque = piscifactoria.getTanques().get(tanqueSeleccionado - 1);

                tanque.vaciarTanque();

                System.out.println("El tanque " + tanqueSeleccionado + " de la piscifactoría "
                        + piscifactoria.getNombre() + " ha sido vaciado.");
            }
        }
    }

    /**
     * Muestra un menú para hacer mejoras como comprar o mejorar edificios.
     */
    private static void upgrade() {
        System.out.println("========== Mejorar ==========");
        String[] opciones = {
                "Comprar edificios",
                "Mejorar edificios",
                "Cancelar"
        };

        int opcion = GeneradorMenus.generarMenuOperativo(opciones, 1, 3);

        switch (opcion) {
            case 1:
                comprarEdificio();
                break;
            case 2:
                mejorarEdificio();
                break;
        }
    }

    /**
     * Muestra un menú para comprar edificios.
     */
    private static void comprarEdificio() {
        System.out.println("========== Comprar edificio ==========");
        if (almacenCentral != null) {

            String[] opciones = { "Cancelar", "Comprar piscifactoría" };
            int opcion = GeneradorMenus.generarMenuOperativo(opciones, 0, 1);

            if (opcion == 1) {
                comprarPiscifactoria();
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            String[] opciones = { "Cancelar", "Comprar piscifactoría", "Comprar almacén central - 2000 monedas" };
            int opcion = GeneradorMenus.generarMenuOperativo(opciones, 0, 2);

            switch (opcion) {
                case 1:
                    comprarPiscifactoria();
                    break;
                case 2:
                    if (sistemaMonedas.getMonedas() >= 2000) {
                        sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - 2000);
                        almacenCentral = new AlmacenCentral();
                        System.out.println("Almacén central comprado.");
                    } else {
                        System.out.println("No tiene suficientes monedas para comprar el almacén central, faltan " + (2000 - sistemaMonedas.getMonedas()) + " monedas.");
                    }
                    break;
            }
        }
    }

    /**
     * Permite al usuario comprar una nueva piscifactoría.
     */
    private static void comprarPiscifactoria() {
        int tipoPiscifactoría = seleccionarTipoPiscifactoría();
        if (tipoPiscifactoría != 0) {
            System.out.println("Escriba el nombre de la piscifactoría: ");
            String nombrePiscifactoría = SistemaEntrada.entradaTexto();
            int costoPiscifactoría = calcularCostoPiscifactoría(tipoPiscifactoría);

            if (sistemaMonedas.getMonedas() >= costoPiscifactoría) {
                sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoPiscifactoría);

                Piscifactoria nuevaPiscifactoria;
                if (tipoPiscifactoría == 1) {
                    nuevaPiscifactoria = new PiscifactoriaRio(nombrePiscifactoría);
                } else {
                    nuevaPiscifactoria = new PiscifactoriaMar(nombrePiscifactoría);
                } 

                Simulador.piscifactorias.add(nuevaPiscifactoria);
                System.out.println("Piscifactoría " + nombrePiscifactoría + " comprada.");
            } else {
                System.out.println("No tienes suficientes monedas para comprar esta piscifactoría, faltan " + (costoPiscifactoría - sistemaMonedas.getMonedas()) + " monedas.");
            }
        }
    }

    /**
     * Muestra un menú para mejorar edificios existentes.
     */
    private static void mejorarEdificio() {
        System.out.println("========== Mejorar edificio ==========");
        if (almacenCentral != null) {
            String[] opciones = {
                    "Cancelar",
                    "Mejorar una piscifactoría",
                    "Mejorar el almacén central"
            };
            int opcion = GeneradorMenus.generarMenuOperativo(opciones, 0, 2);

            switch (opcion) {
                case 1:
                    mejorarPiscifactoria();
                    break;
                case 2:
                    aumentarCapacidadAlmacenCentral();
                    break;
            }
        } else {
            String[] opciones = { "Cancelar", "Mejorar una piscifactoría" };
            int opcion = GeneradorMenus.generarMenuOperativo(opciones, 0, 1);

            if (opcion != 0) {
                mejorarPiscifactoria();
            } else {
                System.out.println("Operación cancelada.");
            }
        }
    }

    /**
     * Permite al usuario mejorar una piscifactoría seleccionada.
     */
    private static void mejorarPiscifactoria() {
        menuPisc();
        int piscifactoriaSeleccionada = SistemaEntrada.entradaOpcionNumerica(0, piscifactorias.size());

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
            System.out.println("========== Mejorar piscifactoría ==========");
            String[] opcionesMejora = {
                    "Cancelar",
                    "Comprar tanque",
                    "Aumentar almacén de comida"
            };

            int opcionMejora = GeneradorMenus.generarMenuOperativo(opcionesMejora, 0, 2);

            switch (opcionMejora) {
                case 1:
                    comprarTanque(piscifactoria);
                    break;
                case 2:
                    piscifactoria.upgradeFood();
                    break;
            }
        }
    }

    /**
     * Permite al usuario comprar un tanque para la piscifactoría especificada.
     * 
     * @param piscifactoria La piscifactoría en la que se comprará el tanque.
     */
    private static void comprarTanque(Piscifactoria piscifactoria) {
        int monedasDisponibles = sistemaMonedas.getMonedas();

        if (piscifactoria instanceof PiscifactoriaRio) {

            PiscifactoriaRio piscifactoriaRio = (PiscifactoriaRio) piscifactoria;
            int costoTanque = 150 * piscifactoriaRio.getTanques().size();

            if (monedasDisponibles >= costoTanque) {
                sistemaMonedas.setMonedas(monedasDisponibles - costoTanque);
                Tanque nuevoTanque = new Tanque(piscifactoriaRio.getTanques().size() + 1, 25);
                piscifactoriaRio.getTanques().add(nuevoTanque);
                System.out.println("Tanque añadido a la piscifactoría " + piscifactoriaRio.getNombre()
                        + ". Total de tanques: " + piscifactoriaRio.getTanques().size());
            } else {
                System.out.println("No tienes suficientes monedas para comprar un tanque, faltan " + (costoTanque - monedasDisponibles) + " monedas.");
            }
        } else if (piscifactoria instanceof PiscifactoriaMar) {

            PiscifactoriaMar piscifactoriaMar = (PiscifactoriaMar) piscifactoria;
            int costoTanque = 600 * piscifactoriaMar.getTanques().size();

            if (monedasDisponibles >= costoTanque) {
                sistemaMonedas.setMonedas(monedasDisponibles - costoTanque);
                Tanque nuevoTanque = new Tanque(piscifactoriaMar.getTanques().size() + 1, 100);
                piscifactoriaMar.getTanques().add(nuevoTanque);
                System.out.println("Tanque añadido a la piscifactoría " + piscifactoriaMar.getNombre()
                        + ". Total de tanques: " + piscifactoriaMar.getTanques().size());
            } else {
                System.out.println("No tienes suficientes monedas para comprar un tanque, faltan " + (costoTanque - monedasDisponibles) + " monedas.");
            }
        }
    }

    /**
     * Aumenta la capacidad del almacén central.
     */
    private static void aumentarCapacidadAlmacenCentral() {
        int costoAumento = 200;
        int monedasDisponibles = sistemaMonedas.getMonedas();

        if (monedasDisponibles >= costoAumento) {
            sistemaMonedas.setMonedas(monedasDisponibles - costoAumento);
            almacenCentral.mejorar();
            System.out.println("Capacidad del almacén central aumentada en 50 unidades.");
        } else {
            System.out.println("No tienes suficientes monedas para aumentar la capacidad del almacén central, faltan " + (costoAumento - monedasDisponibles) + " monedas.");
        }
    }

    /**
     * Muestra un menú para seleccionar el tipo de piscifactoría.
     * 
     * @return El tipo de piscifactoría seleccionado como una cadena de caracteres.
     */
    private static int seleccionarTipoPiscifactoría() {
        System.out.println("========== Tipo piscifactoría ==========");
        String[] opcionesTipo = {
                "Cancelar",
                "Río",
                "Mar"
        };

        return GeneradorMenus.generarMenuOperativo(opcionesTipo, 0, 2);
    }

    /**
     * Calcula el costo de una piscifactoría en función de su tipo.
     * 
     * @param tipo El tipo de piscifactoría.
     * @return El costo de la piscifactoría.
     */
    private static int calcularCostoPiscifactoría(int tipo) {
        int cantidadPiscifactoria;

        if (tipo == 1) {
            cantidadPiscifactoria = numeroPiscifactoriasRio();
            return Math.max(500 * cantidadPiscifactoria, 500);
        } else {
            cantidadPiscifactoria = numeroPiscifactoriasMar();
            return Math.max(2000 * cantidadPiscifactoria, 2000);
        }
    }

    /**
     * 
     * @return Número de piscifactorías de río de la simulación.
     */
    private static int numeroPiscifactoriasRio(){
        int numeroPiscifactoriasRio = 0;

        for(Piscifactoria piscifactoria : piscifactorias){
            if(piscifactoria instanceof PiscifactoriaRio){
                numeroPiscifactoriasRio += 1;
            }
        }

        return numeroPiscifactoriasRio;
    }

    /**
     * 
     * @return Número de piscifactorías de mar de la simulación.
     */
    private static int numeroPiscifactoriasMar(){
        int numeroPiscifactoriasMar = 0;

        for(Piscifactoria piscifactoria : piscifactorias){
            if(piscifactoria instanceof PiscifactoriaMar){
                numeroPiscifactoriasMar += 1;
            }
        }

        return numeroPiscifactoriasMar;
    }

    /**
     * Gestiona la lógica para mostrar el estado de un tanque de una piscifactoría
     * seleccionada.
     */
    private static void mostrarEstadoTanque() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            showTankStatus(piscifactorias.get(piscifactoriaSeleccionada - 1));
        }
    }

    /**
     * Método para pasar varios días en la simulación. 
     */
    private static void pasarDias() {
        int pecesVendidos = 0;
        int dineroAntes;
        int monedasGanadas = 0;
        int dias = 0;
        int dineroDespues;

        System.out.println("Introduce el número de días que deseas avanzar: ");
        dias = SistemaEntrada.entradaOpcionNumericaEnteraPositiva();

        for (int i = 0; i < dias; i++) {
            for (Piscifactoria piscifactoria : piscifactorias) {
                dineroAntes = sistemaMonedas.getMonedas();
                pecesVendidos += piscifactoria.nextDay();
                dineroDespues = sistemaMonedas.getMonedas();

                monedasGanadas += (dineroDespues - dineroAntes);

            }

            if(almacenCentral != null){
                repartirComida();
            }
        }

        diasPasados += dias;

        System.out.println("En estos " + dias + " días se han vendido " + pecesVendidos
                + " peces y se han ganado " + monedasGanadas + " monedas.");
    }

    /**
     * Añade 4 peces aleatorios a una piscifactoría seleccionada por el usuario.
     */
    private static void anadirPezAleatorio(){
        int piscifactoriaSeleccionada = selectPisc();

        if(piscifactoriaSeleccionada != 0){
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
            int pecesAnadidos = 0;
            Random rt = new Random();

            if(piscifactoria instanceof PiscifactoriaMar){
                String[] pecesDisponiblesMar = {
                    AlmacenPropiedades.ABADEJO.getNombre(),
                    AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                    AlmacenPropiedades.CABALLA.getNombre(),
                    AlmacenPropiedades.DORADA.getNombre(),
                    AlmacenPropiedades.ROBALO.getNombre(),
                    AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                    AlmacenPropiedades.SARGO.getNombre(),
                };

                int pezAleatorio;
                int indiceTanqueConEspacioParaPez;
                int indiceTanqueVacio;
                ArrayList<Integer> posiblesPeces = new ArrayList<>();
                posiblesPeces.add(0);
                posiblesPeces.add(1);
                posiblesPeces.add(2);
                posiblesPeces.add(3);
                posiblesPeces.add(4);
                posiblesPeces.add(5);
                posiblesPeces.add(6);

                while(!piscifactoria.isTodosLosTanqueLlenos() && pecesAnadidos < 4){
                    pezAleatorio = posiblesPeces.get(rt.nextInt(posiblesPeces.size()));
                    indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                    indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(pecesDisponiblesMar[pezAleatorio]);

                    
                    if(indiceTanqueConEspacioParaPez != -1){
                        while(indiceTanqueConEspacioParaPez != -1 && pecesAnadidos < 4){
                            Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);
                                
                            if(tanque.pecesMacho() >= tanque.pecesHembra()){
                                tanque.getPeces().add(crearPezMar(pezAleatorio + 1, true));
                                pecesAnadidos++;
                            }
                            else{
                                tanque.getPeces().add(crearPezMar(pezAleatorio + 1, false));
                                pecesAnadidos++;
                            }
                        }
                    }
                    else{
                        if(indiceTanqueVacio != -1){
                            piscifactoria.getTanques().get(indiceTanqueVacio).getPeces().add(crearPezMar(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        }
                        else{
                            posiblesPeces.remove(posiblesPeces.indexOf(pezAleatorio));
                        }
                    } 
                }
            }
            else{
                String[] pecesDisponiblesRio = {
                    AlmacenPropiedades.DORADA.getNombre(),
                    AlmacenPropiedades.PEJERREY.getNombre(),
                    AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
                    AlmacenPropiedades.ROBALO.getNombre(),
                    AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                    AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
                    AlmacenPropiedades.TILAPIA_NILO.getNombre(),
                    };

                int pezAleatorio;
                int indiceTanqueConEspacioParaPez;
                int indiceTanqueVacio;
                ArrayList<Integer> posiblesPeces = new ArrayList<>();
                posiblesPeces.add(0);
                posiblesPeces.add(1);
                posiblesPeces.add(2);
                posiblesPeces.add(3);
                posiblesPeces.add(4);
                posiblesPeces.add(5);
                posiblesPeces.add(6);

                while(!piscifactoria.isTodosLosTanqueLlenos() && pecesAnadidos < 4){
                    pezAleatorio = posiblesPeces.get(rt.nextInt(posiblesPeces.size()));
                    indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                    indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(pecesDisponiblesRio[pezAleatorio]);

                    if(indiceTanqueConEspacioParaPez != -1){
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);
                                
                        if(tanque.pecesMacho() >= tanque.pecesHembra()){
                            tanque.getPeces().add(crearPezRio(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        }
                        else{
                            tanque.getPeces().add(crearPezRio(pezAleatorio + 1, false));
                            pecesAnadidos++;
                        }
                        
                    }
                    else{
                        if(indiceTanqueVacio != -1){
                            piscifactoria.getTanques().get(indiceTanqueVacio).getPeces().add(crearPezRio(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        }
                        else{
                            posiblesPeces.remove(posiblesPeces.indexOf(pezAleatorio));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Reparte la comida del almacén central equitativamente entre las piscafactorías.
     */
    private static void repartirComida(){
        repartirComidaAnimal();
        repartirComidaVegetal();
    }

    /**
     * Indica si todas las piscifactorías que no están llenas están en la media en cuanto a cantidad de comida animal.
     * @param mediaCantidadComidaAnimal Media de la cantidad de comida animal.
     * @return True si todas las piscifactorías que no están llenas están en la media en cuanto a cantidad de comida animal.
     */
    private static boolean todasLasPiscifactoriasEnLaMediaComidaAnimal(int mediaCantidadComidaAnimal){
        Piscifactoria.AlmacenComida almacenComida;
        int cantidadComidaAnimalPiscifactoria;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComida = piscifactoria.getAlmacenInicial();
            cantidadComidaAnimalPiscifactoria = almacenComida.getCantidadComidaAnimal();
            if((cantidadComidaAnimalPiscifactoria != mediaCantidadComidaAnimal) && (cantidadComidaAnimalPiscifactoria != almacenComida.getCapacidadMaximaComida())){
                return false;
            }
        }

        return true;
    }

    /**
     * Indica si todas las piscifactorías están llenas de comida animal.
     * @return True si todas las piscifactorías están llenas de comida animal.
     */
    private static boolean todasLasPiscifactoriasLlenasDeComidaAnimal(){
        Piscifactoria.AlmacenComida almacenComida;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComida = piscifactoria.getAlmacenInicial();
            if(almacenComida.getCantidadComidaAnimal() < almacenComida.getCapacidadMaximaComida()){
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve la media de comida animal de las piscifactorías que no están llenas.
     * @return Media de comida animal de las piscifactorías que no están llenas.
     */
    private static int mediaComidaAnimal(){
        int cantidadComidaAnimal = 0;
        int piscifactoriasNoLlenas = 0;
        Piscifactoria.AlmacenComida almacenComidaPiscifactoria;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();

            if(almacenComidaPiscifactoria.getCantidadComidaAnimal() != almacenComidaPiscifactoria.getCapacidadMaximaComida()){
                cantidadComidaAnimal += piscifactoria.getAlmacenInicial().getCantidadComidaAnimal();
                piscifactoriasNoLlenas += 1;
            }
        }

       return (cantidadComidaAnimal / piscifactoriasNoLlenas);
    }

    /**
     * Gestiona la lógica de distribución equitativa de la comida animal del almacén central a las piscifactorías.
     * @param mediaCantidadComidaAnimal Cantidad de comida animal media por piscifactoría.
     */
    private static void repartirComidaAnimal(){
        ArrayList<Piscifactoria> piscifactoriaOrdenadoPorCantidadComidaAnimal = new ArrayList<>(piscifactorias);

        AlmacenComida almacenComidaPiscifactoria;
        int cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
        int mediaCantidadComidaAnimal;

        while(!todasLasPiscifactoriasLlenasDeComidaAnimal() && cantidadComidaAnimalAlmacenCentral != 0){
            mediaCantidadComidaAnimal = mediaComidaAnimal();
            if(!todasLasPiscifactoriasEnLaMediaComidaAnimal(mediaCantidadComidaAnimal)){
                Collections.sort(piscifactoriaOrdenadoPorCantidadComidaAnimal, new Comparator<Piscifactoria>() {

                    /**
                     * Compara la cantidad de comida animal de dos piscifactorías.
                     * @param piscifactoria1 Primera piscifactoría a comparar.
                     * @param piscifactoria2 Segunda piscifactoría a comparar.
                     * @return 1 si la primera piscifactoría tiene mayor cantidad de comida animal, 0 si 
                     * ambas tienen la misma cantidad de comida animal y -1 si la cantidad de comida animal es mayor en
                     * la segunda.
                     */
                    @Override
                    public int compare(Piscifactoria piscifactoria1, Piscifactoria piscifactoria2){
                        int cantidadComidaAnimalPiscifactoria1 = piscifactoria1.getAlmacenInicial().getCantidadComidaAnimal();
                        int cantidadComidaAnimalPiscifactoria2 = piscifactoria2.getAlmacenInicial().getCantidadComidaAnimal();

                        if(cantidadComidaAnimalPiscifactoria1 > cantidadComidaAnimalPiscifactoria2){
                            return 1;
                        }
                        else{
                            if(cantidadComidaAnimalPiscifactoria1 == cantidadComidaAnimalPiscifactoria2){
                                return 0;
                            }
                            else{
                                return -1;
                            }
                        }
                    }
                });

                int cantidadComidaAnimalPiscifactoria;
                int cantidadDeComidaAAnadir;
                int capacidadComidaAlmacen;
                for(Piscifactoria piscifactoria : piscifactoriaOrdenadoPorCantidadComidaAnimal){
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaAnimalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaAnimal();
                    capacidadComidaAlmacen = almacenComidaPiscifactoria.getCapacidadMaximaComida();

                    if(cantidadComidaAnimalPiscifactoria < mediaCantidadComidaAnimal){
                        if(cantidadComidaAnimalAlmacenCentral != 0){
                            cantidadDeComidaAAnadir = mediaCantidadComidaAnimal - cantidadComidaAnimalPiscifactoria;

                            if(cantidadDeComidaAAnadir + cantidadComidaAnimalPiscifactoria > capacidadComidaAlmacen){
                                cantidadDeComidaAAnadir = capacidadComidaAlmacen - cantidadComidaAnimalPiscifactoria;
                            }

                            if(cantidadComidaAnimalAlmacenCentral >= cantidadDeComidaAAnadir){
                                almacenCentral.setCantidadComidaAnimal(cantidadComidaAnimalAlmacenCentral - cantidadDeComidaAAnadir);
                                almacenComidaPiscifactoria.setCantidadComidaAnimal(mediaCantidadComidaAnimal);
                                cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
                            }
                            else{
                                almacenCentral.setCantidadComidaAnimal(0);
                                almacenComidaPiscifactoria.setCantidadComidaAnimal(cantidadComidaAnimalAlmacenCentral + cantidadComidaAnimalPiscifactoria);
                                cantidadComidaAnimalAlmacenCentral = 0;
                            }
                        }
                    }
                }
            }
            else{
                int cantidadComidaAnimalPiscifactoria;

                for(Piscifactoria piscifactoria : piscifactorias){
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaAnimalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaAnimal();

                    if(cantidadComidaAnimalAlmacenCentral != 0 && (cantidadComidaAnimalPiscifactoria < almacenComidaPiscifactoria.getCapacidadMaximaComida())){
                        almacenComidaPiscifactoria.setCantidadComidaAnimal(cantidadComidaAnimalPiscifactoria + 1);
                        almacenCentral.setCantidadComidaAnimal(almacenCentral.getCantidadComidaAnimal() - 1);
                        cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
                    }
                }
            }
        }
    }

    /**
     * Indica si todas las piscifactorías que no están llenas están en la media en cuanto a cantidad de comida vegetal.
     * @param mediaCantidadComidaVegetal Media de la cantidad de comida vegetal.
     * @return True si todas las piscifactorías que no están llenas están en la media en cuanto a cantidad de comida vegetal.
     */
    private static boolean todasLasPiscifactoriasEnLaMediaComidaVegetal(int mediaCantidadComidaVegetal){
        Piscifactoria.AlmacenComida almacenComida;
        int cantidadComidaVegetalPiscifactoria;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComida = piscifactoria.getAlmacenInicial();
            cantidadComidaVegetalPiscifactoria = almacenComida.getCantidadComidaVegetal();

            if((cantidadComidaVegetalPiscifactoria != mediaCantidadComidaVegetal) && (cantidadComidaVegetalPiscifactoria != almacenComida.getCapacidadMaximaComida())){
                return false;
            }
        }

        return true;
    }

    /**
     * Indica si todas las piscifactorías están llenas de comida vegetal.
     * @return True si todas las piscifactorías están llenas de comida vegetal.
     */
    private static boolean todasLasPiscifactoriasLlenasDeComidaVegetal(){
        Piscifactoria.AlmacenComida almacenComida;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComida = piscifactoria.getAlmacenInicial();
            if(almacenComida.getCantidadComidaVegetal() < almacenComida.getCapacidadMaximaComida()){
                return false;
            }   
        }

        return true;
    }

    /**
     * Devuelve la media de comida vegetal de las piscifactorías que no están llenas.
     * @return Media de comida vegetal de las piscifactorías que no están llenas.
     */
    private static int mediaComidaVegetal(){
        int cantidadComidaVegetal = 0;
        int piscifactoriasNoLlenas = 0;
        Piscifactoria.AlmacenComida almacenComidaPiscifactoria;

        for(Piscifactoria piscifactoria : piscifactorias){
            almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();

            if(almacenComidaPiscifactoria.getCantidadComidaVegetal() != almacenComidaPiscifactoria.getCapacidadMaximaComida()){
                cantidadComidaVegetal += piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();
                piscifactoriasNoLlenas += 1;
            }
        }

       return (cantidadComidaVegetal / piscifactoriasNoLlenas);
    }

    /**
     * Gestiona la lógica de distribución equitativa de la comida vegetal del almacén central a las piscifactorías.
     * @param mediaCantidadComidaVegetal Cantidad de comida vegetal media por piscifactoría.
     */
    private static void repartirComidaVegetal(){
        ArrayList<Piscifactoria> piscifactoriaOrdenadoPorCantidadComidaVegetal = new ArrayList<>(piscifactorias);

        AlmacenComida almacenComidaPiscifactoria;
        int cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
        int mediaCantidadComidaVegetal; 

        while(!todasLasPiscifactoriasLlenasDeComidaVegetal() && cantidadComidaVegetalAlmacenCentral != 0){
            mediaCantidadComidaVegetal = mediaComidaVegetal();
            if(!todasLasPiscifactoriasEnLaMediaComidaVegetal(mediaComidaVegetal())){
                Collections.sort(piscifactoriaOrdenadoPorCantidadComidaVegetal, new Comparator<Piscifactoria>() {

                    /**
                     * Compara la cantidad de comida vegetal de dos piscifactorías.
                     * @param piscifactoria1 Primera piscifactoría a comparar.
                     * @param piscifactoria2 Segunda piscifactoría a comparar.
                     * @return 1 si la primera piscifactoría tiene mayor cantidad de comida vegetal, 0 si 
                     * ambas tienen la misma cantidad de comida vegetal y -1 si la cantidad de comida vegetal es mayor en
                     * la segunda.
                     */
                    @Override
                    public int compare(Piscifactoria piscifactoria1, Piscifactoria piscifactoria2){
                        int cantidadComidaVegetalPiscifactoria1 = piscifactoria1.getAlmacenInicial().getCantidadComidaVegetal();
                        int cantidadComidaVegetalPiscifactoria2 = piscifactoria2.getAlmacenInicial().getCantidadComidaVegetal();

                        if(cantidadComidaVegetalPiscifactoria1 > cantidadComidaVegetalPiscifactoria2){
                            return 1;
                        }
                        else{
                            if(cantidadComidaVegetalPiscifactoria1 == cantidadComidaVegetalPiscifactoria2){
                                return 0;
                            }
                            else{
                                return -1;
                            }
                        }
                    }
                });

                int cantidadComidaVegetalPiscifactoria;
                int cantidadDeComidaAAnadir;
                int capacidadComidaAlmacen;
                for(Piscifactoria piscifactoria : piscifactoriaOrdenadoPorCantidadComidaVegetal){
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaVegetalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaVegetal();
                    capacidadComidaAlmacen = almacenComidaPiscifactoria.getCapacidadMaximaComida();

                    if(cantidadComidaVegetalPiscifactoria < mediaCantidadComidaVegetal){
                        if(cantidadComidaVegetalAlmacenCentral != 0){
                            cantidadDeComidaAAnadir = mediaCantidadComidaVegetal - cantidadComidaVegetalPiscifactoria;

                            if(cantidadDeComidaAAnadir + cantidadComidaVegetalPiscifactoria > capacidadComidaAlmacen){
                                cantidadDeComidaAAnadir = capacidadComidaAlmacen - cantidadComidaVegetalPiscifactoria;
                            }

                            if(cantidadComidaVegetalAlmacenCentral >= cantidadDeComidaAAnadir){
                                almacenCentral.setCantidadComidaVegetal(cantidadComidaVegetalAlmacenCentral - cantidadDeComidaAAnadir);
                                almacenComidaPiscifactoria.setCantidadComidaVegetal(mediaCantidadComidaVegetal);
                                cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
                            }
                            else{
                                almacenCentral.setCantidadComidaVegetal(0);
                                almacenComidaPiscifactoria.setCantidadComidaVegetal(cantidadComidaVegetalAlmacenCentral + cantidadComidaVegetalPiscifactoria);
                                cantidadComidaVegetalAlmacenCentral = 0;
                            }
                        }
                    }
                }
            }
            else{
                int cantidadComidaVegetalPiscifactoria;
                for(Piscifactoria piscifactoria : piscifactorias){
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaVegetalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaVegetal();

                    if(cantidadComidaVegetalAlmacenCentral != 0 && (cantidadComidaVegetalPiscifactoria < almacenComidaPiscifactoria.getCapacidadMaximaComida())){
                        almacenComidaPiscifactoria.setCantidadComidaVegetal(cantidadComidaVegetalAlmacenCentral + 1);
                        almacenCentral.setCantidadComidaVegetal(almacenCentral.getCantidadComidaVegetal() - 1);
                        cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
                    }
                }
            }
        }
    }

    /**
     * Devuelve un string con información relevante de la clase.
     * @return String con información relevante de la clase.
     */
    public String toString(){
        return "Nombre empresa, entidad o partida: " + nombre + "\nDías pasado en la simulación: " + diasPasados + "\nNúmero piscifactoría simulación: " + piscifactorias.size();
    }

    /**
     * Método principal del programa que gestiona el uso del programa por parte del
     * usuario.
     * 
     * @param args Argumentos pasados por línea de comandos.
     */
    public static void main(String[] args) {
        init();

        int opcion = 0;
        int[] opcionesNumericas = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 98, 99 };

        while (opcion != 14) {
            System.out.println("Día actual: " + (diasPasados + 1));
            menu();

            opcion = SistemaEntrada.entradaOpcionNumerica(opcionesNumericas);

            switch(opcion){
                case 1 -> {showGeneralStatus();}
                case 2 -> {showSpecificStatus();}
                case 3 -> {mostrarEstadoTanque();}
                case 4 -> {showStats();}
                case 5 -> {showIctio();}
                case 6 -> {nextDay();
                            showGeneralStatus();}
                case 7 -> {addFood();}
                case 8 -> {addFish();}
                case 9 -> {sell();}
                case 10 -> {cleanTank();}
                case 11 -> {emptyTank();}
                case 12 -> {upgrade();}
                case 13 -> {pasarDias();}
                case 14 -> {System.out.println("Cerrando...");}
                case 98 -> {anadirPezAleatorio();}
                case 99 -> {sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() + 1000);}
            }
        }

        SistemaEntrada.close();
    }

}
