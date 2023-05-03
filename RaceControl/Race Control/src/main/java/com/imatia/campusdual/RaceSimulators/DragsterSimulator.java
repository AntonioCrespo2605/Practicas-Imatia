package com.imatia.campusdual.RaceSimulators;

import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Details.MyTimeFormat;
import com.imatia.campusdual.Objects.Coche;

import java.util.ArrayList;

public class DragsterSimulator {

    private ArrayList <Coche> coches;
    private int[]tiempos;

    public DragsterSimulator(ArrayList<Coche> coches){
        this.coches = coches;
        tiempos = new int[coches.size()];
    }

    public void startRace(){
        ArrayList<Thread>threads = new ArrayList<>();

        for(int i = 0; i < coches.size(); i++){
            Thread thread = new Thread(new CocheInDragster(coches.get(i), tiempos, i));
            threads.add(thread);
            thread.start();
        }

        try {
            for(Thread thread : threads){
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < coches.size(); i++) {
            System.out.println("El " + coches.get(i).toString2() + " ha tardado " + MyTimeFormat.fromMillisToSeconds(tiempos[i]) + " segundos "+Emojis.CRONO);
        }

        System.out.println("El ganador es " + coches.get(posWinner()).toString2() + Emojis.TROFEO);

    }

    public int[] getTiempos() {
        return tiempos;
    }

    private int posWinner(){
        int posMin = 0;
        int tiempoMin = tiempos[0];
        for (int i = 1; i < tiempos.length; i++) {
            if(tiempos[i]<tiempoMin){
                tiempoMin = tiempos[i];
                posMin = i;
            }
        }
        return posMin;
    }

}
