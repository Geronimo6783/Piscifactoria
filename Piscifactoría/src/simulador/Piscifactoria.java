package simulador;

import java.util.ArrayList;

/**
 * Clase que representa a una piscifactoría con múltiples tanques.
 */
public class Piscifactoria {

    /**
     * Clase que representa al almacén de comida de una piscifactoría.
     */
    class AlmacenComida{

        /**
         * Capacidad máxima de comida de cada tipo del almacén.
         */
        private int capacidadMaximaComida;

        /**
         * Cantidad comida animal que hay en el almacén.
         */
        private int cantidadComidaAnimal;

        /**
         * Cantidad comida vegetal que hay en el almacén.
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
         * Mejora el almacen aumentando en 50 unidades la capacidad máxima de comida de cada tipo.
         */
        public void mejorar(){
            capacidadMaximaComida += 50;
        }

    }

    /**
     * Tanques de los que dispone la piscifactoría.
     */
    private ArrayList<Tanque> tanques;

    /**
     * Tipo de agua de la piscifactoría si es true es de mar y si es false es de rio.
     */
    private boolean tipoAgua;
    
    private Tanque tanqueInicial;

    /**
     * Almacén de comida de la piscifactoría.
     */
    private AlmacenComida almacenInicial;

    /**
     * Nombre de la piscifactoría.
     */
    private String nombre = "";

    /**
     * Constructor de Piscifactoria que diferencia entre si el primer tanque es de mar o de rio
     * @param tanques
     * @param tipoAgua
     * @param nombre
     */
    public Piscifactoria(boolean tipoAgua, String nombre) {
        tanques = new ArrayList<>();
        this.tipoAgua = tipoAgua;
        if (tipoAgua) {
            tanqueInicial = new Tanque(1, 100);
        } else {
            tanqueInicial = new Tanque(1, 25);
        }
        this.nombre = nombre;
        this.tanques.add(tanqueInicial);
    }

    /**
     * Devuelve los tanques de la piscifactoria
     * @return tanques
     */
    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    /**
     * Establece el ArrayList de tanques de la piscifactoria
     * @param tanques
     */
    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    /**
     * Devuelve el tanque inicial de la piscifactoria
     * @return 
     */
    public Tanque getTanqueInicial() {
        return tanqueInicial;
    }

    /**
     * Establece el tanque inicial de la piscifactoria
     * @param tanqueInicial
     */
    public void setTanqueInicial(Tanque tanqueInicial) {
        this.tanqueInicial = tanqueInicial;
    }

    /**
     * Devuelve el nombre de la piscifactoria
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el tipo de agua de la piscifactoria
     * @return
     */
    public boolean isTipoAgua() {
        return tipoAgua;
    }

    /**
     * Establece el tipo de agua de la piscifactoria
     * @param tipoAgua
     */
    public void setTipoAgua(boolean tipoAgua) {
        this.tipoAgua = tipoAgua;
    }

    /**
     * devuelve el almacen inicial de la piscifactoria
     * @return
     */
    public AlmacenComida getAlmacenInicial() {
        return almacenInicial;
    }

    /**
     * Establece el almacen inicial de la piscifactoria
     * @param almacenInicial
     */
    public void setAlmacenInicial(AlmacenComida almacenInicial) {
        this.almacenInicial = almacenInicial;
    }

    /**
     * Establece el nombre de la piscifactoria
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que muestra el estado de la piscifactoria y todos sus tanques
     */
    public void showStatus() {
        System.out.println("===============" + nombre +
                "===============\nTanques: " + tanques.size() + datosTanques());
    }

