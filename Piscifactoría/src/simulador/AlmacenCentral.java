package simulador;

/**
 * Clase que representa a un almacén central que almacena comida y la distribuye equitativamente entre las piscifactrías.
 */
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
     * Constructor de almacenes centrales.
     */
    public AlmacenCentral(){
        capacidadComida = 200;
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
     * Mejora el almacén centrar aumentado la capacidad máxima de cada tipo de comida en 50 unidades.
     */
    public void mejorar(){
        capacidadComida += 50;
    }
}
