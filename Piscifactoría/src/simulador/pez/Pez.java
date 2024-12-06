package simulador.pez;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonStreamParser;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.Gson;
import propiedades.AlmacenPropiedades;
import simulador.pez.carnivoro.Caballa;
import simulador.pez.carnivoro.Pejerrey;
import simulador.pez.carnivoro.PercaEuropea;
import simulador.pez.carnivoro.Robalo;
import simulador.pez.carnivoro.SalmonAtlantico;
import simulador.pez.carnivoro.SalmonChinook;
import simulador.pez.filtrador.ArenqueDelAtlantico;
import simulador.pez.filtrador.TilapiaDelNilo;
import simulador.pez.omnivoro.Abadejo;
import simulador.pez.omnivoro.CarpinTresEspinas;
import simulador.pez.omnivoro.Dorada;
import simulador.pez.omnivoro.Sargo;

/**
 * Clase abstracta que representa a un pez.
 */
@JsonAdapter(Pez.AdaptadorJSON.class)
public abstract class Pez {

    /**
     * Nombre del pez.
     */
    protected final String nombre;

    /**
     * Nombre científico del pez.
     */
    protected final String nombreCientifico;

    /**
     * Edad del pez en días.
     */
    protected int edad;

    /**
     * Sexo del pez si el valor es true es hembra si es false es macho.
     */
    protected final boolean sexo;

    /**
     * Indica si el pez es fértil y puede reproducirse.
     */
    protected boolean fertil;

    /**
     * Indica si el pez está vivo.
     */
    protected boolean vivo;

    /**
     * Indica si el pez ha sido alimentado.
     */
    protected boolean alimentado;

    /**
     * Número de días en los que el pez no se ha reproducido desde la última vez que era fértil.
     */
    protected int diasSinReproducirse;

    /**
     * 
     * @return Nombre del pez.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @return Nombre científico del pez.
     */
    public String getNombreCientifico() {
        return nombreCientifico;
    }

    /**
     * 
     * @return Edad del pez.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * 
     * @return Sexo del pez si es true es hembra y se es false es macho.
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * 
     * @return True si el pez es fértil.
     */
    public boolean isFertil() {
        return fertil;
    }

    /**
     * 
     * @return True si el pez está vivo.
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * 
     * @return True si el pez ha sido alimentado.
     */
    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * 
     * @return Número de días en los que el pez no se ha reproducido desde la última vez que era fértil.
     */
    public int getDiasSinReproducirse() {
        return diasSinReproducirse;
    }

