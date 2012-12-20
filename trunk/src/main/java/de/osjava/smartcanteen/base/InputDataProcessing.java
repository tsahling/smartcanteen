package de.osjava.smartcanteen.base;

import java.io.FileNotFoundException;
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
import java.util.Vector;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import de.osjava.smartcanteen.application.CSVTokenizer;
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
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * 
 * TODO(Francesco Luciano) Dokumentieren
 * TODO(Francesco Luciano) Klassenbeschreibung JavaDoc
 * TODO(Francesco Luciano) Methodenbeschreibung JavaDoc
 * 
 */

/**
 * @author Francesco Luciano
 * 
 */
public class InputDataProcessing {

    /* Hier werden feste Werte aus einer externen Properties Datei ausgelesen und auf String Variablen gespeichert */

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
     * Methode um die Liste der beliebtesten Gerichte aus einer übergebenen Datei einzulesen.
     * 
     * @param inputFileURL Url der einzulesenden Datei
     * 
     * @throws IOException
     * @author Francesco Luciano
     * @return hitlist Java List mit Gerichten
     */
    public HitListBase readHitlist(URL inputFileURL) throws IOException {

        Vector<String[]> lines = new Vector<String[]>();

        /* Variable vom Typ HitListBase um die Gerichte abzuspeichern */
        HitListBase hitlist = new HitListBase();

        /* Variable die den Rang des Gerichtes speichert */
        Integer mealPlacement = null;
        /* Variable des den Namen des Gerichtes speichert */
        String mealName = null;

        /* Instanz vom Typ CSVTokenizer um die Datei einzulesen und zu verarbeiten */

        try {
            CSVTokenizer csv = new CSVTokenizer(inputFileURL, ',');

            /* Solange es Zeilen in der Datei gibt, werden diese in den Vector lines gespeichert */
            while (csv.hasMoreLines()) {
                lines.add(csv.nextLine());
            }

            /* Iteration durch den Vector */

            for (int i = 0; i <= lines.size() - 1; i++) {

                /* Der Rang des Gericht wird gesetzt */
                mealPlacement = Integer.valueOf(lines.get(i)[0]);
                /* Name des Gericht wird gesetzt */
                mealName = lines.get(i)[1];
                /* Neues HitlistItem wird erzeugt mit den vorher eingelesenen werten */
                HitListItem oneHitListItem = new HitListItem(mealName, mealPlacement);
                /* HitListItem wird der HitListBase hinzugefügt */
                hitlist.addHitListItem(oneHitListItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
    public ProviderBase readPriceList(URL inputFileURL, ProviderBase providerBase) throws IOException {

        Vector<String[]> lines = new Vector<String[]>();

        /* Deklaration eines Händlers */
        if (providerBase == null) {
            providerBase = new ProviderBase();
        }

        /* Typ des Händler zu speichern (Bauer oder Grosshändler) */
        String typeOfTrader = null;
        /* Name des Händler */
        String nameOfTrader = null;
        /* Bei Großhändlern die Transportkosten und bei Bauern die Entfernung */
        String transportCost = null;
        BigDecimal transportCostBD = null;

        /* Deklaration eines Lebensmittel */

        /* Größe des Gebindes */
        Integer sizeOfItemBundle = null;
        /* Einheit des Gebindes (Gramm, Stück oder Liter) */
        String unitOfItemBundle = null;
        /* Name des Lebensmittel (Gramm, Stück oder Liter) */
        String nameOfIngredient = null;
        /* Typ des Lebensmittel als String */
        String typeOfIngredient = null;
        /* Typ des Lebensmittel als IngredientType */
        IngredientType typeOfIngredientIT = null;
        /* Preis des Lebensmittel als String */
        String priceOfIngredient = null;
        /* Preis des Lebensmittel als Big Dicimal */
        BigDecimal priceOfIngredientBD = null;
        /* Einheit des Preises */
        Amount amountOfIngredientPrice;
        /* Vorhandene Menge des Lebensmittel */
        Integer existingQuantityOfIngredient = null;

        /* Instanz der Klasse Lebensmittel */
        Ingredient ingredient = null;
        /* Instanz der Klasse PriceListItem */
        PriceListItem priceListItem = null;
        /* Set um die Positionen der Preisliste zu speichern */
        Set<PriceListItem> priceList = new HashSet<PriceListItem>();

        try {
            CSVTokenizer csv = new CSVTokenizer(inputFileURL, ',');
            while (csv.hasMoreLines()) {
                // Zeile, einlesen
                lines.add(csv.nextLine());
            }

            /* Erste Zeile der CSV Datei einlesen */

            /* Typ des Händlers auslesen */
            typeOfTrader = lines.get(0)[0];
            /* Name des Händler einlesen */
            nameOfTrader = lines.get(0)[1];
            /* Transportkosten oder Entfernung Einlesen */
            transportCost = lines.get(0)[2].replace(",", ".");
            /* String in BigDecimal Konvertieren */
            transportCostBD = new BigDecimal(transportCost);

            /* Bei Zeile 2 Anfangen Lebensmittel auf Variablen aufteilen */
            for (int i = 1; i <= lines.size() - 1; i++) {

                /* Größe des Gebinde */
                sizeOfItemBundle = Integer.valueOf(lines.get(i)[0]);
                /* Art der Einheit */
                unitOfItemBundle = lines.get(i)[1];
                /* Name des Lebensmittel */
                nameOfIngredient = lines.get(i)[2];
                /* Typ des Lebensmittel */
                typeOfIngredient = lines.get(i)[3];
                /* Preis des Lebensmittel */
                priceOfIngredient = lines.get(i)[4].replace(",", ".");
                /* Preis in Big Decimal Konvertieren */
                priceOfIngredientBD = new BigDecimal(priceOfIngredient);
                /* Vorhandene Menge */
                existingQuantityOfIngredient = Integer.valueOf(lines.get(i)[5]);

                amountOfIngredientPrice = new Amount(priceOfIngredientBD, UnitOfMeasurement.EUR);

                /* Abfrage um was für ein Lebensmittel es sich handelt um den dazugehörigen IngredientType zu setzen */
                /* Erzeugung eines Lebensmittel vom Typ Ingredient mit dem dazugehörigen IngredientType */

                IngredientType ingt = null;

                ingt = typeOfIngredient(typeOfIngredient);

                ingredient = new Ingredient(nameOfIngredient, ingt);

                /* Abfrage um was für eine Maßeinheit es sich handelt */
                /* Erzeugung einer Preislisten Position vom Typ PriceListItem */

                UnitOfMeasurement uom = null;

                uom = unitOfMeasurement(unitOfItemBundle);

                priceListItem = new PriceListItem(ingredient, uom, amountOfIngredientPrice,
                        existingQuantityOfIngredient);

                /* Set mit Preislisten Positionen füllen */
                priceList.add(priceListItem);

            }

            /* Abfragen um was für einen Typ von Händler es sich handelt */
            /* Wenn es ein Grosshändler ist wird ein Wholesaler erzeugt */
            if (typeOfTrader.equals(INPUT_DATA_TYPE_OF_TRADER_WHOLESALER)) {
                Amount transportFee = new Amount(transportCostBD, UnitOfMeasurement.EUR);
                Wholesaler wholesaler = new Wholesaler(nameOfTrader, transportFee, priceList);
                providerBase.addProvider(wholesaler);
            }
            /* Wenn es ein Bauer ist wird ein Farmer erzeugt */
            else if (typeOfTrader.equals(INPUT_DATA_TYPE_OF_TRADER_FARMER)) {
                Amount totalDistance = new Amount(transportCostBD, UnitOfMeasurement.KMR);
                Farmer farmer = new Farmer(nameOfTrader, totalDistance, priceList);
                providerBase.addProvider(farmer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return providerBase;
    }

    /**
     * Methode um eine Rezepte Datei einzulesen
     * 
     * @author Francesco Luciano
     * @param inputFileURL URL der einzulesenden Datei
     * @return RecipeBase
     */

    public RecipeBase readRecipeList(URL inputFileURL) {

        Vector<String[]> lines = new Vector<String[]>();

        /* Name des Rezeptes -analog auch Name des Gericht- */
        String nameOfRecipe = null;
        /* Menge des Lebensmittel */
        String quantityOfIngredient = null;
        /* Masseinheit der Menge */
        String unitOfQuantityFromIngredient = null;
        /* Namen des Lebensmittel */
        String nameOfIngredient = null;

        /* Instanz von der Datenträgerklasse RecipeBase um die Rezepte abzulegen */
        RecipeBase recipeBase = new RecipeBase();

        /* Eine Map um die eingelesenen Zeilen nach Namen zu gruppieren */
        /* Die Hashmap wird mit einem String und einem Objekt vom Typ RecipeListItem gefüllt */
        Map<String, List<RecipeListItem>> grouping = new HashMap<String, List<RecipeListItem>>();

        try {
            CSVTokenizer csv = new CSVTokenizer(inputFileURL, ',');
            while (csv.hasMoreLines()) {
                // Zeile, einlesen
                lines.add(csv.nextLine());
            }

            /* Iteration durch den Vector der die eingelesenen Zeilen enthält */
            for (int i = 0; i <= lines.size() - 1; i++) {
                /* Name des Rezept wird aus dem Array ausgelesen und auf die Variable geschrieben */
                nameOfRecipe = lines.get(i)[0];
                /* Menge des Lebensmittel wird aus dem Array ausgelesen und auf die Variable geschrieben */
                quantityOfIngredient = lines.get(i)[1].replace(",", ".");
                /* Masseinheit des Lebensmittel wird aus dem Array ausgelesen und auf die Variable geschrieben */
                unitOfQuantityFromIngredient = lines.get(i)[2];
                /* Name des Lebensmittel wird aus dem Array ausgelesen und auf die Variable geschrieben */
                nameOfIngredient = lines.get(i)[3];

                /* Wenn es in der Hashmap bereits ein Key mit dem Wert nameOfRecipe gibt,
                 * dann hohle dir genau das Objekt und füge der Liste ein neues RecipeListItem hinzu. */
                if (grouping.containsKey(nameOfRecipe)) {
                    grouping.get(nameOfRecipe).add(
                            new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
                }

                /* Wenn die HashMap noch keinen Key mit dem Wert von nameOfRecipe besitzt,
                 * also ein neues Rezept in der Datei anfängt. Dann erzeuge eine neue Java Liste die RecipeListItem
                 * aufnimmt.
                 * Erzeuge ein RecipeListItem mit den ausgelesenen Werten zu Mengenangaben, Masseinheiten und Namen des
                 * Lebensmittel
                 * und füge es der ebend erzeugten Liste zu.
                 * Füge zur Hashmap grouping ein Element mit dem Key nameOfRecipe und der ebend erzeugten Liste mit
                 * Inhalt RecipeListItem hinzu. */
                else {
                    List<RecipeListItem> list = new ArrayList<RecipeListItem>();
                    list.add(new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
                    grouping.put(nameOfRecipe, list);
                }
            }

            /* links vom Doppelpunkt vom erweiterten for deklaration einer Variable vom Typ
             * Map.Entry-Objekt welche die Werte String und eine Liste mit RecipeListItem aufnehmen kann.
             * rechts vom doppelpunkt vom erweiterten for Aufruf der Map eigenen Methode entryset()
             * die ein Set mit Entry-Objekten zurückgibt, die Schlüssel sowie Wert gleichzeitig enthalten. */
            for (Entry<String, List<RecipeListItem>> entry : grouping.entrySet()) {

                /* Erzeugen eines neuen Rezept vom Typ Recipe */
                /* Setzen des Namen des Rezeptes durch entry.getKey() */
                /* Standartkonstruktor erwartet als zweiten Parameter den Rang des Gerichtes, zu diesem Zeitpunkt setzen
                 * wir den Rang auf 0. */
                /* TODO(Tim Sahling, Francesco Luciano) Sollte hier evt. der Standartkonstruktor von Recipe verändert
                 * werden oder ein
                 * weiterer erzeugt werden der kein rank erwartet */
                Recipe recipe = new Recipe(entry.getKey(), 0);
                /* Eine HashSet erzeugen und dem Objekt vom Typ Recipe übergeben */
                /* TODO(Francesco Luciano) Diese Zeile Code von Tim erklären lassen */
                recipe.setIngredientList(new HashSet<IngredientListItem>());

                /* Durch die Liste iterieren welche die Lebensmittel speichert */
                for (RecipeListItem recipeListItem : entry.getValue()) {

                    /* Variable zum um ide Masseinheit der Mengenangabe zu speichern */
                    UnitOfMeasurement uom = null;
                    /* Variable um die Menge des Lebensmittel als BigDecimal zu speichern */
                    BigDecimal value = new BigDecimal(recipeListItem.getQuantityOfIntredient());

                    /* Aufruf der Helfermethode unitOfMeasurement, der innere Methodenaufruf ist der eingelesene String
                     * der Masseinheit */
                    uom = unitOfMeasurement(recipeListItem.getUnit());

                    /* Erzeugung eines Objekt vom Typ IngredientListItem und hinzufügen zu der Liste */
                    /* TODO(Tim Sahling, Francesco Luciano) Wieso ist der Ingriedent Type hier null? */
                    recipe.getIngredientList().add(
                            new IngredientListItem(new Ingredient(recipeListItem.getNameOfIngredient(), null),
                                    new Amount(value, uom)));
                }

                /* Das Rezept der RecipeBase hinzufügen */
                recipeBase.addRecipe(recipe);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeBase;
    }

    /**
     * Methode die ein Rezept mit dem Rang aus der Hitliste bestückt.
     * 
     * @param recipeBase sind die eingelesenen Rezepte
     * @param hitListBase sind die eigelesenen Gerichte der Hitliste
     */
    public void addRankToRecipes(RecipeBase recipeBase, HitListBase hitListBase) {

        /* Iteartion durch das Set welches die eingelesenen Rezpte enthält */
        for (Recipe r : recipeBase.getRecipes()) {

            /* Gericht in der HitListBase finden das den selben Namen wie das aktuelle Gericht in der RecipeBase hat
             * Das gefunde Geicht aus der HitListBase auf eine Variable vom Typ HitListItem zur
             * weiteren verarbeitung zwischen speichern. */
            HitListItem hitListItem = hitListBase.findHitListItemByName(r.getName());
            /* Innere KLassen-Methode von HitListItem ruft den Rang von dem zwischengespeicherten Gericht
             * aus der HitListBase ab -> Result ist ein int */
            /* Äußere Methode verarbeitet das Ergebnis (int) direkt um mit der
             * Klassen-Methode setRank von Recipe den Rang des Rezept zu setzen */
            r.setRank(hitListItem.getRank());
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
    private UnitOfMeasurement unitOfMeasurement(String inputUnit) {

        /* Variablen deklaration */
        UnitOfMeasurement uom = null;

        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_UNIT_TYPE_GRAMM ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ GRM zugeordnet */

        if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_GRAMM)) {

            uom = UnitOfMeasurement.GRM;

        }
        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_UNIT_TYPE_LITER ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ LTR zugeordnet */
        else if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_LITER)) {
            uom = UnitOfMeasurement.LTR;
        }
        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_UNIT_TYPE_PICES ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ STK zugeordnet */
        else if (inputUnit.equals(INPUT_DATA_UNIT_TYPE_PICES)) {
            uom = UnitOfMeasurement.STK;
        }

        /* Rückgabe der Variablen */
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
    private IngredientType typeOfIngredient(String typeOfIngredient) {

        /* Variablen deklaration */
        IngredientType ingt = null;

        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_MEAT ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ MEAT zugeordnet */
        if (typeOfIngredient.equals(INPUT_DATA_MEAT)) {
            ingt = IngredientType.MEAT;
        }

        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_FISH ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ FISH zugeordnet */
        else if (typeOfIngredient.equals(INPUT_DATA_FISH)) {
            ingt = IngredientType.FISH;
        }

        /* Vergleich ob der die übergebene Variable gleich dem Inhalt der Konstante INPUT_DATA_VEGETERIAN ist */
        /* Wenn der Vergleich TRUE ist wird der Variable uom der ENUM Typ VEGETABLE zugeordnet */
        else if (typeOfIngredient.equals(INPUT_DATA_VEGETERIAN)) {
            ingt = IngredientType.VEGETABLE;
        }

        return ingt;
    }

    /**
     * Eingebettete Klasse RecipeListItem ist eine Datenträgerklasse die eine Zeile aus der Datei der Rezepte
     * repräsentiert. Sie wird nur als zwischenspeicher gebraucht um ein Object vom Typ Recipe zu füllen.
     * 
     * @author Francesco Luciano
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
