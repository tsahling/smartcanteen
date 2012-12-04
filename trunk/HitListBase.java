import java.util.List;

/**
 * 
 * @author Francesco Luciano
 */
public class HitListBase {

    List<HitListItem> hitListItems;

    public HitListBase() {

    }

    public HitListItem addHitListItem() {
        return null;
    }

    public HitListItem updateHitListItem() {
        return null;
    }

    public void removeHitListItem() {
    }

    public HitListItem findHitListItemByName(String name) {
        return null;
    }

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