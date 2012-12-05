package de.osjava.smartcanteen.base;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.RecipeType;

/**
 * 
 * @author Francesco Luciano
 */
public class RecipeBase {

	private Set<Recipe> recipes;

	public RecipeBase() {

	}

	public Recipe addRecipe() {
		return null;
	}

	public Recipe updateRecipe() {
		return null;
	}

	public void removeRecipe() {
	}

	public Recipe findRecipeByName(String name) {
		return null;
	}

	public Set<Recipe> findRecipesByRecipeType(RecipeType recipeType) {
		return null;
	}

	public Set<Recipe> getRecipesSortedByRank() {
		Set<Recipe> result = new TreeSet<Recipe>(new Comparator<Recipe>() {

			@Override
			public int compare(Recipe arg0, Recipe arg1) {
				return Integer.valueOf(arg0.getRank()).compareTo(
						Integer.valueOf(arg1.getRank()));
			}
		});
		result.addAll(recipes);
		return result;
	}

	public Recipe findRecipeByRank(int rank) {
		return null;
	}

	public Recipe findHighestRankRecipe() {
		return null;
	}

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
	 * @param recipes
	 *            the recipes to set
	 */
	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipes == null) ? 0 : recipes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeBase other = (RecipeBase) obj;
		if (recipes == null) {
			if (other.recipes != null)
				return false;
		} else if (!recipes.equals(other.recipes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecipeBase [recipes=" + recipes + "]";
	}

}
