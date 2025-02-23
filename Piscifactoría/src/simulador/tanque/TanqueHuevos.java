package simulador.tanque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulador.pez.Pez;
import simulador.piscifactoria.Piscifactoria;

public class TanqueHuevos extends Tanque{
    private static final int CAPACIDAD_MAXIMA = 25;
    private List<String> huevos; 
    private Piscifactoria piscifactoria;
    

    public TanqueHuevos(int numeroTanque, Piscifactoria piscifactoria) {
        super(numeroTanque, CAPACIDAD_MAXIMA);
        this.piscifactoria = piscifactoria;
        this.huevos = new ArrayList<>();
    }

    public boolean tieneEspacio() {
        return huevos.size() < CAPACIDAD_MAXIMA;
    }

    public void agregarHuevo(String nombrePez) {
        if (tieneEspacio()) {
            huevos.add(nombrePez);
        }
    }

    public List<String> getHuevos() {
        return huevos;
    }
  
    public void procesarHuevos() {
        List<Tanque> tanques = piscifactoria.getTanques();
        Iterator<String> iteradorHuevos = huevos.iterator();
    
        while (iteradorHuevos.hasNext()) {
            String nombrePez = iteradorHuevos.next(); 
    
            for (Tanque tanque : tanques) {
                if (!tanque.getPeces().isEmpty() && tanque.getPeces().size() < tanque.getCapacidadMaximaPeces()) {
                    
                    if (tanque.getPeces().get(0).getNombre().equals(nombrePez)) {
                       
                        Pez pezBase = tanque.getPeces().getFirst();
    
                        Pez nuevoPez = tanque.pecesMacho() >= tanque.pecesHembra() ? 
                                       pezBase.obtenerPezHija() : 
                                       pezBase.obtenerPezHijo();
    
                        tanque.getPeces().add(nuevoPez);
                        iteradorHuevos.remove(); 
    
                        System.out.println("Un huevo ha eclosionado y fue transferido al tanque " + tanque.getNumeroTanque());
    
                        break;
                    }
                }
            }
        }
    }
    
}
