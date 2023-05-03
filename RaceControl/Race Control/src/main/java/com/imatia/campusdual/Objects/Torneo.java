package com.imatia.campusdual.Objects;



import java.util.ArrayList;

public class Torneo {
    private int id;
    private String nombre;
    private ArrayList<Carrera>carreras;
    private ArrayList<Integer>cochesPart;

    public Torneo(int id, String nombre, ArrayList<Carrera> carreras, ArrayList<Integer> cochesPart) {
        this.id = id;
        this.nombre = nombre;
        this.carreras = carreras;
        this.cochesPart = cochesPart;
    }

    public Torneo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.carreras = new ArrayList<>();
        this.cochesPart = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<Carrera> carreras) {
        this.carreras = carreras;
    }

    public ArrayList<Integer> getCochesPart() {
        return cochesPart;
    }

    public void setCochesPart(ArrayList<Integer> cochesPart) {
        this.cochesPart = cochesPart;
    }

    public void addCochePart(int id){
        this.cochesPart.add(id);
    }

    public void addCarrera(Carrera carrera){
        this.carreras.add(carrera);
    }

    @Override
    public String toString() {
        return "Torneo " + getId() + ": " + getNombre();
    }
}
