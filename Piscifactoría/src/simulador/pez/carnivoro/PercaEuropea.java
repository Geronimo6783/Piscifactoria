package simulador.pez.carnivoro;

import java.util.Random;

import propiedades.AlmacenPropiedades;
import simulador.Simulador;
import simulador.pez.*;

/**
 * Clase que representa a una perca europea.
 */
public class PercaEuropea extends Carnivoro implements Rio{

    /**
     * Constructor de percas europeas.
     * @param sexo Sexo de la perca europea si es true es hembra y si es false es macho.
     */
    public PercaEuropea(boolean sexo){
        super(AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getCientifico(), sexo);
    }

    /**
     * Muestra el estado de la perca europea.
     */
    @Override
    public void showStatus(){
        System.out.println("---------------" + nombre + "---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + ((sexo) ? "H" : "M"));
        System.out.println("Vivo: " + ((vivo) ? "Sí" : "No"));
        System.out.println("Alimentado: " + ((alimentado) ? "Sí" : "No"));
        System.out.println("Adulto: " + ((edad >= AlmacenPropiedades.PERCA_EUROPEA.getMadurez()) ? "Sí" : "No"));
        System.out.println("Fértil: " + ((fertil) ? "Sí" : "No"));
        System.out.println("Enfermo: " + ((enfermo) ? "Sí" : "No"));
    }

    /**
     * Hace que la perca europea crezca un día.
     */
    @Override
    public void grow(){
        if(vivo){
            Random rt = new Random();
            if(!isAlimentado()){
                boolean pezSigueConVida = rt.nextBoolean();
                vivo = pezSigueConVida;
            }
            if((edad < AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && edad%2 == 0 && vivo) || (edad == AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && vivo)){
                boolean pezSigueConVida = (rt.nextInt( 100) > 5);
                vivo = pezSigueConVida;
            }
            if(vivo){
                edad++;
                if(edad > AlmacenPropiedades.PERCA_EUROPEA.getMadurez() && !fertil){
                    diasSinReproducirse++;
                }
                if((edad == AlmacenPropiedades.PERCA_EUROPEA.getMadurez()) || (diasSinReproducirse >= AlmacenPropiedades.PERCA_EUROPEA.getCiclo() && edad > AlmacenPropiedades.PERCA_EUROPEA.getMadurez())) {
                    fertil = true;
                }
                if (isEnfermo() && edad == AlmacenPropiedades.PERCA_EUROPEA.getMadurez()) {
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
     * Indica la cantidad de comida que come la perca europea un día.
     * @return Cantidad de comida que come la perca europea un día.
     */
    public int comer(){
        return (new Random().nextBoolean()) ? 2 : 1;
    }

    /**
     * Indica si la perca europea está madura.
     * @return True si la perca europea está madura.
     */
    @Override
    public boolean isMaduro(){
        return edad >= AlmacenPropiedades.PERCA_EUROPEA.getMadurez();
    }

    /**
     * Indica si la perca europea está en la edad óptima para ser vendida.
     * @return True si la perca europea está en la edad óptima para ser vendida.
     */
    @Override
    public boolean isEdadOptima(){
        return edad == AlmacenPropiedades.PERCA_EUROPEA.getOptimo();
    }

    /**
     * 
     * @return Devuelve una perca europea nueva de sexo masculino.
     */
    @Override
    public Pez obtenerPezHijo(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new PercaEuropea(false);
    }

    /**
     * 
     * @return Devuelve una perca europea de sexo femenino.
     */
    @Override
    public Pez obtenerPezHija(){
        Simulador.simulador.estadisticas.registrarNacimiento(nombre);
        return new PercaEuropea(true);
    }
}
