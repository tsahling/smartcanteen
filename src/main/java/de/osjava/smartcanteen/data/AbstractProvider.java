package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;

// Sehr geehrter Herr Wiederhold,
//
// wie schön von ihnen zu hören! Sicherlich können wir im persönlichen Austausch alle Unklarheiten aus dem Weg räumen.
//
// Vom Bauernhof wird per Kurier geliefert, hier fällt eine Pauschale an, die von der Entfernung abhängt. Die
// Großhändler haben hingegen einen Lieferkostensatz, der mit der Anzahl Artikel multipliziert wird. Dieses
// Berechnungsverfahren gilt für alle Großhändler.
//
// Die Preisliste eines Bauernhofs enhält auch die Entfernung, die Preisliste des Großhändlers auch seine
// Kostenpauschale.
//
// Viele Grüße
//
// Jacques Gourmand

// wie bereits gesagt gibt es bei den Bauernhöfen eine Pauschale des Kuriere die nur von der Entfenung abhängt und nicht
// davon was oder wie viel bestellt wurde. Die Großhändler haben einen Lieferkostensatz, der mit der Anzahl Artikel
// multipliziert wird.
/**
 * 
 * @author Tim Sahling
 */
public abstract class AbstractProvider {

    private String name;
    private Set<PriceListItem> priceList;

    protected abstract AbstractProvider createProvider();

    protected abstract AbstractProvider updateProvider();

    protected abstract void deleteProvider();

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the priceList
     */
    public Set<PriceListItem> getPriceList() {
        return priceList;
    }

    /**
     * Sets the priceList.
     * 
     * @param priceList the priceList to set
     */
    public void setPriceList(Set<PriceListItem> priceList) {
        this.priceList = priceList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((priceList == null) ? 0 : priceList.hashCode());
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
        AbstractProvider other = (AbstractProvider) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (priceList == null) {
            if (other.priceList != null)
                return false;
        }
        else if (!priceList.equals(other.priceList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AbstractProvider [name=" + name + ", priceList=" + priceList + "]";
    }

}
