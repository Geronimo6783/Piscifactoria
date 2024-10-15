package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a una dorada.
 */
public class Dorada extends Pez implements Omnivoro, Rio, Mar{

    /**
     * Constructor de doradas.
     * @param sexo Sexo de la dorada.
     */
    public Dorada(boolean sexo){
        super(AlmacenPropiedades.DORADA.getNombre(), AlmacenPropiedades.DORADA.getCientifico(), sexo);
    }

    /**
     * Muestra el estado de la dorada.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.DORADA.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que la dorada crezca un día.
     */
    @Override
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.DORADA.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.DORADA.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.DORADA.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.DORADA.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.DORADA.getCiclo() && edad > AlmacenPropiedades.DORADA.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }

    /**
     * Indica si la dorada está madura.
     */
    @Override
    public boolean isMaduro(){
        return edad > AlmacenPropiedades.DORADA.getMadurez();
    }

    /**
     * Indica si la dorada está en la edad óptima para ser vendida.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.DORADA.getOptimo();
    }
}
