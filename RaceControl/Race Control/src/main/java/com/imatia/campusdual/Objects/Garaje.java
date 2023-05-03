package com.imatia.campusdual.Objects;

import java.util.ArrayList;

public class Garaje {
    private int id;
    private String nombre;
    private ArrayList<Coche>coches;

    public Garaje(int id, String nombre, ArrayList<Coche> coches) {
        this.id = id;
        this.nombre = nombre;
        this.coches = coches;
    }

    public Garaje(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.coches = new ArrayList<>();
    }

    public Garaje(Garaje garaje){
        this.id = garaje.getId();
        this.nombre = garaje.getNombre();
        this.coches = garaje.getCoches();
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

    public ArrayList<Coche> getCoches() {
        return coches;
    }

    public void setCoches(ArrayList<Coche> coches) {
        this.coches = coches;
    }

    public void addCoche(Coche coche){
        this.coches.add(coche);
    }

    public boolean deleteCoche(int id){
        for (int i = 0; i < this.getCoches().size(); i++) {
            if(this.getCoches().get(i).getId() == id){
                this.getCoches().remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Garaje "+getId()+" "+getNombre()+". Tot coches: "+getCoches().size()+".";
    }

    public String toString2(){
        return getId() + " " + getNombre();
    }
}
