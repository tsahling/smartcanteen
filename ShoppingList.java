import java.util.List;

/**
 * 
 * @author Tim Sahling
 */
public class ShoppingList {

    private List<ShoppingListItem> shoppingListItems;

    public ShoppingList() {

    }

    public Amount calculateTotalPrice() {
        return null;
    }

    /**
     * @return the shoppingListItems
     */
    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    /**
     * Sets the shoppingListItems.
     * 
     * @param shoppingListItems the shoppingListItems to set
     */
    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
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
