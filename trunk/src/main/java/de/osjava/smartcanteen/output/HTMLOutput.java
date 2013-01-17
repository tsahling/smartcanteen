package de.osjava.smartcanteen.output;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.helper.FileHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link HTMLOutput} ist eine Klasse, die das Interface {@link IOutput} implementiert und stellt daher die
 * Methoden die im Interface
 * definiert sind bereit. Die Ausgabe erfolgt als HTML-Seite.
 * 
 * @author Marcel Baxmann
 */
public class HTMLOutput implements IOutput {
    // auslesen des betriebssystemspezifischen Zeichen für einen Zeilenumbruch
    String lineSeparator = System.getProperty("line.separator");
    // Defintion der Dateiendung
    String fileExt = ".html";

    /**
     * Standardkonstruktor
     */
    public HTMLOutput() {
    }

    @Override
    public void outputMenuPlan(Canteen canteen) throws IOException {
        // auslesen des Kantinen-Namens
        String canteenName = canteen.getLocation().getName();

        // Erzeugen String-Puffer Objekts in welches die Ergebnisse geschrieben werden
        StringBuilder ausgabeDaten = new StringBuilder();

        // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        // das Datum des Gültigkeitsbeginns auslesen - Verwendung für Dateiname
        String startDate = shortendDate(mealsSortedByDate.get(0).getDate());

        String meal;
        String date;

        // TODO (Marcel Baxmann) Kommentieren
        int mealsPerDay = Integer.parseInt(PropertyHelper.getProperty("planingPeriod.mealsPerDay"));

        // HTML-Ausgabe Startsequenz
        ausgabeDaten.append("<html>" + lineSeparator +
                "<head>" + lineSeparator +
                "<title>Menueplan ab dem " + startDate + " - Kantine " + canteenName +
                "</title>" + lineSeparator +
                "</head>" + lineSeparator +
                "<body>" + lineSeparator);

        // HTML-Ausgabe Überschrift
        ausgabeDaten.append("<h1>Menueplan ab dem " + startDate + " - Kantine " + canteenName + "</h1>");

        // HTML-Ausgabe Start Tabelle
        ausgabeDaten.append("<table border=\"1\"" + lineSeparator +
                "<tr>");

        // HTML-Ausgabe Beginn Überschriftzeile der Tabelle
        // TODO (Marcel Baxmann) Tim fragen, wie ich bei der unbestimmten Anzahl der Ingredients bei der Shopliste die
        // Anzahl abfragen kann
        ausgabeDaten.append("<th>Datum</th>");

        for (int i = 1; i < mealsPerDay + 1; i++) {
            ausgabeDaten.append("<th>Gericht " + i + "</th>");
        }

        // HTML-Ausgabe Ende einer Zeile und Start neue Zeile
        ausgabeDaten.append("</tr>" + lineSeparator +
                "<tr>");

        String previousDate = null;
        for (Meal sortedMeal : mealsSortedByDate) {
            date = shortendDate(sortedMeal.getDate());
            meal = sortedMeal.getRecipe().getName();

            if (previousDate == null) {
                ausgabeDaten.append("<td>" + date + "</td>");
                ausgabeDaten.append("<td>" + meal + "</td>");
                previousDate = date;
            }
            else {
                if (date.equals(previousDate)) {
                    ausgabeDaten.append("<td>" + meal + "</td>");
                }
                else {
                    ausgabeDaten.append("</tr>" + lineSeparator +
                            "<td>" + date + "</td>");
                    ausgabeDaten.append("<td>" + meal + "</td>");
                    previousDate = date;
                }
            }
        }

        // HTML-Ausgabe Ende Tabelle und HTML-Dokument
        ausgabeDaten.append(lineSeparator + "</table>" + lineSeparator +
                "</body>" + lineSeparator +
                "</html>");

        String filename = FileHelper.generateFilename("Menueplan ab " + startDate + " - " + canteenName, fileExt);
        FileHelper.ausgebenInDatei(ausgabeDaten.toString(), filename, true);
    }

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
}
