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
    private PriceList priceList;
    
    protected abstract Provider createProvider();
    
    protected abstract Provider updateProvider();
    
    protected abstract void deleteProvider();
    
    public Amount findAmountOfIngredient(Ingredient ingredient) {
        return null;
    }
}
