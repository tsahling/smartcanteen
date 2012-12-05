package de.osjava.smartcanteen.builder;

import java.util.Set;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.helper.PropertyHelper;

// Hallo Herr Wegener,
//
// der Ausgangspunkt des Projekts waren die wirschaftlichen �berlegungen, daher ist die Kostenverbesserung als
// wichtigeres Ziel zu betrachten. Die Bewertung der Gerichte ist auch wichtig, aber weniger bedeutsam einzustufen als
// die Kosten. Ausgenommen sind dabei die Anforderungen zu vegetarischen Gerichten, Fisch und Fleisch: Dies sind
// muss-Kriterien die Erf�llt sein m�ssen. Auch darf es keine Wiederholungen von Gerichten innerhalb eines
// Speiseplans
// geben, ich bin nicht sicher ob wir das so explizit gesagt haben.
//
// Viele Gr��e
//
// F. Gierig

/**
 * Die {@link MenuPlanBuilder} ist eine der Geschaeftslogikklassen und fuer die
 * Erstellung je eines "optimalen" Speiseplans fuer die Kantinen in Essen und
 * Muelheim zustaendig. Als Ergebnis fuellt die Klasse die {@link MenuPlan}
 * Attribute der {@link Canteen}, die dann im Output bzw. zur Weiterverarbeitung
 * verwendet werden koennen.
 * 
 * @author Tim Sahling
 */
public class MenuPlanBuilder {

	private ProviderBase providerBase;
	private RecipeBase recipeBase;
	private Canteen[] canteens;

	/**
	 * Der Standardkonstruktor der {@link MenuPlanBuilder} initialisiert die
	 * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten bzw.
	 * -klassen entgegen.
	 * 
	 * @param providerBase
	 *            Die {@link ProviderBase} Verwaltungs- bzw. Containerklasse
	 * @param recipeBase
	 *            Die {@link RecipeBase} Verwaltungs- bzw. Containerklasse
	 */
	public MenuPlanBuilder(ProviderBase providerBase, RecipeBase recipeBase) {
		this.providerBase = providerBase;
		this.recipeBase = recipeBase;
		this.canteens = new Canteen[] {
				new Canteen(
						CanteenLocation.ESSEN,
						Integer.valueOf(PropertyHelper
								.getProperty("canteen.essen.numberOfEmployees"))),
				new Canteen(
						CanteenLocation.MUELHEIM,
						Integer.valueOf(PropertyHelper
								.getProperty("canteen.muelheim.numberOfEmployees"))) };
	}

	/**
	 * Die einzige oeffentliche Methode der Klasse {@link MenuPlanBuilder} ruft
	 * die Applikationslogik und den damit verbundenen Optimierungsalgorithmus
	 * fuer die Generierung des {@link MenuPlan} und der {@link Meal}s auf.
	 * 
	 * @return Eine {@link ShoppingList} fuer die Verwendung im Output
	 */
	public Canteen[] buildMenuPlan() {

		// Varianten
		//
		// 1. Günstige Preise
		//
		// 1.1 Suche der am günstigsten zu kochenden Rezepte pro Kantine
		// (Mitarbeiterzahl beachten) unter Berücksichtigung der bereits
		// ausgewählten gerichte
		// 1.2 Auswahl der Gerichte für einen Tag die am günstigsten zu kochen
		// sind und am beliebtesten bei den mitarbeitern und anforderungen an
		// fish, meat und vegetable abdecken

		// 2. Beliebteste Gerichte

		// 2.1 Suche der beliebtesten Gerichte
		// 2.2 Suche der günstigen Anbieter für Gerichte mit Bezug auf
		// Mitarbeiteranzahl
		// 2.3 Auswahl der Anbieter unter Berücksichtigung der anforderungen
		// fish, meat, vegetable

		Set<Recipe> recipes = recipeBase.getRecipes();

		for (Recipe recipe : recipes) {
			Set<IngredientListItem> ingredientList = recipe.getIngredientList();

		}

		return canteens;
	}

	private class TestTupel {

	}
}
