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
 * Die Klasse {@link FileOutput} ist eine Klasse, die das Interface {@link IOutput} implementiert
 * und stellt daher die Methoden die im Interface definiert sind bereit.
 * Die Ausgabe erfolgt in Dateien im Dateisystem als CSV. Der Pfad für den Ablageort
 * wird in der zentralen Properties verwaltet.
 * 
 * @author Marcel Baxmann
 */
public class FileOutput implements IOutput {
    // Auslesen des betriebssystemspezifischen Zeichen für einen Zeilenumbruch
    final String lineSeparator = System.getProperty("line.separator");

    // Abrufen des Voreingestellten Separierungszeichens für die CSV-Datei aus den Properites
    final String dSSeperator = PropertyHelper.getProperty("outputData.dataSetSeperator");

    // Defintion der Dateiendung
    final String fileExt = ".csv";

    // zuweisen der Variante der Darstellung des Menueplans
    // Abrufen der Werte aus Properties
    String layoutMenuePlan = PropertyHelper.getProperty("outputData.LayoutMenuePlan");

    // wenn Wert single --> dann zweispaltige Auflistung nach Namen
    // wenn Wert grouped --> dann pro Spalte bzw. Datum drei Gerichte

    /**
     * Standardkonstruktor
     * 
     * @param canteens
     */
    public FileOutput() {

    }

