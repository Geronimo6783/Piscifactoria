package simulador.pez.filtrador;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

import java.util.Random;

/**
 * Clase que representa a un arenque del atlántico.
 */
public class ArenqueDelAtlantico extends Pez implements Filtrador, Mar{
    
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
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
    }

    /**
     * Hace que el arenque del atlántico crezca un día.
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
            if((edad < AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
                alimentado = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.ARENQUE_ATLANTICO.getCiclo() && edad > AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez())) {
                    fertil = true;
                }
            }
        }
    }

    /**
     * Indica si el arenque del atlántico está maduro.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez();
    }

    /**
     * Indica si el arenque del atlántico está en la edad óptima para ser vendido.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.ARENQUE_ATLANTICO.getOptimo();
    }

    /**
     * 
     * @return Devuelve un arenque del atlántico nuevo de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.estadisticas.registrarNacimiento(nombre);
        return new ArenqueDelAtlantico(false);
    }

    /**
     * 
     * @return Devuelve un arenque del atlántico nuevo de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.estadisticas.registrarNacimiento(nombre);
        return new ArenqueDelAtlantico(true);
    }
}
