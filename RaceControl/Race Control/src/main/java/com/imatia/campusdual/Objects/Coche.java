package com.imatia.campusdual.Objects;

public class Coche {
    private int id;
    private String marca;
    private String modelo;

    private static final int VELOCIDAD_MAX=200;

    public Coche(int id, String marca, String modelo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Coche(Coche coche) {
        this.id = coche.getId();
        this.marca = coche.getMarca();
        this.modelo = coche.getModelo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getVelocidadMax(){
        return VELOCIDAD_MAX;
    }

    @Override
    public String toString() {
        return "ID: "+getId()+" "+getMarca()+" "+getModelo();
    }

    public String toString2(){
        return getMarca() + " " + getModelo() + "(" + getId() + ")";
    }

}
