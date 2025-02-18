package simulador.sistemaguardado;

import simulador.edificios.AlmacenCentral;
import simulador.edificios.Fitoplancton;
import simulador.edificios.Langostinos;

/**
 * Clase que contiene los edificios de la piscifactoría.
 */
public class Edificio {

    /**
     * Almacén central de comida de la piscifactoría.
     */
    private final AlmacenCentral almacen;

    /**
     * Granja de fitoplancton de la piscifactoría.
     */
    private final Fitoplancton fitoplancton;

    /**
     * Granja de langostinos de la piscifactoría.
     */
    private final Langostinos langostinos;

    /**
     * Constructor parametrizado.
     * @param almacen Almacén central de comida de la piscifactoría.
     * @param fitoplancton Granja de fitoplancton de la piscifactoría.
     * @param langostinos Granja de langostinos de la piscifactoría. 
     */
    public Edificio(AlmacenCentral almacen, Fitoplancton fitoplancton, Langostinos langostinos) {
        this.almacen = almacen;
        this.fitoplancton = fitoplancton;
        this.langostinos = langostinos;
    }

    /**
     * Permite obtener el almacén central de comida de la piscifactoría.
     * @return Almacén central de comida de la piscifactoría.
     */
    public AlmacenCentral getAlmacen() {
        return almacen;
    }

    /**
     * Permite obtener la granja de fitoplancton de la piscifactoría.
     * @return Granja de fitoplancton de la piscifactoría.
     */
    public Fitoplancton getFitoplancton() {
        return fitoplancton;
    }

    /**
     * Permite obtener la granja de langostinos de la piscifactoría.
     * @return Granja de langostions de la piscifactoría.
     */
    public Langostinos getLangostinos() {
        return langostinos;
    }
}
