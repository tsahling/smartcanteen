import java.util.List;

/**
 * Die Klasse {@link MenuPlan} ist das Ergebnis der Geschaeftslogikklasse 
 * {@link MenuPlanBuilder} und stellt einen Menue-Plan dar. In Ihr wird eine Liste
 * der enthaltenen Essens-Positionen gefuehrt.
 * @author Marcel Baxmann
 */
public class MenuPlan {

    List<Meal> meals;

    /**
     * Standardkonstruktor
     */
    public MenuPlan() {

    }
    
    /**
     * Sortierung der Menue-Liste nach Datum
     * @return sortierte Liste nach Datum
     */
    public List<Meal> getMealsSortedByDate() {
        return null;
    }

    /**
     * Liefert die Menue-Liste
     * @return Die Essen (meals)
     */
    public List<Meal> getMeals() {
        return meals;
    }

    /**
     * Setzt die Menue-List
     * @param meals  the meals to set
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

