package de.osjava.smartcanteen.base;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;

public class PriceListItem {

    private Ingredient ingredient;
    private Amount amount;
    private int maxAmountOfIngredient;

    public PriceListItem() {

    }

    /**
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Sets the ingredient.
     * 
     * @param ingredient the ingredient to set
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * @return the amount
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * 
     * @param amount the amount to set
     */
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    /**
     * @return the maxAmountOfIngredient
     */
    public int getMaxAmountOfIngredient() {
        return maxAmountOfIngredient;
    }

    /**
     * Sets the maxAmountOfIngredient.
     * 
     * @param maxAmountOfIngredient the maxAmountOfIngredient to set
     */
    public void setMaxAmountOfIngredient(int maxAmountOfIngredient) {
        this.maxAmountOfIngredient = maxAmountOfIngredient;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
        result = prime * result + maxAmountOfIngredient;
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
        if (amount == null) {
            if (other.amount != null)
                return false;
        }
        else if (!amount.equals(other.amount))
            return false;
        if (ingredient == null) {
            if (other.ingredient != null)
                return false;
        }
        else if (!ingredient.equals(other.ingredient))
            return false;
        if (maxAmountOfIngredient != other.maxAmountOfIngredient)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PriceListItem [ingredient=" + ingredient + ", amount=" + amount + ", maxAmountOfIngredient=" + maxAmountOfIngredient + "]";
    }
}
