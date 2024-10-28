package simulador.piscifactoria;

import java.util.ArrayList;

import componentes.SistemaMonedas;
import simulador.Tanque;

/**
 * Clase que representa a una piscifactoría con múltiples tanques.
 */
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
     * Nombre de la piscifactoría.
     */
    protected String nombre = "";

    /**
     * Constructor de Ppiscifactorías.
     * @param nombre Nombre de la piscifactoría.
     */
    protected Piscifactoria(String nombre) {
        tanques = new ArrayList<>();
        this.nombre = nombre;
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
                    + String.format("%.2f",((float)almacenInicial.cantidadComidaAnimal/ (float)almacenInicial.capacidadMaximaComida) * 100) + "%)"
                    + "\n\t-comida vegetal: " + almacenInicial.cantidadComidaVegetal + "/"
                    + almacenInicial.capacidadMaximaComida + "("
                    + String.format("%.2f", ((float)almacenInicial.cantidadComidaVegetal / (float)almacenInicial.capacidadMaximaComida) * 100) + "%)";
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
     */
    public void nextDay() {
        for(Tanque tanque : tanques){
            tanque.alimentar(almacenInicial);
            tanque.nextDay();
        }
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
}