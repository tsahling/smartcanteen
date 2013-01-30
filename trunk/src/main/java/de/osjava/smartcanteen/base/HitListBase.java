package de.osjava.smartcanteen.base;

import java.util.ArrayList;
import java.util.List;

import de.osjava.smartcanteen.data.item.HitListItem;

/**
 * Die Klasse {@link HitListBase} ist eine Datenträgerklasse die in einer Liste die Datenobjekte {@link HitListItem}
 * speichert.
 * 
 * @author Francesco Luciano
 */
public class HitListBase {

    private List<HitListItem> hitListItems;

    /**
     * Methode um ein Element {@link HitListItem} in die Liste hinzuzufügen.
     * 
     * @param hitListItem Das einzufügende Datenobjekt
     * @return Das aktualisierte Datenobjekt wird zurückgegeben
     */
    public HitListItem addHitListItem(HitListItem hitListItem) {
        if (this.hitListItems == null) {
            this.hitListItems = new ArrayList<HitListItem>();
        }
        this.hitListItems.add(hitListItem);
        return hitListItem;
    }

    /**
     * Methode um ein bestimmtes Element in der Liste nach Namen zu suchen.
     * 
     * @param name Name des Datenobjekts
     * @return Das gefundene Datenobjekt wird zurückgegeben
     */
    public HitListItem findHitListItemByName(String name) {

        for (HitListItem hitlistitem : hitListItems) {

            if (hitlistitem.getName().equals(name)) {
                return hitlistitem;
            }
        }
        return null;
    }

    /**
     * Methode um alle Element aus der Liste zu ermitteln
     * 
     * @return Alle Datenobjekte die in der Liste vorhanden sind.
     */
    public List<HitListItem> getHitListItems() {
        return hitListItems;
    }

    /**
     * Setzt den Rang eines Gericht.
     * 
     * @param hitListItems
     *            Die zu setzenden Hitlistpositionen
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
        return "HitListBase [hitListItems=" + hitListItems + "]";
    }
}