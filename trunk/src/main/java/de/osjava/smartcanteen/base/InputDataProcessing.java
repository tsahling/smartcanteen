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
 * 
 * TODO(Francesco Luciano) Möglichst viel Codeduplizierung vermeiden. Das Einlesen ist ja immer gleich, vielleicht kann
 * man da was in weiteren private Methoden zusammenfassen.
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
     * 
     * @param inputFileURL
     * 
     * @throws IOException
     * @author Francesco Luciano
     * @return
     */
    public HitListBase readHitlist(URL inputFileURL) throws IOException {

        Vector<String[]> lines = new Vector<String[]>();
        HitListBase hitlist = new HitListBase();

        Integer mealPlacement = null;
        String mealName = null;

        try {
            CSVTokenizer csv = new CSVTokenizer(inputFileURL, ',');

            while (csv.hasMoreLines()) {
                lines.add(csv.nextLine());
            }
            for (int i = 0; i <= lines.size() - 1; i++) {

                mealPlacement = Integer.valueOf(lines.get(i)[0]);
                mealName = lines.get(i)[1];
                HitListItem oneHitListItem = new HitListItem(mealName, mealPlacement);
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
     * Methode um eine Preisliste einzulesen
     * 
     * @author Francesco Luciano
     * @param inputFileURL
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

    public RecipeBase readRecipeList(URL inputFileURL) {

        Vector<String[]> lines = new Vector<String[]>();

        String nameOfRecipe = null;
        String quantityOfIngredient = null;
        String unitOfQuantityFromIngredient = null;
        String nameOfIngredient = null;

        RecipeBase recipeBase = new RecipeBase();

        Map<String, List<RecipeListItem>> grouping = new HashMap<String, List<RecipeListItem>>();

        try {
            CSVTokenizer csv = new CSVTokenizer(inputFileURL, ',');
            while (csv.hasMoreLines()) {
                // Zeile, einlesen
                lines.add(csv.nextLine());
            }

            for (int i = 0; i <= lines.size() - 1; i++) {
                nameOfRecipe = lines.get(i)[0];
                quantityOfIngredient = lines.get(i)[1].replace(",", ".");
                unitOfQuantityFromIngredient = lines.get(i)[2];
                nameOfIngredient = lines.get(i)[3];

                if (grouping.containsKey(nameOfRecipe)) {
                    grouping.get(nameOfRecipe).add(
                            new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
                }
                else {
                    List<RecipeListItem> list = new ArrayList<RecipeListItem>();
                    list.add(new RecipeListItem(quantityOfIngredient, unitOfQuantityFromIngredient, nameOfIngredient));
                    grouping.put(nameOfRecipe, list);
                }
            }

            for (Entry<String, List<RecipeListItem>> entry : grouping.entrySet()) {

                Recipe recipe = new Recipe(entry.getKey(), null, 0);
                recipe.setIngredientList(new HashSet<IngredientListItem>());

                for (RecipeListItem recipeListItem : entry.getValue()) {

                    UnitOfMeasurement uom = null;
                    BigDecimal value = new BigDecimal(recipeListItem.getQuantityOfIntredient());

                    uom = unitOfMeasurement(recipeListItem.getUnit());

                    recipe.getIngredientList().add(
                            new IngredientListItem(new Ingredient(recipeListItem.getNameOfIngredient(), null),
                                    new Amount(value, uom)));
                }

                recipeBase.addRecipe(recipe);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeBase;
    }

    private UnitOfMeasurement unitOfMeasurement(String inputUnit) {

        UnitOfMeasurement uom = null;

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

    private IngredientType typeOfIngredient(String typeOfIngredient) {

        IngredientType ingt = null;

        if (typeOfIngredient.equals(INPUT_DATA_MEAT)) {
            ingt = IngredientType.MEAT;
        }

        else if (typeOfIngredient.equals(INPUT_DATA_FISH)) {
            ingt = IngredientType.FISH;
        }
        /* Wenn das Feld typeIngredient leer ist, dann ist es Vegetarisch */
        else if (typeOfIngredient.equals(INPUT_DATA_VEGETERIAN)) {
            ingt = IngredientType.VEGETABLE;
        }

        return ingt;
    }

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
