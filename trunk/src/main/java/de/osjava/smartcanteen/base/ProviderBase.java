package de.osjava.smartcanteen.base;

import java.util.HashSet;
import java.util.Set;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.IngredientType;

/**
 * Die Klasse {@link ProviderBase} ist eine Datenträgerklasse die in einem Set
 * die Datenobjekte eines Lebensmittelanbieters {@link AbstractProvider} speichert.
 * 
 * @author Francesco Luciano
 */
public class ProviderBase {

    private Set<AbstractProvider> providers;

    /**
     * Standardkonstruktor
     */
    public ProviderBase() {
    }

    /**
     * Methode um einen neuen Lebensmittelanbieter {@link AbstractProvider} dem
     * Set hinzuzufügen
     * 
     * @param provider
     *            Der hinzuzufügende Anbieter
     * @return Aktualiserter Anbieter {@link AbstractProvider}
     */
    public AbstractProvider addProvider(AbstractProvider provider) {
        if (providers == null) {
            providers = new HashSet<AbstractProvider>();
        }
        providers.add(provider);
        return provider;
    }

    /**
     * Methode un einen Lebensmittel Anbieter {@link AbstractProvider} in dem
     * Set zu verändern
     * 
     * @param provider
     *            Der zu bearbeitende Anbieter
     * @return Aktualiserter Anbieter {@link AbstractProvider}
     */
    public AbstractProvider updateProvider(AbstractProvider provider) {
        return null;
    }

    /**
     * Methode um einen Anbieter {@link AbstractProvider} aus dem Set zu löschen
     * 
     * @param provider
     *            Der zu löschende Anbieter
     */
    public void removeProvider(AbstractProvider provider) {
    }

    /**
     * Methode um den {@link IngredientType} auf Basis des übergebenen Namen zu finden.
     * 
     * @param name
     * @return
     */
    public IngredientType findIngredientTypeByIngredientName(String name) {
        if (providers != null && !providers.isEmpty()) {

            for (AbstractProvider provider : providers) {

                for (PriceListItem priceListItem : provider.getPriceList()) {

                    if (name.equals(priceListItem.getIngredient().getName())) {
                        return priceListItem.getIngredient().getIngredientType();
                    }
                }
            }
        }

        return null;
    }

    /**
     * Methode um einen Lebensmittelanbieter {@link AbstractProvider} anhand
     * seines Namens in dem Set zu finden
     * 
     * @param name
     *            Name des Lebensmittelanbieters {@link AbstractProvider}
     * @return Der Lebensmittelanbieter {@link AbstractProvider}
     */
    public AbstractProvider findProviderByName(String name) {
        return null;
    }

    /**
     * Methode um den besten (günstigsten) Lebensmittelanbieter {@link AbstractProvider} für ein Lebensmittel in dem Set
     * zu ermitteln.
     * 
     * @param ingredient
     *            Name des Lebensmittels
     * @return Der beste (günstigste) Lebensmittelanbieter
     */
    public AbstractProvider findBestPriceProviderByIngredient(
            Ingredient ingredient) {
        return null;
    }

    /**
     * Methode um einen Lebensmittelanbieter {@link AbstractProvider} anhand von einem Lebensmittel {@link Ingredient}
     * und einer bestimmten Menge zu ermitteln.
     * 
     * @param ingredient Lebensmittel
     * @param quantity Menge
     * @return Die Lebensmittelanbieter {@link AbstractProvider}, die das Lebensmittel in der Menge anbieten
     */
    public Set<AbstractProvider> findProviderByIngredientAndQuantity(
            Ingredient ingredient, int quantity) {
        Set<AbstractProvider> result = new HashSet<AbstractProvider>();

        if (providers != null && !providers.isEmpty()) {

            for (AbstractProvider provider : providers) {

                Set<PriceListItem> priceList = provider.getPriceList();

                if (priceList != null && !priceList.isEmpty()) {

                    for (PriceListItem priceListItem : priceList) {

                        if (ingredient.equals(priceListItem.getIngredient()) && priceListItem
                                .getAvailableQuantityOfIngredient() >= quantity) {
                            result.add(provider);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Methode um die Lebensmittelanbieter zu ermitteln
     * 
     * @return Die Lebensmittelanbieter
     */
    public Set<AbstractProvider> getProvider() {
        return providers;
    }

    /**
     * Setzt die Lebensmittelanbieter.
     * 
     * @param provider
     *            Die zu setzenden Lebensmittelanbieter
     */
    public void setProvider(Set<AbstractProvider> provider) {
        this.providers = provider;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link ProviderBase}.
     * 
     * @return Die String-Representation von {@link ProviderBase}
     */
    @Override
    public String toString() {
        return "ProviderBase [provider=" + providers + "]";
    }

}
