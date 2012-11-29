package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.RecipeType;

public class Recipe {

    private String name;
    private RecipeType type;
    private Set<IngredientListItem> ingredientList;
    private int rank;

    public Recipe() {

    }

    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(this.type);
    }

    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(this.type);
    }

    public boolean isVegetableRecipe() {
        return RecipeType.VEGETABLE.equals(this.type);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ingredientList == null) ? 0 : ingredientList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rank;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Recipe other = (Recipe) obj;
		if (ingredientList == null) {
			if (other.ingredientList != null)
				return false;
		} else if (!ingredientList.equals(other.ingredientList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rank != other.rank)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recipe [name=" + name + ", type=" + type + ", ingredientList="
				+ ingredientList + ", rank=" + rank + "]";
	}

    
}
