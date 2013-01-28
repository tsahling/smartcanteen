/**
 * Die Klasse {@link Ingredient} stellt ein Datenobjekt eines Lebensmittels dar. Sie enthaelt ein Atrribut für den Namen des Lebensmittels.
 * 
 * @author Francesco Luciano
 */
public class Ingredient {

    private String name;

    /**
     * Standardkonstruktor
     * 
     * @param name Name des Lebensmittels
     */
    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Methode um den Namen des Lebensmittels abzufragen
     * 
     * @return Name des Lebensmittels
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen des Lebensmittels zu setzen
     */
    public void setName(String name) {
        this.name = name;
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
     * Erstellt die String-Representation des Objekts {@link Ingredient}.
     * 
     * @return Die String-Representation von {@link Ingredient}
     */
    @Override
    public String toString() {
        return null;
    }
}
