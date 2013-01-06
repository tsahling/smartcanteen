package de.osjava.smartcanteen.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

        // Ermitteln bei welchem Anbieter, welche Konstellationen von Zutaten am günstigsten eingekauft werden können
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
     * 
     * @param ingredientQuantities
     * @return
     */
    private Map<AbstractProvider, Set<IngredientQuantity>> determineBestPriceProviders(
            Map<Ingredient, Amount> ingredientQuantities) {
        Map<AbstractProvider, Set<IngredientQuantity>> result = new HashMap<AbstractProvider, Set<IngredientQuantity>>();

        // TODO (Tim Sahling) Hier den neuen Algorithmus aus Scan umsetzen

        for (Entry<Ingredient, Amount> entry : ingredientQuantities.entrySet()) {

        }

        return result;
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

        IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient, quantity);

        if (result.containsKey(provider)) {
            result.get(provider).add(ingredientQuantity);
        }
        else {
            result.put(provider, new HashSet<IngredientQuantity>(Arrays.asList(ingredientQuantity)));
        }

        provider.subtractQuantityFromIngredient(ingredient, quantity);
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
     * Repräsentiert eine temporäre Datenhaltungsklasse, die eine Zutat und eine Menge aufnehmen kann.
     * 
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