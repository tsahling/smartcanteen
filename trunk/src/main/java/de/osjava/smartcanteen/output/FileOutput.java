package de.osjava.smartcanteen.output;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.builder.result.ShoppingListItem;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.helper.FileHelper;
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
        // zuweisen der Variante der Datenaufbereitung
        // wenn 0 --> dann zweispaltige Auflistung nach Namen
        // wenn 1 --> dann pro Spalte bzw. Datum drei Gerichte
        int variante = 1;
        String startDate = null;

        // Erzeugen String-Puffer Objekts in welches die Ergebnisse geschrieben werden
        StringBuilder ausgabeDaten = new StringBuilder();

        // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        // das Datum des Gültigkeitsbeginns auslesen - Verwendung für Dateiname
        startDate = shortendDate(mealsSortedByDate.get(0).getDate());

        String meal;
        String date;

        if (variante == 0) {
            // Überschriften Zeile einfügen
            ausgabeDaten.append("Datum;Gericht" + lineSeparator);

            // für jedes Gericht (meal) in der Liste werden das Datum und der Namen
            // ausgelesen und in den String-Puffer angehangen
            for (Meal sortedMeal : mealsSortedByDate) {

                date = shortendDate(sortedMeal.getDate());
                meal = sortedMeal.getRecipe().getName();

                ausgabeDaten.append(date + ";" + meal + lineSeparator);
            }
        }
        else {
            // TODO (Marcel Baxmann) Kommentieren
            int mealsPerDay = Integer.parseInt(PropertyHelper.getProperty("planingPeriod.mealsPerDay"));

            ausgabeDaten.append("Datum;");
            for (int i = 1; i < mealsPerDay + 1; i++) {
                ausgabeDaten.append("Gericht " + i + ";");
            }
            ausgabeDaten.append(lineSeparator);

            String previousDate = null;
            for (Meal sortedMeal : mealsSortedByDate) {
                date = shortendDate(sortedMeal.getDate());
                meal = sortedMeal.getRecipe().getName();

                if (previousDate == null) {
                    ausgabeDaten.append(date + ";" + meal);
                    previousDate = date;
                }
                else {
                    if (date.equals(previousDate)) {
                        ausgabeDaten.append(";" + meal);
                    }
                    else {
                        ausgabeDaten.append(lineSeparator);
                        ausgabeDaten.append(date + ";" + meal);
                        previousDate = date;
                    }
                }
            }
        }

        String filename = FileHelper.generateFilename("Menueplan ab " + startDate + " - " + canteenName, fileExt);
        FileHelper.ausgebenInDatei(ausgabeDaten.toString(), filename, true);
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

        List<ShoppingListItem> shoppingListItems = shoppingList.getShoppingListItems();

    }

    @Override
    public void outputTotalCosts(ShoppingList shoppingList) {
    }

}
