package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.base.PriceList;
import de.osjava.smartcanteen.datatype.Amount;

public abstract class AbstractProvider {

    private String name;
    private PriceList priceList;

    protected abstract AbstractProvider createProvider();

    protected abstract AbstractProvider updateProvider();

    protected abstract void deleteProvider();

    public Amount findAmountOfIngredient(Ingredient ingredient) {
        return null;
    }

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
    public PriceList getPriceList() {
        return priceList;
    }

    /**
     * Sets the priceList.
     * 
     * @param priceList the priceList to set
     */
    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

}
