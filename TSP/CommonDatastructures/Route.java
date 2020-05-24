package CommonDatastructures;

import java.util.List;

/**
 * Klasse um eine Route in Form von einer Liste mit den Indizes der Adressen und der Distanz zu speichern.
 */
public class Route {

    private int distance;
    private List<Integer> route;

    public Route(final int dist,final List<Integer> route){
        this.distance = dist;
        this.route = route;
    }

    public int getDistance(){return distance;}

    public List<Integer> getRoute(){return route;}
}
