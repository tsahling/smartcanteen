import java.util.Set;

/**
 * 
 * @author Franceso Luciano
 */
public class Recipe {

    private String name;
    private RecipeType type;
    private Set<IngredientListItem> ingredientList;
    private int rank;

    /**
     * Standardkonstruktor
     */
    public Recipe() {

    }

    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(type);
    }

    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(type);
    }

    public boolean isVegetableRecipe() {
        return RecipeType.VEGETABLE.equals(type);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public RecipeType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(RecipeType type) {
        this.type = type;
    }

    /**
     * @return the ingredientList
     */
    public Set<IngredientListItem> getIngredientList() {
        return ingredientList;
    }

    /**
     * @param ingredientList the ingredientList to set
     */
    public void setIngredientList(Set<IngredientListItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Diese Methode gibt den HashCode-Wert fuer das Objekt zurueck, von dem die Methode aufgerufen 
     * wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
          return 0;
    }
    
    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist, von dem die Methode 
     * aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Recipe}.
     * 
     * @return Die String-Representation von {@link Recipe}
     */
    @Override
    public String toString() {
        return null;
    }
}