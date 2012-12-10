import java.util.List;

/**
 * Die Klasse {@link HitListBase} ist eine Datentraegerklasse die in einer Liste die Datenobjekte {@link HitListItem} speichert.
 * 
 * @author Francesco Luciano
 */
public class HitListBase {

    List<HitListItem> hitListItems;

    /**
     * Standartkonstruktor
     */
    public HitListBase() {

    }

    /**
     * Methode um ein Datenobjekt (@link HitListItem) in die Liste hinzuzufuegen.
     * 
     * @param hitListItem Das einzufuegende Datenobjekt
     * @return Das ggf. aktualisierte Datenobjekt wird zurueckgeben
     */
    public HitListItem addHitListItem(HitListItem hitListItem) {
        return null;
    }

    /**
     * Methode um ein bestehendes Datenobjekt in der Liste zu veraendern.
     * 
     * @return Das aktualisierte Datenobjekt
     */
    public HitListItem updateHitListItem(HitListItem hitListItem) {
        return null;
    }

    /**
     * Methode um ein Datenobjekt aus der Liste zu loeschen.
     *     
     * @param hitListItem Das zu loeschende Datenobjekt
     */
    public void removeHitListItem(HitListItem hitListItem) {
    }

    /**
     * Methode um ein bestimmtes Datenobjekt in der Liste nach Namen zu suchen.
     *     
     * @param name Name des Datenobjekts
     * @return Das gefundene Datenobjekt wird zurueckgeben
     */
    public HitListItem findHitListItemByName(String name) {
        return null;
    }

   /**
     * Methode um ein Datenobjekt anhand seines Rangs in der Liste zu suchen.
     *     
     * @param rank Rang des Gerichts
     * @return Das gefundene Datenobjekt wird zurueckgeben
     */
    public HitListItem findHitListItemByRank(int rank) {
        return null;
    }

    /**
     * Methode um alle Datenobjekte aus der Liste zu ermitteln
     *     
     * @return Alle Datenobjekte die in der Liste vorhanden sind.
     */
    public List<HitListItem> getHitListItems() {
        return hitListItems;
    }

    /**
     * Setzt die Hitlistpositionen.
     * 
     * @param hitListItems Die zu setzenden Hitlistpositionen
     */
    public void setHitListItems(List<HitListItem> hitListItems) {
        this.hitListItems = hitListItems;
    }
    
    /**
     * Erstellt die String-Representation des Objekts {@link HitListBase}.
     * 
     * @return Die String-Representation von {@link HitListBase}
     */
    @Override
    public String toString() {
        return null;
    }
    
}