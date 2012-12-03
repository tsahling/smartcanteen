import java.util.List;

/**
 * 
 * @author Marcel Baxmann
 */
public class MenuPlan {

    List<Meal> meals;

    /**
     * Standardkonstruktor
     */
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

    /**
     * Erstellt die String-Representation des Objekts {@link MenuPlan}.
     * 
     * @return Die String-Representation von {@link MenuPlan}
     */
    @Override
    public String toString() {
        return null;
    }
}

