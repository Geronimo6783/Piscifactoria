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
     * Indica la cantidad de comida que come el carpín de tres espinas un día.
     * @return Cantidad de comida que comer el carpín de tres espinas.
     */
    @Override
    public int comer(){
        return (new Random().nextInt(4) < 3) ? 0 : 1;

    }

    /**
     * Indica si el carpín de tres espinas está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad > AlmacenPropiedades.CARPIN_TRES_ESPINAS.getMadurez();
    }

    /**
     * Indica si el carpín de tres espinas está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.CARPIN_TRES_ESPINAS.getOptimo();
    }

    /**
     * 
     * @return Devuelve un carpín de tres espinas nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        return new CarpinTresEspinas(false);
    }

    /**
     * 
     * @return Devuelve un carpín de tres espinas nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        return new CarpinTresEspinas(true);
    }
}
