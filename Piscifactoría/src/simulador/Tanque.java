package simulador;

import simulador.Piscifactoria.AlmacenComida;
import simulador.pez.*;
import java.util.ArrayList;
import simulador.pez.carnivoro.*;
import simulador.pez.filtrador.*;
import java.util.Random;

import componentes.SistemaEntrada;
import componentes.SistemaMonedas;

import java.util.Iterator;

import propiedades.AlmacenPropiedades;

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
    public int pecesVivos() {
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
    public int pecesAlimentados() {
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
    public int pecesAdultos() {
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
    public int pecesMacho() {
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
    public int pecesHembra() {
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
    public int pecesFertiles() {
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
    public void alimentar(Piscifactoria.AlmacenComida almacenComida) {
        int comidaNecesaria = 0;
        ArrayList<Integer> cantidadDeComidaNecesariaPorPez = new ArrayList<>();
        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();

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

                almacenComida.setCantidadComidaAnimal(comidaAnimal);
            } else{
                alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaAnimal);
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

                    almacenComida.setCantidadComidaVegetal(comidaVegetal);
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaVegetal);
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

                    almacenComida.setCantidadComidaAnimal(comidaAnimal);
                    almacenComida.setCantidadComidaVegetal(comidaVegetal);
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaAnimal + comidaVegetal);
                }
            }
        }
    }

    /**
     * Gestiona la lógica para alimentar a los peces.
     * @param almacenCentral Almacén central de donde se obtiene la comida si el tanque se queda sin ella.
     */
    public void alimentar(Piscifactoria.AlmacenComida almacenComida, AlmacenCentral almacenCentral) {
        int comidaNecesaria = 0;
        ArrayList<Integer> cantidadDeComidaNecesariaPorPez = new ArrayList<>();
        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();
        int comidaAnimalAlmacen = almacenCentral.getCantidadComidaAnimal();
        int comidaVegetalAlmacen = almacenCentral.getCantidadComidaVegetal();

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
            if ((comidaAnimal + comidaAnimalAlmacen) >= comidaNecesaria) {
                comidaAnimal -= comidaNecesaria;
                if(comidaAnimal < 0){
                    almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen + comidaAnimal);
                    comidaAnimal = 0;
                }
                for (Pez pez : peces) {
                    if (pez.isVivo() && !pez.isAlimentado()) {
                        pez.setAlimentado(true);
                    }
                }

                almacenComida.setCantidadComidaAnimal(comidaAnimal);
            } else{
                alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaAnimal + comidaAnimalAlmacen, almacenCentral);
            }
        } else {
            if (peces.get(0) instanceof Filtrador) {
                if ((comidaVegetal + comidaVegetalAlmacen)>= comidaNecesaria) {
                    comidaVegetal -= comidaNecesaria;
                    if(comidaVegetal < 0){
                        almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen + comidaVegetal);
                        comidaVegetal = 0;
                    }
                    for (Pez pez : peces) {
                        if (pez.isVivo() && !pez.isAlimentado()) {
                            pez.setAlimentado(true);
                        }
                    }

                    almacenComida.setCantidadComidaVegetal(comidaVegetalAlmacen);
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaVegetal + comidaVegetalAlmacen, almacenCentral);
                }
            } else {
                if ((comidaVegetal + comidaAnimal + comidaAnimalAlmacen + comidaVegetalAlmacen) >= comidaNecesaria) {
                    if(comidaVegetal + comidaAnimal >= comidaNecesaria){
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
                    }
                    else{
                        comidaNecesaria -= (comidaVegetal + comidaAnimal);
                        comidaVegetal = 0;
                        comidaAnimal = 0;

                        if (comidaVegetalAlmacen > comidaAnimalAlmacen) {
                            if (comidaVegetalAlmacen >= comidaNecesaria) {
                                almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen - comidaNecesaria);
                            } else {
                                comidaVegetalAlmacen -= comidaNecesaria;
                                comidaAnimalAlmacen += comidaVegetal;
                                comidaVegetal = 0;
                                almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen);
                                almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen);
                            }
                        } else {
                            if (comidaAnimalAlmacen >= comidaNecesaria) {
                                almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen + comidaNecesaria);
                            } else {
                                comidaAnimalAlmacen -= comidaNecesaria;
                                comidaVegetalAlmacen += comidaAnimal;
                                comidaAnimalAlmacen = 0;
                                almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen);
                                almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen);
                            }
                        }
                    }
                    for (Pez pez : peces) {
                        if (pez.isVivo() && !pez.isAlimentado()) {
                            pez.setAlimentado(true);
                        }
                    }

                    almacenComida.setCantidadComidaAnimal(comidaAnimal);
                    almacenComida.setCantidadComidaVegetal(comidaVegetal);
                } else{
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida, comidaAnimal + comidaVegetal + comidaAnimalAlmacen + comidaVegetalAlmacen, almacenCentral);
                }
            }
        }
    }

    /**
     * Gestiona la lógica para alimentar a los peces cuando la comida es insuficiente.
     * @param cantidadDeComidaNecesariaPorPez Cantidad de comida que necesita cada Pez para alimentarse.
     * @param comidaDisponible Comida de la que se dispone para alimentar a los peces.
     */
    private void alimentarAleatorio(ArrayList<Integer> cantidadDeComidaNecesariaPorPez, Piscifactoria.AlmacenComida almacenComida, int comidaDisponible) {
        Random rt = new Random();
        ArrayList<Integer> posicionesPecesAlimentados = new ArrayList<>();
        int posicionAleatoria = 0;
        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();

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

        almacenComida.setCantidadComidaAnimal(comidaAnimal);
        almacenComida.setCantidadComidaVegetal(comidaVegetal);
    }

    /**
     * Gestiona la lógica para alimentar a los peces cuando la comida es insuficiente.
     * @param cantidadDeComidaNecesariaPorPez Cantidad de comida que necesita cada Pez para alimentarse.
     * @param comidaDisponible Comida de la que se dispone para alimentar a los peces.
     * @param almacenCentral Almacén central de donde se obtiene la comida cuando se agota la comida del tanque.
     */
    private void alimentarAleatorio(ArrayList<Integer> cantidadDeComidaNecesariaPorPez, Piscifactoria.AlmacenComida almacenComida, int comidaDisponible, AlmacenCentral almacenCentral) {
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
            almacenComida.setCantidadComidaAnimal(0);;
            almacenCentral.setCantidadComidaAnimal(comidaDisponible);
        } else {
            if (peces.get(0) instanceof Filtrador) {
                almacenComida.setCantidadComidaVegetal(0);;
                almacenCentral.setCantidadComidaVegetal(comidaDisponible);
               
            } else {
                if (almacenCentral.getCantidadComidaAnimal() > almacenCentral.getCantidadComidaVegetal()) {
                    almacenComida.setCantidadComidaAnimal(0);;
                    almacenComida.setCantidadComidaVegetal(0);;
                    almacenCentral.setCantidadComidaAnimal(0);
                    almacenCentral.setCantidadComidaVegetal(comidaDisponible);
                } else {
                    almacenComida.setCantidadComidaAnimal(0);
                    almacenComida.setCantidadComidaVegetal(0);
                    almacenCentral.setCantidadComidaVegetal(0);
                    almacenCentral.setCantidadComidaAnimal(comidaDisponible);
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
     * @param sistemaMonedas Sistema de monedas donde se realiza el ingreso de las monedas obtenidas.
     */
    private void venderPecesOptimos(SistemaMonedas sistemaMonedas){
        int pecesAVender = 0;
        Iterator<Pez> iterador = peces.iterator();
        
        while(iterador.hasNext()){
            Pez pez = iterador.next();

            if(pez.isEdadOptima()){
                pecesAVender++;
                iterador.remove();
            }
        }

        sistemaMonedas.setMonedas(sistemaMonedas.getMonedas() + (pecesAVender * AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas()));;
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
     * Elimina los peces muertos del tanque.
     */
    public void eliminarPecesMuertos(){
        Iterator<Pez> iterador = peces.iterator();

        while(iterador.hasNext()){
            Pez pez = iterador.next();

            if(!pez.isVivo()){
                iterador.remove();
            }
        }
    }

    /**
     * Implementa la lógica de que haya pasado un día haciendo crecer a los
     * peces, realizando la lógica de reproducción y vendiendo los peces que
     * están en la edad óptima.
     * @param sistemaMonedas Sistema de monedas donde se realiza el ingreso de las monedas obtenidas con las ventas.
     */
    public void nextDay(SistemaMonedas sistemaMonedas) {
        for(Pez pez : peces){
            pez.grow();
        }
        reproducir();
        venderPecesOptimos(sistemaMonedas);
    }
}
