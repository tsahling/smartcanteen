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

    /**
    * Anhand der Methode isMeatRecipe kann abgefragt werden ob es sich um ein Rezept für ein Fleischgericht handelt
    * @return boolean
    */

    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(type);
    }

    /**
    * Anhand der Methode isFishRecipe kann abgefragt werden ob es sich um ein rezept für ein Fischgericht handelt
    * @return boolean
    */
   
    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(type);
    }

    /**
    * Anhand der Methode isVegetableRecipe kann abgefragt werden ob es sich um ein rezept für ein Vegetarischesgericht handelt
    * @return boolean
    */
    public boolean isVegetableRecipe() {
        return RecipeType.VEGETABLE.equals(type);
    }

    /**
     * Anhand der Methode getName kann der Name des Rezept ermittelt werden
     * @return Rückgabe des Rezeptnamen
     */
    public String getName() {
        return name;
    }

    /**
     * Anhand der Methode setName kann der Name des Rezept gesetzt werden
     * @param Name des Rezept
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * Anhand der Methode getType kann abgefragt werden um was für ein Rezepttyp (Fisch, Fleisch, Vegetarisch) es sich handelt
     * @return Typ des Rezept
     */
    public RecipeType getType() {
        return type;
    }

    /**
     * Anhand der Methode setType kann der Typ des Rezept gesetzt werden
     * @param type the type to set
     */
    public void setType(RecipeType type) {
        this.type = type;
    }

    /**
     * Anhand der Methode getIngredientList kann der die Inhaltsliste des Rezept ermittelt werden
     * @return Rückgabe eines Collection welche die Inhalte enthält
     */
    public Set<IngredientListItem> getIngredientList() {
        return ingredientList;
    }

    /**
     * Anhand der Methode setIngredientList kann  die Inhaltsliste des Rezept gesetzt werden
     * @param Inhaltsliste welche zu dem Rezept gehört
     */
    public void setIngredientList(Set<IngredientListItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * Anhand der Methode getRank kann  der Rang des Gericht aus der Hitsliste zu dem das Rezept gehört ermittelt werden
     * @return Rang des Gericht aus der Hitliste zu dem das Rezept gehört
     */
    public int getRank() {
        return rank;
    }

    /**
     * Anhand der Methode setRank kann  der Rang des Gericht aus der Hitsliste zu dem das Rezept gehört gesetzt werden
     * @param Rang aus der Hitliste um den Rang zu setzen
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