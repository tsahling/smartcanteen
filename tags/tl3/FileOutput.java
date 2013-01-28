/**
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface IOutput implementiert und 
 * stellt daher die Methoden die im Interface definiert sind bereit. Die Ausgabe erfolgt in eine
 * Datei im Filesystem.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {

    /**
     * Standardkonstruktor
     */
    public FileOutput() {
    }
    
    @Override
    public void outputMenuPlan(Canteen canteen) {
    }

    @Override
    public void outputShoppingList(ShoppingList shoppingList) {
    }

    @Override
    public void outputTotalCosts(ShoppingList shoppingList) {
    }
}
