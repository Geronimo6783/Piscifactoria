package simulador.piscifactoria;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

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

import simulador.Tanque;

/**
 * Clase que representa a una piscifactoría con múltiples tanques.
 */
@JsonAdapter(Piscifactoria.AdaptadorJSON.class)
public abstract class Piscifactoria {

    /**
     * Clase que representa al almacén de comida de una piscifactoría.
     */
    public static class AlmacenComida{

        /**
         * Capacidad máxima de comida de cada tipo del almacén.
         */
        private int capacidadMaximaComida;

        /**
         * Cantidad de comida animal que hay en el almacén.
         */
        private int cantidadComidaAnimal;

        /**
         * Cantidad de comida vegetal que hay en el almacén.
         */
        private int cantidadComidaVegetal;

        /**
         * Constructor de almacenes de comida.
         * @param capacidadMaximaComida Capacidad máxima de comida de cada tipo.
         * @param cantidadComidaAnimal Cantidad de comida animal del almacén.
         * @param cantidadComidaVegetal Cantidad de comida animal del alamacén.
         */
        public AlmacenComida(int capacidadMaximaComida, int cantidadComidaAnimal, int cantidadComidaVegetal) {
            this.capacidadMaximaComida = capacidadMaximaComida;
            this.cantidadComidaAnimal = cantidadComidaAnimal;
            this.cantidadComidaVegetal = cantidadComidaVegetal;
        }

        /**
         * 
         * @return Capacidad máxima de comida de cada tipo del almacén.
         */
        public int getCapacidadMaximaComida() {
            return capacidadMaximaComida;
        }

        /**
         * Permite establecer la capacidad máxima de comida de cada tipo del almacén.
         * @param capacidadMaximaComida Capacidad máxima de comida de cada tipo del almacén.
         */
        public void setCapacidadMaximaComida(int capacidadMaximaComida) {
            this.capacidadMaximaComida = capacidadMaximaComida;
        }

        /**
         * 
         * @return Cantidad de comida animal del almacén.
         */
        public int getCantidadComidaAnimal() {
            return cantidadComidaAnimal;
        }

        /**
         * Permite establecer la cantidad de comida animal del almacén.
         * @param cantidadComidaAnimal Cantidad de comida animal del almacén a establecer.
         */
        public void setCantidadComidaAnimal(int cantidadComidaAnimal) {
            this.cantidadComidaAnimal = cantidadComidaAnimal;
        }

        /**
         * 
         * @return Cantidad de comida vegetal del almacén.
         */
        public int getCantidadComidaVegetal() {
            return cantidadComidaVegetal;
        }

        /**
         * Permite establecer la cantidad de comida vegetal del almacén.
         * @param cantidadComidaVegetal Cantidad de comida vegetal del almacén a establecer.
         */
        public void setCantidadComidaVegetal(int cantidadComidaVegetal) {
            this.cantidadComidaVegetal = cantidadComidaVegetal;
        }

        /**
         * Mejora el almacen aumentando la capacidad máxima de comida de cada tipo.
         * @param capacidadAAumentar Unidades a aumentar la capacidad máxima de comida de cada tipo.
         */
        public void mejorar(int capacidadAAumentar){
            capacidadMaximaComida += capacidadAAumentar;
        }

        /**
         * Devuelve un string con información relevante del almacén de comida.
         * @return String con información relevante del alamcén de comida.
         */
        public String toString(){
            return "Capacidad máxima de comida por cada tipo: " + capacidadMaximaComida + "\nCantidad comida animal disponible: " + cantidadComidaAnimal
                    + "\nCantidad comida vegetal disponible: " +cantidadComidaVegetal;
        }

    }

    /**
     * Nombre de la piscifactoría.
     */
    protected String nombre = "";

    /**
     * Indica si la piscifactoría es de mar o de río. Si es 0 es de río y si es 1 es de mar.
     */
    protected int tipo;

    public int getTipo() {
        return tipo;
    }

    /**
     * Tanques de los que dispone la piscifactoría.
     */
    protected ArrayList<Tanque> tanques;
    
    /**
     * Tanque con el que empieza obligatoriamente la piscifactoría.
     */
    protected Tanque tanqueInicial;

    /**
     * Almacén de comida de la piscifactoría.
     */
    protected AlmacenComida almacenInicial;

