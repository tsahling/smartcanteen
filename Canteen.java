
/**
 * Die Klasse {@link Canteen} ist ein Datenobjekt, welches sich an einem realen Objekt Kantine orientiert.
 * Die Klase beschreibt den Standort der Kantine sowie die Mitarbeiteranzahl. Sie bietet Methoden um 
 * den Menue-Plan einer Kantine auszugeben oder zu setzen.
 * 
 * @author Marcel Baxmann 
 */
public class Canteen {

    private CanteenLocation location;
    private int numberOfEmployees;
    private MenuPlan menuPlan;

    
    /**
     * Standardkonstruktor
     * Die Klasse nimmt bei Ihrer Erstellung Variablen die Sie beschreiben entgegen
     * 
     * @param CanteenLocation  Standort der Kantine
     * 
     * @param numberOfEmployees  Anzahl der Mitarbeiter am Standort der Kantine
     */
    
    public Canteen(CanteenLocation location, int numberOfEmployees) {
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * Mit dieser Methode wird der Standort der Kantine ausgegeben
     * 
     * @return the location
     */
    public CanteenLocation getLocation() {
        return location;
    }

    /**
     * Mit dieser Methode wird der Standort der Kantine gesetzt
     * 
     * @param location  the location to set
     */
    public void setLocation(CanteenLocation location) {
        this.location = location;
    }

    /**
     * Mit dieser Methode wird die Anzahl der Mitarbeiter am Standort der Kantine ausgegeben
     * 
     * @return the numberOfEmployees
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * Mit dieser Methode wird die Anzahl der Mitarbeiter am Standort der Kantine gesetzt
     * 
     * @param numberOfEmployees   the numberOfEmployees to set
     */
    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * Mit dieser Methode wird der Menue-Plan ausgegeben
     * 
     * @return the menuPlan
     */
    public MenuPlan getMenuPlan() {
        return menuPlan;
    }

    /**
     * Mit dieser Methode wird der Menue-Plan gesetzt
     * 
     * @param menuPlan
     *            the menuPlan to set
     */
    public void setMenuPlan(MenuPlan menuPlan) {
        this.menuPlan = menuPlan;
    }

    /**
     * Diese Methode gibt den HashCode-Wert fuer das Objekt zurueck, von dem die Methode aufgerufen 
     * wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
 
        return 0;
    }

    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist, von dem die Methode 
     * aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {

        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Canteen}.
     * 
     * @return Die String-Representation von {@link Canteen}
     */
    @Override
    public String toString() {
        return null;
    }
}
