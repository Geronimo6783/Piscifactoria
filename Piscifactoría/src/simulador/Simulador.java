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
     * Muestra el menú de tanques de una piscifactoría seleccionada y permite al
     * usuario seleccionar uno.
     * 
     * @return El índice del tanque seleccionado.
     */
    private static int selectTank() {
        int piscifactoriaSeleccionada = selectPisc();

        Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
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

        GeneradorMenus.generarMenu(opcionesTanques, 0);

        return SistemaEntrada.entradaOpcionNumerica(0, tanques.size());

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
     * Muestra un menú para seleccionar un tanque de una una piscifactoría y
     * posteriormente el estado de los peces en dicho tanque.
     * 
     * @param piscifactoria La piscifactoría que contiene los tanques disponibles.
     */
    public static void showTankStatus(Piscifactoria piscifactoria) {
        int opcionTanque = selectTank();

        Tanque tanqueSeleccionado = piscifactoria.getTanques().get(opcionTanque - 1);

        if (tanqueSeleccionado.getPeces().isEmpty()) {
            System.out.println("El tanque seleccionado no contiene peces.");
        } else {
            System.out.println("Estado de los peces en el tanque " + opcionTanque + ":");
            tanqueSeleccionado.showFishStatus();
        }

    }

    /**
     * Muestra un desglose de las estadísticas por cada tipo de pez.
     */
    private static void showStats() {
        estadisticas.mostrar();
    }

    /**
     * Muestra la información relativa a un pez seleccionado por el usuario.
     */
    private static void showIctio() {
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
        int pecesAntesDePasarDia;
        int dineroAntesDePasarDia;
        int pecesVendidos = 0;
        int monedasGanadas = 0;

        for (Piscifactoria piscifactoria : piscifactorias) {
            pecesAntesDePasarDia = piscifactoria.getPecesTotales();
            dineroAntesDePasarDia = sistemaMonedas.getMonedas();
            piscifactoria.nextDay();
            System.out.println("Piscifactoría " + piscifactoria.getNombre() + ":"
                    + (pecesAntesDePasarDia - piscifactoria.getPecesTotales()) + " peces vendidos por un total de "
                    + (sistemaMonedas.getMonedas() - dineroAntesDePasarDia) + " monedas.");
            pecesVendidos += pecesAntesDePasarDia - piscifactoria.getPecesTotales();
            monedasGanadas += sistemaMonedas.getMonedas() - dineroAntesDePasarDia;
        }

        System.out.println(pecesVendidos + " peces vendidos por un total de " + monedasGanadas + " monedas.");
    }

    /**
     * Gestiona la compra de comida para una piscifactoría o el almacén central.
     */
    private static void addFood() {
        if (almacenCentral != null) {
            int tipoComida = menuTipoComida();
            int cantidadAAgregar = menuCantidadComida();

            if (tipoComida == 1) {
                int cantidadComidaAnimalAntes = almacenCentral.getCantidadComidaAnimal();
                if (cantidadComidaAnimalAntes + cantidadAAgregar <= almacenCentral.getCapacidadComida()) {
                    almacenCentral.setCantidadComidaAnimal(cantidadComidaAnimalAntes + cantidadAAgregar);

                    int costoAnimal = calcularCosto(cantidadAAgregar);
                    if (sistemaMonedas.getMonedas() >= costoAnimal) {
                        sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoAnimal);
                        System.out.println("Añadida " + cantidadAAgregar + " de comida animal.");
                        System.out.println("Depósito de comida animal en el almacén central " +
                                cantidadComidaAnimalAntes + "/" + almacenCentral.getCapacidadComida() +
                                " al " + String.format("%.2f",
                                        (float) (cantidadComidaAnimalAntes + cantidadAAgregar) /
                                                almacenCentral.getCapacidadComida() * 100)
                                + "%.");
                    } else {
                        System.out.println("No hay suficientes monedas para comprar la comida animal.");
                    }
                } else {
                    System.out.println("No se puede añadir comida animal, excede la capacidad del almacén central.");
                }
            } else if (tipoComida == 2) {
                int cantidadComidaVegetalAntes = almacenCentral.getCantidadComidaVegetal();
                if (cantidadComidaVegetalAntes + cantidadAAgregar <= almacenCentral.getCapacidadComida()) {
                    almacenCentral.setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

                    int costoVegetal = calcularCosto(cantidadAAgregar);
                    if (sistemaMonedas.getMonedas() >= costoVegetal) {
                        sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoVegetal);
                        System.out.println("Añadida " + cantidadAAgregar + " de comida vegetal.");
                        System.out.println("Depósito de comida vegetal en el almacén central " +
                                cantidadComidaVegetalAntes + "/" + almacenCentral.getCapacidadComida() +
                                " al " + String.format("%.2f",
                                        (float) (cantidadComidaVegetalAntes + cantidadAAgregar) /
                                                almacenCentral.getCapacidadComida() * 100)
                                + "%.");
                    } else {
                        System.out.println("No hay suficientes monedas para comprar la comida vegetal.");
                    }
                } else {
                    System.out.println("No se puede añadir comida vegetal, excede la capacidad del almacén central.");
                }
            }
        } else {
            int piscifactoriaSeleccionada = selectPisc();
            if (piscifactoriaSeleccionada != 0) {
                Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
                int tipoComida = menuTipoComida();
                int cantidadAAgregar = menuCantidadComida();

                if (tipoComida == 1) {

                    int cantidadMaximaAnimal = piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                            - piscifactoria.getAlmacenInicial().getCantidadComidaAnimal();

                    if (cantidadAAgregar > cantidadMaximaAnimal) {
                        cantidadAAgregar = cantidadMaximaAnimal;
                    }

                    if (cantidadAAgregar > 0) {
                        int cantidadComidaAnimalAntes = piscifactoria.getAlmacenInicial().getCantidadComidaAnimal();
                        piscifactoria.getAlmacenInicial()
                                .setCantidadComidaAnimal(cantidadComidaAnimalAntes + cantidadAAgregar);

                        int costoAnimal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoAnimal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoAnimal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida animal a la piscifactoria "
                                    + piscifactoria.getNombre() + ".");
                            System.out.println("Depósito de comida animal en la piscifactoria "
                                    + piscifactoria.getNombre()
                                    + ": " + piscifactoria.getAlmacenInicial().getCantidadComidaAnimal() + "/"
                                    + piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaAnimalAntes + cantidadAAgregar) /
                                                    piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() * 100)
                                    + "%.");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida animal.");
                        }
                    } else {
                        System.out.println(
                                "No se puede añadir comida animal, la capacidad de la piscifactoría está llena.");
                    }
                } else if (tipoComida == 2) {
                    int cantidadMaximaVegetal = piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                            - piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();

                    if (cantidadAAgregar > cantidadMaximaVegetal) {
                        cantidadAAgregar = cantidadMaximaVegetal;
                    }

                    if (cantidadAAgregar > 0) {
                        int cantidadComidaVegetalAntes = piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();
                        piscifactoria.getAlmacenInicial()
                                .setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

                        int costoVegetal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoVegetal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoVegetal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida vegetal a la piscifactoria "
                                    + piscifactoria.getNombre() + ".");
                            System.out.println("Depósito de comida vegetal en la piscifactoria "
                                    + piscifactoria.getNombre()
                                    + ": " + piscifactoria.getAlmacenInicial().getCantidadComidaVegetal() + "/"
                                    + piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaVegetalAntes + cantidadAAgregar) /
                                                    piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() * 100)
                                    + "%.");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida vegetal.");
                        }
                    } else {
                        System.out.println(
                                "No se puede añadir comida vegetal, la capacidad de la piscifactoría está llena.");
                    }
                }
            }
        }

    }

    /**
     * Muestra un menú para seleccionar el tipo de comida (animal o vegetal).
     * 
     * @return 1 para comida animal, 2 para comida vegetal, 0 para cancelar.
     */
    private static int menuTipoComida() {
        String[] opciones = { "Cancelar", "Comida animal", "Comida vegetal" };
        GeneradorMenus.generarMenu(opciones, 0);

        int seleccion = SistemaEntrada.entradaOpcionNumerica(0, 2);

        if (seleccion == 1) {
            return 1;
        } else if (seleccion == 2) {
            return 2;
        }
        return 0;
    }

    /**
     * Muestra un menú para seleccionar la cantidad de comida a añadir.
     * 
     * @return cantidad seleccionada.
     */
    private static int menuCantidadComida() {
        String[] opciones = { "Cancelar", "5", "10", "25", "Llenar" };
        GeneradorMenus.generarMenu(opciones, 0);

        int seleccion = SistemaEntrada.entradaOpcionNumerica(0, 4);

        if (seleccion == 1)
            return 5;
        else if (seleccion == 2)
            return 10;
        else if (seleccion == 3)
            return 25;
        else if (seleccion == 4)
            return Integer.MAX_VALUE;
        return 0;
    }

    /**
     * Calcula el costo de la comida a añadir, aplicando descuentos cada 25
     * unidades.
     * 
     * @param cantidad Cantidad de comida a añadir.
     * @return costo total en monedas.
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
    public static void addFish() { // Falta gestionar el coste
        int piscifactoriaSeleccionada = selectPisc();
        Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

        String[] pecesDisponibles = {
                AlmacenPropiedades.ABADEJO.getNombre(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                AlmacenPropiedades.CABALLA.getNombre(),
                AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(),
                AlmacenPropiedades.DORADA.getNombre(),
                AlmacenPropiedades.PEJERREY.getNombre(),
                AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
                AlmacenPropiedades.ROBALO.getNombre(),
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
                AlmacenPropiedades.SARGO.getNombre(),
                AlmacenPropiedades.TILAPIA_NILO.getNombre()
        };

        GeneradorMenus.generarMenu(pecesDisponibles, 1);
        int opcionPez = SistemaEntrada.entradaOpcionNumerica(1, pecesDisponibles.length);
        String nombrePez = pecesDisponibles[opcionPez - 1];

        boolean sexo;
        int machos = 0, hembras = 0;

        for (Tanque tanque : piscifactoria.getTanques()) {
            if (tanque.getPeces().size() > 0 && tanque.getPeces().get(0).getNombre().equals(nombrePez)) {
                machos += tanque.pecesMacho();
                hembras += tanque.pecesHembra();
            }
        }

        if (hembras == 0 && machos == 0) {
            sexo = true;
        } else if (hembras == machos) {
            sexo = true;
        } else {
            sexo = hembras < machos;
        }

        boolean añadido = false;
        String nombrePezTanque = "";
        for (Tanque tanque : piscifactoria.getTanques()) {
            if (tanque.getPeces().size() != 0) {
                nombrePezTanque = tanque.getPeces().get(0).getNombre();
            } else {
                nombrePezTanque = null;
            }

            if (tanque.getPeces().size() < tanque.getCapacidadMaximaPeces()
                    && (nombrePezTanque == null || nombrePezTanque.equals(nombrePez))) {
                Pez nuevoPez = crearPez(nombrePez, sexo);
                tanque.getPeces().add(nuevoPez);
                añadido = true;
                System.out.println("Pez añadido al tanque existente.");
                break;
            }
        }

        if (!añadido) {
            for (Tanque tanque : piscifactoria.getTanques()) {
                if (tanque.getPeces().isEmpty()) {
                    Pez nuevoPez = crearPez(nombrePez, sexo);
                    tanque.getPeces().add(nuevoPez);
                    añadido = true;
                    System.out.println("Pez añadido a un tanque vacío.");
                    break;
                }
            }
        }

        if (!añadido) {
            System.out.println("No se pudo añadir el pez. No hay espacio disponible.");
        }

        for (Tanque tanque : piscifactoria.getTanques()) {
            tanque.showStatus();
        }
    }

    private static Pez crearPez(String nombrePez, boolean sexo) {
        switch (nombrePez) {
            case "Abadejo":
                return new Abadejo(sexo);
            case "Arenque Atlántico":
                return new ArenqueDelAtlantico(sexo);
            case "Caballa":
                return new Caballa(sexo);
            case "Carpin Tres Espinas":
                return new CarpinTresEspinas(sexo);
            case "Dorada":
                return new Dorada(sexo);
            case "Pejerrey":
                return new Pejerrey(sexo);
            case "Perca Europea":
                return new PercaEuropea(sexo);
            case "Robalo":
                return new Robalo(sexo);
            case "Salmón Atlántico":
                return new SalmonAtlantico(sexo);
            case "Salmón Chinook":
                return new SalmonChinook(sexo);
            case "Sargo":
                return new Sargo(sexo);
            case "Tilapia Nilo":
                return new TilapiaDelNilo(sexo);
            default:
                throw new IllegalArgumentException("Pez no reconocido: " + nombrePez);
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
    private void sell() {
        int piscifactoriaSeleccionada = selectPisc();

        Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

        piscifactoria.sellFish();

        System.out.println("Se han vendido todos los peces maduros y vivos de la piscifactoría "
                + piscifactoria.getNombre() + ".");

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
        Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

        int tanqueSeleccionado = selectTank();

        Tanque tanque = piscifactoria.getTanques().get(tanqueSeleccionado - 1);

        tanque.vaciarTanque();

        System.out.println("El tanque " + tanqueSeleccionado + " de la piscifactoría "
                + piscifactoria.getNombre() + " ha sido vaciado.");
    }

    public static void main(String[] args) {
        init();
        addFish();
    }
}
