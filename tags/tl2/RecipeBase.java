import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Die Klasse {@link Recipebase} ist eine Datentraegerklasse die in einer Java Collection vom Typ Set die Informationen aus der einzulesenden Datei Rezepte 
 * als Datenobjekt {@link Recipe} speichert.
 * 
 * @author Francesco Luciano
 */
public class RecipeBase {
    
    private Set<Recipe> recipes;
    
    /**
     * Konstruktor der Klasse Recipebase
     */
    public RecipeBase() {

    }
    
    /**
     * Methode um ein Datenobjekt (@link Recipe) in das Set hinzuzufuegen.
     * 
     * @param recipe Das einzufuegende Datenobjekt {@link Recipe}
     * @return Das aktualisierte Rezept {@link Recipe}
     */
    public Recipe addRecipe(Recipe recipe) {
        return null;
    }

    /**
     * Methode um ein bestehendes Datenobjekt {@link Recipe} im Set zu veraendern.
     * 
     * @return Das aktualisierte Rezept {@link Recipe}
     */
    public Recipe updateRecipe(Recipe recipe) {
        return null;
    }

    /**
     * Methode um ein bestehendes Datenobjekt {@link Recipe} aus dem Set zu loeschen.
     */
    public void removeRecipe(Recipe recipe) {
    }
    
    /**
     * Methode um ein bestimmtes Datenobjekt {@link Recipe} in dem Set zu suchen.
     * 
     * @param name Name des Datenobjekts {@link Recipe}
     * @return Das gefundene Datenobjekt {@link Recipe}
     */
    public Recipe findRecipeByName(String name) {
        return null;
    }

    /**
     * Methode um im Set eine Menge von Datenobjekten {@link Recipe} anhand ihres Typs {@link RecipeType} zu suchen.
     * 
     * @param recipeType Typ {@link RecipeType} des Rezepts
     * @return Set {@link Recipe} mit dem Inhalt aller Datenobjekte (@link Recipe) die auf den angebenen Typ {@link RecipeTyp} matchen
     */
    public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
        return null;
    }

    /**
     * Methode um die Datenobjekte {@link Recipe} im Set anhand des Rangs der Gerichte {@link Recipe} zu sortieren
     * @return Set {@link Recipe} in dem die Datenobjekte aufsteigend sortiert sind
     */
    public Set<Recipe> getRecipesSortedByRank() {
        return null;
    }

    /**
     * Methode um nach einem Datenobjekt {@link Recipe} eines Gerichts anhand des Rangs zu suchen.
     * 
     * @param rank Rang des Gerichts
     * @return {@link Recipe} welches zu dem Gericht gehoert welches den uebergebenden Rang besitzt
     */
    public Recipe findRecipeByRank(int rank) {
        return null;
    }

    /**
     * Methode um das Datenobjekt {@link Recipe} mit dem hoechsten Rang zu ermitteln
     * @return Datenobjekt {@link Recipe} des beliebstesten Gerichts
     */
    public Recipe findHighestRankRecipe() {
        return null;
    }

    /**
     * Methode um das Datenobjekt {@link Recipe} mit dem niedrigsten Rang zu ermitteln
     * 
     * @return Datenobjekt {@link Recipe} des unbeliebstesten Gerichts
     */
    public Recipe findLowestRankRecipe() {
        return null;
    }

    /**
     * Methode um alle Datenobjekte {@link recipes} aus dem Set {@link recipes} zu ermitteln
     * 
     * @return Set {@link Recipe}
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

