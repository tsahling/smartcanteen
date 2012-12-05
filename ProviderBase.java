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
    /**
     * Mithilfe der Methode addProvider() kann ein neuer Lebensmittel Anbieter in die Datenstruktr provider hinzugefügt werden
     * @return Die aktualisierte Datenstruktur provider wird zurückgeben
     */
    public Provider addProvider() {
        return null;
    }

    /**
     * Mithilfe der Methode updateProvider() kann ein bestehender Lebensmittel Anbieter in die Datenstruktr provider verändert werden
     * @return Die aktualisierte Datenstruktur provider wird zurückgeben
     */
    public Provider updateProvider() {
        return null;
    }

    /**
     * Mithilfe der Methode removeProvider() kann ein bestehender Lebensmittel Anbieter in die Datenstruktr provider gelöscht werden
     * @return Die aktualisierte Datenstruktur provider wird zurückgeben
     */
    public void removeProvider() {
    }

     /**
     * Mithilfe der Methode removeProvider() kann ein Lebensmittel Anbieter 
     * anhand seines Namen in die Datenstrukt provider gefunden werden
     * @return Der Anbieter wird zurückgeben
     * @param Name des Anbieter
     */
    public Provider findProviderByName(String name) {
        return null;
    }

     /**
     * Mithilfe der Methode findBestPriceProviderByIngredient() kann der Lebensmittel Anbieter gefunden 
     * werden welcher bei einem Lebensmittel der günstigste ist
     * @return Der Anbieter wird zurückgeben
     * @param Name des Lebensmittel
     */
    public Provider findBestPriceProviderByIngredient(Ingredient ingredient) {
        return null;
    }

     /**
     * Mithilfe der Methode findProviderByIngredientAndQuantity() kann der Lebensmittel Anbieter gefunden 
     * werden welcher bei ein Lebensmittel in einer bestimmen Menge anbietet
     * @return Der Anbieter wird zurückgeben
     * @param Name des Lebensmittel
     * @param Menge des Lebensmittel
     */
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
