package simulador;

import simulador.pez.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import simulador.pez.carnivoro.*;
import simulador.pez.filtrador.*;
import simulador.piscifactoria.Piscifactoria;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

import java.util.Iterator;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a un tanque de una piscifactoría que contiene un número
 * de peces.
 */
@JsonAdapter(Tanque.AdaptadorJSON.class)
public class Tanque {

    /**
     * Número del tanque en la piscifactoría.
     */
    private int numeroTanque;

    /**
     * Capacidad máxima de peces que puede tener el tanque.
     */
    private transient int capacidadMaximaPeces;

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
     * Permite establecer el número del tanque.
     * @param numeroTanque Número del tanque a establecer.
     */
    public void setNumeroTanque(int numeroTanque){
        this.numeroTanque = numeroTanque;
    }

    /**
     * Constructor de tanques.
     * 
     * @param numeroTanque Número del tanque.
     * @param capacidadMaximaPeces Capacidad máxima de peces del tanque.
     */
    public Tanque(int numeroTanque, int capacidadMaximaPeces) {
        this.numeroTanque = numeroTanque;
        peces = new ArrayList<>();
        this.capacidadMaximaPeces = capacidadMaximaPeces;
    }

    /**
     * Constructor sin parámetros.
     */
    public Tanque(){
        numeroTanque = 0;
        capacidadMaximaPeces = 25;
    }

