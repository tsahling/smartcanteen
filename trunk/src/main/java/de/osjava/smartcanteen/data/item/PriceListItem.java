package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;

/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie
 * enthält einen Verweis auf das jeweilige Lebensmittel ({@link Ingredient}) den
 * Preis und die verfügbare Menge.
 * 
 * @author Francesco Luciano
 */
public class PriceListItem {

    private Amount size;
    private Ingredient ingredient;
    private Amount price;
    private int availableQuantityOfIngredient;

    /**
     * Standardkonstruktor mit verschiedenen Werten.
     * 
     * @param size Größe und Einheit des Gebindes
     * @param ingredient Name und Typ des Gebindes
     * @param price Preis des Gebindes
     * @param availableQuantityOfIngredient Anzahl vorhandener Gebinde
     */
    public PriceListItem(Amount size, Ingredient ingredient, Amount price, int availableQuantityOfIngredient) {
        this.size = size;
        this.ingredient = ingredient;
        this.price = price;
        this.availableQuantityOfIngredient = availableQuantityOfIngredient;
    }

    /**
     * Methode um die Größe des Gebindes abzufragen.
     * 
     * @return Die Größe des Gebindes
     */
    public Amount getSize() {
        return size;
    }

    /**
     * Methode um die Größe des Gebindes zu setzen.
     * 
     * @param size Die zu setzende Größe des Gebindes
     */
    public void setSize(Amount size) {
        this.size = size;
    }

    /**
     * Methode das Gebinde abzufragen.
     * 
     * @return Das Gebinde
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Methode um ein Gebinde zu setzen.
     * 
     * @param Das zu setzende Gebinde
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Methode um den Preis des Gebindes abzufragen
     * 
     * @return Der Preis des Gebindes
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Methode um den Preis eines Gebindest zu setzen
     * 
     * @param price Der zu setzende Preis des Gebindes
     */
    public void setPrice(Amount price) {
        this.price = price;
    }

    /**
     * Mehtode um die verfügbare Menge des Gebindes abzufragen.
     * 
     * @return Die verfügbare Menge des Gebindes
     */
    public int getAvailableQuantityOfIngredient() {
        return availableQuantityOfIngredient;
    }

    /**
     * Methode um die verfügbare Menge des Gebindes zu setzen
     * 
     * @param availableQuantityOfIngredient Die zu setzende verfügbare Menge des Gebindes
     */
    public void setAvailableQuantityOfIngredient(int availableQuantityOfIngredient) {
        this.availableQuantityOfIngredient = availableQuantityOfIngredient;
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
     * Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + availableQuantityOfIngredient;
        result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    /**
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
     * dem die Methode aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PriceListItem other = (PriceListItem) obj;
        if (availableQuantityOfIngredient != other.availableQuantityOfIngredient)
            return false;
        if (ingredient == null) {
            if (other.ingredient != null)
                return false;
        }
        else if (!ingredient.equals(other.ingredient))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        }
        else if (!price.equals(other.price))
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        }
        else if (!size.equals(other.size))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link PriceListItem}.
     * 
     * @return Die String-Representation von {@link PriceListItem}
     */
    @Override
    public String toString() {
        return "PriceListItem [size=" + size + ", ingredient=" + ingredient + ", price=" + price + ", availableQuantityOfIngredient=" + availableQuantityOfIngredient + "]";
    }
}
