package de.osjava.smartcanteen;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.mockito.Mockito;

import de.osjava.smartcanteen.base.HitListBase;
import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.datatype.IngredientType;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

public abstract class AbstractTest {

    private static final int RECIPES_MEAT = 46;
    private static final int RECIPES_VEGETABLE = 48;
    private static final int RECIPES_FISH = 15;
    private static final int TOTAL_RECIPES = RECIPES_MEAT + RECIPES_VEGETABLE + RECIPES_FISH;
    private static final int INGREDIENTS_PER_RECIPE = 4;

    protected HitListBase createHitListBase() {
        return null;
    }

    protected Canteen[] createCanteens() {
        return new Canteen[]{ createCanteen(CanteenLocation.ESSEN, 500), createCanteen(CanteenLocation.MUELHEIM, 300) };
    }

    private Canteen createCanteen(CanteenLocation location, int numberOfEmployees) {
        Canteen canteen = new Canteen(location, numberOfEmployees);
        return canteen;
    }

    protected RecipeBase createRecipeBase() {
        RecipeBase rb = new RecipeBase();
        rb.setRecipes(createRecipes());
        return rb;
    }

    protected ProviderBase createProviderBase() {
        return mock(ProviderBase.class);
    }

    private Set<Recipe> createRecipes() {
        Set<Recipe> result = new HashSet<Recipe>();

        Set<Integer> ranks = createRanks();

        for (int i = 1; i <= RECIPES_MEAT; i++) {
            result.add(createMeatRecipe(Mockito.anyString(), getRandomRank(ranks)));
        }

        for (int i = 1; i <= RECIPES_VEGETABLE; i++) {
            result.add(createVegetableRecipe(Mockito.anyString(), getRandomRank(ranks)));
        }

        for (int i = 1; i <= RECIPES_FISH; i++) {
            result.add(createFishRecipe(Mockito.anyString(), getRandomRank(ranks)));
        }

        return result;
    }

    private Set<Integer> createRanks() {
        Set<Integer> result = new HashSet<Integer>();

        for (int i = 1; i <= TOTAL_RECIPES; i++) {
            result.add(i);
        }

        return result;
    }

    private int getRandomRank(Set<Integer> ranks) {
        List<Integer> keys = new ArrayList<Integer>(ranks);
        Integer result = keys.get(new Random().nextInt(keys.size()));
        ranks.remove(result);
        return result;
    }

    private Recipe createFishRecipe(String name, int rank) {
        Recipe result = createRecipe(name, rank);
        result.setIngredientList(createIngredientListItems(IngredientType.FISH, IngredientType.VEGETABLE));
        return result;
    }

    private Recipe createVegetableRecipe(String name, int rank) {
        Recipe result = createRecipe(name, rank);
        result.setIngredientList(createIngredientListItems(IngredientType.VEGETABLE, IngredientType.VEGETABLE));
        return result;
    }

    private Recipe createMeatRecipe(String name, int rank) {
        Recipe result = createRecipe(name, rank);
        result.setIngredientList(createIngredientListItems(IngredientType.MEAT, IngredientType.VEGETABLE));
        return result;
    }

    private Recipe createRecipe(String name, int rank) {
        return new Recipe(name, rank);
    }

    private Set<IngredientListItem> createIngredientListItems(IngredientType mainIngredientType,
            IngredientType subIngredientType) {
        Set<IngredientListItem> result = new HashSet<IngredientListItem>();

        result.add(createIngredientListItem(Mockito.anyString(), mainIngredientType));

        for (int i = 1; i <= INGREDIENTS_PER_RECIPE; i++) {
            result.add(createIngredientListItem(Mockito.anyString(), subIngredientType));
        }

        return result;
    }

    private IngredientListItem createIngredientListItem(String ingredientName, IngredientType ingredientType) {
        return new IngredientListItem(createIngredient(ingredientName, ingredientType), createIngredientQuantity());
    }

    private Amount createIngredientQuantity() {
        return new Amount(BigDecimal.valueOf(new Random().nextDouble()), getRandomIngredientUnit());
    }

    private UnitOfMeasurement getRandomIngredientUnit() {
        List<UnitOfMeasurement> keys = new ArrayList<UnitOfMeasurement>(Arrays.asList(UnitOfMeasurement.GRM,
                UnitOfMeasurement.KGM, UnitOfMeasurement.LTR, UnitOfMeasurement.MLR, UnitOfMeasurement.STK));
        return keys.get(new Random().nextInt(keys.size()));
    }

    private Ingredient createIngredient(String ingredientName, IngredientType ingredientType) {
        return new Ingredient(ingredientName, ingredientType);
    }
}
