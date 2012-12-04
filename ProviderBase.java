import java.util.Set;

/**
 * 
 * @author Francesco Luciano
 */
public class ProviderBase {

    private Set<Provider> provider;

    /**
     * Standardkonstruktor
     */
    public ProviderBase()
    {
    }

    public Provider addProvider() {
        return null;
    }

    public Provider updateProvider() {
        return null;
    }

    public void removeProvider() {
    }

    public Provider findProviderByName(String name) {
        return null;
    }

    public Provider findBestPriceProviderByIngredient(Ingredient ingredient) {
        return null;
    }

    public Provider findProviderByIngredientAndQuantity(Ingredient ingredient, int quantity) {
        return null;
    }

    /**
     * @return the provider
     */
    public Set<Provider> getProvider() {
        return provider;
    }

    /**
     * Sets the provider.
     * 
     * @param provider the provider to set
     */
    public void setProvider(Set<Provider> provider) {
        this.provider = provider;
    }
    
    /**
     * Erstellt die String-Representation des Objekts {@link ProviderBase}.
     * 
     * @return Die String-Representation von {@link ProviderBase}
     */
    @Override
    public String toString() {
        return null;
    }
}
