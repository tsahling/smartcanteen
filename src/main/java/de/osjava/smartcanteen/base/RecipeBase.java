package de.osjava.smartcanteen.base;

import java.util.Set;

import de.osjava.smartcanteen.data.Recipe;

public class RecipeBase {

    private Set<Recipe> recipes;

    /**
     * 
     * @param name
     * @return
     */
    public Recipe findRecipeByName(String name) {
        for (Recipe recipe : recipes) {
            if (name.equals(recipe.getName())) {
                return recipe;
            }
        }

        return null;
    }
}
