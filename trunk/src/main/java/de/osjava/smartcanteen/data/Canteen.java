package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.datatype.CanteenLocation;

/**
 * Die Klasse {@link Canteen} ist ein Datenobjekt, welches sich an einem realen
 * Objekt Kantine orientiert. Die Klasse beschreibt den Standort der Kantine
 * sowie die Mitarbeiteranzahl. Sie bietet Methoden um den Speiseplan einer
 * Kantine auszugeben oder zu setzen.
 * 
 * @author Marcel Baxmann
 */
public class Canteen {

    private CanteenLocation location;
    private int numberOfEmployees;
    private MenuPlan menuPlan;

    /**
     * Standardkonstruktor
     * 
     * @param location Standort der Kantine
     * @param numberOfEmployees Anzahl der Mitarbeiter am Standort der Kantine
     */
    public Canteen(CanteenLocation location, int numberOfEmployees) {
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * Mit dieser Methode wird der Standort der Kantine ausgegeben
     * 
     * @return Der Standort
     */
    public CanteenLocation getLocation() {
        return location;
    }

    /**
     * Mit dieser Methode wird die Anzahl der Mitarbeiter am Standort der
     * Kantine ausgegeben
     * 
     * @return the numberOfEmployees
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * Mit dieser Methode wird der Speiseplan ausgegeben
     * 
     * @return Den Speiseplan
     */
    public MenuPlan getMenuPlan() {
        return menuPlan;
    }

    /**
     * Mit dieser Methode wird der Speiseplan gesetzt
     * 
     * @param menuPlan
     *            Der zu setzende Speiseplan
     */
    public void setMenuPlan(MenuPlan menuPlan) {
        this.menuPlan = menuPlan;
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
     * Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((location == null) ? 0 : location.hashCode());
        result = prime * result
                + ((menuPlan == null) ? 0 : menuPlan.hashCode());
        result = prime * result + numberOfEmployees;
        return result;
    }

    /**
     * Diese Methode prueft, ob das übergebene Objekt gleich dem Objekt ist, von
     * dem die Methode aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Canteen other = (Canteen) obj;
        if (location != other.location)
            return false;
        if (menuPlan == null) {
            if (other.menuPlan != null)
                return false;
        }
        else if (!menuPlan.equals(other.menuPlan))
            return false;
        if (numberOfEmployees != other.numberOfEmployees)
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Canteen}.
     * 
     * @return Die String-Representation von {@link Canteen}
     */
    @Override
    public String toString() {
        return "Canteen [location=" + location + ", numberOfEmployees=" + numberOfEmployees + ", menuPlan=" + menuPlan + "]";
    }
}
