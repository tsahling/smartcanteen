package de.osjava.smartcanteen.output;

import java.io.IOException;

import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

/**
 * Die Klasse {@link IOutput} ist ein Interface und beschreibt damit eine
 * standardisierte Schnittstelle, wie auf die Ausgabe-Klassen zugegriffen werden
 * kann. Es wird festgelegt, welche Methoden eine Ausgabeklasse mindestens haben
 * muss.
 * 
 * @author Marcel Baxmann
 */
public interface IOutput {

    /**
     * Diese Methode stellt die Möglichkeit bereit einn Speiseplan als noch
     * nicht weitergehend definierte Ausgabe bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link Canteen} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param canteen
     *            Kantinen-Objekt wird übergeben
     * @throws IOException
     * @author Marcel Baxmann
     */
    void outputMenuPlan(Canteen canteen) throws IOException;

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Einkaufsliste als noch
     * nicht weitergehend definierte Ausgabe bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link ShoppingList} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param shoppingList
     *            Einkaufslisten-Objekt wird übergeben
     * @author Marcel Baxmann
     */
    void outputShoppingList(ShoppingList shoppingList);

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Kostenübersicht als noch
     * nicht weitergehend definierte Ausgabe bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link ShoppingList} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param shoppingList
     *            Einkaufslisten-Objekt wird übergeben
     * @author Marcel Baxmann
     */
    void outputTotalCosts(ShoppingList shoppingList);
}
