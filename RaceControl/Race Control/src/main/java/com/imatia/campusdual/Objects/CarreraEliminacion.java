package com.imatia.campusdual.Objects;

import java.util.ArrayList;
import java.util.List;

public class CarreraEliminacion extends Carrera {
    private int intervalo;
    private ArrayList<RegistroCarrera>registros;
    public CarreraEliminacion(int id, String nombre, int intervalo, ArrayList<RegistroCarrera> registros) {
        super(id, nombre);
        this.intervalo = intervalo;
        this.registros = registros;
    }

    public CarreraEliminacion(int id, String nombre, int intervalo) {
        super(id, nombre);
        this.intervalo = intervalo;
        this.registros = new ArrayList<>();
    }
    
    public CarreraEliminacion(int id, String nombre, int intervalo, List<Coche> coches, List<Double>distancias){
        super(id, nombre);
        this.intervalo = intervalo;

        ArrayList<RegistroCarrera> registros = new ArrayList<>();
        for(int i = 0; i < coches.size(); i++){
            RegistroCarrera registro = new RegistroCarrera(coches.get(i).getId(), distancias.get(i));
            registros.add(registro);
        }

        this.registros = registros;
        
    }

    public int getintervalo() {
        return intervalo;
    }

    public void setintervalo(int intervalo) {
        this.intervalo = intervalo;
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
