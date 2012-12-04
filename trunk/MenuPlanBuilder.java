
/**
 * Die Klasse {@link MenuPlanBuilder} ist eine der Geschaeftslogikklassen und für die
 * Erstellung je eines "optimalen" Speiseplans für die Kantinen in Essen und
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
	 * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten bzw. -klassen entgegen.
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
	 * Die einzige oeffentliche Methode der Klasse {@link MenuPlanBuilder} ruft die Applikationslogik 
	 * und den damit verbundenen Optimierungsalgorithmus für die Generierung des {@link MenuPlan} 
	 * und der {@link Meal}s auf.
	 * 
	 * @return Eine {@link ShoppingList} fuer die Verwendung im Output
	 */
	public Canteen[] buildMenuPlan() {
		return canteens;
	}
}
