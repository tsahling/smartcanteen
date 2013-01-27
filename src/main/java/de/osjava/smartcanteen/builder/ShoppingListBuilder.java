package de.osjava.smartcanteen.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.builder.result.ShoppingListItem;
import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.helper.BuilderHelper;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link ShoppingListBuilder} ist eine der Geschäftslogikklassen und
 * für die Erstellung einer Einkaufsliste zuständig, die angibt welche Zutaten
 * in welcher Menge bei welchem Anbieter beschafft werden sollen. Als Ergebnis
 * liefert die Klasse eine {@link ShoppingList}, die dann im Output verwendet
 * werden kann.
 * 
 * @author Tim Sahling
 */
public class ShoppingListBuilder {

    private ProviderBase providerBase;
    private Canteen[] canteens;

    /**
     * Der Standardkonstruktor der {@link ShoppingListBuilder} initialisiert die
     * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten entgegen.
     * 
     * @param providerBase
     *            Die {@link ProviderBase} Verwaltungs- bzw. Containerklasse
     * @param canteens
     *            Eine oder mehrere {@link Canteen}
     */
    public ShoppingListBuilder(ProviderBase providerBase, Canteen... canteens) {
        this.providerBase = providerBase;
        this.canteens = canteens;
    }

    /**
     * Die einzige öffentliche Methode der Klasse {@link ShoppingListBuilder} ruft die Applikationslogik und den damit
     * verbundenen Optimierungsalgorithmus für die Generierung der {@link ShoppingList} auf.
     * 
     * @return Eine {@link ShoppingList} für die Verwendung im Output
     */
    public ShoppingList buildShoppingList() {
        ShoppingList result = new ShoppingList();

        // Summieren der Mengen aller für die Speisepläne benötigten Zutaten
        Map<Ingredient, Amount> ingredientQuantities = sumMenuPlanIngredientQuantities();

        // Ermitteln bei welchem Anbieter, welche Konstellationen von Zutaten am günstigsten eingekauft werden
        // können
        Map<AbstractProvider, Set<IngredientQuantity>> bestPriceProviders = determineBestPriceProviders(ingredientQuantities);

        // Erstellen der Einkaufslistenpositionen
        List<ShoppingListItem> shoppingListItems = new ArrayList<ShoppingListItem>();

        for (Entry<AbstractProvider, Set<IngredientQuantity>> entry : bestPriceProviders.entrySet()) {

            for (IngredientQuantity ingredientQuantity : entry.getValue()) {
                shoppingListItems.add(createShoppingListItem(entry.getKey(), ingredientQuantity));
            }
        }

        if (!shoppingListItems.isEmpty()) {
            result.setShoppingListItems(shoppingListItems);
        }

        return result;
    }

    /**
     * Ermittelt bei welchem {@link AbstractProvider}, welche Konstellationen von Zutaten am günstigsten eingekauft
     * werden können.
     * 
     * @param ingredientQuantities
     * @return
     */
    private Map<AbstractProvider, Set<IngredientQuantity>> determineBestPriceProviders(
            Map<Ingredient, Amount> ingredientQuantities) {
        Map<AbstractProvider, Set<IngredientQuantity>> result = new HashMap<AbstractProvider, Set<IngredientQuantity>>();

        for (Entry<Ingredient, Amount> entry : ingredientQuantities.entrySet()) {

            Ingredient ingredient = entry.getKey();
            Amount ingredientQuantity = entry.getValue();

            // Sucht alle Anbieter, die die Zutat vorrätig haben
            Set<AbstractProvider> providersWithIngredient = providerBase.findProvidersByIngredient(ingredient);

            // Ein Anbieter hat die Zutat vorrätig
            if (providersWithIngredient.size() == 1) {
                computeOneProviderWithIngredient(result, providersWithIngredient.iterator().next(), ingredient,
                        ingredientQuantity);
            }
            // Mehrere Anbieter haben die Zutat vorrätig
            else if (providersWithIngredient.size() > 1) {
                computeMoreProvidersWithIngredient(result, providersWithIngredient, ingredient, ingredientQuantity);
            }
        }

        return result;
    }

    /**
     * Berechnet den Fall, dass nur ein einzelner {@link AbstractProvider} die {@link Ingredient} vorrätig hat.
     * 
     * @param result
     * @param provider
     * @param ingredient
     * @param ingredientQuantity
     */
    private void computeOneProviderWithIngredient(Map<AbstractProvider, Set<IngredientQuantity>> result,
            AbstractProvider provider, Ingredient ingredient, Amount ingredientQuantity) {
        // Zutat muss unabhängig vom Preis bei Anbieter bestellt werden, da er der einzige Anbieter der Zutat ist
        addAndSubtractIngredientAndQuantity(result, provider, ingredient, ingredientQuantity, ingredientQuantity);
    }

