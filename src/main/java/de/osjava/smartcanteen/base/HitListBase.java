package de.osjava.smartcanteen.base;

import java.util.ArrayList;
import java.util.List;

public class HitListBase {

    List<HitListItem> hitListItems;

    public HitListBase() {

    }

    public void addHitListItem(HitListItem hitListItem) {
        if (this.hitListItems == null) {
            this.hitListItems = new ArrayList<HitListItem>();
        }
        this.hitListItems.add(hitListItem);
    }
}
