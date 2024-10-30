package simulador.pez.omnivoro;

import simulador.pez.Pez;
import java.util.Random;

/**
 * Clase que representa a un pez omnívoro.
 */
public abstract class Omnivoro extends Pez{

    /**
     * Constructor de peces omnívoros.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre del pez científico.
     * @param sexo Sexo del pez si es true es hembre y si es false es macho.
     */
    protected Omnivoro(String nombre, String nombreCientifico, boolean sexo){
        super(nombre,nombreCientifico,sexo);
    }

    /**
     * Indica la cantidad de comida que comerá el pez un día.
     * @return Cantidad de comida que comerá el pez un día.
     */
    public int comer(){
        return (new Random().nextInt(4) < 3) ? 1 : 0;
    }
}
