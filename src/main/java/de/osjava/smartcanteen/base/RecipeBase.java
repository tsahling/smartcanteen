package de.osjava.smartcanteen.base;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.IngredientType;

/**
 * Die Klasse {@link RecipeBase} ist eine Datenträgerklasse die in einer Java Collection vom Typ Set die Informationen
 * aus der einzulesenden Datei Rezepte als Datenobjekt {@link Recipe} speichert.
 * 
 * @author Francesco Luciano
 */
public class RecipeBase {

    private Set<Recipe> recipes;

    /**
     * Methode um ein Datenobjekt {@link Recipe} in das Set hinzuzufügen.
     * 
     * @param recipe Das einzufügende Datenobjekt {@link Recipe}
     * @return Das aktualisierte Rezept {@link Recipe}
     */
    public Recipe addRecipe(Recipe recipe) {
        if (this.recipes == null) {
            this.recipes = new HashSet<Recipe>();
        }
        this.recipes.add(recipe);
        return recipe;
    }

    /**
     * Methode um die Datenobjekte {@link Recipe} im Set anhand des Rangs der Gerichte zu sortieren.
     * 
     * @return Set {@link Recipe} in dem die Datenobjekte aufsteigend sortiert sind
     */
    public Set<Recipe> getRecipesSortedByRank(Set<Recipe> recipes) {
        Set<Recipe> result = new TreeSet<Recipe>(new Comparator<Recipe>() {

            @Override
            public int compare(Recipe r1, Recipe r2) {
                return Integer.valueOf(r1.getRank()).compareTo(Integer.valueOf(r2.getRank()));
            }
        });

        if (recipes != null && !recipes.isEmpty()) {
            result.addAll(recipes);
        }
        else {
            result.addAll(this.recipes);
        }

        return result;
    }

    /**
     * 
     * Ermittelt die Rezepte für einen bestimmten {@link IngredientType} und sortiert diese nach Rang.
     * 
     * @param ingredientType Der {@link IngredientType}, nach dem die Rezepte gesucht werden
     * @return Eine nach Rang sortierte Menge von Rezepten für den {@link IngredientType}
     */
    public Set<Recipe> getRecipesForIngredientTypeSortedByRank(IngredientType ingredientType) {
        return getRecipesSortedByRank(findRecipesByIngredientType(ingredientType));
    }

    /**
     * Methode um im Set eine Menge von Datenobjekten {@link Recipe} anhand ihres Typs {@link IngredientType} zu suchen.
     * 
     * @param ingredientType Typ {@link IngredientType} des Rezepts
     * @return Set {@link Recipe} mit dem Inhalt aller Datenobjekte {@link Recipe} die auf den angebenen Typ
     *         {@link IngredientType} matchen
     */
    private Set<Recipe> findRecipesByIngredientType(IngredientType ingredientType) {
        Set<Recipe> result = new HashSet<Recipe>();

        if (this.recipes != null && !this.recipes.isEmpty()) {

            for (Recipe recipe : this.recipes) {

                if (ingredientType.equals(recipe.getIngredientType())) {
                    result.add(recipe);
                }
            }
        }
        return result;
    }

    /**
     * Methode um alle Datenobjekte aus dem Set zu ermitteln.
     * 
     * @return Set {@link Recipe}
     */
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link RecipeBase}.
     * 
     * @return Die String-Representation von {@link RecipeBase}
     */
    @Override
    public String toString() {
        return "RecipeBase [recipes=" + recipes + "]";
    }
}
