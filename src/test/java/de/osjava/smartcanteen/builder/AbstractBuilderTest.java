package de.osjava.smartcanteen.builder;

import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.RecipeType;

public abstract class AbstractBuilderTest {

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
		result.add(createRecipe("Currywurst", RecipeType.MEAT, 1));
		result.add(createRecipe("Hamburger", RecipeType.MEAT, 2));
		result.add(createRecipe("Cheeseburger", RecipeType.MEAT, 3));
		result.add(createRecipe("Pommes", RecipeType.VEGETABLE, 4));
		result.add(createRecipe("Zander", RecipeType.FISH, 5));
		result.add(createRecipe("Gulasch", RecipeType.MEAT, 6));
		result.add(createRecipe("Gemüsepfanne", RecipeType.VEGETABLE, 7));
		result.add(createRecipe("Vegiburger", RecipeType.VEGETABLE, 8));
		result.add(createRecipe("Lachs", RecipeType.FISH, 9));
		result.add(createRecipe("Tintenfisch", RecipeType.FISH, 10));
		return result;
	}

	private Recipe createRecipe(String name, RecipeType type, int rank) {
		Recipe result = new Recipe(name, type, rank);
		return result;
	}
}
