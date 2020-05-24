package CommonDatastructures;

/**
 * Klasse für eine Adresse mit Straße, Straßennummer, Postleitzahl und Ort. Außerdem ein Flag um es sich um den Startpunkt handelt und n als indexwert für die TSP Berechnung
 */
public class Adress {

    private String street;
    private String streetNumber;
    private String plz;
    private String town;
    private int n;
    private boolean startpoint = false;


    public Adress(String streetNumber, String street, String plz, String town, final int n) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.plz = plz;
        this.town = town;
        this.n = n;
    }

    public Adress(String street, String streetNumber, String plz, String town, int n, boolean startpoint) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.plz = plz;
        this.town = town;
        this.n = n;
        this.startpoint = startpoint;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getTown() {
        return town;
    }

    public String getPlz() {
        return plz;
    }

    public int getID(){return n;}

    public boolean isStartpoint(){return startpoint;}

    public String getAdressString(){
        return street+" "+streetNumber+", "+plz+" "+town;
    }
}
