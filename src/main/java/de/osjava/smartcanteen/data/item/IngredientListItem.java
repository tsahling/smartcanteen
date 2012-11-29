package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;

/**
 * 
 * @author Franceso Luciano
 */
public class IngredientListItem {

    private Ingredient ingredient;
    private Amount quantity;

    /**
     * 
     * @param ingredient
     * @param quantity
     */
    public IngredientListItem(Ingredient ingredient, Amount quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    /**
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * @param ingredient the ingredient to set
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * @return the quantity
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Amount quantity) {
        this.quantity = quantity;
    }

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
        }
        else if (!ingredient.equals(other.ingredient))
            return false;
        if (quantity == null) {
            if (other.quantity != null)
                return false;
        }
        else if (!quantity.equals(other.quantity))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IngredientListItem [ingredient=" + ingredient + ", quantity="
                + quantity + "]";
    }

}
