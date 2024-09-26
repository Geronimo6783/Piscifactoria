package simulador.pez.filtrador;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;
import java.util.Random;

/**
 * Clase que representa a un arenque del atlántico.
 */
public class ArenqueDelAtlantico extends Pez implements Filtrador{
    
    /**
     * Contructor de arenque del atlántico.
     * @param sexo Sexo del arenque del atlántico.
     */
    public ArenqueDelAtlantico(boolean sexo) {
        super(AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del arenque del atlántico.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad > AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez()) ? "H" : "M"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el arenque del atlántico crezca un día.
     */
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
                fertil = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt(101) >= 5) ? false : true;
                vivo = pezSigueConVida;
                fertil = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad == AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() || diasSinReproducirse >= AlmacenPropiedades.ARENQUE_ATLANTICO.getCiclo()) {
                    fertil = true;
                }
            }
        }
    }
}
