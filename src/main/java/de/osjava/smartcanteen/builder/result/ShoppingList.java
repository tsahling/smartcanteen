package de.osjava.smartcanteen.builder.result;

import java.math.BigDecimal;
import java.util.List;

import de.osjava.smartcanteen.builder.ShoppingListBuilder;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

/**
 * Die Klasse {@link ShoppingList} ist das Ergebnis der Geschäftslogikklasse {@link ShoppingListBuilder} und stellt die
 * Einkaufsliste, mit den in ihr
 * enthaltenen Einkaufslistenpositionen, dar.
 * 
 * @author Tim Sahling
 */
public class ShoppingList {

    private List<ShoppingListItem> shoppingListItems;

    /**
     * Standardkonstruktor
     */
    public ShoppingList() {
    }

    /**
     * Summiert die Preise aller Einkaufslistenpositionen zu einer Gesamtsumme.
     * 
     * @return Die Gesamtsumme aller Einkaufslistenpositionen
     */
    public Amount calculateTotalPrice() {
        Amount result = new Amount(BigDecimal.valueOf(0), UnitOfMeasurement.EUR);

        if (shoppingListItems != null && !shoppingListItems.isEmpty()) {

            for (ShoppingListItem shoppingListItem : shoppingListItems) {
                result.add(shoppingListItem.calculatePrice());
            }
        }

        return result;
    }

    /**
     * @return Die Einkaufslistenpositionen
     */
    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    /**
     * Setzt die Einkaufslistenpositionen.
     * 
     * @param shoppingListItems
     *            Die zu setzenden Einkaufslistenpositionen
     */
    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link ShoppingList}.
     * 
     * @return Die String-Representation von {@link ShoppingList}
     */
    @Override
    public String toString() {
        return "ShoppingList [shoppingListItems=" + shoppingListItems + "]";
    }

}