    /**
     * Constructor de Ppiscifactorías.
     * @param nombre Nombre de la piscifactoría.
     */
    protected Piscifactoria(String nombre, int tipo) {
        tanques = new ArrayList<>();
        this.nombre = nombre;
        this.tipo = tipo;
    }

    /**
     * Devuelve los tanques de la piscifactoria.
     * @return Tanques de la piscifactoría.
     */
    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    /**
     * Establece el ArrayList de tanques de la piscifactoria.
     * @param tanques ArrayList de tanques a establecer.
     */
    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    /**
     * Devuelve el tanque inicial de la piscifactoria.
     * @return Tanque inicial de la piscifactoría.
     */
    public Tanque getTanqueInicial() {
        return tanqueInicial;
    }

    /**
     * Establece el tanque inicial de la piscifactoria.
     * @param tanqueInicial Tanque inicial de la piscifactoría a establecer.
     */
    public void setTanqueInicial(Tanque tanqueInicial) {
        this.tanqueInicial = tanqueInicial;
    }

    /**
     * Devuelve el nombre de la piscifactoria.
     * @return Nombre de la piscifactoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el almacén inicial de la piscifactoría.
     * @return Almacén inicial de la piscifactoría.
     */
    public AlmacenComida getAlmacenInicial() {
        return almacenInicial;
    }

    /**
     * Establece el almacén inicial de la piscifactoría
     * @param almacenInicial Almacén inicial de la piscifactoría a establecer.
     */
    public void setAlmacenInicial(AlmacenComida almacenInicial) {
        this.almacenInicial = almacenInicial;
    }

    /**
     * Establece el nombre de la piscifactoria
     * @param nombre Nombre de la piscifactoría a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que muestra el estado de la piscifactoria y de todos sus tanques.
     */
    public void showStatus() {
        System.out.println("===============" + nombre +
                "===============\nTanques: " + tanques.size() + datosTanques());
    }

    /**
     * Método auxiliar de showStatus que realiza los calculos de cantidades y porcentajes para devolver como string al resto del mensaje.
     */
    private String datosTanques() {
        int peces = 0;
        int capacidad = 0;
        int vivos = 0;
        int alimentados = 0;
        int adultos = 0;
        int hembras = 0;
        int machos = 0;
        int fertiles = 0;

        for (int i = 0; i < tanques.size(); i++) {
            peces += tanques.get(i).getPeces().size();
            capacidad += tanques.get(i).getCapacidadMaximaPeces();
            vivos += tanques.get(i).pecesVivos();
            alimentados += tanques.get(i).pecesAlimentados();
            adultos += tanques.get(i).pecesAdultosVivos();
            hembras += tanques.get(i).pecesHembra();
            machos += tanques.get(i).pecesMacho();
            fertiles += tanques.get(i).pecesFertiles();
            }
        
        if(peces != 0){

            if(vivos != 0){
                return "\nOcupación: " + peces + "/" + capacidad + " " + "(" + String.format("%.2f", (((float)peces / (float)capacidad) * 100))+ "%)" +
                        "\nPeces vivos: " + vivos + "/" + peces + "(" + String.format("%.2f", (((float)vivos / (float)peces) * 100)) + "%)" +
                        "\nPeces alimentados: " + alimentados + "/" + vivos + "(" + String.format("%.2f",(((float)alimentados / (float)vivos) * 100)) + "%)" +
                        "\nPeces adultos: " + adultos + "/" + vivos + "(" + String.format("%.2f", (((float)adultos / (float)vivos) * 100)) + "%)" +
                        "\nHembras / Machos: " + hembras + "/" + machos +
                        "\nFértiles: " + fertiles + "/" + vivos + "(" + String.format("%.2f", (((float)fertiles / (float)vivos) * 100))+ "%)" +
                        "\nAlmacén de comida: \n\t-comida carnivoros: " + almacenInicial.cantidadComidaAnimal + "/"
                        + almacenInicial.capacidadMaximaComida + "("
                        + String.format("%.2f",((float)almacenInicial.cantidadComidaAnimal/ (float)almacenInicial.capacidadMaximaComida) * 100) + "%)"
                        + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
                        + almacenInicial.capacidadMaximaComida + "("
                        + String.format("%.2f", ((float)almacenInicial.cantidadComidaVegetal / (float)almacenInicial.capacidadMaximaComida) * 100) + "%)";
            }
            else{
                return "\nOcupación: " + peces + "/" + capacidad + " " + "(" + String.format("%.2f", (((float)peces / (float)capacidad) * 100))+ "%)" +
                    "\nPeces vivos: " + vivos + "/" + peces + "(" + String.format("%.2f", (((float)vivos / (float)peces) * 100)) + "%)" +
                    "\nPeces alimentados: " + 0 + "/" + 0 + "(" + 100 + "%)" +
                    "\nPeces adultos: " + 0 + "/" + 0 + "(" + 100 + "%)" +
                    "\nHembras / Machos: " + hembras + "/" + machos +
                    "\nFértiles: " + 0 + "/" + 0 + "(" + 100 + "%)" +
                    "\nAlmacén de comida: \n\t-comida carnivoros: " + almacenInicial.cantidadComidaAnimal + "/"
                    + almacenInicial.capacidadMaximaComida + "("
                    + String.format("%.2f",((float)almacenInicial.cantidadComidaAnimal/ (float)almacenInicial.capacidadMaximaComida) * 100) + "%)"
                    + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
                    + almacenInicial.capacidadMaximaComida + "("
                    + String.format("%.2f", ((float)almacenInicial.cantidadComidaVegetal / (float)almacenInicial.capacidadMaximaComida) * 100) + "%)";
            }
        }
        else{
            return "\nOcupación: " + peces + "/" + capacidad + " " + "(" + (peces / capacidad) * 100 + "%)" +
            "\nPeces vivos: " + vivos + "/" + peces + "(100%)" +
            "\nPeces alimentados: " + alimentados + "/" + vivos + "(100%)" +
            "\nPeces adultos: " + adultos + "/" + vivos + "(100%)" +
            "\nHembras / Machos: " + hembras + "/" + machos +
            "\nFértiles: " + fertiles + "/" + vivos + "(100%)" +
            "\nAlmacén de comida: \n\t-comida carnivoros: " + almacenInicial.cantidadComidaAnimal + "/"
            + almacenInicial.capacidadMaximaComida + "("
            + String.format("%.2f", ((float)almacenInicial.cantidadComidaAnimal / (float)almacenInicial.capacidadMaximaComida) * 100) + "%)"
            + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
            + almacenInicial.capacidadMaximaComida + "("
            + String.format("%.2f", ((float)almacenInicial.cantidadComidaVegetal / (float)almacenInicial.capacidadMaximaComida) * 100) + "%)";
        }
    }

