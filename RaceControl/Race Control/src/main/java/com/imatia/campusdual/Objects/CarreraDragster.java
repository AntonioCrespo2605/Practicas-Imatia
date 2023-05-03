package com.imatia.campusdual.Objects;

import java.util.ArrayList;

public class CarreraDragster extends Carrera{
    private int coche1, coche2;
    private int millisC1, millisC2;
    public CarreraDragster(int id, String nombre, int idC1, int idC2, int tC1, int tC2) {
        super(id, nombre);
        this.coche1 = idC1;
        this.coche2 = idC2;
        this.millisC1 = tC1;
        this.millisC2 = tC2;
    }

    public CarreraDragster(int id, String nombre, ArrayList<Coche> coches, int[]tiempos){
        super(id, nombre);
        this.coche1 = coches.get(0).getId();
        this.coche2 = coches.get(1).getId();
        this.millisC1 = tiempos[0];
        this.millisC2 = tiempos[1];
    }

    public int getCoche1() {
        return coche1;
    }

    public void setCoche1(int coche1) {
        this.coche1 = coche1;
    }

    public int getCoche2() {
        return coche2;
    }

    public void setCoche2(int coche2) {
        this.coche2 = coche2;
    }

    public int getMillisC1() {
        return millisC1;
    }

    public void setMillisC1(int millisC1) {
        this.millisC1 = millisC1;
    }

    public int getMillisC2() {
        return millisC2;
    }

    public void setMillisC2(int millisC2) {
        this.millisC2 = millisC2;
    }
}
