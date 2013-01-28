import java.util.Set;

/**
 * Die Klasse {@link ProviderBase} ist eine Datentraegerklasse die in einem Set die Datenobjekte eines Lebensmittelanbieters {@link Provider} speichert.
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
     * Methode um einen neuen Lebensmittelanbieter {@link Provider} dem Set hinzuzufuegen
     * 
     * @param provider Der hinzuzufuegende Anbieter
     * @return Aktualiserter Anbieter {@link Provider}
     */
    public Provider addProvider(Provider provider) {
        return null;
    }

    /**
     * Methode un einen Lebensmittel Anbieter {@link Provider} in dem Set zu veraendern
     * 
     * @param provider Der zu bearbeitende Anbieter
     * @return Aktualiserter Anbieter {@link Provider}
     */
    public Provider updateProvider(Provider provider) {
        return null;
    }

    /**
     * Methode um einen Anbieter {@link Provider} aus dem Set zu loeschen
     * 
     * @param provider Der zu loeschende Anbieter
     */
    public void removeProvider(Provider provider) {
    }

    /**
     * Methode um einen Lebensmittel Anbieter {@link Provider} anhand seines Namen in dem Set zu finden
     * 
     * @param name Name des Lebensmittelanbieters {@link Provider}
     * @return Der Lebensmittelanbieter {@link Provider} 
     */
    public Provider findProviderByName(String name) {
        return null;
    }

    /**
     * Methode um den besten (guenstigsten) Lebensmittelanbieter {@link Provider}
     * fuer ein Lebensmittel in dem Set zu ermitteln.
     * 
     * @param ingredient Name des Lebensmittels
     * @return Der beste (guenstigste) Lebensmittelanbieter
     */
    public Provider findBestPriceProviderByIngredient(Ingredient ingredient) {
        return null;
    }

    /**
     * Methode um einen Lebensmittelanbieter {@link Provider} anhand von einem Lebensmittel (@link Ingredient) und einer bestimmten Menge zu ermitteln.
     * 
     * @param ingredient Name des Lebensmittels
     * @param quantity Menge des Lebensmittels
     * @return Der Lebensmittelanbieter {@link Provider} der das Lebensmittel in der Menge anbietet
     */
    public Provider findProviderByIngredientAndQuantity(Ingredient ingredient, int quantity) {
        return null;
    }

    /**
     * Methode um eine die Lebensmittelanbieter zu ermitteln
     * 
     * @return Die Lebensmittelanbieter
     */
    public Set<Provider> getProvider() {
        return provider;
    }

    /**
     * Setzt die Lebensmittelanbieter.
     * 
     * @param provider Die zu setzenden Lebensmittelanbieter
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
