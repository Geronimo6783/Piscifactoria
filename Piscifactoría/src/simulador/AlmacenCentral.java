package simulador;

/**
 * Clase que representa a un alamcén central que almacena comida y la distribuye equitativamente entre las piscifactrías.
 */
public class AlmacenCentral {
    
    /**
     * Capacidad para comida animal en el almacén.
     */
    private int capacidadComidaAnimal;

    /**
     * Capacidad para comida vegetal en el almacén.
     */
    private int capacidadComidaVegetal;

    /**
     * Cantidad de comida animal disponible en el almacén central.
     */
    private int cantidadComidaAnimal;

    /**
     * Cantidad de comida vegetal disponible en el almacén central.
     */
    private int cantidadComidaVegetal;

    /**
     * Constructor de almacenes centrales.
     */
    public AlmacenCentral(){
        capacidadComidaAnimal = 200;
        capacidadComidaVegetal = 200;
    }

    /**
     * 
     * @return Capacidad para comida animal del almacén central.
     */
    public int getCapacidadComidaAnimal() {
        return capacidadComidaAnimal;
    }

    /**
     * Permite establecer la capacidad de comida animal del almacén central.
     * @param capacidadComidaAnimal Capacidad de comida animal a establecer.
     */
    public void setCapacidadComidaAnimal(int capacidadComidaAnimal) {
        this.capacidadComidaAnimal = capacidadComidaAnimal;
    }

    /**
     * 
     * @return Capacidad para comida vegetal del almacén central.
     */
    public int getCapacidadComidaVegetal() {
        return capacidadComidaVegetal;
    }

    /**
     * Permite establecer la capacidad de comida vegetal del almacén central.
     * @param capacidadComidaAnimal Capacidad de comida vegetal a establecer.
     */
    public void setCapacidadComidaVegetal(int capacidadComidaVegetal) {
        this.capacidadComidaVegetal = capacidadComidaVegetal;
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
     * Mejora el almacén centrar aumentado la capacidad de ambas comidas en 50 unidades.
     */
    public void mejorar(){
        capacidadComidaAnimal += 50;
        capacidadComidaVegetal += 50;
    }
}
