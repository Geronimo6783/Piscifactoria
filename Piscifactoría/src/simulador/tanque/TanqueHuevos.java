package simulador.tanque;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import componentes.Transcripciones;
import simulador.pez.Pez;
import simulador.piscifactoria.Piscifactoria;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

/**
 * Clase que representa un tanque especializado en almacenar huevos dentro de
 * una piscifactoría.
 * Un tanque de huevos puede contener un máximo de 25 huevos, y se encarga de
 * procesar la transferencia
 * de los huevos a tanques de peces y de manejar la eclosión de los mismos.
 */
@JsonAdapter(TanqueHuevos.AdaptadorJSON.class)
public class TanqueHuevos extends Tanque {

    /**
     * Archivo de transcripciones de la partida donde se registran las acciones
     * relacionadas con el tanque de huevos.
     */
    public static Transcripciones archivoTranscripcionesPartida;

    /**
     * Capacidad máxima de huevos que puede almacenar el tanque de huevos.
     */
    private static final int CAPACIDAD_MAXIMA = 25;

    /**
     * Lista de nombres de los huevos almacenados en el tanque.
     */
    private List<String> huevos;

    /**
     * Piscifactoria a la que pertenece el tanque de huevos.
     */
    private Piscifactoria piscifactoria;

    /**
     * Lista de peces que están asociados al tanque de huevos.
     */
    private ArrayList<Pez> peces;

    /**
     * Número de huevos restantes por transferir.
     */
    private int huevosRestantes;

    /**
     * Constructor que inicializa un tanque de huevos con el número de tanque y la
     * piscifactoria asociada.
     * 
     * @param numeroTanque  Número del tanque.
     * @param piscifactoria Piscifactoria a la que pertenece el tanque.
     */
    public TanqueHuevos(int numeroTanque, Piscifactoria piscifactoria) {
        super(numeroTanque, CAPACIDAD_MAXIMA);
        this.piscifactoria = piscifactoria;
        this.huevos = new ArrayList<>();
        this.peces = new ArrayList<>();
    }

    /**
     * Constructor sin parámetros, crea un tanque de huevos con valores
     * predeterminados.
     */
    public TanqueHuevos() {
        super(0, 25);
        this.piscifactoria = piscifactoria;
        this.huevos = new ArrayList<>();
        this.huevosRestantes = 0;
        this.peces = new ArrayList<>();
    }

    /**
     * Agrega huevos sobrantes al tanque de huevos.
     * 
     * @param cantidadHuevos Cantidad de huevos a agregar.
     */
    public void agregarHuevosSobrantes(int cantidadHuevos) {
        this.huevosRestantes += cantidadHuevos;
    }

    /**
     * Obtiene la lista de peces asociados al tanque de huevos.
     * 
     * @return Lista de peces.
     */
    public ArrayList<Pez> getPeces() {
        return peces;
    }

    /**
     * Verifica si el tanque tiene espacio para más huevos.
     * 
     * @return true si tiene espacio, false si está lleno.
     */
    public boolean tieneEspacio() {
        return huevos.size() < CAPACIDAD_MAXIMA;
    }

    /**
     * Agrega un huevo al tanque de huevos, si hay espacio disponible.
     * 
     * @param nombrePez Nombre del pez asociado al huevo.
     */
    public void agregarHuevo(String nombrePez) {
        if (tieneEspacio()) {
            huevos.add(nombrePez);
        }
    }

    /**
     * Obtiene la lista de huevos almacenados en el tanque.
     * 
     * @return Lista de nombres de huevos.
     */
    public List<String> getHuevos() {
        return huevos;
    }

    /**
     * Transfiere los huevos sobrantes a los tanques disponibles, verificando si
     * tienen espacio y tipo de pez.
     * Cada vez que un huevo es transferido, el número de huevos restantes
     * disminuye.
     */
    public void transferirHuevos() {
        while (huevosRestantes > 0 && tieneEspacio()) {
            agregarHuevo(peces.get(0).getNombre());
            huevosRestantes--;
            System.out.println("Huevo transferido al TanqueHuevos.");
        }
    }

    /**
     * Procesa los huevos almacenados en el tanque y los transfiere a los tanques
     * correspondientes,
     * eclosionando los huevos y creando nuevos peces en el proceso.
     */
    public void procesarHuevos() {
        List<Tanque> tanques = piscifactoria.getTanques();
        Iterator<String> iteradorHuevos = huevos.iterator();
        int pecesTransferidos = 0;

        while (iteradorHuevos.hasNext()) {
            String nombrePez = iteradorHuevos.next();

            for (Tanque tanque : tanques) {
                if (!tanque.getPeces().isEmpty() && tanque.getPeces().size() < tanque.getCapacidadMaximaPeces()) {
                    if (tanque.getPeces().get(0).getNombre().equals(nombrePez)) {
                        Pez pezBase = tanque.getPeces().getFirst();

                        Pez nuevoPez = tanque.pecesMacho() >= tanque.pecesHembra() ? pezBase.obtenerPezHija()
                                : pezBase.obtenerPezHijo();

                        tanque.getPeces().add(nuevoPez);
                        iteradorHuevos.remove();
                        pecesTransferidos++;

                        System.out.println(
                                "Un huevo ha eclosionado y fue transferido al tanque " + tanque.getNumeroTanque());

                        break;
                    }
                }
            }
        }

        if (pecesTransferidos > 0) {
            archivoTranscripcionesPartida.registrarEnvioPecesTanqueHuevos(pecesTransferidos, piscifactoria.getNombre());
        }
    }

    /**
     * Clase que se encarga de adaptar la serialización y la deserialización de
     * objetos de la clase TanqueHuevos.
     */
    private static class AdaptadorJSON implements JsonSerializer<TanqueHuevos>, JsonDeserializer<TanqueHuevos> {

        /**
         * Método para deserializar un objeto de tipo TanqueHuevos desde su
         * representación en JSON.
         * 
         * @param json    Representación JSON del objeto.
         * @param typeOfT Tipo del objeto a deserializar.
         * @param context Contexto de deserialización.
         * @return Objeto deserializado de tipo TanqueHuevos.
         * @throws JsonParseException Si ocurre un error durante la deserialización.
         */
        @Override
        public TanqueHuevos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Gson gson = new Gson();
            TanqueHuevos tanque = new TanqueHuevos();
            tanque.huevos = new ArrayList<>();

            JsonElement huevosElement = json.getAsJsonObject().get("huevos");

            if (huevosElement != null && huevosElement.isJsonArray()) {
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                List<String> huevosList = gson.fromJson(huevosElement, listType);

                tanque.huevos.addAll(huevosList);
            }

            return tanque;
        }

        /**
         * Método para serializar un objeto de tipo TanqueHuevos a su representación en
         * JSON.
         * 
         * @param src       Objeto TanqueHuevos a serializar.
         * @param typeOfSrc Tipo del objeto a serializar.
         * @param context   Contexto de serialización.
         * @return Representación JSON del objeto.
         */
        @Override
        public JsonElement serialize(TanqueHuevos src, Type typeOfSrc, JsonSerializationContext context) {
            Gson gson = new Gson();
            String huevosJson = gson.toJson(src.getHuevos());

            String json = "{ \"huevos\" : " + huevosJson + " }";

            return JsonParser.parseString(json);
        }
    }
}