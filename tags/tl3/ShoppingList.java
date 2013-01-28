import java.util.List;

/**
 * Die Klasse {@link ShoppingList} ist das Ergebnis der Geschaeftslogikklasse 
 * {@link ShoppingListBuilder} und stellt die Einkaufsliste, mit den in ihr enthaltenen 
 * Einkaufslistenpositionen, dar.
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
        return null;
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
     * @param shoppingListItems Die zu setzenden Einkaufslistenpositionen
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
        return null;
    }
}
