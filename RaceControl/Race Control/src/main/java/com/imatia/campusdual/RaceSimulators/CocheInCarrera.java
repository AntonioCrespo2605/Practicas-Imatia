package com.imatia.campusdual.RaceSimulators;
import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Objects.Coche;

import java.util.Random;

public class CocheInCarrera implements Runnable {
    private static final int VELOCIDAD_INICIAL = 50;
    private static final int VELOCIDAD_MAXIMA = 200;
    private static final int VELOCIDAD_MINIMA = 30;
    private int porCiclo;
    private Coche coche;
    private int velocidad;
    private Random random;
    private double distanciaTotal;
    private boolean isEstandar;

    public CocheInCarrera(Coche coche, int tiempoEliminacion) {
        this.coche = coche;
        this.velocidad = VELOCIDAD_INICIAL;
        this.random = new Random();
        this.porCiclo = 4 * tiempoEliminacion;
        this.distanciaTotal = 0;
        this.isEstandar = false;
    }

    public CocheInCarrera(Coche coche, int tiempoEliminacion, boolean estandar) {
        this.coche = coche;
        this.velocidad = VELOCIDAD_INICIAL;
        this.random = new Random();
        this.porCiclo = 4 * tiempoEliminacion;
        this.distanciaTotal = 0;
        this.isEstandar = estandar;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public Coche getCoche() {
        return coche;
    }

    @Override
    public void run() {
        if(distanciaTotal == 0)System.out.println("El coche " + coche.toString2() + " ha arrancado con una velocidad inical de " + velocidad + " km/h "+Emojis.PISTOLA);
        for (int i = 0; i < porCiclo; i++) {
            int aceleracion = random.nextInt(41) - 20;
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            distanciaTotal += ((velocidad * 100) / 24);
            int velocidadAnterior = velocidad;
            velocidad += aceleracion;
            if (velocidad > VELOCIDAD_MAXIMA) velocidad = VELOCIDAD_MAXIMA;
            else if (velocidad < VELOCIDAD_MINIMA) velocidad = VELOCIDAD_MINIMA;

            if(velocidad > velocidadAnterior) System.out.println("El coche " + coche.toString2() + " ha acelerado hasta " + velocidad + " km/h " + Emojis.ACELERACION);
            else if(velocidad < velocidadAnterior) System.out.println("El coche "+ coche.toString2() + " ha frenado. Ahora conduce a " + velocidad +" km/h " + Emojis.DECELERACION);
            else System.out.println("El coche " + coche.toString2() + " mantiene su velocidad de " + velocidad + " km/h");

        }
        if(!isEstandar)System.out.println("El coche " + coche.toString2() + " lleva " + distanciaTotal + " metros recorridos." + Emojis.COCHE);
    }
}
