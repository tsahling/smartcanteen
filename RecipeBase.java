import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Die Klasse Recipebase ist die Repräsentation der Rezepte Sammlung, welche als Eingabedatei übergeben wird.
 * @author Francesco Luciano
 */
public class RecipeBase {
    
    private Set<Recipe> recipes;
    
    /**
     * Konstruktor der Klasse Recipebase
     *  
    */
    public RecipeBase() {

    }
    
    /**
     * Mithilfe der Methode addRecipe() kann ein neues Rezept in die Datenstrukt recipes hinzugefügt werden
     * @return Die aktualisierte Datenstruktur recipes wird zurückgeben
     */
    public Recipe addRecipe(Recipe recipe) {
        return null;
    }

     /**
     * Mithilfe der Methode updateRecipe() kann ein bestehendes Rezept in die Datenstrukt recipes verändert werden
     * @return Die aktualisierte Datenstruktur recipes wird zurückgeben
     */
    public Recipe updateRecipe(Recipe recipe) {
        return null;
    }

     /**
     * Mithilfe der Methode removeRecipe() kann ein bestehendes Rezept in der Datenstrukt recipes gelöscht werden
     */
    public void removeRecipe(Recipe recipe) {
    }
    
   /**
     * Mithilfe der Methode findRecipeByName() kann ein bestimmtes Rezept in der Datenstrukt recipes gesucht werden
     * @param Name des Rezept
     * @return Das gefundene Rezept wird zurückgeben
     */
    public Recipe findRecipeByName(String name) {
        return null;
    }

     /**
     * Mithilfe der Methode findRecipesByRecipeType() können Rezept anhand ihres 
     * RecipeType (Fleisch, Fisch, Vegetarisch) in der Datenstrukt recipes gesucht werden
     * @param recipeType ((Fleisch, Fisch, Vegetarisch))
     * @return Collection die den Inhalt der Rezepte welche auf den recipeType passen wird zurückgegeben
     */
    public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
        return null;
    }

     /**
     * Mithilfe der Methode getRecipesSortedByRank() können die Rezepte anhand des Rang der Gerichte  
     * Hitliste sortiert werden
     * @return Collection welche die Rezepte der Gerichte nach ihrem Rang in der Hitliste aufsteigen sortiert enthält wird zurückgegeben
     */
    public Set<Recipe> getRecipesSortedByRank() {
        return null;
    }

     /**
     * Mithilfe der Methode findRecipeByRank() kann ein bestimmtes Rezept eines Gericht anhand des Rang in der Hitliste gefunden werden.
     * @param Rang des Gericht in der Hitliste
     * @return Recipe welches zu dem Gericht gehört welches den übergebenden Rang in der Hitliste besitzt
     */
    public Recipe findRecipeByRank(int rank) {
        return null;
    }

    /**
     * Mithilfe der Methode findHighestRankRecipe() kann das Rezept des beliebstesten Gericht der Hitliste gefunden werden
     * @return Recipe welches zum Gericht mit dem beliebtesten Gericht in der Hitliste gehört
     */
    public Recipe findHighestRankRecipe() {
        return null;
    }

       /**
     * Mithilfe der Methode findLowestRankRecipe() kann das Rezept des unbeliebstesten Gericht der Hitliste gefunden werden
     * @return Recipe welches zum Gericht mit dem unbeliebtesten Gericht in der Hitliste gehört
     */
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

