package de.osjava.smartcanteen.base;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.RecipeType;

/**
 * Die Klasse {@link RecipeBase} ist eine Datenträgerklasse die in einer Java
 * Collection vom Typ Set die Informationen aus der einzulesenden Datei Rezepte
 * als Datenobjekt {@link Recipe} speichert.
 * 
 * @author Francesco Luciano
 */
public class RecipeBase {

	private Set<Recipe> recipes;

	/**
	 * Standardkonstruktor
	 */
	public RecipeBase() {

	}

	/**
	 * Methode um ein Datenobjekt {@link Recipe} in das Set hinzuzufügen.
	 * 
	 * @param recipe
	 *            Das einzufügende Datenobjekt {@link Recipe}
	 * @return Das aktualisierte Rezept {@link Recipe}
	 */
	public Recipe addRecipe(Recipe recipe) {
		return null;
	}

	/**
	 * Methode um ein bestehendes Datenobjekt {@link Recipe} im Set zu
	 * verändern.
	 * 
	 * @return Das aktualisierte Rezept {@link Recipe}
	 */
	public Recipe updateRecipe(Recipe recipe) {
		return null;
	}

	/**
	 * Methode um ein bestehendes Datenobjekt {@link Recipe} aus dem Set zu
	 * löschen.
	 */
	public void removeRecipe(Recipe recipe) {
	}

	/**
	 * Methode um ein bestimmtes Datenobjekt {@link Recipe} in dem Set zu
	 * suchen.
	 * 
	 * @param name
	 *            Name des Datenobjekts {@link Recipe}
	 * @return Das gefundene Datenobjekt {@link Recipe}
	 */
	public Recipe findRecipeByName(String name) {
		return null;
	}

	/**
	 * Methode um im Set eine Menge von Datenobjekten {@link Recipe} anhand
	 * ihres Typs {@link RecipeType} zu suchen.
	 * 
	 * @param recipeType
	 *            Typ {@link RecipeType} des Rezepts
	 * @return Set {@link Recipe} mit dem Inhalt aller Datenobjekte
	 *         {@link Recipe} die auf den angebenen Typ {@link RecipeTyp}
	 *         matchen
	 */
	public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
		return null;
	}

	/**
	 * Methode um die Datenobjekte {@link Recipe} im Set anhand des Rangs der
	 * Gerichte zu sortieren
	 * 
	 * @return Set {@link Recipe} in dem die Datenobjekte aufsteigend sortiert
	 *         sind
	 */
	public Set<Recipe> getRecipesSortedByRank() {
		Set<Recipe> result = new TreeSet<Recipe>(new Comparator<Recipe>() {

			@Override
			public int compare(Recipe arg0, Recipe arg1) {
				return Integer.valueOf(arg0.getRank()).compareTo(
						Integer.valueOf(arg1.getRank()));
			}
		});

		if (recipes != null) {
			result.addAll(recipes);
		}

		return result;
	}

	/**
	 * Methode um nach einem Datenobjekt {@link Recipe} eines Gerichts anhand
	 * des Rangs zu suchen.
	 * 
	 * @param rank
	 *            Rang des Gerichts
	 * @return {@link Recipe} welches zu dem Gericht gehört welches den
	 *         übergebenden Rang besitzt
	 */
	public Recipe findRecipeByRank(int rank) {
		return null;
	}

	/**
	 * Methode um das Datenobjekt {@link Recipe} mit dem höchsten Rang zu
	 * ermitteln
	 * 
	 * @return Datenobjekt {@link Recipe} des beliebstesten Gerichts
	 */
	public Recipe findHighestRankRecipe() {
		return null;
	}

	/**
	 * Methode um das Datenobjekt {@link Recipe} mit dem niedrigsten Rang zu
	 * ermitteln
	 * 
	 * @return Datenobjekt {@link Recipe} des unbeliebstesten Gerichts
	 */
	public Recipe findLowestRankRecipe() {
		return null;
	}

	/**
	 * Methode um alle Datenobjekte aus dem Set zu ermitteln
	 * 
	 * @return Set {@link Recipe}
	 */
	public Set<Recipe> getRecipes() {
		return recipes;
	}

	/**
	 * Setzt die Rezepte
	 * 
	 * @param recipes
	 *            Die zu setzenden Rezepte
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
		return "RecipeBase [recipes=" + recipes + "]";
	}

}
