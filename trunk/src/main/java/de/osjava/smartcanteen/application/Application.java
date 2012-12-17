package de.osjava.smartcanteen.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import de.osjava.smartcanteen.base.HitListBase;
import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.MenuPlanBuilder;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Farmer;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Wholesaler;
import de.osjava.smartcanteen.data.item.HitListItem;
import de.osjava.smartcanteen.data.item.PriceListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.IngredientType;
import de.osjava.smartcanteen.datatype.UnitOfMeasurement;
import de.osjava.smartcanteen.helper.LogHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link Application} versetzt das Programm anhand der übergebenen
 * Aufparameter in einen initialen und konstanten Zustand. Dabei werden zu
 * Beginn die übergebenen Aufparameter (in Form von Dateinamen) validiert und
 * anhand der Klasse {@link CSVTokenizer} eingelesen sowie in die entsprechenden
 * Containerklassen übergeben.
 * 
 * Nach erfolgreicher Initialisierung startet die Klasse {@link Application} die
 * eigentliche Programmlogik und sorgt ganz am Ende des Ablaufs für die Ausgabe
 * der Ergebnisse.
 * 
 * @author Franceso Luciano und Tim Sahling
 */
public class Application {

    private static final Logger LOG = LogHelper.getLogger(Application.class
            .getName());

    private static final String ARG_SPLIT = "=";
    private static final String INPUT_FILE_SPLIT = ";";
    private static final String EMPTY = "";

    private static final String INPUT_FILENAME_HITLIST = PropertyHelper.getProperty("inputFile.name.hitList");
    private static final String INPUT_FILENAME_PRICELIST = PropertyHelper.getProperty("inputFile.name.priceList");
    private static final String INPUT_FILENAME_RECIPELIST = PropertyHelper.getProperty("inputFile.name.recipeList");

    private HitListBase hitListBase;
    private RecipeBase recipeBase;
    private ProviderBase providerBase;

    /**
     * Standardkonstruktor
     */
    public Application() {

    }

    /**
     * Versetzt die Anwendung in einen initialen und konstanten Zustand und ruft
     * nach erfolgreichem Einlesen der Eingabedaten die Logiken der
     * Applikationsroutine auf.
     * 
     * @author Tim Sahling
     * @param args
     *            Aufparameter der Applikation
     * @throws Exception
     *             Alle auftretenden Fehler in der Applikation
     */
    public void bootstrap(final String[] args) throws Exception {
        if (initInput(args)) {
            startApplication();
        }
    }

    /**
     * Liest die Eingabedaten ein und füllt die Datenträgerklassen
     * 
     * @param args
     * @throws IOException
     */
    private boolean initInput(final String[] args) throws IOException {
        String inputFiles = EMPTY;

        if (args.length == 0) {
            inputFiles = JOptionPane.showInputDialog(null, PropertyHelper
                    .getProperty("message.missingInputFiles.message"),
                    PropertyHelper
                            .getProperty("message.missingInputFiles.title"),
                    JOptionPane.QUESTION_MESSAGE);
        }
        else if (args.length == 1
                && args[0].equals(PropertyHelper.getProperty("param.help"))) {
            System.out.println(PropertyHelper
                    .getProperty("application.usageInfo"));
            return false;
        }
        else {
            for (String arg : args) {

                if (arg.contains(PropertyHelper.getProperty("param.inputFiles"))) {
                    String[] argSplit = arg.split(ARG_SPLIT);

                    if (argSplit.length > 1) {
                        inputFiles = inputFiles.concat(argSplit[1].trim())
                                .concat(INPUT_FILE_SPLIT);
                    }
                }
            }
        }

        boolean wrongInputFile = false;

        if (inputFiles != null && !inputFiles.equals(EMPTY)) {
            String[] inputFileSplit = inputFiles.split(INPUT_FILE_SPLIT);

            if (inputFileSplit.length > 0) {
                for (String inputFile : inputFileSplit) {

                    URL inputFileUrl = ClassLoader
                            .getSystemResource(PropertyHelper
                                    .getProperty("application.inputFilePath")
                                    + inputFile);

                    if (inputFileUrl != null) {

                        String file = inputFileUrl.getFile();

                        if (file.contains(INPUT_FILENAME_HITLIST)) {
                            hitListBase = readHitlist(inputFileUrl);
                        }
                        else if (file.contains(INPUT_FILENAME_PRICELIST)) {
                            providerBase = readPriceList(inputFileUrl);
                        }
                        else if (file.contains(INPUT_FILENAME_RECIPELIST)) {
                            // TODO(Francesco Luciano) Einlesen Rezepte in RecipeBase
                        }
                    }
                    else {
                        wrongInputFile = true;
                        break;
                    }
                }

                if (!wrongInputFile) {
                    return true;
                }
            }
        }

        throw new IllegalArgumentException(
                PropertyHelper
                        .getProperty("message.missingInputFiles.exception"));
    }

