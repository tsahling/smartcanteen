/**
 * 
 */
package de.osjava.smartcanteen.base;

import java.util.Set;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;

/**
 * 
 * @author Francesco Luciano
 */
public class ProviderBase {

    private Set<AbstractProvider> provider;

    public ProviderBase()
    {

    }

    public AbstractProvider addProvider() {
        return null;
    }

    public AbstractProvider updateProvider() {
        return null;
    }

    public void removeProvider() {
    }

    public AbstractProvider findProviderByName(String name) {
        return null;
    }

    public AbstractProvider findBestPriceProviderByIngredient(Ingredient ingredient) {
        return null;
    }

    public AbstractProvider findProviderByIngredientAndQuantity(Ingredient ingredient, int quantity) {
        return null;
    }

    /**
     * @return the provider
     */
    public Set<AbstractProvider> getProvider() {
        return provider;
    }

    /**
     * Sets the provider.
     * 
     * @param provider the provider to set
     */
    public void setProvider(Set<AbstractProvider> provider) {
        this.provider = provider;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((provider == null) ? 0 : provider.hashCode());
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
        ProviderBase other = (ProviderBase) obj;
        if (provider == null) {
            if (other.provider != null)
                return false;
        }
        else if (!provider.equals(other.provider))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProviderBase [provider=" + provider + "]";
    }

}
