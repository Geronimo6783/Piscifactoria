package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un salmón atlántico.
 */
public class SalmonAtlantico extends Pez implements Carnivoro, Rio, Mar{

    /**
     * Constructor de salmones atlánticos.
     * @param sexo Sexo del salmón atlántico.
     */
    public SalmonAtlantico(boolean sexo){
        super(AlmacenPropiedades.SALMON_ATLANTICO.getNombre(), AlmacenPropiedades.SALMON_ATLANTICO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del salmón atlántico.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.SALMON_ATLANTICO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el salmón atlántico crezca un día.
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
            if((edad < AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.SALMON_ATLANTICO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.SALMON_ATLANTICO.getCiclo() && edad > AlmacenPropiedades.SALMON_ATLANTICO.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }
}
