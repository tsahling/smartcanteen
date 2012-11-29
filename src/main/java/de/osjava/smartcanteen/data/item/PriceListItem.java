package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;

public class PriceListItem {

	private Ingredient ingredient;
	private Amount price;
	private int quantityOfIngredient;

	public PriceListItem() {

	}

	/**
	 * @return the ingredient
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}

	/**
	 * @param ingredient
	 *            the ingredient to set
	 */
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * @return the price
	 */
	public Amount getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Amount price) {
		this.price = price;
	}

	/**
	 * @return the quantityOfIngredient
	 */
	public int getQuantityOfIngredient() {
		return quantityOfIngredient;
	}

	/**
	 * @param quantityOfIngredient
	 *            the quantityOfIngredient to set
	 */
	public void setQuantityOfIngredient(int quantityOfIngredient) {
		this.quantityOfIngredient = quantityOfIngredient;
	}

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

	@Override
	public String toString() {
		return "PriceListItem [ingredient=" + ingredient + ", price=" + price
				+ ", quantityOfIngredient=" + quantityOfIngredient + "]";
	}

}
