package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un róbalo.
 */
public class Robalo extends Pez implements Carnivoro {

    /**
     * Constructor de róbalos.
     * @param sexo Sexo del róbalo.
     */
    public Robalo(boolean sexo){
        super(AlmacenPropiedades.ROBALO.getNombre(), AlmacenPropiedades.ROBALO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del róbalo.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad > AlmacenPropiedades.ROBALO.getMadurez()) ? "H" : "M"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el róbalo crezca un día.
     */
    @Override
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
                fertil = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.ROBALO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ROBALO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                fertil = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad == AlmacenPropiedades.ROBALO.getMadurez() || diasSinReproducirse >= AlmacenPropiedades.ROBALO.getCiclo()) {
                    fertil = true;
                }
            }
        }
    }
}
