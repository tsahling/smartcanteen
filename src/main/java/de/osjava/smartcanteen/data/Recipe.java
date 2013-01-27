package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.application.Application;
import de.osjava.smartcanteen.application.InputFileHandler;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.IngredientType;

/**
 * Die Klasse {@link Recipe} stellt ein Datenobjekt eines speziellen Rezepts aus
 * der eingelesenen Datei Rezepte dar. Die Rezeptedatei wird der Klasse {@link Application} als Aufrufparameter
 * übergeben und von der Klasse {@link InputFileHandler} verarbeitet und in ihre Datenfelder zerlegt. Sie enthält
 * den Namen des Rezepts, eine Liste von Zutaten und den Rang des dazugehörigen Gerichts.
 * 
 * @author Francesco Luciano
 */
public class Recipe {

    private String name;
    private Set<IngredientListItem> ingredientList;
    private int rank;

    /**
     * Konstruktor mit Name des Rezepts.
     * 
     * @param name Der Name des Rezepts
     */
    public Recipe(String name) {
        this.name = name;
    }

    /**
     * Konstruktor mit Name und Rang des Gerichts
     * 
     * @param name Name des Gerichts
     * @param rank Rang des Gerichts
     */
    public Recipe(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * Ermittelt den Typ des {@link Recipe} auf Basis aller {@link Ingredient} des {@link Recipe}.
     * 
     * @return Den Typ des {@link Recipe}
     */
    public IngredientType getIngredientType() {
        if (isMeatRecipe()) {
            return IngredientType.MEAT;
        }
        else if (isFishRecipe()) {
            return IngredientType.FISH;
        }
        else {
            return IngredientType.VEGETABLE;
        }
    }

    /**
     * Methode um abzufragen ob das {@link Recipe} Fleisch enthält.
     * 
     * @return Wenn das Rezept {@link Recipe} Fleisch enthält WAHR sonst FALSCH
     */
    public boolean isMeatRecipe() {
        return checkTypeOfIngredients(IngredientType.MEAT);
    }

    /**
     * Methode um abzufragen ob das {@link Recipe} Fisch enthält.
     * 
     * @return Wenn das Rezept {@link Recipe} Fisch enthält WAHR sonst FALSCH
     */
    public boolean isFishRecipe() {
        return checkTypeOfIngredients(IngredientType.FISH);
    }

    /**
     * Methode um abzufragen ob das {@link Recipe} vegetarisch ist.
     * 
     * @return Wenn alle Zutaten des {@link Recipe} vegetarisch sind WAHR sonst FALSCH
     */
    public boolean isVegetableRecipe() {
        return checkTypeOfIngredients(IngredientType.VEGETABLE);
    }

    /**
     * Methode überprüft welcher Typ von Gericht (Fisch, Fleisch, Vegetarisch) vorliegt. Diese Überprüfung findet anhand
     * der Zutatenliste statt. Bei {@link IngredientType#MEAT} oder {@link IngredientType#FISH} reicht es, wenn eine
     * Zutat der Liste von diesem Typ ist, um das Gericht als Fisch oder Fleisch zu deklarieren. Bei
     * {@link IngredientType#VEGETABLE} müssen alle Zutaten von der Liste vegetarisch sein, um das Gericht als
     * vegetarisch zu deklarieren.
     * 
     * @param ingredientType Der zu überprüfende {@link IngredientType}
     * @return
     */
    private boolean checkTypeOfIngredients(IngredientType ingredientType) {
        if (ingredientList == null || (ingredientList != null && ingredientList.isEmpty())) {
            return false;
        }

        switch (ingredientType) {
        case FISH:
        case MEAT:

            for (IngredientListItem ingredient : ingredientList) {

                if (ingredientType.equals(ingredient.getIngredient().getIngredientType())) {
                    return true;
                }
            }

            break;
        case VEGETABLE:

            for (IngredientListItem ingredient : ingredientList) {

                if (!ingredientType.equals(ingredient.getIngredient().getIngredientType())) {
                    return false;
                }
            }

            return true;
        }

        return false;
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
        }
        else if (!ingredientList.equals(other.ingredientList))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (rank != other.rank)
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
        return "Recipe [name=" + name + ", ingredientList=" + ingredientList + ", rank=" + rank + "]";
    }

}