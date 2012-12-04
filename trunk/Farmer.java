
/**
 * Die Klasse {@link Farmer} ist eine Spezialisierung der Fach- bzw. Datentraegerklasse 
 * {@link Provider} und stellt einen oertlichen Bauernhof dar.
 * 
 * @author Tim Sahling
 */
public class Farmer extends Provider {

    private Amount distanceToCentral;

    /**
     * Standardkonstruktor
     */
    public Farmer() {
    }

    @Override
    protected Farmer createProvider() {
        return null;
    }

    @Override
    protected Farmer updateProvider() {
        return null;
    }

    @Override
    protected void deleteProvider() {
    }

    /**
     * @return Die Entfernung zur Zentrale der VAWi GmbH
     */
    public Amount getDistanceToCentral() {
        return null;
    }
    
    /**
     * Setzt die Entfernung zur Zentrale der VAWi GmbH.
     * 
     * @param distanceToCentral Die zu setzende Entfernung zur Zentrale der VAWi GmbH
     */
    public void setDistanceToCentral(Amount distanceToCentral) {
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
     * Erstellt die String-Representation des Objekts {@link Farmer}.
     * 
     * @return Die String-Representation von {@link Farmer}
     */
    @Override
    public String toString() {
        return null;
    }
}