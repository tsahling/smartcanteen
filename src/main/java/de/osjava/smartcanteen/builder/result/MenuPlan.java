package de.osjava.smartcanteen.builder.result;

import java.util.List;

import de.osjava.smartcanteen.datatype.SortingOrder;

/**
 * 
 * @author Marcel Baxmann
 */
public class MenuPlan {

    List<Meal> meals;

    public MenuPlan() {

    }

    public List<Meal> getMealsSortedByDate(SortingOrder sortOrder) {
        return null;
    }

    /**
     * @return the meals
     */
    public List<Meal> getMeals() {
        return meals;
    }

    /**
     * @param meals
     *            the meals to set
     */
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((meals == null) ? 0 : meals.hashCode());
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
        MenuPlan other = (MenuPlan) obj;
        if (meals == null) {
            if (other.meals != null)
                return false;
        }
        else if (!meals.equals(other.meals))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MenuPlan [meals=" + meals + "]";
    }

}
