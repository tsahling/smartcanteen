package de.osjava.smartcanteen.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

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
    public String dateiname = "MenuePlan";
    // Dateityp für die Ausagbedatei
    public String dateityp = ".csv";
    // auslesen des betriebssystemspezifisch Zeichens für einen Zeilenumbruch
    public String lineSeparator = System.getProperty("line.separator");

    /**
     * Standardkonstruktor
     * 
     * @param canteens
     */
    public FileOutput() {

    }

    @Override
    public void outputMenuPlan(Canteen canteen) throws IOException {
        String canteenName = canteen.getLocation().getName();

        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        for (Meal sortedMeal : mealsSortedByDate) {
            String date = shortendDate(sortedMeal.getDate());

            String meal = sortedMeal.getRecipe().getName();

            // System.out.println(date + ": " + meal);
            ausgebenInDatei(date + ";" + meal + "; " + lineSeparator, canteenName, true);
            // ausgebenInDatei(System.getProperty(), true);
            // System.out.println(System.getProperty("line.seperator"));
        }

    }

    /**
     * Klasse um ein Datum zu kürzen in das Format dd.mm.yyyy
     * 
     * @param date
     * @return String eines Datums im Format dd.mm.yyyy
     */
    private String shortendDate(Date date) {
        String stringDate;
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
        stringDate = formatter.format(date);
        return stringDate;
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
    public void ausgebenInDatei(String ausgabeDaten, String namensZusatz, boolean anhaengen) throws IOException {
        File file = new File(speicherort + dateiname + " - " + namensZusatz + dateityp);
        FileWriter writer = new FileWriter(file, anhaengen);

        // übergebener String wird in Datei geschrieben
        writer.write(ausgabeDaten);

        // schreiben der Daten in Stream in die Datei
        writer.flush();

        // schließen des Stream
        writer.close();
    }

}
