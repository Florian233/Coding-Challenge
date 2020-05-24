package GUI;

import CommonDatastructures.Adress;
import CommonDatastructures.DistanceAndDurationMatrix;
import CommonDatastructures.Route;
import DistanceDetermination.DistanceDetermination;
import Exceptions.DestinationNotFoundException;
import RouteCalculation.CalculateRoute;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse um die public methode eines CalculateRoute Objekts aufzurufen und den String zur Ausgabe in der GUI zusammenzubauen
 */

public class BuildRoute {

    public String buildRouteStringRepresentation (final List<Adress> adressList) throws DestinationNotFoundException {
        //Map, die jeder Adresse als Key den zugehörigen n wert zuordnet
        Map<Integer,Adress> identifierToAdressMap = new HashMap<>();
        adressList.forEach(adress -> {
            identifierToAdressMap.put(adress.getID(),adress);
        });

        DistanceAndDurationMatrix distanceAndDurationMatrix = null;

        //Mit Hilfe der Google Maps API die Adjazenzmatrizen für Distanz und Fahrtzeit bestimmen
        //Wenn DestinationNotFoundException geworfen wird, dann werden die Indices in der Message der Exception durch die Adressen ersetzt
        try {
            distanceAndDurationMatrix = new DistanceDetermination().determineDistances(adressList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(DestinationNotFoundException exp){
            String message = "";
            String oldMessage = exp.getMessage();
            int index1 = oldMessage.indexOf(",");
            String substring1 = oldMessage.substring(0,index1);
            String substring2 = oldMessage.substring(index1+1);
            int id1 = Integer.parseInt(substring1);
            int id2 = Integer.parseInt(substring2);
            message = identifierToAdressMap.get(id1).getAdressString()+" oder "+identifierToAdressMap.get(id2)+" keine gültige Adresse, bitte überprüfen!";
            throw new DestinationNotFoundException(message);
        }

        int[][] durationMatrix = distanceAndDurationMatrix.getDurationMatrix();
        int[][] distanceMatrix = distanceAndDurationMatrix.getDistanceMatrix();
        //Kürzeste Route bestimmen
        Route routeObj = new CalculateRoute().calculateRoute(distanceAndDurationMatrix.getDistanceMatrix());

        //String repräsentation zur Ausgabe in der GUI erstellen
        String route = "Gesamtstrecke:"+(routeObj.getDistance()/1000)+"km\n";
        int previousStop = -1;
        int i = 0;
        int time = 0;
        for(int r:routeObj.getRoute()){
            if(i>0){route=route+"Stop "+i+": ";}
            else if(i==0){route=route+"Startpunkt:";}
            route = route+identifierToAdressMap.get(r).getAdressString()+"\n";
            if(previousStop>=0){
                route=route+"Fahrzeit:"+convertSecondsToHoursMinutesSeconds(durationMatrix[previousStop][r])+"\n";
                time = time+durationMatrix[previousStop][r];
                route = route + "Strecke:"+(distanceMatrix[previousStop][r])/1000+"km\n";
            }
            previousStop = r;
            i++;
            route=route+"\n\n";
        }

        return route;
    }


    private String convertSecondsToHoursMinutesSeconds(final int seconds){
        int hours = seconds / 3600;
        int remainder = seconds-hours*3600;
        int minutes = remainder/60;
        remainder = remainder-minutes * 60;
        int secnds = remainder;

        return hours+" Stunden, "+minutes+" Minuten und "+secnds+" Sekunden";
    }
}
