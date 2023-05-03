package com.imatia.campusdual.RaceSimulators;


import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Details.TextStyle;
import com.imatia.campusdual.Objects.Coche;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EliminacionSimulator {
    private List<CocheInCarrera> coches;
    private List<Thread> hilos;
    private List<Coche>cochesAcabados;
    private List<Double>distanciasDeCoches;

    public EliminacionSimulator(List<Coche> coches, int intervalo) {
        this.coches = new ArrayList<>();
        this.hilos = new ArrayList<>();
        this.cochesAcabados = new ArrayList<>();
        this.distanciasDeCoches = new ArrayList<>();

        for (Coche c : coches) {
            this.coches.add(new CocheInCarrera(c, intervalo));
        }
    }

    public void start() throws InterruptedException {
        int numCoches = coches.size();

        while (numCoches > 1) {
            CountDownLatch latch = new CountDownLatch(numCoches);

            for (CocheInCarrera c : coches) {
                Thread hilo = new Thread(() -> {
                    c.run();
                    latch.countDown();
                });
                hilos.add(hilo);
                hilo.start();
            }

            latch.await();

            Collections.sort(coches, (c1, c2) -> Double.compare(c2.getDistanciaTotal(), c1.getDistanciaTotal()));

            CocheInCarrera eliminado = coches.remove(coches.size() - 1);
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("El coche " + eliminado.getCoche().toString2() + " ha sido eliminado con una distancia final de " + eliminado.getDistanciaTotal() +" metros" + Emojis.X_ROJA);
            System.out.println("------------------------------------------------------------------------------------------");

            cochesAcabados.add(eliminado.getCoche());
            distanciasDeCoches.add(eliminado.getDistanciaTotal());

            numCoches--;
        }

        CocheInCarrera ganador = coches.get(0);
        cochesAcabados.add(ganador.getCoche());
        distanciasDeCoches.add(ganador.getDistanciaTotal());

        System.out.println(TextStyle.golden("El " + ganador.getCoche().toString2() + " ha ganado la carrera.") + Emojis.MEDALLA_ORO);

       imprimirResultados();
    }

    private void imprimirResultados(){
        System.out.println("------------------------------------------------------------------------------------------");
        for(int i = 0; i < cochesAcabados.size() ; i++){
            System.out.println(cochesAcabados.get(i) + " ha recorrido " + distanciasDeCoches.get(i) + " metros");
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public List<Coche> getCochesAcabados() {
        return cochesAcabados;
    }

    public List<Double> getDistanciasDeCoches() {
        return distanciasDeCoches;
    }
}
