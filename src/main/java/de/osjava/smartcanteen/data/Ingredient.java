package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.datatype.IngredientType;

/**
 * Die Klasse {@link Ingredient} stellt ein Datenobjekt eines Lebensmittels dar.
 * Sie enthält ein Atrribut für den Namen des Lebensmittels.
 * 
 * @author Francesco Luciano
 */
public class Ingredient {

    private String name;
    private IngredientType ingredientType;

    /**
     * Standardkonstruktor
     * 
     * @param name Name des Lebensmittels
     * @param name Typ (Fisch, Gemüse, Fleisch) des Lebensmittels
     */
    public Ingredient(String name, IngredientType ingredientType) {
        this.name = name;
        this.ingredientType = ingredientType;
    }

    /**
     * Methode um den Namen des Lebensmittels abzufragen
     * 
     * @return Name des Lebensmittels
     */
    public String getName() {
        return name;
    }

    /**
     * Methode um den Namen des Lebensmittels zu setzen
     * 
     * @param name Der zu setzende Name des Lebensmittels
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode um den Type des Lebensmittels abzufragen
     * 
     * @return Typ des Lebensmittels
     */
    public IngredientType getIngredientType() {
        return ingredientType;
    }

    /**
     * Methode um den Typ des Lebensmittels zu setzen
     * 
     * @param ingredientType Der zu setzende Typ des Lebensmittels
     */
    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
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
        result = prime * result + ((ingredientType == null) ? 0 : ingredientType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Ingredient other = (Ingredient) obj;
        if (ingredientType != other.ingredientType)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Ingredient}.
     * 
     * @return Die String-Representation von {@link Ingredient}
     */
    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", ingredientType=" + ingredientType + "]";
    }

}
