
/**
 * 
 * @author Franceso Luciano
 */
public class PriceListItem {

    private Ingredient ingredient;
    private Amount price;
    private int quantityOfIngredient;

    /**
     * 
     * @param ingredient
     * @param price
     * @param quantityOfIngredient
     */
    public PriceListItem(Ingredient ingredient, Amount price, int quantityOfIngredient) {
        this.ingredient = ingredient;
        this.price = price;
        this.quantityOfIngredient = quantityOfIngredient;
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

    /**
     * Diese Methode gibt den HashCode-Wert fuer das Objekt zurueck, von dem die Methode aufgerufen 
     * wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
          return 0;
    }
    
    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist, von dem die Methode 
     * aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link PriceListItem}.
     * 
     * @return Die String-Representation von {@link PriceListItem}
     */
    @Override
    public String toString() {
        return null;
    }
}
