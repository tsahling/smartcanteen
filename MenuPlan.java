import java.util.List;

/**
 * 
 * @author Marcel Baxmann
 */
public class MenuPlan {

    List<Meal> meals;

    public MenuPlan() {

    }

    public List<Meal> getMealsSortedByDate() {
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

