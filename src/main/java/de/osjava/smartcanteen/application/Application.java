package de.osjava.smartcanteen.application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.osjava.smartcanteen.base.HitListBase;
import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.MenuPlanBuilder;
import de.osjava.smartcanteen.builder.ShoppingListBuilder;
import de.osjava.smartcanteen.builder.result.ShoppingList;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.helper.BaseHelper;
import de.osjava.smartcanteen.helper.LogHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;
import de.osjava.smartcanteen.output.FileOutput;
import de.osjava.smartcanteen.output.HTMLOutput;

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
 * @author Franceso Luciano und Tim Sahling und Marcel Baxmann
 */
public class Application {

    private static final Logger LOG = LogHelper.getLogger(Application.class
            .getName());

    private static final String ARG_SPLIT = "=";
    private static final String VALUE_SPLIT = ";";
    private static final String EMPTY = "";

    private static final String INPUT_FILENAME_HITLIST = PropertyHelper.getProperty("inputFile.name.hitList");
    private static final String INPUT_FILENAME_PRICELIST = PropertyHelper.getProperty("inputFile.name.priceList");
    private static final String INPUT_FILENAME_RECIPELIST = PropertyHelper.getProperty("inputFile.name.recipeList");

    // Setze Werte ob beschriebenes Fileformat erzeugt werden soll
    // Auslesen der Werte aus externer Properties Datei und casten auf Wahrheitswert
    private static final boolean GENERATE_CSV = Boolean.parseBoolean(PropertyHelper
            .getProperty("outputData.generateCSV"));
    private static final boolean GENERATE_HTML = Boolean.parseBoolean(PropertyHelper
            .getProperty("outputData.generateHTML"));

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
            inputFiles = JOptionPane.showInputDialog(null,
                    PropertyHelper.getProperty("message.wrongOrMissingInputFiles.message"),
                    PropertyHelper.getProperty("message.wrongOrMissingInputFiles.title"), JOptionPane.QUESTION_MESSAGE);
        }
        else if (args.length == 1 && args[0].equals(PropertyHelper.getProperty("param.help"))) {
            System.out.println(PropertyHelper.getProperty("application.usageInfo"));
            return false;
        }
        else {
            for (String arg : args) {

                if (arg.contains(PropertyHelper.getProperty("param.inputFiles"))) {
                    inputFiles = setApplicationInputFilesFromArgs(arg.split(ARG_SPLIT));
                }
                else if (arg.contains(PropertyHelper.getProperty("param.properties"))) {
                    setApplicationParamsFromArgs(arg
                            .substring(PropertyHelper.getProperty("param.properties").length() + 1));
                }
            }
        }

        boolean wrongInputFile = false;

        if (inputFiles != null && !inputFiles.equals(EMPTY)) {
            String[] inputFileSplit = inputFiles.split(VALUE_SPLIT);

            if (inputFileSplit.length > 0) {

                for (String inputFile : inputFileSplit) {

                    URL inputFileUrl = ClassLoader.getSystemResource(PropertyHelper
                            .getProperty("application.inputFilePath") + inputFile);

                    if (inputFileUrl != null) {

                        String file = inputFileUrl.getFile();

                        if (file.contains(INPUT_FILENAME_HITLIST)) {
                            hitListBase = BaseHelper.readHitlist(inputFileUrl);
                        }
                        else if (file.contains(INPUT_FILENAME_PRICELIST)) {
                            providerBase = BaseHelper.readPriceList(inputFileUrl, providerBase);
                        }
                        else if (file.contains(INPUT_FILENAME_RECIPELIST)) {
                            recipeBase = BaseHelper.readRecipeList(inputFileUrl);
                        }
                    }
                    else {
                        wrongInputFile = true;
                        break;
                    }
                }

                if (!wrongInputFile && (validateHitListBase(hitListBase) && validateRecipeBase(recipeBase) && validateProviderBase(providerBase))) {
                    BaseHelper.addRankToRecipes(recipeBase, hitListBase);
                    BaseHelper.addIngredientTypeToIngredientsOfRecipes(recipeBase, providerBase);
                    return true;
                }
            }
        }

        throw new IllegalArgumentException(PropertyHelper.getProperty("message.wrongOrMissingInputFiles.exception"));
    }

    private boolean validateHitListBase(HitListBase hitListBase) {
        return hitListBase != null && hitListBase.getHitListItems() != null && !hitListBase.getHitListItems().isEmpty();
    }

    private boolean validateRecipeBase(RecipeBase recipeBase) {
        return recipeBase != null && recipeBase.getRecipes() != null && !recipeBase.getRecipes().isEmpty();
    }

    private boolean validateProviderBase(ProviderBase providerBase) {
        return providerBase != null && providerBase.getProvider() != null && !providerBase.getProvider().isEmpty();
    }

    /**
     * 
     * @param args
     * @param result
     */
    private String setApplicationInputFilesFromArgs(String[] args) {
        String result = null;

        if (args != null && args.length > 0) {

            if (args.length > 1) {
                result = args[1].trim().concat(VALUE_SPLIT);
            }
        }

        return result;
    }

    /**
     * 
     * @param args
     */
    private void setApplicationParamsFromArgs(String arg) {

        if (arg != null && arg.length() > 0) {

            String[] propertyArgs = arg.split(VALUE_SPLIT);

            if (propertyArgs != null && propertyArgs.length > 0) {

                for (String propertyArg : propertyArgs) {

                    String[] propertyArgSplit = propertyArg.split(ARG_SPLIT);

                    if (propertyArgSplit.length > 1) {
                        PropertyHelper.setProperty(propertyArgSplit[0], propertyArgSplit[1]);
                    }
                }
            }
        }
    }

    /**
     * @throws IOException
     * 
     */
    private void startApplication() throws IOException {

        // Start der Applikationslogik für die Erstellung der Speisepläne für die beiden Kantinen
        MenuPlanBuilder mpb = new MenuPlanBuilder(providerBase, recipeBase);
        Canteen[] canteens = mpb.buildMenuPlan();

        // Start der Applikationslogik für die Erstellung der Einkaufsliste
        ShoppingListBuilder slb = new ShoppingListBuilder(providerBase, canteens);
        ShoppingList shoppingList = slb.buildShoppingList();

        LOG.log(Level.INFO, shoppingList.calculateTotalPrice().toString());

        // Übergabe der Ergebnisse der Applikationslogik an die ausgebende Methode
        outputApplicationResult(shoppingList, canteens);
    }

    /**
     * Methode in der die Ergebnisse der Builderklassen {@link MenuPLanBuilder} und {@link ShoppingListBuilder}
     * ausgegeben werden
     * 
     * @param shoppingList
     * @param canteens
     * @throws IOException
     * @author Marcel
     */
    private void outputApplicationResult(ShoppingList shoppingList,
            Canteen... canteens) throws IOException {

        // Logging
        LOG.log(Level.INFO, "Beginne Aufbereitung Datenausgabe");

        // Erstellung Ausgabe-Objekt für CSV-Ausgabe
        FileOutput fileOutput = new FileOutput();
        // Erstellung Ausgabe-Objekt für HTML-Ausgabe
        HTMLOutput htmlOutput = new HTMLOutput();

        if (GENERATE_CSV) {
            // Aufruf der Methode zum Erzeugen eines Menueplans als CSV je existierender Kantine
            int x = 1;
            for (Canteen canteen : canteens) {
                LOG.log(Level.INFO, "CSV-Menüplan wird erzeugt für Kantine " + x);
                fileOutput.outputMenuPlan(canteen);
                x++;
            }

            // Aufruf der Methode zum Erzeugen einer Einkaufsliste als CSV
            LOG.log(Level.INFO, "CSV-Einkaufsliste wird erzeugt");
            fileOutput.outputShoppingList(shoppingList);

            // Aufruf der Methode zum Erzeugen einer Kostenuebersicht als CSV
            LOG.log(Level.INFO, "CSV-Kostenuebersicht wird erzeugt");
            fileOutput.outputTotalCosts(shoppingList);
        }
        else {
            LOG.log(Level.INFO, "CSV soll nicht erzeugt werden");
        }

        if (GENERATE_HTML) {
            // Aufruf der Methode zum Erzeugen eines Menueplans als HTML je existierender Kantine
            int x = 1;
            for (Canteen canteen : canteens) {
                LOG.log(Level.INFO, "HTML-Menüplan wird erzeugt für Kantine " + x);
                htmlOutput.outputMenuPlan(canteen);
                x++;
            }

            // Aufruf der Methode zum Erzeugen einer Einkaufsliste als CSV
            LOG.log(Level.INFO, "HTML-Einkaufsliste wird erzeugt");
            htmlOutput.outputShoppingList(shoppingList);

            // Aufruf der Methode zum Erzeugen einer Kostenuebersicht als CSV
            LOG.log(Level.INFO, "HTML-Kostenuebersicht wird erzeugt");
            htmlOutput.outputTotalCosts(shoppingList);
        }
        else {
            LOG.log(Level.INFO, "HTML soll nicht erzeugt werden");
        }

        LOG.log(Level.INFO, "Ausgabe erfolgreich abgeschlossen");

    }
}
