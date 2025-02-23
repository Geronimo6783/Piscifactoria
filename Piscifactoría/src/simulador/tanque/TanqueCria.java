package simulador.tanque;

import java.util.ArrayList;

import componentes.Transcripciones;
import propiedades.AlmacenPropiedades;

import simulador.pez.Pez;
import simulador.pez.carnivoro.Carnivoro;
import simulador.pez.filtrador.Filtrador;

import simulador.piscifactoria.Piscifactoria;

public class TanqueCria extends Tanque {

    private Piscifactoria piscifactoria;
    public static Transcripciones archivoTranscripcionesPartida;

    /**
     * Peces del tanque.
     */
    private ArrayList<Pez> peces;

    public TanqueCria(int numeroTanque, int capacidadMaximaPeces, Piscifactoria piscifactoria) {
        super(numeroTanque, 2);
        this.piscifactoria = piscifactoria;
        peces = new ArrayList<>();
    }

    public Piscifactoria getPiscifactoria() {
        return piscifactoria;
    }

    @Override
    public void alimentar(Piscifactoria.AlmacenComida almacenComida) {
        if (peces.size() != 2) {
            return; // Asegurar que solo hay dos peces en el tanque
        }

        int comidaAnimal = almacenComida.getCantidadComidaAnimal();
        int comidaVegetal = almacenComida.getCantidadComidaVegetal();
        int comidaNecesariaAnimal = 0;
        int comidaNecesariaVegetal = 0;

        for (Pez pez : peces) {
            if (!pez.isAlimentado()) {
                if (pez instanceof Carnivoro) {
                    comidaNecesariaAnimal += 2;
                } else if (pez instanceof Filtrador) {
                    comidaNecesariaVegetal += 2;
                } else { 
                    comidaNecesariaAnimal += 1;
                    comidaNecesariaVegetal += 1;
                }
            }
        }

        boolean hayComidaSuficiente = (comidaAnimal >= comidaNecesariaAnimal)
                && (comidaVegetal >= comidaNecesariaVegetal);

        if (hayComidaSuficiente) {
            comidaAnimal -= comidaNecesariaAnimal;
            comidaVegetal -= comidaNecesariaVegetal;
            for (Pez pez : peces) {
                if (!pez.isAlimentado()) {
                    pez.setAlimentado(true);
                }
            }
        }

        almacenComida.setCantidadComidaAnimal(comidaAnimal);
        almacenComida.setCantidadComidaVegetal(comidaVegetal);
    }

    /**
     * Compra la pareja de peces para el tanque.
     *
     * @param pezHembra Pez que debe ser hembra.
     * @param pezMacho  Pez que debe ser macho.
     * @return true si la pareja se agregó correctamente; false en caso contrario.
     */
    // public boolean comprarPareja(Pez pezHembra, Pez pezMacho) {

    //     if (!peces.isEmpty()) {
    //         return false;
    //     }

    //     if (!pezHembra.isSexo() || pezMacho.isSexo()) {
    //         return false;
    //     }

    //     peces.add(pezHembra);
    //     peces.add(pezMacho);
    //     return true;
    // }

    public void avanzarEdad(Piscifactoria.AlmacenComida almacenComida) {
        alimentar(almacenComida);

        for (Pez pez : peces) {
            if (pez.isAlimentado()) {
                pez.grow();

                pez.setAlimentado(false);
                pez.setVivo(true);
            }
        }
        reproducir();
    }

    private void reproducir() {
        // Verificar que exista la pareja
        if (peces.size() != 2) {
            return;
        }

        Pez pez1 = peces.get(0);
        Pez pez2 = peces.get(1);

        // Comprueba que ambos hayan sido alimentados y sean fértiles.
        if (!(pez1.isAlimentado() && pez2.isAlimentado() && pez1.isFertil() && pez2.isFertil())) {
            return;
        }

        String raza = pez1.getNombre();
       
        Tanque tanqueDestino = buscarTanquePorRaza(piscifactoria, raza);
        if (tanqueDestino == null) {
            System.out.println("No se encontró un tanque disponible para la raza: " + raza);
            return;
        }
        int espacioTanqueDestino = tanqueDestino.getCapacidadMaximaPeces() - tanqueDestino.getPeces().size();
        if (espacioTanqueDestino < 1) {
            return;
        }

        // Obtener el número de huevos definido para la especie.
        int huevos = AlmacenPropiedades.getPropByName(pez1.getNombre()).getHuevos();
        int totalHuevos = 2 * huevos;
        int huevosAProducir = Math.min(totalHuevos, espacioTanqueDestino);

        // Generar y agregar las crías al tanque destino.
        for (int i = 0; i < huevosAProducir; i++) {

            int machos = tanqueDestino.pecesMacho();
            int hembras = tanqueDestino.pecesHembra();

            Pez nuevaCria = (machos >= hembras) ? pez1.obtenerPezHija() : pez1.obtenerPezHijo();

            tanqueDestino.getPeces().add(nuevaCria);

        }

        archivoTranscripcionesPartida.registrarEnvioPecesTanqueCria(huevosAProducir, piscifactoria.getNombre());


        // Reiniciamos los indicadores reproductivos de la pareja.
        pez1.setFertil(false);
        pez2.setFertil(false);
        pez1.setDiasSinReproducirse(0);
        pez2.setDiasSinReproducirse(0);
    }

    private Tanque buscarTanquePorRaza(Piscifactoria piscifactoria, String raza) {
        for (Tanque tanque : piscifactoria.getTanques()) {
            if (!tanque.getPeces().isEmpty() && tanque.getPeces().get(0).getNombre().equals(raza)) {
                return tanque;
            }
        }
        return null;
    }

}