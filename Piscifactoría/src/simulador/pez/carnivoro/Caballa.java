package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a una caballa.
 */
public class Caballa extends Carnivoro implements Mar{

    /**
     * Constructor de caballas.
     * @param sexo Sexo de la caballa si es true es hembra y si es false es macho.
     */
    public Caballa(boolean sexo){
        super(AlmacenPropiedades.CABALLA.getNombre(), AlmacenPropiedades.CABALLA.getCientifico(), sexo);
    }

    /**
     * Muestra el estado de la caballa.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.CABALLA.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
        System.out.println("Enfermo: " + ((enfermo) ? "Sí" : "No"));
    }

    /**
     * Hace que la caballa crezca un día.
     */
    @Override
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.CABALLA.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.CABALLA.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.CABALLA.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.CABALLA.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.CABALLA.getCiclo() && edad > AlmacenPropiedades.CABALLA.getMadurez())) {
                    fertil = true;
                }
                if (isEnfermo() && edad == AlmacenPropiedades.CABALLA.getMadurez()) {
                    boolean pezSigueConVida = (rt.nextInt( 100) > 10);
                    vivo=pezSigueConVida;
                    if(isAlimentado() || pezSigueConVida){
                        boolean sigueEnfermo=(rt.nextInt(100)>10);
                        enfermo=sigueEnfermo;
                    }
                }else if (isEnfermo()) {
                    boolean pezSigueConVida = (rt.nextInt( 100) > 25);
                    vivo=pezSigueConVida;
                    if(isAlimentado() || pezSigueConVida){
                        boolean sigueEnfermo=(rt.nextInt(100)>10);
                        enfermo=sigueEnfermo;
                    }
                }
            }
            else{
                fertil = false;
            }
        }
        alimentado = false;
        enfermo = false;
    }

    /**
     * Indica si la caballa está madura.
     * @return True si la caballa está madura.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.CABALLA.getMadurez();
    }

    /**
     * Indica si la caballa está en la edad óptima para ser vendida.
     * @return True si la caballa está en la edad óptima para ser vendida.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.CABALLA.getOptimo();
    }

    /**
     * 
     * @return Devuelve una caballa nueva de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Caballa(false);
    }

    /**
     * 
     * @return Devuelve una caballa nueva de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Caballa(true);
    }
}
