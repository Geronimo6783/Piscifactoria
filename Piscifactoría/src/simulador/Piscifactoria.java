package simulador;

import java.util.ArrayList;

public class Piscifactoria {
    private ArrayList<Tanque> tanques;
    private boolean tipoAgua;// true de mar, false de rio
    private Tanque tanqueInicial;
    private AlmacenCentral almacenInicial;
    private String nombre="";
    
   

    public Piscifactoria(ArrayList<Tanque> tanques, boolean tipoAgua, String nombre) {
        this.tanques.add(tanqueInicial);
        this.tipoAgua = tipoAgua;
        if(tipoAgua){
            this.tanqueInicial.setCapacidadMaximaPeces(100);
            this.almacenInicial.setCapacidadComidaAnimal(100);
            this.almacenInicial.setCapacidadComidaVegetal(100);
        }else{
            this.tanqueInicial.setCapacidadMaximaPeces(25);
            this.almacenInicial.setCapacidadComidaAnimal(25);
            this.almacenInicial.setCapacidadComidaVegetal(25);
        }
        this.nombre = nombre;
    }

    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    public Tanque getTanqueInicial() {
        return tanqueInicial;
    }

    public void setTanqueInicial(Tanque tanqueInicial) {
        this.tanqueInicial = tanqueInicial;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isTipoAgua() {
        return tipoAgua;
    }

    public void setTipoAgua(boolean tipoAgua) {
        this.tipoAgua = tipoAgua;
    }

    public AlmacenCentral getAlmacenInicial() {
        return almacenInicial;
    }

    public void setAlmacenInicial(AlmacenCentral almacenInicial) {
        this.almacenInicial = almacenInicial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void showStatus(){
        System.out.println("==============="+nombre+"===============\nTanques: "+tanques.size()+"\nOcupación:"+tanqueInicial.getPeces()+"/"+ tanqueInicial.getCapacidadMaximaPeces()+" "+ (x%)+"\nPeces vivos:"+ vivos / total (x%)+"\nPeces alimentados:"+alimentados / vivos (x%)+"\nPeces adultos:"+ adultos / vivos (x%)+"\nHembras / Machos:"+ H/M+"\nFértiles:"+ fértiles / vivos"\nAlmacén de comida:"+actual / max (x%));
    }

    public void showTankStatus(){
        for(int i=0; i<tanques.size();i++){
            tanques.get(i).showStatus();
        }
    }

    public void showFishStatus(){
        for(int i=0; i<tanques.size();i++){
            tanques.get(i).showFishStatus();
        }
    }

    public void showCapacity(){
        for(int i=0; i<tanques.size();i++){
            tanques.get(i).showCapacity(nombre);
        }
    }

    public void showFood(){
        System.out.println("============== Almacen ================\nComida animal: "+almacenInicial.getCantidadComidaAnimal()+""+"");
    }

    public void nextDay(){}

    public void sellFish(){}

    public void upgradeFood(){}
}
