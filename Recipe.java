import java.util.Set;

/**
 * Die Klasse {@link Recipe} stellt ein Datenobjekt eines speziellen Rezept {@link Recipe} aus der eingelesenen Datei Rezepte dar. Die Rezepte Datei wird 
 * der Klasse {@Application} als Klassenparameter übergeben und von der Klasse {@link CSVTokenizer} verarbeitet und in ihre Datenfelder zerlegt.
 * Sie enthaelt den Namen {@link name} des Rezept, eine Liste {@link ingredientList} 
 * von Zutaten {@link IngredientListItem} und den Rang {@link rank} des dazugehörigen Gericht {@link Meal} in der Hitliste {@link HitListbase}.
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
    * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fleisch ist TRUE sonst FALSE
    */
    public boolean isMeatRecipe() {
        return RecipeType.MEAT.equals(type);
    }

  /**
    * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ {@link RecipeType} Fisch ist.
    * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Fisch ist TRUE sonst FALSE
    */
   
    public boolean isFishRecipe() {
        return RecipeType.FISH.equals(type);
    }

   /**
    * Methode um abzufragen ob das Rezept {@link Recipe} vom Typ {@link RecipeType} Vegetarisch ist.
    * @return Wenn das Rezept {@link Recipe} vom Typ {@link RecipeType} Vegetarisch ist TRUE sonst FALSE
    */
    public boolean isVegetableRecipe() {
        return RecipeType.VEGETABLE.equals(type);
    }

    /**
     * Methode um den Namen {@link name} des des Rezept {@link Recipe} abzufragen 
     * @return Name {@link name} des Rezept {@link Recipe}
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen {@link name} des Rezept {@link Recipe} zu setzen
     * @param name Name des Rezept
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * Methode um den Typ {@link RecipeType} des Rezept {@link Recipe} abzufragen
     * @return Typ {@link RecipeType} des Rezept {@link Recipe}
     */
    public RecipeType getType() {
        return type;
    }

    /**
     * Methode um den Typ {@link RecipeType} eines Rezept {@link Recipe} zu setzen
     * @param type Typ {@link RecipeType} eines Rezept {@link Recipe} 
     */
    public void setType(RecipeType type) {
        this.type = type;
    }

    /**
     * Methode um die Lebensmittelliste {@link ingredientList} des Rezept {@link Recipe} zu ermitteln
     * @return Set {@link ingredientList}
     */
    public Set<IngredientListItem> getIngredientList() {
        return ingredientList;
    }

    /**
     * Methode um die Lebensmittelliste {@link ingredientList} des Rezept {@link Recipe} zu setzen
     * @param ingredientList Lebensmittelliste {@link ingredientList} die zu dem Rezept {@link Recipe} gehört
     */
    public void setIngredientList(Set<IngredientListItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * Methode um den Rang des Gericht {@link Meal} aus der Hitliste {@link HitListBase} die zum dem Rezept {@link Recipe} gehürt zu ermitteln
     * @return Rang des Gericht {@link Meal} aus der Hitliste {@link HitListBase} das zu dem Rezept {@link Recipe} gehört
     */
    public int getRank() {
        return rank;
    }

    /**
     * Metohde um den Rang des Gericht {@link Meal} aus der Hitsliste {@link HitListBase} zu dem das Rezept {@link Recipe} gehört zu setzen
     * @param rank Rang aus der Hitliste {@link HitListBase}
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