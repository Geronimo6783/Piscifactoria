package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un salmón chinook.
 */
public class SalmonChinook extends Pez implements Carnivoro {

    /**
     * Constructor de salomnes chinooks.
     * @param sexo Sexo del salmón chinook.
     */
    public SalmonChinook(boolean sexo){
        super(AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SALMON_CHINOOK.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del salmón chinook.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.SALMON_CHINOOK.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el salmón chinook crezca un día.
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
            if((edad < AlmacenPropiedades.SALMON_CHINOOK.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.SALMON_CHINOOK.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.SALMON_CHINOOK.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.SALMON_CHINOOK.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.SALMON_CHINOOK.getCiclo() && edad > AlmacenPropiedades.SALMON_CHINOOK.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }
}
