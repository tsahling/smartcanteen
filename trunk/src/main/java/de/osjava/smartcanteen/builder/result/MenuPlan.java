package de.osjava.smartcanteen.builder.result;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.osjava.smartcanteen.builder.MenuPlanBuilder;

/**
 * Die Klasse {@link MenuPlan} ist das Ergebnis der Geschäftslogikklasse {@link MenuPlanBuilder} und stellt einen
 * Speiseplan dar. In ihr wird eine
 * Liste der enthaltenen Speisen geführt.
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

    /**
     * Sortierung der Gerichte nach Datum
     * 
     * @return Sortierte Liste mit Gerichten nach Datum
     */
    public List<Meal> getMealsSortedByDate() {
        if (meals != null && !meals.isEmpty()) {
            Collections.sort(meals, new Comparator<Meal>() {

                @Override
                public int compare(Meal arg0, Meal arg1) {
                    return arg0.getDate().compareTo(arg1.getDate());
                }
            });
        }

        return meals;
    }

    /**
     * Gruppiert die Gerichte eines Speiseplans nach Datum
     * 
     * @return Eine Zuordnung von Daten zu Gerichten, die an dem jeweiligen Datum gekocht werden sollen
     */
    public Map<Date, List<Meal>> getMealsGroupedByDate() {
        Map<Date, List<Meal>> result = new HashMap<Date, List<Meal>>();

        if (meals != null && !meals.isEmpty()) {

            for (Meal meal : meals) {
                Date date = meal.getDate();

                if (result.containsKey(date)) {
                    result.get(date).add(meal);
                }
                else {
                    result.put(date, new LinkedList<Meal>(Arrays.asList(meal)));
                }
            }
        }

        return result;
    }

    /**
     * Liefert die Speisenliste
     * 
     * @return Die Speisen
     */
    public List<Meal> getMeals() {
        return meals;
    }

    /**
     * Setzt die Speisenliste
     * 
     * @param meals
     *            Die zu setzende Speisenliste
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
        return "MenuPlan [meals=" + meals + "]";
    }

}
