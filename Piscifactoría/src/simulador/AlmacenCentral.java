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

    
}
