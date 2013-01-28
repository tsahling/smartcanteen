package de.osjava.smartcanteen.base;

import java.math.BigDecimal;
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
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link ProviderBase} ist eine Datenträgerklasse die in einem Set
 * die Datenobjekte eines Lebensmittelanbieters {@link AbstractProvider} speichert.
 * 
 * @author Francesco Luciano
 */
public class ProviderBase {

    private Set<AbstractProvider> providers;

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
     * 
     * @param ingredient
     * @return
     */
    public Amount findBestPriceForOneUnitOfIngredient(Ingredient ingredient) {
        Amount result = null;

        List<PriceListItem> priceListItems = findPriceListItemsByIngredient(ingredient);

        if (priceListItems != null && !priceListItems.isEmpty()) {

            Collections.sort(priceListItems, new Comparator<PriceListItem>() {

                @Override
                public int compare(PriceListItem pli1, PriceListItem pli2) {
                    return comparePriceOfPriceListItems(pli1, pli2);
                }
            });

            result = new Amount(priceListItems.iterator().next().calculatePriceForOneUnitOfSize(),
                    UnitOfMeasurement.EUR);
        }

        return result;
    }

    /**
     * Ermittelt eine Liste von {@link PriceListItem} auf Basis einer {@link Ingredient}.
     * 
     * @param ingredient Die {@link Ingredient}, mit der gesucht wird
     * @return Eine Liste von {@link PriceListItem}
     */
    private List<PriceListItem> findPriceListItemsByIngredient(Ingredient ingredient) {
        List<PriceListItem> result = new ArrayList<PriceListItem>();

        Set<AbstractProvider> ingredientProviders = findProvidersByIngredient(ingredient);

        if (ingredientProviders != null && !ingredientProviders.isEmpty()) {

            for (AbstractProvider provider : ingredientProviders) {

                PriceListItem priceListItem = provider.findPriceListItemByIngredient(ingredient);

                if (priceListItem != null) {
                    result.add(priceListItem);
                }
            }
        }

        return result;
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
     * Methode um die {@link AbstractProvider} zu ermitteln, die für eine {@link Ingredient} den besten Preis bieten.
     * Die Auswahl beschränkt sich dabei auf {@link AbstractProvider}, welche die Zutat auch vorrätig haben.
     * 
     * @param providersWithIngredient
     * @param ingredient
     * @return
     */
    public List<AbstractProvider> findBestPriceProvidersByIngredient(
            final Set<AbstractProvider> providersWithIngredient, final Ingredient ingredient) {
        List<AbstractProvider> result = new ArrayList<AbstractProvider>();

        if (providersWithIngredient != null && !providersWithIngredient.isEmpty()) {

            result.addAll(providersWithIngredient);

            Collections.sort(result, new Comparator<AbstractProvider>() {

                @Override
                public int compare(AbstractProvider p1, AbstractProvider p2) {
                    PriceListItem pli1 = p1.findPriceListItemByIngredient(ingredient);
                    PriceListItem pli2 = p2.findPriceListItemByIngredient(ingredient);
                    return comparePriceOfPriceListItems(pli1, pli2);
                }
            });
        }

        return result;
    }

    /**
     * Methode um die {@link AbstractProvider} zu ermitteln, die für eine {@link Ingredient} und eine {@link Amount} den
     * besten Preis bieten. Die Auswahl beschränkt sich dabei auf {@link AbstractProvider}, welche die Zutat auch
     * vorrätig haben.
     * 
     * @param providersWithIngredientAndQuantity
     * @param ingredient
     * @param ingredientQuantity
     * @return
     */
    public List<AbstractProvider> findBestPriceProvidersByIngredientAndQuantity(
            final Set<AbstractProvider> providersWithIngredientAndQuantity, final Ingredient ingredient,
            final Amount ingredientQuantity) {
        List<AbstractProvider> result = new ArrayList<AbstractProvider>();

        if (providersWithIngredientAndQuantity != null && !providersWithIngredientAndQuantity.isEmpty()) {

            result.addAll(providersWithIngredientAndQuantity);

            Collections.sort(result, new Comparator<AbstractProvider>() {

                @Override
                public int compare(AbstractProvider p1, AbstractProvider p2) {
                    PriceListItem pli1 = p1.findPriceListItemByIngredient(ingredient);
                    PriceListItem pli2 = p2.findPriceListItemByIngredient(ingredient);
                    return comparePriceOfPriceListItemsWithQuantity(pli1, pli2, ingredientQuantity);
                }
            });
        }

        return result;
    }

    /**
     * Methode um die {@link AbstractProvider} zu ermitteln, die für eine {@link Ingredient} und eine {@link Amount} den
     * geringsten Ausschuss haben. Die Auswahl beschränkt sich dabei auf {@link AbstractProvider}, welche die Zutat auch
     * vorrätig haben.
     * 
     * @param providersWithIngredientAndQuantity
     * @param ingredient
     * @param ingredientQuantity
     * @return
     */
    public List<AbstractProvider> findOptimalQuantityProvidersByIngredientAndQuantity(
            final Set<AbstractProvider> providersWithIngredientAndQuantity, final Ingredient ingredient,
            final Amount ingredientQuantity) {

        List<AbstractProvider> result = new ArrayList<AbstractProvider>();

        if (providersWithIngredientAndQuantity != null && !providersWithIngredientAndQuantity.isEmpty()) {

            result.addAll(providersWithIngredientAndQuantity);

            Collections.sort(result, new Comparator<AbstractProvider>() {

                @Override
                public int compare(AbstractProvider p1, AbstractProvider p2) {
                    return p1.calculateWasteQuantityFromIngredientAndQuantity(ingredient, ingredientQuantity)
                            .compareTo(
                                    p2.calculateWasteQuantityFromIngredientAndQuantity(ingredient, ingredientQuantity));
                }
            });
        }

        return result;

    }

    /**
     * Ermittelt die {@link AbstractProvider}, die eine bestimmte {@link Ingredient} vorrätig haben.
     * 
     * @param ingredient Die {@link Ingredient}, mit der gesucht wird
     * @return Ein Set von {@link AbstractProvider}
     */
    public Set<AbstractProvider> findProvidersByIngredient(Ingredient ingredient) {
        Set<AbstractProvider> result = new HashSet<AbstractProvider>();

        if (providers != null && !providers.isEmpty() && ingredient != null) {

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
     * Methode um die {@link AbstractProvider} zu ermitteln, die eine bestimmte {@link Ingredient} in einer bestimmten
     * {@link Amount} vorrätig haben. Die Auswahl beschränkt sich dabei auf {@link AbstractProvider}, welche die Zutat
     * vollständig vorrätig haben.
     * 
     * @param providersWithIngredient
     * @param ingredient
     * @param quantity
     * @return
     */
    public Set<AbstractProvider> findProvidersByIngredientAndQuantity(Set<AbstractProvider> providersWithIngredient,
            Ingredient ingredient, Amount quantity) {
        Set<AbstractProvider> result = new HashSet<AbstractProvider>();

        if (providersWithIngredient != null && !providersWithIngredient.isEmpty()) {

            for (AbstractProvider provider : providersWithIngredient) {

                if (provider.hasIngredientWithQuantity(ingredient, quantity)) {
                    result.add(provider);
                }
            }
        }

        return result;
    }

    /**
     * Vergleicht die Preise von zwei {@link PriceListItem} miteinander.
     * 
     * @param pli1 Das erste {@link PriceListItem}
     * @param pli2 Das zweite {@link PriceListItem}
     * @return 0, -1 oder 1, je nachdem wie sich die Preise der beiden zu vergleichenden {@link PriceListItem}
     *         zueinander verhalten
     */
    private int comparePriceOfPriceListItems(PriceListItem pli1, PriceListItem pli2) {
        // Wenn eine Preislistenposition nicht gesetzt ist, wird von Gleichheit ausgegangen. Dieser Fall dürfte aber
        // normalerweise nicht eintreten.
        if (pli1 == null || pli2 == null) {
            return 0;
        }

        // Wenn die Größe und Einheit der Gebinde identisch sind, wird der Preis der beiden Positionen verglichen
        if (pli1.getSize().equals(pli2.getSize())) {
            return pli1.getPrice().getValue().compareTo(pli2.getPrice().getValue());
        }

        // Wenn die Größe des Gebindes von Position 1 kleiner ist als die Größe des Gebindes von Position 2, müssen
        // diese Werte auf die gleiche Größe gebracht werden, um die Preise vergleichen zu können.
        if (pli1.getSize().getValue().compareTo(pli2.getSize().getValue()) == -1) {
            BigDecimal dividedSize = NumberHelper.divide(pli2.getSize().getValue(), pli1.getSize()
                    .getValue());

            int compare = NumberHelper.divide(pli2.getPrice().getValue(), dividedSize).compareTo(
                    pli1.getPrice().getValue());

            // Invertieren des Ergebnisses, da eigentlich Position 1 mit Position 2 verglichen wird.
            if (compare == -1) {
                return 1;
            }
            else if (compare == 1) {
                return -1;
            }
            else {
                return compare;
            }
        }
        // Wenn die Größe des Gebindes von Position 1 größer ist als die Größe des Gebindes von Position 2, müssen
        // diese Werte auf die gleiche Größe gebracht werden, um die Preise vergleichen zu können.
        else if (pli1.getSize().getValue().compareTo(pli2.getSize().getValue()) == 1) {
            BigDecimal dividedSize = NumberHelper.divide(pli1.getSize().getValue(), pli2.getSize().getValue());

            return NumberHelper.divide(pli1.getPrice().getValue(), dividedSize).compareTo(pli2.getPrice().getValue());
        }

        return 0;
    }

    /**
     * Vergleicht die Preise von zwei {@link PriceListItem}s miteinander auf Basis einer übergebenen Menge.
     * 
     * @param pli1
     * @param pli2
     * @param quantity
     * @return
     */
    private int comparePriceOfPriceListItemsWithQuantity(PriceListItem pli1, PriceListItem pli2, Amount quantity) {
        // Wenn eine Preislistenposition oder die Menge nicht gesetzt ist, wird von Gleichheit ausgegangen. Dieser Fall
        // dürfte aber normalerweise nicht eintreten.
        if (pli1 == null || pli2 == null || quantity == null) {
            return 0;
        }

        BigDecimal pli1PriceForQuantity = pli1.calculatePriceForQuantity(quantity);
        BigDecimal pli2PriceForQuantity = pli2.calculatePriceForQuantity(quantity);

        if (pli1PriceForQuantity != null && pli2PriceForQuantity != null) {
            return pli1PriceForQuantity.compareTo(pli2PriceForQuantity);
        }

        return 0;
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
     * Erstellt die String-Representation des Objekts {@link ProviderBase}.
     * 
     * @return Die String-Representation von {@link ProviderBase}
     */
    @Override
    public String toString() {
        return "ProviderBase [provider=" + providers + "]";
    }
}
