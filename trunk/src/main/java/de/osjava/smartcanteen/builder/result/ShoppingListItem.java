package de.osjava.smartcanteen.builder.result;

import java.math.BigDecimal;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

/**
 * Die Klasse {@link ShoppingListItem} stellt eine Einkaufslistenposition dar. Sie enthält einen Verweis auf die
 * jeweilige zu bestellende Zutat ( {@link Ingredient}), eine Menge ({@link Amount}), wieviel von der Zutat bestellt
 * werden soll, und einen Verweis auf den Anbieter ( {@link AbstractProvider}) von dem die Ware bezogen werden soll.
 * Über den Verweis auf den Anbieter und die Menge kann der Preis für ein {@link ShoppingListItem} berechnet werden und
 * muss nicht als extra Attribut enthalten sein.
 * 
 * @author Tim Sahling
 */
public class ShoppingListItem {

    private Ingredient ingredient;
    private Amount quantity;
    private AbstractProvider provider;

    /**
     * Standardkonstruktor
     * 
     * @param ingredient Zutat
     * @param quantity Menge
     * @param provider Anbieter
     */
    public ShoppingListItem(Ingredient ingredient, Amount quantity, AbstractProvider provider) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.provider = provider;
    }

    /**
     * Berechnet den Preis der Einkaufslistenposition.
     * 
     * @return Den Preis der Einkaufslistenposition
     */
    public Amount calculatePrice() {
        Amount result = new Amount(BigDecimal.valueOf(0), UnitOfMeasurement.EUR);

        if (provider != null) {
            result.add(provider.calculatePriceForIngredientAndQuantity(ingredient, quantity));
        }

        return result;
    }

    /**
     * Liefert die Zutat
     * 
     * @return Eine Zutat
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Setzt eine Zutat
     * 
     * @param ingredient
     *            Die zu setzende Zutat
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Liefert die Menge
     * 
     * @return Eine Menge
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * Setzt eine Menge
     * 
     * @param quantity
     *            Die zu setzende Menge
     */
    public void setQuantity(Amount quantity) {
        this.quantity = quantity;
    }

    /**
     * Liefert den Anbieter
     * 
     * @return Ein Anbieter
     */
    public AbstractProvider getProvider() {
        return provider;
    }

    /**
     * Setzt einen Anbieter
     * 
     * @param provider
     *            Der zu setzende Anbieter
     */
    public void setProvider(AbstractProvider provider) {
        this.provider = provider;
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurueck, von dem die
     * Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ingredient == null) ? 0 : ingredient.hashCode());
        result = prime * result
                + ((provider == null) ? 0 : provider.hashCode());
        result = prime * result
                + ((quantity == null) ? 0 : quantity.hashCode());
        return result;
    }

    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist,
     * von dem die Methode aufgerufen wurde.
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
        ShoppingListItem other = (ShoppingListItem) obj;
        if (ingredient == null) {
            if (other.ingredient != null)
                return false;
        }
        else if (!ingredient.equals(other.ingredient))
            return false;
        if (provider == null) {
            if (other.provider != null)
                return false;
        }
        else if (!provider.equals(other.provider))
            return false;
        if (quantity == null) {
            if (other.quantity != null)
                return false;
        }
        else if (!quantity.equals(other.quantity))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link ShoppingListItem}.
     * 
     * @return Die String-Representation von {@link ShoppingListItem}
     */
    @Override
    public String toString() {
        return "ShoppingListItem [ingredient=" + ingredient + ", quantity="
                + quantity + ", provider=" + provider + "]";
    }
}
