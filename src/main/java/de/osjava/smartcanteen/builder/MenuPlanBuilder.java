package de.osjava.smartcanteen.builder;

import java.util.Set;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link MenuPlanBuilder} ist eine der Geschäftslogikklassen und für
 * die Erstellung je eines optimalen Speiseplans für die Kantinen in Essen und
 * Muelheim zuständig. Als Ergebnis füllt die Klasse die {@link MenuPlan}
 * Attribute der {@link Canteen}, die dann im Output bzw. zur Weiterverarbeitung
 * verwendet werden können.
 * 
 * @author Tim Sahling
 */
public class MenuPlanBuilder {

	private ProviderBase providerBase;
	private RecipeBase recipeBase;
	private Canteen[] canteens;

	/**
	 * Der Standardkonstruktor der {@link MenuPlanBuilder} initialisiert die
	 * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten entgegen.
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
	 * Die einzige öffentliche Methode der Klasse {@link MenuPlanBuilder} ruft
	 * die Applikationslogik und den damit verbundenen Optimierungsalgorithmus
	 * für die Generierung des {@link MenuPlan} und der {@link Meal}s auf.
	 * 
	 * @return Ein Array von Kantinen für die Verwendung im Output
	 */
	public Canteen[] buildMenuPlan() {
		Set<Recipe> recipesSortedByRank = recipeBase.getRecipesSortedByRank();

		return canteens;
	}
}
