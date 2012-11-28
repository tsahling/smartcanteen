package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.result.MenuPlan;

public class Canteen {

    private String name;
    private CanteenLocation location;
    private MenuPlan menuPlan;

    public Canteen(String name, CanteenLocation location) {
        this.name = name;
        this.location = location;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the location
     */
    public CanteenLocation getLocation() {
        return location;
    }

    /**
     * Sets the location.
     * 
     * @param location the location to set
     */
    public void setLocation(CanteenLocation location) {
        this.location = location;
    }

    /**
     * @return the menuPlan
     */
    public MenuPlan getMenuPlan() {
        return menuPlan;
    }

    /**
     * Sets the menuPlan.
     * 
     * @param menuPlan the menuPlan to set
     */
    public void setMenuPlan(MenuPlan menuPlan) {
        this.menuPlan = menuPlan;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((menuPlan == null) ? 0 : menuPlan.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Canteen [name=" + name + ", location=" + location + ", menuPlan=" + menuPlan + "]";
    }
}
