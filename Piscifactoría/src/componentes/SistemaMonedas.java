package componentes;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

/**
 * Clase que representa a un sistema en el se dispone de una cantidad de monedas.
 */
@JsonAdapter(SistemaMonedas.AdaptadorJSON.class)
public class SistemaMonedas {
    
    /**
     * Monedas de las que se dispone.
     */
    private int monedas;

    /**
     * Constructor que inicia el sistema de monedas con una cantidad nula de monedas.
     */
    public SistemaMonedas(){
        this.monedas = 0;
    }

    /**
     * Constructor parametrizado.
     * @param monedas Cantidad de monedas de las que se dispone inicialmente en el sistema de monedas.
     */
    public SistemaMonedas(int monedas){
        super();
        this.monedas = monedas;
    }

    /**
     * 
     * @return Monedas de las que se dispone en el sistema de monedas.
     */
    public int getMonedas() {
        return monedas;
    }

    /**
     * Establece la cantidad de monedas de las que se dispone en el sistema de monedas.
     * @param monedas Número de monedas a establecer.
     */
    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    /**
     * Devuelve un String con información relevante del sistema de monedas.
     * @return String con información relevante del sistema de monedas.s
     */
    @Override
    public String toString(){
        return "Monedas: " + monedas;
    }

    /**
     * Clase que se encarga de adaptar la clase SistemaMonedas al formato JSON.
     */
    private class AdaptadorJSON implements JsonSerializer<SistemaMonedas>, JsonDeserializer<SistemaMonedas>{

        @Override
        public SistemaMonedas deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new SistemaMonedas(json.getAsInt());
        }

        @Override
        public JsonElement serialize(SistemaMonedas src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.getMonedas());
        }

        
    }
}
