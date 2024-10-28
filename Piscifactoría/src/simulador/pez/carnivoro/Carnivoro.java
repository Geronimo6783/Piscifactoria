package simulador.pez.carnivoro;

import simulador.pez.Pez;

/**
 * Clase de peces carnívoros.
 */
public abstract class Carnivoro extends Pez {

    /**
     * Constructor de peces carnívoros.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     * @param sexo Sexo del pez si es true es hembra y si es false es macho.
     */
    protected Carnivoro(String nombre, String nombreCientifico, boolean sexo){
        super(nombre, nombreCientifico, sexo);
    }
}
