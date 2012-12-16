package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;

/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie
 * enthält einen Verweis auf das jeweilige Lebensmittel ({@link Ingredient}) den
 * Preis und die verfügbare Menge.
 * 
 * @author Francesco Luciano
 */
public class PriceListItem {

	private Ingredient ingredient;
	private Amount price;
	private int quantityOfIngredient;

	/**
	 * Standardkonstruktor
	 * 
	 * @param ingredient
	 *            Lebensmittel
	 * @param price
	 *            Preis des Lebensmittels
	 * @param quantityOfIngredient
	 *            Menge des Lebensmittels
	 */
	public PriceListItem(Ingredient ingredient, Amount price,
			int quantityOfIngredient) {
		this.ingredient = ingredient;
		this.price = price;
		this.quantityOfIngredient = quantityOfIngredient;
	}

	/**
	 * Methode um ein Lebensmittel abzufragen.
	 * 
	 * @return Lebenmittel
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}

	/**
	 * Methode um ein Lebensmittel dem Objekt hinzuzufügen
	 * 
	 * @param Lebensmittel
	 */
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * Methode um Preis des Lebensmittels abzufragen
	 * 
	 * @return Preis des Lebensmittels
	 */
	public Amount getPrice() {
		return price;
	}

	/**
	 * Methode um den Preis eines Lebensmittels zu setzen
	 * 
	 * @param price
	 *            Preis des Lebensmittels
	 */
	public void setPrice(Amount price) {
		this.price = price;
	}

	/**
	 * Methode um die Menge eines Lebensmittels abzufragen
	 * 
	 * @return Menge des Lebensmittels
	 */
	public int getQuantityOfIngredient() {
		return quantityOfIngredient;
	}

	/**
	 * Methode um die Menge eines Lebensmittels zu setzen
	 * 
	 * @param price
	 *            Menge des Lebensmittels
	 */
	public void setQuantityOfIngredient(int quantityOfIngredient) {
		this.quantityOfIngredient = quantityOfIngredient;
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
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + quantityOfIngredient;
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
		PriceListItem other = (PriceListItem) obj;
		if (ingredient == null) {
			if (other.ingredient != null)
				return false;
		} else if (!ingredient.equals(other.ingredient))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantityOfIngredient != other.quantityOfIngredient)
			return false;
		return true;
	}

	/**
	 * Erstellt die String-Representation des Objekts {@link PriceListItem}.
	 * 
	 * @return Die String-Representation von {@link PriceListItem}
	 */
	@Override
	public String toString() {
		return "PriceListItem [ingredient=" + ingredient + ", price=" + price
				+ ", quantityOfIngredient=" + quantityOfIngredient + "]";
	}

}
