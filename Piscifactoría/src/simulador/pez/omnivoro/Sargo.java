package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un sargo.
 */
public class Sargo extends Pez implements Omnivoro {

    /**
     * Constructor de sargos.
     * @param sexo Sexo del sargo.
     */
    public Sargo(boolean sexo){
        super(AlmacenPropiedades.SARGO.getNombre(), AlmacenPropiedades.SARGO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del sargo.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad > AlmacenPropiedades.SARGO.getMadurez()) ? "H" : "M"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el sargo crezca un día.
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
            if((edad < AlmacenPropiedades.SARGO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.SARGO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                fertil = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad == AlmacenPropiedades.SARGO.getMadurez() || diasSinReproducirse >= AlmacenPropiedades.SARGO.getCiclo()) {
                    fertil = true;
                }
            }
        }
    }
}