    /**
     * Permite establecer la edad del pez.
     * @param edad Edad a establecer.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Permite establecer si el pez es fértil.
     * @param fertil True para establecer que el pez es fértil.
     */
    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }

    /**
     * Permite establecer si el pez está vivo.
     * @param vivo True para establecer que el pez está vivo.
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Permite establecer si el pez ha sido alimentado.
     * @param alimentado True para establecer que el pez ha sido alimentado.
     */
    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    /**
     * Permite establecer el número de días sin reproducirse desde la última vez que el pez era fértil.
     * @param diasSinReproducirse Número de días sin reproducirse desde la última vez que el pez era fértil.
     */
    public void setDiasSinReproducirse(int diasSinReproducirse) {
        this.diasSinReproducirse = diasSinReproducirse;
    }

    /**
     * Constructor de peces a partir del nombre, nombre científico y sexo del pez.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     * @param sexo Sexo del pez.
     */
    protected Pez(String nombre, String nombreCientifico, boolean sexo) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.sexo = sexo;
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
    }

    /**
     * Muestra el estado del pez.
     */
    public abstract void showStatus();

    /**
     * Hace que el pez crezca un día.
     */
    public abstract void grow();

    /**
     * Reinicia el pez estableciendo sus atributos a sus estados iniciales.
     */
    public void reset(){
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.diasSinReproducirse = 0;
    }

    /**
     * Indica la cantidad de comida que comerá el pez un día.
     * @return Cantidad de comida que come el pez.
     */
    public int comer(){
        return 1;
    }

    /**
     * 
     * @return True si el pez está maduro.
     */
    public abstract boolean isMaduro();

    /**
     * 
     * @return True si el pez está en la edad óptima para ser vendido.
     */
    public abstract boolean isEdadOptima();

    /**
     * 
     * @return Pez nuevo de sexo masculino.
     */
    public abstract Pez obtenerPezHijo();

    /**
     * 
     * @return Pez nuevo de sexo femenino.
     */
    public abstract Pez obtenerPezHija();

    /**
     * Devuelve un string con información relevante del pez.
     * @return String con información relevante del pez.
     */
    public String toString(){
        return "Nombre común: " + nombre + "\nNombre científico: " + nombreCientifico + "\nEdad: " + edad 
            + ((sexo) ? "\nSexo: Hembra" : "\nSexo: Macho") + ((fertil) ? "\nFértil: Sí" : "\nFértil: No" + ((vivo) ? "\nVivo: Sí" : "\nVivo: No")
            + ((alimentado) ? "\nAlimentado: Sí" : "\nAlimentado: No") + "\nDías sin reproducirse: " + diasSinReproducirse);
    }

    /**
     * Clase que se encarga de adaptar la serialización y deserialización de un pez a JSON.
     */
    private class AdaptadorJSON implements JsonSerializer<Pez>, JsonDeserializer<Pez>{

        /**
         * Se encarga de la deserialización de un objeto Pez.
         */
        @Override
        public Pez deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject pezDeserializado = json.getAsJsonObject();
            JsonObject extras = pezDeserializado.get("extra").getAsJsonObject();
            
            switch(extras.get("codigoPez").getAsInt()){
                case 0 -> {
                    Abadejo pez = new Abadejo(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }
                
                case 1 -> {
                    ArenqueDelAtlantico pez = new ArenqueDelAtlantico(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 2 -> {
                    Caballa pez = new Caballa(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 3 -> {
                    CarpinTresEspinas pez = new CarpinTresEspinas(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 4 ->{
                    Dorada pez = new Dorada(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 5 -> {
                    Pejerrey pez = new Pejerrey(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 6 -> {
                    PercaEuropea pez = new PercaEuropea(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 7 -> {
                    Robalo pez = new Robalo(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 8 -> {
                    SalmonAtlantico pez = new SalmonAtlantico(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 9 -> {
                    SalmonChinook pez = new SalmonChinook(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                case 10 -> {
                    Sargo pez = new Sargo(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }

                default -> {
                    TilapiaDelNilo pez = new TilapiaDelNilo(pezDeserializado.get("sexo").getAsBoolean());
                    pez.alimentado = pezDeserializado.get("alimentado").getAsBoolean();
                    pez.diasSinReproducirse = extras.get("diasSinReproducirse").getAsInt();
                    pez.edad = pezDeserializado.get("edad").getAsInt();
                    pez.vivo = pezDeserializado.get("vivo").getAsBoolean();
                    return (Pez) pez;
                }
            }
        }

        /**
         * Se encarga de la serilaización de un objeto Pez.
         */
        @Override
        public JsonElement serialize(Pez src, Type typeOfSrc, JsonSerializationContext context) {
            int codigoPez;

            if(src instanceof Abadejo){
                codigoPez = 0;
            }
            else if(src instanceof ArenqueDelAtlantico){
                codigoPez = 1;
            }
            else if(src instanceof Caballa){
                codigoPez = 2;
            }
            else if(src instanceof CarpinTresEspinas){
                codigoPez = 3;
            }
            else if(src instanceof Dorada){
                codigoPez = 4;
            }
            else if(src instanceof Pejerrey){
                codigoPez = 5;
            }
            else if(src instanceof PercaEuropea){
                codigoPez = 6;
            }
            else if(src instanceof Robalo){
                codigoPez = 7;
            }
            else if(src instanceof SalmonAtlantico){
                codigoPez = 8;
            }
            else if(src instanceof SalmonChinook){
                codigoPez = 9;
            }
            else if(src instanceof Sargo){
                codigoPez = 11;
            }
            else{
                codigoPez = 12;
            }
            String json = "{\"edad\": " + " \"" + src.edad + "\"" + ", \"sexo\": " + " \"" + src.sexo + "\"" + ", \"vivo\": " + " \"" + src.vivo + "\"" + ", \"maduro\": " + " \"" + (src.edad > AlmacenPropiedades.getPropByName(src.nombre).getMadurez()) + "\"" + 
            ", \"fertil\": " + " \"" + src.fertil + "\"" + ", \"ciclo\": " + " \"" + ((src.sexo && src.edad > AlmacenPropiedades.getPropByName(src.nombre).getMadurez()) ? (AlmacenPropiedades.getPropByName(src.nombre).getCiclo() - src.diasSinReproducirse) : 0) + "\"" + ", \"alimentado\": "
            + " \"" + src.alimentado + "\"" + ", \"extra\": {" +"\"codigoPez\" : \"" + codigoPez + "\"" +  " , \"diasSinReproducirse\": " + " \"" + src.diasSinReproducirse + "\"" + "}}";
            return JsonParser.parseString(json);
        }
        
    }
}
