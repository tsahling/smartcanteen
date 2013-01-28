package de.osjava.smartcanteen.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.osjava.smartcanteen.application.InputFileHandler;
import de.osjava.smartcanteen.base.HitListBase;
import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.data.Farmer;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.data.Wholesaler;
import de.osjava.smartcanteen.data.item.HitListItem;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.IngredientType;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;

/**
 * @author Francesco Luciano
 * 
 */
public class BaseHelper {

    // Feste Werte aus Properties Datei auslesen und String Variablen zuweisen

    private static final String INPUT_DATA_FISH = PropertyHelper.getProperty("inputData.ingredientType.fish");
    private static final String INPUT_DATA_MEAT = PropertyHelper.getProperty("inputData.ingredientType.meat");
    private static final String INPUT_DATA_VEGETERIAN = PropertyHelper
            .getProperty("inputData.ingredientType.vegetarian");

    private static final String INPUT_DATA_UNIT_TYPE_GRAMM = PropertyHelper.getProperty("inputData.unitType.gramm");
    private static final String INPUT_DATA_UNIT_TYPE_LITER = PropertyHelper.getProperty("inputData.unitType.liter");
    private static final String INPUT_DATA_UNIT_TYPE_PICES = PropertyHelper.getProperty("inputData.unitType.pieces");

    private static final String INPUT_DATA_TYPE_OF_TRADER_WHOLESALER = PropertyHelper
            .getProperty("inputData.typeOfTrader.wholesaler");
    private static final String INPUT_DATA_TYPE_OF_TRADER_FARMER = PropertyHelper
            .getProperty("inputData.typeOfTrader.farmer");

    /**
     * Diese Methode liest die beliebtesten Gerichte der Input Datei HitList
     * 
     * @param inputFileURL Url der einzulesenden Datei
     * 
     * @throws IOException
     * @author Francesco Luciano
     * @return hitlist Java List mit Gerichten
     */
    public static HitListBase readHitlist(URL inputFileURL) throws IOException {

        /* Die Klasse List ist eine der Java Collections.
         * Eine Liste ist eine Java-Repraesentation einer linearen Liste. Die Liste kann Elemente beliebigen
         * Typs enthalten, und ihre Laenge ist zur Laufzeit veraenderbar.
         * 
         * List erlauben duplikate */

        // Die Variable lines ist vom Typ List der ein Array von Strings speichert
        // Die Variable wird verwendet um die eingelesenen Zeilen in Ihren Einzelteilen zu Speichern
        List<String[]> lines = new ArrayList<String[]>();

        // Die Variable hitlist ist vom Typ HitListBase
        // HitListBase ist eine Datentraegerklasse welche die komplette einzulesenden Datei
        // der beliebtesten Gerichte repraesentiert.
        HitListBase hitlist = new HitListBase();

        // Die Variable mealplacement speichert den Rang des Gericht
        Integer mealPlacement = null;
        // Die Variable mealName speichert den Namen des Gericht
        String mealName = null;

        // Instanz vom Typ CSVTokenizer um die Datei einzulesen und zu verarbeiten
        InputFileHandler csv = new InputFileHandler(inputFileURL, ',');

        // Solange es Zeilen in der Datei gibt, werden diese in den List lines gespeichert
        while (csv.hasMoreLines()) {
            lines.add(csv.nextLine());
        }

        // Wenn keine Zeile mehr in der einzulesenden Datei vorhanden sind,
        // wird durch den gefuellten List iteriert.
        // Die Laufvariable i laeuft die Elemnte des Lists ab
        // Jedes Element ist ein String[] mit zwei Feldern
        // Das 0 Feld beinhaltet des Rang des Gericht
        // Das 1 Feld beinhaltet desn Namen des Gericht
        // Die Werte aus dem String[] werden den vorher deklarierten Variablen gespeichert

        // Mithilfe dieser Variablen wird eine HitListItem erzeugt. Ein HitListItem ist eine
        // Datentraegerklasse die EIN Gericht der HitList Klasse repraesentiert.
        // Das erzeugte HitListItem wird der hitlist hinzugefuegt.

        for (int i = 0; i <= lines.size() - 1; i++) {

            mealPlacement = Integer.valueOf(lines.get(i)[0]);
            mealName = lines.get(i)[1];
            HitListItem oneHitListItem = new HitListItem(mealName, mealPlacement);
            hitlist.addHitListItem(oneHitListItem);
        }

        return hitlist;
    }

