package simulador.piscifactoria;

import simulador.Simulador;
import simulador.Tanque;

/**
 * Clase que representa a una piscifactoría de río.
 */
public class PiscifactoriaRio extends Piscifactoria {

    /**
     * Constructor de piscifactorías de río.
     * @param nombre Nombre de la piscifactoría de río.
     */
    public PiscifactoriaRio(String nombre){
        super(nombre);
        tanqueInicial = new Tanque(1, 25);
        if(Simulador.piscifactorias.size() != 0){
            almacenInicial = new AlmacenComida(25, 0, 0);
        }
        else{
            almacenInicial = new AlmacenComida(25, 25, 25);
        }
        this.tanques.add(tanqueInicial);
    }

    /**
     * Mejora la capacidad del almacén de comida en 25 unidades.
     */
    public void upgradeFood(){
        almacenInicial.mejorar(25);
        System.out.println("Almacén de la piscifactoria " + nombre
            + " mejorado. Su capacidad ha aumentado en 25 hasta un total de "
            + almacenInicial.getCapacidadMaximaComida() + ".");
    }

    /**
     * Devuelve un string con información de la piscifactoría de río.
     * @return String con información de la piscifactoría de río.
     */
    public String toString(){
        return "Nombre piscifactoría de río: " + nombre + "\nNúmero de tanques: " + tanques.size() + "\nAlmacén comida:\n\t" + almacenInicial;
    }
}
