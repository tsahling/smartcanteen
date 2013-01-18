package de.osjava.smartcanteen.builder.result;

import java.math.BigDecimal;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

/**
 * Die Klasse {@link ShoppingListItem} stellt eine Einkaufslistenposition dar. Sie enthält einen Verweis auf die
 * jeweilige zu bestellende {@link Ingredient}, eine {@link Amount}, wieviel von der Zutat bestellt werden soll, und
 * einen Verweis auf den {@link AbstractProvider} von dem die Ware bezogen werden soll. Über den Verweis auf den
 * Anbieter und die Menge kann der Preis für ein {@link ShoppingListItem} berechnet werden und muss nicht als extra
 * Attribut enthalten sein.
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
     * @param ingredient {@link Ingredient}
     * @param quantity {@link Amount} der {@link Ingredient}
     * @param provider {@link AbstractProvider}
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

        if (this.provider != null) {
            result.add(this.provider.calculatePriceForIngredientAndQuantity(this.ingredient, this.quantity));
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
     * Liefert die {@link Amount}.
     * 
     * @return Die {@link Amount} der {@link Ingredient} die bestellt werden soll
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
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
        result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
        result = prime * result + ((provider == null) ? 0 : provider.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
        return result;
    }

    /**
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
     * dem die Methode aufgerufen wurde.
     * 
     * @return WAHR/FALSCH, je nachdem ob die zu vergleichenden Objekte gleich sind
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
     * Erstellt die String-Representation des Objekts.
     * 
     * @return Die String-Representation des Objekts
     */
    @Override
    public String toString() {
        return "ShoppingListItem [ingredient=" + ingredient + ", quantity=" + quantity + ", provider=" + provider + "]";
    }
}