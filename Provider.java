import java.util.Set;

/**
 * Abstract class Provider - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Provider
{    
    private String name;
    private Set<PriceListItem> priceList;
    
    protected abstract Provider createProvider();
    
    protected abstract Provider updateProvider();
    
    protected abstract void deleteProvider();
        
    public PriceListItem addPriceListItem() {
        return null;
    }
    
    public PriceListItem updatePriceListItem() {
        return null;
    }
    
    public void removePriceListItem() {       
    }
    
    public Amount findPriceByIngredient(Ingredient ingredient) {
        return null;
    }
    
    public int findMaxQuantityOfIngredientByIngredient(Ingredient ingredient) {
        return 0;
    }
}