    /**
     * 
     */
    private void startApplication() {

        System.out.println(hitListBase.getHitListItems().size());

        MenuPlanBuilder mpb = new MenuPlanBuilder(providerBase, recipeBase);

        outputApplicationResult(null, null);
    }

    /**
     * 
     * @param shoppingList
     * @param canteens
     */
    private void outputApplicationResult(ShoppingList shoppingList,
            Canteen... canteens) {
        // TODO(Marcel Baxmann) Ergebnisse der Applikationslogik mit
        // Output-Klassen verarbeiten
    }

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
    public ProviderBase readPriceList(URL inputFileURL) throws IOException {

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
        Integer itemSize = null;
        /* Einheit des Gebindes (Gramm, Stück oder Liter) */
        String unit = null;
        /* Name des Lebensmittel (Gramm, Stück oder Liter) */
        String nameIngredient = null;
        /* Typ des Lebensmittel als String */
        String typeIngredient = null;
        /* Typ des Lebensmittel als IngredientType */
        IngredientType type = null;
        /* Preis des Lebensmittel als String */
        String price = null;
        /* Preis des Lebensmittel als Big Dicimal */
        BigDecimal priceBG = null;
        /* Einheit des Preises */
        Amount ingredientPrice;
        /* Vorhandene Menge des Lebensmittel */
        Integer existingQuantity = null;

        /* Instanz der Klasse Lebensmittel */
        Ingredient ingredient = null;
        /* Instanz der Klasse PriceListItem */
        PriceListItem priceListItem = null;
        /* Set um die Positionen der Preisliste zu speichern */
        Set<PriceListItem> priceList = null;

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
                itemSize = Integer.valueOf(lines.get(i)[0]);
                /* Art der Einheit */
                unit = lines.get(i)[1];
                /* Name des Lebensmittel */
                nameIngredient = lines.get(i)[2];
                /* Typ des Lebensmittel */
                typeIngredient = lines.get(i)[3];
                /* Preis des Lebensmittel */
                price = lines.get(i)[4].replace(",", ".");
                /* Preis in Big Decimal Konvertieren */
                priceBG = new BigDecimal(price);
                /* Vorhandene Menge */
                existingQuantity = Integer.valueOf(lines.get(i)[5]);

                ingredientPrice = new Amount(priceBG, UnitOfMeasurement.EUR);

                /* Abfrage um was für ein Lebensmittel es sich handelt um den dazugehörigen IngredientType zu setzen */
                /* Erzeugung eines Lebensmittel vom Typ Ingredient mit dem dazugehörigen IngredientType */

                if (typeIngredient.equals("m")) {
                    ingredient = new Ingredient(nameIngredient, IngredientType.MEAT);
                }

                if (typeIngredient.equals("f")) {
                    ingredient = new Ingredient(nameIngredient, IngredientType.FISH);
                }
                /* Wenn das Feld typeIngredient leer ist, dann ist es Gemüse */
                if (typeIngredient.equals("")) {
                    ingredient = new Ingredient(nameIngredient, IngredientType.VEGETABLE);
                }

                /* Abfrage um was für eine Maßeinheit es sich handelt */
                /* Erzeugung einer Preislisten Position vom Typ PriceListItem */
                if (unit.equals("g")) {

                    priceListItem = new PriceListItem(ingredient, UnitOfMeasurement.GRM, ingredientPrice,
                            existingQuantity);

                }
                if (unit.equals("l")) {

                    priceListItem = new PriceListItem(ingredient, UnitOfMeasurement.LTR, ingredientPrice,
                            existingQuantity);

                }
                /* Wenn das Feld unit leer ist, dann handelt es sich um Stück */
                if (unit.equals("")) {

                    priceListItem = new PriceListItem(ingredient, UnitOfMeasurement.STK, ingredientPrice,
                            existingQuantity);

                }

                /* Set mit Preislisten Positionen füllen */
                priceList.add(priceListItem);

            }

            /* Abfragen um was für einen Typ von Händler es sich handelt */

            /* Wenn es ein Grosshändler ist wird ein Wholesaler erzeugt */
            if (typeOfTrader.equals("Grosshandel")) {
                Amount transportFee = new Amount(transportCostBD, UnitOfMeasurement.EUR);
                Wholesaler wholesaler = new Wholesaler(nameOfTrader, transportFee, priceList);
                providerBase.addProvider(wholesaler);
            }
            else if (typeOfTrader.equals("Bauer")) { /* Wenn es ein Bauer ist wird ein Farmer erzeugt */
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
}
