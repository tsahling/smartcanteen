import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Write a description of class RecipeBase here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecipeBase
{
    private Set<Recipe> recipes;

    /**
     * Constructor for objects of class RecipeBase
     */
    public RecipeBase()
    {
    }
    
    public Set<Recipe> getRecipes() {
        return this.recipes;
    }
    
    public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {       
        return null;
    }
    
    public Set<Recipe> findRecipesSortedByRank() {
        return null;
    }
    
    public Recipe findHighestRankRecipe() {
        return null;
    }
    
    public Recipe findLowestRankRecipe() {
        return null;
    }
}
