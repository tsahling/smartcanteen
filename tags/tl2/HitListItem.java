/**
 * Die Klasse {@link HitListItem} stellt ein Gericht {@link Recipe} anhand des Namens in der Datentraegerklasse (@link HitListBase) dar. 
 * Die HitList Datei wird der Klasse {@link Application} als Aufrufparameter uebergeben und von der Klasse {@link CSVTokenizer} verarbeitet und in ihre Datenfelder zerlegt.
 * Die Klasse {@link HitListItem} enthaelt ein Attribut für den Namen des Gerichts und den Rang des Gerichts.
 * 
 * @author Francesco Luciano
 */
public class HitListItem {

    private String name;
    private int rank;

    /**
     * Standartkonstruktor zum Erzeugen eines Gericht mit Namen und Rang
     * 
     * @param name
     * @param rank
     */
    public HitListItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * Methode um den Namen eines Datenojekts zur ermitteln
     * 
     * @return Name des Datenojekts
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen eines Datenojekts zu setzen
     * 
     * @param name Name des Datenojekts
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode um den Rang eines {@link HitlistItem} zu ermitteln
     * 
     * @return Rang des {@link HitlistItem}
     */
    public int getRank() {
        return rank;
    }

    /**
     * Methode um den Rang eines {@link HitlistItem} zu setzen
     * 
     * @param rank Rang des {@link HitlistItem}
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
