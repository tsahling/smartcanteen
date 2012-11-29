
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
 
        return 0;
    }

    @Override
    public boolean equals(Object obj) {

        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
