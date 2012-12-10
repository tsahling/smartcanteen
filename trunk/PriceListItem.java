/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie enthaelt einen Verweis 
 * auf das jeweilige Lebensmittel ({@link Ingredient}) den Preis und die verfuegbare Menge.
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
     * @param price Preis des Lebensmittels
     * @param quantityOfIngredient Menge des Lebensmittels
     */
    public PriceListItem(Ingredient ingredient, Amount price, int quantityOfIngredient) {
        this.ingredient = ingredient;
        this.price = price;
        this.quantityOfIngredient = quantityOfIngredient;
    }

    /**
     * Methode um ein Lebensmittel abzufragen.
     * 
     * @return Lebenmittel
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Methode um ein Lebensmittel dem Objekt hinzuzufuegen
     * 
     * @param Lebensmittel
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Methode um Preis des Lebensmittels abzufragen
     * 
     * @return Preis des Lebensmittels
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Methode um Preis eines Lebensmittels zu setzen
     * 
     * @param price Preis des Lebensmittels
     */
    public void setPrice(Amount price) {
        this.price = price;
    }

    /**
     * Methode um die Menge eines Lebensmittels abzufragen
     * 
     * @return Menge des Lebensmittels
     */
    public int getQuantityOfIngredient() {
        return quantityOfIngredient;
    }

    /**
     * Methode um die Menge eines Lebensmittels zu setzen
     * 
     * @param price Menge des Lebensmittels
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
