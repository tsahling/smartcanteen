package de.osjava.smartcanteen.output;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.builder.result.ShoppingListItem;
import de.osjava.smartcanteen.data.AbstractProvider;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.datatype.Amount;
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
        StringBuilder outputBuffer = new StringBuilder();

        // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
        List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

        // das Datum des Gültigkeitsbeginns auslesen - Verwendung für Dateiname
        String startDate = FileHelper.shortendDate(mealsSortedByDate.get(0).getDate());

        // Abfrage der Anzahl der Gerichte pro Tag und speichern in lokale Variable (geparstes int)
        int mealsPerDay = Integer.parseInt(PropertyHelper.getProperty("planingPeriod.mealsPerDay"));

        // HTML-Ausgabe Generierung Kopfdaten des Dokuemnts und anhaengen in Puffer
        outputBuffer.append(generateHTMLHeader("Menueplan ab dem " + startDate + " - Kantine " + canteenName));

        // HTML-Ausgabe Start Tabelle
        outputBuffer.append("<table border=\"1\">" + lineSeparator + "<tr>");

        // HTML-Ausgabe Beginn Überschriftzeile der Tabelle
        outputBuffer.append("<th>Datum</th>");

        // Je nach Anzahl der Gerichte werden in den Ausgabepuffer die Überschriften Zeile angehangen
        for (int i = 1; i < mealsPerDay + 1; i++) {
            outputBuffer.append("<th>Gericht " + i + "</th>");
        }

        // HTML-Ausgabe Ende der Überschrift-Zeile und Start Inhalts-Zeile
        outputBuffer.append("</tr>" + lineSeparator +
                "<tr>");

        // für jedes Gericht (meal) in der Liste werden das Datum und der Namen ausgelesen
        String previousDate = null;
        if (mealsSortedByDate != null) {
            for (Meal sortedMeal : mealsSortedByDate) {
                date = FileHelper.shortendDate(sortedMeal.getDate());
                meal = sortedMeal.getRecipe().getName();

                // wenn previusDate noch nicht gefüllt ist (null) starte erste Zeile in Ausgabedatei
                // indem erstes Datum und erstes Gericht in Ausgabepuffer angehangen werden
                if (previousDate == null) {
                    // Dateum umrundet von HTML Elementen für eine Zelle
                    outputBuffer.append("<td>" + date + "</td>");
                    // Ein Gericht umrundet von HTML Elementen für eine Zelle
                    // Check ob Bild vorhanden und einfügen img-tag je nach Typ des Gerichts
                    outputBuffer.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
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
                        outputBuffer.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
                    }
                    else {
                        // beende Zeile durch </tr> und starte mit neuem Datum umrundet mit HTML-Tag für Zelle
                        outputBuffer.append("</tr>" + lineSeparator +
                                "<tr><td>" + date + "</td>");
                        // Ein Gericht umrundet von HTML-Tag für eine Zelle
                        // Check ob Bild vorhanden und einfügen img-tag je nach Typ des Gerichts
                        outputBuffer.append("<td>" + meal + pictureCheck(sortedMeal) + "</td>");
                        // setze Variable previousDate auf aktuelles Datum
                        previousDate = date;
                    }
                }
            }
        }
        // HTML-Ausgabe Ende Tabelle und HTML-Dokument
        outputBuffer.append(lineSeparator + "</table>" + lineSeparator +
                "</body>" + lineSeparator +
                "</html>");

        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Menueplan ab " + startDate + " - " + canteenName, fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);
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
     * @throws IOException
     */
    @Override
    public void outputShoppingList(ShoppingList shoppingList) throws IOException {
        // Erzeugen StringBuilder-Objekts (Ausgabepuffer) in welches die Ergebnisse geschrieben werden
        StringBuilder outputBuffer = new StringBuilder();

        // Map mit Key Abstract Provider und Value List<ShoppingListItem> anlegen
        Map<AbstractProvider, List<ShoppingListItem>> shoppingListItems = shoppingList
                .getShoppingListItemsGroupedByProvider();

        // HTML-Ausgabe Generierung Kopfdaten des Dokuemnts und anhaengen in Puffer
        outputBuffer.append(generateHTMLHeader("Einkaufsliste je Lieferant"));

        // Generiere Übersicht mit Links auf Anker der einzelnen Tabellen
        outputBuffer.append("Einkaufslisten fuer folgende Lieferanten wurden " +
                "generiert (anklicken zum anzeigen):<ul>" + lineSeparator);

        // Alagen lokale Variable für die Positionbestimmung des Distributors (Distributor-ID)
        int numberDistributor = 0;

        // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
        for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
            // Name des aktuellen Providers auslesen
            String name = entry.getKey().getName();
            outputBuffer.append("<li><a href=\"#" + numberDistributor +
                    "\">" + name + "</a></li>" + lineSeparator);
            // zum nächsten Distributor hochzaehlen (Distributor-ID)
            numberDistributor++;
        }
        // Aufzaehlung beenden
        outputBuffer.append("</ul>" + lineSeparator + lineSeparator);

        // Zaehler für die Positionbestimmung des Distributors (Distributor-ID) zuruecksetzen, da neue Schleife
        numberDistributor = 0;
        // prüfen, dass Objekt nicht null ist
        if (shoppingListItems != null) {
            // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
            for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
                // Name des Anbieters auslesen
                String name = entry.getKey().getName();

                // HTML-Ausgabe Ueberschrift Lieferant mit HTML-Name Tag,
                // so dass ueber die erstellte Liste direktzum Anbieter gesprungen werden kann
                outputBuffer.append("<h3><a name=\"" + numberDistributor +
                        "\">Einkaufsliste fuer Lieferant: " + name + "</a></h2>");

                // HTML-Ausgabe Start Tabelle
                outputBuffer.append("<table border=\"1\">" + lineSeparator + "<tr>");

                // HTML-Ausgabe Beginn Überschriftzeile der Tabelle
                outputBuffer.append("<th>Lieferant</th><th>Zutat</th>" +
                        "<th>Menge</th><th>Einheit</th><th>Menge & Einheit</th></tr>");

                // Liste mit Shoppinglist Items auslesen und in lokale Variable speichern
                List<ShoppingListItem> value = entry.getValue();

                // fuer jedes Listenelement Anweisungen ausfuehren - jeweils eine Zeile generieren
                for (ShoppingListItem item : value) {
                    // Name des Anbieters in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<tr><td>" + name + "</td>");
                    // Name der Zutat in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getIngredient().getName() + "</td>");

                    // Wert und Einheit in getrennte Spalten
                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getQuantity().getValue() + "</td>");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getQuantity().getUnit().getName() + "</td>");

                    // Wert und Einheit in gleiche Spalte schreiben
                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getQuantity().getValue() + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getUnit().getName() + "</td></tr>" + lineSeparator);
                }
                // HTML-Ausgabe Ende Tabelle mit Link zum Seitenbeginn
                outputBuffer.append("</table>" + lineSeparator + "<a href=\"#top\">" +
                        " zurueck zur Uebersicht</a>" + lineSeparator);
                // zum nächsten Distributor hochzaehlen (Distributor-ID)
                numberDistributor++;
            }
        }
        // HTML-Ausgabe Ende Tabelle und HTML-Dokument
        outputBuffer.append("</body>" + lineSeparator + "</html>");

        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Einkaufsliste", fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);
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
     * @throws IOException
     */
    @Override
    public void outputTotalCosts(ShoppingList shoppingList) throws IOException {
        // Erzeugen StringBuilder-Objekts (Ausgabepuffer) in welches die Ergebnisse geschrieben werden
        StringBuilder outputBuffer = new StringBuilder();

        // Map mit Key Abstract Provider und Value List<ShoppingListItem> anlegen
        Map<AbstractProvider, List<ShoppingListItem>> shoppingListItems = shoppingList
                .getShoppingListItemsGroupedByProvider();

        // Abfragen der Anzahl an Lieferanten aus der Map
        int elements = shoppingListItems.entrySet().size();
        // Array initalisieren mit 2 Datensaetzen pro Anzahl Provider
        String dataOverview[][] = new String[elements][2];

        // HTML-Ausgabe Generierung Kopfdaten des Dokuemnts und anhaengen in Puffer
        outputBuffer.append(generateHTMLHeader("Kostenubersicht"));

        // Generiere Übersicht mit Links auf Anker der einzelnen Tabellen
        outputBuffer
                .append("Die Darstellung enhaelt Kostenuebersichten mit Einzelposition je Lieferant<br>" +
                        "sowie eine Gesamtkostenuebersicht (anklicken zum anzeigen):<ul>" + lineSeparator);

        // Zaehler für die Positionbestimmung des Distributors (Distributor-ID) zuruecksetzen, da neue Schleife
        int numberDistributor = 0;
        // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
        for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
            String name = entry.getKey().getName();
            outputBuffer
                    .append("<li><a href=\"#" + numberDistributor + "\">" + name + "</a></li>" + lineSeparator);
            // zum nächsten Distributor hochzaehlen (Distributor-ID)
            numberDistributor++;
        }
        // lokale Variable zum Speichern der Ankernummer der Gesamtkostenuebersicht
        int positionCostOverview = numberDistributor;
        outputBuffer
                .append("<li><a href=\"#" + positionCostOverview + "\">Gesamtkostenuebersicht</a></li>" + lineSeparator);
        // Aufzaehlung beenden
        outputBuffer.append("</ul>" + lineSeparator + lineSeparator);

        // Beginn Ausgabe Kostenuebersichten mit Einzelposition je Lieferant
        outputBuffer.append("<h2> Kostenuebersichten mit Einzelposition je Lieferant</h2>" + lineSeparator);
        // Zaehler für die Positionbestimmung des Distributors (Dristributor-ID) zuruecksetzen, da neue Schleife
        numberDistributor = 0;
        // prüfen, dass Objekt nicht null ist
        if (shoppingListItems != null) {
            // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
            for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
                // Variable für das Hochzählen der Kosten je Anbieter
                BigDecimal costPerDistributor = BigDecimal.valueOf(0);

                // Name des Anbieters auslesen
                String name = entry.getKey().getName();

                // HTML-Ausgabe Ueberschrift Lieferant mit HTML-Name Tag,
                // so dass ueber die erstellte Liste direktzum Anbieter gesprungen werden kann
                outputBuffer.append("<h3><a name=\"" + numberDistributor +
                        "\">Einkaufsliste fuer Lieferant: " + name + "</a></h2>");

                // HTML-Ausgabe Start Tabelle
                outputBuffer.append("<table border=\"1\">" + lineSeparator + "<tr>");

                // HTML-Ausgabe Beginn Überschriftzeile der Tabelle
                outputBuffer.append("<th>Lieferant</th><th>Zutat</th>" +
                        "<th>Menge</th><th>Wert</th><th>Waehrung</th><th>Kosten in Euro</th></tr>");

                // Liste mit Shoppinglist Items auslesen und in lokale Variable speichern
                List<ShoppingListItem> value = entry.getValue();

                Amount calculatePrice = null;
                // fuer jedes Listenelement Anweisungen ausfuehren - jeweils eine Zeile generieren
                for (ShoppingListItem item : value) {
                    // Name des Anbieters in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<tr><td>" + name + "</td>");
                    // Name der Zutat in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getIngredient().getName() + "</td>");

                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + item.getQuantity().getValue() + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getUnit().getName() + "</td>");

                    calculatePrice = item.calculatePrice();
                    // Kosten ausgeben
                    // Wert und Einheit in getrennte Spalten
                    // formatierten Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + FileHelper.formatBD(calculatePrice.getValue()) + "</td>");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append("<td>" + calculatePrice.getUnit().getName() + "</td>");

                    // Wert und Einheit in gleiche Spalte schreiben
                    // formatierten Wert in Puffer anhaengen plus Leerzeichen
                    outputBuffer.append("<td>" + FileHelper.formatBD(calculatePrice.getValue()) + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(calculatePrice.getUnit().getName() + "</td></tr>" + lineSeparator);

                    // hochzaehlen der Einkaufskosten je Anbieter
                    costPerDistributor = costPerDistributor.add(calculatePrice.getValue());

                }
                // sichern Datensaetze fuer Uebersichtsanzeige in Array
                dataOverview[numberDistributor][0] = name;
                if (calculatePrice != null) {
                    dataOverview[numberDistributor][1] = FileHelper.formatBD(costPerDistributor)
                            + " " + calculatePrice.getUnit().getName();
                }

                // Ausgabe formatierte Gesamtkosten pro Provider
                outputBuffer.append("<tr><td colspan=\"5\" align=\"right\"> Kosten fuer " +
                        name + ":</td><td>" + FileHelper.formatBD(costPerDistributor) +
                        " Euro</td></tr>" + lineSeparator);

                // HTML-Ausgabe Ende Tabelle mit Link zum Seitenbeginn-Anker
                outputBuffer
                        .append("</table>" + lineSeparator + "<a href=\"#top\"> zurueck zur Uebersicht</a>" + lineSeparator);
                numberDistributor++;
            }
        }

        // Beginn Ausgabe Gesamtkostenuebersicht
        outputBuffer.append("<h2><a name=\"" + positionCostOverview + "\">" +
                "Gesamtkostenuebersicht</a></h2>" + lineSeparator);
        // HTML-Ausgabe Start Tabelle
        outputBuffer.append("<table border=\"1\">" + lineSeparator + "<tr>");
        // Ueberschriftzeile für Tabelle
        outputBuffer.append("<tr><th>Lieferant</th><th>Kosten in Euro</th></tr>");

        for (int i = 0; i < dataOverview.length; i++) {
            outputBuffer.append("<tr><td>" + dataOverview[i][0] + "</td>");
            outputBuffer.append("<td>" + dataOverview[i][1] + "</td></tr>");
        }

        // HTML-Ausgabe der formatierten Gesamtkosten
        Amount calculateTotalPrice = shoppingList.calculateTotalPrice();
        outputBuffer.append("<tr><td align=\"right\"><b>Gesamtkosten:</b></td>" +
                "<td><b>" + FileHelper.formatBD(calculateTotalPrice.getValue()) + " "
                + calculateTotalPrice.getUnit().getName() + "</b></td></tr>" + lineSeparator);

        // HTML-Ausgabe Ende Tabelle mit Link zum Seitenbeginn-Anker
        outputBuffer
                .append("</table>" + lineSeparator + "<a href=\"#top\"> zurueck zur Uebersicht</a>" + lineSeparator);
        numberDistributor++;

        // HTML-Ausgabe Ende Tabelle und HTML-Dokument
        outputBuffer.append("</body>" + lineSeparator + "</html>");

        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Kostenuebersicht", fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);
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
        // TODO(Tim Sahling) Pfadangabe für spätere Ergebnisse?

        // vordefinierte Bildergrößte wird aus Properties ausgelesen
        int size = Integer.parseInt(PropertyHelper.getProperty("outputData.picSize"));
        // auslesen der Pfadangabe
        String outputPath = PropertyHelper.getProperty("outputData.saveTo");
        String picPath = PropertyHelper.getProperty("outputData.picSaveTo");

        // Prüfe ob zu verwendendes Bild im Zielordner ist
        boolean existFishPic = FileHelper.foundPicture(outputPath + picPath + "fish.jpg");
        boolean existMeatPic = FileHelper.foundPicture(outputPath + picPath + "meat.jpg");
        boolean existVeggiePic = FileHelper.foundPicture(outputPath + picPath + "veggie.jpg");

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

    /**
     * Methode zum generieren eines HTML-Headers mit Überschrift
     * 
     * @param String
     *            Titel der im Mail-Header und in Überschrift stehen soll
     * @author Marcel Baxmann
     * @return String
     */
    private String generateHTMLHeader(String title) {

        StringBuilder buffer = new StringBuilder();

        // HTML-Ausgabe Startsequenz
        buffer.append("<html>" + lineSeparator +
                "<head>" + lineSeparator +
                "<title>" + title +
                "</title>" + lineSeparator +
                "<META HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=utf-8\">" + lineSeparator +
                "</head>" + lineSeparator +
                "<body>" + lineSeparator);

        // HTML-Ausgabe Überschrift
        buffer.append("<h1><a name=\"top\"><u>" + title + "</u></a></h1>" + lineSeparator);

        return buffer.toString();
    }

}
