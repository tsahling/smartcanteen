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

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {

        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}