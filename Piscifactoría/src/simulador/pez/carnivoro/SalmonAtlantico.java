package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a un salmón atlántico.
 */
public class SalmonAtlantico extends Carnivoro implements Rio, Mar{

    /**
     * Constructor de salmones atlánticos.
     * @param sexo Sexo del salmón atlántico si es true es hembra y si es false es macho.
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
        System.out.println("Enfermo: " + ((enfermo) ? "Sí" : "No"));
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
            }
            if((edad < AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.SALMON_ATLANTICO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.SALMON_ATLANTICO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.SALMON_ATLANTICO.getCiclo() && edad > AlmacenPropiedades.SALMON_ATLANTICO.getMadurez())) {
                    fertil = true;
                }
                if (isEnfermo() && edad == AlmacenPropiedades.SALMON_ATLANTICO.getMadurez()) {
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
     * Indica si el salmón atlántico está maduro.
     * @return True si el salmón atlántico está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.SALMON_ATLANTICO.getMadurez();
    }

    /**
     * Indica si el salmón atlántico está en la edad óptima para ser vendido.
     * @return True si el salmón atlántico está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.SALMON_ATLANTICO.getOptimo();
    }

    /**
     * 
     * @return Devuelve un salmón atlántico nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new SalmonAtlantico(false);
    }

    /**
     * 
     * @return Devuelve un salmón atlántico nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new SalmonAtlantico(true);
    }
}
