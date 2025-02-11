package simulador;

import java.io.IOException;
import java.lang.reflect.Type;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import componentes.GeneradorMenus;
import componentes.LecturaEscrituraJSON;
import componentes.Logs;
import componentes.SistemaEntrada;
import componentes.SistemaFicheros;
import componentes.SistemaMonedas;
import componentes.SistemaRecompensas;
import componentes.Transcripciones;
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
import simulador.sql.Conexion;
import simulador.sql.DAOPedidos;
import simulador.sql.GeneradorBD;
import simulador.sql.dto.DTOCliente;
import simulador.sql.dto.DTOPedido;
import simulador.sql.dto.DTOPedidoUsuarioPez;
import simulador.sql.dto.DTOPez;

public class Simulador {

    /**
     * Clase que se encarga de adaptar la clase Estadisticas al formato JSON.
     */
    private class AdaptadorJSONEstadisticas implements JsonSerializer<Estadisticas>, JsonDeserializer<Estadisticas> {

        /**
         * Se encarga de la deserialización de un objeto Estadisticas.
         */
        @Override
        public Estadisticas deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new Estadisticas(new String[] { AlmacenPropiedades.ABADEJO.getNombre(),
                    AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                    AlmacenPropiedades.CABALLA.getNombre(), AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(),
                    AlmacenPropiedades.DORADA.getNombre(),
                    AlmacenPropiedades.PEJERREY.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
                    AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                    AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                    AlmacenPropiedades.TILAPIA_NILO.getNombre() }, json.getAsString());
        }

