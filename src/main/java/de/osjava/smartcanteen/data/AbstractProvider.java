package de.osjava.smartcanteen.data;

import java.math.BigDecimal;
import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link AbstractProvider} ist eine der Fach- bzw.
 * Datenträgerklassen und als abstrakte, generalistische Klasse ausgelegt, die
 * mit den beiden Klassen {@link Farmer} und {@link Wholesaler} verschiedene
 * Spezialisierungen hat. Sie stellt die Basis für die unterschiedlichen
 * Anbieter von für den Kantinenbetrieb benötigten Lebensmitteln und deren
 * jeweiligen Preise dar.
 * 
 * @author Tim Sahling
 */
public abstract class AbstractProvider {

    private String name;
    private Set<PriceListItem> priceList;

    /**
     * Standardkonstruktor
     * 
     * @param name Name des Anbieters
     * @param priceList Preisliste des Anbieters
     */
    public AbstractProvider(String name, Set<PriceListItem> priceList) {
        this.name = name;
        this.priceList = priceList;
    }

    /**
     * Überprüft ob ein Anbieter eine Zutat in einer bestimmten Menge vorrätig hat
     * 
     * @param ingredient
     * @param quantity
     * @return
     */
    public boolean hasIngredientWithQuantity(Ingredient ingredient, Amount quantity) {
        boolean result = false;

        if (priceList != null && !priceList.isEmpty()) {

            for (PriceListItem priceListItem : priceList) {

                if (priceListItem.getIngredient().equals(ingredient)) {

                    BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

                    if (NumberHelper.compareGreaterOrEqual(priceListItem.getAvailableQuantityOfIngredient(),
                            quantityOfIngredient)) {
                        result = true;
                    }

                    break;
                }
            }
        }

        return result;
    }

    /**
     * Subtrahiert eine Menge von einer Zutat.
     * 
     * @param ingredient
     * @param quantity
     */
    public void subtractQuantityFromIngredient(Ingredient ingredient, Amount quantity) {
        if (priceList != null && !priceList.isEmpty()) {

            for (PriceListItem priceListItem : priceList) {

                if (priceListItem.getIngredient().equals(ingredient)) {

                    BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

                    if (quantityOfIngredient != null) {
                        priceListItem.setAvailableQuantityOfIngredient(NumberHelper.subtract(
                                priceListItem.getAvailableQuantityOfIngredient(), quantityOfIngredient));
                    }

                    break;
                }
            }
        }
    }

    /**
     * Kalkuliert einen Preis für eine Zutat und eine Menge bei einem Anbieter. Transportkosten der Anbieter werden an
     * dieser Stelle nicht berücksichtigt, sondern nur beim Berechnen der gesamten Einkaufsliste.
     * 
     * @param ingredient
     * @param quantity
     * @return
     */
    public Amount calculatePriceForIngredientAndQuantity(Ingredient ingredient, Amount quantity) {
        Amount result = new Amount(BigDecimal.valueOf(0), UnitOfMeasurement.EUR);

        if (priceList != null && !priceList.isEmpty()) {

            for (PriceListItem priceListItem : priceList) {

                if (priceListItem.getIngredient().equals(ingredient)) {

                    BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

                    result.add(new Amount(NumberHelper.multiply(quantityOfIngredient, priceListItem.getPrice()
                            .getValue()), UnitOfMeasurement.EUR));

                    break;
                }
            }
        }

        return result;
    }

    /**
     * Methode um den {@link Amount} einer {@link Ingredient} zu ermitteln.
     * 
     * @param ingredient Die zu suchende {@link Ingredient}
     * @return Den {@link Amount} der {@link Ingredient}
     */
    public Amount findQuantityByIngredient(Ingredient ingredient) {
        Amount result = null;

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {
            result = priceListItem.multiplyAvailableQuantityWithSize();
        }

        return result;
    }

    /**
     * 
     * @param ingredient
     * @return
     */
    public PriceListItem findPriceListItemByIngredient(Ingredient ingredient) {
        PriceListItem result = null;

        if (priceList != null && !priceList.isEmpty()) {

            for (PriceListItem priceListItem : priceList) {

                if (priceListItem.getIngredient().equals(ingredient)) {
                    result = priceListItem;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Liefert den Namen des Anbieters
     * 
     * @return Der Name des Anbieters
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Anbieters.
     * 
     * @param name
     *            Der zu setzende Name des Anbieters
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert eine Liste mit Preislistenpositionen
     * 
     * @return Die Preislistenpositionen
     */
    public Set<PriceListItem> getPriceList() {
        return priceList;
    }

    /**
     * Setzt die Preislistenpositionen.
     * 
     * @param priceList
     *            Die zu setzenden Preislistenpositionen
     */
    public void setPriceList(Set<PriceListItem> priceList) {
        this.priceList = priceList;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((priceList == null) ? 0 : priceList.hashCode());
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
        AbstractProvider other = (AbstractProvider) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (priceList == null) {
            if (other.priceList != null)
                return false;
        }
        else if (!priceList.equals(other.priceList))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link AbstractProvider}.
     * 
     * @return Die String-Representation von {@link AbstractProvider}
     */
    @Override
    public String toString() {
        return "AbstractProvider [name=" + name + ", priceList=" + priceList
                + "]";
    }
}
