package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.datatype.CanteenLocation;

/**
 * 
 * @author Tim Sahling
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

    @Override
    public String toString() {
        return "Canteen [location=" + location + ", numberOfEmployees="
                + numberOfEmployees + ", menuPlan=" + menuPlan + "]";
    }

}
