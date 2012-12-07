/**
 * Die Klasse {@link Ingredient} stellt ein Datenobjekt eines Lebensmittel dar.
 * Sie enthaelt ein Atrribut für den Namen {@link name} des Lebensmittel.
 * 
 * @author Francesco Luciano
 */
public class Ingredient {

    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Methode um den Namen {@link name} des des Lebensmittel {@link Ingredient} abzufragen 
     * @return Name {@link name} des Lebensmittel {@link Ingredient}
     */
    public String getName() {
        return name;
    }

     /**
     * Methode um den Namen {@link name} des des Lebensmittel {@link Ingredient} zu setzen
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
