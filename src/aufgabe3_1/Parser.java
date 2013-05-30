package aufgabe3_1;

import java.io.*;
import java.util.*;

public class Parser {

    // Startwert der Pheromone pro Connection
    final public static int pheromon = 0;

    /**
     * Liest eine (symmetrische) TSP Datei ein und uebertraegt die linke
     * untere Dreiecksmatrix in ein int Array.
     * Erfordert Nullen als Trennzeichen (siehe Aufbau der TSP Dateien).
     * 
     * @param filePath  String des Dateinamens / Dateipfades
     * @return int      Array mit den Laengen der TSP Datei
     */
    public static int[] parseTestFile(String filePath) {

        int[] resultArray = null;
        String line; // Zu verarbeitende Zeile
        String splitedLine[]; // Werte ohne Trennzeichen
        

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            line = br.readLine();
            splitedLine = line.split(";");
      	 
         
            resultArray = new int[splitedLine.length];
            for(int i = 0; i < resultArray.length; i++) {
                resultArray[i] = Integer.parseInt(splitedLine[i]);
            } // for
            	
    
            br.close(); // BufferedReader Stream schliessen

        } catch (Exception e) {
            System.err.println(e);
            System.err.println("csv Datei konnte nicht eingelesen werden!");
            System.exit(0);
        } // catch
		
        return resultArray;
    }

    /**
     * Erzeugt eine ArrayList von Connections
     * 
     * @param array Array mit den Entfernungen zwischen den Nodes
     * @return      Eine ArrayList von Connections
     */
    public static List<Connection> initConnections(int[] array) {

        List<Connection> connectionList = new ArrayList<Connection>();
        int currentCity = 1;
        int neighborCity = 0;
        int connectionID = 1;

        for (int i = 0; i < array.length-1; i++) {
        	
            if (array[i] == 0) {
            	
                currentCity++;
                neighborCity = array[i+1];
                List<Integer> cities = new ArrayList<Integer>();
                cities.add(currentCity);
                cities.add(neighborCity);
                connectionList.add(new Connection(connectionID, array[i+2], pheromon, cities));
                connectionID++;
                i = i+2;            
            } else {
            	 List<Integer> cities = new ArrayList<Integer>();
            	neighborCity = array[i];
                cities.add(currentCity);
                cities.add(neighborCity);
                connectionList.add(new Connection(connectionID, array[i+1], pheromon, cities));
                connectionID++;
                i = i+1;
            } // else
        } // for        
        return connectionList;
    }

    /**
     * Erzeugt eine ArrayListe aus Nodes
     * 
     * @param connections   ArrayListe von Connections
     * @return              ArrayList von Nodes
     * @throws InterruptedException 
     */
    public static List<Node> initNodes(List<Connection> connections) {

        List<Node> nodeList = new ArrayList<Node>();
        Set<Integer> connectionSet = new HashSet<Integer>();

        for (int i = 0; i < connections.size(); i++) {
            connectionSet.addAll(connections.get(i).cities);
        } // for

        for (int i = 0; i < connectionSet.size(); i++) {

            List<Connection> connectionList = new ArrayList<Connection>();

            for (int j = 0; j < connections.size(); j++) {

                if (connections.get(j).cities.contains(i + 1)) {
                    connectionList.add(connections.get(j));
                } // if
            } // for

            nodeList.add(new Node(i + 1, connectionList));
        } // for
        
        return nodeList;
    }

}