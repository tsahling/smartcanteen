package de.osjava.smartcanteen.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.osjava.smartcanteen.builder.result.Meal;
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
        // <<<<<<< .mine

        List<Meal> meals = canteen.getMenuPlan().getMeals();

        Map<Date, List<Meal>> mealsGroupedByDate = canteen.getMenuPlan().getMealsGroupedByDate();

        for (Entry<Date, List<Meal>> entry : mealsGroupedByDate.entrySet()) {

            entry.getKey();

            List<Meal> value = entry.getValue();

            for (Meal xyz : value) {

                xyz.getRecipe().getName();

                String transformDateToNiceString = transformDateToNiceString(xyz.getDate());

            }

        }

        if (true) {
            // throw new IOException(PropertyHelper.getProperty(""));
        }

        Set<Date> keySet = mealsGroupedByDate.keySet();

        for (Date xyz : keySet) {

            System.out.println(xyz.toString());

        }

        System.out.println(canteen.getMenuPlan().getMeals());
        // =======
        // System.out.println(canteen.getMenuPlan().getMealsGroupedByDate());
        // try {
        // ausgebenInDatei("Die Daten die in die Datei geschrieben werden sollen als Objekt");
        // } catch (IOException e) {
        // // TODO(Marcel) handle this exception properly
        // e.printStackTrace();
        // }
        // >>>>>>> .r154

    }

    private String transformDateToNiceString(Date date) {
        return "";
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
