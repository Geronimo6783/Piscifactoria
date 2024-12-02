package simulador.piscifactoria;

import simulador.Tanque;

/**
 * Clase que representa a una piscifactoría de mar.
 */
public class PiscifactoriaMar extends Piscifactoria {

    /**
     * Constructor de piscifactorías de mar.
     * @param nombre Nombre de la piscifactoría de mar.
     */
    public PiscifactoriaMar(String nombre){
        super(nombre, 1);
        tanqueInicial = new Tanque(1, 100);
        almacenInicial = new AlmacenComida(100, 0, 0);
        this.tanques.add(tanqueInicial);
    }

    /**
     * Mejora la capacidad del almacén de comida en 100 unidades.
     */
    public void upgradeFood(){
        almacenInicial.mejorar(100);
        System.out.println("Almacén de la piscifactoria " + nombre
            + " mejorado. Su capacidad ha aumentado en 100 hasta un total de "
            + almacenInicial.getCapacidadMaximaComida() + ".");
    }

    /**
     * Devuelve un string con información de la piscifactoría de mar.
     * @return String con información de la piscifactoría de mar.
     */
    public String toString(){
        return "Nombre piscifactoría de mar: " + nombre + "\nNúmero de tanques: " + tanques.size() + "\nAlmacén comida:\n\t" + almacenInicial;
    }
}
