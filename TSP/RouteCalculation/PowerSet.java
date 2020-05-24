package RouteCalculation;

import java.util.ArrayList;
import java.util.List;

/**
 * Bestimmung aller Teilmengen einer n-Elementigen Menge
 */
public class PowerSet {

    protected List<List<String>> createPowerSet(final int n){
        List<List<String>> storage = new ArrayList<>();

        //Liste für alle i elementigen Teilmengen anlegen
        for(int i = 0;i<=n;i++){
            storage.add(new ArrayList<>());
        }

        //2^n bestimmen
        int power = (int) Math.pow(2,n);

        //alle Werte von 0 bis 2^n durchlaufen - jede dieser Zahl umgewandelt in eine binärzahl gibt eine bitmap einer Teilmenge
        for(int i = 0;i<power;i++){
            String tmp = Integer.toBinaryString(i);
            tmp = addLeadingZeros(tmp,n);
            //bestimmen wie viele Elemente die Teilmenge hat und der richtigen Liste hinzufügen
            int numberOfOnes = countOnes(tmp);
            ArrayList<String> tmpList = (ArrayList<String>) storage.get(numberOfOnes);
            tmpList.add(tmp);
        }

        return storage;
    }


    private String addLeadingZeros(final String s, final int n){
        int diff = n - s.length();
        String newString = s;
        for(int i=0;i<diff;i++){
            newString = "0"+newString;
        }
        return newString;
    }

    private int countOnes(final String s){
        int result = 0;
        for(int i =0;i<s.length();i++){
            if(String.valueOf(s.charAt(i)).equals("1")){
                result++;
            }
        }
        return result;
    }
}
