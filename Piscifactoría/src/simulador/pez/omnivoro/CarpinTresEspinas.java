package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un carpín de tres espinas.
 */
public class CarpinTresEspinas extends Pez implements Omnivoro, Rio{

    /**
     * Constructor de carpines de tres espinas.
     * @param sexo Sexo del carpín de tres espinas.
     */
    public CarpinTresEspinas(boolean sexo){
        super(AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.CARPIN_TRES_ESPINAS.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del carpín de tres espinas.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }
    
    /**
     * Hace que el carpín de tres espinas crezca un día.
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
            if((edad < AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.CARPIN_TRES_ESPINAS.getCiclo() && edad > AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }

    /**
     * 
     * @return True si el carpín de tres espinas no consume alimento un día.
     */
    public boolean noConsumirComida(){
        return (new Random().nextInt(4) < 3);
    }
}