    /**
     * Methode um eine Preisliste einzulesen.
     * 
     * @author Francesco Luciano
     * @param inputFileURL URL der einzulesenden Datei
     * @return
     * @throws IOException
     */
    public static ProviderBase readPriceList(URL inputFileURL, ProviderBase providerBase) throws IOException {

        // s.o.
        List<String[]> lines = new ArrayList<String[]>();

        // TODO(Francesco Luciano) Tim Fragen was hier passiert!
        if (providerBase == null) {
            providerBase = new ProviderBase();
        }

        // Die Variable typeOfTrader speichert den Typ eines Haendler ab.
        String typeOfTrader = null;
        // Die Variable nameOfTrader speichert den Namen eines Haendler ab.
        String nameOfTrader = null;
        // Die Variable transportCost speichert die Transportkosten eines Haendler ab.
        String transportUnit = null;
        // Die Variable transportCostBD speichert die Transportkosten eines Haendler als BigDecimal ab.
        BigDecimal transportUnitBD = null;

        BigDecimal sizeOfItemBundleBD = null;
        String unitOfItemBundle = null;
        // Die Variable size ist eine vom Typ Amount, die Klasse Amount ist eine Datentraegerklasse die eine Groeße
        // repraesentiert.
        // Ein Amount fuer einen Mengenangabe setzt dich aus der Mengenangabe als BigDecimal und einer Einheit zusammen.
        Amount size = null;

        // Die Variable nameOfIngredient speichert den Namen eines Lebensmittel ab.
        String nameOfIngredient = null;
        // Die Variable typeOfIngredient speichert den Typ (Fleisch, Fisch, Gemuese) eines Lebensmittel ab.
        String typeOfIngredient = null;

        String priceOfIngredient = null;
        BigDecimal priceOfIngredientBD = null;
        // Die Variable price ist eine vom Typ Amount, die Klasse Amount ist eine Datentraegerklasse die Groeße
        // repraesentiert.
        // Ein Amount fuer einen Preis setzt dich aus der Einkauspreis als BigDecimal und einer Einheit zusammen.
        Amount price = null;

        // Variable availableQuantityOfIngredient repraesentiert die vroahndene Menge eines Gebeindes/Lebensmittel
        BigDecimal availableQuantityOfIngredient = null;

        // Instanz der Datentraegerklasse Ingredient, die ein Lebensmittel repraesentiert
        Ingredient ingredient = null;
        // Instanz der Datentraegerklasse PriceListItem, die ein Element in einer Preisliste repraesentiert
        PriceListItem priceListItem = null;
        // Java Set um die Positionen der Preisliste zu speichern
        Set<PriceListItem> priceList = new HashSet<PriceListItem>();

        // s.o
        InputFileHandler csv = new InputFileHandler(inputFileURL, ',');
        // Solange hasMoreLines() == true in der Schleife bleiben
        while (csv.hasMoreLines()) {
            // String[] in lines hinzufügen
            lines.add(csv.nextLine());
        }

        // Kopfzeile der Preisliste auslesen

        // Typ des Haendlers (Bauer, Großhaendler)
        typeOfTrader = lines.get(0)[0];
        // Name des Haendler
        nameOfTrader = lines.get(0)[1];
        // Transportkosten des Haendler
        transportUnit = lines.get(0)[2].replace(",", ".");
        // Transportkosten von String in BigDecimal umgewandelt
        transportUnitBD = new BigDecimal(transportUnit);

        // Iteration des List faengt bei Element 1 an, da 0 die Kopfzeile ist.
        // Die Laufvariable i laeuft die Elemnte des Lists ab
        // Jedes Element ist ein String[] mit fuenf Feldern
        // Das 0 Feld beinhaltet die Groeße des Gebindes
        // Das 1 Feld beinhaltet die Mengenangabe (Stueck, g, kg) des Lebensmittel
        // Das 2 Feld beinhaltet den Typ des Lebensmittel (Fleisch, Fisch, Gemuese)
        // Das 3 Feld beinhaltet den Preis des Gebindes
        // Das 4 Feld beinhaltet die vorhandene Menge des Haendlers

        for (int i = 1; i <= lines.size() - 1; i++) {

            // Der String wird in einen BigDecimal umgewandelt
            sizeOfItemBundleBD = new BigDecimal(lines.get(i)[0]);
            unitOfItemBundle = lines.get(i)[1];
            nameOfIngredient = lines.get(i)[2];
            typeOfIngredient = lines.get(i)[3];
            // Komma werden zu Punkten, da Kommazahlen in Java mit Punkten getrennt werden
            priceOfIngredient = lines.get(i)[4].replace(",", ".");
            // Der String wird zu einem BigDecimal umgewandelt
            priceOfIngredientBD = new BigDecimal(priceOfIngredient);
            availableQuantityOfIngredient = new BigDecimal(lines.get(i)[5]);

            // Price wird erzeugt mit der Einheit Euro
            price = new Amount(priceOfIngredientBD, UnitOfMeasurement.EUR);

            // Variable ignt ist vom Typ IngredientType, diese repraesentiert den Typ eines Lebensmittel
            IngredientType ingt = null;
            // Aufruf der Klassenmethode typeOfIngredient um den richtigen Lebensmittel Typ zu setzen
            ingt = typeOfIngredient(typeOfIngredient);
            // Erzeugen eines Lebensmittel mit dazugehoerigen Lebensmitteltyp
            ingredient = new Ingredient(nameOfIngredient, ingt);

            // Aufruf der Klassenmethode unitOfMeasurement um den korekten Mengenanagben Typ zu setzen
            UnitOfMeasurement uom = unitOfMeasurement(unitOfItemBundle);
            // Size wird erzeugt mit der dazugehoerigen Mengeneinheit
            size = new Amount(sizeOfItemBundleBD, uom);

            // Eine Preislistenposition wird erzeugt
            priceListItem = new PriceListItem(size, ingredient, price, availableQuantityOfIngredient);

            // Die erzeugte Preislistenposition dem Set hinzufuegen
            priceList.add(priceListItem);
        }

        // Abfragen um was fuer eine Art Haendler es sich handelt um die Einheit des Transport zu setzen
        // Einen Haendler erzeugen mit Name, der Preisliste und den Transportkosten

        if (typeOfTrader.equals(INPUT_DATA_TYPE_OF_TRADER_WHOLESALER)) {
            Amount transportFee = new Amount(transportUnitBD, UnitOfMeasurement.EUR);
            Wholesaler wholesaler = new Wholesaler(nameOfTrader, priceList, transportFee);
            providerBase.addProvider(wholesaler);
        }
        else if (typeOfTrader.equals(INPUT_DATA_TYPE_OF_TRADER_FARMER)) {
            Amount totalDistance = new Amount(transportUnitBD, UnitOfMeasurement.KMR);
            Farmer farmer = new Farmer(nameOfTrader, priceList, totalDistance);
            providerBase.addProvider(farmer);
        }

        return providerBase;
    }

