/**
 * Die Klasse {@link IOutput} ist ein Interface und beschreibt damit eine standardisierte 
 * Schnittstelle, wie auf die Ausgabe-Klassen zugegriffen werden kann. Es wird festgelegt,
 * welche Methoden eine Ausgabeklasse mindestens haben muss. 
 * 
 * @author Marcel Baxmann
 */
public interface IOutput {

    /**
     * Diese Methode stellt die Moeglichkeit bereit ein Menue-Plan als noch nicht weitergehend 
     * definierte Ausgabe bereitzustellen. Die Ausgabe wird auf Basis der Daten eines Objekts 
     * {@link Canteen} gestaltet. Dieses Objekt muss der Methode beim Aufruf uebergeben werden.
     * 
     * @param canteen Katinen-Objekt wird uebergeben
     */
    void outputMenuPlan(Canteen canteen);


    /**
     * Diese Methode stellt die Moeglichkeit bereit eine Einkaufsliste als noch nicht weitergehend 
     * definierte Ausgabe bereitzustellen. Die Ausgabe wird auf Basis der Daten eines Objekts 
     * ShoppingList gestaltet. Dieses Objekt muss der Methode beim Aufruf uebergeben werden.
     * 
     * @param shoppingList Einkaufslisten-Objekt wird uebergeben
     */
    void outputShoppingList(ShoppingList shoppingList);

    /**
     * Diese Methode stellt die Moeglichkeit bereit eine eine Kostenübersicht als noch nicht 
     * weitergehend definierte Ausgabe bereitzustellen. Die Ausgabe wird auf Basis der Daten 
     * eines Objekts {@link ShoppingList} gestaltet. Dieses Objekt muss der Methode beim Aufruf 
     * uebergeben werden.
     * 
     * @param shoppingList Einkaufslisten-Objekt wird uebergeben
     */
    void outputTotalCosts(ShoppingList shoppingList);
}
