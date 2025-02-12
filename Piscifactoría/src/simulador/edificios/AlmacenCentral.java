package simulador;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

/**
 * Clase que representa a un almacén central que almacena comida y la distribuye equitativamente entre las piscifactrías.
 */
@JsonAdapter(AlmacenCentral.AdaptadorJSONAlmacenCentral.class)
public class AlmacenCentral {

    /**
     * Capacidad máxima del almacén para cada tipo de comida.
     */
    private int capacidadComida;

    /**
     * Cantidad de comida animal disponible en el almacén central.
     */
    private int cantidadComidaAnimal;

    /**
     * Cantidad de comida vegetal disponible en el almacén central.
     */
    private int cantidadComidaVegetal;

    /**
     * Indica si el almecén central está disponible.
     */
    private boolean disponible;

    /**
     * Constructor de almacenes centrales.
     */
    public AlmacenCentral(){
        capacidadComida = 200;
        disponible = false;
    }

    /**
     * 
     * @return Capacidad máxima de cada tipo de comida del almacén central.
     */
    public int getCapacidadComida() {
        return capacidadComida;
    }

    /**
     * Permite establecer la capacidad máxima de cada tipo de comida del almacén central
     * @param capacidadComidaAnimal Capacidad máxima de cada tipo de comida a establecer.
     */
    public void setCapacidadComidaAnimal(int capacidadComida) {
        this.capacidadComida = capacidadComida;
    }

    /**
     * 
     * @return Cantidad de comida animal disponible en el almacén central.
     */
    public int getCantidadComidaAnimal() {
        return cantidadComidaAnimal;
    }

    /**
     * Permite establecer la cantidad de comida animal disponible en el almacén central.
     * @param cantidadComidaAnimal Cantidad de comida animal disponible en el almacén central a establecer.
     */
    public void setCantidadComidaAnimal(int cantidadComidaAnimal) {
        this.cantidadComidaAnimal = cantidadComidaAnimal;
    }

    /**
     * 
     * @return Cantidad de comida vegetal disponible en el almacén central.
     */
    public int getCantidadComidaVegetal() {
        return cantidadComidaVegetal;
    }

    /**
     * Permite establecer la cantidad de comida vegetal disponible en el almacén central.
     * @param cantidadComidaVegetal Cantidad de comida vegetal disponible a establecer.
     */
    public void setCantidadComidaVegetal(int cantidadComidaVegetal) {
        this.cantidadComidaVegetal = cantidadComidaVegetal;
    }

    /**
     * 
     * @return Si el almacen central está disponible.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Permite establecer la disponibilidad del almacén central.
     * @param disponible True para establecer que el almacén central está disponible.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Mejora el almacén centrar aumentado la capacidad máxima de cada tipo de comida en 50 unidades.
     */
    public void mejorar(){
        capacidadComida += 50;
    }

    /**
     * Devuelve un string con información del almacén central.
     * @return String con información del almacén central.
     */
    public String toString(){
        return "Capacidad máxima de comida por cada tipo: " + capacidadComida + "\nCantidad comida animal disponible: " + cantidadComidaAnimal
            + "\nCantidad comida vegetal disponible: " + cantidadComidaVegetal;
    }

    /**
     * Clase que se encarga de adaptar la clase AlmacenCentral al formato JSON.
     */
    private class AdaptadorJSONAlmacenCentral implements JsonSerializer<AlmacenCentral>, JsonDeserializer<AlmacenCentral>{

        /**
         * Se encarga de la deserialización de un objeto AlmacenCentral.
         */
        @Override
        public AlmacenCentral deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            AlmacenCentral almacenCentral = new AlmacenCentral();
            JsonObject almacen = json.getAsJsonObject().get("almacen").getAsJsonObject();
            JsonObject comida = almacen.get("comida").getAsJsonObject();
            almacenCentral.disponible = almacen.get("disponible").getAsBoolean();
            almacenCentral.capacidadComida = almacen.get("capacidad").getAsInt();
            almacenCentral.cantidadComidaAnimal = comida.get("animal").getAsInt();
            almacenCentral.cantidadComidaVegetal = comida.get("vegetal").getAsInt();
            return almacenCentral; 
        }

        /**
         * Se encarga de la serialización de un objeto AlmacenCentral.
         */
        @Override
        public JsonElement serialize(AlmacenCentral src, Type typeOfSrc, JsonSerializationContext context) {
            String json = "{ \"almacen\" : { \"disponible\" : \"" + src.disponible + "\" , \"capacidad\" : " + src.capacidadComida
            + " , \"comida\" : { \"vegetal\" : " + src.cantidadComidaVegetal + " , \"animal\" : " + src.cantidadComidaAnimal + " }}}";
            return JsonParser.parseString(json);
        }

        
    }
}