        /**
         * Se encarga de la serialización de un objeto Estadisticas.
         */
        @Override
        public JsonElement serialize(Estadisticas src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.exportarDatos(new String[] { AlmacenPropiedades.ABADEJO.getNombre(),
                    AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                    AlmacenPropiedades.CABALLA.getNombre(), AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(),
                    AlmacenPropiedades.DORADA.getNombre(),
                    AlmacenPropiedades.PEJERREY.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
                    AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                    AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                    AlmacenPropiedades.TILAPIA_NILO.getNombre() }));
        }

    }

    /**
     * Peces implimentados en la simulación.
     */
    @SerializedName("implementados")
    public String[] pecesImplementados;

    /**
     * Nombre de la entidad, empresa o partida de la simulación.
     */
    @SerializedName("empresa")
    public String nombre;

    /**
     * Indica el número de días que han pasado en la simulación.
     */
    @SerializedName("dia")
    private int diasPasados;

    /**
     * Sistema de monedas de la simulación.
     */
    @SerializedName("monedas")
    public SistemaMonedas sistemaMonedas;

    /**
     * Estadísticas de los peces de la simualción.
     */
    @SerializedName("orca")
    @JsonAdapter(AdaptadorJSONEstadisticas.class)
    public Estadisticas estadisticas;

    /**
     * Almacén central de comida usado en la simulación.
     */
    @SerializedName("edificios")
    public AlmacenCentral almacenCentral = new AlmacenCentral();

    /**
     * Piscifactorías de la simulación.
     */
    public ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();

    /**
     * Archivo compartido entre todas las partidas donde se registran los errores.
     */
    public static final File archivoLogsGeneral = new File("logs/0_errors.log");

    /**
     * Archivo de log de la partida.
     */
    public static Logs archivoLogPartida;

    /**
     * Archivo de transcripciones de la partida.
     */
    public static Transcripciones archivoTranscripcionesPartida;

    /**
     * Archivo de guardado de la partida.
     */
    public static File archivoGuardadoPartida;

    /**
     * Directorio donde se guardan las rescompensas.
     */
    public static File directorioRecompensas = new File("rewards");

    /**
     * Simulador que se ejecuta en la partida.
     */
    public static Simulador simulador;

    /**
     * Método que inicializa la simulación pidiendo una serie de datos al usuario.
     */
    private static void init() {
        GeneradorBD.crearTablas();
        GeneradorBD.insertarClientes();
        GeneradorBD.insertarPeces();
        int opcion = 0;
        String nombrePiscifactoria = "";

        try {
            if (!SistemaFicheros.existeDirectorio("saves")) {
                SistemaFicheros.crearCarpeta("saves");
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear la carpeta 'saves'." + e.toString());
        }

        try {
            File[] archivosGuardado = new File("saves").listFiles();

            if (archivosGuardado.length != 0) {
                opcion = GeneradorMenus.generarMenuOperativo(new String[] { "¿Desea cargar una partida guardada?" },
                        new String[] { "Sí", "No" }, 1, 2);

                if (opcion == 1) {
                    String[] partidasGuardadas = new String[archivosGuardado.length];
                    String[] tokensNombreArchivoGuardado;

                    for (int i = 0; i < archivosGuardado.length; i++) {
                        tokensNombreArchivoGuardado = archivosGuardado[i].getName().split(".save");
                        partidasGuardadas[i] = tokensNombreArchivoGuardado[(tokensNombreArchivoGuardado.length != 1)
                                ? tokensNombreArchivoGuardado.length - 2
                                : tokensNombreArchivoGuardado.length - 1];
                    }

                    opcion = GeneradorMenus.generarMenuOperativo(new String[] { "Escoja la partida que desea cargar:" },
                            partidasGuardadas, 1, partidasGuardadas.length);

                    try {
                        simulador = LecturaEscrituraJSON.<Simulador>cargarJSON(archivosGuardado[opcion - 1]);
                        opcion = -1;
                    } catch (IOException e) {
                        Logs.escribirError("No se ha podido cargar la partida.");
                    }
                }
            }
        } catch (Exception e) {
            Logs.escribirError("Hubo un problema durante el proceso de carga de la partida. " + e.toString());
        }

        if (opcion == 0 || opcion == 2) {
            simulador = new Simulador();
            simulador.diasPasados = 0;
            System.out.print("Introduzca el nombre de la entidad, empresa o partida de la simulación: ");
            simulador.nombre = SistemaEntrada.entradaTexto();
            System.out.print("Introduzca el nombre de la primera piscifactoría: ");
            nombrePiscifactoria = SistemaEntrada.entradaTexto();
            simulador.sistemaMonedas = new SistemaMonedas(100);
            simulador.piscifactorias = new ArrayList<Piscifactoria>();
            simulador.piscifactorias.add(new PiscifactoriaRio(nombrePiscifactoria));
            simulador.pecesImplementados = new String[] { AlmacenPropiedades.ABADEJO.getNombre(),
                    AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.CABALLA.getNombre(),
                    AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(),
                    AlmacenPropiedades.PEJERREY.getNombre(),
                    AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(),
                    AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                    AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(),
                    AlmacenPropiedades.TILAPIA_NILO.getNombre() };
            simulador.estadisticas = new Estadisticas(simulador.pecesImplementados);

        }

        try {
            if (!SistemaFicheros.existeDirectorio("logs")) {
                SistemaFicheros.crearCarpeta("logs");
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear la carpeta 'logs'. " + e.toString());
        }

        try {
            if (!SistemaFicheros.existeArhivo("logs/0_errors.log")) {
                SistemaFicheros.crearArchivo("logs/0_errors.log");
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear el fichero '0_errors.log'. " + e.toString());
        }

        try {
            if (!SistemaFicheros.existeDirectorio("transcripciones")) {
                SistemaFicheros.crearCarpeta("transcripciones");
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear la carpeta 'transcripciones'." + e.toString());
        }

        try {
            if (!SistemaFicheros.existeArhivo("transcripciones/" + simulador.nombre + ".tr")) {
                SistemaFicheros.crearArchivo("transcripciones/" + simulador.nombre + ".tr");
                archivoTranscripcionesPartida = new Transcripciones(
                        new File("transcripciones/" + simulador.nombre + ".tr"));
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear el fichero de transcripciones de la partida. " + e.toString());
        }

        try {
            if (!SistemaFicheros.existeArhivo("logs/" + simulador.nombre + ".log")) {
                SistemaFicheros.crearArchivo("logs/" + simulador.nombre + ".log");
                archivoLogPartida = new Logs(new File("logs/" + simulador.nombre + ".log"));
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha crear el fichero de logs de la partida. " + e.toString());
        }

        try {
            if (!SistemaFicheros.existeDirectorio("rewards")) {
                SistemaFicheros.crearCarpeta("rewards");
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear la carpeta 'rewards'. " + e.toString());
        }
        try {
            if (SistemaFicheros.isDirectorioVacio("saves")) {
                SistemaFicheros.crearArchivo("saves/" + simulador.nombre + ".save");
                archivoGuardadoPartida = new File("saves/" + simulador.nombre + ".save");
            } else {
                try {
                    if (!SistemaFicheros.existeArhivo("saves/" + simulador.nombre + ".save")) {
                        try {
                            SistemaFicheros.crearArchivo("saves/" + simulador.nombre + ".save");
                            archivoGuardadoPartida = new File("saves/" + simulador.nombre + ".save");
                        } catch (IOException e) {
                            Logs.escribirError(
                                    "No se ha podido crear el archivo de guardado de la partida. " + e.toString());
                        }
                    } else {
                        archivoGuardadoPartida = new File("saves/" + simulador.nombre + ".save");
                    }
                } catch (IOException e) {
                    Logs.escribirError("No se ha podido comprobar la existencia del archivo de guardado de la partida. "
                            + e.toString());
                }
            }

            try {
                LecturaEscrituraJSON.<Simulador>guardarJSON(archivoGuardadoPartida, simulador);
            } catch (IOException e) {
                Logs.escribirError("No se ha podido guardar la partida. " + e.toString());
            }
        } catch (IOException e) {
            Logs.escribirError("No se ha podido crear o comprobar la existencia de 'saves'. " + e.toString());
        }

        archivoTranscripcionesPartida = new Transcripciones(new File("transcripciones/" + simulador.nombre + ".tr"));
        archivoLogPartida = new Logs(new File("logs/" + simulador.nombre + ".log"));

        if(opcion!=-1){
        archivoTranscripcionesPartida.iniciarTranscripciones(simulador.nombre, simulador.sistemaMonedas.getMonedas(),
                new String[] { AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(),
                        AlmacenPropiedades.DORADA.getNombre(),
                        AlmacenPropiedades.PEJERREY.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
                        AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                        AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre() },
                new String[] { AlmacenPropiedades.ABADEJO.getNombre(), AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                        AlmacenPropiedades.CABALLA.getNombre(), AlmacenPropiedades.DORADA.getNombre(),
                        AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
                        AlmacenPropiedades.SARGO.getNombre() },
                nombrePiscifactoria);

        archivoLogPartida.inicioLog(simulador.nombre, nombrePiscifactoria);
                }
        
    }

    /**
     * Imprime por pantalla el menú principal,
     */
    private static void menu() {
        String[] opcionesMenuPrincipal = { "Estado general", "Estado piscifactoría", "Estado tanques", "Informes",
                "Ictiopedia", "Pasar día",
                "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanque", "Mejorar",
                "Pasar varios días", "Reclamar recompensa", "Gestionar pedidos no finalizados", "Salir" };
        GeneradorMenus.generarMenu(opcionesMenuPrincipal, 1);
    }

    /**
     * Imprime un menú para seleccionar una piscifactoría de la simulación.
     */
    private void menuPisc() {
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
    public int selectPisc() {
        menuPisc();

        return SistemaEntrada.entradaOpcionNumerica(0, piscifactorias.size());
    }

    /**
     * Muestra el menú de tanques de una piscifactoría y permite al usuario
     * seleccionar uno.
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
    private void showGeneralStatus() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.showStatus();
        }
        System.out.println("Día actual: " + (diasPasados + 1));
        System.out.println("Monedas: " + sistemaMonedas.getMonedas());
        if (almacenCentral.isDisponible()) {
            int cantidadComidaAnimalAlmacen = almacenCentral.getCantidadComidaAnimal();
            int cantidadComidaVegetalAlmacen = almacenCentral.getCantidadComidaVegetal();
            int capacidadComidaAlmacen = almacenCentral.getCapacidadComida();
            System.out.println("Comida animal disponible en el almacén central: " + cantidadComidaAnimalAlmacen);
            System.out.println("Comida vegetal disponible en el almacén central: " + cantidadComidaVegetalAlmacen);
            System.out
                    .println("Capacidad máxima de comida animal en el almacén central: " + capacidadComidaAlmacen + " ("
                            + String.format("%.2f",
                                    (((float) cantidadComidaAnimalAlmacen) / (float) capacidadComidaAlmacen) * 100)
                            + "%)");
            System.out.println(
                    "Capacidad máxima de comida vegetal en el almacén central: " + capacidadComidaAlmacen + " ("
                            + String.format("%.2f",
                                    (((float) cantidadComidaVegetalAlmacen / (float) capacidadComidaAlmacen)) * 100)
                            + "%)");
        }
    }

    /**
     * Muestra por pantalla el estado de todos los tanque de una piscifactoría
     * seleccionada por el usuario.
     */
    private void showSpecificStatus() {
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
    private void showStats() {
        System.out.println(
                "======================================== Estadísticas ========================================");
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

        try {
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
        } catch (Exception e) {
            Logs.escribirError("Hubo un problema a la hora de mostrar la información de un pez. " + e.toString());
        }
    }

    /**
     * Avanza un día en todas las piscifactorías y muestra el número de peces
     * vendidos y las monedas ganadas con ello.
     */
    private void nextDay() {
        int dineroAntesDePasarDia;
        int pecesVendidoPiscifactoria = 0;
        int pecesVendidos = 0;
        int monedasGanadas = 0;
        int pecesRio = 0;
        int pecesMar = 0;

        for (Piscifactoria piscifactoria : piscifactorias) {
            dineroAntesDePasarDia = sistemaMonedas.getMonedas();
            pecesVendidoPiscifactoria += piscifactoria.nextDay();
            pecesVendidos += pecesVendidoPiscifactoria;
            System.out.println("Piscifactoría " + piscifactoria.getNombre() + ": "
                    + pecesVendidoPiscifactoria + " peces vendidos por un total de "
                    + (sistemaMonedas.getMonedas() - dineroAntesDePasarDia) + " monedas.");
            monedasGanadas += sistemaMonedas.getMonedas() - dineroAntesDePasarDia;
            if (piscifactoria instanceof PiscifactoriaMar) {
                pecesMar += piscifactoria.getPecesTotales();
            } else {
                pecesRio += piscifactoria.getPecesTotales();
            }
        }

        System.out.println(pecesVendidos + " peces vendidos por un total de " + monedasGanadas + " monedas.");

        if (almacenCentral.isDisponible()) {
            repartirComida();
        }

        archivoTranscripcionesPartida.registrarPasoDia(diasPasados + 1, pecesRio, pecesMar, monedasGanadas,
                pecesVendidos);
        archivoLogPartida.registrarPasoDia(diasPasados + 1);

        diasPasados++;
        showGeneralStatus();
        simulador.generarPedidosAutomaticamente(diasPasados);

        try {
            LecturaEscrituraJSON.<Simulador>guardarJSON(archivoGuardadoPartida, simulador);
        } catch (IOException e) {
            Logs.escribirError("Hubo un problema al guardar la partida. " + e.toString());
        }
    }

    /**
     * Gestiona la compra de comida para una piscifactoría o para el almacén
     * central.
     */
    private void addFood() {
        if (almacenCentral.isDisponible()) {
            int tipoComida = menuTipoComida();

            if (tipoComida != 0) {
                int cantidadAAgregar = menuCantidadComida();
                int capacidadComidaAlmacen = almacenCentral.getCapacidadComida();

                if (tipoComida == 1) {
                    int cantidadComidaAnimalAntes = almacenCentral.getCantidadComidaAnimal();
                    if (cantidadComidaAnimalAntes < capacidadComidaAlmacen) {
                        switch (cantidadAAgregar) {
                            case 1 -> {
                                cantidadAAgregar = 5;
                            }
                            case 2 -> {
                                cantidadAAgregar = 10;
                            }
                            case 3 -> {
                                cantidadAAgregar = 25;
                            }
                            case 4 -> {
                                cantidadAAgregar = (capacidadComidaAlmacen - cantidadComidaAnimalAntes);
                            }
                        }

                        almacenCentral.setCantidadComidaAnimal(cantidadComidaAnimalAntes + cantidadAAgregar);

                        int costoAnimal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoAnimal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoAnimal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida animal.");
                            System.out.println("Depósito de comida animal en el almacén central " +
                                    almacenCentral.getCantidadComidaAnimal() + "/" + almacenCentral.getCapacidadComida()
                                    +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaAnimalAntes + cantidadAAgregar) /
                                                    almacenCentral.getCapacidadComida() * 100)
                                    + "%.");

                            archivoTranscripcionesPartida.registrarCompraComida(cantidadAAgregar, " animal ",
                                    costoAnimal);
                            archivoLogPartida.registrarCompraComida(cantidadAAgregar, " animal ");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida animal, faltan "
                                    + (costoAnimal - sistemaMonedas.getMonedas()) + " monedas.");
                        }
                    } else {
                        System.out
                                .println("No se puede añadir comida animal, excede la capacidad del almacén central.");
                    }
                } else if (tipoComida == 2) {
                    int cantidadComidaVegetalAntes = almacenCentral.getCantidadComidaVegetal();
                    if (cantidadComidaVegetalAntes < capacidadComidaAlmacen) {
                        switch (cantidadAAgregar) {
                            case 1 -> {
                                cantidadAAgregar = 5;
                            }
                            case 2 -> {
                                cantidadAAgregar = 10;
                            }
                            case 3 -> {
                                cantidadAAgregar = 25;
                            }
                            case 4 -> {
                                cantidadAAgregar = (capacidadComidaAlmacen - cantidadComidaVegetalAntes);
                            }
                        }

                        almacenCentral.setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

                        int costoVegetal = calcularCosto(cantidadAAgregar);
                        if (sistemaMonedas.getMonedas() >= costoVegetal) {
                            sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() - costoVegetal);
                            System.out.println("Añadida " + cantidadAAgregar + " de comida vegetal.");
                            System.out.println("Depósito de comida vegetal en el almacén central " +
                                    almacenCentral.getCantidadComidaVegetal() + "/"
                                    + almacenCentral.getCapacidadComida() +
                                    " al " + String.format("%.2f",
                                            (float) (cantidadComidaVegetalAntes + cantidadAAgregar) /
                                                    almacenCentral.getCapacidadComida() * 100)
                                    + "%.");

                            archivoTranscripcionesPartida.registrarCompraComida(cantidadAAgregar, " vegetal ",
                                    costoVegetal);
                            archivoLogPartida.registrarCompraComida(cantidadAAgregar, " vegetal ");
                        } else {
                            System.out.println("No hay suficientes monedas para comprar la comida vegetal, faltan "
                                    + (costoVegetal - sistemaMonedas.getMonedas()) + " monedas.");
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

                        int cantidadMaximaAnimal = (piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                                - cantidadComidaAnimalAntes);

                        switch (cantidadAAgregar) {
                            case 1 -> {
                                cantidadAAgregar = 5;
                            }
                            case 2 -> {
                                cantidadAAgregar = 10;
                            }
                            case 3 -> {
                                cantidadAAgregar = 25;
                            }
                            case 4 -> {
                                cantidadAAgregar = cantidadMaximaAnimal;
                            }
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

                                archivoTranscripcionesPartida.registrarCompraComida(cantidadAAgregar, " animal ",
                                        costoAnimal, piscifactoria.getNombre());
                                archivoLogPartida.registrarCompraComida(cantidadAAgregar, " animal ",
                                        piscifactoria.getNombre());
                            } else {
                                System.out.println("No hay suficientes monedas para comprar la comida animal, faltan "
                                        + (costoAnimal - sistemaMonedas.getMonedas()) + " monedas.");
                            }
                        } else {
                            System.out.println(
                                    "No se puede añadir comida animal, la capacidad de la piscifactoría está llena.");
                        }
                    } else if (tipoComida == 2) {
                        int cantidadComidaVegetalAntes = piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();
                        int cantidadMaximaVegetal = piscifactoria.getAlmacenInicial().getCapacidadMaximaComida()
                                - cantidadComidaVegetalAntes;

                        switch (cantidadAAgregar) {
                            case 1 -> {
                                cantidadAAgregar = 5;
                            }
                            case 2 -> {
                                cantidadAAgregar = 10;
                            }
                            case 3 -> {
                                cantidadAAgregar = 25;
                            }
                            case 4 -> {
                                cantidadAAgregar = cantidadMaximaVegetal;
                            }
                        }

                        if (cantidadAAgregar > cantidadMaximaVegetal) {
                            cantidadAAgregar = cantidadMaximaVegetal;
                        }

                        if (cantidadAAgregar > 0) {
                            piscifactoria.getAlmacenInicial()
                                    .setCantidadComidaVegetal(cantidadComidaVegetalAntes + cantidadAAgregar);

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

                                archivoTranscripcionesPartida.registrarCompraComida(cantidadAAgregar, " vegetal ",
                                        costoVegetal, piscifactoria.getNombre());
                                archivoLogPartida.registrarCompraComida(cantidadAAgregar, " vegetal ",
                                        piscifactoria.getNombre());
                            } else {
                                System.out.println("No hay suficientes monedas para comprar la comida vegetal, faltan "
                                        + (costoVegetal - sistemaMonedas.getMonedas()) + " monedas.");
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

        int seleccion = GeneradorMenus.generarMenuOperativo(opciones, 0, 2);
        ;

        return seleccion;
    }

    /**
     * Muestra un menú para seleccionar la cantidad de comida a añadir.
     * 
     * @return Opción seleccionada.
     */
    private static int menuCantidadComida() {
        System.out.println("========== Cantidad comida ==========");
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
    private void addFish() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

            if (piscifactoria instanceof PiscifactoriaMar) {
                addFishMar(piscifactoria);
            } else {
                addFishRio(piscifactoria);
            }
        }
    }

    /**
     * Gestiona la lógica de añadir un pez a una piscifactoría de mar.
     * 
     * @param piscifactoria Piscifactoría donde se va a añadir el pez.
     */
    private void addFishMar(Piscifactoria piscifactoria) {
        String[] pecesDisponiblesMar = {
                "Cancelar",
                AlmacenPropiedades.ABADEJO.getNombre() + " - " + AlmacenPropiedades.ABADEJO.getCoste() + " monedas",
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre() + " - "
                        + AlmacenPropiedades.ARENQUE_ATLANTICO.getCoste() + " monedas",
                AlmacenPropiedades.CABALLA.getNombre() + " - " + AlmacenPropiedades.CABALLA.getCoste() + " monedas",
                AlmacenPropiedades.DORADA.getNombre() + " - " + AlmacenPropiedades.DORADA.getCoste() + " monedas",
                AlmacenPropiedades.ROBALO.getNombre() + " - " + AlmacenPropiedades.ROBALO.getCoste() + " monedas",
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre() + " - " + AlmacenPropiedades.SALMON_ATLANTICO.getCoste()
                        + " monedas",
                AlmacenPropiedades.SARGO.getNombre() + " - " + AlmacenPropiedades.SARGO.getCoste() + " monedas",
        };

        int monedas = sistemaMonedas.getMonedas();
        System.out.println("Cartera: " + monedas + " monedas.");
        int opcion = GeneradorMenus.generarMenuOperativo(pecesDisponiblesMar, 0, 7);

        if (opcion != 0) {
            String nombrePez = pecesDisponiblesMar[opcion].split(" - ")[0];

            int indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(nombrePez);

            if (indiceTanqueConEspacioParaPez != -1) {
                int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();

                if (monedas >= coste) {
                    Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);
                    sistemaMonedas.setMonedas(monedas - coste);

                    if (tanque.pecesMacho() >= tanque.pecesHembra()) {
                        tanque.getPeces().add(crearPezMar(opcion, true));
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, true, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, true, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    } else {
                        tanque.getPeces().add(crearPezMar(opcion, false));
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, false, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, false, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    }
                } else {
                    System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan "
                            + (coste - monedas) + " monedas.");
                }
            } else {
                int indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                if (indiceTanqueVacio != -1) {
                    int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();

                    if (monedas >= coste) {
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueVacio);
                        tanque.getPeces().add(crearPezMar(opcion, true));
                        sistemaMonedas.setMonedas(monedas - coste);
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, true, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, true, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    } else {
                        System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan "
                                + (coste - monedas) + " monedas.");
                    }
                } else {
                    System.out.println("No se puede añadir el pez a la piscifactoría.");
                }
            }
        }
    }

    /**
     * Gestiona la lógica de añadir un pez a una piscifactoría de río.
     * 
     * @param piscifactoria Piscifactoría donde se va a añadir el pez.
     */
    private void addFishRio(Piscifactoria piscifactoria) {
        String[] pecesDisponiblesRio = {
                "Cancelar",
                AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre() + " - "
                        + AlmacenPropiedades.CARPIN_TRES_ESPINAS.getCoste() + " monedas",
                AlmacenPropiedades.DORADA.getNombre() + " - " + AlmacenPropiedades.DORADA.getCoste() + " monedas",
                AlmacenPropiedades.PEJERREY.getNombre() + " - " + AlmacenPropiedades.PEJERREY.getCoste() + " monedas",
                AlmacenPropiedades.PERCA_EUROPEA.getNombre() + " - " + AlmacenPropiedades.PERCA_EUROPEA.getCoste()
                        + " monedas",
                AlmacenPropiedades.ROBALO.getNombre() + " - " + AlmacenPropiedades.ROBALO.getCoste() + " monedas",
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre() + " - " + AlmacenPropiedades.SALMON_ATLANTICO.getCoste()
                        + " monedas",
                AlmacenPropiedades.SALMON_CHINOOK.getNombre() + " - " + AlmacenPropiedades.SALMON_CHINOOK.getCoste()
                        + " monedas",
                AlmacenPropiedades.TILAPIA_NILO.getNombre() + " - " + AlmacenPropiedades.TILAPIA_NILO.getCoste()
                        + " monedas"
        };

        int monedas = sistemaMonedas.getMonedas();
        System.out.println("Cartera: " + monedas + " monedas.");
        int opcion = GeneradorMenus.generarMenuOperativo(pecesDisponiblesRio, 0, 8);

        if (opcion != 0) {
            String nombrePez = pecesDisponiblesRio[opcion].split(" - ")[0];

            int indiceTanqueConEspacioParaPez = piscifactoria.getIndiceTanqueConEspacioParaPez(nombrePez);

            if (indiceTanqueConEspacioParaPez != -1) {
                int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();
                monedas = sistemaMonedas.getMonedas();

                if (monedas >= coste) {
                    Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);
                    sistemaMonedas.setMonedas(monedas - coste);

                    if (tanque.pecesMacho() >= tanque.pecesHembra()) {
                        tanque.getPeces().add(crearPezRio(opcion, true));
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, true, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, true, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    } else {
                        tanque.getPeces().add(crearPezRio(opcion, false));
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, false, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, false, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    }
                } else {
                    System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan "
                            + (coste - monedas) + " monedas.");
                }
            } else {
                int indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                if (indiceTanqueVacio != -1) {
                    int coste = AlmacenPropiedades.getPropByName(nombrePez).getCoste();
                    monedas = sistemaMonedas.getMonedas();

                    if (monedas >= coste) {
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueVacio);
                        tanque.getPeces().add(crearPezRio(opcion, true));
                        sistemaMonedas.setMonedas(monedas - coste);
                        tanque.showCapacity(piscifactoria.getNombre());

                        archivoTranscripcionesPartida.registrarCompraPeces(nombrePez, true, coste,
                                tanque.getNumeroTanque(), piscifactoria.getNombre());
                        archivoLogPartida.registrarCompraPez(nombrePez, true, tanque.getNumeroTanque(),
                                piscifactoria.getNombre());
                    } else {
                        System.out.println("No se disponen de las suficientes monedas para comprar el pez, faltan "
                                + (coste - monedas) + " monedas.");
                    }
                } else {
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
    private static Pez crearPezMar(int pez, boolean sexo) throws IllegalArgumentException {
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
                return new CarpinTresEspinas(sexo);
            }
            case 2 -> {
                return new Dorada(sexo);
            }
            case 3 -> {
                return new Pejerrey(sexo);
            }
            case 4 -> {
                return new PercaEuropea(sexo);
            }
            case 5 -> {
                return new Robalo(sexo);
            }
            case 6 -> {
                return new SalmonAtlantico(sexo);
            }
            case 7 -> {
                return new SalmonChinook(sexo);
            }
            case 8 -> {
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
        PecesProps alimentacionPez = null;
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
            if (propiedad == PecesProps.CARNIVORO || propiedad == PecesProps.FILTRADOR
                    || propiedad == PecesProps.OMNIVORO) {
                alimentacionPez = propiedad;
            }
        }

        switch (alimentacionPez) {
            case CARNIVORO -> {
                System.out.println("\nTipo de comida que consume: Comida animal");
            }
            case FILTRADOR -> {
                System.out.println("\nTipo de comida que consume: Comida vegetal");
            }
            case OMNIVORO -> {
                System.out.println("\nTipo de comida que consume: Comida animal y vegetal");
            }
            default -> {
                Logs.escribirError("Tipo de alimentación del pez no válida");
            }
        }

        System.out.println("Tipo: " + datosDelPez.getTipo());

    }

    /**
     * Método que vende todos los peces adultos que estén vivos en una piscifactoría
     * seleccionada.
     */
    private void sell() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
            int pecesAntes = piscifactoria.getPecesTotales();

            piscifactoria.sellFish();

            System.out.println("Se han vendido todos los peces maduros y vivos de la piscifactoría "
                    + piscifactoria.getNombre() + ".");

            int pecesVendidos = pecesAntes - piscifactoria.getPecesTotales();

            String nombrePiscifactoria = piscifactoria.getNombre();
            archivoTranscripcionesPartida.registrarVentaPeces(pecesVendidos, nombrePiscifactoria,
                    sistemaMonedas.getMonedas());
            archivoLogPartida.registrarVentaPeces(pecesVendidos, nombrePiscifactoria);
        }

    }

    /**
     * Elimina los peces muertos de la piscifactoría escogida.
     */
    private void cleanTank() {

        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

            ArrayList<Tanque> tanques = piscifactoria.getTanques();

            for (Tanque tanque : tanques) {
                tanque.eliminarPecesMuertos();

                int numeroTanque = tanque.getNumeroTanque();
                String nombrePiscifactoria = piscifactoria.getNombre();

                archivoTranscripcionesPartida.registrarLimpiezaTanque(numeroTanque, nombrePiscifactoria);
                archivoLogPartida.registrarLimpiezaTanque(numeroTanque, nombrePiscifactoria);
            }

            System.out.println("Se han eliminado todos los peces muertos de los tanque de la piscifactoría "
                    + piscifactoria.getNombre() + ".");
        }
    }

    /**
     * Elimina todos los peces de un tanque, estén vivos o muertos.
     */
    private void emptyTank() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);

            int tanqueSeleccionado = selectTank(piscifactoria);

            if (tanqueSeleccionado != 0) {
                Tanque tanque = piscifactoria.getTanques().get(tanqueSeleccionado - 1);

                tanque.vaciarTanque();

                System.out.println("El tanque " + tanqueSeleccionado + " de la piscifactoría "
                        + piscifactoria.getNombre() + " ha sido vaciado.");

                int numeroTanque = tanque.getNumeroTanque();
                String piscifactoriaNombre = piscifactoria.getNombre();

                archivoTranscripcionesPartida.registrarVaciadoTanque(numeroTanque, piscifactoriaNombre);
                archivoLogPartida.registrarVaciadoTanque(numeroTanque, piscifactoriaNombre);
            }
        }
    }

    /**
     * Muestra un menú para hacer mejoras como comprar o mejorar edificios.
     */
    private void upgrade() {
        System.out.println("========== Mejorar ==========");
        String[] opciones = {
                "Cancelar",
                "Comprar edificios",
                "Mejorar edificios",
        };

        int opcion = GeneradorMenus.generarMenuOperativo(opciones, 0, 2);

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
    private void comprarEdificio() {
        System.out.println("========== Comprar edificio ==========");
        if (almacenCentral.isDisponible()) {

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
                        almacenCentral.setDisponible(true);
                        System.out.println("Almacén central comprado.");

                        archivoTranscripcionesPartida.registrarCompraAlmacenCentral();
                        archivoLogPartida.registrarCompraAlmacenCentral();
                    } else {
                        System.out.println("No tiene suficientes monedas para comprar el almacén central, faltan "
                                + (2000 - sistemaMonedas.getMonedas()) + " monedas.");
                    }
                    break;
            }
        }
    }

    /**
     * Permite al usuario comprar una nueva piscifactoría.
     */
    private void comprarPiscifactoria() {
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

                    archivoTranscripcionesPartida.resgistrarCompraPiscifactoria(0, nombrePiscifactoría,
                            costoPiscifactoría);
                    archivoLogPartida.registrarCompraPiscifactoria(0, nombrePiscifactoría);
                } else {
                    nuevaPiscifactoria = new PiscifactoriaMar(nombrePiscifactoría);

                    archivoTranscripcionesPartida.resgistrarCompraPiscifactoria(1, nombrePiscifactoría,
                            costoPiscifactoría);
                    archivoLogPartida.registrarCompraPiscifactoria(1, nombrePiscifactoría);
                }

                piscifactorias.add(nuevaPiscifactoria);
                System.out.println("Piscifactoría " + nombrePiscifactoría + " comprada.");
            } else {
                System.out.println("No tienes suficientes monedas para comprar esta piscifactoría, faltan "
                        + (costoPiscifactoría - sistemaMonedas.getMonedas()) + " monedas.");
            }
        }
    }

    /**
     * Muestra un menú para mejorar edificios existentes.
     */
    private void mejorarEdificio() {
        System.out.println("========== Mejorar edificio ==========");
        if (almacenCentral.isDisponible()) {
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
    private void mejorarPiscifactoria() {
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
                    mejorarAlmacenComidaPiscifactoria(piscifactoria);
                    break;
            }
        }
    }

    /**
     * Gestiona la lógica de mejora del almacén de comida de una piscifactoría.
     * 
     * @param piscifactoria Piscifactoría de la que se desea mejorar el almacén de
     *                      comida.
     */
    private void mejorarAlmacenComidaPiscifactoria(Piscifactoria piscifactoria) {
        int monedasDisponibles = sistemaMonedas.getMonedas();

        if (piscifactoria instanceof PiscifactoriaRio) {
            if (monedasDisponibles >= 50) {
                if (piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() < 250) {
                    sistemaMonedas.setMonedas(monedasDisponibles - 50);
                    piscifactoria.upgradeFood();

                    archivoTranscripcionesPartida.registrarMejoraPiscifactoria(piscifactoria.getNombre(),
                            piscifactoria.getAlmacenInicial().getCapacidadMaximaComida(), 50);
                } else {
                    System.out.println(
                            "No se puede aumentar más la capacidad de comida del almacén de comida de la piscifactoría.");
                }
            } else {
                System.out.println(
                        "No se dispone de las suficientes monedas para aumentar la capacidad de comida del almacén de comida de la piscifactoría, faltan "
                                + (50 - monedasDisponibles) + " monedas.");
            }
        } else {
            if (monedasDisponibles >= 200) {
                if (piscifactoria.getAlmacenInicial().getCapacidadMaximaComida() < 1000) {
                    sistemaMonedas.setMonedas(monedasDisponibles - 200);
                    piscifactoria.upgradeFood();

                    archivoTranscripcionesPartida.registrarMejoraPiscifactoria(piscifactoria.getNombre(),
                            piscifactoria.getAlmacenInicial().getCapacidadMaximaComida(), 200);
                } else {
                    System.out.println(
                            "No se puede aumentar más la capacidad de comida del almacén de comida de la piscifactoría.");
                }
            } else {
                System.out.println(
                        "No se dispone de las suficientes monedas para aumentar la capacidad de comida del almacén de comida de la piscifactoría, faltan "
                                + (200 - monedasDisponibles) + " monedas.");
            }
        }

        archivoLogPartida.registrarMejoraPiscifactoria(piscifactoria.getNombre());
    }

    /**
     * Permite al usuario comprar un tanque para la piscifactoría especificada.
     * 
     * @param piscifactoria La piscifactoría en la que se comprará el tanque.
     */
    private void comprarTanque(Piscifactoria piscifactoria) {
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

                archivoTranscripcionesPartida.registrarCompraTanque(nuevoTanque.getNumeroTanque(),
                        piscifactoriaRio.getNombre(), costoTanque);
            } else {
                System.out.println("No tienes suficientes monedas para comprar un tanque, faltan "
                        + (costoTanque - monedasDisponibles) + " monedas.");
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

                archivoTranscripcionesPartida.registrarCompraTanque(nuevoTanque.getNumeroTanque(),
                        piscifactoriaMar.getNombre(), costoTanque);
            } else {
                System.out.println("No tienes suficientes monedas para comprar un tanque, faltan "
                        + (costoTanque - monedasDisponibles) + " monedas.");
            }
        }

        archivoLogPartida.registrarCompraTanque(piscifactoria.getNombre());
    }

    /**
     * Aumenta la capacidad del almacén central.
     */
    private void aumentarCapacidadAlmacenCentral() {
        int costoAumento = 200;
        int monedasDisponibles = sistemaMonedas.getMonedas();

        if (monedasDisponibles >= costoAumento) {
            sistemaMonedas.setMonedas(monedasDisponibles - costoAumento);
            almacenCentral.mejorar();
            System.out.println("Capacidad del almacén central aumentada en 50 unidades.");

            archivoTranscripcionesPartida.registrarMejoraAlmacenCentral(almacenCentral.getCapacidadComida(),
                    costoAumento);
        } else {
            System.out.println("No tienes suficientes monedas para aumentar la capacidad del almacén central, faltan "
                    + (costoAumento - monedasDisponibles) + " monedas.");
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
    private int calcularCostoPiscifactoría(int tipo) {
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
    private int numeroPiscifactoriasRio() {
        int numeroPiscifactoriasRio = 0;

        for (Piscifactoria piscifactoria : piscifactorias) {
            if (piscifactoria instanceof PiscifactoriaRio) {
                numeroPiscifactoriasRio += 1;
            }
        }

        return numeroPiscifactoriasRio;
    }

    /**
     * 
     * @return Número de piscifactorías de mar de la simulación.
     */
    private int numeroPiscifactoriasMar() {
        int numeroPiscifactoriasMar = 0;

        for (Piscifactoria piscifactoria : piscifactorias) {
            if (piscifactoria instanceof PiscifactoriaMar) {
                numeroPiscifactoriasMar += 1;
            }
        }

        return numeroPiscifactoriasMar;
    }

    /**
     * Gestiona la lógica para mostrar el estado de un tanque de una piscifactoría
     * seleccionada.
     */
    private void mostrarEstadoTanque() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            showTankStatus(piscifactorias.get(piscifactoriaSeleccionada - 1));
        }
    }

    /**
     * Método para pasar varios días en la simulación.
     */
    private void pasarDias() {
        int pecesVendidos = 0;
        int dineroAntes;
        int monedasGanadas = 0;
        int dias = 0;
        int dineroDespues;
        int pecesRio = 0;
        int pecesMar = 0;

        System.out.println("Introduce el número de días que deseas avanzar: ");
        dias = SistemaEntrada.entradaOpcionNumericaEnteraPositiva();

        for (int i = 0; i < dias; i++) {
            for (Piscifactoria piscifactoria : piscifactorias) {
                dineroAntes = sistemaMonedas.getMonedas();
                pecesVendidos += piscifactoria.nextDay();
                dineroDespues = sistemaMonedas.getMonedas();

                monedasGanadas += (dineroDespues - dineroAntes);

                if (piscifactoria instanceof PiscifactoriaMar) {
                    pecesMar += piscifactoria.getPecesTotales();
                } else {
                    pecesRio += piscifactoria.getPecesTotales();
                }

            }

            if (almacenCentral.isDisponible()) {
                repartirComida();
            }

            archivoTranscripcionesPartida.registrarPasoDia(diasPasados + 1, pecesRio, pecesMar, monedasGanadas,
                    pecesVendidos);
            archivoLogPartida.registrarPasoDia(diasPasados + 1);
            diasPasados++;
            pecesRio = 0;
            pecesMar = 0;
        }

        System.out.println("En estos " + dias + " días se han vendido " + pecesVendidos
                + " peces y se han ganado " + monedasGanadas + " monedas.");
        showGeneralStatus();
        simulador.generarPedidosAutomaticamente(diasPasados);

        try {
            LecturaEscrituraJSON.<Simulador>guardarJSON(archivoGuardadoPartida, simulador);
        } catch (IOException e) {
            Logs.escribirError("Hubo un error al guardar la partida. " + e.toString());
        }
    }

    /**
     * Añade 4 peces aleatorios a una piscifactoría seleccionada por el usuario.
     */
    private void anadirPezAleatorio() {
        int piscifactoriaSeleccionada = selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaSeleccionada - 1);
            int pecesAnadidos = 0;
            Random rt = new Random();

            if (piscifactoria instanceof PiscifactoriaMar) {
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

                while (!piscifactoria.isTodosLosTanqueLlenos() && pecesAnadidos < 4) {
                    pezAleatorio = posiblesPeces.get(rt.nextInt(posiblesPeces.size()));
                    indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                    indiceTanqueConEspacioParaPez = piscifactoria
                            .getIndiceTanqueConEspacioParaPez(pecesDisponiblesMar[pezAleatorio]);

                    if (indiceTanqueConEspacioParaPez != -1) {
                        while (indiceTanqueConEspacioParaPez != -1 && pecesAnadidos < 4) {
                            Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);

                            if (tanque.pecesMacho() >= tanque.pecesHembra()) {
                                tanque.getPeces().add(crearPezMar(pezAleatorio + 1, true));
                                pecesAnadidos++;
                            } else {
                                tanque.getPeces().add(crearPezMar(pezAleatorio + 1, false));
                                pecesAnadidos++;
                            }
                        }
                    } else {
                        if (indiceTanqueVacio != -1) {
                            piscifactoria.getTanques().get(indiceTanqueVacio).getPeces()
                                    .add(crearPezMar(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        } else {
                            posiblesPeces.remove(posiblesPeces.indexOf(pezAleatorio));
                        }
                    }
                }
            } else {
                String[] pecesDisponiblesRio = {
                        AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(),
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
                posiblesPeces.add(7);

                while (!piscifactoria.isTodosLosTanqueLlenos() && pecesAnadidos < 4) {
                    pezAleatorio = posiblesPeces.get(rt.nextInt(posiblesPeces.size()));
                    indiceTanqueVacio = piscifactoria.getIndiceTanqueVacio();
                    indiceTanqueConEspacioParaPez = piscifactoria
                            .getIndiceTanqueConEspacioParaPez(pecesDisponiblesRio[pezAleatorio]);

                    if (indiceTanqueConEspacioParaPez != -1) {
                        Tanque tanque = piscifactoria.getTanques().get(indiceTanqueConEspacioParaPez);

                        if (tanque.pecesMacho() >= tanque.pecesHembra()) {
                            tanque.getPeces().add(crearPezRio(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        } else {
                            tanque.getPeces().add(crearPezRio(pezAleatorio + 1, false));
                            pecesAnadidos++;
                        }

                    } else {
                        if (indiceTanqueVacio != -1) {
                            piscifactoria.getTanques().get(indiceTanqueVacio).getPeces()
                                    .add(crearPezRio(pezAleatorio + 1, true));
                            pecesAnadidos++;
                        } else {
                            posiblesPeces.remove(posiblesPeces.indexOf(pezAleatorio));
                        }
                    }
                }
            }

            String nombrePiscifactoria = piscifactoria.getNombre();
            archivoTranscripcionesPartida.registrarAnadirPecesOculto(nombrePiscifactoria);
            archivoLogPartida.registrarAnadirPecesOculto(nombrePiscifactoria);
        }
    }

    /**
     * Reparte la comida del almacén central equitativamente entre las
     * piscafactorías.
     */
    public void repartirComida() {
        repartirComidaAnimal();
        repartirComidaVegetal();
    }

    /**
     * Indica si todas las piscifactorías que no están llenas están en la media en
     * cuanto a cantidad de comida animal.
     * 
     * @param mediaCantidadComidaAnimal Media de la cantidad de comida animal.
     * @return True si todas las piscifactorías que no están llenas están en la
     *         media en cuanto a cantidad de comida animal.
     */
    private boolean todasLasPiscifactoriasEnLaMediaComidaAnimal(int mediaCantidadComidaAnimal) {
        Piscifactoria.AlmacenComida almacenComida;
        int cantidadComidaAnimalPiscifactoria;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComida = piscifactoria.getAlmacenInicial();
            cantidadComidaAnimalPiscifactoria = almacenComida.getCantidadComidaAnimal();
            if ((cantidadComidaAnimalPiscifactoria != mediaCantidadComidaAnimal)
                    && (cantidadComidaAnimalPiscifactoria != almacenComida.getCapacidadMaximaComida())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Indica si todas las piscifactorías están llenas de comida animal.
     * 
     * @return True si todas las piscifactorías están llenas de comida animal.
     */
    private boolean todasLasPiscifactoriasLlenasDeComidaAnimal() {
        Piscifactoria.AlmacenComida almacenComida;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComida = piscifactoria.getAlmacenInicial();
            if (almacenComida.getCantidadComidaAnimal() < almacenComida.getCapacidadMaximaComida()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve la media de comida animal de las piscifactorías que no están llenas.
     * 
     * @return Media de comida animal de las piscifactorías que no están llenas.
     */
    private int mediaComidaAnimal() {
        int cantidadComidaAnimal = 0;
        int piscifactoriasNoLlenas = 0;
        Piscifactoria.AlmacenComida almacenComidaPiscifactoria;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();

            if (almacenComidaPiscifactoria.getCantidadComidaAnimal() != almacenComidaPiscifactoria
                    .getCapacidadMaximaComida()) {
                cantidadComidaAnimal += piscifactoria.getAlmacenInicial().getCantidadComidaAnimal();
                piscifactoriasNoLlenas += 1;
            }
        }

        return (cantidadComidaAnimal % piscifactoriasNoLlenas == 0) ? (cantidadComidaAnimal / piscifactoriasNoLlenas)
                : ((cantidadComidaAnimal / piscifactoriasNoLlenas) + 1);
    }

    /**
     * Gestiona la lógica de distribución equitativa de la comida animal del almacén
     * central a las piscifactorías.
     * 
     * @param mediaCantidadComidaAnimal Cantidad de comida animal media por
     *                                  piscifactoría.
     */
    private void repartirComidaAnimal() {
        ArrayList<Piscifactoria> piscifactoriaOrdenadoPorCantidadComidaAnimal = new ArrayList<>(piscifactorias);

        AlmacenComida almacenComidaPiscifactoria;
        int cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
        int mediaCantidadComidaAnimal;

        while (!todasLasPiscifactoriasLlenasDeComidaAnimal() && cantidadComidaAnimalAlmacenCentral != 0) {
            mediaCantidadComidaAnimal = mediaComidaAnimal();
            if (!todasLasPiscifactoriasEnLaMediaComidaAnimal(mediaCantidadComidaAnimal)) {
                Collections.sort(piscifactoriaOrdenadoPorCantidadComidaAnimal, new Comparator<Piscifactoria>() {

                    /**
                     * Compara la cantidad de comida animal de dos piscifactorías.
                     * 
                     * @param piscifactoria1 Primera piscifactoría a comparar.
                     * @param piscifactoria2 Segunda piscifactoría a comparar.
                     * @return 1 si la primera piscifactoría tiene mayor cantidad de comida animal,
                     *         0 si
                     *         ambas tienen la misma cantidad de comida animal y -1 si la cantidad
                     *         de comida animal es mayor en
                     *         la segunda.
                     */
                    @Override
                    public int compare(Piscifactoria piscifactoria1, Piscifactoria piscifactoria2) {
                        int cantidadComidaAnimalPiscifactoria1 = piscifactoria1.getAlmacenInicial()
                                .getCantidadComidaAnimal();
                        int cantidadComidaAnimalPiscifactoria2 = piscifactoria2.getAlmacenInicial()
                                .getCantidadComidaAnimal();

                        if (cantidadComidaAnimalPiscifactoria1 > cantidadComidaAnimalPiscifactoria2) {
                            return 1;
                        } else {
                            if (cantidadComidaAnimalPiscifactoria1 == cantidadComidaAnimalPiscifactoria2) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                });

                int cantidadComidaAnimalPiscifactoria;
                int cantidadDeComidaAAnadir;
                int capacidadComidaAlmacen;
                for (Piscifactoria piscifactoria : piscifactoriaOrdenadoPorCantidadComidaAnimal) {
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaAnimalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaAnimal();
                    capacidadComidaAlmacen = almacenComidaPiscifactoria.getCapacidadMaximaComida();

                    if (cantidadComidaAnimalPiscifactoria < mediaCantidadComidaAnimal) {
                        if (cantidadComidaAnimalAlmacenCentral != 0) {
                            cantidadDeComidaAAnadir = mediaCantidadComidaAnimal - cantidadComidaAnimalPiscifactoria;

                            if (cantidadDeComidaAAnadir + cantidadComidaAnimalPiscifactoria > capacidadComidaAlmacen) {
                                cantidadDeComidaAAnadir = capacidadComidaAlmacen - cantidadComidaAnimalPiscifactoria;
                            }

                            if (cantidadComidaAnimalAlmacenCentral >= cantidadDeComidaAAnadir) {
                                almacenCentral.setCantidadComidaAnimal(
                                        cantidadComidaAnimalAlmacenCentral - cantidadDeComidaAAnadir);
                                almacenComidaPiscifactoria.setCantidadComidaAnimal(mediaCantidadComidaAnimal);
                                cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
                            } else {
                                almacenCentral.setCantidadComidaAnimal(0);
                                almacenComidaPiscifactoria.setCantidadComidaAnimal(
                                        cantidadComidaAnimalAlmacenCentral + cantidadComidaAnimalPiscifactoria);
                                cantidadComidaAnimalAlmacenCentral = 0;
                            }
                        }
                    }
                }
            } else {
                int cantidadComidaAnimalPiscifactoria;

                for (Piscifactoria piscifactoria : piscifactorias) {
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaAnimalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaAnimal();

                    if (cantidadComidaAnimalAlmacenCentral != 0
                            && (cantidadComidaAnimalPiscifactoria < almacenComidaPiscifactoria
                                    .getCapacidadMaximaComida())) {
                        almacenComidaPiscifactoria.setCantidadComidaAnimal(cantidadComidaAnimalPiscifactoria + 1);
                        almacenCentral.setCantidadComidaAnimal(almacenCentral.getCantidadComidaAnimal() - 1);
                        cantidadComidaAnimalAlmacenCentral = almacenCentral.getCantidadComidaAnimal();
                    }
                }
            }
        }
    }

    /**
     * Indica si todas las piscifactorías que no están llenas están en la media en
     * cuanto a cantidad de comida vegetal.
     * 
     * @param mediaCantidadComidaVegetal Media de la cantidad de comida vegetal.
     * @return True si todas las piscifactorías que no están llenas están en la
     *         media en cuanto a cantidad de comida vegetal.
     */
    private boolean todasLasPiscifactoriasEnLaMediaComidaVegetal(int mediaCantidadComidaVegetal) {
        Piscifactoria.AlmacenComida almacenComida;
        int cantidadComidaVegetalPiscifactoria;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComida = piscifactoria.getAlmacenInicial();
            cantidadComidaVegetalPiscifactoria = almacenComida.getCantidadComidaVegetal();

            if ((cantidadComidaVegetalPiscifactoria != mediaCantidadComidaVegetal)
                    && (cantidadComidaVegetalPiscifactoria != almacenComida.getCapacidadMaximaComida())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Indica si todas las piscifactorías están llenas de comida vegetal.
     * 
     * @return True si todas las piscifactorías están llenas de comida vegetal.
     */
    private boolean todasLasPiscifactoriasLlenasDeComidaVegetal() {
        Piscifactoria.AlmacenComida almacenComida;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComida = piscifactoria.getAlmacenInicial();
            if (almacenComida.getCantidadComidaVegetal() < almacenComida.getCapacidadMaximaComida()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve la media de comida vegetal de las piscifactorías que no están
     * llenas.
     * 
     * @return Media de comida vegetal de las piscifactorías que no están llenas.
     */
    private int mediaComidaVegetal() {
        int cantidadComidaVegetal = 0;
        int piscifactoriasNoLlenas = 0;
        Piscifactoria.AlmacenComida almacenComidaPiscifactoria;

        for (Piscifactoria piscifactoria : piscifactorias) {
            almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();

            if (almacenComidaPiscifactoria.getCantidadComidaVegetal() != almacenComidaPiscifactoria
                    .getCapacidadMaximaComida()) {
                cantidadComidaVegetal += piscifactoria.getAlmacenInicial().getCantidadComidaVegetal();
                piscifactoriasNoLlenas += 1;
            }
        }

        return (cantidadComidaVegetal % piscifactoriasNoLlenas == 0) ? (cantidadComidaVegetal / piscifactoriasNoLlenas)
                : ((cantidadComidaVegetal / piscifactoriasNoLlenas) + 1);
    }

    /**
     * Gestiona la lógica de distribución equitativa de la comida vegetal del
     * almacén central a las piscifactorías.
     * 
     * @param mediaCantidadComidaVegetal Cantidad de comida vegetal media por
     *                                   piscifactoría.
     */
    private void repartirComidaVegetal() {
        ArrayList<Piscifactoria> piscifactoriaOrdenadoPorCantidadComidaVegetal = new ArrayList<>(piscifactorias);

        AlmacenComida almacenComidaPiscifactoria;
        int cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
        int mediaCantidadComidaVegetal;

        while (!todasLasPiscifactoriasLlenasDeComidaVegetal() && cantidadComidaVegetalAlmacenCentral != 0) {
            mediaCantidadComidaVegetal = mediaComidaVegetal();
            if (!todasLasPiscifactoriasEnLaMediaComidaVegetal(mediaComidaVegetal())) {
                Collections.sort(piscifactoriaOrdenadoPorCantidadComidaVegetal, new Comparator<Piscifactoria>() {

                    /**
                     * Compara la cantidad de comida vegetal de dos piscifactorías.
                     * 
                     * @param piscifactoria1 Primera piscifactoría a comparar.
                     * @param piscifactoria2 Segunda piscifactoría a comparar.
                     * @return 1 si la primera piscifactoría tiene mayor cantidad de comida vegetal,
                     *         0 si
                     *         ambas tienen la misma cantidad de comida vegetal y -1 si la cantidad
                     *         de comida vegetal es mayor en
                     *         la segunda.
                     */
                    @Override
                    public int compare(Piscifactoria piscifactoria1, Piscifactoria piscifactoria2) {
                        int cantidadComidaVegetalPiscifactoria1 = piscifactoria1.getAlmacenInicial()
                                .getCantidadComidaVegetal();
                        int cantidadComidaVegetalPiscifactoria2 = piscifactoria2.getAlmacenInicial()
                                .getCantidadComidaVegetal();

                        if (cantidadComidaVegetalPiscifactoria1 > cantidadComidaVegetalPiscifactoria2) {
                            return 1;
                        } else {
                            if (cantidadComidaVegetalPiscifactoria1 == cantidadComidaVegetalPiscifactoria2) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                });

                int cantidadComidaVegetalPiscifactoria;
                int cantidadDeComidaAAnadir;
                int capacidadComidaAlmacen;
                for (Piscifactoria piscifactoria : piscifactoriaOrdenadoPorCantidadComidaVegetal) {
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaVegetalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaVegetal();
                    capacidadComidaAlmacen = almacenComidaPiscifactoria.getCapacidadMaximaComida();

                    if (cantidadComidaVegetalPiscifactoria < mediaCantidadComidaVegetal) {
                        if (cantidadComidaVegetalAlmacenCentral != 0) {
                            cantidadDeComidaAAnadir = mediaCantidadComidaVegetal - cantidadComidaVegetalPiscifactoria;

                            if (cantidadDeComidaAAnadir + cantidadComidaVegetalPiscifactoria > capacidadComidaAlmacen) {
                                cantidadDeComidaAAnadir = capacidadComidaAlmacen - cantidadComidaVegetalPiscifactoria;
                            }

                            if (cantidadComidaVegetalAlmacenCentral >= cantidadDeComidaAAnadir) {
                                almacenCentral.setCantidadComidaVegetal(
                                        cantidadComidaVegetalAlmacenCentral - cantidadDeComidaAAnadir);
                                almacenComidaPiscifactoria.setCantidadComidaVegetal(mediaCantidadComidaVegetal);
                                cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
                            } else {
                                almacenCentral.setCantidadComidaVegetal(0);
                                almacenComidaPiscifactoria.setCantidadComidaVegetal(
                                        cantidadComidaVegetalAlmacenCentral + cantidadComidaVegetalPiscifactoria);
                                cantidadComidaVegetalAlmacenCentral = 0;
                            }
                        }
                    }
                }
            } else {
                int cantidadComidaVegetalPiscifactoria;
                for (Piscifactoria piscifactoria : piscifactorias) {
                    almacenComidaPiscifactoria = piscifactoria.getAlmacenInicial();
                    cantidadComidaVegetalPiscifactoria = almacenComidaPiscifactoria.getCantidadComidaVegetal();

                    if (cantidadComidaVegetalAlmacenCentral != 0
                            && (cantidadComidaVegetalPiscifactoria < almacenComidaPiscifactoria
                                    .getCapacidadMaximaComida())) {
                        almacenComidaPiscifactoria.setCantidadComidaVegetal(cantidadComidaVegetalPiscifactoria + 1);
                        almacenCentral.setCantidadComidaVegetal(almacenCentral.getCantidadComidaVegetal() - 1);
                        cantidadComidaVegetalAlmacenCentral = almacenCentral.getCantidadComidaVegetal();
                    }
                }
            }
        }
    }

    /**
     * Añade 1000 monedas al sistema de monedas de la simulación.
     */
    private void anadirMonedasOculto() {
        sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() + 1000);

        archivoTranscripcionesPartida.registrarAnadirMonedasOculta(sistemaMonedas.getMonedas());
        archivoLogPartida.registrarAnadirMonedasOculto();
    }

    /**
     * Devuelve un string con información relevante de la clase.
     * 
     * @return String con información relevante de la clase.
     */
    public String toString() {
        return "Nombre empresa, entidad o partida: " + nombre + "\nDías pasado en la simulación: " + diasPasados
                + "\nNúmero piscifactoría simulación: " + piscifactorias.size();
    }

    /**
     * Gestiona los pedidos no finalizados, permitiendo seleccionar un pedido,
     * asignar peces maduros de un tanque y actualizar la base de datos con los
     * peces enviados y el pago.
     */
    public void gestionarPedidosNoFinalizados() {
        DAOPedidos daoPedidos = new DAOPedidos(Conexion.getConexion());
        ArrayList<DTOPedidoUsuarioPez> pedidos = daoPedidos.obtenerPedidosNoFinalizados();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos pendientes.");
        } else {
            Collections.sort(pedidos, new Comparator<DTOPedidoUsuarioPez>() {

                /**
                 * Compara dos DTOPedidoUsuarioPez indica si el primero es menor o mayor al segundo.
                 * @param arg0 Primer argumento de la comparación.
                 * @param arg1 Segundo argumento de la comparación.
                 */
                @Override
                public int compare(DTOPedidoUsuarioPez arg0, DTOPedidoUsuarioPez arg1) {
                    if(arg0.getNumeroReferencia() < arg1.getNumeroReferencia()){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }
                
            });
            String[] opcionesPedidos = new String[pedidos.size() + 1];
            opcionesPedidos[0] = "Cancelar";
            for (int i = 0; i < pedidos.size(); i++) {
                DTOPedidoUsuarioPez pedido = pedidos.get(i);
                opcionesPedidos[i + 1] = "[" + pedido.getNumeroReferencia() + "] " + pedido.getNombreCliente() + ": "
                        + pedido.getNombrePez() + " " + pedido.getPecesEnviados() + "/" + pedido.getPecesSolicitados()
                        + " (" + pedido.getPorcentajeCompletado() + "%)";
            }

            int opcionPedido = GeneradorMenus.generarMenuOperativo(opcionesPedidos, 0, pedidos.size());
            if (opcionPedido != 0) {
                DTOPedidoUsuarioPez pedidoSeleccionado = pedidos.get(opcionPedido - 1);
                String nombrePezPedido = pedidoSeleccionado.getNombrePez();
                int cantidadRestante = pedidoSeleccionado.getPecesSolicitados() - pedidoSeleccionado.getPecesEnviados();

                int opcionPisc = selectPisc();
                if (opcionPisc != 0) {
                    Piscifactoria piscifactoria = piscifactorias.get(opcionPisc - 1);

                    int opcionTanque = selectTank(piscifactoria);
                    if (opcionTanque != 0) {
                        Tanque tanque = piscifactoria.getTanques().get(opcionTanque - 1);

                        int pecesDisponibles = 0;
                        for (Pez pez : tanque.getPeces()) {
                            if (pez.getNombre().equals(nombrePezPedido) && pez.isMaduro()) {
                                pecesDisponibles++;
                            }
                        }

                        if (pecesDisponibles > 0) {
                            int pecesAEnviar = Math.min(pecesDisponibles, cantidadRestante);
                            int precioPez = AlmacenPropiedades.getPropByName(nombrePezPedido).getMonedas();
                            int totalPago = pecesAEnviar * precioPez;

                            Iterator<Pez> iter = tanque.getPeces().iterator();
                            int enviados = 0;
                            while (iter.hasNext() && enviados < pecesAEnviar) {
                                Pez pez = iter.next();
                                if (pez.getNombre().equals(nombrePezPedido) && pez.isMaduro()) {
                                    iter.remove();
                                    enviados++;
                                }
                            }

                            int pecesEnvio = enviados + pedidoSeleccionado.getPecesEnviados();
                            daoPedidos.actualizarPecesEnviados(pedidoSeleccionado.getNumeroReferencia(), pecesEnvio);
                            Simulador.simulador.sistemaMonedas
                                    .setMonedas(Simulador.simulador.sistemaMonedas.getMonedas() + totalPago);

                            System.out.println("Se enviaron " + enviados + " peces de tipo " + nombrePezPedido +
                                    " para el pedido " + pedidoSeleccionado.getNumeroReferencia() +
                                    ". Se han recibido " + totalPago + " monedas.");

                            archivoTranscripcionesPartida.registrarEnvioPeces(pedidoSeleccionado.getNumeroReferencia(),
                                    nombrePezPedido, enviados);

                            if (pedidoSeleccionado.getPecesEnviados() + enviados >= pedidoSeleccionado
                                    .getPecesSolicitados()) {
                                archivoTranscripcionesPartida.registrarPedidoCompletado(opcionPedido, nombrePezPedido);
                                archivoLogPartida.registrarPedidoCompletado(opcionPedido, nombrePezPedido);

                                generarRecompensasPedidosCompletados();
                            }
                        } else {
                            System.out.println("No hay peces maduros suficientes en este tanque.");
                        }
                    }
                }
            }
        }
    }

    /*
     * Metodo auxiliar que genera las recompensas tras completar un pedido.
     */
    public void generarRecompensasPedidosCompletados() {
        Random r = new Random();
        int nRandomTipoRecomp = r.nextInt(10) + 1;
        String origen = "Pedido completado";
        if (nRandomTipoRecomp == 1|| nRandomTipoRecomp == 2|| nRandomTipoRecomp == 3|| nRandomTipoRecomp == 4 || nRandomTipoRecomp == 5) {
            int tipoComida = r.nextInt(3) + 1;
            int nivelComida = r.nextInt(10)+1;
            if (tipoComida == 1) {
                if (nivelComida == 1 || nivelComida == 2 || nivelComida == 3 || nivelComida == 4|| nivelComida == 5|| nivelComida == 6)  {
                    SistemaRecompensas.generarRecompensaAlgas(1, origen);
                } else if (nivelComida == 7|| nivelComida == 8|| nivelComida == 9) {
                    SistemaRecompensas.generarRecompensaAlgas(2, origen);
                } else if (nivelComida == 10) {
                    SistemaRecompensas.generarRecompensaAlgas(3, origen);
                }
            } else if (tipoComida == 2) {
                if (nivelComida == 1 || nivelComida == 2 || nivelComida == 3 || nivelComida == 4|| nivelComida == 5|| nivelComida == 6)  {
                    SistemaRecompensas.generarRecompensaPienso(1, origen);
                } else if (nivelComida == 7|| nivelComida == 8|| nivelComida == 9) {
                    SistemaRecompensas.generarRecompensaPienso(2, origen);
                } else if (nivelComida == 10) {
                    SistemaRecompensas.generarRecompensaPienso(3, origen);
                }
            } else {
                if (nivelComida == 1 || nivelComida == 2 || nivelComida == 3 || nivelComida == 4|| nivelComida == 5|| nivelComida == 6)  {
                    SistemaRecompensas.generarRecompensaComida(1, origen);
                } else if (nivelComida == 7|| nivelComida == 8|| nivelComida == 9) {
                    SistemaRecompensas.generarRecompensaComida(2, origen);
                } else if (nivelComida == 10) {
                    SistemaRecompensas.generarRecompensaComida(3, origen);
                }
            }
        } else if (nRandomTipoRecomp == 6 || nRandomTipoRecomp == 7 || nRandomTipoRecomp == 8 || nRandomTipoRecomp == 9) {
            int nivelMonedas = r.nextInt(10)+1;
            if (nivelMonedas == 1 || nivelMonedas == 2 || nivelMonedas == 3 || nivelMonedas == 4|| nivelMonedas == 5|| nivelMonedas == 6)  {
                SistemaRecompensas.generarRecompensaMonedas(1, origen);
            } else if (nivelMonedas == 7|| nivelMonedas == 8|| nivelMonedas == 9) {
                SistemaRecompensas.generarRecompensaMonedas(2, origen);
            } else if (nivelMonedas == 10) {
                SistemaRecompensas.generarRecompensaMonedas(3, origen);
            }
        } else {
            int tipoTanque = r.nextInt(10)+1;
            if (tipoTanque == 1 || tipoTanque == 2 || tipoTanque == 3 || tipoTanque == 4|| tipoTanque == 5|| tipoTanque == 6)  {
                SistemaRecompensas.generarRecompensaTanqueMar(origen);
            }else{
                SistemaRecompensas.generarRecompensaTanqueRio(origen);
            }
        }
    }

    /**
     * Genera un nuevo pedido automáticamente cada 10 días.
     *
     * @param diasPasados Número de días transcurridos en la simulación.
     *                    Se generará un pedido solo si es un múltiplo de 10.
     */
    public void generarPedidosAutomaticamente(int diasPasados) {
        if (diasPasados % 10 == 0) {
            DAOPedidos daoPedidos = new DAOPedidos(Conexion.getConexion());
            Random random = new Random();
    
            List<DTOCliente> clientes = daoPedidos.obtenerClientes();
            List<DTOPez> peces = daoPedidos.obtenerPeces();
    
            if (!clientes.isEmpty() && !peces.isEmpty()) {
                int idCliente = clientes.get(random.nextInt(clientes.size())).getId();
                int idPez = peces.get(random.nextInt(peces.size())).getId();
                int cantidadPeces = 10 + random.nextInt(41);
    
                DTOPedido nuevoPedido = new DTOPedido(idCliente, idPez, cantidadPeces, 0);
                daoPedidos.insertarPedido(nuevoPedido);
    
                
                List<DTOPedido> pedidos = daoPedidos.obtenerPedidos();
                if (!pedidos.isEmpty()) {
                    DTOPedido ultimoPedido = pedidos.get(pedidos.size() - 1);
                    int idPedido = ultimoPedido.getNumeroReferencia();
                    
                    
                    archivoTranscripcionesPartida.registrarGeneracionPedido(idPedido, obtenerNombrePez(idPez, peces));                   
                    archivoLogPartida.registrarGeneracionPedido(idPedido, obtenerNombrePez(idPez, peces));

                }
            } else {
                System.out.println("No hay clientes o peces disponibles para generar pedidos.");
            }
        }
    }

    /**
     * Obtiene el nombre de un pez a partir de su identificador.
     *
     * @param idPez el identificador del pez que se desea buscar.
     * @param peces la lista de objetos {@code DTOPez} donde se realizará la
     *              búsqueda.
     * @return el nombre del pez si se encuentra en la lista.
     */
    private String obtenerNombrePez(int idPez, List<DTOPez> peces) {
        for (DTOPez pez : peces) {
            if (pez.getId() == idPez) {
                return pez.getNombre();
            }
        }
        return "Desconocido";
    }

    /**
     * Método para borrar todos los pedidos de la base de datos.
     */
    public void borrarTodosLosPedidos() {
        DAOPedidos daoPedidos = new DAOPedidos(Conexion.getConexion());
        daoPedidos.borrarPedidos();
        System.out.println("Todos los pedidos han sido eliminados.");
    }

    /**
     * Método para mostrar los pedidos completados al 100%.
     */
    public void mostrarPedidosCompletados() {
        DAOPedidos daoPedidos = new DAOPedidos(Conexion.getConexion());
        ArrayList<DTOPedidoUsuarioPez> pedidos = daoPedidos.obtenerPedidosCompletados();
        boolean hayPedidosCompletados = false;

        System.out.println("===== Pedidos Completados =====");
        for (DTOPedidoUsuarioPez pedido : pedidos) {
            if (pedido.getPecesEnviados() == pedido.getPecesSolicitados()) {
                System.out.println("[" + pedido.getNumeroReferencia() + "] " + pedido.getNombreCliente() + ": "
                        + pedido.getNombrePez() + " " + pedido.getPecesEnviados() + "/" + pedido.getPecesSolicitados()
                        + " (" + pedido.getPorcentajeCompletado() + "%)");
                hayPedidosCompletados = true;
            }
        }

        if (!hayPedidosCompletados) {
            System.out.println("No hay pedidos completados.");
        }
    }

    /**
     * Método principal del programa que gestiona el uso del programa por parte del
     * usuario.
     * 
     * @param args Argumentos pasados por línea de comandos.
     */
    public static void main(String[] args) {
        try {
            init();

            int opcion = 0;
            int[] opcionesNumericas = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 , 95, 96, 97, 98, 99 };

            while (opcion != 16) {

                try {
                    System.out.println("Día actual: " + (simulador.diasPasados + 1));
                    menu();

                    opcion = SistemaEntrada.entradaOpcionNumerica(opcionesNumericas);

                    switch(opcion){
                        case 1 -> {simulador.showGeneralStatus();}
                        case 2 -> {simulador.showSpecificStatus();}
                        case 3 -> {simulador.mostrarEstadoTanque();}
                        case 4 -> {simulador.showStats();}
                        case 5 -> {Simulador.showIctio();}
                        case 6 -> {simulador.nextDay();}
                        case 7 -> {simulador.addFood();}
                        case 8 -> {simulador.addFish();}
                        case 9 -> {simulador.sell();}
                        case 10 -> {simulador.cleanTank();}
                        case 11 -> {simulador.emptyTank();}
                        case 12 -> {simulador.upgrade();}
                        case 13 -> {simulador.pasarDias();}
                        case 14 -> {SistemaRecompensas.reclamarRecompensa();}
                        case 15 -> {simulador.gestionarPedidosNoFinalizados();}
                        case 16 -> {System.out.println("Cerrando...");}
                        case 95 -> {simulador.mostrarPedidosCompletados();}
                        case 96 -> {simulador.borrarTodosLosPedidos();}
                        case 97 -> {SistemaRecompensas.reclamarRecompensa();}
                        case 98 -> {simulador.anadirPezAleatorio();}
                        case 99 -> {simulador.anadirMonedasOculto();}
                    }
                } catch (Exception e) {
                    Logs.escribirError("Hubo un error en el menú principal. " + e.toString());
                }
            }

            SistemaEntrada.close();

            archivoLogPartida.registrarSalidaPartida();

            try {
                LecturaEscrituraJSON.<Simulador>guardarJSON(archivoGuardadoPartida, simulador);
            } catch (IOException e) {
                Logs.escribirError("Hubo un error al guardar la partida. " + e.toString());
            }

            Conexion.close();
        } catch (Exception e) {
            Logs.escribirError("Hubo un error durante la ejecución del programa. " + e.toString());
            System.err.println(e);
            e.printStackTrace();
        }
    }
}