package com.imatia.campusdual;

import com.imatia.campusdual.Controller.XMLHandler;
import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Details.TextStyle;
import com.imatia.campusdual.Objects.*;
import com.imatia.campusdual.RaceSimulators.DragsterSimulator;
import com.imatia.campusdual.RaceSimulators.EliminacionSimulator;
import com.imatia.campusdual.RaceSimulators.EstandarSimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    private static BufferedReader br;
    private static XMLHandler handler;
    public static void main(String[] args) {

        boolean repeat;
        br = new BufferedReader(new InputStreamReader(System.in));

        try{
            do {
                repeat = true;
                System.out.println(TextStyle.bold("Elije una opción: "));
                System.out.println("1-Crear nuevo registro " + Emojis.MAS_VERDE+
                        "\n2-Abrir registro existente " + Emojis.BANDERA+
                        "\n3-Cancelar " + Emojis.CHAU);
                String opcion = br.readLine();

                switch (opcion){

                    case "1":
                        crear();
                        break;

                    case "2":
                        abrir();
                        break;

                    case "3":
                        repeat=false;
                        br.close();
                        break;

                    default:
                        System.err.println("Opción desconocida. Por favor, inténtelo de nuevo");
                        break;
                }


            }while(repeat);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    private static void crear() {
        boolean repeat;
        do{
            repeat= false;
            try{
                System.out.println("Escribe el nombre del nuevo fichero sin la extensión(0 para cancelar)");
                String fichero = br.readLine().trim();
                fichero += ".xml";
                if(!fichero.equals("0.xml")) {
                    try {
                        File f = new File(fichero);
                        if (f.exists()) {
                            repeat = true;
                            System.err.println("El fichero ya existe. Por favor, inténtelo de nuevo");
                        } else {
                            f.createNewFile();
                            menu(fichero);
                        }

                    } catch (Exception e) {
                        System.err.println("El nombre del fichero no es válido. Inténtelo de nuevo");
                        repeat = true;
                    }
                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }while(repeat);
    }

    private static void abrir() {
        boolean repeat;
        do{
            repeat = false;
            try {
                System.out.println("Escribe la ruta del fichero sin la extensión: (0 para cancelar)");
                String fichero  = br.readLine().trim();
                fichero += ".xml";
                if(!fichero.trim() .equals("0.xml")){
                    try {
                        File f = new File(fichero);
                        if(f.exists())menu(fichero);
                        else{
                            System.err.println("El fichero " +fichero+ " no existe. Por favor, inténtelo de nuevo");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El nombre del fichero no es válido. Por favor, inténtelo de nuevo");
                        repeat = true;
                    }
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }while(repeat);
    }

    private static void menu(String fichero) {
        handler = new XMLHandler(fichero);
        boolean repeat;

        do {
            repeat=true;
            try {
                System.out.println(TextStyle.bold("Elije una opción"));
                System.out.println("1-Volver al menu de selección de fichero " +Emojis.RETORNO+
                        "\n2-Finalizar programa " +Emojis.CHAU+
                        "\n3-Gestionar garajes y vehículos " +Emojis.COCHE+
                        "\n4-Gestionar torneos y carreras "+Emojis.BANDERA);

                String op = br.readLine();
                op = op.toLowerCase().trim();
                switch (op) {
                    case "1":
                        repeat=false;
                        break;
                    case "2":
                        br.close();
                        System.exit(0);
                        break;
                    case "3":gestionarGarajes();
                        break;
                    case "4":gestionarTorneos();
                        break;
                    default:
                        System.err.println("Opción desconocida. Por favor, inténtelo de nuevo");
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }while(repeat);

    }

    private static void gestionarGarajes(){

        boolean repeat;

        do{
            repeat = true;

            System.out.println(TextStyle.bold("Elije una opción: "));
            System.out.println("1-Nuevo Garaje " + Emojis.MAS_VERDE + Emojis.EDIFICIO +
                    "\n2-Borrar Garaje" + Emojis.X_ROJA + Emojis.EDIFICIO +
                    "\n3-Editar Garaje (Cambiar nombre) " + Emojis.LAPIZ + Emojis.EDIFICIO+
                    "\n4-Nuevo coche " + Emojis.MAS_VERDE + Emojis.COCHE +
                    "\n5-Borrar coche " + Emojis.X_ROJA + Emojis.COCHE +
                    "\n6-Editar coche " + Emojis.LAPIZ + Emojis.COCHE +
                    "\n7-Listar coches " + Emojis.LISTA + Emojis.COCHE +
                    "\n8-Listar garajes " + Emojis.LISTA + Emojis.EDIFICIO +
                    "\n9-Volver al menu de gestion general " + Emojis.RETORNO );

            try{

                String op = br.readLine().trim();
                switch(op){
                    case "1":nuevoGaraje();
                        break;
                    case "2":borrarGaraje();
                        break;
                    case "3":editarGaraje();
                        break;
                    case "4":nuevoCoche();
                        break;
                    case "5":borrarCoche();
                        break;
                    case "6":editarCoche();
                        break;
                    case "7":listarCoches();
                        break;
                    case "8":listarGarajes();
                        break;
                    case "9":repeat = false;
                        break;
                    default:
                        System.out.println("Opción desconocida. Por favor, inténtelo de nuevo");
                        break;

                }

            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }while(repeat);
    }

    private static void listarCoches() {
        System.out.println(handler.showCoches());
    }

    private static void listarGarajes() {
        System.out.println(handler.showGarajes());
    }

    private static void editarCoche() {
        boolean repeat = true;

        do{
            System.out.println("Coches disponibles: " +
                    "\n"+handler.getAllCochesId());
            System.out.println("Escribe el id del coche a editar (0 para cancelar):");
            try{

                String aux = br.readLine().trim();
                if(!aux.equals("0")){
                    try{
                        int id = Integer.parseInt(aux);
                        if(handler.cocheExists(id)){
                            boolean repeat2;
                            do{
                                repeat2 = false;
                                Coche c = handler.getCocheById(id);
                                System.out.println(c.getMarca() + " " + c.getModelo() +
                                        "1-Cambiar marca\n" +
                                        "2-Cambiar modelo\n" +
                                        "3-Cancelar");

                                String op = br.readLine().trim();

                                switch (op){
                                    case "1":
                                        System.out.println("Escribe la nueva marca:");
                                        String marca = br.readLine().trim();
                                        if(handler.cocheExists(marca, c.getModelo())){
                                            System.err.println("El coche " + marca + " " + c.getModelo() + " ya existe.");
                                            repeat2 = true;
                                        }else{
                                            c.setMarca(marca);
                                            if(handler.updateCoche(c)){
                                                System.out.println("Coche modificado con éxito");
                                            }else System.err.println("No se ha podido modificar el coche");
                                        }
                                        handler.saveAll();
                                        break;
                                    case "2":
                                        System.out.println("Escribe la nueva marca:");
                                        String modelo = br.readLine().trim();
                                        if(handler.cocheExists(c.getMarca(), modelo)){
                                            System.err.println("El coche " + c.getMarca() + " " + modelo + " ya existe.");
                                            repeat2 = true;
                                        }else{
                                            c.setModelo(modelo);
                                            if(handler.updateCoche(c)){
                                                System.out.println("Coche modificado con éxito");
                                            }else System.err.println("No se ha podido modificar el coche");
                                        }
                                        handler.saveAll();
                                        break;
                                    case "3":
                                        break;
                                    default:
                                        System.err.println("Opción desconocida. Por favor, inténtelo de nuevo");
                                        repeat2 = true;
                                        break;
                                }
                            }while(repeat2);


                        }else System.err.println("El coche no existe");
                    }catch (Exception e){
                        System.err.println("Formato de id incorrecto");
                    }
                }else repeat = false;

            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }while (repeat);
    }

    private static void borrarCoche() {
        boolean repeat = true;

        do{
            System.out.println("Al borrar un coche borrará sus registros de carreras, y si se invalida una competición por falta de coches, también será eliminada.");
            System.out.println("Coches disponibles: " +
                    "\n"+handler.getAllCochesId());
            System.out.println("Escribe el id del coche a borrar (0 para cancelar):");
            try{

                String aux = br.readLine().trim();
                if(!aux.equals("0")){
                    try{
                        int id = Integer.parseInt(aux);
                        if(handler.cocheExists(id)){
                            if(handler.deleteCoche(id)) {
                                System.out.println("Coche Borrado con éxito;");
                                handler.saveAll();
                            }else System.out.println("No se ha borrado el coche;");
                            repeat=false;
                        }else System.err.println("El coche no existe");
                    }catch (Exception e){
                        System.err.println("Formato de id incorrecto");
                    }
                }else repeat = false;

            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }while (repeat);

    }

    private static void nuevoCoche() {
        boolean repeat = true;

        do{
            System.out.println("Garajes disponibles: " +
                    "\n"+handler.getAllGarajesId());
            System.out.println("Escribe el id del garaje a al que le quieres añadir un coche (0 para cancelar):");
            try{
                String aux = br.readLine().trim();
                if(!aux.equals("0")){
                    try{
                        int id = Integer.parseInt(aux);
                        if(handler.garajeExists(id)){

                            String marca="";
                            while(marca.equals("")){
                                System.out.println("Escribe la marca: ");
                                marca = br.readLine().trim();
                                if(marca.equals("")) System.err.println("La marca no puede estar vacía");
                            }

                            String modelo="";
                            while(modelo.equals("")){
                                System.out.println("Escribe la marca: ");
                                modelo = br.readLine().trim();
                                if(modelo.equals("")) System.err.println("El modelo no puede estar vacía");
                            }

                            if(handler.cocheExists(marca, modelo)) System.err.println("No se ha añadido, ya que el coche ya existe.");
                            else if(handler.addCocheToGaraje(new Coche(handler.getNewIdForCoche(), marca, modelo), id)){
                                System.out.println("Coche añadido con éxito");
                                handler.saveAll();
                            }
                            else System.err.println("No se a añadido el coche");
                        }else System.err.println("El garaje no existe");
                    }catch (Exception e){
                        e.printStackTrace();
                        System.err.println("Formato de id incorrecto");
                    }
                }else repeat = false;

            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }while (repeat);
    }

    private static void editarGaraje() {
        boolean repeat = true;

        do{
            System.out.println("Garajes disponibles: " +
                    "\n"+handler.getAllGarajesId());
            System.out.println("Escribe el id del garaje a al que le quieres añadir un coche (0 para cancelar):");
            try{
                String aux = br.readLine().trim();
                if(!aux.equals("0")){
                    try{
                        int id = Integer.parseInt(aux);
                        if(handler.garajeExists(id)){
                            Garaje garaje = new Garaje(handler.getGarageById(id));
                            System.out.println("Nombre actual: "+garaje.getNombre());
                            System.out.println("Escribe el nuevo nombre (0 para cancelar)");
                            String nombre = br.readLine().trim();
                            if(garaje.getNombre().toLowerCase().equals(nombre.toLowerCase()))System.err.println("El nombre es el mismo, no se ha cambiado.");
                            else if (handler.garajeExists(nombre)){
                                System.err.println("El nombre ya existe en otro garaje. No se ha cambiado.");
                                repeat = true;
                            }else{
                                garaje.setNombre(nombre);
                                handler.updateGaraje(garaje);
                                handler.saveAll();
                            }
                        }else System.err.println("El garaje no existe");
                    }catch (Exception e){
                        e.printStackTrace();
                        System.err.println("Formato de id incorrecto");
                    }
                }else repeat = false;

            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }while (repeat);
    }

    private static void borrarGaraje() {
        boolean repeat = true;

        do{
            System.out.println("Si borra un garaje, eliminara sus coches y por consiguiente las competiciones en las que ha participado.\nSi una competición queda invalidada por falta de coches, también será eliminada.");
            System.out.println("Garajes disponibles: " +
                    "\n"+handler.getAllGarajesId());
            System.out.println("Escribe el id del garaje a borrar (0 para cancelar):");
            try{
                String aux = br.readLine().trim();
                if(!aux.equals("0")){
                    try{
                        int id = Integer.parseInt(aux);
                        if(handler.garajeExists(id)){
                            if(handler.deleteGaraje(id)) {
                                System.out.println("Garaje Borrado con éxito;");
                                handler.saveAll();
                            }else System.out.println("No se ha borrado el garaje;");

                            repeat=false;
                        }else System.err.println("El garaje no existe");
                    }catch (Exception e){
                        e.printStackTrace();
                        System.err.println("Formato de id incorrecto");
                    }
                }else repeat = false;

            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }while (repeat);
    }

    private static void nuevoGaraje() {
        try{
            System.out.println("Escribe le nombre del nuevo garaje: (0 para cancelar)");
            String nombre = br.readLine().trim();
            if(!nombre.equals("0")){
                if(handler.garajeExists(nombre)) System.err.println("El garaje ya existe");
                else {
                    handler.addGaraje(new Garaje(handler.getNewIdForGaraje(), nombre));
                    System.out.println("Garaje guardado con éxito");
                    handler.saveAll();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static void gestionarTorneos(){
        boolean repeat;

        do{
            repeat = true;
            System.out.println(TextStyle.bold("Elije una opción"));
            System.out.println("1-Nuevo torneo " + Emojis.MAS_VERDE + Emojis.TROFEO +
                    "\n2-Gestionar torneo (crear carreras e información)" + Emojis.LAPIZ + Emojis.COCHE + Emojis.TROFEO +
                    "\n3-Mostrar torneos " + Emojis.LISTA + Emojis.TROFEO +
                    "\n4-Editar torneo (nombre) " + Emojis.LAPIZ + Emojis.TROFEO +
                    "\n5-Borrar torneo" + Emojis.X_ROJA + Emojis.TROFEO +
                    "\n6-Nuevo dragster " + Emojis.MAS_VERDE + Emojis.COCHE + Emojis.COCHE2 +
                    "\n7-Editar dragster (nombre) " + Emojis.LAPIZ + Emojis.COCHE + Emojis.COCHE2 +
                    "\n8-Borrar dragster " + Emojis.X_ROJA + Emojis.COCHE + Emojis.COCHE2 +
                    "\n9-Mostrar dragsters " + Emojis.LISTA + Emojis.COCHE + Emojis.COCHE2 +
                    "\n10-Volver al menu de gestion general "+ Emojis.RETORNO);

            try{

                String op = br.readLine().trim();
                switch(op){
                    case "1":nuevoTorneo();
                        break;
                    case "2":gestionarTorneo();
                        break;
                    case "3":mostrarTorneos();
                        break;
                    case "4":editarTorneo();
                        break;
                    case "5":borrarTorneo();
                        break;
                    case "6":nuevoDragster();
                        break;
                    case "7":editarDragster();
                        break;
                    case "8":borrarDragster();
                        break;
                    case "9":mostrarDragster();
                        break;
                    case "10":repeat = false;
                        break;
                    default:
                        System.out.println("Opción desconocida. Por favor, inténtelo de nuevo");
                        break;

                }

            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }while(repeat);
    }

    private static void borrarTorneo(){
        boolean repeat;
        try{
            do{
                repeat = false;
                System.out.println(handler.showTorneosShort());
                System.out.println("Escribe el id del torneo a borrar el nombre (0 para cancelar): ");
                String idA = br.readLine().trim();
                if(!idA.equals("0")){
                    try {
                        int id = Integer.parseInt(idA);
                        if(handler.torneoExists(id)){
                            handler.deleteTorneo(id);
                            System.out.println("Torneo borrado con éxito");
                            handler.saveAll();
                        }else{
                            System.err.println("El torneo no existe. Por favor, inténtelo de nuevo.");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un número entero. Por favor, inténtelo de nuevo.");
                        repeat = true;
                    }
                }
            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarDragster() {
        System.out.println(handler.showDragsters());
    }

    private static void borrarDragster() {
        boolean repeat;
        try{
        do{
            repeat = false;
            System.out.println("IDs de dragsters: " + handler.showDragstersIds());
            System.out.println("Escribe el id del dragster a borrar (0 para cancelar): ");
            String ids = br.readLine().trim();
            if(!ids.equals("0")){
                try {
                    int id = Integer.parseInt(ids);
                    if(handler.dragsterExists(id)){
                        if(handler.deleteDragster(id)){
                            handler.saveAll();
                            System.out.println("Dragster borrado con éxito.");
                        }else System.err.println("No se ha podido borrar el dragster.");
                    }else{
                        System.err.println("El dragster no existe. Por favor, inténtelo de nuevo;");
                        repeat = true;
                    }
                }catch (Exception e){
                    System.err.println("El id introducido no se corresponde con un número. Por favor, inténtelo de nuevo;");
                    repeat = true;
                }
            }
        }while(repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void editarDragster() {
        boolean repeat, repeat2;
        try{
            do{
                repeat = false;
                System.out.println("IDs de dragsters: " + handler.showDragstersIds());
                System.out.println("Escribe el id del dragster a modificar (0 para cancelar): ");
                String ids = br.readLine().trim();
                if(!ids.equals("0")){
                    try {
                        int id = Integer.parseInt(ids);
                        if(handler.dragsterExists(id)){
                            CarreraDragster cd = handler.getDragsterById(id);

                            do {
                                repeat2 = false;
                                System.out.println("Nombre actual: " + cd.getNombre());
                                System.out.println("Escribe el nuevo nombre del dragster: ");
                                String nombre = br.readLine().trim();

                                if(nombre.equals(cd.getNombre())){
                                    System.err.println("El nombre es el mismo, no se ha cambiado");
                                }else if(handler.dragsterExists(nombre)){
                                    System.err.println("El dragster " + nombre + " ya existe. Porfavor, inténtelo de nuevo.");
                                    repeat2 = true;
                                }else{
                                    cd.setNombre(nombre);
                                    if(handler.updateDragster(cd)){
                                        System.out.println("Dragster modificado con éxito");
                                        handler.saveAll();
                                    }else System.err.println("No se ha podido modificar el dragster.");
                                }
                            }while (repeat2);

                        }else{
                            System.err.println("El dragster no existe. Por favor, inténtelo de nuevo;");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un número. Por favor, inténtelo de nuevo;");
                        repeat = true;
                    }
                }
            }while(repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void nuevoDragster(){
        boolean repeat;

        try {
            do {
                repeat = false;
                System.out.println("Escribe el nombre del nuevo dragster: ");

                String nombreDragster = br.readLine().trim();

                if(!handler.dragsterExists(nombreDragster)){
                    iniciarDragster(nombreDragster);
                }else{
                    System.err.println("El nombre de ese dragster ya existe");
                    repeat = true;
                }
            }while (repeat);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }


    }

    private static void iniciarDragster(String nombreDragster) {
        ArrayList<Coche>coches = new ArrayList<>();
        boolean repeat, repeat2;
        try {
            do {
                repeat = false;
                int id, id2;
                System.out.println(handler.showCochesBasicInfo());
                System.out.println("Escribe el id del primer coche (0 para cancelar): ");
                String idA = br.readLine().trim();
                if(!idA.equals("0")){
                    try {
                        id = Integer.parseInt(idA);
                        if(handler.cocheExists(id)){
                            do{
                                repeat2 = false;

                                System.out.println("Escribe el id del segundo coche (0 para cancelar)");
                                try {
                                    idA = br.readLine().trim();
                                    if(!idA.equals("0")){
                                        try {
                                            id2 = Integer.parseInt(idA);
                                            if(handler.cocheExists(id2)){
                                                if(id != id2){
                                                    System.out.println("--------------------------------");
                                                    coches.add(handler.getCocheById(id));
                                                    coches.add(handler.getCocheById(id2));
                                                    DragsterSimulator dragsterSimulator = new DragsterSimulator(coches);
                                                    dragsterSimulator.startRace();
                                                    System.out.println("\nfin de la carrera");
                                                    System.out.println("--------------------------------");

                                                    CarreraDragster carreraDragster= new CarreraDragster(handler.getNewIdForDragster(), nombreDragster, coches, dragsterSimulator.getTiempos());
                                                    handler.addDragster(carreraDragster);
                                                    handler.saveAll();
                                                }else{
                                                    System.err.println("El coche ya esta participando en el dragster. Por favor, inténtelo de nuevo");
                                                    repeat2 = true;
                                                }
                                            }else{
                                                System.err.println("No existe ese coche. Por favor, inténtelo de nuevo");
                                                repeat2 = true;
                                            }
                                        }catch (Exception e){
                                            System.err.println("El id introducido no se corresponde con un número entero. Por favor, inténtelo de nuevo");
                                            repeat2 = true;
                                        }
                                    }
                                }catch (Exception e){
                                    System.err.println(e.getMessage());
                                    e.printStackTrace();
                                }
                            }while (repeat2);
                        }else {
                            System.err.println("No existe ese coche. Por favor, inténtelo de nuevo");
                        }
                    }catch (Exception e){
                        System.err.println("El id no se corresponde con un número entero. Por favor, inténtelo de nuevo");
                        repeat = true;
                    }
                }
            } while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }


    }

    private static void editarTorneo() {
        boolean repeat;
        try{
            do{
                repeat = false;
                System.out.println(handler.showTorneosShort());
                System.out.println("Escribe el id del torneo al que deseas cambiar el nombre (0 para cancelar): ");
                String idA = br.readLine().trim();
                if(!idA.equals("0")){
                    try {
                        int id = Integer.parseInt(idA);
                        if(handler.torneoExists(id)){
                            System.out.println("Escribe el nuevo nombre: ");
                            String nombre = br.readLine().trim();
                            if(handler.getTorneoById(id).getNombre().trim().equals(nombre)){
                                System.err.println("El nombre es el mismo. No se ha cambiado.");
                                repeat = true;
                            }else if(handler.torneoExists(nombre)){
                                System.err.println("Ya existe un torneo con ese nombre. No se ha cambiado.");
                                repeat = true;
                            }else{
                                Torneo torneo = handler.getTorneoById(id);
                                torneo.setNombre(nombre);
                                if(handler.updateTorneo(torneo)){
                                    handler.saveAll();
                                    System.out.println("Torneo modificado con éxito.");
                                }else System.err.println("No se ha podido modificar el torneo.");
                            }
                        }else{
                            System.err.println("El torneo no existe. Por favor, inténtelo de nuevo.");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un número entero. Por favor, inténtelo de nuevo.");
                        repeat = true;
                    }
                }
            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarTorneos() {
        System.out.println(handler.showTorneos());
    }

    private static void nuevoTorneo() {

        boolean posibleGarajeRace = handler.posibleGarajeRace();
        boolean posibleGeneralRace = handler.posibleGeneralRace();

        if(!(posibleGarajeRace || posibleGeneralRace)){
            System.err.println("No se puede crear ningún tipo de torneo por el momento ya que no existen suficientes coches.");
            return;
        }

        boolean repeat;
        do{
            repeat = false;
            System.out.println("Escribe el nombre del nuevo torneo (0 para cancelar):");
            try {
                String nombre = br.readLine().trim();
                if (!nombre.equals("0")){
                    if (handler.dragsterExists(nombre)) {
                        System.err.println("El torneo " + nombre + " ya existe");
                        repeat = true;
                    } else {
                        Torneo torneo = new Torneo(handler.getNewIdForTorneo(), nombre);
                        boolean repeat2;
                        do {
                            repeat2 = false;
                            System.out.println("Elije un tipo de torneo: " +
                                    "\n1-General (1 coche aleatorio de cada garaje)" +
                                    "\n2-De equipo (todos los coches de un garaje)" +
                                    "\n3-Cancelar creación de torneo");
                            String option = br.readLine().trim();
                            switch (option) {
                                case "1":
                                    if (posibleGeneralRace) {
                                        torneo.setCochesPart(handler.generateRandomCochesGeneralTorneo());
                                        handler.addTorneo(torneo);
                                        handler.saveAll();
                                    } else
                                        System.err.println("No es posible iniciar un torneo general, ya que no existen al menos 2 garajes con al menos 1 coche.");

                                    break;
                                case "2":
                                    if (posibleGarajeRace) {
                                        System.out.println("Garajes: " + handler.getAllGarajesId());
                                        System.out.println("Escribe el id del garaje que quiere realizar el torneo (0 para cancelar): ");
                                        String idGaraje = br.readLine().trim();
                                        if (idGaraje.equals("")) repeat2 = true;
                                        else {
                                            try {
                                                int id = Integer.parseInt(idGaraje);
                                                if (handler.garajeExists(id)) {
                                                    if(handler.posibleGarajeRaceInGaraje(id)){
                                                        torneo.setCochesPart(handler.generateCochesGarajeTorneo(id));
                                                        handler.addTorneo(torneo);
                                                        handler.saveAll();
                                                        System.out.println("Se ha creado el torneo con éxito. ID: " + torneo.getId());
                                                    }else{
                                                        System.err.println("Este garaje no cuenta con suficientes coches para realizar un torneo.");
                                                        repeat = true;
                                                    }
                                                } else {
                                                    System.err.println("El garaje no existe");
                                                    repeat2 = true;
                                                }
                                            } catch (Exception e) {
                                                System.err.println("El id no se corresponde con un número entero.");
                                                repeat2 = true;
                                            }
                                        }
                                    } else
                                        System.err.println("No es posible iniciar un torneo de garaje, ya que no existe ningún garaje con al menos 2 coches.");
                                    break;
                                case "3":
                                    break;
                                default:
                                    System.out.println("Opción desconocida. Por favor, inténtelo de nuevo.");
                                    repeat2 = true;
                            }
                        } while (repeat2);
                    }
            }

            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }while(repeat);
    }

    private static void gestionarTorneo() {
        boolean repeat;
        try{
            do{
                repeat = false;
                System.out.println(handler.showTorneosShort());
                System.out.println("Escribe el id del torneo a gestionar (0 para cancelar): ");
                String idA = br.readLine().trim();
                if(!idA.equals("0")){
                    try {
                        int id = Integer.parseInt(idA);
                        if(handler.torneoExists(id)){
                            gestionarTorneo(id);
                        }else{
                            System.err.println("El torneo no existe. Por favor, inténtelo de nuevo.");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un número entero. Por favor, inténtelo de nuevo.");
                        repeat = true;
                    }
                }
            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void gestionarTorneo(int id) {
        boolean repeat;

        try {
            do {
                repeat = true;
                System.out.println("\n" + handler.getTorneoById(id).toString());
                System.out.println(TextStyle.bold("\nElije una opción"));
                System.out.println("1-Crear carrera estándar" + Emojis.MAS_VERDE + Emojis.BANDERA + Emojis.COCHE +
                        "\n2-Crear carrera de eliminación" + Emojis.MAS_VERDE + Emojis.BANDERA + Emojis.CRONO +
                        "\n3-Borrar carrera" + Emojis.X_ROJA + Emojis.BANDERA +
                        "\n4-Editar carrera(nombre)" + Emojis.LAPIZ + Emojis.BANDERA +
                        "\n5-Ver toda la información del torneo" + Emojis.LISTA + Emojis.BANDERA +
                        "\n6-Volver al menu de gestión de carreras" + Emojis.RETORNO);

                String op = br.readLine().trim();
                switch (op){
                    case "1":
                        crearCarreraEstandar(id);
                        break;
                    case "2":
                        crearCarreraEliminacion(id);
                        break;
                    case "3":
                        borrarCarrera(id);
                        break;
                    case "4":
                        editarCarrera(id);
                        break;
                    case "5":
                        System.out.println(handler.showTorneo(id));
                        break;
                    case "6":
                        repeat = false;
                        break;
                    default:
                        System.err.println("Opción desconocida. Por favor, inténtelo de nuevo.");
                        break;

                }

            }while(repeat);


        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void crearCarreraEstandar(int id) {
        boolean repeat, repeat2;
        try {
            do {
                repeat = false;
                System.out.println("Escribe el nombre de la nueva Carrera Estándar: ");
                String nombre = br.readLine().trim();
                if(!handler.carreraExists(nombre)){
                    do{
                        repeat2 = false;
                        System.out.println("Cuanto debe durar la carrera (5-30): ");
                        String minA = br.readLine().trim();
                        try {
                            int min = Integer.parseInt(minA);
                            if(min < 5 || min >30){
                                System.err.println("Lo minutos deben ser como mínimo 5 y como máximo 30. Por favor, inténtelo de nuevo.");
                                repeat2 = true;
                            }else{
                                System.err.println(TextStyle.bold("Empieza la carrera"));
                                ArrayList<Coche>coches = handler.getCochesFromTorneo(id);
                                EstandarSimulator estandarSimulator = new EstandarSimulator(coches, min);
                                try {
                                    estandarSimulator.start();
                                    CarreraEstandar carreraEstandar= new CarreraEstandar(handler.getNewIdForCarrera(), nombre, min, estandarSimulator.getCochesAcabados(), estandarSimulator.getDistanciasDeCoches());
                                    handler.addCarreraToTorneo(carreraEstandar, id);
                                    handler.saveAll();
                                    System.out.println("\nSe ha guardado la carrera con éxito");
                                } catch (InterruptedException e) {
                                    System.err.println(e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }catch (Exception e){
                            System.err.println("Los minutos deben ser números enteros. Por favor, inténtelo de nuevo.");
                            repeat2 = true;
                        }
                    }while (repeat2);
                }else{
                    System.err.println("Ya existe una carrera con ese nombre. Por favor, inténtelo de nuevo. ");
                    repeat = true;
                }

            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void crearCarreraEliminacion(int id){
        boolean repeat, repeat2;
        try {
            do {
                repeat = false;
                System.out.println("Escribe el nombre de la nueva Carrera de Eliminación: ");
                String nombre = br.readLine().trim();
                if(!handler.carreraExists(nombre)){
                    do{
                        repeat2 = false;
                        System.out.println("Cada cuantos minutos se debe eliminar un coche (2-10): ");
                        String minA = br.readLine().trim();
                        try {
                            int min = Integer.parseInt(minA);
                            if(min < 2 || min >10){
                                System.err.println("Lo minutos deben ser como mínimo 2 y como máximo 10. Por favor, inténtelo de nuevo.");
                                repeat2 = true;
                            }else{
                                System.err.println(TextStyle.bold("Empieza la carrera"));
                                ArrayList<Coche>coches = handler.getCochesFromTorneo(id);
                                EliminacionSimulator eliminacionSimulator = new EliminacionSimulator(coches, min);
                                try {
                                    eliminacionSimulator.start();
                                    CarreraEliminacion carreraEliminacion= new CarreraEliminacion(handler.getNewIdForCarrera(), nombre, min, eliminacionSimulator.getCochesAcabados(), eliminacionSimulator.getDistanciasDeCoches());
                                    handler.addCarreraToTorneo(carreraEliminacion, id);

                                    handler.saveAll();
                                    System.out.println("\nSe ha guardado la carrera con éxito");
                                } catch (InterruptedException e) {
                                    System.err.println(e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }catch (Exception e){
                            System.err.println("Los minutos deben ser números enteros. Por favor, inténtelo de nuevo.");
                            repeat2 = true;
                        }
                    }while (repeat2);
                }else{
                    System.err.println("Ya existe una carrera con ese nombre. Por favor, inténtelo de nuevo. ");
                    repeat = true;
                }

            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void borrarCarrera(int idTorneo){
        boolean repeat;

        try {
            do{
                repeat = false;
                System.out.println(handler.getInfoCarreras(idTorneo));
                System.out.println("Escribe el id de la carrera a borrar (0 para cancelar):");
                String op = br.readLine().trim();
                if(!op.equals("0")){
                    try {
                        int id = Integer.parseInt(op);

                        if(handler.carreraExistsInTorneo(id, idTorneo)){
                            if(handler.deleteCarrera(id)){
                                System.out.println("Carrera borrada con éxito.");
                                handler.saveAll();
                            }
                            else System.err.println("No se ha podido borrar la carrera. ");
                        }else{
                            System.err.println("La carrera introducida no existe en el torneo. Por favor, inténtelo de nuevo. ");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un entero. Por favor, inténtelo de nuevo.");
                        repeat = true;
                    }
                }
            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void editarCarrera(int idTorneo){
        boolean repeat, repeat2;

        try {
            do{
                repeat = false;
                System.out.println(handler.getInfoCarreras(idTorneo));
                System.out.println("Escribe el id de la carrera a editar (0 para cancelar):");
                String op = br.readLine().trim();
                if(!op.equals("0")){
                    try {
                        int id = Integer.parseInt(op);
                        if(handler.carreraExistsInTorneo(id, idTorneo)){
                            do{
                                repeat2 = false;
                                Carrera carrera = handler.getCarreraById(id);
                                System.out.println("Nombre actual: "+carrera.getNombre());
                                System.out.println("Escribe el nuevo nombre (0 para cancelar):");
                                String nombre = br.readLine().trim();
                                if(!nombre.equals("0")){
                                    if(nombre.equals(carrera.getNombre())){
                                        System.err.println("El nombre introducido es el mismo al actual. Por favor, inténtelo de nuevo.");
                                        repeat2 = true;
                                    }else{
                                        if(handler.carreraExists(nombre)){
                                            System.err.println("Ya existe una carrera con ese nombre. Por favor, inténtelo de nuevo.");
                                            repeat2 = true;
                                        }else{
                                            carrera.setNombre(nombre);
                                            if(handler.updateCarrera(carrera)){
                                                System.out.println("Carrera modificada con éxito.");
                                                handler.saveAll();
                                            }else System.err.println("No se ha podido modificar la carrera. ");
                                        }
                                    }
                                }
                            }while (repeat2);
                        }else{
                            System.err.println("La carrera introducida no existe en el torneo. Por favor, inténtelo de nuevo. ");
                            repeat = true;
                        }
                    }catch (Exception e){
                        System.err.println("El id introducido no se corresponde con un entero. Por favor, inténtelo de nuevo.");
                        repeat = true;
                    }
                }
            }while (repeat);
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}