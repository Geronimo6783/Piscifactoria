package simulador.tanque;

import java.lang.reflect.Type;
import java.util.ArrayList;

import componentes.Transcripciones;
import propiedades.AlmacenPropiedades;

import simulador.pez.Pez;
import simulador.pez.carnivoro.Carnivoro;
import simulador.pez.filtrador.Filtrador;

import simulador.piscifactoria.Piscifactoria;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

/**
 * Representa un tanque de cría en una piscifactoría, donde se gestionan peces
 * en su fase de cría.
 */
@JsonAdapter(TanqueCria.AdaptadorJSON.class)
public class TanqueCria extends Tanque {

    /**
     * Piscifactoría a la que pertenece este tanque de cría.
     */
    private Piscifactoria piscifactoria;

    /**
     * Archivo de transcripciones para registrar los eventos de la partida.
     */
    public static Transcripciones archivoTranscripcionesPartida;

    /**
     * Lista de peces del tanque.
     */
    private ArrayList<Pez> peces;

    /**
     * Crea un tanque de cría con el número de tanque, la capacidad máxima de peces
     * y la piscifactoría a la que pertenece.
     * 
     * @param numeroTanque         El número del tanque.
     * @param capacidadMaximaPeces La capacidad máxima de peces en el tanque.
     * @param piscifactoria        La piscifactoría a la que pertenece este tanque.
     */
    public TanqueCria(int numeroTanque, int capacidadMaximaPeces, Piscifactoria piscifactoria) {
        super(numeroTanque, 2);
        this.piscifactoria = piscifactoria;
        peces = new ArrayList<>();
    }

    /**
     * Constructor por defecto de TanqueCria.
     */
    public TanqueCria() {
        super(0, 2);
        this.piscifactoria = null;
        this.peces = new ArrayList<>();
    }

    /**
     * Obtiene la piscifactoría a la que pertenece este tanque.
     * 
     * @return La piscifactoría asociada al tanque.
     */
    public Piscifactoria getPiscifactoria() {
        return piscifactoria;
    }

    /**
     * Alimenta a los peces en el tanque de cría, utilizando los recursos del
     * almacen de comida.
     * Asegura que los peces sean alimentados según sus necesidades.
     * 
     * @param almacenComida El almacén de comida para los peces.
     */
    @Override
    public void alimentar(Piscifactoria.AlmacenComida almacenComida) {
        if (peces.size() != 2) {
            return; // Asegurar que solo hay dos peces en el tanque
        }

        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();
        int comidaNecesariaAnimal = 0;
        int comidaNecesariaVegetal = 0;

        for (Pez pez : peces) {
            if (!pez.isAlimentado()) {
                if (pez instanceof Carnivoro) {
                    comidaNecesariaAnimal += 2;
                } else if (pez instanceof Filtrador) {
                    comidaNecesariaVegetal += 2;
                } else {
                    comidaNecesariaAnimal += 1;
                    comidaNecesariaVegetal += 1;
                }
            }
        }

        boolean hayComidaSuficiente = (comidaAnimal >= comidaNecesariaAnimal)
                && (comidaVegetal >= comidaNecesariaVegetal);

        if (hayComidaSuficiente) {
            comidaAnimal -= comidaNecesariaAnimal;
            comidaVegetal -= comidaNecesariaVegetal;
            for (Pez pez : peces) {
                if (!pez.isAlimentado()) {
                    pez.setAlimentado(true);
                }
            }
        }

        almacenComida.setCantidadComidaAnimal(comidaAnimal);
        almacenComida.setCantidadComidaVegetal(comidaVegetal);
    }

    /**
     * Avanza la edad de los peces en el tanque, alimentándolos si es necesario,
     * y luego permite la reproducción si los peces están listos.
     * 
     * @param almacenComida El almacén de comida para los peces.
     */
    public void avanzarEdad(Piscifactoria.AlmacenComida almacenComida) {
        alimentar(almacenComida);

        for (Pez pez : peces) {
            if (pez.isAlimentado()) {
                pez.grow();
                pez.setAlimentado(false);
            }
            pez.setVivo(true);
        }
        reproducir();
    }

