import java.util.Set;

/**
 * Die Klasse {@link Recipe} stellt ein Datenobjekt eines speziellen Rezepts aus der eingelesenen Datei Rezepte dar. Die Rezepte Datei wird 
 * der Klasse {@link Application} als Aufrufparameter uebergeben und von der Klasse {@link CSVTokenizer} verarbeitet und in ihre Datenfelder zerlegt.
 * Sie enthaelt den Namen des Rezepts, eine Liste von Zutaten und den Rang des dazugehoerigen Gerichts.
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
     */
    public Recipe() {

    }

    /**
     * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ {@link RecipeType} Fleisch ist.
     * 
     * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fleisch ist TRUE sonst FALSE
     */
    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(type);
    }

   /**
    * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ {@link RecipeType} Fisch ist.
    * 
    * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fisch ist TRUE sonst FALSE
    */  
    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(type);
    }

   /**
    * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ {@link RecipeType} Vegetarisch ist.
    * 
    * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Vegetarisch ist TRUE sonst FALSE
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
     * @param name Name des Rezept
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode um den Typ {@link RecipeType} des Rezept {@link Recipe} abzufragen
     * 
     * @return Typ {@link RecipeType} des Rezepts {@link Recipe}
     */
    public RecipeType getType() {
        return type;
    }

    /**
     * Methode um den Typ {@link RecipeType} eines Rezepts {@link Recipe} zu setzen
     * 
     * @param type Typ {@link RecipeType} eines Rezepts {@link Recipe} 
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
     * @param ingredientList Lebensmittelliste die zu dem Rezept {@link Recipe} gehoert
     */
    public void setIngredientList(Set<IngredientListItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * Methode um den Rang des Gerichts die zum dem Rezept {@link Recipe} gehoert zu ermitteln
     * 
     * @return Rang des Gerichts, dass zu dem Rezept {@link Recipe} gehoert
     */
    public int getRank() {
        return rank;
    }

    /**
     * Metohde um den Rang des Gerichts zu dem das Rezept {@link Recipe} gehoert zu setzen
     * 
     * @param rank Rang
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