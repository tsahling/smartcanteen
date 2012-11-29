
/**
 * 
 * @author Tim Sahling
 */
public class ShoppingListItem {

    private Ingredient ingredient;
    private Amount quantity;
    private Provider provider;

    public ShoppingListItem() {

    }

    public Amount calculatePrice() {
        return null;
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
     * @return the quantity
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(Amount quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * @param provider
     *            the provider to set
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
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
