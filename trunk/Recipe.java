
/**
 * Write a description of class Recipe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Recipe
{
    private RecipeType type;
    private IngredientList ingredientList;
    private int popularity;
    
    /**
     * Constructor for objects of class Recipe
     */
    public Recipe()
    {

    }
    
    public RecipeType getType() {
        return this.type;
    }
    
    public int getPopularity() {
        return this.popularity;
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
