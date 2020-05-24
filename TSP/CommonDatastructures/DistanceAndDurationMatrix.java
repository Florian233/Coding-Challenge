package CommonDatastructures;

/**
 * Klasse zum speichern der Adjazenzmatrizen für Distanz(in metern) und Fahrtzeit(in sekunden)
 */
public class DistanceAndDurationMatrix {

    private int[][] distanceMatrix;
    private int[][] durationMatrix;

    public DistanceAndDurationMatrix(final int[][] distanceMatrix, final int[][] durationMatrix ){
        this.distanceMatrix = distanceMatrix;
        this.durationMatrix = durationMatrix;
    }

    public int[][] getDistanceMatrix(){return distanceMatrix.clone();}

    public int[][] getDurationMatrix(){return durationMatrix.clone();}
}
