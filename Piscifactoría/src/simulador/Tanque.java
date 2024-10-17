package simulador;

import simulador.pez.*;
import java.util.ArrayList;
import simulador.pez.carnivoro.*;
import simulador.pez.filtrador.*;
import java.util.Random;
import java.util.Iterator;

import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a un tanque de una piscifactoría que contiene un número
 * de peces.
 */
public class Tanque {

    /**
     * Número del tanque en la piscifactoría.
     */
    private final int numeroTanque;

    /**
     * Capacidad máxima de peces que puede tener el tanque.
     */
    private int capacidadMaximaPeces;

    /**
     * Peces del tanque.
     */
    private ArrayList<Pez> peces;

    /**
     * Capacidad máxima de comida en el tanque por tipo.
     */
    private int capacidadMaximaComida;

    /**
     * Cantidad de comida animal disponible.
     */
    private int comidaAnimal;

    /**
     * Cantidad de comida vegetal disponible.
     */
    private int comidaVegetal;

    /**
     * 
     * @return Capacidad máxima de peces que puede tener el tanque.
     */
    public int getCapacidadMaximaPeces() {
        return capacidadMaximaPeces;
    }

    /**
     * Permite establecer la capacidad máxima de peces que puede tener el tanque.
     * 
     * @param capacidadMaximaPeces Capacidad máxima de peces que puede tener el
     *                             tanque a establecer.
     */
    public void setCapacidadMaximaPeces(int capacidadMaximaPeces) {
        this.capacidadMaximaPeces = capacidadMaximaPeces;
    }

    /**
     * 
     * @return Peces del tanque.
     */
    public ArrayList<Pez> getPeces() {
        return peces;
    }

    /**
     * Permite establecer los peces del tanque.
     * 
     * @param peces Peces del tanque a establecer.
     */
    public void setPeces(ArrayList<Pez> peces) {
        this.peces = peces;
    }

    /**
     * 
     * @return Capacidad máxima de comida por tipo.
     */
    public int getCapacidadMaximaComida() {
        return capacidadMaximaComida;
    }

    /**
     * Permite establecer la capacidad máxima de comida por tipo.
     * 
     * @param capacidadMaximaComida Capacidad máxima de comida por tipo a
     *                              establecer.
     */
    public void setCapacidadMaximaComida(int capacidadMaximaComida) {
        this.capacidadMaximaComida = capacidadMaximaComida;
    }

    /**
     * 
     * @return Cantidad de comida animal disponible.
     */
    public int getComidaAnimal() {
        return comidaAnimal;
    }

    /**
     * Permite establecer la cantidad de comida animal disponible.
     * 
     * @param comidaAnimal Cantidad de comida animal a establecer.
     */
    public void setComidaAnimal(int comidaAnimal) {
        this.comidaAnimal = comidaAnimal;
    }

    /**
     * 
     * @return Cantidad de comidad vegatal disponible
     */
    public int getComidaVegetal() {
        return comidaVegetal;
    }

    /**
     * Permite establecer la cantidad de comida vegetal disponible.
     * 
     * @param comidaVegetal Cantidad de comida vegetal disponible a establecer.
     */
    public void setComidaVegetal(int comidaVegetal) {
        this.comidaVegetal = comidaVegetal;
    }

    /**
     * 
     * @return Número del tanque.
     */
    public int getNumeroTanque() {
        return numeroTanque;
    }

    /**
     * Constructor de tanques.
     * 
     * @param numeroTanque Número del tanque.
     */
    public Tanque(int numeroTanque, int capacidadMaximaPeces) {
        capacidadMaximaComida = 200;
        comidaAnimal = 200;
        comidaVegetal = 200;
        this.numeroTanque = numeroTanque;
        peces = new ArrayList<>();
        this.capacidadMaximaPeces = capacidadMaximaPeces;
    }

    /**
     * Imprime el estado del tanque por pantalla.
     */
    public void showStatus() {
        System.out.println(
                "=============== Tanque " + numeroTanque + " ===============\nOcupación: " + peces.size() + " / " + capacidadMaximaPeces
                        + "(" + String.format("%.2f", (((float) peces.size()) / (float) capacidadMaximaPeces) *100)
                        + "%)\nPeces vivos: " + pecesVivos() + " / " + peces.size() + "("
                        + String.format("%.2f", (((float) pecesVivos() / (float) peces.size())) * 100)
                        + "%)\nPeces alimentados: " + pecesAlimentados() + " / " + peces.size() + "("
                        + String.format("%.2f", (((float) pecesAlimentados() / (float) peces.size())) * 100)
                        + "%)\nPeces adultos: " + pecesAdultos() + " / " + peces.size() + "("
                        + String.format("%.2f", (((float) pecesAdultos() / (float) peces.size())) * 100)
                        + "%)\nHembras / Machos: " + pecesHembra() + " / " + pecesMacho()
                        + "\nFértiles: " + pecesFertiles() + " / " + pecesVivos());

    }

