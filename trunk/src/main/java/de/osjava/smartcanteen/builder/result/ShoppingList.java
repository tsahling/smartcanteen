package de.osjava.smartcanteen.builder.result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.osjava.smartcanteen.builder.ShoppingListBuilder;
import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Farmer;
import de.osjava.smartcanteen.data.Wholesaler;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

/**
 * Die Klasse {@link ShoppingList} ist das Ergebnis der Geschäftslogikklasse {@link ShoppingListBuilder} und stellt die
 * Einkaufsliste, mit den in ihr enthaltenen Einkaufslistenpositionen, dar.
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

        Map<AbstractProvider, List<ShoppingListItem>> providerShoppingListItems = getShoppingListItemsGroupedByProvider();

        if (providerShoppingListItems != null && !providerShoppingListItems.isEmpty()) {

            for (Entry<AbstractProvider, List<ShoppingListItem>> entry : providerShoppingListItems.entrySet()) {

                Amount transportCosts = null;

                if (entry.getKey() instanceof Farmer) {
                    Farmer farmer = (Farmer) entry.getKey();
                    transportCosts = farmer.calculateTransportCosts();
                }
                else if (entry.getKey() instanceof Wholesaler) {
                    Wholesaler wholesaler = (Wholesaler) entry.getKey();
                    transportCosts = wholesaler.calculateTransportCosts(entry.getValue().size());
                }

                List<ShoppingListItem> shoppingListItems = entry.getValue();

                if (shoppingListItems != null && !shoppingListItems.isEmpty()) {

                    for (ShoppingListItem shoppingListItem : shoppingListItems) {
                        result.add(shoppingListItem.calculatePrice());
                    }
                }

                result.add(transportCosts);
            }

            // Kaufmännisches Runden auf zwei Nachkommastellen
            result.setValue(result.getValue().setScale(2, RoundingMode.HALF_UP));
        }

        return result;
    }

    /**
     * Gruppiert die Einkaufslistenpositionen nach Anbieter
     * 
     * @return Eine Zuordnung von Anbietern zu Einkaufslistenpositionen
     */
    public Map<AbstractProvider, List<ShoppingListItem>> getShoppingListItemsGroupedByProvider() {
        Map<AbstractProvider, List<ShoppingListItem>> result = new HashMap<AbstractProvider, List<ShoppingListItem>>();

        if (shoppingListItems != null && !shoppingListItems.isEmpty()) {

            for (ShoppingListItem shoppingListItem : shoppingListItems) {
                AbstractProvider provider = shoppingListItem.getProvider();

                if (result.containsKey(provider)) {
                    result.get(provider).add(shoppingListItem);
                }
                else {
                    result.put(provider, new ArrayList<ShoppingListItem>(Arrays.asList(shoppingListItem)));
                }
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
