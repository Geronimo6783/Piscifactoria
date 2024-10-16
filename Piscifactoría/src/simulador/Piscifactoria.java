package simulador;

import java.util.ArrayList;

import propiedades.AlmacenPropiedades;

public class Piscifactoria {
    private ArrayList<Tanque> tanques;
    private boolean tipoAgua;// true de mar, false de rio
    private Tanque tanqueInicial;
    private AlmacenCentral almacenInicial;
    private String nombre = "";

    /**
     * Constructor de Piscifactoria que diferencia entre si el primer tanque es de mar o de rio
     * @param tanques
     * @param tipoAgua
     * @param nombre
     */
    public Piscifactoria(ArrayList<Tanque> tanques, boolean tipoAgua, String nombre) {
        this.tanques.add(tanqueInicial);
        this.tipoAgua = tipoAgua;
        if (tipoAgua) {
            this.tanqueInicial.setCapacidadMaximaPeces(100);
            this.almacenInicial.setCapacidadComidaAnimal(100);
            this.almacenInicial.setCapacidadComidaVegetal(100);
        } else {
            this.tanqueInicial.setCapacidadMaximaPeces(25);
            this.almacenInicial.setCapacidadComidaAnimal(25);
            this.almacenInicial.setCapacidadComidaVegetal(25);
        }
        this.nombre = nombre;
    }

    /**
     * Devuelve los tanques de la piscifactoria
     * @return tanques
     */
    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    /**
     * Establece el ArrayList de tanques de la piscifactoria
     * @param tanques
     */
    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    /**
     * Devuelve el tanque inicial de la piscifactoria
     * @return 
     */
    public Tanque getTanqueInicial() {
        return tanqueInicial;
    }

    /**
     * Establece el tanque inicial de la piscifactoria
     * @param tanqueInicial
     */
    public void setTanqueInicial(Tanque tanqueInicial) {
        this.tanqueInicial = tanqueInicial;
    }

    /**
     * Devuelve el nombre de la piscifactoria
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el tipo de agua de la piscifactoria
     * @return
     */
    public boolean isTipoAgua() {
        return tipoAgua;
    }

    /**
     * Establece el tipo de agua de la piscifactoria
     * @param tipoAgua
     */
    public void setTipoAgua(boolean tipoAgua) {
        this.tipoAgua = tipoAgua;
    }

    /**
     * devuelve el almacen inicial de la piscifactoria
     * @return
     */
    public AlmacenCentral getAlmacenInicial() {
        return almacenInicial;
    }

    /**
     * Establece el almacen inicial de la piscifactoria
     * @param almacenInicial
     */
    public void setAlmacenInicial(AlmacenCentral almacenInicial) {
        this.almacenInicial = almacenInicial;
    }

    /**
     * Establece el nombre de la piscifactoria
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que muestra el estado de la piscifactoria y todos sus tanques
     */
    public void showStatus() {
        System.out.println("===============" + nombre +
                "===============\nTanques: " + tanques.size() + datosTanques());
    }

    /**
     * Metodo auxiliar de showStatus que realiza los calculos de cantidades y porcentajes para devolver como string el resto del mensaje
     */
    private String datosTanques() {
        int peces = 0;
        int capacidad = 0;
        int vivos = 0;
        int alimentados = 0;
        int adultos = 0;
        int hembras = 0;
        int machos = 0;
        int fertiles = 0;
        for (int i = 0; i < tanques.size(); i++) {
            peces += tanques.get(i).getPeces().size();
            capacidad += tanques.get(i).getCapacidadMaximaPeces();
            vivos += tanques.get(i).pecesVivos();
            alimentados += tanques.get(i).pecesAlimentados();
            adultos += tanques.get(i).pecesAdultos();
            hembras += tanques.get(i).pecesHembra();
            machos += tanques.get(i).pecesMacho();
            fertiles += tanques.get(i).pecesFertiles();
            }
        
        return "\nOcupación: " + peces + "/" + capacidad + " " + "(" + (peces / capacidad) * 100 + "%)" +
                "\nPeces vivos: " + vivos + "/" + peces + "(" + (vivos / peces) * 100 + "%)" +
                "\nPeces alimentados: " + alimentados + "/" + vivos + "(" + (alimentados / vivos) * 100 + "%)" +
                "\nPeces adultos: " + adultos + "/" + vivos + "(" + (adultos / vivos) * 100 + "%)" +
                "\nHembras / Machos: " + hembras + "/" + machos +
                "\nFértiles: " + fertiles + "/" + vivos + "(" + (fertiles / vivos) * 100 + "%)" +
                "\nAlmacén de comida: \n\t-comida carnivoros: " + almacenInicial.getCantidadComidaAnimal() + "/"
                + almacenInicial.getCapacidadComidaAnimal() + "("
                + (almacenInicial.getCantidadComidaAnimal() / almacenInicial.getCapacidadComidaAnimal()) * 100 + "%)"
                + "\n\t-comida vegetal: " + almacenInicial.getCantidadComidaVegetal() + "/"
                + almacenInicial.getCapacidadComidaVegetal() + "("
                + (almacenInicial.getCantidadComidaVegetal() / almacenInicial.getCapacidadComidaVegetal()) * 100 + "%)";
    }

    /**
     * Metodo que muestra el estado de los tanques
     */
    public void showTankStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showStatus();
        }
    }

    /**
     * Metodo que muestra el estado de los peces de los tanques
     */
    public void showFishStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showFishStatus();
        }
    }

    /**
     * Metodo que muestra la capacidad de los tanques
     */
    public void showCapacity() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showCapacity(nombre);
        }
    }

    /**
     * Metodo que muestra por mensaje el estado del Almacen
     */
    public void showFood() {
        System.out.println("============== Almacen ================\nComida animal: "
                + almacenInicial.getCantidadComidaAnimal() + "/" + almacenInicial.getCapacidadComidaAnimal() +
                "(" + (almacenInicial.getCantidadComidaAnimal() / almacenInicial.getCapacidadComidaAnimal()) * 100
                + "%)" +
                "\nComida vegetal: " + almacenInicial.getCantidadComidaVegetal() + "/"
                + almacenInicial.getCapacidadComidaVegetal() +
                "(" + (almacenInicial.getCantidadComidaVegetal() / almacenInicial.getCapacidadComidaVegetal()) * 100
                + "%)");
    }

    public void nextDay() {

    }

    /**
     * Metodo que vende los peces maduros y vivos de los tanques devolviendo el valor de monedas conseguidas.
     * @return Cantidad de monedas ganadas de la venta 
     */
    public int sellFish() {
        int monedasGanadas=0;
        for (int i = 0; i < tanques.size(); i++) {
            monedasGanadas=+tanques.get(i).venderPeces();
        }
        return monedasGanadas;
    }
    
    /**
     * Metodo que mejora el almacen
     */
    public void upgradeFood() {
        almacenInicial.mejorar();
        System.out.println("Almacén de la piscifactoria " + nombre
                + " mejorado. Su capacidad ha aumentado en 50 hasta un total de "
                + almacenInicial.getCapacidadComidaAnimal());
    }
}