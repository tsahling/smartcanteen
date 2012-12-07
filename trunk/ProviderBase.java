import java.util.Set;

/**
 * Die Klasse {@link ProviderBase} ist eine Datenträgerklasse die in einem Set {@link provider} die Datenobjekte eines Lebensmittel Anbieter {@link Provider} speichert.
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
     * Methode um einen neuen Lebensmittel Anbieter {@link Provider} in dem Set {@link provider} hinzuzufügen
     * @return aktualiserter Anbieter {@link Provider}
     */
    public Provider addProvider() {
        return null;
    }

    /**
     * Methode un einen Lebensmittel Anbieter {@link Provider} in dem Set {@link provider} zu verändern
     * @return  aktualiserter Anbieter {@link Provider}
     */
    public Provider updateProvider() {
        return null;
    }

    /**
     * Methode um einen Anbieter {@link Provider} aus der Set {@link provider} zu löschen
     */
    public void removeProvider() {
    }

     /**
     * Methode um einen Lebensmittel Anbieter {@link Provider} 
     * anhand seines Namen in dem Set {@link provider} zu finden
     * @param name Name des Lebensmittel Anbieter {@link Provider}
     * @return Der Lebensmittel Anbieter {@link Provider} 
     */
    public Provider findProviderByName(String name) {
        return null;
    }

     /**
     * Methode um den besten (günstigsten) Lebensmittel Anbieter {@link Provider}
     * für ein Lebensmittel (@link ingredient) in dem Set {@link provider} zu ermitteln
     * @param ingredient Name des Lebensmittel
     * @return Der beste (günstigste) Lebensmittel Anbieter {@link Provider}
     */
    public Provider findBestPriceProviderByIngredient(Ingredient ingredient) {
        return null;
    }

     /**
     * Methode um einen Lebensmittel Anbieter {@link Provider} anhand von einem Lebensmittel (@link ingredient)
     * und einer bestimmten Menge {@link quantity} zu ermitteln
     * @param ingredient Name des Lebensmittel
     * @param quantity Menge des Lebensmittel
     * @return Die Lebensmittel Anbieter {@link Provider} die das Lebensmittel (@link ingredient) in der Menge {@link quantity} anbieten
     */
    public Provider findProviderByIngredientAndQuantity(Ingredient ingredient, int quantity) {
        return null;
    }

      /**
     * Methode um einen die Lebensmittel Anbieter {@link Provider} zu ermitteln
     * @return Die Lebensmittel Anbieter {@link provider}
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
