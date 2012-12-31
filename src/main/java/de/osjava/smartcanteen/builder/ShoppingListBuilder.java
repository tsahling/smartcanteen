package de.osjava.smartcanteen.builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import de.osjava.smartcanteen.helper.PropertyHelper;

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

    private static final BigDecimal PROP_CANTEEN_MOREMEALSTHANEMPLOYEESFACTOR = new BigDecimal(
            PropertyHelper.getProperty("canteen.moreMealsThanEmployeesFactor"));
    private static final Integer PROP_PLANINGPERIOD_MEALSPERDAY = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.mealsPerDay"));

    private static final BigDecimal PROP_CANTEEN_FAVMEALMULTIPLYFACTOR = new BigDecimal(
            PropertyHelper.getProperty("canteen.favMealMultiplyFactor"));

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

        // Summieren der Mengen aller für den Speiseplan benötigten Zutaten
        Map<Ingredient, Amount> ingredientAmounts = sumIngredientAmounts();

        // Ermitteln bei welchem Anbieter, welche Konstellationen von Zutaten am günstigsten eingekauft werden können
        Map<AbstractProvider, Set<IngredientAmount>> test = bla(ingredientAmounts);

        // Erstellen der Einkaufslistenpositionen
        List<ShoppingListItem> shoppingListItems = new ArrayList<ShoppingListItem>();

        for (Entry<AbstractProvider, Set<IngredientAmount>> entry : test.entrySet()) {

            for (IngredientAmount ingredientAmount : entry.getValue()) {
                shoppingListItems.add(createShoppingListItem(entry.getKey(), ingredientAmount));
            }
        }

        if (!shoppingListItems.isEmpty()) {
            result.setShoppingListItems(shoppingListItems);
        }

        return result;
    }

    private ShoppingListItem createShoppingListItem(AbstractProvider provider, IngredientAmount ingredientAmount) {
        ShoppingListItem result = new ShoppingListItem();
        result.setProvider(provider);
        result.setIngredient(ingredientAmount.getIngredient());
        result.setQuantity(ingredientAmount.getAmount());
        return result;
    }

    private static final class IngredientAmount {
        private Ingredient ingredient;
        private Amount amount;

        public Ingredient getIngredient() {
            return ingredient;
        }

        public Amount getAmount() {
            return amount;
        }

    }

    private Map<AbstractProvider, Set<IngredientAmount>> bla(Map<Ingredient, Amount> ingredientAmounts) {
        Map<AbstractProvider, Set<IngredientAmount>> result = new HashMap<AbstractProvider, Set<IngredientAmount>>();

        for (Entry<Ingredient, Amount> entry : ingredientAmounts.entrySet()) {
            Set<AbstractProvider> providers = providerBase.findProviderByIngredientAndQuantity(entry.getKey(), entry
                    .getValue().getValue().intValue());

            if (providers != null && !providers.isEmpty()) {

                if (providers.size() == 1) {

                }
                else {

                }
            }
            else {

            }

        }

        // TODO(tsahling) provide sensible implementation
        return result;
    }

    /**
     * Summiert alle Mengen der für die Speisepläne benötigten Zutaten bzw. Rezepte.
     * 
     * @return
     */
    private Map<Ingredient, Amount> sumIngredientAmounts() {
        Map<Ingredient, Amount> result = new HashMap<Ingredient, Amount>();

        if (canteens != null) {

            for (Canteen canteen : canteens) {
                MenuPlan menuPlan = canteen.getMenuPlan();

                if (menuPlan != null) {

                    if (menuPlan.getMeals() != null && !menuPlan.getMeals().isEmpty()) {

                        // Kalkuliert wieviele Gerichte pro Kantine gekocht werden müssen
                        BigDecimal totalMealsForCanteen = calculateTotalMealsForCanteen(canteen);

                        // Gruppiert die Gerichte eines Speiseplans nach Datum
                        Map<Date, List<Meal>> mealsGroupedByDate = menuPlan.getMealsGroupedByDate();

                        for (Entry<Date, List<Meal>> entry : mealsGroupedByDate.entrySet()) {

                            for (int i = 0; i < entry.getValue().size(); i++) {

                                Recipe recipe = entry.getValue().get(i).getRecipe();

                                BigDecimal mealMultiplyFactor = calculateMealMultiplyFactor(i, totalMealsForCanteen);

                                if (recipe != null && recipe.getIngredientList() != null && !recipe.getIngredientList()
                                        .isEmpty()) {

                                    for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                                        Ingredient ingredient = ingredientListItem.getIngredient();
                                        Amount quantity = ingredientListItem.getQuantity();

                                        if (ingredient != null && quantity != null) {

                                            if (mealMultiplyFactor != null) {
                                                quantity.setValue(quantity.getValue().multiply(mealMultiplyFactor));
                                            }

                                            if (result.containsKey(ingredient)) {
                                                result.get(ingredient).add(quantity);
                                            }
                                            else {
                                                result.put(ingredient, quantity);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Kalkuliert die Gesamtmenge an Gerichten die für eine Kantine gekocht werden müssen, ausgehend von der
     * Anforderung, dass eine gewisse Anzahl bzw. ein gewisser Faktor an Gerichten pro Kantine mehr vorgehalten werden.
     * 
     * @param canteen
     * @return
     */
    private BigDecimal calculateTotalMealsForCanteen(Canteen canteen) {
        return new BigDecimal(canteen.getNumberOfEmployees()).multiply(PROP_CANTEEN_MOREMEALSTHANEMPLOYEESFACTOR);
    }

    /**
     * Kalkuliert die Gesamtmenge an Gerichten die, je nach Priorität des Gerichts, gekocht werden müssen, ausgehend von
     * der Anforderung, dass das beliebtestete Gericht auf Basis eines Faktors öfter gekocht wird als die anderen beiden
     * Gerichte.
     * 
     * @param priority
     * @param totalMeals
     * @return
     */
    private BigDecimal calculateMealMultiplyFactor(int priority, BigDecimal totalMeals) {
        BigDecimal result = null;

        if (priority == 0) {
            result = totalMeals.multiply(PROP_CANTEEN_FAVMEALMULTIPLYFACTOR);
        }
        else {
            result = totalMeals.multiply(PROP_CANTEEN_FAVMEALMULTIPLYFACTOR)
                    .divide(BigDecimal.valueOf(PROP_PLANINGPERIOD_MEALSPERDAY - 1)).setScale(0, RoundingMode.HALF_UP);
        }

        return result;
    }
}