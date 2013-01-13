package de.osjava.smartcanteen.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;

/**
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface {@link IOutput} implementiert und stellt daher die
 * Methoden die im Interface
 * definiert sind bereit. Die Ausgabe erfolgt in eine Datei im Dateisystem.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {

    // Ablageort für die Ausagbedatei
    public String speicherort = "D:/";
    // Dateiname für die Ausagbedatei
    public String dateiname = "MenuePlan.txt";

    /**
     * Standardkonstruktor
     * 
     * @param canteens
     */
    public FileOutput(Canteen[] canteens) {
        Canteen canteen;

        for (int x = 0; x != canteens.length; x++) {
            canteen = canteens[x];
            System.out.println("Übergabe Menu Plan:" + x);
            outputMenuPlan(canteen);
        }

    }

    @Override
    public void outputMenuPlan(Canteen canteen) {
        System.out.println(canteen.getMenuPlan().getMealsGroupedByDate());
        try {
            ausgebenInDatei("Die Daten die in die Datei geschrieben werden sollen als Objekt");
        } catch (IOException e) {
            // TODO(Marcel) handle this exception properly
            e.printStackTrace();
        }

    }

    @Override
    public void outputShoppingList(ShoppingList shoppingList) {
    }

    @Override
    public void outputTotalCosts(ShoppingList shoppingList) {
    }

    /**
     * Schreiben der übergebenen Daten in Datei
     * 
     * @param String wird übergeben
     * @throws IOException
     */
    public void ausgebenInDatei(String ausgabeDaten) throws IOException {
        File file = new File(speicherort + dateiname);
        FileWriter writer = new FileWriter(file, true);
        writer.write(ausgabeDaten);
        writer.flush();
        writer.close();
    }

}
