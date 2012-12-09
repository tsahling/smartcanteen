/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie enthaelt einen Verweis 
 * auf das jeweilige Lebensmittel ({@link Ingredient}) den Preis {@link price} und die verfügbare Menge {@link quantityOfIngredient}.
 * 
 * @author Francesco Luciano
 */
public class PriceListItem {

    private Ingredient ingredient;
    private Amount price;
    private int quantityOfIngredient;

    /**
     * 
     * @param ingredient Lebensmittel
     * @param price Preis des Lebensmittel
     * @param quantityOfIngredient Menge des Lebensmittel
     */
    public PriceListItem(Ingredient ingredient, Amount price, int quantityOfIngredient) {
        this.ingredient = ingredient;
        this.price = price;
        this.quantityOfIngredient = quantityOfIngredient;
    }

    /**
     * Methode um ein Lebensmittel abzufragen.
     * @return Lebenmittel
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Methode um ein Lebensmittel dem Objekt {@link PriceListItem} hinzuzufügen
     * @param Lebensmittel
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Methode um Preis des Lebensmittel abzufragen
     * @return Preis des Lebensmittel
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Methode um Preis eines Lebensmittel zu setzen
     * @param price Preis des Lebensmittel
     */
    public void setPrice(Amount price) {
        this.price = price;
    }

    /**
     * Methode um Preis die Menge eines Lebensmittel abzufragen
     * @return Menge des Lebensmittel
     */
    public int getQuantityOfIngredient() {
        return quantityOfIngredient;
    }

      /**
     * Methode um Menge eines Lebensmittel zu setzen
     * @param price Menge des Lebensmittel
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
