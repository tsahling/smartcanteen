/**
 * Die Klasse {@link HitListItem} stellt ein Gericht {@link Meal} in der Datenträgerklasse (@link HitListBase) dar. Die HitList Datei wird der Klasse {@Application} 
 * als Klassenparameter übergeben und von der Klasse {@link CSVTokenizer} verarbeitet und in ihre Datenfelder zerlegt.
 * Die Klasse {@link HitListItem} enthaelt eine Attribut für den Namen des Gericht {@link name} und den Rang {@link rank} des Gericht {@link Meal}
 * 
 * @author Francesco Luciano
 */
public class HitListItem {

    private String name;
    private int rank;

    /**
     * Standartkonstruktor zum erzeugen eines Gericht mit Namen und Rang
     * @param name
     * @param rank
     */
    public HitListItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * Methode um den Namen {@link name} eines Datenojekt {@link HitListItem} zur ermitteln
     * @return Name des Datenojekt {@link HitListItem}
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen {@link name} eines Datenojekt {@link HitListItem} zur setzen
     * @param name Name des Datenojekt {@link HitListItem} 
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode um den Rang {@link rang} eines HitlistItem zu ermitteln
     * @return Rang {link rang} des HitListItem
     */
    public int getRank() {
        return rank;
    }

    /**
     * Methode um den Rang {@link rang} eines HitListItem zu setzen
     * @param Rang desHitListItem
     *
     */
    public void setRank(int rank) {
        this.rank = rank;
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
     * Erstellt die String-Representation des Objekts {@link HitListItem}.
     * 
     * @return Die String-Representation von {@link HitListItem}
     */
    @Override
    public String toString() {
        return null;
    }
}