    /**
     * Berechnet den Fall, dass mehrere {@link AbstractProvider} die {@link Ingredient} vorrätig haben.
     * 
     * @param result
     * @param providers
     * @param ingredient
     * @param ingredientQuantity
     */
    private void computeMoreProvidersWithIngredient(Map<AbstractProvider, Set<IngredientQuantity>> result,
            Set<AbstractProvider> providers, Ingredient ingredient, Amount ingredientQuantity) {

        Set<AbstractProvider> providersWithIngredientAndQuantity = providerBase.findProvidersByIngredientAndQuantity(
                providers, ingredient, ingredientQuantity);

        // Wenn kein Anbieter die Zutat in der benötigten Menge vorrätig hat, muss die Bestellung auf mehrere
        // Anbieter aufgeteilt werden.
        if (providersWithIngredientAndQuantity.size() == 0) {
            computeNoProviderWithIngredientAndQuantity(result, providers, ingredient, ingredientQuantity);
        }
        // Wenn ein Anbieter die Zutat und die benötigte Menge vorrätig hat, bestellen wir bei ihm, da eine
        // Bestellung bei nur einem Anbieter Transportkosten spart.
        else if (providersWithIngredientAndQuantity.size() == 1) {
            computeOneProviderWithIngredientAndQuantity(result, providersWithIngredientAndQuantity.iterator().next(),
                    ingredient, ingredientQuantity);
        }
        // Wenn mehrere Anbieter die Zutat und die benötigte Menge vorrätig haben, muss bei dem Anbieter bestellt
        // werden, der die benötigte Menge am günstigsten anbietet
        else {
            computeMoreProvidersWithIngredientAndQuantity(result, providersWithIngredientAndQuantity, ingredient,
                    ingredientQuantity);
        }
    }

    /**
     * Berechnet den Fall, dass kein {@link AbstractProvider} die {@link Ingredient} in der benötigten {@link Amount}
     * vorrätig hat.
     * 
     * @param result
     * @param providers
     * @param ingredient
     * @param ingredientQuantity
     */
    private void computeNoProviderWithIngredientAndQuantity(Map<AbstractProvider, Set<IngredientQuantity>> result,
            Set<AbstractProvider> providers, Ingredient ingredient, Amount ingredientQuantity) {

        // Ermitteln der preisgünstigsten Anbieter pro Zutat
        List<AbstractProvider> bestPriceProviders = providerBase.findBestPriceProvidersByIngredient(
                providers, ingredient);

        for (AbstractProvider bestPriceProvider : bestPriceProviders) {

            // Suchen der Menge einer Zutat von einem Anbieter
            Amount ingredientQuantityOfProvider = bestPriceProvider.findQuantityByIngredient(ingredient);

            if (ingredientQuantityOfProvider != null) {

                // Subtrahiert von der insgesamt benötigten Menge, die Menge des jeweiligen Anbieters
                BigDecimal subtract = NumberHelper.subtract(ingredientQuantity.getValue(),
                        ingredientQuantityOfProvider.getValue());

                // Wenn das Ergebnis der Subtraktion größer oder gleich 0 ist, wird die komplette Menge des
                // Anbieters bestellt und die insgesamt benötigte Menge der Zutat entsprechend angepasst
                if (NumberHelper.compareGreaterOrEqual(subtract, BigDecimal.ZERO)) {
                    addAndSubtractIngredientAndQuantity(result, bestPriceProvider, ingredient,
                            ingredientQuantityOfProvider, ingredientQuantity);
                }
                // Wenn das Ergebnis der Subtraktion kleiner 0 ist, ist die restliche Menge beim Anbieter
                // bestellbar
                else {
                    addAndSubtractIngredientAndQuantity(result, bestPriceProvider, ingredient, ingredientQuantity,
                            ingredientQuantity);
                    break;
                }
            }
        }
    }

    /**
     * Berechnet den Fall, dass ein {@link AbstractProvider} die {@link Ingredient} in der benötigten {@link Amount}
     * vorrätig hat.
     * 
     * @param result
     * @param provider
     * @param ingredient
     * @param ingredientQuantity
     */
    private void computeOneProviderWithIngredientAndQuantity(Map<AbstractProvider, Set<IngredientQuantity>> result,
            AbstractProvider provider, Ingredient ingredient, Amount ingredientQuantity) {
        addAndSubtractIngredientAndQuantity(result, provider, ingredient, ingredientQuantity, ingredientQuantity);
    }

