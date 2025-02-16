package simulador.edificios;

import java.lang.reflect.Type;

import com.google.gson.Gson;
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
 * Interfaz bandera de edificios.
 */
@JsonAdapter(Edificio.AdaptadorJSONEdificio.class)
public interface Edificio {

    /**
     * Clase que se encarga de adaptar un edificio al formato JSON.
     */
    class AdaptadorJSONEdificio implements JsonDeserializer<Edificio>, JsonSerializer<Edificio>{

        /**
         * Se encarga de la serialización de un objeto edificio.
         */
        @Override
        public JsonElement serialize(Edificio src, Type typeOfSrc, JsonSerializationContext context) {
            if(src instanceof AlmacenCentral){
                return JsonParser.parseString(new Gson().toJson(src, AlmacenCentral.class));
            }
            else{
                if(src instanceof Fitoplancton){
                    return JsonParser.parseString(new Gson().toJson(src, Fitoplancton.class));
                }
                else{
                    return JsonParser.parseString(new Gson().toJson(src, Langostinos.class));
                }
            }
        }

        /**
         * Se encarga de la deserialización de un objeto edificio.
         */
        @Override
        public Edificio deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject tipo = json.getAsJsonObject();
            if(tipo.keySet().contains("almacen")){
                return new Gson().fromJson(json, AlmacenCentral.class);
            }
            else{
                if(tipo.keySet().contains("fitoplancton")){
                    return new Gson().fromJson(json, Fitoplancton.class);
                }
                else{
                    return new Gson().fromJson(json, Langostinos.class);
                }
            }
        }
        
    }
}
