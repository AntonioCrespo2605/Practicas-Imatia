package com.imatia.campusdual.Objects;

public class RegistroCarrera {
    private int coche;
    private double distTot;

    public RegistroCarrera(int coche, double distTot) {
        this.coche = coche;
        this.distTot = distTot;
    }

    public int getCoche() {
        return coche;
    }

    public void setCoche(int coche) {
        this.coche = coche;
    }

    public double getDistTot() {
        return distTot;
    }

    public void setDistTot(double distTot) {
        this.distTot = distTot;
    }


}
