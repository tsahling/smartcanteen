import java.util.Date;

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
          return 0;
    }

    @Override
    public boolean equals(Object obj) {

        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
