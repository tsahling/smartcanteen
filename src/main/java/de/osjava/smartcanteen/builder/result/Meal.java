package de.osjava.smartcanteen.builder.result;

import java.util.Date;

import de.osjava.smartcanteen.data.Recipe;

// Hallo,
//
// wir halten nicht ale drei Gerichte für jeden Mitarbeiter vor, das wäre eine immense Verschwendung. Durch die Analyse
// konnten wir herausfinden, dass das Gericht, das (von den drei angebotenen) laut Ranking am beliebtesten ist, doppelt
// so viel gegessen wird wie die anderen beiden. Wir halten 50% mehr Essen als Mitarbeiter bereit. In Mülheim würde das
// bedeuten, dass wir 450 Portionen bereit halten, davon 225 vom beliebtesten der drei Gerichte und je 113 von den
// anderen beiden, in Essen gibt entsprechend insgesamt 750 Portionen.
//
// Fischgerichte zählen bei uns in der Tat nicht zu den vegetarischen Gerichten.
//
// Kulinarische Grüße
//
// Jacques Gourmand

/**
 * 
 * @author Marcel Baxmann
 */
public class Meal {

    private Recipe recipe;
    private Date date;

    public Meal() {

    }

    /**
     * @return the recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * @param recipe
     *            the recipe to set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
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

    @Override
    public String toString() {
        return "Meal [recipe=" + recipe + ", date=" + date + "]";
    }

}
