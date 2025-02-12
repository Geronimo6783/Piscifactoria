package simulador.edificios;

/**
 * Clase que representa a una granja de fitoplacton que produce comida vegetal.
 */
public class Fitoplancton {

    /**
     * Indica si la granja de fitoplacton está disponible.
     */
    private boolean disponible = false;

    /**
     * Número de tanques donde se produce el fitoplacton.
     */
    private int tanques = 0;

    /**
     * Día del ciclo de reproducción.
     */
    private int ciclo = 0;

    /**
     * Indica si la granja de fitoplacton está disponible.
     * @return Si la granja de fitoplacton está disponible.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Permite establecer si la granja de fitoplacton está disponible.
     * @param disponible Disponibilidad de la granja de fitoplacton a establecer.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Permite obtener el número de tanque que tiene la granja de fitoplacton.
     * @return
     */
    public int getTanques() {
        return tanques;
    }



    public void setTanques(int tanques) {
        this.tanques = tanques;
    }



    public int getCiclo() {
        return ciclo;
    }



    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }



    /**
     * Mejora la granja de fitoplacton.
     */
    public void mejorar(){
        tanques++;
        ciclo = 0;
    }
}
