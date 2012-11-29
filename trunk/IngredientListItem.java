
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
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }

}
