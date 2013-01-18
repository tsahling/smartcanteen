package de.osjava.smartcanteen.output;

import java.io.IOException;
import java.util.List;

import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.helper.FileHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link HTMLOutput} ist eine Klasse, die das Interface {@link IOutput} implementiert
 * und stellt daher die Methoden die im Interface definiert sind bereit.
 * Die Ausgabe erfolgt in Dateien im Dateisystem als HTML. Der Pfad für den Ablageort
 * wird in der zentralen Properties verwaltet.
 * 
 * @author Marcel Baxmann
 */
public class HTMLOutput implements IOutput {
    // Auslesen des betriebssystemspezifischen Zeichen für einen Zeilenumbruch
    final String lineSeparator = System.getProperty("line.separator");
    // Defintion der Dateiendung
    final String fileExt = ".html";

    /**
     * Standardkonstruktor
     */
    public HTMLOutput() {
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit einn Speiseplan als
     * HTML-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link Canteen} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param canteen
     *            Kantinen-Objekt wird übergeben
     * @throws IOException
     * @author Marcel Baxmann
     */
    @Override
    public void outputMenuPlan(Canteen canteen) throws IOException {
        // auslesen des Kantinen-Namens
        String canteenName = canteen.getLocation().getName();
        // typisieren von Variablen für Datensätze der Gerichte und Datum
        String meal;
        String date;

        // Erzeugen StringBuilder-Objekts (Ausgabepuffer) in welches die Ergebnisse geschrieben werden
        StringBuilder ausgabeDaten = new StringBuilder();

        // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        // das Datum des Gültigkeitsbeginns auslesen - Verwendung für Dateiname
        String startDate = FileHelper.shortendDate(mealsSortedByDate.get(0).getDate());

        // Abfrage der Anzahl der Gerichte pro Tag und speichern in lokale Variable (geparstes int)
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

        // Je nach Anzahl der Gerichte werden in den Ausgabepuffer die Überschriften Zeile angehangen
        for (int i = 1; i < mealsPerDay + 1; i++) {
            ausgabeDaten.append("<th>Gericht " + i + "</th>");
        }

        // HTML-Ausgabe Ende der Überschrift-Zeile und Start Inhalts-Zeile
        ausgabeDaten.append("</tr>" + lineSeparator +
                "<tr>");

        // für jedes Gericht (meal) in der Liste werden das Datum und der Namen ausgelesen
        String previousDate = null;
        for (Meal sortedMeal : mealsSortedByDate) {
            date = FileHelper.shortendDate(sortedMeal.getDate());
            meal = sortedMeal.getRecipe().getName();

            // wenn previusDate noch nicht gefüllt ist (null) starte erste Zeile in Ausgabedatei
            // indem erstes Datum und erstes Gericht in Ausgabepuffer angehangen werden
            if (previousDate == null) {
                // Dateum umrundet von HTML Elementen für eine Zelle
                ausgabeDaten.append("<td>" + date + "</td>");
                // Ein Gericht umrundet von HTML Elementen für eine Zelle
                // Check ob Bild vorhanden und einfügen img-tag je nach Typ des Gerichts
                ausgabeDaten.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
                // setze Variable previousDate auf aktuelles Datum
                previousDate = date;
            }
            // wenn previous Date bereits mit einem Datum gefüllt ist rufe folgenden Strang auf
            else {
                // wenn vorhergehendes Datum aktuellem Datum entspricht haenge
                // aktuelles Gericht in gleiche Zeile in Ausgabepuffer an
                if (date.equals(previousDate)) {
                    // Ein Gericht umrundet von HTML-Tag für eine Zelle
                    // Check ob Bild vorhanden und einfügen img-tag je nach Typ des Gerichts
                    ausgabeDaten.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
                }
                else {
                    // beende Zeile durch </tr> und starte mit neuem Datum umrundet mit HTML-Tag für Zelle
                    ausgabeDaten.append("</tr>" + lineSeparator +
                            "<td>" + date + "</td>");
                    // Ein Gericht umrundet von HTML-Tag für eine Zelle
                    // Check ob Bild vorhanden und einfügen img-tag je nach Typ des Gerichts
                    ausgabeDaten.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
                    // setze Variable previousDate auf aktuelles Datum
                    previousDate = date;
                }
            }
        }

        // HTML-Ausgabe Ende Tabelle und HTML-Dokument
        ausgabeDaten.append(lineSeparator + "</table>" + lineSeparator +
                "</body>" + lineSeparator +
                "</html>");

        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Menueplan ab " + startDate + " - " + canteenName, fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(ausgabeDaten.toString(), filename, true);
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Einkaufsliste als
     * HTML-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link ShoppingList} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param shoppingList
     *            Einkaufslisten-Objekt wird übergeben
     * @author Marcel Baxmann
     */
    @Override
    public void outputShoppingList(ShoppingList shoppingList) {
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Kostenübersicht als
     * HTML-Datei bereitzustellen. Die Ausgabe wird
     * auf Basis der Daten eines Objekts {@link ShoppingList} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param shoppingList
     *            Einkaufslisten-Objekt wird übergeben
     * @author Marcel Baxmann
     */
    @Override
    public void outputTotalCosts(ShoppingList shoppingList) {
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit das übergebene Gericht
     * dahingehend zu ueberpruefen ob es Fisch, Fleisch oder vegetarisch ist
     * Je nach Ergebnis wird ein anderer String mit Verweis auf ein passsendes Bild geliefert.
     * Die Ausgabe wird auf Basis der Daten eines Objekts {@link Meal} gestaltet. Dieses
     * Objekt muss der Methode beim Aufruf übergeben werden.
     * 
     * @param sortedMeal
     *            ein Gericht
     * @author Marcel Baxmann
     */
    private String pictureCheck(Meal sortedMeal) {
        // TODO(Marcel Baxmann) Pfadangabe für spätere Ergebnisse?
        // TODO(Marcel Baxmann) Methode in FileHelper zum kopieren der Bilder in Ausgabeordner, sofern diese noch nicht
        // exisiteren
        // vordefinierte Bildergrößte wird aus Properties ausgelesen
        int size = Integer.parseInt(PropertyHelper.getProperty("outputData.picSize"));
        // auslesen der Pfadangabe
        String picPath = PropertyHelper.getProperty("outputData.picSaveTo");

        // Prüfe ob zu verwendendes Bild im Zielordner ist
        boolean existFishPic = FileHelper.foundPicture(picPath + "fish.jpg");
        boolean existMeatPic = FileHelper.foundPicture(picPath + "meat.jpg");
        boolean existVeggiePic = FileHelper.foundPicture(picPath + "veggie.jpg");

        // initalisieren des Rückgabewerts
        String picture = "";
        // wenn es ein Fischgericht ist und das Bild im angegebenen Pfad vorhanden ist wird ein HTML-Code
        // mit Verweis auf ein Fischbild als String geliefert
        if (sortedMeal.getRecipe().isFishRecipe() && existFishPic) {
            picture = "<img src=\"" + picPath + "fish.jpg\" width=\"" + size + "\">";
        }
        // wenn es ein Fleischericht ist und das Bild im angegebenen Pfad vorhanden ist ein HTML-Code mit
        // Verweis auf ein GFleischbild als String geliefert
        else if (sortedMeal.getRecipe().isMeatRecipe() && existMeatPic) {
            picture = "<img src=\"" + picPath + "meat.jpg\" width=\"" + size + "\">";
        }
        // wenn es ein vegetarisches Gericht und das Bild im angegebenen Pfad vorhanden ist wird ein HTML-Code
        // mit Verweis auf ein Bild mit vegetarischem Essen als String geliefert
        else if (sortedMeal.getRecipe().isVegetableRecipe() && existVeggiePic) {
            picture = "<img src=\"" + picPath + "veggie.jpg\" width=\"" + size + "\">";
        }
        else {
            // Wenn keine Zuordnung getroffen werden kann wird ein leerer String geliefert
            picture = "";
        }
        // Rueckgabe des Strings
        return picture;
    }

}
