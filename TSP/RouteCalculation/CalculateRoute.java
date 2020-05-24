package RouteCalculation;

import CommonDatastructures.Route;

public class CalculateRoute {

    /**
     * Public Methode dieses Packages, die erst den Solver dann das Backtracing aufruft
     * @param distanceMatrix
     * @return Route, die das TSP gegeben durch die Adjazenzmatrix distanceMatrix optimal löst
     */
    public Route calculateRoute(final int[][] distanceMatrix){

        return new Backtracking().backtraceResult(new TSPSolver().solveTSP(distanceMatrix),distanceMatrix);
    }
}
