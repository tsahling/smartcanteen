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
    
    public Set<Recipe> findRecipesOfRecipeType(RecipeType recipeType) {
        Set<Recipe> result = new HashSet<Recipe>();
        
        for(Recipe recipe : recipes) {
            if(recipeType.equals(recipe.getType())) {
                result.add(recipe);
            }
        }
        
        return result;
    }
    
    public Set<Recipe> findRecipesSortedByPopularity() {
        Set<Recipe> result = new TreeSet<Recipe>(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return Integer.valueOf(o1.getPopularity()).compareTo(Integer.valueOf(o2.getPopularity()));
                }         
        });      
        result.addAll(this.recipes);      
        return result;
    }
    
    public Recipe findMostPopularRecipe() {
        return null;
    }
    
    public Recipe findLowestPopularRecipe() {
        return null;
    }
}
