package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a un abadejo.
 */
public class Abadejo extends Omnivoro implements Mar{

    /**
     * Constructor de abadejos.
     * @param sexo Sexo del abadejo si es true es hembra y si es false es macho.
     */
    public Abadejo(boolean sexo){
        super(AlmacenPropiedades.ABADEJO.getNombre(), AlmacenPropiedades.ABADEJO.getCientifico(), sexo);
    }

    /**
     * Muestra el estado del abadejo.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.ABADEJO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
        System.out.println("Enfermo: " + ((enfermo) ? "Sí" : "No"));
    }

    /**
     * Hace que el abadejo crezca un día.
     */
    @Override
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.ABADEJO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ABADEJO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.ABADEJO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.ABADEJO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.ABADEJO.getCiclo() && edad > AlmacenPropiedades.ABADEJO.getMadurez())) {
                    fertil = true;
                }
                if (isEnfermo() && edad == AlmacenPropiedades.ABADEJO.getMadurez()) {
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
     * Indica si el abadejo está maduro.
     * @return True si el abadejo está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.ABADEJO.getMadurez();
    }

    /**
     * Indica si el abadejo está en la edad óptima para ser vendido.
     * @return True si el abadejo está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.ABADEJO.getOptimo();
    }

    /**
     * 
     * @return Devuelve un abadejo nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Abadejo(false);
    }

    /**
     * 
     * @return Devuelve un abadejo nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Abadejo(true);
    }
}
