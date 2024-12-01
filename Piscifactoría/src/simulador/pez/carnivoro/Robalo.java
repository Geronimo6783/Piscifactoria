package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a un róbalo.
 */
public class Robalo extends Carnivoro implements Mar{

    /**
     * Constructor de róbalos.
     * @param sexo Sexo del róbalo si es true es hembra y si es false es macho.
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
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.ROBALO.getMadurez()) ? "Sí" : "No"));
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
            }
            if((edad < AlmacenPropiedades.ROBALO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ROBALO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.ROBALO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.ROBALO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.ROBALO.getCiclo() && edad > AlmacenPropiedades.ROBALO.getMadurez())) {
                    fertil = true;
                }
            }
        }
        alimentado = false;
    }

    /**
     * Indica si el róbalo está maduro.
     * @return True si el róbalo está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.ROBALO.getMadurez();
    }

    /**
     * Indica si el róbalo está en la edad óptima para ser vendido.
     * @return True si el róbalo está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.ROBALO.getOptimo();
    }

    /**
     * 
     * @return Devuelve un róbalo nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Robalo(false);
    }

    /**
     * 
     * @return Devuelve un róbalo nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Robalo(true);
    }
}
