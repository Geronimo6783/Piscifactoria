package simulador.tanque;

import java.util.ArrayList;

import simulador.pez.Pez;


public class TanqueCria  extends Tanque{

    private boolean alimentados;

    /**
     * Peces del tanque.
     */
    private ArrayList<Pez> peces;

    public TanqueCria(int numeroTanque, int capacidadMaximaPeces){
        super(numeroTanque, 2);
        peces = new ArrayList<>();
        this.alimentados = false;
    }

   

}
