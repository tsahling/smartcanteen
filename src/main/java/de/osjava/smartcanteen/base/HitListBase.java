package de.osjava.smartcanteen.base;

import java.util.ArrayList;
import java.util.List;

import de.osjava.smartcanteen.data.item.HitListItem;

/**
 * Die Klasse {@link HitListBase} ist eine Datenträgerklasse die in einer Liste
 * die Datenobjekte {@link HitListItem} speichert.
 * 
 * @author Francesco Luciano
 */
public class HitListBase {

    List<HitListItem> hitListItems;

    /**
     * Standardkonstruktor
     */
    public HitListBase() {

    }

    /**
     * Methode um ein Datenobjekt {@link HitListItem} in die Liste hinzuzufügen.
     * 
     * @param hitListItem Das einzufügende Datenobjekt
     * @return Das aktualisierte Datenobjekt wird zurückgegeben
     */
    public HitListItem addHitListItem(HitListItem hitListItem) {
        if (hitListItems == null) {
            hitListItems = new ArrayList<HitListItem>();
        }
        hitListItems.add(hitListItem);
        return hitListItem;
    }

    /**
     * Methode um ein bestehendes Datenobjekt in der Liste zu verändern.
     * 
     * @return Das aktualisierte Datenobjekt
     */
    public HitListItem updateHitListItem(HitListItem hitListItem) {
        return null;
    }

    /**
     * Methode um ein Datenobjekt aus der Liste zu löschen.
     * 
     * @param hitListItem Das zu löschende Datenobjekt
     */
    public void removeHitListItem(HitListItem hitListItem) {
    }

    /**
     * Methode um ein bestimmtes Datenobjekt in der Liste nach Namen zu suchen.
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
     * Methode um ein Datenobjekt anhand seines Rangs in der Liste zu suchen.
     * 
     * @param rank
     *            Rang des Gerichts
     * @return Das gefundene Datenobjekt wird zurückgeben
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