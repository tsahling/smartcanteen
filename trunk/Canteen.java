
/**
 * 
 * @author Marcel Baxmann
 */
public class Canteen {

    private CanteenLocation location;
    private int numberOfEmployees;
    private MenuPlan menuPlan;

    public Canteen(CanteenLocation location, int numberOfEmployees) {
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * @return the location
     */
    public CanteenLocation getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(CanteenLocation location) {
        this.location = location;
    }

    /**
     * @return the numberOfEmployees
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * @param numberOfEmployees
     *            the numberOfEmployees to set
     */
    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * @return the menuPlan
     */
    public MenuPlan getMenuPlan() {
        return menuPlan;
    }

    /**
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
