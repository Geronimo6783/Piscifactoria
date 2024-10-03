package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un abadejo.
 */
public class Abadejo extends Pez implements Omnivoro, Mar{

    /**
     * Constructor de abadejos.
     * @param sexo Sexo del abadejo.
     */
    public Abadejo(boolean sexo){
        super(AlmacenPropiedades.ABADEJO.getNombre(), AlmacenPropiedades.ABADEJO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del abadejo.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.ABADEJO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el abadejo crezca un día.
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
            if((edad < AlmacenPropiedades.ABADEJO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ABADEJO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.ABADEJO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.ABADEJO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.ABADEJO.getCiclo() && edad > AlmacenPropiedades.ABADEJO.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }

    /**
     * Indica si el abadejo está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad > AlmacenPropiedades.ABADEJO.getMadurez();
    }

    /**
     * Indica si el abadejo está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.ABADEJO.getOptimo();
    }
}
