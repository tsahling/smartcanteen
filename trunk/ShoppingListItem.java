
/**
 * Die Klasse {@link ShoppingListItem} stellt eine Einkaufslistenposition dar. Sie enthaelt einen Verweis 
 * auf die jeweilige zu bestellende Zutat ({@link Ingredient}), eine Menge ({@link Amount}), wieviel von 
 * der Zutat bestellt werden soll, und einen Verweis auf den Anbieter ({@link Provider}) von dem die Ware 
 * bezogen werden soll. Ueber den Verweis auf den Anbieter und die Menge kann der Preis für ein 
 * {@link ShoppingListItem} berechnet werden und muss nicht als extra Attribut enthalten sein.
 * 
 * @author Tim Sahling
 */
public class ShoppingListItem {

    private Ingredient ingredient;
    private Amount quantity;
    private Provider provider;

    /**
     * Standardkonstruktor
     */
    public ShoppingListItem() {

    }

    /**
     * Berechnet den Preis der Einkaufslistenposition.
     * 
     * @return Den Preis der Einkaufslistenposition
     */
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
     * Erstellt die String-Representation des Objekts {@link ShoppingListItem}.
     * 
     * @return Die String-Representation von {@link ShoppingListItem}
     */
    @Override
    public String toString() {
        return null;
    }
}
