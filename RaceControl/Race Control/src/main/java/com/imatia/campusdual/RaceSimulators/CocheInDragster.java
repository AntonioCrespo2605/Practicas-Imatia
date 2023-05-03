package com.imatia.campusdual.RaceSimulators;

import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Objects.Coche;

import java.util.Random;

public class CocheInDragster implements Runnable {

    private Coche coche;
    private int[] tiempos;
    private int posicion;

    public CocheInDragster(Coche coche, int[]tiempos, int posicion){
        this.coche = coche;
        this.tiempos = tiempos;
        this.posicion = posicion;
    }

    @Override
    public void run() {
        Random r = new Random();
        int tiempoR = r.nextInt(8000) + 7000;

        System.out.println("El coche " + coche.toString2() + " ha arrancado "+ Emojis.PISTOLA);
        try {
            Thread.sleep(tiempoR);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El coche " + coche.toString2() + " ha llegado a la meta "+Emojis.BANDERA);

        synchronized (tiempos){
            tiempos[posicion] = tiempoR;
        }
    }
}
