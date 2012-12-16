package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.application.Application;
import de.osjava.smartcanteen.application.CSVTokenizer;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.RecipeType;

/**
 * Die Klasse {@link Recipe} stellt ein Datenobjekt eines speziellen Rezepts aus
 * der eingelesenen Datei Rezepte dar. Die Rezeptedatei wird der Klasse
 * {@link Application} als Aufrufparameter übergeben und von der Klasse
 * {@link CSVTokenizer} verarbeitet und in ihre Datenfelder zerlegt. Sie enthält
 * den Namen des Rezepts, eine Liste von Zutaten und den Rang des dazugehörigen
 * Gerichts.
 * 
 * @author Francesco Luciano
 */
public class Recipe {

	private String name;
	private RecipeType type;
	private Set<IngredientListItem> ingredientList;
	private int rank;

	/**
	 * Standardkonstruktor
	 * 
	 * @param name
	 * @param type
	 * @param rank
	 */
	public Recipe(String name, RecipeType type, int rank) {
		this.name = name;
		this.type = type;
		this.rank = rank;
	}

	/**
	 * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ
	 * {@link RecipeType} Fleisch ist.
	 * 
	 * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fleisch
	 *         ist WAHR sonst FALSCH
	 */
	public boolean isMeatRecipe() {
		return RecipeType.MEAT.equals(type);
	}

	/**
	 * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ
	 * {@link RecipeType} Fisch ist.
	 * 
	 * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fisch
	 *         ist WAHR sonst FALSCH
	 */
	public boolean isFishRecipe() {
		return RecipeType.FISH.equals(type);
	}

	/**
	 * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ
	 * {@link RecipeType} Vegetarisch ist.
	 * 
	 * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType}
	 *         Vegetarisch ist WAHR sonst FALSCH
	 */
	public boolean isVegetableRecipe() {
		return RecipeType.VEGETABLE.equals(type);
	}

	/**
	 * Methode um den Namen des Rezepts {@link Recipe} abzufragen
	 * 
	 * @return Name des Rezepts {@link Recipe}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Methode um den Namen des Rezepts {@link Recipe} zu setzen
	 * 
	 * @param name
	 *            Name des Rezept
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Methode um den Typ {@link RecipeType} des Rezept {@link Recipe}
	 * abzufragen
	 * 
	 * @return Typ {@link RecipeType} des Rezepts {@link Recipe}
	 */
	public RecipeType getType() {
		return type;
	}

	/**
	 * Methode um den Typ {@link RecipeType} eines Rezepts {@link Recipe} zu
	 * setzen
	 * 
	 * @param type
	 *            Typ {@link RecipeType} eines Rezepts {@link Recipe}
	 */
	public void setType(RecipeType type) {
		this.type = type;
	}

	/**
	 * Methode um die Lebensmittelliste des Rezepts {@link Recipe} zu ermitteln
	 * 
	 * @return Menge von Lebensmitteln
	 */
	public Set<IngredientListItem> getIngredientList() {
		return ingredientList;
	}

	/**
	 * Methode um die Lebensmittelliste des Rezepts {@link Recipe} zu setzen
	 * 
	 * @param ingredientList
	 *            Lebensmittelliste die zu dem Rezept {@link Recipe} gehoert
	 */
	public void setIngredientList(Set<IngredientListItem> ingredientList) {
		this.ingredientList = ingredientList;
	}

	/**
	 * Methode um den Rang des Gerichts die zum dem Rezept {@link Recipe} gehört
	 * zu ermitteln
	 * 
	 * @return Rang des Gerichts, dass zu dem Rezept {@link Recipe} gehoert
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Metohde um den Rang des Gerichts zu dem das Rezept {@link Recipe} gehoert
	 * zu setzen
	 * 
	 * @param rank
	 *            Rang
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
	 * Methode aufgerufen wurde.
	 * 
	 * @return Der HashCode-Wert des Objekts als int-Representation
	 */
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

	/**
	 * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
	 * dem die Methode aufgerufen wurde.
	 * 
	 * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
	 */
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

	/**
	 * Erstellt die String-Representation des Objekts {@link Recipe}.
	 * 
	 * @return Die String-Representation von {@link Recipe}
	 */
	@Override
	public String toString() {
		return "Recipe [name=" + name + ", type=" + type + ", ingredientList="
				+ ingredientList + ", rank=" + rank + "]";
	}

}