import java.util.Date;

/**
 * Die Klasse {@link Meal} stellt eine Position auf einem Menue-Plan dar. Sie enthaelt einen Verweis 
 * auf das jeweilige Rezept um das Essen zuzubereiten ({@link Recipe}) und ein Datum, an welchem das Essen 
 * auf dem Menue-Plan steht. 
 * 
 * @author Marcel Baxmann
 */
public class Meal {

    private Recipe recipe;
    private Date date;

    
    /**
     * Standardkonstruktor
     */
    public Meal() {

    }

    /**
     * Ausgabe des Rezepts, welches zum jeweiligen Meal-Objekt gehoert
     * @return Das Rezept
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Setzen eines Rezeptes
     * @param recipe  the recipe to set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Rückgabe des Datums, an welchem das Essen auf dem Menue-Plan steht. 
     * @return Das Datum
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setzen des Datums, an welchem das Essen auf dem Menue-Plan steht. 
     * @param date  the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
     * Erstellt die String-Representation des Objekts {@link Meal}.
     * 
     * @return Die String-Representation von {@link Meal}
     */
    @Override
    public String toString() {
        return null;
    }
}