    /**
     * Imprime el estado del tanque por pantalla.
     */
    public void showStatus() {
        if (peces.size() != 0) {
            System.out.println(
                    "=============== Tanque " + numeroTanque + " ===============\nOcupación: " + peces.size() + " / "
                            + capacidadMaximaPeces
                            + "(" + String.format("%.2f", (((float) peces.size()) / (float) capacidadMaximaPeces) * 100)
                            + "%)\nPeces vivos: " + pecesVivos() + " / " + peces.size() + "("
                            + String.format("%.2f", (((float) pecesVivos() / (float) peces.size())) * 100)
                            + "%)\nPeces alimentados: " + pecesAlimentados() + " / " + peces.size() + "("
                            + String.format("%.2f", (((float) pecesAlimentados() / (float) peces.size())) * 100)
                            + "%)\nPeces adultos: " + pecesAdultos() + " / " + peces.size() + "("
                            + String.format("%.2f", (((float) pecesAdultos() / (float) peces.size())) * 100)
                            + "%)\nHembras / Machos: " + pecesHembra() + " / " + pecesMacho()
                            + "\nFértiles: " + pecesFertiles() + " / " + pecesVivos());
        } else {
            System.out.println(
                    "=============== Tanque " + numeroTanque + " ===============\nOcupación: " + peces.size() + " / "
                            + capacidadMaximaPeces
                            + "(" + String.format("%.2f", (((float) peces.size()) / (float) capacidadMaximaPeces) * 100)
                            + "%)\nPeces vivos: " + pecesVivos() + " / " + peces.size() + "(0,00%)\nPeces alimentados: "
                            + pecesAlimentados() + " / " + peces.size() + "(0,00%)\nPeces adultos: "
                            + pecesAdultos() + " / " + peces.size() + "(0,00%)\nHembras / Machos: "
                            + pecesHembra() + " / " + pecesMacho()
                            + "\nFértiles: " + pecesFertiles() + " / " + pecesVivos());
        }
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
     * @return Número de peces adultos en el tanque.
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
     * @return Número de peces adultos vivos en el tanque.
     */
    public int pecesAdultosVivos(){
        int pecesAdultos = 0;

        for(Pez pez : peces){
            if(pez.isVivo() && pez.isMaduro()){
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
                + String.format("%.2f", ((float) peces.size() / (float) capacidadMaximaPeces) * 100) + "% de capacidad.");
    }

    /**
     * Gestiona la lógica para alimentar a los peces.
     * @param almacenComida Almacén de comida de la piscifactoría donde se sitúa el tanque.
     */
    public void alimentar(Piscifactoria.AlmacenComida almacenComida) {
        int comidaNecesaria = 0;
        ArrayList<Integer> cantidadDeComidaNecesariaPorPez = new ArrayList<>();
        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();

        if (!Simulador.simulador.almacenCentral.isDisponible()) {
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
                } else {
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
                    } else {
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
                    } else {
                        alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida,
                                comidaAnimal + comidaVegetal);
                    }
                }
            }
        } else {
            int comidaAnimalAlmacen = Simulador.simulador.almacenCentral.getCantidadComidaAnimal();
            int comidaVegetalAlmacen = Simulador.simulador.almacenCentral.getCantidadComidaVegetal();

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
                    if (comidaAnimal < 0) {
                        Simulador.simulador.almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen + comidaAnimal);
                        comidaAnimal = 0;
                    }
                    for (Pez pez : peces) {
                        if (pez.isVivo() && !pez.isAlimentado()) {
                            pez.setAlimentado(true);
                        }
                    }

                    almacenComida.setCantidadComidaAnimal(comidaAnimal);
                } else {
                    alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida,
                            comidaAnimal + comidaAnimalAlmacen);
                }
            } else {
                if (peces.get(0) instanceof Filtrador) {
                    if ((comidaVegetal + comidaVegetalAlmacen) >= comidaNecesaria) {
                        comidaVegetal -= comidaNecesaria;
                        if (comidaVegetal < 0) {
                            Simulador.simulador.almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen + comidaVegetal);
                            comidaVegetal = 0;
                        }
                        for (Pez pez : peces) {
                            if (pez.isVivo() && !pez.isAlimentado()) {
                                pez.setAlimentado(true);
                            }
                        }

                        almacenComida.setCantidadComidaVegetal(comidaVegetalAlmacen);
                    } else {
                        alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida,
                                comidaVegetal + comidaVegetalAlmacen);
                    }
                } else {
                    if ((comidaVegetal + comidaAnimal + comidaAnimalAlmacen
                            + comidaVegetalAlmacen) >= comidaNecesaria) {
                        if (comidaVegetal + comidaAnimal >= comidaNecesaria) {
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
                        } else {
                            comidaNecesaria -= (comidaVegetal + comidaAnimal);
                            comidaVegetal = 0;
                            comidaAnimal = 0;

                            if (comidaVegetalAlmacen > comidaAnimalAlmacen) {
                                if (comidaVegetalAlmacen >= comidaNecesaria) {
                                    Simulador.simulador.almacenCentral
                                            .setCantidadComidaVegetal(comidaVegetalAlmacen - comidaNecesaria);
                                } else {
                                    comidaVegetalAlmacen -= comidaNecesaria;
                                    comidaAnimalAlmacen += comidaVegetal;
                                    comidaVegetal = 0;
                                    Simulador.simulador.almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen);
                                    Simulador.simulador.almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen);
                                }
                            } else {
                                if (comidaAnimalAlmacen >= comidaNecesaria) {
                                    Simulador.simulador.almacenCentral
                                            .setCantidadComidaAnimal(comidaAnimalAlmacen - comidaNecesaria);
                                } else {
                                    comidaAnimalAlmacen -= comidaNecesaria;
                                    comidaVegetalAlmacen += comidaAnimal;
                                    comidaAnimalAlmacen = 0;
                                    Simulador.simulador.almacenCentral.setCantidadComidaAnimal(comidaAnimalAlmacen);
                                    Simulador.simulador.almacenCentral.setCantidadComidaVegetal(comidaVegetalAlmacen);
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
                    } else {
                        alimentarAleatorio(cantidadDeComidaNecesariaPorPez, almacenComida,
                                comidaAnimal + comidaVegetal + comidaAnimalAlmacen + comidaVegetalAlmacen);
                    }
                }
            }
        }
    }

    /**
     * Gestiona la lógica para alimentar a los peces cuando la comida es insuficiente.
     * @param cantidadDeComidaNecesariaPorPez Cantidad de comida que necesita cada Pez para alimentarse.
     * @param almacenComida Almacén de comida de la piscifactoría donde se sitúa el tanque.
     * @param comidaDisponible Comida de la que se dispone para alimentar a los peces.
     */
    private void alimentarAleatorio(ArrayList<Integer> cantidadDeComidaNecesariaPorPez,
        Piscifactoria.AlmacenComida almacenComida, int comidaDisponible) {
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

        if (Simulador.simulador.almacenCentral == null) {
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
        } else {
            if (peces.get(0) instanceof Carnivoro) {
                almacenComida.setCantidadComidaAnimal(0);
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(comidaDisponible);
            } else {
                if (peces.get(0) instanceof Filtrador) {
                    almacenComida.setCantidadComidaVegetal(0);
                    Simulador.simulador.almacenCentral.setCantidadComidaVegetal(comidaDisponible);

                } else {
                    if (Simulador.simulador.almacenCentral.getCantidadComidaAnimal() > Simulador.simulador.almacenCentral
                            .getCantidadComidaVegetal()) {
                        almacenComida.setCantidadComidaAnimal(0);
                        almacenComida.setCantidadComidaVegetal(0);
                        Simulador.simulador.almacenCentral.setCantidadComidaAnimal(0);
                        Simulador.simulador.almacenCentral.setCantidadComidaVegetal(comidaDisponible);
                    } else {
                        almacenComida.setCantidadComidaAnimal(0);
                        almacenComida.setCantidadComidaVegetal(0);
                        Simulador.simulador.almacenCentral.setCantidadComidaVegetal(0);
                        Simulador.simulador.almacenCentral.setCantidadComidaAnimal(comidaDisponible);
                    }

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
     * Indica si hay un macho fértil en el tanque.
     * 
     * @return True si hay un macho fértil en el tanque.
     */
    private boolean hayMachoFertil() {
        for (Pez pez : peces) {
            if (!pez.isSexo() && pez.isFertil()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gestiona la lógica de reproducción de los peces del tanque.
     */
    private void reproducir() {
        int numeroHuevos = 0;
        int numeroHuevosPorHembra = AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getHuevos();

        if (hayMachoFertil() ) {
            for (Pez pez : peces) {
                if (pez.isSexo() && pez.isFertil()) {
                    pez.setFertil(false);
                    pez.setDiasSinReproducirse(0);
                    numeroHuevos += numeroHuevosPorHembra;
                }
            }

            while (peces.size() < capacidadMaximaPeces && numeroHuevos > 0) {
                if (pecesMacho() >= pecesHembra()) {
                    peces.add(peces.get(0).obtenerPezHija());
                } else {
                    peces.add(peces.get(0).obtenerPezHijo());
                }

                numeroHuevos--;
            }
        }
    }

    /**
     * Vende todos los peces que se encuentran en una edad óptima para ser vendidos.
     * 
     * @return Peces vendidos.
     */
    private int venderPecesOptimos() {
        if(!peces.isEmpty()){
            int pecesAVender = 0;
            Iterator<Pez> iterador = peces.iterator();
            String nombrePez = peces.get(0).getNombre();
            
            while(iterador.hasNext()){
                Pez pez = iterador.next();

                if (pez.isEdadOptima()) {
                    pecesAVender++;
                    Simulador.simulador.estadisticas.registrarVenta(nombrePez, AlmacenPropiedades.getPropByName(nombrePez).getMonedas());
                    iterador.remove();
                }
            }

            Simulador.simulador.sistemaMonedas.setMonedas(Simulador.simulador.sistemaMonedas.getMonedas()
                    + (pecesAVender * AlmacenPropiedades.getPropByName(peces.get(0).getNombre()).getMonedas()));
            
            return pecesAVender;
        }

        return 0;
    }

    /**
     * Vende todos los peces que estén maduros.
     */
    public void venderPeces(){
        if(!peces.isEmpty()){
            int monedasAObtener = 0;
            Iterator<Pez> iterador = peces.iterator();
            String nombrePez = peces.get(0).getNombre();
            int monedasPez = (AlmacenPropiedades.getPropByName(nombrePez).getMonedas() / 2);

            while (iterador.hasNext()) {
                Pez pez = iterador.next();

                if(pez.isMaduro() && !pez.isEdadOptima()){
                    monedasAObtener += monedasPez;
                    Simulador.simulador.estadisticas.registrarVenta(nombrePez, monedasPez);
                    iterador.remove();
                }
            }

            Simulador.simulador.sistemaMonedas.setMonedas(Simulador.simulador.sistemaMonedas.getMonedas() + monedasAObtener);
        }
    }

    /**
     * Elimina los peces muertos del tanque.
     */
    public void eliminarPecesMuertos() {
        Iterator<Pez> iterador = peces.iterator();

        while (iterador.hasNext()) {
            Pez pez = iterador.next();

            if (!pez.isVivo()) {
                iterador.remove();
            }
        }
    }

    /**
     * Implementa la lógica de que haya pasado un día haciendo crecer a los
     * peces, realizando la lógica de reproducción y vendiendo los peces que
     * están en la edad óptima.
     * @return Peces vendidos en el día.
     */
    public int nextDay() {
        if(!peces.isEmpty()){
            for (Pez pez : peces) {
                pez.grow();
            }
            reproducir();
            enfermar();
            return venderPecesOptimos();
        }

        return 0;
    }


    /**
     * Indica si hay un pez enfermo en el tanque.
     * 
     * @return True si hay un pez enfermo en el tanque.
     */
    private boolean hayPezEnfermo() {
        for (Pez pez : peces) {
            if (pez.isEnfermo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return Número de peces enfermos en el tanque.
     */
    public int pecesEnfermos() {
        int pecesEnfermos = 0;

        for (Pez pez : peces) {
            if (pez.isEnfermo()) {
                pecesEnfermos++;
            }
        }

        return pecesEnfermos;
    }

    /**
     * Indica si hay un pez muerto en el tanque.
     * 
     * @return True si hay un pez muerto en el tanque.
     */
    private boolean hayPezMuerto() {
        for (Pez pez : peces) {
            if (!pez.isVivo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gestiona la lógica de enfermar de los peces del tanque.
     */
    private void enfermar() {
        Random rt = new Random();
        if (hayPezMuerto() ) {
            for (Pez pez : peces) {
                if (!pez.isEnfermo()) {
                    boolean pezEnferma = (rt.nextInt( 100) > 5);
                    pez.setEnfermo(pezEnferma);
                }
            }
        }else if(hayPezEnfermo()){
            for (Pez pez : peces) {
                if (!pez.isEnfermo()) {
                    boolean pezEnferma = (rt.nextInt( 100) > 10);
                    pez.setEnfermo(pezEnferma);
                }
            }
        }
    }
    /**
     * Metodo que cura manualmente a los peces del tanque
     */
    public void curar(){
        for (Pez pez : peces) {
            pez.setEnfermo(false);
        }
    }

    /**
     * Elimina todos los peces de un tanque, independientemente de si 
     * están vivos o muertos.
     */
    public void vaciarTanque() {
        peces.clear();
    }

    /**
     * Devuelve un String con información relevante del tanque.
     * @return String con información relevante del tanque.
     */
    public String toString(){
        int numeroPeces = peces.size();
        return "Número tanque: " + numeroTanque + "\nCapacidad máxima peces: " + capacidadMaximaPeces + "\nNúmero de peces: " + numeroPeces
            + ((numeroPeces != 0) ? "\nPez: " + peces.get(0).getNombre() : "");
    }

    /**
     * Clase que se encarga de adaptar la serialización y la deserialización de objetos de la clase Tanque.
     */
    private class AdaptadorJSON implements JsonDeserializer<Tanque>, JsonSerializer<Tanque>{

        /**
         * Se encarga de la deserialización de un objeto Tanque.
         */
        @Override
        public Tanque deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Tanque tanque = new Tanque();
            tanque.peces = new ArrayList<Pez>();
            
            for(Pez pez : new Gson().fromJson(json.getAsJsonObject().get("peces"), Pez[].class)){
                tanque.peces.add(pez);
            }

            return tanque;
        }

        /**
         * Se encarga de la serialización de un objeto Tanque.
         */
        @Override
        public JsonElement serialize(Tanque src, Type typeOfSrc, JsonSerializationContext context) {
            int peces = (src.peces.isEmpty()) ? 0 : src.peces.size();
            Gson gson = new Gson();
            String pecesJson;

            if(peces != 0){
                pecesJson = " [";

                for(Pez pez : src.peces){
                    pecesJson += gson.toJson(pez,  Pez.class) + ",";
                }

                pecesJson = pecesJson.substring(0, pecesJson.length() - 1);
                pecesJson += " ]";
            }
            else{
                pecesJson = " [] ";
            }

            String json = "{ \"pez\" : \"" + ((peces != 0) ? src.peces.get(0).getNombre() + "\"" : "\"") + " , \"num\" : " + peces + " , \"datos\" : {"
            + " \"vivos\" : " + src.pecesVivos() + " , \"maduros\" : " + src.pecesAdultosVivos() + " , \"fertiles\" : " + src.pecesFertiles() + " , \"enfermos\" : " + src.pecesEnfermos() + " }" + ", \"peces\" : "
            + pecesJson + "}";
            return JsonParser.parseString(json);
        }

    }
}
