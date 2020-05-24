package RouteCalculation;

import CommonDatastructures.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse um mithilfe des Resultats des TSPSolvers die Route zu bestimmen
 */
public class Backtracking {

    /**
     * Methode, die aus dem Ergebnis des TSpSolvers und der DistanceMatrix eine Route erzeugt
     * @param tspResult Ergebnis des TSPSolvers
     * @param distanceMatrix Adjazenzmatrix aller Punkte
     * @return Route, die das TSP optimal löst
     */
    protected Route backtraceResult(final TSPResult tspResult, final int[][] distanceMatrix){
        int[][] resultMatrix = tspResult.getTspResult();
        List<Integer> route = new ArrayList<>();

        int n = distanceMatrix.length;
        //erster Stop nach Startpunkt als Anfangspunkt fürs Backtracing
        int followingNode = tspResult.getK();
        //Bitmap mit allen noch nicht besuchten stops angelegen
        String bitmapOfUnvistedNodes = Integer.toBinaryString((int) Math.pow(2,tspResult.getTspResult().length)-1);

        //Startknoten hinzufügen
        route.add(resultMatrix.length);

        //Strecke durchlaufen mithilfe der bestimmten k werte für jedes Tupel von (i,subset von n)
        for(int i = 0;i<n;i++){
            route.add(followingNode);
            if(i!=n-1) {// n-1 ist wieder startpunkt - würde also in beiden Zeilen eine Exception auslösen
                bitmapOfUnvistedNodes = bitmapOfUnvistedNodes.substring(0, followingNode) + "0" + bitmapOfUnvistedNodes.substring(followingNode + 1);
                followingNode = tspResult.getkMap()[followingNode][Integer.parseInt(bitmapOfUnvistedNodes, 2)];
            }
        }

        return new Route(tspResult.getMinDist(),route);

    }
}
