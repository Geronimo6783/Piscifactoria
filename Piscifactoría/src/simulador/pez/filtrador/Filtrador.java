package simulador.pez.filtrador;

import simulador.pez.Pez;
import java.util.Random;

/**
 * Clase que representa a un pez filtrador.
 */
public abstract class Filtrador extends Pez {

    /**
     * Constructor de peces filtradores.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     * @param sexo Sexo del pez si es true es hembre y si es false es macho.
     */
    protected Filtrador(String nombre, String nombreCientifico, boolean sexo){
        super(nombre, nombreCientifico, sexo);
    }

    /**
     * Indica la cantida de comida que comerá el pez un día.
     * @return Cantidad de comida que comerá el pez un día.
     */
    public int comer(){
        return (new Random().nextBoolean()) ? 1 : 0;
    }
}
