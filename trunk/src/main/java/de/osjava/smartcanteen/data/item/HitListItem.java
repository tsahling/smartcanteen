package de.osjava.smartcanteen.data.item;

import de.osjava.smartcanteen.application.Application;
import de.osjava.smartcanteen.application.InputFileHandler;
import de.osjava.smartcanteen.data.Recipe;

/**
 * Die Klasse {@link HitListItem} stellt ein Gericht {@link Recipe} anhand des
 * Namens in der Datenträgerklasse (@link HitListBase) dar. Die HitListdatei
 * wird der Klasse {@link Application} als Aufrufparameter übergeben und von der
 * Klasse {@link InputFileHandler} verarbeitet und in ihre Datenfelder zerlegt. Die
 * Klasse {@link HitListItem} enthält ein Attribut für den Namen des Gerichts
 * und den Rang des Gerichts.
 * 
 * @author Francesco Luciano
 */
public class HitListItem {

    private String name;
    private int rank;

    /**
     * Standardkonstruktor zum Erzeugen eines Gericht mit Namen und Rang
     * 
     * @param name
     * @param rank
     */
    public HitListItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * Methode um den Namen eines Datenobjekts zur ermitteln
     * 
     * @return Name des Datenobjekts
     */
    public String getName() {
        return name;
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
        result = prime * result + rank;
        return result;
    }

    /**
     * Diese Methode prueft, ob das übergebene Objekt gleich dem Objekt ist, von
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
        HitListItem other = (HitListItem) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link HitListItem}.
     * 
     * @return Die String-Representation von {@link HitListItem}
     */
    @Override
    public String toString() {
        return "HitListItem [name=" + name + ", rank=" + rank + "]";
    }
}
