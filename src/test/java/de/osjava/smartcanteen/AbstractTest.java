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

    private static final int RECIPES = 109;
    private static final int INGREDIENTS_PER_RECIPE = 4;

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

        for (int i = 1; i <= RECIPES; i++) {
            result.add(createRecipe(Mockito.anyString(), i));
        }

        return result;
    }

    private Recipe createRecipe(String name, int rank) {
        Recipe result = new Recipe(name, rank);
        result.setIngredientList(createIngredientListItems());
        return result;
    }

    private Set<IngredientListItem> createIngredientListItems() {
        Set<IngredientListItem> result = new HashSet<IngredientListItem>();

        result.add(createIngredientListItem(Mockito.anyString(), true));

        for (int i = 1; i <= INGREDIENTS_PER_RECIPE; i++) {
            result.add(createIngredientListItem(Mockito.anyString(), false));
        }

        return result;
    }

    private IngredientListItem createIngredientListItem(String ingredientName, boolean randomIT) {
        return new IngredientListItem(createIngredient(ingredientName, randomIT), createIngredientQuantity());
    }

    private Amount createIngredientQuantity() {
        return new Amount(BigDecimal.valueOf(new Random().nextDouble()), getRandomIngredientUnit());
    }

    private UnitOfMeasurement getRandomIngredientUnit() {
        List<UnitOfMeasurement> keys = new ArrayList<UnitOfMeasurement>(Arrays.asList(UnitOfMeasurement.GRM,
                UnitOfMeasurement.KGM, UnitOfMeasurement.LTR, UnitOfMeasurement.MLR, UnitOfMeasurement.STK));
        return keys.get(new Random().nextInt(keys.size()));
    }

    private Ingredient createIngredient(String ingredientName, boolean randomIT) {
        return new Ingredient(ingredientName, (randomIT ? getRandomIngredientType() : IngredientType.VEGETABLE));
    }

    private IngredientType getRandomIngredientType() {
        List<IngredientType> keys = new ArrayList<IngredientType>(Arrays.asList(IngredientType.values()));
        return keys.get(new Random().nextInt(keys.size()));
    }
}
