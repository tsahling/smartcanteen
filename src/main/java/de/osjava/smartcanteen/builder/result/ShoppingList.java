package de.osjava.smartcanteen.builder.result;

import java.util.List;

import de.osjava.smartcanteen.datatype.Amount;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((shoppingListItems == null) ? 0 : shoppingListItems.hashCode());
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
        ShoppingList other = (ShoppingList) obj;
        if (shoppingListItems == null) {
            if (other.shoppingListItems != null)
                return false;
        }
        else if (!shoppingListItems.equals(other.shoppingListItems))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShoppingList [shoppingListItems=" + shoppingListItems + "]";
    }
}
