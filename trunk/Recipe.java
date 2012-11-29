import java.util.Set;

/**
 * Write a description of class Recipe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Recipe
{
    private String name;
    private RecipeType type;
    private Set<IngredientListItem> ingredientList;
    private int rank;
    
    /**
     * Constructor for objects of class Recipe
     */
    public Recipe()
    {

    }
        
    public IngredientListItem addIngredientListItem() {
        return null;
    }
    
    public IngredientListItem updateIngredientListItem() {
        return null;
    }
    
    public void removeIngredientListItem() {       
    }
    
    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(this.type);
    }
    
    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(this.type);
    }
    
    public boolean isVegetableRecipe() {
        return RecipeType.VEGETABLE.equals(this.type);
    }
}
