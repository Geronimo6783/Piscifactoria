package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.pez.*;

/**
 * Clase que representa a un pejerrey.
 */
public class Pejerrey extends Pez implements Carnivoro{

    /**
     * Constructor de pejerreyes.
     * @param sexo Sexo del pejerrey.
     */
    public Pejerrey(boolean sexo){
        super(AlmacenPropiedades.PEJERREY.getNombre(), AlmacenPropiedades.PEJERREY.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del pejerrey.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.PEJERREY.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el pejerrey crezca un día.
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
            if((edad < AlmacenPropiedades.PEJERREY.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.PEJERREY.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.PEJERREY.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.PEJERREY.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.PEJERREY.getCiclo() && edad > AlmacenPropiedades.PEJERREY.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }
}
