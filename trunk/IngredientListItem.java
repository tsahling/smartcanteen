
/**
 * Die Klasse {@link IngredientListItem} stellt eine Lebensmittelposition {@link Ingriedent} aus einem Rezept {@link Recipe} in dem Set (@link ingredientList) dar.
 * Sie enthaelt einen Verweis auf die jeweilige zu verwendene Zutat ({@link Ingredient}), eine Menge ({@link Amount}) die angibt wieviel von 
 * der Zutat benutzt werden muss.
 * 
 * @author Francesco Luciano
 */

public class IngredientListItem {

    private Ingredient ingredient;
    private Amount quantity;

    /**
     * Standartkonstruktor der Klasse {@link IngredientListItem}
     * @param ingredient Das Lebenmittel {@link Ingredient}
     * @param quantity Die benötigte Menge {@link Amount} die in dem Rezept {@link Recipe} angeben ist
     */
    public IngredientListItem(Ingredient ingredient, Amount quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    /**
     * Methode um das Lebensmittel {@link Ingredient} zu ermitteln 
     * @return Das Lebensmittel {@link ingredient}
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Methode um das Lebensmittel {@link ingredient} zu setzen
     * @param ingredient Das Lebensmittel {@link ingredient}
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Methode um die Menge {@link quantity} des Lebensmittel {@link ingredient} zu ermitteln 
     * @return Die Menge {@link quantity} des Lebensmittel {@link ingredient}
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * Methode um die Menge {@link quantity} zu setzen
     * @param quantity Die Menge {@link quantity} des Lebensmittel {@link ingredient}
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
