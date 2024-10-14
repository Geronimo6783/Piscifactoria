package simulador;

import simulador.pez.*;
import java.util.ArrayList;
import simulador.pez.carnivoro.*;
import simulador.pez.filtrador.*;
import simulador.pez.omnivoro.*;
import java.util.Random;

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
    public Tanque(int numeroTanque) {
        capacidadMaximaComida = 200;
        comidaAnimal = 200;
        comidaVegetal = 200;
        this.numeroTanque = numeroTanque;
    }

    /**
     * Imprime el estado del tanque por pantalla.
     */
    public void showStatus() {
        System.out.println(
                "=============== Tanque"+numeroTanque+"===============\nOcupación: " + peces.size() + " / " + capacidadMaximaPeces
                        + "(" + String.format("%.2f", ((float) peces.size()) / (float) capacidadMaximaPeces)
                        + ")\nPeces vivos: " + pecesVivos() + " / " + peces.size() + "("
                        + String.format("%.2f", ((float) pecesVivos() / (float) peces.size()))
                        + ")\nPeces alimentados: " + pecesAlimentados() + " / " + peces.size() + "("
                        + String.format("%.2f", ((float) pecesAlimentados() / (float) peces.size()))
                        + ")\nPeces adultos: " + pecesAdultos() + " / " + peces.size() + "("
                        + String.format("%.2f", ((float) pecesAdultos() / (float) peces.size()))
                        + ")\nHembras / Machos: " + pecesHembra() + " / " + pecesMacho()
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

    public void nextDay() {

    }

    /*
     * Metodo auxiliar de sellFish en piscifactoria que vende los peces de un tanque y devuelve el valor de monedas
     */
    public int ventaPeces(){
        int valorPez=AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas();
        int validos=0;
        if(pecesAdultos()<pecesVivos()){
            validos=pecesAdultos()+(pecesAdultos()-pecesVivos());
        }else{
            validos=pecesAdultos()-(pecesAdultos()-pecesVivos());
        }
        valorPez=valorPez*validos;
        for (int i=0;i<peces.size();i++) {
            if (peces.get(i).isMaduro()) {
                peces.remove(i);
            }
        }
        return valorPez;
    }
}
