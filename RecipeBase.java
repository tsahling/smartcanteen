import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Die Klasse {@link Recipebase} ist eine Datenträgerklasse die in einer Java Collection vom Typ Set {@link recipes} die Informationen aus der einzulesenden Datei Rezepte 
 * als Datenobjekt {@link Recipe} speichert.
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
     * Methode um ein Datenobjekt (@link Recipe) in das Set {@link recipes} hinzuzufügen.
     * @param recipe Das einzufügende Datenobjekt (@link Recipe)
     * @return Das aktualisierte Set {@link recipes}
     */
    public Recipe addRecipe(Recipe recipe) {
        return null;
    }

     /**
     * Methode um ein bestehendes Datenobjekt (@link Recipe) im Set {@link recipes} zu verändern.
     * @return Das aktualisierte Set {@link recipes}
     */
    public Recipe updateRecipe(Recipe recipe) {
        return null;
    }

     /**
     * Methode um ein bestehendes Datenobjekt (@link Recipe) aus dem Set {@link recipes} zu löschen.
     */
    public void removeRecipe(Recipe recipe) {
    }
    
   /**
      * Methode um ein bestimmtes Datenobjekt (@link Recipe) in dem Set {@link recipes} zu suchen.
     * @param name Name des Datenobjekt (@link Recipe)
     * @return Das gefundene Datenobjekt (@link Recipe)
     */
    public Recipe findRecipeByName(String name) {
        return null;
    }

     /**
     * Methode um im Set {@link recipes} ein Datenobjekt (@link Recipe) anhand ihres 
     * Typ {@link RecipeType} zu suchen.
     * @param recipeType Typ {@link RecipeType}  des Gericht
     * @return Set {@link Recipe} mit dem Inhalt aller Datenobjekte (@link Recipe) die auf den angebenen Typ {@link RecipeTyp} matchen
     */
    public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
        return null;
    }

     /**
     * Methode um die Datenobjekte (@link Recipe) im Set {@link recipes} anhand des Rang {@link rank} der Gerichte {@link Meal}
     * in der Hitliste {@link HitListBase} zu sortieren
     * @return Set {@link recipes} in dem die Datenobjekte (@link Recipe) aufsteigend sortiert sind
     */
    public Set<Recipe> getRecipesSortedByRank() {
        return null;
    }

     /**
     * Methode um nach einem Datenobjekt (@link Recipe) eines Gericht {@link Meal} anhand des Rang {@link rank} in der Hitliste {@link HitListBase} zu suchen.
     * @param rank Rang {@link rank} des Gericht {@link Meal} in der Hitliste {@link HitListBase}
     * @return Recipe welches zu dem Gericht gehört welches den übergebenden Rang in der Hitliste besitzt
     */
    public Recipe findRecipeByRank(int rank) {
        return null;
    }

    /**
     *  Methode um das Datenobjekt {@link Recipe} der Hitliste {@link HitListBasse} mit dem höchsten Rang {@link rank} zu ermitteln
     * @return Datenobjekt {@link Recipe} des beliebstesten Gericht der Hitliste
     */
    public Recipe findHighestRankRecipe() {
        return null;
    }

    /**
     *  Methode um das Datenobjekt {@link Recipe} der Hitliste {@link HitListBasse} mit dem niedrigsten Rang {@link rank} zu ermitteln
     * @return Datenobjekt {@link Recipe} des unbeliebstesten Gericht der Hitliste
     */
    public Recipe findLowestRankRecipe() {
        return null;
    }

    /**
     * Methode um alle Datenobjekte {@link recipes} aus dem Set {@link recipes} zu ermitteln
     * @return Set {@link recipes}
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

