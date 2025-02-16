package simulador.edificios;

import java.util.ArrayList;
import java.util.Random;

import simulador.Simulador;

/**
 * Clase que representa a una granja de langostinos que produce
 * comida animal.
 */
public class Langostinos implements Edificio{

    /**
     * Indica si la granja de langostinos está disponible.
     */
    private boolean disponible = false;

    /**
     * Número de peces muertos que tiene la granja.
     */
    private int muertos = 0;

    /**
     * Tanques de langostinos de la granja.
     */
    private ArrayList<TanqueLangostinos> tanques = new ArrayList<>();

    /**
     * Permite saber si está disponible la granja de langostinos.
     * @return Si está disponible la granja de langostinos.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Permite establecer si la granja de langostinos está disponible.
     * @param disponible Disponibilidad de la granja de langostinos a establecer.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Permite obtener el número de peces muertos que tiene la granja.
     * @return Número de peces muertos que tiene la granja.
     */
    public int getMuertos() {
        return muertos;
    }

    /**
     * Permite establecer el número de peces muertos que tiene la granja.
     * @param muertos Número de peces muertos que tiene la granja a establecer.
     */
    public void setMuertos(int muertos) {
        this.muertos = muertos;
    }

    /**
     * Gestiona la mejora de la granja de langostinos.
     */
    public void mejorar(){
        tanques.add(new TanqueLangostinos());
    }

    /**
     * Reparte la comida vegetal entre los tanques de langostinos.
     */
    public void repartirComidaVegetal(){
        int comidaTanque;
        int comidaAlmacenCentral = ((AlmacenCentral) Simulador.simulador.edificios[0]).getCantidadComidaVegetal();

        if(comidaAlmacenCentral >= 50 && !isTodosLosTanqueLlenos()){
            for(TanqueLangostinos tanque : tanques){
                comidaTanque = tanque.getComida();
                if(comidaTanque < 3 && comidaAlmacenCentral >= 50){
                    comidaAlmacenCentral -= 50;
                    comidaTanque++;
                    tanque.setComida(comidaTanque);
                }
            }
        }

        ((AlmacenCentral) Simulador.simulador.edificios[0]).setCantidadComidaVegetal(comidaAlmacenCentral);
    }

    /**
     * Permite obtener la comida animal generada por los tanques.
     * @return Comida animal generada por los tanques.
     */
    public int comidaGenerada(){
        int comidaGenerada = 0;
        Random rt = new Random();

        for(TanqueLangostinos tanque : tanques){
            if(tanque.getDescanso() == 0){
                comidaGenerada += rt.nextInt(100, 201);
            }
        }

        return comidaGenerada;
    }

    /**
     * Indica si todos los tanques están llenos.
     * @return Si todos los tanques están llenos o no.
     */
    private boolean isTodosLosTanqueLlenos(){
        for(TanqueLangostinos tanque : tanques){
            if(tanque.getComida() < 3){
                return false;
            }
        }

        return true;
    }

    /**
     * Gestiona la lógica de la granja de langostinos cuando pasa un día.
     */
    public void nextDay(){
        for(TanqueLangostinos tanque : tanques){
            tanque.nextDay();
        }
    }

    /**
     * Permite obtener el número de tanques de la granja de langostinos.
     * @return Número de tanques de la granja de langostinos.
     */
    public int getNumeroTanques(){
        return tanques.size();
    }

    /**
     * Clase que representa a un tanque de la granja
     * de langostinos.
     */
    private static class TanqueLangostinos {

        /**
         * Cantidad de comida para días de las que dispone el tanque.
         */
        private int comida = 0;

        /**
         * Número de días hasta que el tanque vuelva a producir alimento.
         */
        private int descanso = 3;

        /**
         * Permite obtener la cantidad de comida para días de las que dispone el tanque.
         * @return Cantidad de comida para días de las que dispone el tanque.
         */
        public int getComida() {
            return comida;
        }

        /**
         * Permite establecer la cantidad de comida para días de las que dispone el tanque.
         * @param comida Cantidad de comida para días de las que dispone el tanque a establecer.
         */
        public void setComida(int comida) {
            this.comida = comida;
        }

        /**
         * Permite obtener el número de días hasta que el tanque vuelva a producir alimento.
         * @return Número de días hasta que el tanque vuelva a producir alimento.
         */
        public int getDescanso() {
            return descanso;
        }

        /**
         * Permite establecer el número de días hasta que el tanque vuelva a producir alimento.
         * @param descanso Número de días hasta que el tanque vuelva a producir alimento a establecer.
         */
        public void setDescanso(int descanso) {
            this.descanso = descanso;
        }

        /**
         * Gestiona la lógica de pasar un días en el tanque.
         */
        public void nextDay(){
            int pecesMuertos = ((Langostinos) Simulador.simulador.edificios[2]).getMuertos();
            if(pecesMuertos > 0){
                ((Langostinos) Simulador.simulador.edificios[2]).setMuertos(--pecesMuertos);
                if(descanso > 0){
                    descanso--;
                }
            }
            else{
                if(comida > 0){
                    comida--;
                    if(descanso > 0){
                        descanso--;
                    }
                }
                else{
                    descanso++;
                }
            }
        }
    }
}
