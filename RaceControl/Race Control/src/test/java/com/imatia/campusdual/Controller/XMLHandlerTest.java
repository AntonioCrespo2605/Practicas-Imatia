package com.imatia.campusdual.Controller;

import com.imatia.campusdual.Objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLHandlerTest {
    XMLHandler handler;

    @BeforeEach
    void initiateHandler() {
        handler = new XMLHandler();
    }

    /******************************************************************************************************************/
    //comprobar si existen objetos
    @Test
    void garajeExistsTest() {
        handler.addGaraje(new Garaje(1, "Ferrari"));
        assertTrue(handler.garajeExists(1));
        assertFalse(handler.garajeExists(2));
        assertTrue(handler.garajeExists("Ferrari"));
        assertFalse(handler.garajeExists("Lamborghini"));
    }

    @Test
    void cocheExistsTest() {
        handler.addGaraje(new Garaje(1, "Opel"));
        handler.addCocheToGaraje(new Coche(3, "Opel", "Corsa"), 1);

        assertTrue(handler.cocheExists(3));
        assertFalse(handler.cocheExists(1));
        assertTrue(handler.cocheExists("Opel", "Corsa"));
        assertFalse(handler.cocheExists("Opel", "Astra"));
    }

    @Test
    void torneoExistsTest() {
        handler.addTorneo(new Torneo(4, "Torneo 1"));
        assertTrue(handler.torneoExists(4));
        assertFalse(handler.torneoExists(2));
        assertTrue(handler.torneoExists("Torneo 1"));
        assertFalse(handler.torneoExists("Torneo 2"));
    }

    @Test
    void carreraExistsTest() {
        handler.addTorneo(new Torneo(1, "Torneo 1"));
        handler.addTorneo(new Torneo(2, "Torneo 1"));

        CarreraEliminacion ce = new CarreraEliminacion(2, "carrera 1", 1);
        handler.addCarreraToTorneo(ce, 1);

        assertTrue(handler.carreraExists(2));
        assertFalse(handler.carreraExists(3));
        assertTrue(handler.carreraExists("carrera 1"));
        assertFalse(handler.carreraExists("carrera 2"));

        assertTrue(handler.carreraExistsInTorneo(2, 1));
        assertFalse(handler.carreraExistsInTorneo(2, 2));
    }

    @Test
    void dragsterExistsTest() {
        handler.addDragster(new CarreraDragster(1, "dragster 1", 1, 2, 1, 2));
        assertTrue(handler.dragsterExists(1));
        assertFalse(handler.dragsterExists(5));
        assertTrue(handler.dragsterExists("dragster 1"));
        assertFalse(handler.dragsterExists("dragster 2"));
    }

    @Test
    void carreraExistsInTorneoTest(){
        handler.addTorneo(new Torneo(1, "Torneo1"));
        handler.addTorneo(new Torneo(2, "Torneo2"));
        handler.addCarreraToTorneo(new CarreraEstandar(1, "c1", 3), 1);
        handler.addCarreraToTorneo(new CarreraEstandar(2, "c2", 5), 2);

        assertTrue(handler.carreraExistsInTorneo(1, 1));
        assertFalse(handler.carreraExistsInTorneo(2, 1));
    }

    /******************************************************************************************************************/
    //nuevos ids:
    @Test
    void newIdGarajeTest() {
        handler.addGaraje(new Garaje(1, "a"));
        handler.addGaraje(new Garaje(2, "b"));
        handler.addGaraje(new Garaje(4, "c"));

        assertEquals(3, handler.getNewIdForGaraje());
    }

    @Test
    void newIdCocheTest() {
        handler.addGaraje(new Garaje(1, "Opel"));
        handler.addCocheToGaraje(new Coche(1, "Opel", "Corsa"), 1);
        handler.addCocheToGaraje(new Coche(3, "Opel", "Astra"), 1);

        assertEquals(2, handler.getNewIdForCoche());

    }

    @Test
    void newIdTorneoTest() {
        handler.addTorneo(new Torneo(1, "Torneo 1"));
        handler.addTorneo(new Torneo(2, "Torneo 2"));
        handler.addTorneo(new Torneo(4, "Torneo 3"));

        assertEquals(3, handler.getNewIdForTorneo());
    }

    @Test
    void newIdCarreraTest() {
        handler.addTorneo(new Torneo(1, "a"));
        handler.addCarreraToTorneo(new CarreraEliminacion(1, "aa", 1), 1);

        assertEquals(2, handler.getNewIdForCarrera());
    }

    @Test
    void newIdDragsterTest() {
        handler.addDragster(new CarreraDragster(1, "dragster 1", 1, 2, 1, 2));
        handler.addDragster(new CarreraDragster(3, "dragster 2", 1, 2, 1, 2));
        assertEquals(2, handler.getNewIdForDragster());
    }


    /******************************************************************************************************************/
    //getters de Objetos

    @Test
    void getGarajeByIdTest(){
        Garaje muestra = new Garaje(1, "Ferrari");

        handler.addGaraje(muestra);
        handler.addGaraje(new Garaje(2, "Lamborghini"));
        handler.addGaraje(new Garaje(3, "Bugatti"));

        assertEquals(muestra, handler.getGarageById(1));
        assertNotEquals(muestra, handler.getGarageById(2));

        assertNull(handler.getGarageById(4));
    }
    @Test
    void getCocheByIdTest(){
        handler.addGaraje(new Garaje(1, "Lamborghini"));
        Coche coche = new Coche(2, "Lamborghini" ,"Huracan");

        handler.addCocheToGaraje(new Coche(1, "Lamborghini", "Aventador"), 1);
        handler.addCocheToGaraje(coche, 1);
        handler.addCocheToGaraje(new Coche(3, "Lamborghini", "Urus"), 1);

        assertEquals(coche, handler.getCocheById(2));
        assertNotEquals(coche, handler.getCocheById(1));

        assertNull(handler.getCocheById(4));
    }

    @Test
    void getTorneoByIdTest(){
        Torneo torneo = new Torneo(2, "Torneo2");

        handler.addTorneo(new Torneo(1, "Torneo1"));
        handler.addTorneo(torneo);

        assertEquals(torneo, handler.getTorneoById(2));
        assertNotEquals(torneo, handler.getTorneoById(1));

        assertNull(handler.getTorneoById(3));
    }

    @Test
    void getCarreraByIdTest(){
        Torneo torneo = new Torneo(1, "Torneo");
        handler.addTorneo(torneo);

        CarreraEliminacion carreraEliminacion = new CarreraEliminacion(1, "eliminacion", 5);
        CarreraEstandar carreraEstandar = new CarreraEstandar(2, "estandar", 10);

        handler.addCarreraToTorneo(carreraEliminacion, 1);
        handler.addCarreraToTorneo(carreraEstandar, 1);

        handler.addCarreraToTorneo(new CarreraEliminacion(3, "el2", 3), 1);
        handler.addCarreraToTorneo(new CarreraEstandar(4, "es2", 10), 1);

        assertEquals(carreraEliminacion, handler.getCarreraById(1));
        assertNotEquals(carreraEliminacion, handler.getCarreraById(3));

        assertEquals(carreraEstandar, handler.getCarreraById(2));
        assertNotEquals(carreraEstandar, handler.getCarreraById(4));

        assertNull(handler.getCarreraById(5));
    }

    @Test
    void getDragsterByIdTest(){
        CarreraDragster carrera = new CarreraDragster(2, "dragster1", 1, 2, 7000, 8000);

        handler.addDragster(new CarreraDragster(1, "dragster2", 1, 2, 5000, 9000));
        handler.addDragster(carrera);

        assertEquals(carrera, handler.getDragsterById(2));
        assertNotEquals(carrera, handler.getDragsterById(1));

        assertNull(handler.getDragsterById(3));
    }

    @Test
    void getGarajeByCocheTest(){
        Garaje mercedes = new Garaje(1, "Mercedes");
        Garaje bmw = new Garaje(2, "BMW");

        Coche glc = new Coche(1, "Mercedes", "GLC");
        Coche benz = new Coche(2, "Mercedes", "Benz");
        Coche roadster = new Coche(3, "BMW", "Roadster");
        Coche m2 = new Coche(4, "BMW", "M2 Competition");

        mercedes.addCoche(glc);
        mercedes.addCoche(benz);
        bmw.addCoche(roadster);
        bmw.addCoche(m2);

        handler.addGaraje(mercedes);
        handler.addGaraje(bmw);

        assertEquals(mercedes, handler.getGarajeFromCoche(1));
        assertNotEquals(mercedes, handler.getGarajeFromCoche(3));

        assertEquals(bmw, handler.getGarajeFromCoche(4));
        assertNotEquals(bmw, handler.getGarajeFromCoche(2));

        assertNull(handler.getGarajeFromCoche(5));

    }

    @Test
    void getCochesFromTorneoTest(){
        handler.addGaraje(new Garaje(1, "Aston Martin"));
        Coche c1 = new Coche(1, "Aston Martin", "DB11");
        Coche c2 = new Coche(2, "Aston Martin", "DBS Superleggera");
        Coche c3 = new Coche(3, "Aston Martin", "Vantage");

        ArrayList<Coche>coches = new ArrayList<>();
        coches.add(c1);
        coches.add(c2);
        coches.add(c3);

        handler.addCocheToGaraje(c1, 1);
        handler.addCocheToGaraje(c2, 1);
        handler.addCocheToGaraje(c3, 1);

        ArrayList<Integer>cochesParticipantes = new ArrayList<>();
        cochesParticipantes.add(1);
        cochesParticipantes.add(2);
        cochesParticipantes.add(3);

        Torneo torneo = new Torneo(1, "Copa de Aston Martin");
        torneo.setCochesPart(cochesParticipantes);

        handler.addTorneo(torneo);

        assertEquals(coches, handler.getCochesFromTorneo(1));
    }

    /******************************************************************************************************************/
    //borrar datos

    @Test
    void deleteGarajeTest(){
        handler.addGaraje(new Garaje(1, "Ford"));
        handler.addGaraje(new Garaje(2, "Seat"));
        handler.addCocheToGaraje(new Coche(1,"Ford", "Focus RS"),1);

        assertEquals(2, handler.getGarajes().size());
        assertTrue(handler.garajeExists(1));
        assertTrue(handler.cocheExists(1));

        handler.deleteGaraje(1);

        assertEquals(1, handler.getGarajes().size());
        assertFalse(handler.garajeExists(1));
        assertFalse(handler.cocheExists(1));

        System.out.println("deleteGarajeTest (id 1 mustn't exists): " + handler.getAllGarajesId());

        assertFalse(handler.deleteGaraje(3));
    }
    @Test
    void deleteCocheTest(){
        handler.addGaraje(new Garaje(1, "Ford"));
        handler.addCocheToGaraje(new Coche(1, "Ford", "Focus RS"), 1);
        handler.addCocheToGaraje(new Coche(2, "Ford", "Mustang GT"), 1);
        handler.addCocheToGaraje(new Coche(3, "Ford", "Fiesta"), 1);

        assertEquals(3, handler.getTotCoches());
        assertTrue(handler.cocheExists(2));

        handler.deleteCoche(2);

        assertEquals(2, handler.getTotCoches());
        assertFalse(handler.cocheExists(2));

        System.out.println("deleteCocheTest (id 2 mustn't): " + handler.getAllCochesId());

    }

    @Test
    void deleteRegistrosFromCocheTest(){
        handler.addGaraje(new Garaje(1, "Ford"));
        handler.addCocheToGaraje(new Coche(1, "Ford", "Focus RS"), 1);
        handler.addCocheToGaraje(new Coche(2, "Ford", "Mustang GT"), 1);
        handler.addCocheToGaraje(new Coche(3, "Ford", "Fiesta"), 1);

        Torneo torneo = new Torneo(1, "Torneo ford");
        torneo.addCochePart(1);
        torneo.addCochePart(2);
        torneo.addCochePart(3);
        handler.addTorneo(torneo);

        ArrayList<RegistroCarrera>registros1 = new ArrayList<>();
        registros1.add(new RegistroCarrera(1, 7856));
        registros1.add(new RegistroCarrera(2, 3771));
        registros1.add(new RegistroCarrera(3, 1751));
        CarreraEliminacion cElim = new CarreraEliminacion(1, "carrera 1", 3,  registros1);

        ArrayList<RegistroCarrera>registros2 = new ArrayList<>();
        registros2.add(new RegistroCarrera(1, 8262));
        registros2.add(new RegistroCarrera(2, 9314));
        registros2.add(new RegistroCarrera(3, 6292));
        CarreraEstandar cEst = new CarreraEstandar(2, "carrera 2", 10, registros2);

        handler.addCarreraToTorneo(cElim, 1);
        handler.addCarreraToTorneo(cEst, 1);

        CarreraDragster cd = new CarreraDragster(1, "dragster ford", 1, 2, 13000, 12000);
        handler.addDragster(cd);

        assertEquals(3, handler.getCochesFromTorneo(1).size());
        assertTrue(handler.torneoExists(1));
        assertTrue(handler.dragsterExists(1));
        assertTrue(handler.carreraExists(1));
        assertTrue(handler.carreraExists(2));


        handler.deleteCoche(1);

        assertFalse(handler.dragsterExists(1));
        assertEquals(2, handler.getCochesFromTorneo(1).size());

        handler.deleteCoche(2);

        assertFalse(handler.torneoExists(1));
    }

    @Test
    void deleteTorneoTest(){
        handler.addTorneo(new Torneo(1, "t1"));
        handler.addTorneo(new Torneo(2, "t2"));
        handler.addTorneo(new Torneo(3, "t2"));

        assertEquals(3, handler.getTorneos().size());
        assertTrue(handler.torneoExists(3));

        handler.deleteTorneo(3);

        assertEquals(2, handler.getTorneos().size());
        assertFalse(handler.torneoExists(3));

        System.out.println("deleteTorneoTest (id 3 mustn't exists): "+handler.showTorneosShort());

        assertFalse(handler.deleteTorneo(4));
    }

    @Test
    void deleteCarreraTest(){
        handler.addTorneo(new Torneo(1, "t1"));
        handler.addCarreraToTorneo(new CarreraEstandar(1, "c1", 3), 1);
        handler.addCarreraToTorneo(new CarreraEstandar(2, "c2", 3), 1);
        handler.addCarreraToTorneo(new CarreraEstandar(3, "c3", 3), 1);
        handler.addCarreraToTorneo(new CarreraEstandar(4, "c4", 3), 1);

        assertEquals(4, handler.getTorneoById(1).getCarreras().size());
        assertTrue(handler.carreraExists(1));

        handler.deleteCarrera(1);

        assertEquals(3, handler.getTorneoById(1).getCarreras().size());
        assertFalse(handler.carreraExists(1));

        System.out.println("deleteCarreraTest (id 1 mustn't exists):\n"+handler.getInfoCarreras(1));

        assertFalse(handler.deleteCarrera(5));
    }

    @Test
    void deleteDragsterTest(){
        Garaje maserati = new Garaje(1, "Maserati");
        maserati.addCoche(new Coche(1, "Maserati", "Spyder"));
        maserati.addCoche(new Coche(2, "Maserati", "Bora"));
        handler.addGaraje(maserati);

        handler.addDragster(new CarreraDragster(1, "dragster1", 1, 2, 10000, 9000));
        handler.addDragster(new CarreraDragster(2, "dragster2", 1, 2, 3000, 5000));

        assertEquals(2, handler.getDragsters().size());
        assertTrue(handler.dragsterExists(2));

        handler.deleteDragster(2);

        assertEquals(1, handler.getDragsters().size());
        assertFalse(handler.dragsterExists(2));

        System.out.println("deleteDragsterTest (id 2 mustn´t exists): \n" + handler.showDragsters());

        assertFalse(handler.deleteDragster(3));
    }

    /******************************************************************************************************************/
    //añadir datos

    @Test
    void addGarajeTest(){
        handler.addGaraje(new Garaje(1, "Volkswagen"));

        assertEquals(1, handler.getGarajes().size());
    }

    @Test
    void addCocheTest(){
        Garaje volkswagen = new Garaje(1, "Volkswagen");
        volkswagen.addCoche(new Coche(1, "Volkswagen", "Golf"));//directamente en el coche

        Garaje seat = new Garaje(2, "Seat");

        handler.addGaraje(volkswagen);
        handler.addGaraje(seat);
        handler.addCocheToGaraje(new Coche(2, "Volkswagen", "Polo"), 1);//añadiéndolo desde el controlador
        handler.addCocheToGaraje(new Coche(3, "Seat", "Leon"),2);

        assertEquals(3, handler.getTotCoches());
        assertEquals(2, handler.getGarageById(1).getCoches().size());
        assertEquals(1, handler.getGarageById(2).getCoches().size());

        assertFalse(handler.addCocheToGaraje(new Coche(4, "Opel", "Corsa"), 3));
    }

    @Test
    void addTorneoTest(){
        Torneo torneo = new Torneo(1, "Torneo 1");
        handler.addTorneo(torneo);

        assertEquals(1, handler.getTorneos().size());
    }

    @Test
    void addCarreraTest(){
        Torneo t1 = new Torneo(1, "t1");
        t1.addCarrera(new CarreraEstandar(1, "c1t1", 2));//directamente en el torneo
        handler.addTorneo(t1);
        handler.addCarreraToTorneo(new CarreraEliminacion(2, "c2t1", 1), 1);//desde el controlador

        assertEquals(2, handler.getTorneoById(1).getCarreras().size());
    }

    @Test
    void addDragsterTest(){
        handler.addDragster(new CarreraDragster(1, "dragster1", 1, 2, 1, 2));
        assertEquals(1, handler.getDragsters().size());
    }

    /******************************************************************************************************************/
    //modificar datos

    @Test
    void updateGarajeTest(){
        Garaje garaje = new Garaje(1, "Toyota");
        handler.addGaraje(garaje);

        assertEquals(handler.getGarageById(1).getNombre(), "Toyota");

        garaje.setNombre("Seat");
        handler.updateGaraje(garaje);

        assertEquals(handler.getGarageById(1).getNombre(), "Seat");

        assertFalse(handler.updateGaraje(new Garaje(2, "Opel")));
    }

    @Test
    void updateCocheTest(){
        handler.addGaraje(new Garaje(1, "Toyota"));

        Coche coche = new Coche(1, "Toyota", "Carolla");
        handler.addCocheToGaraje(coche, 1);
        assertEquals("Carolla", handler.getCocheById(1).getModelo());

        coche.setModelo("GR Supra");
        handler.updateCoche(coche);

        assertEquals("GR Supra", handler.getCocheById(1).getModelo());
        assertFalse(handler.updateCoche(new Coche(2, "Toyota", "Camry")));
    }

    @Test
    void updateTorneoTest(){
        Torneo torneo = new Torneo(1, "Torneo 1");
        handler.addTorneo(torneo);

        assertEquals("Torneo 1", handler.getTorneoById(1).getNombre());

        torneo.setNombre("Copa Piston");
        handler.updateTorneo(torneo);

        assertEquals("Copa Piston", handler.getTorneoById(1).getNombre());
        assertFalse(handler.updateTorneo(new Torneo(2, "Torneo 2")));
    }

    @Test
    void updateCarreraTest(){
        handler.addTorneo(new Torneo(1, "T1"));

        CarreraEstandar c1= new CarreraEstandar(1, "c1", 10);
        CarreraEliminacion c2 = new CarreraEliminacion(2, "c2", 11);
        handler.addCarreraToTorneo(c1, 1);
        handler.addCarreraToTorneo(c2 ,1);

        assertEquals("c1", handler.getCarreraById(1).getNombre());
        assertEquals("c2", handler.getCarreraById(2).getNombre());

        c1.setNombre("carrera estandar");
        c2.setNombre("carrera eliminacion");

        handler.updateCarrera(c1);
        handler.updateCarrera(c2);

        assertEquals("carrera estandar", handler.getCarreraById(1).getNombre());
        assertEquals("carrera eliminacion", handler.getCarreraById(2).getNombre());

        assertFalse(handler.updateCarrera(new CarreraEliminacion(3, "c3", 11)));
    }

    @Test
    void updateDragsterTest(){
        CarreraDragster cd = new CarreraDragster(1, "d", 1, 2, 1,2);
        handler.addDragster(cd);

        assertEquals("d", handler.getDragsterById(1).getNombre());

        CarreraDragster cd2 = new CarreraDragster(1, "dragster", 1, 2, 1, 2);

        handler.updateDragster(cd2);

        assertEquals("dragster", handler.getDragsterById(1).getNombre());

        assertFalse(handler.updateDragster(new CarreraDragster(2, "drag", 1,2,1,2)));
    }

    /******************************************************************************************************************/
    //metodos de seguridad

    //comprueba si es posible que un garaje pueda realizar una carrera. Si el garaje tiene al menos 2 coches será posible
    @Test
    void posibleGarajeRaceInGarajeTest(){
        handler.addGaraje(new Garaje(1, "Lamborghini"));
        handler.addGaraje(new Garaje(2, "Ferrari"));
        handler.addGaraje(new Garaje(3, "Bugatti"));//bugatti tiene 0 coches

        handler.addCocheToGaraje(new Coche(1, "Lamborghini" ,"Huracan"),1);
        handler.addCocheToGaraje(new Coche(2, "Lamborghini" ,"Urus"),1); //lamborghini tiene 2 coches
        handler.addCocheToGaraje(new Coche(3, "Ferrari" ,"Dino"),2);//ferrari tiene 1 coche

        assertTrue(handler.posibleGarajeRaceInGaraje(1));
        assertFalse(handler.posibleGarajeRaceInGaraje(2));
        assertFalse(handler.posibleGarajeRaceInGaraje(3));
    }

    //comprueba si existe algún garaje que pueda hacer una carrera de garaje (si existe algun garaje en la lista que tenga al menos 2 coches)
    @Test
    void posibleGarajeRaceTest(){
        handler.addGaraje(new Garaje(1, "Lamborghini"));
        handler.addGaraje(new Garaje(2, "Ferrari"));
        handler.addGaraje(new Garaje(3, "Bugatti"));//bugatti tiene 0 coches

        handler.addCocheToGaraje(new Coche(1, "Lamborghini" ,"Huracan"),1);//lamborghini tiene 1 coche
        handler.addCocheToGaraje(new Coche(3, "Ferrari" ,"Dino"),2);//ferrari tiene 1 coche

        assertFalse(handler.posibleGarajeRace());

        handler.addCocheToGaraje(new Coche(2, "Lamborghini" ,"Urus"),1); //ahora lamborghini tiene 2 coches

        assertTrue(handler.posibleGarajeRace());
    }

    //compruba si existen 2 garajes con al menos 1 coche cada 1
    @Test
    void posibleGeneralRaceTest(){
        //solo hay 2 garajes
        handler.addGaraje(new Garaje(1, "Lamborghini"));
        handler.addGaraje(new Garaje(2, "Ferrari"));//ferrari tiene 0 garajes

        handler.addCocheToGaraje(new Coche(1, "Lamborghini" ,"Huracan"),1);//lamborghini tiene 1 coche

        assertFalse(handler.posibleGeneralRace());

        handler.addCocheToGaraje(new Coche(3, "Ferrari" ,"Dino"),2);//ferrari tiene 1 coche

        assertTrue(handler.posibleGeneralRace());
    }

    /******************************************************************************************************************/
    //comprobar guardado y lecturas
    @Test
    void saveAndReadTest(){
        String fileTest = "testing";
        int count  = 0;

        File f = new File(fileTest + ".xml");
        while(f.exists()){
            count++;
            f = new File(fileTest + count + ".xml");
        }

        if(count != 0)fileTest += count + ".xml";
        else fileTest += ".xml";

        handler.setNombreFichero(fileTest);

        //Objetos a guardar
        Garaje garaje = new Garaje(1, "Mercedes");
        garaje.addCoche(new Coche(1, "Mercedes", "Benz"));
        garaje.addCoche(new Coche(2, "Mercedes", "Clase G"));

        CarreraDragster cd = new CarreraDragster(1, "Dragster 1", 1, 2, 13000, 12000);

        Torneo torneo = new Torneo(1, "Torneo 1");
        torneo.addCochePart(1);
        torneo.addCochePart(2);

        ArrayList<RegistroCarrera>registros = new ArrayList<>();
        registros.add(new RegistroCarrera(1, 9000));
        registros.add(new RegistroCarrera(2, 12780));

        torneo.addCarrera(new CarreraEliminacion(1, "Carrera 1", 3, registros));
        torneo.addCarrera(new CarreraEstandar(2, "Carrera 2", 10, registros));

        handler.addGaraje(garaje);
        handler.addDragster(cd);
        handler.addTorneo(torneo);

        //Guardar
        assertDoesNotThrow(()->{
            handler.saveAll();
        });

        //Leer
        handler = new XMLHandler(fileTest);

        assertEquals(garaje.toString(), handler.getGarageById(1).toString());
        assertEquals(garaje.getCoches().get(0).toString(), handler.getCocheById(1).toString());
        assertEquals(garaje.getCoches().get(1).toString(), handler.getCocheById(2).toString());
        assertEquals(cd.toString(), handler.getDragsterById(1).toString());
        assertEquals(torneo.toString(), handler.getTorneoById(1).toString());

        f.delete();

        //Leer de uno inexistente
        handler = new XMLHandler(fileTest);
        assertEquals(0, handler.getGarajes().size());
        assertEquals(0, handler.getTorneos().size());
        assertEquals(0, handler.getDragsters().size());

        f.delete();

    }


    /******************************************************************************************************************/

    //comprobar generación de coches para torneos
    @Test
    void generateCochesGarajeTorneoTest(){
        handler.addGaraje(new Garaje(1, "Ford"));
        handler.addCocheToGaraje(new Coche(4, "Ford", "Focus"), 1);
        handler.addCocheToGaraje(new Coche(7, "Ford", "Mustang"), 1);

        ArrayList<Integer> coches = handler.generateCochesGarajeTorneo(1);

        assertEquals(4, coches.get(0));
        assertEquals(7, coches.get(1));
    }


    @Test
    void generateRandomCochesGeneralTorneoTest(){
        handler.addGaraje(new Garaje(1, "Chevrolet"));
        handler.addGaraje(new Garaje(2, "Ford"));

        Coche c1 = new Coche(1, "Chevrolet", "Camaro");
        Coche c2 = new Coche(2, "Ford", "Mustang");

        handler.addCocheToGaraje(c1,1);
        handler.addCocheToGaraje(c2, 2);

        ArrayList<Coche>coches = new ArrayList<>();
        coches.add(c1);
        coches.add(c2);

        assertEquals(coches.get(0).getId(), handler.generateRandomCochesGeneralTorneo().get(0));
        assertEquals(coches.get(1).getId(), handler.generateRandomCochesGeneralTorneo().get(1));
    }

    /******************************************************************************************************************/
    //metodos de mostrar información

    @Test
    void showGarajesTest(){
        handler.addGaraje(new Garaje(1, "Chevrolet"));
        handler.addGaraje(new Garaje(2, "Ford"));
        handler.addGaraje(new Garaje(3, "Nissan"));
        handler.addGaraje(new Garaje(4, "Toyota"));

        String expected = "-----------------------------------------------------\n" +
                "Garaje 1 Chevrolet. Tot coches: 0.\n" +
                "Garaje 2 Ford. Tot coches: 0.\n" +
                "Garaje 3 Nissan. Tot coches: 0.\n" +
                "Garaje 4 Toyota. Tot coches: 0.\n" +
                "-----------------------------------------------------";

        assertEquals(expected, handler.showGarajes());
    }

    @Test
    void showCochesTest(){
        handler.addGaraje(new Garaje(1, "Nissan"));
        handler.addGaraje(new Garaje(2, "Toyota"));
        handler.addCocheToGaraje(new Coche(1, "Nissan", "GT-R"), 1);
        handler.addCocheToGaraje(new Coche(2, "Nissan", "370Z"), 1);
        handler.addCocheToGaraje(new Coche(3, "Toyota", "Carolla"), 2);
        handler.addCocheToGaraje(new Coche(4, "Toyota", "GR Supra"), 2);

        String expected = "-----------------------------------------------------\n" +
                "Coche ID: 1 Nissan GT-R. Garaje:1-Nissan.\n" +
                "Coche ID: 2 Nissan 370Z. Garaje:1-Nissan.\n" +
                "-----------------------------------------------------\n" +
                "Coche ID: 3 Toyota Carolla. Garaje:2-Toyota.\n" +
                "Coche ID: 4 Toyota GR Supra. Garaje:2-Toyota.\n" +
                "-----------------------------------------------------\n";

        assertEquals(expected, handler.showCoches());

        expected = "ID: 1 Nissan GT-R, ID: 2 Nissan 370Z, ID: 3 Toyota Carolla, ID: 4 Toyota GR Supra, ";
        assertEquals(expected, handler.showCochesBasicInfo());
    }

    @Test
    void showDragstersTest(){
        Garaje garaje = new Garaje(1, "Mercedes");
        Garaje garaje2 = new Garaje(2, "BMW");
        garaje.addCoche(new Coche(1, "Mercedes", "Benz"));
        garaje.addCoche(new Coche(2, "Mercedes", "Clase G"));
        garaje2.addCoche(new Coche(3,"BMW", "M2 Competition"));

        CarreraDragster cd = new CarreraDragster(1, "Dragster 1", 1, 2, 13000, 12000);
        CarreraDragster cd2 = new CarreraDragster(2, "Dragster 2", 1, 3, 9000, 9500);

        handler.addGaraje(garaje);
        handler.addGaraje(garaje2);

        handler.addDragster(cd);
        handler.addDragster(cd2);

        String expected ="1-Dragster 1, 2-Dragster 2, ";
        assertEquals(expected, handler.showDragstersIds());

        expected="*****************************************************\n" +
                "\u001B[4mDragster 1\u001B[0m\n" +
                "\u001B[38;5;178mGaraje 1 Mercedes, Coche ID: 2 Mercedes Clase G, Tiempo: 12.0 segundos\n" +
                "\u001B[0m\u001B[38;5;250mGaraje 1 Mercedes, Coche ID: 1 Mercedes Benz, Tiempo: 13.0 segundos\n" +
                "\u001B[0m-----------------------------------------------------\n" +
                "\u001B[4mDragster 2\u001B[0m\n" +
                "\u001B[38;5;178mGaraje 1 Mercedes, Coche ID: 1 Mercedes Benz, Tiempo: 9.0 segundos\n" +
                "\u001B[0m\u001B[38;5;250mGaraje 2 BMW, Coche ID: 3 BMW M2 Competition, Tiempo: 9.5 segundos\n" +
                "\u001B[0m-----------------------------------------------------\n" +
                "*****************************************************\n";

        assertEquals(expected, handler.showDragsters());
    }

    @Test
    void showTorneosTest(){
        Garaje garaje = new Garaje(1, "Porsche");
        garaje.addCoche(new Coche(1, "Porsche", "911 Carrera S"));
        garaje.addCoche(new Coche(2, "Porsche", "Panamera Turbo S"));
        garaje.addCoche(new Coche(3, "Porsche", "718 Spyder"));

        handler.addGaraje(garaje);

        Torneo torneo = new Torneo(1, "Torneo Porsche");
        torneo.addCochePart(1);
        torneo.addCochePart(2);
        torneo.addCochePart(3);

        ArrayList<RegistroCarrera>registros = new ArrayList<>();
        registros.add(new RegistroCarrera(1, 9000));
        registros.add(new RegistroCarrera(2, 12780));
        registros.add(new RegistroCarrera(3, 7000));

        ArrayList<RegistroCarrera>registros2 = new ArrayList<>();
        registros2.add(new RegistroCarrera(1, 15000));
        registros2.add(new RegistroCarrera(2, 6000));
        registros2.add(new RegistroCarrera(3, 7500));

        torneo.addCarrera(new CarreraEliminacion(1, "Carrera 1", 3, registros));
        torneo.addCarrera(new CarreraEstandar(2, "Carrera 2", 10, registros2));

        handler.addTorneo(torneo);

        System.out.println(handler.showTorneo(1));

        String expected = "*****************************************************\n" +
                "\u001B[4m1 Torneo Porsche\u001B[0m\n" +
                "\n" +
                "Coches participantes: \n" +
                "\tID: 1 Porsche 911 Carrera SGaraje 1 Porsche. Tot coches: 3.\n" +
                "\tID: 2 Porsche Panamera Turbo SGaraje 1 Porsche. Tot coches: 3.\n" +
                "\tID: 3 Porsche 718 SpyderGaraje 1 Porsche. Tot coches: 3.\n" +
                "\n" +
                "Carreras:\n" +
                "\n" +
                "Carrera 1 Carrera 1-> 3 minutos de intervalo\n" +
                "\u001B[38;5;178m\tCoche Porsche Panamera Turbo S(2) Distancia total: 12780.0m\u001B[0m puntos: (3)\n" +
                "\u001B[38;5;250m\tCoche Porsche 911 Carrera S(1) Distancia total: 9000.0m\u001B[0m puntos (2)\n" +
                "\u001B[38;5;136m\tCoche Porsche 718 Spyder(3) Distancia total: 7000.0m\u001B[0m puntos (1)\n" +
                "---------------------------------------------------\n" +
                "\n" +
                "Carrera 2 Carrera 2-> 10 minutos totales\n" +
                "\u001B[38;5;178m\tCoche Porsche 911 Carrera S(1) Distancia total: 15000.0m\u001B[0m puntos: (3)\n" +
                "\u001B[38;5;250m\tCoche Porsche 718 Spyder(3) Distancia total: 7500.0m\u001B[0m puntos (2)\n" +
                "\u001B[38;5;136m\tCoche Porsche Panamera Turbo S(2) Distancia total: 6000.0m\u001B[0m puntos (1)\n" +
                "---------------------------------------------------\n" +
                "\n" +
                "Podio: \n" +
                "\u001B[38;5;178m\tCoche Porsche 911 Carrera S(1) Total puntos: 5\u001B[0m\uD83E\uDD47\n" +
                "\u001B[38;5;250m\tCoche Porsche Panamera Turbo S(2) Total puntos: 4\u001B[0m\uD83E\uDD48\n" +
                "\u001B[38;5;136m\tCoche Porsche 718 Spyder(3) Total puntos: 3\u001B[0m\uD83E\uDD49\n" +
                "*****************************************************\n";
        assertEquals(expected, handler.showTorneo(1));

        expected="\u001B[1mTorneos:\n" +
                "\u001B[0m\u001B[4m1 Torneo Porsche\u001B[0m\n" +
                "Total carreras :2\n" +
                "\n" +
                "Podio: \n" +
                "\u001B[38;5;178m\tCoche Porsche 911 Carrera S(1) Total puntos: 5\u001B[0m\uD83E\uDD47\n" +
                "\u001B[38;5;250m\tCoche Porsche Panamera Turbo S(2) Total puntos: 4\u001B[0m\uD83E\uDD48\n" +
                "\u001B[38;5;136m\tCoche Porsche 718 Spyder(3) Total puntos: 3\u001B[0m\uD83E\uDD49\n" +
                "---------------------------------------------------\n";
        assertEquals(expected, handler.showTorneos());
    }





}