    /**
     * Metodo auxiliar de showStatus que realiza los calculos de cantidades y porcentajes para devolver como string el resto del mensaje
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
            adultos += tanques.get(i).pecesAdultos();
            hembras += tanques.get(i).pecesHembra();
            machos += tanques.get(i).pecesMacho();
            fertiles += tanques.get(i).pecesFertiles();
            }
        
        if(peces != 0){
            return "\nOcupación: " + peces + "/" + capacidad + " " + "(" + (peces / capacidad) * 100 + "%)" +
                    "\nPeces vivos: " + vivos + "/" + peces + "(" + (vivos / peces) * 100 + "%)" +
                    "\nPeces alimentados: " + alimentados + "/" + vivos + "(" + (alimentados / vivos) * 100 + "%)" +
                    "\nPeces adultos: " + adultos + "/" + vivos + "(" + (adultos / vivos) * 100 + "%)" +
                    "\nHembras / Machos: " + hembras + "/" + machos +
                    "\nFértiles: " + fertiles + "/" + vivos + "(" + (fertiles / vivos) * 100 + "%)" +
                    "\nAlmacén de comida: \n\t-comida carnivoros: " + almacenInicial.cantidadComidaAnimal + "/"
                    + almacenInicial.capacidadMaximaComida + "("
                    + String.format("%.2f",(almacenInicial.cantidadComidaAnimal/ almacenInicial.capacidadMaximaComida) * 100) + "%)"
                    + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
                    + almacenInicial.capacidadMaximaComida + "("
                    + String.format("%.2f", (almacenInicial.cantidadComidaVegetal / almacenInicial.capacidadMaximaComida) * 100) + "%)";
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
            + String.format("%.2f", (almacenInicial.cantidadComidaAnimal / almacenInicial.capacidadMaximaComida) * 100) + "%)"
            + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
            + almacenInicial.capacidadMaximaComida + "("
            + String.format("%.2f", (almacenInicial.cantidadComidaVegetal / almacenInicial.capacidadMaximaComida) * 100) + "%)";
        }
    }

    /**
     * Metodo que muestra el estado de los tanques
     */
    public void showTankStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showStatus();
        }
    }

    /**
     * Metodo que muestra el estado de los peces de los tanques
     */
    public void showFishStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showFishStatus();
        }
    }

    /**
     * Metodo que muestra la capacidad de los tanques
     */
    public void showCapacity() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showCapacity(nombre);
        }
    }

    /**
     * Metodo que muestra por mensaje el estado del Almacen
     */
    public void showFood() {
        System.out.println("============== Almacen ================\nComida animal: "
                + almacenInicial.getCantidadComidaAnimal() + "/" + almacenInicial.getCapacidadMaximaComida() +
                "(" + (almacenInicial.getCantidadComidaAnimal() / almacenInicial.getCapacidadMaximaComida()) * 100
                + "%)" +
                "\nComida vegetal: " + almacenInicial.getCantidadComidaVegetal() + "/"
                + almacenInicial.getCapacidadMaximaComida() +
                "(" + (almacenInicial.getCantidadComidaVegetal() / almacenInicial.getCapacidadMaximaComida()) * 100
                + "%)");
    }

    /**
     * Realiza la lógica de que se pasa un día cuando no se dispone de almacén central.
     */
    public void nextDay() {
        for(Tanque tanque : tanques){
            tanque.alimentar(almacenInicial);
            tanque.nextDay();
        }
    }

    /**
     * Realiza la lógica de que se pasa un día cuando se dispono de almacén central.
     * @param almacenCentral Almacén central del que se dispone.
     */
    public void nextDay(AlmacenCentral almacenCentral){
        for(Tanque tanque : tanques){
            tanque.alimentar(almacenInicial, almacenCentral);
            tanque.nextDay();
        }
    }

    /**
     * Metodo que vende los peces maduros y vivos de los tanques devolviendo el valor de monedas conseguidas.
     * @return Cantidad de monedas ganadas de la venta 
     */
    public int sellFish() {
        int monedasGanadas=0;
        for (int i = 0; i < tanques.size(); i++) {
            monedasGanadas=+tanques.get(i).venderPeces();
        }
        return monedasGanadas;
    }
    
    /**
     * Metodo que mejora el almacen
     */
    public void upgradeFood() {
        almacenInicial.mejorar();
        System.out.println("Almacén de la piscifactoria " + nombre
                + " mejorado. Su capacidad ha aumentado en 50 hasta un total de "
                + almacenInicial.getCapacidadMaximaComida());
    }

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
}