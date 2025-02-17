package simulador.pez.omnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a un sargo.
 */
public class Sargo extends Omnivoro implements Mar{

    /**
     * Constructor de sargos.
     * @param sexo Sexo del sargo si es true es hembra y si es false es macho.
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
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.SARGO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
        System.out.println("Enfermo: " + ((enfermo) ? "Sí" : "No"));
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
            }
            if((edad < AlmacenPropiedades.SARGO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.SARGO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.SARGO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.SARGO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.SARGO.getCiclo() && edad > AlmacenPropiedades.SARGO.getMadurez())) {
                    fertil = true;
                }
                if (isEnfermo() && edad == AlmacenPropiedades.SARGO.getMadurez()) {
                    boolean pezSigueConVida = (rt.nextInt( 100) > 10);
                    vivo=pezSigueConVida;
                }else if (isEnfermo()) {
                    boolean pezSigueConVida = (rt.nextInt( 100) > 25);
                    vivo=pezSigueConVida;
                    if(isAlimentado()){
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
     * Indica si el sargo está maduro.
     * @return True si el sargo está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.SARGO.getMadurez();
    }

    /**
     * Indica si el sargo está en la edad óptima para ser vendido.
     * @return True si el sargo está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.SARGO.getOptimo();
    }

    /**
     * 
     * @return Devuelve un sargo nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Sargo(false);
    }

    /**
     * 
     * @return Devuelve un sargo nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new Sargo(true);
    }
}
