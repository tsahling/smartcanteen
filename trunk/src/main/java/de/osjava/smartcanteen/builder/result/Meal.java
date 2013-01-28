package de.osjava.smartcanteen.builder.result;

import java.util.Date;

import de.osjava.smartcanteen.data.Recipe;

/**
 * Die Klasse {@link Meal} stellt eine Position auf einem Speiseplan dar. Sie
 * enthält einen Verweis auf das jeweilige Rezept um das Essen zuzubereiten ( {@link Recipe}) und ein Datum, an welchem
 * das Essen auf dem Speiseplan steht.
 * 
 * @author Marcel Baxmann
 */
public class Meal {

    private Recipe recipe;
    private Date date;

    /**
     * Standardkonstruktor
     * 
     * @param recipe Rezept
     * @param date Datum
     */
    public Meal(Recipe recipe, Date date) {
        this.recipe = recipe;
        this.date = date;
    }

    /**
     * Ausgabe des Rezepts, welches zum jeweiligen Gericht gehört
     * 
     * @return Das Rezept
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Rückgabe des Datums, an welchem das Essen auf dem Speiseplan steht.
     * 
     * @return Das Datum
     */
    public Date getDate() {
        return date;
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
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
        return result;
    }

    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist,
     * von dem die Methode aufgerufen wurde.
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
        Meal other = (Meal) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (recipe == null) {
            if (other.recipe != null)
                return false;
        }
        else if (!recipe.equals(other.recipe))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Meal}.
     * 
     * @return Die String-Representation von {@link Meal}
     */
    @Override
    public String toString() {
        return "Meal [recipe=" + recipe + ", date=" + date + "]";
    }

}
