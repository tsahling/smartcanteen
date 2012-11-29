package de.osjava.smartcanteen.base;

import java.util.List;

import de.osjava.smartcanteen.data.item.HitListItem;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hitListItems == null) ? 0 : hitListItems.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HitListBase other = (HitListBase) obj;
        if (hitListItems == null) {
            if (other.hitListItems != null)
                return false;
        }
        else if (!hitListItems.equals(other.hitListItems))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HitListBase [hitListItems=" + hitListItems + "]";
    }

}
