
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
     * Erstellt die String-Representation des Objekts {@link IngredientListItem}.
     * 
     * @return Die String-Representation von {@link IngredientListItem}
     */
    @Override
    public String toString() {
        return null;
    }
}
