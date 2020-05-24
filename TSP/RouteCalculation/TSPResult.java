package RouteCalculation;

/**
 * Klasse um das Ergebnis einer TSP Lösung zu speichern
 */
public class TSPResult {

    private int[][] tspResult;
    private int minDist;
    /**
     * startwert für das backtracing. Dieses k ist der Index des ersten Zwischenstops nach dem Startpunkt.
     */
    private int k;
    private int[][] kMap;

    public TSPResult(final int[][] result, final int minDist, final int k, final int[][] kMap){
        this.tspResult = result;
        this.minDist = minDist;
        this.k = k;
        this.kMap = kMap;
    }

    public int[][] getTspResult(){return tspResult;}

    public int getMinDist(){return minDist;}

    public int getK(){return k;}

    public int[][] getkMap(){return kMap;}
}
