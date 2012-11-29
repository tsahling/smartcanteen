import java.util.Set;

/**
 * 
 * @author Tim Sahling
 */
public abstract class Provider
{    
    private String name;
    private Set<PriceListItem> priceList;

    protected abstract Provider createProvider();

    protected abstract Provider updateProvider();

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
