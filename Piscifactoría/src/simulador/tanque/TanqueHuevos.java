package simulador.tanque;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import simulador.pez.Pez;
import simulador.piscifactoria.Piscifactoria;

public class TanqueHuevos extends Tanque{
    private static final int CAPACIDAD_MAXIMA = 25;
    private List<Pez> huevos; 
    private Piscifactoria piscifactoria;

    public TanqueHuevos(int numeroTanque, Piscifactoria piscifactoria) {
        super(numeroTanque, 25);
        this.piscifactoria = piscifactoria;
        this.huevos = new LinkedList<>();
    }

    public boolean tieneEspacio() {
        return huevos.size() < CAPACIDAD_MAXIMA;
    }

    public void agregarHuevo(Pez huevo) {
        if (tieneEspacio()) {
            huevos.add(huevo);
        }
    }

    public void procesarHuevos() {
        List<Tanque> tanques = piscifactoria.getTanques();
        List<Pez> huevosTransferidos = new LinkedList<>();
    
        for (Pez huevo : new ArrayList<>(huevos)) {
            for (Tanque tanque : tanques) {
                if (puedeRecibirPez(huevo)) {
                    tanque.getPeces().add(huevo); 
                    huevosTransferidos.add(huevo);
                    System.out.println("Huevo de " + huevo.getNombre() + " transferido al tanque " + tanque.getNumeroTanque());
                    break;
                }
            }
        }
        
        huevos.removeAll(huevosTransferidos); 
    }
    
    public boolean puedeRecibirPez(Pez pez) {
        // Verificar espacio
        if (getCapacidadMaximaPeces() - getPeces().size() <= 0) {
            return false;
        }
    
        // Verificar raza 
        if (!getPeces().isEmpty() && !getPeces().get(0).getNombre().equals(pez.getNombre())) {
            return false;
        }
    
        return true;
    }
    
}
