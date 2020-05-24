package DistanceDetermination;

import CommonDatastructures.Adress;
import CommonDatastructures.DistanceAndDurationMatrix;
import Exceptions.DestinationNotFoundException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Adjazenzmatrix für Distanz und Fahrzeit für die angegeben Knoten(Adressen) mit der Hilfe der Google Maps Distance Matrix API erstellen
 */
public class DistanceDetermination {

    private int[][] distanceMatrix;
    private int[][] durationMatrix;

    /**
     * Methode um Adjazenzmatrizen für Distanz und Fahrtzeit zwischen in den Knoten(Adressen)
     * @param destinations - Liste in der Reihenfolge der vergebenen identifier - startpunkt am ende
     * @return Objekt, dass die Adjazenzmatrizen für Distanz und Fahrzeit zwischen den Knoten enthält
     * @throws DestinationNotFoundException
     * @throws IOException
     */
    public synchronized DistanceAndDurationMatrix determineDistances(final List<Adress> destinations) throws DestinationNotFoundException, IOException {
        int n = destinations.size();

        distanceMatrix = new int[n][n];
        durationMatrix = new int[n][n];

        String intermediateDestinations = createStringForIntermediateDestinations(destinations);
        String startPoint = createStringForStartingPoint(destinations);

        //Google Maps gibt maximal 100 Elemente zurück, also im Worst Case zu klein, also aufgeteilt in die Abfragen
        //für alle Zwischenstops auf alle Zwischenstops, Startknoten auf alle Zwischenstops und alle Zwischenstops auf den Startknoten
        executeGoogleMapsDistanceMatrixAPICall(startPoint,intermediateDestinations,n-1,0);
        executeGoogleMapsDistanceMatrixAPICall(intermediateDestinations,startPoint,0,n-1);
        executeGoogleMapsDistanceMatrixAPICall(intermediateDestinations,intermediateDestinations,0,0);


        return new DistanceAndDurationMatrix(distanceMatrix,durationMatrix);
    }

    /**
     *
     * @param origins String für origins parameter der google maps API Abfrage
     * @param destinations  String für destinations parameter der google maps API Abfrage
     * @param iOffset offset für die Reihen der Matrix - notwendig um Startpunkt-Zwischenstationen einzufügen
     * @param jOffset offset für die Spalten der Matrix - notwendig um Zwischenstationen-Startpunkt einzufügen
     * @throws IOException
     * @throws DestinationNotFoundException
     */
    private void executeGoogleMapsDistanceMatrixAPICall(final String origins, final String destinations,final int iOffset, final int jOffset) throws IOException,DestinationNotFoundException {
        String query = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origins+"&destinations="
                +destinations+"&language=de-DE";

        //Verbindung aufbauen und JSON Objekt für die Antwort erstellen
        HttpsURLConnection con = (HttpsURLConnection)(new URL(query)).openConnection();
        InputStream ins = con.getInputStream();
        JsonReader rdr = Json.createReader(ins);

        //JSON Objekt parsen und Werte in die Matrix einfügen
        JsonObject obj = rdr.readObject();
        JsonArray results = obj.getJsonArray("rows");
        int i = iOffset;
        int j = jOffset;
        for (JsonObject result : results.getValuesAs(JsonObject.class)) {

            JsonArray innerElements = result.getJsonArray("elements");
            for(JsonObject innerElement:innerElements.getValuesAs(JsonObject.class)){
                // Wenn google Maps den Ort nicht bestimmen kann oder keine Strecke findet, wird die DestinationNotFoundException ausgelöst
                if(innerElement.getString("status").equals("NOT_FOUND") || innerElement.getString("status").equals("ZERO_RESULTS")){
                    throw new DestinationNotFoundException(+i+","+j);
                }
                distanceMatrix[i][j]=innerElement.getJsonObject("distance").getInt("value");
                durationMatrix[i][j]=innerElement.getJsonObject("duration").getInt("value");
                j++;
            }
            j=jOffset;
            i++;
        }
    }

    /**
     * String für die Google Maps Abfrage des Startpunkts
     * @param destinations alle Adressen von denen nur die Letzte - der Startpunkt - ausgelesen wird
     * @return String in dem notwendigen Format für die Google Maps API Abfrage
     */
    private String createStringForStartingPoint(final List<Adress> destinations) {
        Adress adress = destinations.get(destinations.size()-1);
        String output = adress.getStreet()+"+"+adress.getStreetNumber()+"+"+adress.getPlz()+"+"+adress.getTown();

        return output.replaceAll(" ","+");
    }

    /**
     * String für die Google Maps abfrage zusammenbauen
     * @param destinations
     * @return String in dem Format für die Google Maps API Abfrage
     */
    private String createStringForIntermediateDestinations(final List<Adress> destinations) {
        String output = "";
        for(Adress adress:destinations){
            if(!output.equals("")){
                output = output + "|";
            }
            if(adress.isStartpoint()){//Startpunkt am ende der Liste - also break und natürlich nicht hinzufügen
                break;
            }
            output = output+adress.getStreet()+"+"+adress.getStreetNumber()+"+"+adress.getPlz()+"+"+adress.getTown();
        }

        return output.replaceAll(" ","+");
    }


}
