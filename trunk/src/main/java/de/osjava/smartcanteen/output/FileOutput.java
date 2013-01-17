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
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface {@link IOutput} implementiert und stellt daher die
 * Methoden die im Interface
 * definiert sind bereit. Die Ausgabe erfolgt in eine Datei im Dateisystem.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {
    // auslesen des betriebssystemspezifisch Zeichens für einen Zeilenumbruch
    String lineSeparator = System.getProperty("line.separator");
    // Defintion der Dateiendung
    String fileExt = ".csv";

    /**
     * Standardkonstruktor
     * 
     * @param canteens
     */
    public FileOutput() {

    }

    @Override
    public void outputMenuPlan(Canteen canteen) throws IOException {
        // auslesen des Kantinen-Namens
        String canteenName = canteen.getLocation().getName();

        StringBuilder ausgabeDaten = new StringBuilder();
        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        // das Datum des Gültigkeitsbeginns auslesen - Verwendung für Dateiname
        String startDate = shortendDate(mealsSortedByDate.get(0).getDate());

        for (Meal sortedMeal : mealsSortedByDate) {
            String meal;
            String date;

            date = shortendDate(sortedMeal.getDate());
            meal = sortedMeal.getRecipe().getName();

            ausgabeDaten.append(date + ";" + meal + "; " + lineSeparator);

        }

        String filename = generateFilename("Menueplan ab " + startDate + " - " + canteenName);

        ausgebenInDatei(ausgabeDaten.toString(), filename, true);
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
     * es wird der Dateiname generiert
     * 
     * @return
     */
    public String generateFilename(String customName) {
        // TODO (Marcel Baxmann) Prüfung vornehmen
        // auslesen der Pfadangabe
        String path = PropertyHelper.getProperty("outputData.saveTo");
        // setzen der übergebenen Parameter für den Filenamen
        String dateiname = path + customName + fileExt;

        // Anlage eines Objekts der Klasse File mit dem generierten Dateinamen
        File file = new File(dateiname);

        // Damit die bereits erstellten Ausgabeergebnisse nicht ueberschrieben werden
        // erfolgt ein Test, ob bereits eine Datei mit dem Dateinamen exisitert.
        // Ist dies der Fall wird solange der Dateiname hochgezählt,
        // bis die Datei unter einem neuen Dateinamen gespeichert werden kann

        // solange File Exisitert wird die For-Schleife ausgeführt
        for (int i = 1; file.exists(); i++) {
            dateiname = path + customName + " (" + i + ")" + fileExt;
            file = new File(dateiname);
            // nach 100 versuchen wird abgebrochen und die Datei mit dem Zusatz (101) überschrieben
            if (i == 100) {
                System.out
                        .println("Abbruch: Sie haben mehr als " + i + " mal die Datei abgelegt. Bitte leeren Sie ihren Ausgabeordner");
                break;
            }
        }
        System.out.println("Datei exisitiert bereits, Name wird angepasst auf: " + file.getAbsolutePath());
        return dateiname;
    }

    /**
     * Schreiben der übergebenen Daten in Datei
     * 
     * @param String wird übergeben
     * @throws IOException
     */
    public void ausgebenInDatei(String ausgabeDaten, String dateiname, boolean anhaengen) throws IOException {
        File file = new File(dateiname);
        FileWriter writer = new FileWriter(file, anhaengen);

        // übergebener String wird in Datei geschrieben
        writer.write(ausgabeDaten);

        // schreiben der Daten in Stream in die Datei
        writer.flush();

        // schließen des Stream
        writer.close();
    }

}