    /**
     * Realiza la reproducción de los peces en el tanque de cría si los dos peces
     * están alimentados y fértiles.
     * La cría se realiza en un tanque disponible y con espacio suficiente.
     */
    private void reproducir() {

        if (peces.size() != 2) {
            return;
        }

        Pez pez1 = peces.get(0);
        Pez pez2 = peces.get(1);

        if (!(pez1.isAlimentado() && pez2.isAlimentado() && pez1.isFertil() && pez2.isFertil())) {
            return;
        }

        String raza = pez1.getNombre();

        Tanque tanqueDestino = buscarTanquePorRaza(piscifactoria, raza);
        if (tanqueDestino == null) {
            System.out.println("No se encontró un tanque disponible para la raza: " + raza);
            return;
        }
        int espacioTanqueDestino = tanqueDestino.getCapacidadMaximaPeces() - tanqueDestino.getPeces().size();
        if (espacioTanqueDestino < 1) {
            return;
        }

        int huevos = AlmacenPropiedades.getPropByName(pez1.getNombre()).getHuevos();
        int totalHuevos = 2 * huevos;
        int huevosAProducir = Math.min(totalHuevos, espacioTanqueDestino);

        for (int i = 0; i < huevosAProducir; i++) {

            int machos = tanqueDestino.pecesMacho();
            int hembras = tanqueDestino.pecesHembra();

            Pez nuevaCria = (machos >= hembras) ? pez1.obtenerPezHija() : pez1.obtenerPezHijo();

            tanqueDestino.getPeces().add(nuevaCria);

        }

        archivoTranscripcionesPartida.registrarEnvioPecesTanqueCria(huevosAProducir, piscifactoria.getNombre());

        pez1.setFertil(false);
        pez2.setFertil(false);
        pez1.setDiasSinReproducirse(0);
        pez2.setDiasSinReproducirse(0);
    }

    /**
     * Busca un tanque en la piscifactoría que contenga peces de la misma raza que
     * los del tanque de cría.
     * 
     * @param piscifactoria La piscifactoría donde se realiza la búsqueda.
     * @param raza          La raza de los peces que se está buscando.
     * @return El tanque que contiene peces de la raza buscada, o null si no se
     *         encuentra.
     */
    private Tanque buscarTanquePorRaza(Piscifactoria piscifactoria, String raza) {
        for (Tanque tanque : piscifactoria.getTanques()) {
            if (!tanque.getPeces().isEmpty() && tanque.getPeces().get(0).getNombre().equals(raza)) {
                return tanque;
            }
        }
        return null;
    }

    /**
     * Clase que se encarga de adaptar la serialización y la deserialización de
     * objetos de la clase TanqueCria.
     */
    private class AdaptadorJSON implements JsonDeserializer<TanqueCria>, JsonSerializer<TanqueCria> {

        /**
         * Deserializa un objeto JSON a un objeto de tipo TanqueCria.
         * 
         * @param json    El elemento JSON que contiene los datos.
         * @param typeOfT El tipo de clase que se deserializa.
         * @param context El contexto de deserialización.
         * @return El objeto TanqueCria deserializado.
         * @throws JsonParseException Si ocurre un error durante la deserialización.
         */
        @Override
        public TanqueCria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            TanqueCria tanque = new TanqueCria();
            tanque.peces = new ArrayList<Pez>();

            for (Pez pez : new Gson().fromJson(json.getAsJsonObject().get("peces"), Pez[].class)) {
                tanque.peces.add(pez);
            }

            return tanque;
        }

        /**
         * Serializa un objeto de tipo TanqueCria a formato JSON.
         * 
         * @param src       El objeto de tipo TanqueCria a serializar.
         * @param typeOfSrc El tipo del objeto.
         * @param context   El contexto de serialización.
         * @return El elemento JSON que representa el objeto TanqueCria.
         */
        @Override
        public JsonElement serialize(TanqueCria src, Type typeOfSrc, JsonSerializationContext context) {
            Gson gson = new Gson();
            StringBuilder pecesJson = new StringBuilder();

            if (!src.peces.isEmpty()) {
                pecesJson.append("[");

                for (Pez pez : src.peces) {
                    pecesJson.append(gson.toJson(pez, Pez.class)).append(",");
                }

                pecesJson.setLength(pecesJson.length() - 1);
                pecesJson.append("]");
            } else {
                pecesJson.append("[]");
            }

            String json = "{ \"peces\" : " + pecesJson.toString() + " }";
            return JsonParser.parseString(json);
        }
    }
}