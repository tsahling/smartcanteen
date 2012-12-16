package de.osjava.smartcanteen.output;

import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

/**
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface
 * {@link IOutput} implementiert und stellt daher die Methoden die im Interface
 * definiert sind bereit. Die Ausgabe erfolgt in eine Datei im Dateisystem.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {

	/**
	 * Standardkonstruktor
	 */
	public FileOutput() {
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
