package de.osjava.smartcanteen.builder;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

/**
 * Die Klasse {@link ShoppingListBuilder} ist eine der Geschäftslogikklassen und
 * für die Erstellung einer Einkaufsliste zuständig, die angibt welche Zutaten
 * in welcher Menge bei welchem Anbieter beschafft werden sollen. Als Ergebnis
 * liefert die Klasse eine {@link ShoppingList}, die dann im Output verwendet
 * werden kann.
 * 
 * @author Tim Sahling
 */
public class ShoppingListBuilder {

	private ProviderBase providerBase;
	private RecipeBase recipeBase;
	private Canteen[] canteens;

	/**
	 * Der Standardkonstruktor der {@link ShoppingListBuilder} initialisiert die
	 * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten entgegen.
	 * 
	 * @param providerBase
	 *            Die {@link ProviderBase} Verwaltungs- bzw. Containerklasse
	 * @param recipeBase
	 *            Die {@link RecipeBase} Verwaltungs- bzw. Containerklasse
	 * @param canteens
	 *            Eine oder mehrere {@link Canteen}
	 */
	public ShoppingListBuilder(ProviderBase providerBase,
			RecipeBase recipeBase, Canteen... canteens) {
		this.providerBase = providerBase;
		this.recipeBase = recipeBase;
		this.canteens = canteens;
	}

	/**
	 * Die einzige öffentliche Methode der Klasse {@link ShoppingListBuilder}
	 * ruft die Applikationslogik und den damit verbundenen
	 * Optimierungsalgorithmus für die Generierung der {@link ShoppingList} auf.
	 * 
	 * @return Eine {@link ShoppingList} für die Verwendung im Output
	 */
	public ShoppingList buildShoppingList() {
		return null;
	}
}