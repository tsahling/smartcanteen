/**
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface IOutput implementiert und 
 * stellt daher die Methoden die im Interface definiert sind bereit.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {

    /**
     * Standardkonstruktor
     */
    public FileOutput() {
    }
    
    /**
     * Diese Methode stellt die Möglichkeit bereit ein Menue-Plan 
     * als CSV-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts Canteen gestaltet. Dieses Objekt muss der Methode
     * beim Aufruf uebergeben werden.
     * 
     * @param Canteen Katinen-Objekt wird uebergeben
     */
    @Override
    public void outputMenuPlan(Canteen canteen) {
    }

     /**
     * Diese Methode stellt die Möglichkeit bereit eine Einkaufsliste 
     * als CSV-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts ShoppingList gestaltet. Dieses Objekt muss der Methode
     * beim Aufruf uebergeben werden.
     * 
     * @param Canteen Einkaufslisten-Objekt wird uebergeben
     */
    @Override
    public void outputShoppingList(ShoppingList shoppingList) {
    }

     /**
     * Diese Methode stellt die Möglichkeit bereit eine eine Kostenübersicht 
     * als  CSV-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts ShoppingList gestaltet. Dieses Objekt muss der Methode
     * beim Aufruf uebergeben werden.
     * 
     * @param Canteen Einkaufslisten-Objekt wird uebergeben
     */
    @Override
    public void outputTotalCosts(ShoppingList shoppingList) {
    }
}
