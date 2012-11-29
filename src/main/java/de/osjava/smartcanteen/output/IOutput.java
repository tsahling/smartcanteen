package de.osjava.smartcanteen.output;

import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

public interface IOutput {

	void outputMenuPlan(Canteen canteen);
	
	void outputShoppingList(ShoppingList shoppingList);
	
	void outputTotalCosts(ShoppingList shoppingList);
}
