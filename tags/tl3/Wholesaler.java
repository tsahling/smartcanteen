
/**
 * Die Klasse {@link Wholesaler} ist eine Spezialisierung der Fach- bzw. Datentraegerklasse 
 * {@link Provider} und stellt einen Grosshaendler dar.
 * 
 * @author Tim Sahling
 */
public class Wholesaler extends Provider {

    private Amount transportFee;

    /**
     * Standardkonstruktor
     */
    public Wholesaler() {
    }
    
    @Override
    protected Wholesaler createProvider(Provider provider) {
        return null;
    }

    @Override
    protected Wholesaler updateProvider(Provider provider) {
        return null;
    }

    @Override
    protected void deleteProvider(Provider provider) {
    }

    /**
     * @return Die Versandkostenpauschale des Grosshaendlers
     */
    public Amount getTransportFee() {
        return null;
    }
    
    /**
     * Setzt die Versandkostenpauschale des Grosshaendlers.
     * 
     * @param transportFee Die zu setzende Versandkostenpauschale des Grosshaendlers
     */
    public void setTransportFee(Amount transportFee) {
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
     * Erstellt die String-Representation des Objekts {@link Wholesaler}.
     * 
     * @return Die String-Representation von {@link Wholesaler}
     */
    @Override
    public String toString() {
        return null;
    }
}