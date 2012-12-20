package de.osjava.smartcanteen.output;

import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

/**
 * Die Klasse {@link HTMLOutput} ist eine Klasse, die das Interface
 * {@link IOutput} implementiert und stellt daher die Methoden die im Interface
 * definiert sind bereit. Die Ausgabe erfolgt als HTML-Seite.
 * 
 * @author Marcel Baxmann
 */
public class HTMLOutput implements IOutput {

	/**
	 * Standardkonstruktor
	 */
	public HTMLOutput() {
	}

	@Override
	public void outputMenuPlan(Canteen canteen) {
	}

	@Override
	public void outputShoppingList(ShoppingList shoppingList) {
	}

	@Override
	public void outputTotalCosts(ShoppingList shoppingList) {
	}
}