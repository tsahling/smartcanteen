import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * 
 * @author Francesco Luciano
 */
public class RecipeBase {

    private Set<Recipe> recipes;

    public RecipeBase() {

    }

    public Recipe addRecipe() {
        return null;
    }

    public Recipe updateRecipe() {
        return null;
    }

    public void removeRecipe() {
    }

    public Recipe findRecipeByName(String name) {
        return null;
    }

    public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
        return null;
    }

    public Set<Recipe> getRecipesSortedByRank() {
        return null;
    }

    public Recipe findRecipeByRank(int rank) {
        return null;
    }

    public Recipe findHighestRankRecipe() {
        return null;
    }

    public Recipe findLowestRankRecipe() {
        return null;
    }

    /**
     * @return the recipes
     */
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets the recipes.
     * 
     * @param recipes the recipes to set
     */
    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    /**
     * Erstellt die String-Representation des Objekts {@link RecipeBase}.
     * 
     * @return Die String-Representation von {@link RecipeBase}
     */
    @Override
    public String toString() {
        return null;
    }
}

