package simulador.pez.filtrador;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;
import simulador.pez.omnivoro.Sargo;

/**
 * Clase que representa a una tilapia del nilo.
 */
public class TilapiaDelNilo extends Pez implements Filtrador, Rio{

    /**
     * Constructor de tilapias del nilo.
     * @param sexo Sexo de la tilapia del nilo.
     */
    public TilapiaDelNilo(boolean sexo){
        super(AlmacenPropiedades.TILAPIA_NILO.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado de la tilapia del nilo.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.TILAPIA_NILO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que la tilapia del nilo crezca un día.
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
            if((edad < AlmacenPropiedades.TILAPIA_NILO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.TILAPIA_NILO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.TILAPIA_NILO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.TILAPIA_NILO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.TILAPIA_NILO.getCiclo() && edad > AlmacenPropiedades.TILAPIA_NILO.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }

    /**
     * Indica si la tirapia del nilo está madura.
     */
    @Override
    public boolean isMaduro(){
        return edad > AlmacenPropiedades.TILAPIA_NILO.getMadurez();
    }

    /**
     * Indica si la tirapia del nilo está en la edad óptima para ser vendida.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.TILAPIA_NILO.getOptimo();
    }

    /**
     * 
     * @return Devuelve una tilapia del nilo nueva de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        return new TilapiaDelNilo(false);
    }

    /**
     * 
     * @return Devuelve una tilapia del nilo nueva de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        return new TilapiaDelNilo(true);
    }
}
