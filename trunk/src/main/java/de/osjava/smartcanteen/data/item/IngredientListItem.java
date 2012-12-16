package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.Amount;

/**
 * Die Klasse {@link IngredientListItem} stellt eine Lebensmittelposition
 * {@link Ingriedent} aus einem Rezept {@link Recipe} dar. Sie enthält einen
 * Verweis auf die jeweilige zu verwendende Zutat ({@link Ingredient}) und einer
 * Menge ({@link Amount}) die angibt, wieviel von der Zutat benutzt werden muss.
 * 
 * @author Francesco Luciano
 */

public class IngredientListItem {

	private Ingredient ingredient;
	private Amount quantity;

	/**
	 * Standardkonstruktor der Klasse {@link IngredientListItem}
	 * 
	 * @param ingredient
	 *            Das Lebenmittel {@link Ingredient}
	 * @param quantity
	 *            Die benötigte Menge {@link Amount} die in dem Rezept
	 *            {@link Recipe} angegeben ist
	 */
	public IngredientListItem(Ingredient ingredient, Amount quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}

	/**
	 * Methode um das Lebensmittel {@link Ingredient} zu ermitteln
	 * 
	 * @return Das Lebensmittel {@link Ingredient}
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}

	/**
	 * Methode um das Lebensmittel {@link Ingredient} zu setzen
	 * 
	 * @param ingredient
	 *            Das Lebensmittel {@link Ingredient}
	 */
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * Methode um die Menge des Lebensmittels zu ermitteln
	 * 
	 * @return Die Menge des Lebensmittels
	 */
	public Amount getQuantity() {
		return quantity;
	}

	/**
	 * Methode um die Menge zu setzen
	 * 
	 * @param quantity
	 *            Die Menge des Lebensmittels
	 */
	public void setQuantity(Amount quantity) {
		this.quantity = quantity;
	}

	/**
	 * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
	 * Methode aufgerufen wurde.
	 * 
	 * @return Der HashCode-Wert des Objekts als int-Representation
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ingredient == null) ? 0 : ingredient.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	/**
	 * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
	 * dem die Methode aufgerufen wurde.
	 * 
	 * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientListItem other = (IngredientListItem) obj;
		if (ingredient == null) {
			if (other.ingredient != null)
				return false;
		} else if (!ingredient.equals(other.ingredient))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	/**
	 * Erstellt die String-Representation des Objekts {@link IngredientListItem}
	 * .
	 * 
	 * @return Die String-Representation von {@link IngredientListItem}
	 */
	@Override
	public String toString() {
		return "IngredientListItem [ingredient=" + ingredient + ", quantity="
				+ quantity + "]";
	}

}
