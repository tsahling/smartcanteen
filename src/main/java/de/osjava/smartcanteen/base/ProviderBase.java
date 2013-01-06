package de.osjava.smartcanteen.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
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
     * Summiert für alle Anbieter die Mengen der Zutaten.
     * 
     * @return
     */
    public Map<Ingredient, Amount> sumIngredientQuantities() {
        Map<Ingredient, Amount> result = new HashMap<Ingredient, Amount>();

        if (providers != null && !providers.isEmpty()) {

            for (AbstractProvider provider : providers) {

                for (PriceListItem priceListItem : provider.getPriceList()) {

                    Amount quantity = priceListItem.multiplyAvailableQuantityWithSize();

                    if (result.containsKey(priceListItem.getIngredient())) {
                        result.get(priceListItem.getIngredient()).add(quantity);
                    }
                    else {
                        result.put(priceListItem.getIngredient(), quantity);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Methode um die Anbieter zu ermitteln, die eine bestimmte Zutat vorrätig haben.
     * 
     * @param ingredient
     * @return
     */
    public Set<AbstractProvider> findProvidersByIngredient(Ingredient ingredient) {
        Set<AbstractProvider> result = new HashSet<AbstractProvider>();

        if (providers != null && !providers.isEmpty()) {

            for (AbstractProvider provider : providers) {

                Set<PriceListItem> priceList = provider.getPriceList();

                if (priceList != null && !priceList.isEmpty()) {

                    for (PriceListItem priceListItem : priceList) {

                        if (ingredient.equals(priceListItem.getIngredient())) {
                            result.add(provider);
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 
     * @param providersWithIngredient
     * @param ingredient
     * @return
     */
    public List<AbstractProvider> findBestPriceProvidersByIngredient(
            final Set<AbstractProvider> providersWithIngredient, final Ingredient ingredient) {
        List<AbstractProvider> result = new ArrayList<AbstractProvider>(providersWithIngredient);

        Collections.sort(result, new Comparator<AbstractProvider>() {

            @Override
            public int compare(AbstractProvider o1, AbstractProvider o2) {
                return o1.findPriceForIngredient(ingredient).getValue()
                        .compareTo(o2.findPriceForIngredient(ingredient).getValue());
            }
        });

        return result;
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
     * Methode um die Lebensmittelanbieter {@link AbstractProvider} zu finden, die ein Lebensmittel in einer gewünschten
     * Menge vorrätig haben. Die Ausgabe wird nach dem Preis sortiert.
     * 
     * @param ingredient Lebensmittel
     * @param quantity Menge
     * @return Eine nach dem Preis sortierte ZUordnung von Preis zu Anbieter
     */
    public Map<Amount, AbstractProvider> findProvidersByIngredientAndQuantitySortedByPrice(Ingredient ingredient,
            Amount quantity) {
        // Map<Amount, AbstractProvider> providerPrices = new TreeMap<Amount, AbstractProvider>(new Comparator<Amount>()
        // {
        //
        // @Override
        // public int compare(Amount o1, Amount o2) {
        // return o1.getValue().compareTo(o2.getValue());
        // }
        // });
        //
        // if (providers != null && !providers.isEmpty()) {
        //
        // for (AbstractProvider provider : providers) {
        //
        // Set<PriceListItem> priceList = provider.getPriceList();
        //
        // if (priceList != null && !priceList.isEmpty()) {
        //
        // for (PriceListItem priceListItem : priceList) {
        //
        // int availableQuantityOfIngredient = priceListItem.getAvailableQuantityOfIngredient();
        //
        // if (priceListItem.getIngredient().equals(ingredient) && availableQuantityOfIngredient >= quantity
        // .getValue().intValue()) {
        //
        // Amount price = provider.calculatePriceForIngredientAndQuantity(ingredient, quantity);
        //
        // if (price != null) {
        // providerPrices.put(price, provider);
        // }
        //
        // // Verfügbare Menge des Gebindes muss um die angefragte Menge reduziert werden
        // priceListItem.setAvailableQuantityOfIngredient(availableQuantityOfIngredient - quantity
        // .getValue().intValue());
        // }
        // }
        // }
        // }
        // }

        // return providerPrices;

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
    public Set<AbstractProvider> findProvidersByIngredientAndQuantity(Ingredient ingredient, int quantity) {
        Set<AbstractProvider> result = new HashSet<AbstractProvider>();

        // if (providers != null && !providers.isEmpty()) {
        //
        // for (AbstractProvider provider : providers) {
        //
        // Set<PriceListItem> priceList = provider.getPriceList();
        //
        // if (priceList != null && !priceList.isEmpty()) {
        //
        // for (PriceListItem priceListItem : priceList) {
        //
        // if (ingredient.equals(priceListItem.getIngredient()) && priceListItem
        // .getAvailableQuantityOfIngredient() >= quantity) {
        // result.add(provider);
        // }
        // }
        // }
        // }
        // }

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