    /**
     * Berechnet den Fall, dass mehrere {@link AbstractProvider} die {@link Ingredient} in der benötigten {@link Amount}
     * vorrätig haben.
     * 
     * @param result
     * @param providers
     * @param ingredient
     * @param ingredientQuantity
     */
    private void computeMoreProvidersWithIngredientAndQuantity(Map<AbstractProvider, Set<IngredientQuantity>> result,
            Set<AbstractProvider> providers, Ingredient ingredient, Amount ingredientQuantity) {

        List<ProviderRanking> providerRankings = new ArrayList<ProviderRanking>();

        // 1. Schritt: Ermitteln der preisgünstigsten Anbieter pro Zutat und Menge
        List<AbstractProvider> bestPriceProviders = providerBase.findBestPriceProvidersByIngredientAndQuantity(
                providers, ingredient, ingredientQuantity);

        if (bestPriceProviders != null && !bestPriceProviders.isEmpty()) {

            for (int i = 0; i < bestPriceProviders.size(); i++) {
                providerRankings.add(new ProviderRanking(bestPriceProviders.get(i), i));
            }

            // 2. Schritt: Vergleich der Ausschussmenge
            List<AbstractProvider> optimalQuantityProviders = providerBase
                    .findOptimalQuantityProvidersByIngredientAndQuantity(providers, ingredient, ingredientQuantity);

            for (ProviderRanking providerRanking : providerRankings) {
                int lastIndexOf = optimalQuantityProviders.lastIndexOf(providerRanking.getProvider());
                providerRanking.setRank(providerRanking.getRank() + lastIndexOf + 1);
            }

            // 3. Schritt: Sortiert die Anbieter nach Ranking
            Collections.sort(providerRankings, new Comparator<ProviderRanking>() {

                @Override
                public int compare(ProviderRanking pr1, ProviderRanking pr2) {
                    return pr1.getRank().compareTo(pr2.getRank());
                }
            });

            // 4. Schritt: Vergleicht ob Anbieter mit gleichem Rank bereits im Ergebnis sind. Wenn ja, wird dieser
            // bevorzugt verwendet.
            Iterator<ProviderRanking> iterator = providerRankings.iterator();

            while (iterator.hasNext()) {

                ProviderRanking next = iterator.next();

                if (iterator.hasNext()) {

                    ProviderRanking next2 = iterator.next();

                    if (next.getRank().equals(next2.getRank())) {

                        if (result.containsKey(next.getProvider())) {
                            next.setRank(next.getRank() - 1);
                        }

                        if (result.containsKey(next2.getProvider())) {
                            next2.setRank(next2.getRank() - 1);
                        }
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }

            }

            // 5. Schritt: Sortiert final die Anbieter nach Ranking
            Collections.sort(providerRankings, new Comparator<ProviderRanking>() {

                @Override
                public int compare(ProviderRanking pr1, ProviderRanking pr2) {
                    return pr1.getRank().compareTo(pr2.getRank());
                }
            });

            addAndSubtractIngredientAndQuantity(result, providerRankings.iterator().next().getProvider(), ingredient,
                    ingredientQuantity, ingredientQuantity);
        }
    }

    /**
     * 
     * @param result
     * @param provider
     * @param ingredient
     * @param ingredientQuantity
     * @param quantityToSubtractFrom
     */
    private void addAndSubtractIngredientAndQuantity(Map<AbstractProvider, Set<IngredientQuantity>> result,
            AbstractProvider provider, Ingredient ingredient, Amount ingredientQuantity,
            Amount ingredientQuantityToSubtractFrom) {

        // Berechnet auf Basis der Zutat und der Menge der Zutat die tatsächlich vom Anbieter zu bestellende Menge
        Amount quantityToStore = provider.calculateQuantityFromIngredientAndQuantity(ingredient, ingredientQuantity);

        // Überprüfen ob Anbieter die Zutat in gewünschter Menge hat
        if (quantityToStore != null && provider.hasIngredientWithQuantity(ingredient, quantityToStore)) {
            // Hinzufügen der Zutat und der Menge zum Ergebnis
            addIngredientAndQuantity(result, provider, ingredient, quantityToStore);
            // Subtraktion der Menge einer Zutat vom Anbieter
            provider.subtractQuantityFromIngredient(ingredient, quantityToStore);
            // Subtraktion der Menge einer Zutat von der Gesamtmenge der Zutat
            ingredientQuantityToSubtractFrom.subtract(quantityToStore);
        }
    }

    /**
     * 
     * @param result
     * @param provider
     * @param ingredient
     * @param quantity
     */
    private void addIngredientAndQuantity(Map<AbstractProvider, Set<IngredientQuantity>> result,
            AbstractProvider provider, Ingredient ingredient, Amount quantity) {

        IngredientQuantity ingredientQuantity = new IngredientQuantity(new Ingredient(ingredient.getName(),
                ingredient.getIngredientType()), new Amount(quantity.getValue(), quantity.getUnit()));

        if (result.containsKey(provider)) {
            result.get(provider).add(ingredientQuantity);
        }
        else {
            result.put(provider, new HashSet<IngredientQuantity>(Arrays.asList(ingredientQuantity)));
        }
    }

    /**
     * Erstellt eine Einkaufslistenposition.
     * 
     * @param provider
     * @param ingredientQuantity
     * @return
     */
    private ShoppingListItem createShoppingListItem(AbstractProvider provider, IngredientQuantity ingredientQuantity) {
        return new ShoppingListItem(ingredientQuantity.getIngredient(), ingredientQuantity.getQuantity(), provider);
    }

    /**
     * Summiert alle Mengen der für die Speisepläne benötigten Zutaten bzw. Rezepte.
     * 
     * @return
     */
    private Map<Ingredient, Amount> sumMenuPlanIngredientQuantities() {
        Map<Ingredient, Amount> result = new HashMap<Ingredient, Amount>();

        if (canteens != null) {

            for (Canteen canteen : canteens) {
                MenuPlan menuPlan = canteen.getMenuPlan();

                if (menuPlan != null && menuPlan.getMeals() != null && !menuPlan.getMeals().isEmpty()) {

                    // Kalkuliert wieviele Gerichte pro Kantine gekocht werden müssen
                    BigDecimal totalMealsForCanteen = BuilderHelper.calculateTotalMealsForCanteen(canteen);

                    // Gruppiert die Gerichte eines Speiseplans nach Datum
                    Map<Date, List<Meal>> mealsGroupedByDate = menuPlan.getMealsGroupedByDate();

                    if (mealsGroupedByDate != null && !mealsGroupedByDate.isEmpty()) {

                        for (Entry<Date, List<Meal>> entry : mealsGroupedByDate.entrySet()) {

                            Iterator<Meal> iterator = entry.getValue().iterator();

                            int index = 0;

                            while (iterator.hasNext()) {

                                Recipe recipe = iterator.next().getRecipe();

                                BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(index,
                                        totalMealsForCanteen);

                                if (recipe != null && recipe.getIngredientList() != null && !recipe.getIngredientList()
                                        .isEmpty()) {

                                    for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                                        Ingredient ingredient = ingredientListItem.getIngredient();
                                        Amount quantity = ingredientListItem.getQuantity();

                                        if (ingredient != null && quantity != null) {

                                            if (mealMultiplyFactor != null) {

                                                BigDecimal ingredientQuantity = NumberHelper.multiply(
                                                        quantity.getValue(), mealMultiplyFactor);

                                                if (result.containsKey(ingredient)) {
                                                    result.get(ingredient).add(
                                                            new Amount(ingredientQuantity, quantity.getUnit()));
                                                }
                                                else {
                                                    result.put(ingredient,
                                                            new Amount(ingredientQuantity, quantity.getUnit()));
                                                }
                                            }
                                        }
                                    }
                                }

                                index++;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Repräsentiert eine temporäre Datenhaltungsklasse, die einen Anbieter und einen Rang aufnehmen kann.
     */
    private static final class ProviderRanking {
        private AbstractProvider provider;
        private Integer rank;

        public ProviderRanking(AbstractProvider provider, Integer rank) {
            this.provider = provider;
            this.rank = rank;
        }

        public AbstractProvider getProvider() {
            return provider;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((provider == null) ? 0 : provider.hashCode());
            result = prime * result + ((rank == null) ? 0 : rank.hashCode());
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
            ProviderRanking other = (ProviderRanking) obj;
            if (provider == null) {
                if (other.provider != null)
                    return false;
            }
            else if (!provider.equals(other.provider))
                return false;
            if (rank == null) {
                if (other.rank != null)
                    return false;
            }
            else if (!rank.equals(other.rank))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "ProviderRanking [provider=" + provider + ", rank=" + rank + "]";
        }

    }

    /**
     * Repräsentiert eine temporäre Datenhaltungsklasse, die eine Zutat und eine Menge aufnehmen kann.
     */
    private static final class IngredientQuantity {
        private Ingredient ingredient;
        private Amount quantity;

        public IngredientQuantity(Ingredient ingredient, Amount quantity) {
            this.ingredient = ingredient;
            this.quantity = quantity;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }

        public Amount getQuantity() {
            return quantity;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
            result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
            IngredientQuantity other = (IngredientQuantity) obj;
            if (ingredient == null) {
                if (other.ingredient != null)
                    return false;
            }
            else if (!ingredient.equals(other.ingredient))
                return false;
            if (quantity == null) {
                if (other.quantity != null)
                    return false;
            }
            else if (!quantity.equals(other.quantity))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "IngredientQuantity [ingredient=" + ingredient + ", quantity=" + quantity + "]";
        }
    }
}