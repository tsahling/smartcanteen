package de.osjava.smartcanteen.builder.result;

import java.util.List;

import de.osjava.smartcanteen.builder.MenuPlanBuilder;

/**
 * Die Klasse {@link MenuPlan} ist das Ergebnis der Geschäftslogikklasse
 * {@link MenuPlanBuilder} und stellt einen Speiseplan dar. In ihr wird eine
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
	 * Sortierung der Speisenliste Datum
	 * 
	 * @return Sortierte Liste nach Datum
	 */
	public List<Meal> getMealsSortedByDate() {
		return null;
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
