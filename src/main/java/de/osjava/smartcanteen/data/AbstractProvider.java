package de.osjava.smartcanteen.data;

import java.math.BigDecimal;
import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link AbstractProvider} ist eine der Fach- bzw. Datenträgerklassen und als abstrakte, generalistische
 * Klasse ausgelegt, die mit den beiden Klassen {@link Farmer} und {@link Wholesaler} verschiedene Spezialisierungen
 * hat. Sie stellt die Basis für die unterschiedlichen Anbieter von für den Kantinenbetrieb benötigten Lebensmitteln und
 * deren jeweiligen Preise dar.
 * 
 * @author Tim Sahling
 */
public abstract class AbstractProvider {

    private String name;
    private Set<PriceListItem> priceList;

    /**
     * Standardkonstruktor
     * 
     * @param name Name des {@link AbstractProvider}
     * @param priceList Preisliste des {@link AbstractProvider}
     */
    public AbstractProvider(String name, Set<PriceListItem> priceList) {
        this.name = name;
        this.priceList = priceList;
    }

    /**
     * Überprüft ob ein {@link AbstractProvider} eine Zutat in einer bestimmten Menge vorrätig hat
     * 
     * @param ingredient Die {@link Ingredient}, die überprüft werden soll
     * @param quantity Die {@link Amount}, der überprüft werden soll
     * @return wahr/falsch, je nachdem ob der {@link AbstractProvider} die {@link Ingredient} in der benötigten
     *         {@link Amount} vorrätig hat
     */
    public boolean hasIngredientWithQuantity(Ingredient ingredient, Amount quantity) {
        boolean result = false;

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {
            BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

            if (NumberHelper.compareGreaterOrEqual(priceListItem.getAvailableQuantityOfIngredient(),
                    quantityOfIngredient)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Berechnet auf Basis der {@link Ingredient} und der {@link Amount} den tatsächlich vom {@link AbstractProvider} zu
     * bestellenden {@link Amount}. Diese Funktion liegt darin begründet, dass {@link AbstractProvider} nur ganze
     * Gebinde verkaufen und der benötigte {@link Amount} zwischen Gebindegrößen liegen kann.
     * 
     * @param ingredient Die {@link Ingredient}, die bestellt werden soll
     * @param quantity Die {@link Amount}, der bestellt werden soll
     * @return Die {@link Amount} an Gebinden, die bestellt werden muss
     */
    public Amount calculateQuantityFromIngredientAndQuantity(Ingredient ingredient, Amount quantity) {
        Amount result = null;

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {
            BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

            if (quantityOfIngredient != null) {
                // Multipliziert die Größe des Gebindes mit der berechneten und benötigten Menge an Gebinden
                result = new Amount(NumberHelper.multiply(priceListItem.getSize().getValue(), quantityOfIngredient),
                        priceListItem.getSize().getUnit());
            }
        }

        return result;
    }

    /**
     * Berechnet auf Basis der {@link Ingredient} und der {@link Amount} die Ausschussmenge die erzeugt werden würde,
     * wenn diese Zutaten/Mengen-Kombination bei dem {@link AbstractProvider} tatsächlich bestellt werden würde.
     * 
     * @param ingredient Die {@link Ingredient}, die bestellt werden soll
     * @param quantity Die {@link Amount}, der bestellt werden soll
     * @return Die {@link Amount} an Ausschuss, der erzeugt werden würde
     */
    public Amount calculateWasteQuantityFromIngredientAndQuantity(Ingredient ingredient, Amount quantity) {
        Amount result = null;

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {
            BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

            if (quantityOfIngredient != null) {
                // Multipliziert die Größe des Gebindes mit der berechneten und benötigten Menge an Gebinden und
                // subtrahiert danach von diesem Produkt, die tatsächlich benötigte Menge
                result = new Amount(NumberHelper.subtract(
                        NumberHelper.multiply(priceListItem.getSize().getValue(), quantityOfIngredient),
                        quantity.getValue()), priceListItem.getSize().getUnit());
            }
        }

        return result;
    }

    /**
     * Subtrahiert eine {@link Amount} von einer {@link Ingredient} von der beim {@link AbstractProvider} verfügbaren
     * Menge.
     * 
     * @param ingredient Die {@link Ingredient}, die subtrahiert werden soll
     * @param quantity Die {@link Amount}, der subtrahiert werden soll
     */
    public void subtractQuantityFromIngredient(Ingredient ingredient, Amount quantity) {

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {
            BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

            if (quantityOfIngredient != null) {
                priceListItem.setAvailableQuantityOfIngredient(NumberHelper.subtract(
                        priceListItem.getAvailableQuantityOfIngredient(), quantityOfIngredient));
            }
        }
    }

    /**
     * Berechnet einen Preis für eine {@link Ingredient} und eine {@link Amount} bei einem {@link AbstractProvider}.
     * Transportkosten der Anbieter werden an dieser Stelle nicht berücksichtigt, sondern nur beim Berechnen der
     * gesamten Einkaufsliste.
     * 
     * @param ingredient Die {@link Ingredient}, die berechnet werden soll
     * @param quantity Die {@link Amount}, die berechnet werden soll
     * @return Die berechnete {@link Amount} für die übergebene {@link Ingredient} und benötigte {@link Amount}
     */
    public Amount calculatePriceForIngredientAndQuantity(Ingredient ingredient, Amount quantity) {
        Amount result = new Amount(BigDecimal.valueOf(0), UnitOfMeasurement.EUR);

        PriceListItem priceListItem = findPriceListItemByIngredient(ingredient);

        if (priceListItem != null) {

            BigDecimal quantityOfIngredient = priceListItem.divideQuantityWithSize(quantity);

            result.add(new Amount(NumberHelper.multiply(priceListItem.getPrice().getValue(), quantityOfIngredient),
                    UnitOfMeasurement.EUR));
        }

        return result;
    }

    /**
     * Methode um den {@link Amount} einer {@link Ingredient} zu ermitteln.
     * 
     * @param ingredient Die zu suchende {@link Ingredient}
     * @return Die {@link Amount} der {@link Ingredient}
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
     * Methode um ein {@link PriceListItem} auf Basis einer {@link Ingredient} zu ermitteln.
     * 
     * @param ingredient Die zu suchende {@link Ingredient}
     * @return Das {@link PriceListItem}, dass auf Basis der {@link Ingredient} gefunden wurde
     */
    public PriceListItem findPriceListItemByIngredient(Ingredient ingredient) {
        PriceListItem result = null;

        if (this.priceList != null && !this.priceList.isEmpty()) {

            for (PriceListItem priceListItem : this.priceList) {

                if (priceListItem.getIngredient().equals(ingredient)) {
                    result = priceListItem;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Liefert den Namen des Anbieters.
     * 
     * @return Der Name des Anbieters
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert eine Liste mit Preislistenpositionen.
     * 
     * @return Die Preislistenpositionen
     */
    public Set<PriceListItem> getPriceList() {
        return priceList;
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