    /**
     * Diese Methode stellt die Möglichkeit bereit ein Speiseplan als
     * CSV Ausgabe bereitzustellen. Die Ausgabe wird
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

        // Abfrage des Layouttyps - Springe in Ausführung wenn "single"
        if (layoutMenuePlan.equals("single")) {
            // Überschriften Zeile einfügen in Ausgabepuffer
            outputBuffer.append("Datum" + dSSeperator + "Gericht" + lineSeparator);

            // für jedes Gericht (meal) in der Liste werden das Datum und der Namen
            // ausgelesen und in den Ausgabepuffer angehangen
            if (mealsSortedByDate != null) {
                for (Meal sortedMeal : mealsSortedByDate) {

                    date = FileHelper.shortendDate(sortedMeal.getDate());
                    meal = sortedMeal.getRecipe().getName();

                    outputBuffer.append(date + dSSeperator + meal + lineSeparator);
                }
            }
            // Ende Variante single und Abfrage für Layouttyp grouped
        }
        else if (layoutMenuePlan.equals("grouped")) {
            // Abfrage der Anzahl der Gerichte pro Tag und speichern in lokale Variable (geparstes int)
            int mealsPerDay = Integer.parseInt(PropertyHelper.getProperty("planingPeriod.mealsPerDay"));

            // Je nach Anzahl der Gerichte werden in den Ausgabepuffer die Überschriften Zeile angehangen
            outputBuffer.append("Datum" + dSSeperator);
            for (int i = 1; i < mealsPerDay + 1; i++) {
                outputBuffer.append("Gericht " + i + dSSeperator);
            }
            // Springe in nächste Zeile in Ausgabedatei
            outputBuffer.append(lineSeparator);

            // Defintion einer Variable für die Abfrage, ob bereits ein Datum durchlaufen wurde
            String previousDate = null;

            if (mealsSortedByDate != null) {
                // für jedes Gericht (meal) in der Liste werden das Datum und der Namen ausgelesen
                for (Meal sortedMeal : mealsSortedByDate) {
                    date = FileHelper.shortendDate(sortedMeal.getDate());
                    meal = sortedMeal.getRecipe().getName();

                    // wenn previusDate noch nicht gefüllt ist (null) starte erste Zeile in Ausgabedatei
                    // indem erstes Datum und erstes Gericht in Ausgabepuffer angehangen werden
                    if (previousDate == null) {
                        outputBuffer.append(date + dSSeperator + meal);
                        // setze Variable previousDate auf aktuelles Datum
                        previousDate = date;
                    }
                    // wenn previous Date bereits mit einem Datum gefüllt ist rufe folgenden Strang auf
                    else {
                        // wenn vorhergehendes Datum aktuellem Datum entspricht haenge
                        // aktuelles Gericht in gleiche Zeile in Ausgabepuffer an
                        if (date.equals(previousDate)) {
                            outputBuffer.append(dSSeperator + meal);
                        }
                        // ansonsten
                        else {
                            // haenge neue Zeile an und starte mit neuem Datum und erstem Gericht
                            outputBuffer.append(lineSeparator);
                            outputBuffer.append(date + dSSeperator + meal);
                            // setze Variable previousDate auf aktuelles Datum
                            previousDate = date;
                        }
                    }
                }
            }
        }
        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Menueplan ab " + startDate + " - " + canteenName, fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Einkaufsliste als
     * CSV-Datei bereitzustellen. Die Ausgabe wird
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

        // Ueberschrift Zeile einfügen in Ausgabepuffer
        outputBuffer.append("Lieferant" + dSSeperator + "Zutat" + dSSeperator +
                "Menge" + dSSeperator +
                "Einheit" + dSSeperator +
                "Menge & Einheit" + lineSeparator);

        // prüfen, dass Objekt nicht null ist
        if (shoppingListItems != null) {
            // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
            for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
                // Name des Anbieters auslesen
                String name = entry.getKey().getName();

                // Liste mit Shoppinglist Items auslesen und in lokale Variable speichern
                List<ShoppingListItem> value = entry.getValue();

                // fuer jedes Listenelement Anweisungen ausfuehren
                for (ShoppingListItem item : value) {
                    // Name des Anbieters in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(name + dSSeperator);
                    // Name der Zutat in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getIngredient().getName() + dSSeperator);

                    // Wert und Einheit in getrennte Spalten
                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getValue() + dSSeperator);
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getUnit().getName() + dSSeperator);

                    // Wert und Einheit in gleiche Spalte schreiben
                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getValue() + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getUnit().getName() + dSSeperator + lineSeparator);

                }
                // neue Zeile in Puffer schreiben
                outputBuffer.append(lineSeparator);
            }
        }
        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Einkaufsliste", fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);
    }

    /**
     * Diese Methode stellt die Möglichkeit bereit eine Kostenübersicht als
     * CSV-Datei bereitzustellen. Die Ausgabe wird
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

        // Ueberschrift Zeile einfügen in Ausgabepuffer
        outputBuffer
                .append("Lieferant" + dSSeperator + "Zutat" + dSSeperator +
                        "Menge" + dSSeperator + "Wert" + dSSeperator +
                        "Waehrung" + dSSeperator + "Kosten in Euro" + dSSeperator + lineSeparator);

        // prüfen, dass Objekt nicht null ist
        if (shoppingListItems != null) {
            // für jeden Eintrag Abstract Provider Anweisungen ausfuehren
            for (Entry<AbstractProvider, List<ShoppingListItem>> entry : shoppingListItems.entrySet()) {
                // Variable für das Hochzählen der Kosten je Anbieter
                BigDecimal costPerDistributor = BigDecimal.valueOf(0);

                // Name des Anbieters auslesen
                String name = entry.getKey().getName();

                // Liste mit Shoppinglist Items auslesen und in lokale Variable speichern
                List<ShoppingListItem> value = entry.getValue();

                // fuer jedes Listenelement Anweisungen ausfuehren
                for (ShoppingListItem item : value) {
                    // Name des Anbieters in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(name + dSSeperator);
                    // Name der Zutat in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getIngredient().getName() + dSSeperator);
                    // Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append((item.getQuantity().getValue()) + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(item.getQuantity().getUnit().getName() + dSSeperator);

                    Amount calculatePrice = item.calculatePrice();
                    // Kosten ausgeben
                    // Wert und Einheit in getrennte Spalten
                    // formatierten Wert in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(FileHelper.formatBD(calculatePrice.getValue()) + dSSeperator);
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(calculatePrice.getUnit().getName() + dSSeperator);

                    // Wert und Einheit in gleiche Spalte schreiben
                    // formatierten Wert in Puffer anhaengen plus Leerzeichen
                    outputBuffer.append(FileHelper.formatBD(calculatePrice.getValue()) + " ");
                    // Einheit in Puffer anhaengen und Trennzeichen setzen
                    outputBuffer.append(calculatePrice.getUnit().getName() + lineSeparator);

                    // hochzaehlen der Einkaufskosten je Anbieter
                    costPerDistributor = costPerDistributor.add(calculatePrice.getValue());

                }
                // Ausgabe formatierte Gesamtkosten pro Provider
                outputBuffer
                        .append(dSSeperator + dSSeperator + dSSeperator + dSSeperator + "Kosten fuer " +
                                name + ":" + dSSeperator + FileHelper.formatBD(costPerDistributor) + " Euro" + lineSeparator + lineSeparator);

            }
            // Ausgabe der formatierten Gesamtkosten
            Amount calculateTotalPrice = shoppingList.calculateTotalPrice();
            outputBuffer.append(dSSeperator + dSSeperator + dSSeperator + dSSeperator +
                    "Gesamtkosten:" + dSSeperator + FileHelper.formatBD(calculateTotalPrice.getValue()) + " "
                    + calculateTotalPrice.getUnit().getName() + lineSeparator);
        }
        // sind alle Gerichte ausgelesen wird der Dateiname generiert
        String filename = FileHelper.generateFilename("Kostenuebersicht", fileExt);

        // der Ausgabepuffer und der Dateiname werden an die Methode zum Schreiben in eine Datei übergeben
        FileHelper.ausgebenInDatei(outputBuffer.toString(), filename, true);

    }
}
