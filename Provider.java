import java.util.Set;

/**
 * Die Klasse {@link Provider} ist eine der Fach- bzw. Datentraegerklassen und als abstrakte,
 * generalistische Klasse ausgelegt, die mit den beiden Klassen {@link Farmer} und {@link Wholesaler}
 * verschiedene Spezialisierungen hat. Sie stellt die Basis für die unterschiedlichen Anbieter von 
 * fuer den Kantinenbetrieb benoetigten Lebensmitteln und deren jeweiligen Preise dar.
 * 
 * @author Tim Sahling
 */
public abstract class Provider
{    
    private String name;
    private Set<PriceListItem> priceList;

    /**
     * Erstellt einen Anbieter.
     * 
     * @return Der erstellte Anbieter
     */
    protected abstract Provider createProvider();

    /**
     * Bearbeitet einen Anbieter.
     * 
     * @return Der bearbeitete Anbieter
     */
    protected abstract Provider updateProvider();

    /**
     * Loescht einen Anbieter.
     */
    protected abstract void deleteProvider();

    /**
     * 
     * @return Der Name des Anbieters
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Anbieters.
     * 
     * @param name Der zu setzende Name des Anbieters
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Die Preislistenpositionen
     */
    public Set<PriceListItem> getPriceList() {
        return priceList;
    }

    /**
     * Setzt die Preislistenpositionen.
     * 
     * @param priceList Die zu setzenden Preislistenpositionen
     */
    public void setPriceList(Set<PriceListItem> priceList) {
        this.priceList = priceList;
    }

    /**
     * Diese Methode gibt den HashCode-Wert fuer das Objekt zurueck, von dem die Methode aufgerufen 
     * wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
          return 0;
    }
    
    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist, von dem die Methode 
     * aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Provider}.
     * 
     * @return Die String-Representation von {@link Provider}
     */
    @Override
    public String toString() {
        return null;
    }
}
