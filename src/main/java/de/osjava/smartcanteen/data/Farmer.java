package de.osjava.smartcanteen.data;

import java.math.BigDecimal;
import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.NumberHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link Farmer} ist eine Spezialisierung der Fach- bzw. Datenträgerklasse {@link AbstractProvider} und
 * stellt einen örtlichen Bauernhof dar.
 * 
 * @author Tim Sahling
 */
public class Farmer extends AbstractProvider {

    private Amount distanceToCentral;

    /**
     * Standardkonstruktor mit Parametern.
     * 
     * @param name Der Name des {@link Farmer}
     * @param priceList Die Preisliste des {@link Farmer}
     * @param distanceToCentral Die Distanz zur zentrale der VAWi GmbH des {@link Farmer}
     */
    public Farmer(String name, Set<PriceListItem> priceList, Amount distanceToCentral) {
        super(name, priceList);
        this.distanceToCentral = distanceToCentral;
    }

    /**
     * Kalkuliert die Transportkosten für einen {@link Farmer}. Vom {@link Farmer} wird per Kurier geliefert, hier fällt
     * eine Pauschale an, die von der Entfernung abhängt. Die Pauschale pro km ist in einer Property hinterlegt.
     * 
     * @return Die Transportkosten für einen {@link Farmer}
     */
    public Amount calculateTransportCosts() {
        return new Amount(NumberHelper.multiply(this.distanceToCentral.getValue(),
                new BigDecimal(PropertyHelper.getProperty("provider.farmer.transportFlatRatePerKm"))),
                UnitOfMeasurement.EUR);
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime
                * result
                + ((distanceToCentral == null) ? 0 : distanceToCentral
                        .hashCode());
        return result;
    }

    /**
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von dem die Methode aufgerufen wurde.
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
        Farmer other = (Farmer) obj;
        if (distanceToCentral == null) {
            if (other.distanceToCentral != null)
                return false;
        }
        else if (!distanceToCentral.equals(other.distanceToCentral))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Farmer}.
     * 
     * @return Die String-Representation von {@link Farmer}
     */
    @Override
    public String toString() {
        return "Farmer [distanceToCentral=" + distanceToCentral + "]";
    }
}