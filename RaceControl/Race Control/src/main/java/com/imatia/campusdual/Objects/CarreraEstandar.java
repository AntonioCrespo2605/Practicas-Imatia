package com.imatia.campusdual.Objects;

import java.util.ArrayList;
import java.util.List;

public class CarreraEstandar extends Carrera {
    private int minutos;
    private ArrayList<RegistroCarrera> registros;

    public CarreraEstandar(int id, String nombre, int minutos, ArrayList<RegistroCarrera> registros) {
        super(id, nombre);
        this.minutos = minutos;
        this.registros = registros;
    }

    public CarreraEstandar(int id, String nombre, int minutos) {
        super(id, nombre);
        this.minutos = minutos;
        this.registros = new ArrayList<>();
    }

    public CarreraEstandar(int id, String nombre, int minutos, List<Coche> coches, List<Double>distancias){
        super(id, nombre);
        this.minutos = minutos;

        ArrayList<RegistroCarrera> registros = new ArrayList<>();
        for(int i = 0; i < coches.size(); i++){
            RegistroCarrera registro = new RegistroCarrera(coches.get(i).getId(), distancias.get(i));
            registros.add(registro);
        }

        this.registros = registros;

    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public ArrayList<RegistroCarrera> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<RegistroCarrera> registros) {
        this.registros = registros;
    }

    public boolean deleteRegistro(int idCoche){
        for (int i = 0; i < this.registros.size(); i++) {
            if(this.registros.get(i).getCoche() == idCoche){
                this.registros.remove(i);
                return true;
            }
        }
        return false;
    }
}