    /**
     * 
     * @return Número de peces vivos en el tanque.
     */
    private int pecesVivos() {
        int pecesVivos = 0;

        for (Pez pez : peces) {
            if (pez.isVivo()) {
                pecesVivos++;
            }
        }

        return pecesVivos;
    }

    /**
     * 
     * @return Número de peces alimentados en el tanque.
     */
    private int pecesAlimentados() {
        int pecesAlimentados = 0;

        for (Pez pez : peces) {
            if (pez.isAlimentado()) {
                pecesAlimentados++;
            }
        }

        return pecesAlimentados;
    }

    /**
     * 
     * @return Número de peces vivos en el tanque.
     */
    private int pecesAdultos() {
        int pecesAdultos = 0;

        for (Pez pez : peces) {
            if (pez.isMaduro()) {
                pecesAdultos += 1;
            }
        }

        return pecesAdultos;
    }

    /**
     * 
     * @return Número de peces macho en el tanque.
     */
    private int pecesMacho() {
        int pecesMacho = 0;

        for (Pez pez : peces) {
            if (!pez.isSexo()) {
                pecesMacho++;
            }
        }

        return pecesMacho;
    }

    /**
     * 
     * @return Número de peces hembra en el tanque.
     */
    private int pecesHembra() {
        int pecesHembra = 0;

        for (Pez pez : peces) {
            if (pez.isSexo()) {
                pecesHembra++;
            }
        }

        return pecesHembra;
    }

    /**
     * 
     * @return Número de peces fértiles en el tanque.
     */
    private int pecesFertiles() {
        int pecesFertiles = 0;

        for (Pez pez : peces) {
            if (pez.isFertil()) {
                pecesFertiles++;
            }
        }

        return pecesFertiles;
    }

    /**
     * Muestra el estado de cada pez del tanque por pantalla.
     */
    public void showFishStatus() {
        for (Pez pez : peces) {
            pez.showStatus();
        }
    }

    /**
     * Imprime el porcentaje de capacidad del tanque.
     * 
     * @param piscifactoria Nombre de la piscifactoría en la que se situa el tanque.
     */
    public void showCapacity(String piscifactoria) {
        System.out.println("Tanque " + numeroTanque + " de la piscifactoría " + piscifactoria + " al "
                + String.format("%.2f", ((float) peces.size() / (float) capacidadMaximaPeces)) + "% de capacidad.");
    }

