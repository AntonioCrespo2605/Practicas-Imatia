package com.imatia.campusdual.Controller;

import com.imatia.campusdual.Details.Emojis;
import com.imatia.campusdual.Details.MyTimeFormat;
import com.imatia.campusdual.Details.TextStyle;
import com.imatia.campusdual.Objects.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

public class XMLHandler {
    private ArrayList<Garaje>garajes;
    private ArrayList<Torneo>torneos;
    private ArrayList<CarreraDragster>dragsters;
    private String nombreFichero;


    //CONSTRUCTOR
    public XMLHandler(String nombreFichero) {
        this.nombreFichero=nombreFichero;
        checkFile();
        readGarajes();
        readTorneos();
        readDragsters();
    }

    public XMLHandler(){
        this.nombreFichero="";
        this.garajes = new ArrayList<>();
        this.torneos = new ArrayList<>();
        this.dragsters = new ArrayList<>();
    }

    //COMPROBAR FORMATO DEL FICHERO
    private void checkFile(){
        String expectedLine = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        boolean ok = true;

        if(!new File(nombreFichero).exists())ok = false;
        else{
            try (BufferedReader lector = new BufferedReader(new FileReader(nombreFichero))) {
                String primeraLinea = lector.readLine();
                if (primeraLinea != null && primeraLinea.startsWith(expectedLine)) ok = true;
                else ok = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        if(!ok){
            try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreFichero))) {
                escritor.println(expectedLine);
                escritor.println("<root>");
                escritor.println("</root>");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //METODOS DE LECTURA DE XML
    private void readTorneos() {
        ArrayList<Torneo> torneos = new ArrayList<>();

        try {
            File inputFile = new File(nombreFichero);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList torneoNodeList = doc.getElementsByTagName("torneo");

            for (int i = 0; i < torneoNodeList.getLength(); i++) {
                Element torneoElement = (Element) torneoNodeList.item(i);
                int torneoId = Integer.parseInt(torneoElement.getAttribute("id"));
                String torneoNombre = torneoElement.getAttribute("nombre");
                Torneo torneo = new Torneo(torneoId, torneoNombre);

                NodeList cocheAnotNodeList = torneoElement.getElementsByTagName("cocheAnot");
                for(int j = 0; j<cocheAnotNodeList.getLength();j++){
                    Element cocheAnotElement = (Element) cocheAnotNodeList.item(j);
                    torneo.addCochePart(Integer.parseInt(cocheAnotElement.getAttribute("id")));
                }

                NodeList carreraNodeList = torneoElement.getElementsByTagName("carrera");

                for (int j = 0; j < carreraNodeList.getLength(); j++) {
                    Element carreraElement = (Element) carreraNodeList.item(j);
                    int carreraId = Integer.parseInt(carreraElement.getAttribute("id"));
                    String carreraNombre = carreraElement.getAttribute("nombre");
                    int carreraMinutos = Integer.parseInt(carreraElement.getAttribute("minutos"));

                    NodeList cocheNodeList = carreraElement.getElementsByTagName("cochePart");
                    ArrayList<RegistroCarrera> registrosCarrera = new ArrayList<>();

                    for (int z = 0; z < cocheNodeList.getLength(); z++) {
                        Element cocheElement = (Element) cocheNodeList.item(z);
                        int cocheId = Integer.parseInt(cocheElement.getAttribute("id"));
                        double cocheDistancia = Double.parseDouble(cocheElement.getAttribute("distancia"));
                        RegistroCarrera registroCarrera = new RegistroCarrera(cocheId, cocheDistancia);
                        registrosCarrera.add(registroCarrera);
                    }

                    String tipoCarrera = carreraElement.getAttribute("tipo");
                    Carrera carrera;
                    if (tipoCarrera.equals("eliminacion")) {
                        carrera = new CarreraEliminacion(carreraId, carreraNombre, carreraMinutos, registrosCarrera);
                    } else {
                        carrera = new CarreraEstandar(carreraId, carreraNombre, carreraMinutos, registrosCarrera);
                    }

                    torneo.getCarreras().add(carrera);
                }

                torneos.add(torneo);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        this.torneos=torneos;
    }

    private void readGarajes(){
        ArrayList<Garaje> garajes = new ArrayList<>();

        try {
            File inputFile = new File(nombreFichero);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("garaje");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String nombre = element.getAttribute("nombre");

                    Garaje garaje = new Garaje(id, nombre);
                    NodeList cochesNodeList = element.getElementsByTagName("coche");

                    for (int j = 0; j < cochesNodeList.getLength(); j++) {
                        Node cocheNode = cochesNodeList.item(j);

                        if (cocheNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element cocheElement = (Element) cocheNode;
                            int cocheId = Integer.parseInt(cocheElement.getAttribute("id"));
                            String marca = cocheElement.getAttribute("marca");
                            String modelo = cocheElement.getAttribute("modelo");

                            Coche coche = new Coche(cocheId, marca, modelo);
                            garaje.addCoche(coche);
                        }
                    }

                    garajes.add(garaje);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        this.garajes=garajes;
    }

    private void readDragsters(){
        ArrayList<CarreraDragster> dragsters = new ArrayList<>();

        try {
            File inputFile = new File(nombreFichero);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("dragster");

            for(int i = 0; i< nodeList.getLength(); i++){
                Element dragsterElement = (Element) nodeList.item(i);
                int idDragster = Integer.parseInt(dragsterElement.getAttribute("id"));
                String nombreDragster = dragsterElement.getAttribute("nombre");
                int c1 = 0, c2 = 0, t1 = 0, t2 = 0;

                NodeList cochesPartNode = dragsterElement.getElementsByTagName("cochePart");
                for(int j = 0; j < cochesPartNode.getLength(); j++){
                    Element cochePartElement = (Element) cochesPartNode.item(j);
                    if(j == 0){
                        c1 = Integer.parseInt(cochePartElement.getAttribute("id"));
                        t1 = Integer.parseInt(cochePartElement.getAttribute("tiempo"));
                    }else{
                        c2 = Integer.parseInt(cochePartElement.getAttribute("id"));
                        t2 = Integer.parseInt(cochePartElement.getAttribute("tiempo"));
                        break;
                    }
                }

                dragsters.add(new CarreraDragster(idDragster, nombreDragster, c1, c2, t1, t2));
            }

        }catch (Exception e){
           System.err.println(e.getMessage());
           e.printStackTrace();
        }

        this.dragsters = dragsters;
    }


    //METODO DE GUARDADO
    public void saveAll(){

        deleteAll();

        //garajes
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

            //garajes
            Element garajesElement = doc.createElement("garajes");
            rootElement.appendChild(garajesElement);

            for(Garaje garaje : getGarajes()){
                Element garajeElement = doc.createElement("garaje");
                garajeElement.setAttribute("id", garaje.getId()+"");
                garajeElement.setAttribute("nombre", garaje.getNombre());

                for(Coche coche : garaje.getCoches()){
                    Element cocheElement = doc.createElement("coche");
                    cocheElement.setAttribute("id", coche.getId()+"");
                    cocheElement.setAttribute("marca", coche.getMarca());
                    cocheElement.setAttribute("modelo", coche.getModelo());
                    garajeElement.appendChild(cocheElement);
                }

                garajesElement.appendChild(garajeElement);
            }

            //torneos
            Element torneosElement = doc.createElement("torneos");
            rootElement.appendChild(torneosElement);

            for(Torneo torneo : getTorneos()){
                Element torneoElement = doc.createElement("torneo");
                torneoElement.setAttribute("id", torneo.getId()+"");
                torneoElement.setAttribute("nombre", torneo.getNombre());

                for(Integer i : torneo.getCochesPart()){
                    Element cocheAnotElement = doc.createElement("cocheAnot");
                    cocheAnotElement.setAttribute("id", i+"");
                    torneoElement.appendChild(cocheAnotElement);
                }

                for(Carrera carrera : torneo.getCarreras()){
                    Element carreraElement = doc.createElement("carrera");
                    carreraElement.setAttribute("id", carrera.getId()+"");
                    carreraElement.setAttribute("nombre", carrera.getNombre());


                    if (carrera instanceof CarreraEstandar){
                        CarreraEstandar ce = (CarreraEstandar)carrera;
                        carreraElement.setAttribute("tipo", "estandar");
                        carreraElement.setAttribute("minutos", ce.getMinutos()+"");

                        for(RegistroCarrera rc : ce.getRegistros()){
                            if(cocheExists(rc.getCoche())){
                                Element cochePartElement = doc.createElement("cochePart");
                                cochePartElement.setAttribute("id",rc.getCoche()+"");
                                cochePartElement.setAttribute("distancia", rc.getDistTot()+"");

                                carreraElement.appendChild(cochePartElement);
                            }
                        }
                    }else {
                        CarreraEliminacion ce = (CarreraEliminacion)carrera;
                        carreraElement.setAttribute("tipo", "eliminacion");
                        carreraElement.setAttribute("minutos", ce.getintervalo()+"");

                        for(RegistroCarrera rc : ce.getRegistros()){
                            if(cocheExists(rc.getCoche())){
                                Element cochePartElement = doc.createElement("cochePart");
                                cochePartElement.setAttribute("id",rc.getCoche()+"");
                                cochePartElement.setAttribute("distancia", rc.getDistTot()+"");

                                carreraElement.appendChild(cochePartElement);
                            }
                        }
                    }

                    torneoElement.appendChild(carreraElement);
                }

                torneosElement.appendChild(torneoElement);
            }

            //dragsters
            Element dragstersElement = doc.createElement("dragsters");
            rootElement.appendChild(dragstersElement);
            for(CarreraDragster carreraDragster: getDragsters()){
                Element dragsterElement = doc.createElement("dragster");
                dragsterElement.setAttribute("id", carreraDragster.getId()+"");
                dragsterElement.setAttribute("nombre", carreraDragster.getNombre());

                Element cochePart1Element = doc.createElement("cochePart");
                cochePart1Element.setAttribute("id", carreraDragster.getCoche1()+"");
                cochePart1Element.setAttribute("tiempo", carreraDragster.getMillisC1()+"");
                dragsterElement.appendChild(cochePart1Element);

                Element cochePart2Element = doc.createElement("cochePart");
                cochePart2Element.setAttribute("id", carreraDragster.getCoche2()+"");
                cochePart2Element.setAttribute("tiempo", carreraDragster.getMillisC2()+"");
                dragsterElement.appendChild(cochePart2Element);

                dragstersElement.appendChild(dragsterElement);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreFichero));
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //METODO DE RESETEO DE FICHEROS
    private void deleteAll(){
        File file = new File(nombreFichero);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(("").getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************************************/
    //GETTERS Y SETTERS
    public ArrayList<Garaje> getGarajes() {
        return garajes;
    }

    public void setGarajes(ArrayList<Garaje> garajes) {
        this.garajes = garajes;
    }

    public ArrayList<Torneo> getTorneos() {
        return torneos;
    }

    public void setTorneos(ArrayList<Torneo> torneos) {
        this.torneos = torneos;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public ArrayList<CarreraDragster> getDragsters() {
        return dragsters;
    }

    public void setDragsters(ArrayList<CarreraDragster> dragsters) {
        this.dragsters = dragsters;
    }

    /******************************************************************************************************************/
    //METODOS PARA COMPROBAR EXISTENCIAS

    //comprobar garaje
    public boolean garajeExists(int id){
        return garajes.stream().anyMatch(g -> g.getId() == id);
    }

    public boolean garajeExists(String nombre){
        String nom = nombre.toLowerCase().trim();
        return garajes.stream().anyMatch(g -> g.getNombre().toLowerCase().trim().equals(nom.toLowerCase().trim()));
    }

    //comprobar coches
    public boolean cocheExists(int id){

        for(Garaje garaje : garajes){
            for(Coche c : garaje.getCoches()){
                if(c.getId() == id)return true;
            }
        }

        return false;
    }

    public boolean cocheExists(String marca, String modelo){
        marca=marca.toLowerCase().trim();
        modelo=modelo.toLowerCase().trim();
        for(Garaje garaje : garajes){
            for(Coche c : garaje.getCoches()){
                if(c.getMarca().toLowerCase().trim().equals(marca) && c.getModelo().toLowerCase().trim().equals(modelo))return true;
            }
        }
        return false;
    }

    //comprobar torneos
    public boolean torneoExists(int id){
        return torneos.stream().anyMatch(t -> t.getId() == id);
    }

    public boolean torneoExists(String nombre){
        String nom = nombre.toLowerCase().trim();
        return torneos.stream().anyMatch(t -> t.getNombre().toLowerCase().trim().equals(nom));
    }

    //comprobar carreras
    public boolean carreraExists(int id){
        for(Torneo torneo : torneos){
            for(Carrera carrera : torneo.getCarreras()){
                if(carrera.getId()==id)return true;
            }
        }
        return false;
    }

    public boolean carreraExistsInTorneo(int idCarrera, int idTorneo){
        Torneo torneo = getTorneoById(idTorneo);
        for(Carrera carrera : torneo.getCarreras()){
            if(carrera.getId() == idCarrera)return true;
        }
        return false;
    }

    public boolean carreraExists(String nombre){
        nombre=nombre.toLowerCase().trim();
        for(Torneo torneo : torneos){
            for(Carrera carrera : torneo.getCarreras()){
                if(carrera.getNombre().toLowerCase().trim().equals(nombre))return true;
            }
        }
        return false;
    }

    //comprueba si existe un dragster por su id
    public boolean dragsterExists(int id){
        for(CarreraDragster cd : dragsters){
            if(cd.getId() == id)return true;
        }
        return false;
    }

    //comprueba si existe un dragster por su nombre
    public boolean dragsterExists(String nombre){
        nombre = nombre.toLowerCase().trim();
        for(CarreraDragster cd : dragsters){
            if(cd.getNombre().toLowerCase().trim().equals(nombre))return true;
        }
        return false;
    }


    /******************************************************************************************************************/
    //GETTERS ESPECIFICOS

    public Garaje getGarajeFromCoche(int idCoche){
        for(Garaje garaje : garajes){
            for(Coche coche : garaje.getCoches()){
                if(coche.getId() == idCoche) return garaje;
            }
        }
        return null;
    }

    public Garaje getGarageById(int id){
        for(Garaje garaje : garajes){
            if(garaje.getId() == id)return garaje;
        }
        return null;
    }

    public Carrera getCarreraById(int id){
        for(Torneo torneo : torneos){
            for(Carrera carrera : torneo.getCarreras()){
                if(carrera.getId()==id)return carrera;
            }
        }
        return null;
    }

    public Coche getCocheById(int id){
        for(Garaje garaje : garajes){
            for(Coche coche : garaje.getCoches()){
                if(coche.getId() == id)return coche;
            }
        }
        return null;
    }

    public Torneo getTorneoById(int id){
        for(Torneo torneo: torneos){
            if(torneo.getId() == id)return torneo;
        }
        return null;
    }

    public ArrayList<Coche> getCochesFromTorneo(int id) {//todo
        ArrayList<Coche>toret = new ArrayList<>();
        Torneo torneo = getTorneoById(id);
        for(Integer i : torneo.getCochesPart()){
            toret.add(getCocheById(i));
        }
        return toret;
    }

    public CarreraDragster getDragsterById(int id){
        for(CarreraDragster cd : dragsters){
            if(cd.getId() == id)return cd;
        }
        return null;
    }


    /******************************************************************************************************************/
    //METODOS PARA BORRAR

    //elimina un coche junto a sus registros en torneos y dragsters. Si el torneo queda sin sufiecient
    public boolean deleteCoche(int id){
        boolean founded = false;

        for(int i = 0; i< dragsters.size(); i++){
            if(dragsters.get(i).getCoche1() == id || dragsters.get(i).getCoche2() ==id){
                dragsters.remove(i);
            }
        }

        for(int i = 0; i < torneos.size(); i++){
            for(int j = 0; j < torneos.get(i).getCochesPart().size(); j++){
                if(torneos.get(i).getCochesPart().get(j) == id){
                    torneos.get(i).getCochesPart().remove(j);
                }
            }

            for(int j = 0; j < torneos.get(i).getCarreras().size(); j++){
                if(torneos.get(i).getCarreras().get(j) instanceof CarreraEstandar){
                    CarreraEstandar ce = (CarreraEstandar) torneos.get(i).getCarreras().get(j);
                    for(RegistroCarrera rc : ce.getRegistros()){
                        if(rc.getCoche() == id){
                            ce.getRegistros().remove(rc);
                            break;
                        }
                    }
                }else{
                    CarreraEliminacion ce = (CarreraEliminacion) torneos.get(i).getCarreras().get(j);
                    for(RegistroCarrera rc : ce.getRegistros()){
                        if(rc.getCoche() == id){
                            ce.getRegistros().remove(rc);
                            break;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < torneos.size(); i++){
            if(torneos.get(i).getCochesPart().size()<2)torneos.remove(i);
        }

        for(Garaje garaje : garajes){
           if(garaje.deleteCoche(id))founded = true;
        }

        return founded;
    }

    //elimina un garaje junto a sus coches
    public boolean deleteGaraje(int id){

        for(int i = 0; i < garajes.size() ; i++){
            if(garajes.get(i).getId() == id){
                for(int j = 0; j < garajes.get(i).getCoches().size() ; j++){
                    deleteCoche(garajes.get(i).getCoches().get(j).getId());
                }
                garajes.remove(i);
                return true;
            }
        }

        return false;
    }

    //elimina la información de un dragster
    public boolean deleteDragster(int id){
        for(int i = 0; i < dragsters.size(); i++){
            if(dragsters.get(i).getId() == id){
                dragsters.remove(i);
                return true;
            }
        }
        return false;
    }

    //elimina una carrera
    public boolean deleteCarrera(int id){
        for(Torneo torneo : torneos){
            for (int i = 0; i < torneo.getCarreras().size(); i++) {
                if(torneo.getCarreras().get(i).getId() == id){
                    torneo.getCarreras().remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    //elimina un torneo
    public boolean deleteTorneo(int id){
        for(Torneo torneo : torneos){
            if(torneo.getId() == id){
                torneos.remove(torneo);
                return true;
            }
        }
        return false;
    }

    /******************************************************************************************************************/
    //METODOS PARA GENERAR IDS NUEVOS

    //devuelve el primer id disponible (sin asignar) que pueda ser usado por un nuevo coche
    public int getNewIdForCoche(){
        int id = 1;
        while(cocheExists(id)){
            id++;
        }
        return id;
    }

    //devuelve el primer id disponible (sin asignar) que pueda ser usado por un nuevo garaje
    public int getNewIdForGaraje(){
        int id = 1;
        while(garajeExists(id)){
            id++;
        }
        return id;
    }

    //devuelve el primer id disponible (sin asignar) que pueda ser usado por un nuevo torneo
    public int getNewIdForTorneo(){
        int id = 1;
        while (torneoExists(id))id++;
        return id;
    }

    //devuelve el primer id disponible (sin asignar) que pueda ser usado por un nuevo dragster
    public int getNewIdForDragster(){
        int id = 1;
        while(dragsterExists(id)){
            id++;
        }
        return id;
    }

    //devuelve el primer id disponible (sin asignar) que pueda ser usado por una nueva carrera
    public int getNewIdForCarrera(){
        int id = 1;
        while (carreraExists(id))id++;
        return id;
    }

    /******************************************************************************************************************/
    //METODOS DE MUESTRA

    //muestra todos los garajes
    public String showGarajes() {
        String toret = "-----------------------------------------------------\n";
        toret += garajes.stream().map(g -> g.toString()).collect(Collectors.joining("\n"));
        toret += "\n-----------------------------------------------------";
        return toret;
    }

    //muestra todos los coches
    public String showCoches() {
        String toret = "";
        for(Garaje garaje : garajes) {
            toret += "-----------------------------------------------------\n";
            for(Coche coche : garaje.getCoches()){
                toret += "Coche " + coche.toString() + ". Garaje:" + garaje.getId() + "-" + garaje.getNombre()+".\n";
            }
        }
        toret += "-----------------------------------------------------\n";
        return toret;
    }

    //muestra el id de todos los coches, la marca y el modelo
    public String showCochesBasicInfo(){
        String toret = "";
        for(Garaje garaje : garajes) {
            for(Coche coche : garaje.getCoches()){
                toret += coche.toString() + ", ";
            }
        }
        return toret;
    }

    //Muestra todos los ids de los dragsters existentes
    public String showDragstersIds(){
        String toret = "";
        for(CarreraDragster cd : dragsters){
            toret += cd.getId()+"-"+ cd.getNombre()+", ";
        }
        return toret;
    }

    //muestra toda la información de todos los dragsters
    public String showDragsters(){
        String toret = "*****************************************************\n";

        for(CarreraDragster cd : dragsters){
            toret += TextStyle.underline(cd.getNombre())+"\n";
            if(cd.getMillisC1() < cd.getMillisC2()){
                toret += TextStyle.golden("Garaje "+getGarajeFromCoche(cd.getCoche1()).toString2()+", Coche "+getCocheById(cd.getCoche1())+", Tiempo: "+ MyTimeFormat.fromMillisToSeconds(cd.getMillisC1())+" segundos\n");
                toret += TextStyle.silver("Garaje "+getGarajeFromCoche(cd.getCoche2()).toString2()+", Coche "+getCocheById(cd.getCoche2())+", Tiempo: "+ MyTimeFormat.fromMillisToSeconds(cd.getMillisC2())+" segundos\n");
            }else{
                toret += TextStyle.golden("Garaje "+getGarajeFromCoche(cd.getCoche2()).toString2()+", Coche "+getCocheById(cd.getCoche2())+", Tiempo: "+ MyTimeFormat.fromMillisToSeconds(cd.getMillisC2())+" segundos\n");
                toret += TextStyle.silver("Garaje "+getGarajeFromCoche(cd.getCoche1()).toString2()+", Coche "+getCocheById(cd.getCoche1())+", Tiempo: "+ MyTimeFormat.fromMillisToSeconds(cd.getMillisC1())+" segundos\n");
            }

            toret += "-----------------------------------------------------\n";

        }
        toret += "*****************************************************\n";
        return toret;
    }

    //muestra los id de todos los torneos
    public String showTorneos(){
        String toret = TextStyle.bold("Torneos:\n");
        for(Torneo torneo : torneos){
            toret += showShortTorneo(torneo.getId());
            toret += "---------------------------------------------------\n";
        }
        return toret;
    }

    //muestra la información basica de un torneo
    public String showShortTorneo(int idTorneo){
        Torneo torneo = getTorneoById(idTorneo);

        String toret = TextStyle.underline(torneo.getId()+ " " + torneo.getNombre())+"\n";
        toret += "Total carreras :"+torneo.getCarreras().size()+"\n";
        toret += "\nPodio: \n";
        if(torneo.getCarreras().size() == 0)toret += "\nEl podio aparecerá a partir de que se organice la primera carrera;\n";
        else {
            ArrayList<Integer>clasFinal = getPodio(torneo);
            for(int i = 0; i < clasFinal.size(); i++){
                String line = "\tCoche "+getCocheById(clasFinal.get(i)).toString2() + " Total puntos: "+getTotalPoints(clasFinal.get(i),torneo);
                if(i == 0){
                    toret += TextStyle.golden(line) + Emojis.MEDALLA_ORO + "\n";
                }else if(i == 1){
                    toret += TextStyle.silver(line) + Emojis.MEDALLA_PLATA + "\n";
                }else if(i == 2){
                    toret += TextStyle.bronze(line) + Emojis.MEDALLA_BRONCE + "\n";
                }else break;
            }
        }

        return toret;
    }

    //muestra la información completa de un torneo
    public String showTorneo(int idTorneo){
        Torneo torneo = getTorneoById(idTorneo);

        String toret = "*****************************************************\n";
        toret += TextStyle.underline(torneo.getId()+ " " + torneo.getNombre())+"\n";

        toret += "\nCoches participantes: \n";
        for(Integer i : torneo.getCochesPart()){
            toret += "\t" + getCocheById(i).toString() + getGarajeFromCoche(i).toString() + "\n";
        }

        toret += "\nCarreras:\n";
        if(torneo.getCarreras().size() == 0)toret += "No se ha organizado ninguna carrera en este torneo todavía;\n";

        for(Carrera carrera : torneo.getCarreras()){
            toret += "\nCarrera " + carrera.toString() ;

            ArrayList<RegistroCarrera> registrosOrdenados;
            if(carrera instanceof CarreraEliminacion){
                CarreraEliminacion ce = (CarreraEliminacion)carrera;
                toret += "-> " + ce.getintervalo() + " minutos de intervalo\n";
                registrosOrdenados = orderRegistros(ce.getRegistros());
            }else{
                CarreraEstandar ce = (CarreraEstandar) carrera;
                toret += "-> " + ce.getMinutos() + " minutos totales\n";
                registrosOrdenados = orderRegistros(ce.getRegistros());
            }

            int cont = 0;

            for(RegistroCarrera rc : registrosOrdenados){
                String line = "\tCoche " + getCocheById(rc.getCoche()).toString2() + " Distancia total: " + rc.getDistTot() + "m";
                switch (cont){
                    case 0:
                        toret += TextStyle.golden(line) + " puntos: (3)\n";
                        break;
                    case 1:
                        toret += TextStyle.silver(line) + " puntos (2)\n";
                        break;
                    case 2 :
                        toret += TextStyle.bronze(line) + " puntos (1)\n";
                        break;
                    default:
                        toret += line + "\n";
                        break;
                }
                cont++;
            }
            toret += "---------------------------------------------------\n";
        }

        toret += "\nPodio: \n";
        if(torneo.getCarreras().size() == 0)toret += "El podio aparecerá una vez organizada la primera carrera\n";
        else{
            ArrayList<Integer>clasFinal = getPodio(torneo);
            for(int i = 0; i < clasFinal.size(); i++){
                String line = "\tCoche "+getCocheById(clasFinal.get(i)).toString2() + " Total puntos: "+getTotalPoints(clasFinal.get(i),torneo);
                if(i == 0){
                    toret += TextStyle.golden(line) + Emojis.MEDALLA_ORO + "\n";
                }else if(i == 1){
                    toret += TextStyle.silver(line) + Emojis.MEDALLA_PLATA + "\n";
                }else if(i == 2){
                    toret += TextStyle.bronze(line) + Emojis.MEDALLA_BRONCE + "\n";
                }else break;
            }
        }

        toret += "*****************************************************\n";
        return toret;
    }

    //muestra el nombre y el id de todos los torneos
    public String showTorneosShort(){
        String toret = "";
        for(Torneo torneo : torneos){
            toret += torneo.getId() + " "+torneo.getNombre()+ ", ";
        }
        return toret;
    }

    public String getAllCochesId(){
        String toret="";
        for(Garaje garaje : garajes){
            for(Coche coche : garaje.getCoches()){
                toret+=coche.getId()+",";
            }
        }
        return toret;
    }

    //mustra la información basica de una carrera
    public String getInfoCarreras(int idTorneo){
        Torneo torneo = getTorneoById(idTorneo);
        String toret = "";
        for(Carrera carrera : torneo.getCarreras()){
            toret += carrera.getId() + "-" + carrera.getNombre() + "(T" + torneo.getId() + "),  ";
        }
        return toret;
    }

    //muestra todos los ids de los garajes
    public String getAllGarajesId(){
        String ids = garajes.stream().map(g -> Integer.toString(g.getId())).collect(Collectors.joining(","));
        return ids;
    }

    /******************************************************************************************************************/
    //METODOS PARA AÑADIR
    //añade un nuevo coche a un garaje
    public boolean addCocheToGaraje(Coche coche, int idGaraje){
        for (int i = 0; i < garajes.size(); i++) {
            if(garajes.get(i).getId() == idGaraje){
                garajes.get(i).addCoche(coche);
                return true;
            }
        }
        return false;
    }

    //añade un nuevo garaje
    public void addGaraje(Garaje garaje){
        garajes.add(garaje);
    }

    //añade un nuevo torneo
    public void addTorneo(Torneo torneo){
        this.torneos.add(torneo);
    }

    //añadde un nuevo dragster
    public void addDragster(CarreraDragster carreraDragster){
        dragsters.add(carreraDragster);
    }

    //añade una nueva carrera a un torneo
    public void addCarreraToTorneo(Carrera carrera, int idTorneo){
        for(int i = 0; i < torneos.size(); i++){
            if(torneos.get(i).getId() == idTorneo){
                torneos.get(i).addCarrera(carrera);
                break;
            }
        }
    }

    /******************************************************************************************************************/
    //METODOS DE MODIFICADO

    //modifica los datos de un coche (excepto el id)
    public boolean updateCoche(Coche coche){
        for(int i = 0 ; i < garajes.size() ; i++){
            for(int j = 0; j < garajes.get(i).getCoches().size() ; j++){
                if(garajes.get(i).getCoches().get(j).getId() == coche.getId()){
                    garajes.get(i).getCoches().set(j, coche);
                    return true;
                }
            }
        }
        return false;
    }

    //modifica los datos de un garaje (excepto el id)
    public boolean updateGaraje(Garaje garaje){
        for (int i = 0; i < garajes.size(); i++) {
            if(garajes.get(i).getId() == garaje.getId()){
                garajes.set(i, garaje);
                return true;
            }
        }
        return false;
    }

    //modifica los datos de un torneo (excepto el id)
    public boolean updateTorneo(Torneo torneo) {
        for(int i = 0; i< torneos.size(); i++){
            if(torneos.get(i).getId() == torneo.getId()){
                torneos.set(i, torneo);
                return true;
            }
        }
        return false;
    }

    //modifica los datos de una carrera (excepto el id)
    public boolean updateCarrera(Carrera carrera) {
        for(Torneo torneo : torneos){
            for(int i = 0; i < torneo.getCarreras().size(); i++){
                if(torneo.getCarreras().get(i).getId() == carrera.getId()){
                    torneo.getCarreras().set(i, carrera);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateDragster(CarreraDragster cd){
        for(int i = 0; i < dragsters.size(); i++){
            if(dragsters.get(i).getId() == cd.getId()){
                dragsters.set(i, cd);
                return true;
            }
        }
        return false;
    }

    /******************************************************************************************************************/
    //METODOS DE SEGURIDAD

    //indica si es posible iniciar una carrera de un garaje en concreto (si dispone de 2 coches mínimo)
    public boolean posibleGarajeRaceInGaraje(int idGaraje){
        Garaje garaje = getGarageById(idGaraje);
        if(garaje.getCoches().size()>1)return true;
        else return false;
    }

    //indica si al menos 1 garaje puede realizar una carrera de garaje
    public boolean posibleGarajeRace(){
        for(Garaje garaje : garajes){
            if(garaje.getCoches().size()>1)return true;
        }
        return false;
    }

    //indica si es posible iniciar una carrera general (si existe más de un garaje con al menos 1 coche)
    public boolean posibleGeneralRace(){
        boolean g1Ok = false;

        for(Garaje garaje : garajes){
            if(garaje.getCoches().size()>0){
                if(g1Ok) return true;
                else g1Ok = true;
            }
        }
        return false;
    }

    /******************************************************************************************************************/
    //ORDEN

    //ordena los registros de una carrera por la distancia recorrida
    private ArrayList<RegistroCarrera> orderRegistros(ArrayList<RegistroCarrera> registrosCarrera){
        ArrayList<RegistroCarrera> registrosOrdenados = new ArrayList<>(registrosCarrera);

        Collections.sort(registrosOrdenados, new Comparator<RegistroCarrera>() {
            @Override
            public int compare(RegistroCarrera registro1, RegistroCarrera registro2) {
                return Double.compare(registro2.getDistTot(), registro1.getDistTot());
            }
        });

        return registrosOrdenados;
    }

    //Devuelve un arrayList correspondientes a los ids de los coches que han participado en un torneo ordenados por sus puntos. Con las 3 primeras posiciones se obtiene el podio
    private ArrayList<Integer> getPodio(Torneo torneo){
        Comparator<Integer> comparadorPorPuntos = new Comparator<Integer>() {
            public int compare(Integer id1, Integer id2) {
                int puntos1 = getTotalPoints(id1, torneo);
                int puntos2 = getTotalPoints(id2, torneo);
                if(puntos1 != puntos2)return Integer.compare(puntos2, puntos1);
                else{
                    double distancia1 = getTotalDistance(id1, torneo);
                    double distancia2 = getTotalDistance(id2, torneo);
                    return Double.compare(distancia2, distancia1);
                }
            }
        };
        ArrayList<Integer>coches = new ArrayList<>(torneo.getCochesPart());

        Collections.sort(coches, comparadorPorPuntos);

        return coches;
    }

    /******************************************************************************************************************/
    //OTROS

    //genera un arraylist de coches cogiendo un coche aleatorio de cada garaje
    public ArrayList<Integer>generateRandomCochesGeneralTorneo(){
        ArrayList<Integer>coches = new ArrayList<>();
        Random random = new Random();

        for(Garaje garaje : garajes){
            int totCoches = garaje.getCoches().size();
            if(totCoches > 0){
                coches.add(garaje.getCoches().get(random.nextInt(totCoches)).getId());
            }
        }

        return coches;
    }

    //genera un array con todos los coches de un garaje
    public ArrayList<Integer>generateCochesGarajeTorneo(int idGaraje){
        ArrayList<Integer>coches = new ArrayList<>();
        Garaje garaje = getGarageById(idGaraje);
        for(Coche coche : garaje.getCoches()){
            coches.add(coche.getId());
        }
        return coches;
    }

    //Devuelve el total de puntos acumulados por un coche en un torneo, contando todas sus carreras
    private int getTotalPoints(int idCoche, Torneo torneo){
        int points = 0;
        for(Carrera carrera : torneo.getCarreras()){
            switch (getPositionInRace(idCoche, carrera)){
                case 0:points += 3;
                    break;
                case 1:points += 2;
                    break;
                case 2:points += 1;
                    break;
            }
        }
        return points;
    }

    //Devulve los metros totales recorridos por un coche en un Torneo
    private double getTotalDistance(int idCoche, Torneo torneo){
        double distance = 0;

        for(Carrera carrera: torneo.getCarreras()){
            ArrayList<RegistroCarrera> registros = new ArrayList<>();

            if(carrera instanceof CarreraEstandar){
                CarreraEstandar ce = (CarreraEstandar) carrera;
                registros = ce.getRegistros();
            }else if(carrera instanceof CarreraEliminacion){
                CarreraEliminacion ce = (CarreraEliminacion) carrera;
                registros = ce.getRegistros();
            }
            for(RegistroCarrera rc : registros){
                if(rc.getCoche() == idCoche)distance += rc.getDistTot();
            }
        }

        return distance;
    }

    //Devuelve la posición de un coche en una carrera, siendo la primera 0. Si no ha participado devuelve -1
    private int getPositionInRace(int idCoche, Carrera carrera){
        ArrayList<RegistroCarrera>registros = new ArrayList<>();
        if(carrera instanceof CarreraEstandar){
            CarreraEstandar ce = (CarreraEstandar)carrera;
            registros = orderRegistros(ce.getRegistros());
        }else if (carrera instanceof CarreraEliminacion){
            CarreraEliminacion ce = (CarreraEliminacion)carrera;
            registros = orderRegistros(ce.getRegistros());
        }else {
            CarreraDragster cd = (CarreraDragster) carrera;
            if(cd.getCoche1() == idCoche || cd.getCoche2() == idCoche){
                if(cd.getCoche1() == idCoche && cd.getMillisC1() <= cd.getMillisC2())return 0;
                else if (cd.getCoche1() == idCoche && cd.getMillisC1() > cd.getMillisC2())return 1;
                else if(cd.getCoche2() == idCoche && cd.getMillisC2() <= cd.getMillisC1())return 0;
                else if(cd.getCoche2() == idCoche && cd.getMillisC2() > cd.getMillisC1())return 1;
            }else return -1;
        }

        for (int i = 0; i < registros.size(); i++) {
            if(registros.get(i).getCoche() == idCoche) return i;
        }

        return -1;
    }

    //devuelve un entero con el total de todos los coches existentes
    public int getTotCoches(){
        int count = 0;
        for (Garaje garaje: garajes){
            count += garaje.getCoches().size();
        }
        return count;
    }

}