    /**
     * Método que muestra el estado de los tanques.
     */
    public void showTankStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showStatus();
        }
    }

    /**
     * Método que muestra el estado de los peces de los tanques.
     */
    public void showFishStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showFishStatus();
        }
    }

    /**
     * Método que muestra la capacidad de los tanques.
     */
    public void showCapacity() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showCapacity(nombre);
        }
    }

    /**
     * Método que muestra por mensaje el estado del almacén.
     */
    public void showFood() {
        System.out.println("============== Almacén ================\nComida animal: "
                + almacenInicial.getCantidadComidaAnimal() + "/" + almacenInicial.getCapacidadMaximaComida() +
                "(" + String.format("%.2f", (((float)almacenInicial.getCantidadComidaAnimal() / (float)almacenInicial.getCapacidadMaximaComida()) * 100))
                + "%)" +
                "\nComida vegetal: " + almacenInicial.getCantidadComidaVegetal() + "/"
                + almacenInicial.getCapacidadMaximaComida() +
                "(" + String.format("%.2f", (((float)almacenInicial.getCantidadComidaVegetal() / (float)almacenInicial.getCapacidadMaximaComida()) * 100))
                + "%)");
    }

    /**
     * Realiza la lógica de que se pasa un día cuando no se dispone de almacén central.
     * @return Número de peces vendidos en la piscifactoría cuando pasa el día.
     */
    public int nextDay() {
        int pecesVendidos = 0;
        for(Tanque tanque : tanques){
            if(!tanque.getPeces().isEmpty()){
                tanque.alimentar(almacenInicial);
            }
            pecesVendidos += tanque.nextDay();
        }

        return pecesVendidos;
    }

    /**
     * Método que vende los peces maduros y vivos de los tanques.
     */
    public void sellFish() {
        for (Tanque tanque : tanques) {
            tanque.venderPeces();
        }
    }
    
    /**
     * Método que mejora el almacen.
     */
    public abstract void upgradeFood();

    /**
     * 
     * @return Número de peces vivos en la piscifactoría.
     */
    public int getPecesVivos(){
        int pecesVivos = 0;
        for(Tanque tanque : tanques){
            pecesVivos += tanque.pecesVivos();
        }

        return pecesVivos;
    }

    /**
     * 
     * @return Número de peces totales en la piscifactoría.
     */
    public int getPecesTotales(){
        int peces = 0;
        for(Tanque tanque : tanques){
            peces += tanque.getPeces().size();
        }

        return peces;
    }

    /**
     * 
     * @return Espacio total para peces en la piscifactoría.
     */
    public int getEspacioPeces(){
        int espacioPeces = 0;
        for(Tanque tanque : tanques){
            espacioPeces += tanque.getCapacidadMaximaPeces();
        }

        return espacioPeces;
    }

    /**
     * Permite obtener el índice de un tanque vacio de la piscifactoría.
     * @return Índice de un tanque vación de la piscifactoría o -1 si no hay un tanque vacio en la piscifactoría.
     */
    public int getIndiceTanqueVacio(){
        for(Tanque tanque : tanques){
            if(tanque.getPeces().isEmpty()){
                return tanques.indexOf(tanque);
            }
        }

        return -1;
    }

    /**
     * Permite obtener el índice de un tanque que tiene un espacio para un pez en concreto.
     * @param nombrePez Nombre del pez.
     * @return Índice de un tanque que tiene un espacio para un pez en concreto o -1 
     * si no hay un tanque con un espacio para un pez en concreto.
     */
    public int getIndiceTanqueConEspacioParaPez(String nombrePez){
        for(Tanque tanque : tanques){
            if(!tanque.getPeces().isEmpty() && tanque.getPeces().get(0).getNombre().equals(nombrePez)){
                return tanques.indexOf(tanque);
            }
        }

        return -1;
    }

    /**
     * Indica si todos los tanques de la piscifactoría están llenos.
     * @return True si todos los tanques de la piscifactoría están llenos.
     */
    public boolean isTodosLosTanqueLlenos(){
        for(Tanque tanque : tanques){
            if(tanque.getPeces().size() < tanque.getCapacidadMaximaPeces()){
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve un string con información de la piscifactoría.
     * @return String con información de la piscifactoría.
     */
    public String toString(){
        return "Nombre piscifactoría: " + nombre + "\nNúmero de tanques: " + tanques.size() + "\nAlmacén comida:\n\t" + almacenInicial;
    }

    /**
     * Clase que se encarga de adaptar un objeto Piscifactoria para que pueda ser serializado y deserializado como JSON.
     */
    private class AdaptadorJSON implements JsonDeserializer<Piscifactoria>, JsonSerializer<Piscifactoria>{

        /**
         * Se encarga de la serialización de un objeto Piscifactoria.
         */
        @Override
        public JsonElement serialize(Piscifactoria src, Type typeOfSrc, JsonSerializationContext context) {
            String json = "{ \"nombre\" : \"" + src.nombre + "\" , \"tipo\" : \"" + src.tipo + "\" , \"capacidad\" : \"" + src.almacenInicial.capacidadMaximaComida + "\" , \"comida\" : { "
            + "\"vegetal\" : \"" + src.almacenInicial.cantidadComidaVegetal + "\" , \"animal\" : \"" + src.almacenInicial.cantidadComidaAnimal + "\" }, \"tanques\" : " + new Gson().toJson(src.tanques) + "}";
            return JsonParser.parseString(json);
        }

        /**
         * Se encarga de la deserialización de un objeto Piscifactoria.
         */
        @Override
        public Piscifactoria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject objetoJson = json.getAsJsonObject();
            Tanque[] tanques = new Gson().fromJson(objetoJson.get("tanques"), Tanque[].class);
            int tipoPiscifactoria = objetoJson.get("tipo").getAsInt();

            Piscifactoria piscifactoria;
            if(tipoPiscifactoria == 0){
                piscifactoria = new PiscifactoriaRio(objetoJson.get("nombre").getAsString());
            }
            else{
                piscifactoria = new PiscifactoriaMar(objetoJson.get("nombre").getAsString());
            }

            for(int i = 0; i < tanques.length; i++) {
                tanques[i].setNumeroTanque(i + 1);
                if (tipoPiscifactoria == 0) { 
                    tanques[i].setCapacidadMaximaPeces(25);
                    piscifactoria.tanques.add(tanques[i]);
                } else { 
                    tanques[i].setCapacidadMaximaPeces(100);
                    piscifactoria.tanques.add(tanques[i]);
                }
            }

            return piscifactoria;
        }

    }
}