    /**
     * Methode um eine Rezepte Datei einzulesen
     * 
     * @author Francesco Luciano
     * @param inputFileURL URL der einzulesenden Datei
     * @return RecipeBase
     * @throws IOException
     */

    public static RecipeBase readRecipeList(URL inputFileURL) throws IOException {

        // s.o
        List<String[]> lines = new ArrayList<String[]>();

        // Die Variable nameOfRecipe speichert den Namen des Rezept
        String nameOfRecipe = null;
        // Die Variable quantityOfIngredient speichert die Menge des Lebensmittel
        String quantityOfIngredient = null;
        // Die Variable unitOfQuantityFromIngredient speichert die Einheit der Menge des Lebensmittel
        String unitOfQuantityFromIngredient = null;
        // Die Variable nameOfIngredient speichert den Namen des Lebensmittel
        String nameOfIngredient = null;

        // Instanz der Datentraegerklasse RecipeBase
        RecipeBase recipeBase = new RecipeBase();

        /* Eine Map enthaelt Objekte in einer strukturierten Form. Eine Map ist wie ein Woerterbuch aufgebaut.
         * Jeder Eintrag besteht aus einem Schluessel (key) und dem zugehoerigen Wert (value).
         * Es koennen beliebige Objekte hinzugefuegt oder entfernt werden.
         * Jeder Schluessel darf in einer Map nur genau einmal vorhanden sein,
         * wodurch jedes Schluessel-Wert-Paar einzigartig ist. */

        // Die Map wird hier als Rezeptbuch benutzt, der einzigartige Key ist der Name des Rezept
        // der Value ist eine Liste von Positionen des Rezept

        Map<String, List<RecipeListItem>> recipeGroupingMap = new HashMap<String, List<RecipeListItem>>();

        // s.o
        InputFileHandler csv = new InputFileHandler(inputFileURL, ',');
        while (csv.hasMoreLines()) {
            lines.add(csv.nextLine());
        }

        // Die Laufvariable i laeuft die Elemnte des Lists ab
        // Jedes Element ist ein String[] mit vier Feldern
        // Das 0 Feld beinhaltet den Namen des Rezept
        // Das 1 Feld beinhaltet die Mengenangabe des Lebensmittel
        // Das 2 Feld beinhaltet die Einheit der Mengenanabe (Stueck, g,)
        // Das 3 Feld beinhaltet den Namen des Lebensmittel

        for (int i = 0; i <= lines.size() - 1; i++) {
            nameOfRecipe = lines.get(i)[0];
            // Komma werden zu Punkten, da Kommazahlen in Java mit Punkten getrennt werden
            quantityOfIngredient = lines.get(i)[1].replace(",", ".");
            unitOfQuantityFromIngredient = lines.get(i)[2];
            nameOfIngredient = lines.get(i)[3];

            // Die Implmentierung der Map wird hier benutzt um keine doppelten Rezepte abzuspeicher
            // und die Lebensmittel dem Rezept zuzuordnen.
            // Wenn die Map bereits einen Schluessel mit dem Namen des Rezept hat
            // wird der Liste eine neue Rezept Position hinzugefuegt.
            if (recipeGroupingMap.containsKey(nameOfRecipe)) {
                recipeGroupingMap.get(nameOfRecipe).add(
                        new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
            }

            // Wenn die Map noch keinen Schluessel mit dem Namen des Rezept hat
            // wird eine neue Liste um die Rezeptpositionen abzulegen erzeugt.
            // Rezeptposition wird der Liste hinzugefuegt
            else {
                List<RecipeListItem> list = new ArrayList<RecipeListItem>();
                list.add(new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
                recipeGroupingMap.put(nameOfRecipe, list);
            }
        }

        // Erweitertes for um duch die Map zu iterieren
        // Variable vom typ entry vom Typ Entry speicher eine Map Element ab
        // Mit der Map Methode entrySet() wird ein Map Element zurueckgegeben
        for (Entry<String, List<RecipeListItem>> entry : recipeGroupingMap.entrySet()) {

            // Erzeugung eines Rezept mit dem Namen des Rezept (Key des Map Element)
            Recipe recipe = new Recipe(entry.getKey());

            // TODO(Francesco Luciano) Diese Zeile Code von Tim erklaeren lassen
            recipe.setIngredientList(new HashSet<IngredientListItem>());

            // Erweitertes for um duch die Liste zu iterieren
            // Mit der Map Methode getValue() wird ein der Value des Map Element zurueckgegeben
            for (RecipeListItem recipeListItem : entry.getValue()) {

                // Diese Varaible speichert die Einheit der Mengenangabe als UnitOfMeasurement
                UnitOfMeasurement uom = null;
                // Menge des Lebensmittel abfragen und als BigDecimal in der Variable value speichern
                BigDecimal value = new BigDecimal(recipeListItem.getQuantityOfIntredient());

                // Aufruf der Klassenmethode unitOfMeasurement um den Typ der Masseinheit abzufragen
                uom = unitOfMeasurement(recipeListItem.getUnit());

                // Erzeugen eines Lebensmittel fuer ein Rezept und hinzufuegen zur Liste
                // TODO(Francesco Luciano) Diese Zeile Code von Tim erklaeren lassen
                recipe.getIngredientList().add(
                        new IngredientListItem(new Ingredient(recipeListItem.getNameOfIngredient()), new Amount(value,
                                uom)));
            }

            // Rezept in Datentraegerklasse speichern
            recipeBase.addRecipe(recipe);

        }

        return recipeBase;
    }

    /**
     * Methode die ein Rezept mit dem Rang aus der Hitliste setzt
     * 
     * @param recipeBase sind die eingelesenen Rezepte
     * @param hitListBase sind die eigelesenen Gerichte der Hitliste
     */
    public static void addRankToRecipes(RecipeBase recipeBase, HitListBase hitListBase) {

        // Iteartion durch das Set welches die eingelesenen Rezpte enthaelt
        for (Recipe r : recipeBase.getRecipes()) {

            // Gericht in der HitListBase finden das den selben Namen wie das aktuelle Gericht in der RecipeBase hat.
            // Das gefunde Geicht aus der HitListBase auf eine Variable vom Typ HitListItem zur weiteren verarbeitung
            // zwischen speichern.
            HitListItem hitListItem = hitListBase.findHitListItemByName(r.getName());

            // Innere KLassen-Methode von HitListItem ruft den Rang von dem zwischengespeicherten Gericht aus der
            // HitListBase ab -> Result ist ein int. Das Ergebnis (int) direkt mit der
            // Klassen-Methode setRank von Recipe den Rang des Rezept zu setzen */
            if (hitListItem != null) {
                r.setRank(hitListItem.getRank());
            }
        }
    }

    /**
     * Methode die alle {@link Recipe} sowie deren {@link IngredientListItem} durchlaeuft und danach in der
     * {@link ProviderBase} nach dem entsprechenden {@link Ingredient} sucht, um den {@link IngredientType} zu
     * ermitteln. Danach wird der gefundene {@link IngredientType} in das {@link IngredientListItem} des {@link Recipe}
     * gesetzt. Dies muss erfolgen, da in der Rezepte Eingangsdatei keine Typen definiert sind, sondern nur in den
     * Listen der Anbieter.
     * 
     * @param recipeBase
     * @param providerBase
     */
    public static void addIngredientTypeToIngredientsOfRecipes(RecipeBase recipeBase, ProviderBase providerBase) {
        for (Recipe r : recipeBase.getRecipes()) {
            for (IngredientListItem ili : r.getIngredientList()) {
                IngredientType ingredientType = providerBase.findIngredientTypeByIngredientName(ili.getIngredient()
                        .getName());
                ili.getIngredient().setIngredientType(ingredientType);
            }
        }
    }

    /**
     * In den Rezepten und Preislisten sind Masseinheiten als String angeben, diese Strings sollen
     * in Variablen vom Typ UnitOfMeasurement umgewandelt werden. Um keine keinen redundanten Quellcode in den
     * Methoden {@link readPriceList} und {@link readRecipeList} zu erzeugen, wird das Vergleichen und erzeugen der
     * Variable vom Typ UnitOfMeasurement mit dem passenden ENUM diese externe Methode ausgelagert.
     * 
     * @param inputUnit String Masseinheit der aus der Datei eingelesen wurde
     * @return uom Variable vom Typ UnitOfMeasurement mit richtigem ENUM Typ
     */
    private static UnitOfMeasurement unitOfMeasurement(String inputUnit) {

        UnitOfMeasurement uom = null;

        // Vergleich des String um die richtige Einheit zu setzen

        if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_GRAMM)) {
            uom = UnitOfMeasurement.GRM;
        }

        else if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_LITER)) {
            uom = UnitOfMeasurement.LTR;
        }

        else if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_PICES)) {
            uom = UnitOfMeasurement.STK;
        }

        return uom;
    }

    /**
     * In den Rezepten und Preislisten sind Lebensmittel als String angeben, diese Strings sollen
     * in Variablen vom Typ IngredientType umgewandelt werden. Um keinen redundanten Quellcode in den
     * Methoden {@link readPriceList} und {@link readRecipeList} zu erzeugen, wird das Vergleichen und erzeugen der
     * Variable vom Typ IngredientType mit dem passenden ENUM diese externe Methode ausgelagert.
     * 
     * @param inputUnit String Masseinheit der aus der Datei eingelesen wurde
     * @return uom Variable vom Typ UnitOfMeasurement mit richtigem ENUM Typ
     */
    private static IngredientType typeOfIngredient(String typeOfIngredient) {

        IngredientType ingt = null;

        // Vergleich des String um die richtige Typ zu setzen
        if (typeOfIngredient.equals(INPUT_DATA_MEAT)) {
            ingt = IngredientType.MEAT;
        }

        else if (typeOfIngredient.equals(INPUT_DATA_FISH)) {
            ingt = IngredientType.FISH;
        }

        else if (typeOfIngredient.equals(INPUT_DATA_VEGETERIAN)) {
            ingt = IngredientType.VEGETABLE;
        }

        return ingt;
    }

    /**
     * Eingebettete Klasse RecipeListItem ist eine Datentraegerklasse die eine Zeile aus der Datei der Rezepte
     * repraesentiert. Sie wird nur als zwischenspeicher gebraucht um ein Object vom Typ Recipe zu fuellen.
     */
    private static final class RecipeListItem {
        String quantityOfIngredient;
        String unitOfQuantity;
        String nameOfIngredient;

        public RecipeListItem(String quantityOfIntredient, String unit, String nameOfIngredient) {
            this.quantityOfIngredient = quantityOfIntredient;
            this.unitOfQuantity = unit;
            this.nameOfIngredient = nameOfIngredient;
        }

        /**
         * @return the quantityOfIntredient
         */
        public String getQuantityOfIntredient() {
            return quantityOfIngredient;
        }

        /**
         * @return the unit
         */
        public String getUnit() {
            return unitOfQuantity;
        }

        /**
         * @return the nameOfIngredient
         */
        public String getNameOfIngredient() {
            return nameOfIngredient;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((nameOfIngredient == null) ? 0 : nameOfIngredient.hashCode());
            result = prime * result + ((quantityOfIngredient == null) ? 0 : quantityOfIngredient.hashCode());
            result = prime * result + ((unitOfQuantity == null) ? 0 : unitOfQuantity.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            RecipeListItem other = (RecipeListItem) obj;
            if (nameOfIngredient == null) {
                if (other.nameOfIngredient != null)
                    return false;
            }
            else if (!nameOfIngredient.equals(other.nameOfIngredient))
                return false;
            if (quantityOfIngredient == null) {
                if (other.quantityOfIngredient != null)
                    return false;
            }
            else if (!quantityOfIngredient.equals(other.quantityOfIngredient))
                return false;
            if (unitOfQuantity == null) {
                if (other.unitOfQuantity != null)
                    return false;
            }
            else if (!unitOfQuantity.equals(other.unitOfQuantity))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "RecipeListItem [quantityOfIntredient=" + quantityOfIngredient + ", unit=" + unitOfQuantity + ", nameOfIngredient=" + nameOfIngredient + "]";
        }
    }

}
