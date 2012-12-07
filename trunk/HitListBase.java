import java.util.List;

/**
 * Die Klasse HitListBase {@link Recipebase} ist eine Datenträgerklasse die in einer List (@link hitListItems)
 * die Datenobjekte {@link HitListItem} speichert.
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
     * Methode um ein Datenobjekt (@link HitListItem) in die List {@link hitListItems} hinzuzufügen.
     * @param recipe Das einzufügende Datenobjekt (@link hitListItem)
     * @return Die aktualisierte Datenstruktur {@linkhitListItems} wird zurückgeben
     */
   
    public HitListItem addHitListItem(HitListItem hitListItem) {
        return null;
    }

    /**
     * Methode um ein bestehendes Datenobjekt {@link hitListItem} in der List {@link hitListItems zu verändern.
     * @return Die aktualisierte Datenstruktur {@link hitListItems}
     */
    public HitListItem updateHitListItem(HitListItem hitListItem) {
        return null;
    }

     /**
     * Methode um ein Datenobjekt {@link hitListItem} aus der List {@link hitListItems zu löschen.
     */
    public void removeHitListItem(HitListItem hitListItem) {
    }

    /**
     * Methode um ein bestimmtes Datenobjekt {@link hitListItem} in der List {@link hitListItems zu suchen.
     * @param name Name des Datenobjekt {@link hitListItem} 
     * @return Das gefundene Datenobjekt {@link hitListItem} wird zurückgeben
     */
    public HitListItem findHitListItemByName(String name) {
        return null;
    }

   /**
     * Methode ein Datenobjekt {@link hitListItem} anhand seines 
     * Rank {@link rank} in der List {@link hitListItems zu suchen.
     * @param rank Rank des Gericht
     * @return Java Collection {@link HitListItem} mit dem passenden Rang
     */
    public HitListItem findHitListItemByRank(int rank) {
        return null;
    }

    /**
     * Methode um alle Datenobjekte {@link hitListItem} aus der List {@link hitListItems zu ermitteln
     * @return Alle Datenobjekte {@link hitListItem} die in der List {@link hitListItems vorhanden sind.
     */
    public List<HitListItem> getHitListItems() {
        return hitListItems;
    }

    /**
     * Sets the hitListItems.
     * 
     * @param hitListItems the hitListItems to set
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