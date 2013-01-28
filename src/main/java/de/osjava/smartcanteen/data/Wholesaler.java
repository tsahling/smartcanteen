package de.osjava.smartcanteen.data;

import java.math.BigDecimal;
import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link Wholesaler} ist eine Spezialisierung der Fach- bzw.
 * Datenträgerklasse {@link AbstractProvider} und stellt einen Grosshändler dar.
 * 
 * @author Tim Sahling
 */
public class Wholesaler extends AbstractProvider {

    private Amount transportFee;

    /**
     * Standardkonstruktor
     * 
     * @param name
     * @param priceList
     * @param transportFee
     */
    public Wholesaler(String name, Set<PriceListItem> priceList, Amount transportFee) {
        super(name, priceList);
        this.transportFee = transportFee;
    }

    /**
     * Kalkuliert die Transportkosten für einen {@link Wholesaler}. Der {@link Wholesaler} hat einen Lieferkostensatz,
     * der mit der Anzahl Artikel, die bei ihm bestellt werden sollen, multipliziert wird. Dieses Berechnungsverfahren
     * gilt für alle {@link Wholesaler}.
     * 
     * @param numberOfItemsToOrder Anzahl an Zutaten, die bei dem {@link Wholesaler} bestellt werden
     * @return Die Transportkosten für einen {@link Wholesaler}
     */
    public Amount calculateTransportCosts(BigDecimal numberOfIngredientsToOrder) {
        return new Amount(NumberHelper.multiply(this.transportFee.getValue(), numberOfIngredientsToOrder),
                UnitOfMeasurement.EUR);
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
        int result = super.hashCode();
        result = prime * result
                + ((transportFee == null) ? 0 : transportFee.hashCode());
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Wholesaler other = (Wholesaler) obj;
        if (transportFee == null) {
            if (other.transportFee != null)
                return false;
        }
        else if (!transportFee.equals(other.transportFee))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Wholesaler}.
     * 
     * @return Die String-Representation von {@link Wholesaler}
     */
    @Override
    public String toString() {
        return "Wholesaler [transportFee=" + transportFee + "]";
    }
}