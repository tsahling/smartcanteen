package de.osjava.smartcanteen.application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.osjava.smartcanteen.application.gui.ApplicationGUI;
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

    private static final String PROP_APPLICATION_START_GUI = PropertyHelper.getProperty("application.startGui");

    // Setze Werte ob beschriebenes Fileformat erzeugt werden soll
    // Auslesen der Werte aus externer Properties Datei und casten auf Wahrheitswert
    private static final boolean GENERATE_CSV = Boolean.parseBoolean(PropertyHelper
            .getProperty("outputData.generateCSV"));
    private static final boolean GENERATE_HTML = Boolean.parseBoolean(PropertyHelper
            .getProperty("outputData.generateHTML"));

    private HitListBase hitListBase;
    private RecipeBase recipeBase;
    private ProviderBase providerBase;

    private Canteen[] canteens;
    private ShoppingList shoppingList;
    private String inputFiles;

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
        inputFiles = EMPTY;

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
     * Diese Methode stellt dem User zur Auswahl ob die Anwendung in der Konsole
     * oder mit GUI-Unterstützung geladen werden soll
     * 
     * @throws IOException
     * @author Tim Sahling und Marcel Baxmann
     */
    private void startApplication() throws IOException {

        int startGui = 0;

        // Wenn Property für Application GUI Start nicht gesetzt ist, kommt eine Benutzerabfrage, ob die Software in der
        // Konsole ohne User-Interaktion (0) durchlaufen soll oder mit einer GUI (1) gestartet wird
        if (PROP_APPLICATION_START_GUI == null || PROP_APPLICATION_START_GUI.equals(EMPTY)) {
            startGui = JOptionPane
                    .showOptionDialog(
                            null,
                            PropertyHelper.getProperty("message.applicationStart.message"),
                            PropertyHelper.getProperty("message.applicationStart.title"),
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{ PropertyHelper.getProperty("message.applicationStart.option1"), PropertyHelper
                                    .getProperty("message.applicationStart.option2") }, PropertyHelper
                                    .getProperty("message.applicationStart.option2"));
        }
        // Wenn Property für Application GUI Start gesetzt ist, wird dieser Wert verwendet um abzufragen ob Applikation
        // mit GUI oder ohne gestartet werden soll
        else {
            if (Boolean.parseBoolean(PROP_APPLICATION_START_GUI)) {
                startGui = 1;
            }
        }

        // Wenn startGui wahr ist dann generiere Benutzeroberfläche
        if (startGui == 1) {
            ApplicationGUI applicationGUI = new ApplicationGUI(this);
            applicationGUI.initialize();
        }
        // Wenn startGui falsch ist dann führe Aktionen ohne Benutzereingaben aus
        else {
            // Start der Applikationslogik für die Erstellung der Speisepläne für die beiden Kantinen
            buildMenuPlan();

            // Start der Applikationslogik für die Erstellung der Einkaufsliste
            buildShoppingList();

            // Start der Ausgabelogik fuer die Ergebnisse der Applikationslogik ausgeben
            outputApplicationResult();
        }
    }

    /**
     * Start der Applikationslogik für die Erstellung der Speisepläne für die beiden Kantinen
     * erstellt ein Objekt der Klasse {@link MenuPLanBuilder} und erhaelt von diesem
     * das Objekt canteens zurueck
     * 
     * @param providerBase
     * @param recipeBase
     */
    public void buildMenuPlan() {
        MenuPlanBuilder mpb = new MenuPlanBuilder(providerBase, recipeBase);
        canteens = mpb.buildMenuPlan();
    }

    /**
     * Start der Applikationslogik für die Erstellung der Einkaufsliste
     * Erstellt ein Objekt der Klasse {@link ShoppingListBuilder} und erhaelt von diesem
     * das Objekt shoppingList zurueck
     * 
     * @param providerBase
     * @param canteens
     * @throws IOException
     */

    public void buildShoppingList() {
        ShoppingListBuilder slb = new ShoppingListBuilder(providerBase, canteens);
        shoppingList = slb.buildShoppingList();
    }

    /**
     * Methode in der die Ergebnisse der Builderklassen {@link MenuPLanBuilder} und {@link ShoppingListBuilder}
     * ausgegeben werden
     * 
     * @param shoppingList
     * @param canteens
     * @throws IOException
     * @author Marcel Baxmann
     */
    public void outputApplicationResult() throws IOException {

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

    public HitListBase getHitListBase() {
        return hitListBase;
    }

    public RecipeBase getRecipeBase() {
        return recipeBase;
    }

    public ProviderBase getProviderBase() {
        return providerBase;
    }

    public Canteen[] getCanteens() {
        return canteens;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public String getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(String inputFiles) {
        this.inputFiles = inputFiles;
    }
}
