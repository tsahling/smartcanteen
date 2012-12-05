import java.util.List;

/**
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
     * Mithilfe der Methode addHitListItem() kann ein Gericht in die Hitliste hinzugefügt werden
     * @return Hitliste wird zurückgeben
     */
   
    public HitListItem addHitListItem() {
        return null;
    }

    /**
     * Mithilfe der Methode updateHitListItem() kann ein Gericht in die Hitliste geändert werden
     * @return Hitliste wird zurückgeben
     */
    public HitListItem updateHitListItem() {
        return null;
    }

    /**
     * Mithilfe der Methode removeHitListItem() kann ein Gericht in die Hitliste gelöscht werden
     * @return Hitliste wird zurückgeben
     */
    public void removeHitListItem() {
    }

     /**
     * Mithilfe der Methode findHitListItemByName() kann ein Gericht in die Hitliste 
     * anhand des Namen des Gericht gefunden werden
     * @param Name des Gericht
     * @return Gericht aus der Hitliste wird zurückgeben
     */
    public HitListItem findHitListItemByName(String name) {
        return null;
    }

      /**
     * Mithilfe der Methode findHitListItemByRank() kann ein Gericht in die Hitliste 
     * anhand des Rang des Gericht gefunden werden
     * @param Rang des Gericht
     * @return Gericht aus der Hitliste wird zurückgeben
     */
    public HitListItem findHitListItemByRank(int rank) {
        return null;
    }

    /**
     * 
     * @return the hitListItems
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