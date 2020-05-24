package RouteCalculation;

import java.util.ArrayList;
import java.util.List;

/**
 *  Berechnen der Dynamisierungsformel d(k,subset)=min über alle i in subset( distanz(k,i)+d(i,subset\{i}) mit d(k,{})=distanz(k,startpunkt)
 *  Subset ohne Startpunkt.
 */

public class TSPSolver {

    /**
     * Ergebnis n x 2^n Matrix mit einem Feld für jedes bestimmte Tupel (i,subset von n)
     */
    private int[][] result;
    private int[][] distanceMatrix;
    /**
     * Anzahl der Zwischenstationen
     */
    private int n;
    /**
     * Matrix in der die bestimmten k Werte für jedes Tupel (i,subset von n) gespeichert werden, macht dasBacktracing einfacher
     */
    private int[][] kMatrix;

    /**
     * Methode, um basierend auf einer Adjazenzmatrix das TSP mit Startpunkt n-1 zu lösen. -> Startpunkt muss immer letzte Spalte/Reihe sein!
     * @param distanceMatrix Reihen VON - Spalten NACH , index n = Startpunkt
     * @return TSPResult Objekt mit der kürzesten Distanz, der result-matrix und der kMatrix
     */

    protected TSPResult solveTSP(final int[][] distanceMatrix){
        this.distanceMatrix = distanceMatrix;
        this.n = distanceMatrix.length-1;
        result = new int[n][(int) Math.pow(2,n)];
        kMatrix = new int[n][(int) Math.pow(2,n)];

        //alle möglichen Teilmengen der Zwischenstationen
        PowerSet p = new PowerSet();
        List<List<String>> subsets = p.createPowerSet(n);

        //initialisieren von distanz(k,{}) , wobei die leere Menge der Distanz des Knotens zum Startpunkt entspricht
        for(int k = 0;k<n;k++){
            result[k][0] = distanceMatrix[n][k];
            kMatrix[k][0] = n;
        }

        //Durchlaufen aller i-elementigen Teilmengen und berechen der Dynamisierungsformel
        for(int i = 1;i<subsets.size();i++){
            List<String> subsetsOfSizeI = subsets.get(i);
            for (String subset : subsetsOfSizeI) {
                for (int k = 0; k < n; k++) {
                    //Dynamisierungsformel berechnen falls k nicht in der Teilmenge enthalten ist
                    if (String.valueOf(subset.charAt(k)).equals("0")) {
                        result[k][Integer.parseInt(subset, 2)] = calculateMinDistance(k, subset);
                    }
                }
            }
        }

        return calculateTSPResult();

    }

    /**
     * Berechnen der Dynamisierungsformel für ein bestimmtes k und eine bestimmte Teilmenge
     * @param k
     * @param subset
     * @return minimale Distanz von dem bestimmten k zu der angegebenen Teilmenge
     */
    private int calculateMinDistance(final int k, final String subset){

        //Alle Elemente in der Teilmenge bestimmten
        List<Integer> subsetElements = getIndicesOfOnes(subset);
        int min = Integer.MAX_VALUE;
        //Für jedes Element e in der Teilmenge  d(k,subset) = distanz(k,e)+d(e,subset ohne e) bestimmen, minimales beibehalten
        for(Integer i:subsetElements){
            String subsetWithoutI = subset.substring(0,i)+"0"+subset.substring(i+1);
            int calculatedValueForI = distanceMatrix[k][i]+result[i][Integer.parseInt(subsetWithoutI,2)];
            if(min>calculatedValueForI){
                min = calculatedValueForI;
                kMatrix[k][Integer.parseInt(subset,2)] = i;
            }
        }
        return min;
    }


    /**
     * Ergebnis des TSP bestimmen, indem das minimum von allen Knoten i mit d(i,menge aller knoten ohne i) + distanz(i,startknoten) bestimmt wird
     * @return TSPResult objekt dieses TSPs
     */
    private TSPResult calculateTSPResult(){
        int min = Integer.MAX_VALUE;
        int indexOfMinDist=0;
        for(int i = 0;i<n;i++) {
            int completeSetWithoutIIndex = (int) (Math.pow(2,n)-1-Math.pow(2,n-1-i));
            int minDistanceWithFirstStopI = distanceMatrix[n][i]+result[i][completeSetWithoutIIndex];
            if(minDistanceWithFirstStopI<min){
                min=minDistanceWithFirstStopI;
                indexOfMinDist = i;
            }
        }
        result[indexOfMinDist][(int) (Math.pow(2,n)-1)]=min;
        return new TSPResult(result,min,indexOfMinDist,kMatrix);
    }

    /**
     * Für einen String der eine Teilmenge repräsentiert bestimmen welche Knoten enthalten sind
     * @param s
     * @return Liste aller in der von dem String s repräsentierten Teilmenge enthaltenen Knoten
     */
    private static List<Integer> getIndicesOfOnes(final String s){
        ArrayList<Integer> list = new ArrayList<>();
        int i = -1;

        while(true){
            i = s.indexOf("1",i+1);
            list.add(i);
            if(s.lastIndexOf("1")==i) break;
        }

        return list;
    }
}