    /**
     *  Gestiona la lógica para alimentar a los peces.
     */
    public void alimentar() {
        int comidaNecesaria = 0;
        ArrayList<Integer> cantidadDeComidaNecesariaPorPez = new ArrayList<>();

        for (Pez pez : peces) {
            if (pez.isVivo() && !pez.isAlimentado()) {
                cantidadDeComidaNecesariaPorPez.add(pez.comer());
            } else {
                cantidadDeComidaNecesariaPorPez.add(0);
            }
        }

        for (Integer cantidadComida : cantidadDeComidaNecesariaPorPez) {
            comidaNecesaria += cantidadComida;
        }

        if (peces.get(0) instanceof Carnivoro) {
            if (comidaAnimal >= comidaNecesaria) {
                comidaAnimal -= comidaNecesaria;
                for (Pez pez : peces) {
                    if (pez.isVivo() && !pez.isAlimentado()) {
                        pez.setAlimentado(true);
                    }
                }
            } else{
                alimentarAleatorio(cantidadDeComidaNecesariaPorPez, comidaAnimal);
            }
        } else {
            if (peces.get(0) instanceof Filtrador) {
                if (comidaVegetal >= comidaNecesaria) {
                    comidaVegetal -= comidaNecesaria;
                    for (Pez pez : peces) {
                        if (pez.isVivo() && !pez.isAlimentado()) {
                            pez.setAlimentado(true);
                        }
                    }
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, comidaVegetal);
                }
            } else {
                if ((comidaVegetal + comidaAnimal) >= comidaNecesaria) {
                    if (comidaVegetal > comidaAnimal) {
                        if (comidaVegetal >= comidaNecesaria) {
                            comidaVegetal -= comidaNecesaria;
                        } else {
                            comidaVegetal -= comidaNecesaria;
                            comidaAnimal += comidaVegetal;
                            comidaVegetal = 0;
                        }
                    } else {
                        if (comidaAnimal >= comidaNecesaria) {
                            comidaAnimal -= comidaNecesaria;
                        } else {
                            comidaAnimal -= comidaNecesaria;
                            comidaVegetal += comidaAnimal;
                            comidaAnimal = 0;
                        }
                    }

                    for (Pez pez : peces) {
                        if (pez.isVivo() && !pez.isAlimentado()) {
                            pez.setAlimentado(true);
                        }
                    }
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, comidaAnimal + comidaVegetal);
                }
            }
        }
    }

    /**
     * Gestiona la lógica para alimentar a los peces cuando la comida es insuficiente.
     * @param cantidadDeComidaNecesariaPorPez Cantidad de comida que necesita cada Pez para alimentarse.
     * @param comidaDisponible Comida de la que se dispone para alimentar a los peces.
     */
    private void alimentarAleatorio(ArrayList<Integer> cantidadDeComidaNecesariaPorPez, int comidaDisponible) {
        Random rt = new Random();
        ArrayList<Integer> posicionesPecesAlimentados = new ArrayList<>();
        int posicionAleatoria = 0;

        for (int i = 0; i < cantidadDeComidaNecesariaPorPez.size(); i++) {
            if (cantidadDeComidaNecesariaPorPez.get(i) == 0) {
                posicionesPecesAlimentados.add(i);
            }
        }

        while (comidaDisponible > 0) {
            posicionAleatoria = rt.nextInt(cantidadDeComidaNecesariaPorPez.size());

            if (!posicionesPecesAlimentados.contains(posicionAleatoria)) {
                comidaDisponible -= cantidadDeComidaNecesariaPorPez.get(posicionAleatoria);
                cantidadDeComidaNecesariaPorPez.set(posicionAleatoria, 0);
                posicionesPecesAlimentados.add(posicionAleatoria);
            }
            if (comidaDisponible == 1 && !cantidadDeComidaNecesariaPorPez.contains(1)) {
                break;
            }
        }

        if (peces.get(0) instanceof Carnivoro) {
            comidaAnimal = comidaDisponible;
          
        } else {
            if (peces.get(0) instanceof Filtrador) {
                comidaVegetal = comidaDisponible;
               
            } else {
                if (comidaAnimal > comidaVegetal) {
                    comidaAnimal = 0;
                    comidaVegetal = comidaDisponible;
                } else {
                    comidaVegetal = 0;
                    comidaAnimal = comidaDisponible;
                }
                
            }
        }
        for (Integer posicion : posicionesPecesAlimentados) {
            if (peces.get(posicion).isVivo()) {
                peces.get(posicion).setAlimentado(true);
            }
        }
    }

    /**
     * Indica si hay un macho fértil en el tanque.
     * @return True si hay un macho fértil en el tanque.
     */
    private boolean hayMachoFertil(){
        for(Pez pez : peces){
            if(!pez.isSexo() && pez.isFertil()){
                return true;
            }
        }

        return false;
    }

    /**
     * Indica si hay una hembra fértil en el tanque.
     * @return True si hay una hembra fértil en el tanque.
     */
    private boolean hayHembraFertil(){
        for(Pez pez : peces){
            if(pez.isSexo() && pez.isFertil()){
                return true;
            }
        }

        return false;
    }

    /**
     * Gestiona la lógica de reporudcción de los peces del tanque.
     */
    private void reproducir(){
        int numeroHuevos = 0;
        int numeroHuevosPorHembra = AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getHuevos();

        if(hayMachoFertil() && hayHembraFertil()){
            for(Pez pez : peces){
                if(pez.isSexo() && pez.isFertil()){
                    pez.setFertil(false);
                    pez.setDiasSinReproducirse(0);
                    numeroHuevos += numeroHuevosPorHembra;
                }
            }

            while(peces.size() < capacidadMaximaPeces && numeroHuevos > 0){
                if(pecesMacho() >= pecesHembra()){
                    peces.add(peces.getFirst().obtenerPezHija());
                }
                else{
                    peces.add(peces.getFirst().obtenerPezHijo());
                }

                numeroHuevos--;
            }
        }
    }

    /**
     * Vende todos los peces que se encuentran en una edad óptima para ser vendidos.
     * @return Monedas obtenidas por la venta de todos los peces que se encuentran en una edad óptima para ser vendidos.
     */
    private int venderPecesOptimos(){
        int pecesAVender = 0;
        Iterator<Pez> iterador = peces.iterator();
        
        while(iterador.hasNext()){
            Pez pez = iterador.next();

            if(pez.isEdadOptima()){
                pecesAVender++;
                iterador.remove();
            }
        }

        return pecesAVender * AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas();
    }

    /**
     * Vende todos los peces que estén maduros.
     * @return Monedas obtenidas por la venta de todos los peces que estén maduros.
     */
    public int venderPeces(){
        int monedasAObtener = 0;
        Iterator<Pez> iterador = peces.iterator();

        while(iterador.hasNext()){
            Pez pez = iterador.next();

            if(pez.isMaduro() && !pez.isEdadOptima()){
                monedasAObtener += (AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas() / 2);
                iterador.remove();
            }
            else{
                if(pez.isEdadOptima()){
                    monedasAObtener += AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas();
                    iterador.remove();
                }
            }
        }

        return monedasAObtener;
    }

    /**
     * Implementa la lógica de que haya pasado un día haciendo crecer a los
     * peces, realizando la lógica de reproducción y vendiendo los peces que
     * están en la edad óptima.
     */
    public void nextDay() {
        for(Pez pez : peces){
            pez.grow();
        }
        reproducir();
        venderPecesOptimos();
    }
}
