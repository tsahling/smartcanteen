package de.osjava.smartcanteen.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.IngredientType;
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
     * Methode um die benötigte {@link Amount} einer {@link Ingredient} in allen Konstellationen auf die
     * {@link AbstractProvider} zu verteilen, bei denen die {@link Ingredient} beschaffbar ist.
     * 
     * @param ingredient
     * @param quantity
     * @return
     */
    public Map<AbstractProvider, List<Amount>> distributeQuantityOfIngredientToProviders(Ingredient ingredient,
            Amount quantity) {

        Map<AbstractProvider, List<Amount>> result = new HashMap<AbstractProvider, List<Amount>>();

        // Sucht alle Anbieter bei denen die Zutat beschaffbar ist
        Set<AbstractProvider> ingredientProviders = findProvidersByIngredient(ingredient);

        // Erstellt alle möglichen Anbieterkombinationen
        List<List<AbstractProvider>> ingredientProviderCombinations = createIngredientProviderCombinations(ingredientProviders);

        if (ingredientProviderCombinations != null && !ingredientProviderCombinations.isEmpty()) {

            for (List<AbstractProvider> ingredientProviderCombination : ingredientProviderCombinations) {

                // Erstellen eines temporären Sets für Anbieter damit das Ausgangsset im weiteren Ablauf nicht verändert
                // wird
                Set<AbstractProvider> tempIngredientProviders = Sets.newHashSet(ingredientProviders);

                if (ingredientProviderCombination.size() == 1) {

                    AbstractProvider provider = ingredientProviderCombination.iterator().next();

                    // Nur wenn bei einem einzelnen Anbieter die Zutat vollständig beschaffbar ist, kommt dieser in das
                    // Ergebnis. Die anderen Anbieter werden mit der Menge 0 ins Ergebnis übernommen.
                    if (provider.hasIngredientWithQuantity(ingredient, quantity)) {
                        putToResultMap(result, provider, quantity);
                        tempIngredientProviders.remove(provider);
                        putProvidersWithQuantityToResultMap(result, tempIngredientProviders,
                                new Amount(quantity).subtract(quantity));
                    }
                }
                else {
                    Amount quantityToDistribute = new Amount(quantity);

                    for (AbstractProvider provider : ingredientProviderCombination) {

                        // Sucht die Menge einer Zutat von einem Anbieter
                        Amount providerIngredientQuantity = provider.findQuantityByIngredient(ingredient);

                        // Subtrahiert von der zu verteilenden Menge die Menge des Anbieters
                        Amount quantityToDistributeMinusProviderQuantity = new Amount(quantityToDistribute)
                                .subtract(providerIngredientQuantity);

                        // Zutat ist bei Anbieter vollständig bestellbar
                        if (!NumberHelper.compareGreaterOrEqual(quantityToDistributeMinusProviderQuantity.getValue(),
                                BigDecimal.ZERO)) {
                            // Komplett benötigte Menge des Anbieters wird ins Ergebnis übernommen
                            putToResultMap(result, provider, quantityToDistribute);
                            // Zu verteilende Menge wird um komplett benötigte Menge reduziert
                            quantityToDistribute = new Amount(quantityToDistribute).subtract(quantityToDistribute);
                        }
                        // Zutat ist bei Anbieter nicht vollständig beschaffbar
                        else {
                            // Beschaffbare Menge des Anbieters wird ins Ergebnis übernommen
                            putToResultMap(result, provider, providerIngredientQuantity);
                            // Zu verteilende Menge wird um Menge des Anbieters reduziert
                            quantityToDistribute = new Amount(quantityToDistribute)
                                    .subtract(providerIngredientQuantity);
                        }
                    }

                    // Alle Anbieter die nicht durchlaufen werden müssen mit der Menge 0 ins Ergebnis übernommen werden
                    // um die Datenzuordnung konsistent zu halten
                    tempIngredientProviders.removeAll(ingredientProviderCombination);

                    putProvidersWithQuantityToResultMap(result, tempIngredientProviders,
                            new Amount(quantity).subtract(quantity));

                    // Wenn die zu verteilende Menge nicht auf alle Anbieter verteilt werden konnte, werden die Einträge
                    // wieder gelöscht, da die Zutat dann in der Anbieterkombination nicht beschaffbar ist
                    if (NumberHelper.compareGreater(quantityToDistribute.getValue(), BigDecimal.ZERO)) {
                        removeLastQuantityFromResultMap(result, ingredientProviderCombination);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Methode um einen {@link AbstractProvider} und einen {@link Amount} ins Ergebnis zu übernehmen.
     * 
     * @param result
     * @param key
     * @param value
     */
    private void putToResultMap(Map<AbstractProvider, List<Amount>> result, AbstractProvider key, Amount value) {
        if (result.containsKey(key)) {
            result.get(key).add(new Amount(value));
        }
        else {
            result.put(key, Lists.newLinkedList(Arrays.asList(new Amount(value))));
        }
    }

    /**
     * Methode um mehrere {@link AbstractProvider} mit einer {@link Amount} ins Ergebnis zu übernehmen.
     * 
     * @param result
     * @param providers
     * @param quantity
     */
    private void putProvidersWithQuantityToResultMap(Map<AbstractProvider, List<Amount>> result,
            Set<AbstractProvider> providers, Amount quantity) {

        if (providers != null && !providers.isEmpty()) {

            for (AbstractProvider provider : providers) {
                putToResultMap(result, provider, quantity);
            }
        }
    }

    /**
     * Methode um die letzte {@link Amount} der übergebenen {@link AbstractProvider} zu entfernen.
     * 
     * @param result
     * @param providers
     */
    private void removeLastQuantityFromResultMap(Map<AbstractProvider, List<Amount>> result,
            List<AbstractProvider> providers) {

        if (result != null && !result.isEmpty() && providers != null && !providers.isEmpty()) {

            for (Entry<AbstractProvider, List<Amount>> entry : result.entrySet()) {

                if (providers.contains(entry.getKey())) {
                    Amount lastQuantity = (Amount) ((LinkedList<Amount>) entry.getValue()).getLast();
                    entry.getValue().remove(lastQuantity);
                }
            }
        }
    }

    /**
     * Methode zum Erstellen aller möglichen Anbieterkombinationen auf Basis der übergebenen {@link AbstractProvider}.
     * 
     * Als erstes wird mit Hilfe der Google Guava Library ein PowerSet erstellt, welches alle möglichen Kombinationen
     * der
     * übergebenen {@link AbstractProvider} enthält.
     * 
     * Beispiel: ImmutableSet.of(1, 2) => {{}, {1}, {2}, {1, 2}}
     * 
     * Danach wird der leere Eintrag aus dem PowerSet entfernt, da dieser später nicht mehr benötigt wird.
     * 
     * Enthält ein Subset des PowerSet mehr als einen Eintrag, müssen alle Permutationen des Subsets zusätzlich mit
     * Hilfe Google Guava Library erstellt werden und im Ergebnis gespeichert werden. Die Aufteilung einer
     * {@link Ingredient} müssen passieren, da diese abhängig ist von der Reihenfolge der {@link AbstractProvider} und
     * der Verfügbarkeit der {@link Ingredient}.
     * 
     * @param ingredientProviders Das Set mit übergebenen {@link AbstractProvider}
     * @return Eine Liste von Listen mit allen Kombinationen von {@link AbstractProvider}
     */
    private List<List<AbstractProvider>> createIngredientProviderCombinations(Set<AbstractProvider> ingredientProviders) {
        List<List<AbstractProvider>> result = new LinkedList<List<AbstractProvider>>();

        if (ingredientProviders != null && !ingredientProviders.isEmpty()) {
            // Erstellung des PowerSet mit allen Anbieterkombinationen
            Set<Set<AbstractProvider>> powerSet = Sets.powerSet(ingredientProviders);

            for (Set<AbstractProvider> providers : powerSet) {

                if (!providers.isEmpty()) {

                    if (providers.size() == 1) {
                        result.add(Lists.newLinkedList(providers));
                    }
                    else {
                        // Erstellen aller Permutationen (Anordnungsmöglichkeiten) der Anbieter
                        Collection<List<AbstractProvider>> permutations = Collections2.permutations(providers);

                        for (List<AbstractProvider> perm : permutations) {
                            if (!perm.isEmpty()) {
                                result.add(Lists.newLinkedList(perm));
                            }
                        }
                    }
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
     * Vergleicht die Preise von zwei {@link PriceListItem}s miteinander.
     * 
     * @param pli1
     * @param pli2
     * @return
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
