package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a una perca europea.
 */
public class PercaEuropea extends Pez implements Carnivoro{

    /**
     * Constructor de percas europeas.
     * @param sexo Sexo de la perca europea.
     */
    public PercaEuropea(boolean sexo){
        super(AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getCientifico(), sexo);
    }

    /**
     * Muestra el estado de la perca europea.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.PERCA_EUROPEA.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que la perca europea crezca un día.
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
            if((edad < AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.PERCA_EUROPEA.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.PERCA_EUROPEA.getCiclo() && edad > AlmacenPropiedades.PERCA_EUROPEA.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }
}
