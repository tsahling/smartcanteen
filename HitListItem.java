/**
 * 
 * @author Franceso Luciano
 */
public class HitListItem {

    private String name;
    private int rank;

    /**
     * Stanartkonstruktor zum erzeugen eines Gericht mit Namen und Rang
     * @param name
     * @param rank
     */
    public HitListItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * Methode um den Namen eines HitlistItem zur ermitteln
     * @return Name des HitlistItem
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen eines HitlistItem zu setzen
     * @param  Name des HitlistItem 
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode um den Rang eines HitlistItem zu ermitteln
     * @return Rang des HitListItem
     */
    public int getRank() {
        return rank;
    }

    /**
     * Methode um den Rang eines HitListItem zu setzen
     * @param rank
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
