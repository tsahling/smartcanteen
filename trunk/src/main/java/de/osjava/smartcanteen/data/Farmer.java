package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;

/**
 * Die Klasse {@link Farmer} ist eine Spezialisierung der Fach- bzw.
 * Datenträgerklasse {@link AbstractProvider} und stellt einen örtlichen
 * Bauernhof dar.
 * 
 * @author Tim Sahling
 */
public class Farmer extends AbstractProvider {

    private Amount distanceToCentral;

    /**
     * Standardkonstruktor
     */
    public Farmer(String name, Amount distanceToCentral, Set<PriceListItem> priceList) {
        super(name, priceList);
        this.distanceToCentral = distanceToCentral;
    }

    @Override
    protected Farmer createProvider(AbstractProvider provider) {
        return null;
    }

    @Override
    protected Farmer updateProvider(AbstractProvider provider) {
        return null;
    }

    @Override
    protected void deleteProvider(AbstractProvider provider) {
    }

    /**
     * Liefert die Entfernung zur Zentrale der VAWi GmbH
     * 
     * @return Die Entfernung zur Zentrale der VAWi GmbH
     */
    public Amount getDistanceToCentral() {
        return distanceToCentral;
    }

    /**
     * Setzt die Entfernung zur Zentrale der VAWi GmbH.
     * 
     * @param distanceToCentral
     *            Die zu setzende Entfernung zur Zentrale der VAWi GmbH
     */
    public void setDistanceToCentral(Amount distanceToCentral) {
        this.distanceToCentral = distanceToCentral;
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
        result = prime
                * result
                + ((distanceToCentral == null) ? 0 : distanceToCentral
                        .hashCode());
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