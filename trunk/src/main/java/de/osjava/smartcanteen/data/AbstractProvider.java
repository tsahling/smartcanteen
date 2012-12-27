package de.osjava.smartcanteen.data;

import java.math.BigDecimal;
import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

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

    public AbstractProvider(String name, Set<PriceListItem> priceList) {
        this.name = name;
        this.priceList = priceList;
    }

    public Amount calculatePriceForIngredientAndQuantity(Ingredient ingredient, Amount quantity) {
        BigDecimal value = new BigDecimal(0);
        Amount result = new Amount(value, UnitOfMeasurement.EUR);

        if (priceList != null && !priceList.isEmpty()) {

            // TODO(Tim Sahling) mach dat hier richtig!
            for (PriceListItem priceListItem : priceList) {

            }

        }

        return result;

    }

    /**
     * Erstellt einen Anbieter.
     * 
     * @param provider
     *            Der zu erstellende Anbieter
     * @return Der erstellte Anbieter
     */
    protected abstract AbstractProvider createProvider(AbstractProvider provider);

    /**
     * Bearbeitet einen Anbieter.
     * 
     * @param provider
     *            Der zu bearbeitende Anbieter
     * @return Der bearbeitete Anbieter
     */
    protected abstract AbstractProvider updateProvider(AbstractProvider provider);

    /**
     * Löscht einen Anbieter.
     * 
     * @param provider
     *            Der zu löschende Anbieter
     */
    protected abstract void deleteProvider(AbstractProvider provider);

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
