package com.imatia.campusdual.RaceSimulators;

import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Details.TextStyle;
import com.imatia.campusdual.Objects.Coche;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EstandarSimulator {
    private List<CocheInCarrera> coches;
    private List<Thread> hilos;
    private List<Coche>cochesAcabados;
    private List<Double>distanciasDeCoches;

    public EstandarSimulator(List<Coche> coches, int tiempo) {
        this.coches = new ArrayList<>();
        this.hilos = new ArrayList<>();
        this.cochesAcabados = new ArrayList<>();
        this.distanciasDeCoches = new ArrayList<>();

        for (Coche c : coches) {
            this.coches.add(new CocheInCarrera(c, tiempo, true));
        }
    }

    public void start() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(coches.size());

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

        for(int i = 0; i<coches.size(); i++){
            cochesAcabados.add(coches.get(i).getCoche());
            distanciasDeCoches.add(coches.get(i).getDistanciaTotal());
        }

        CocheInCarrera ganador = coches.get(0);

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
