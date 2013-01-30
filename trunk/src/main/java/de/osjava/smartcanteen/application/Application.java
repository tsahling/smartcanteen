package de.osjava.smartcanteen.application;

import java.io.File;
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
 * Die Klasse {@link Application} versetzt das Programm anhand der übergebenen Aufparameter in einen initialen und
 * konstanten Zustand. Dabei werden zu Beginn die übergebenen Aufparameter (in Form von Dateinamen) validiert und anhand
 * der Klasse {@link InputFileHandler} eingelesen sowie in die entsprechenden Datenträgerklassen übergeben. Nach
 * erfolgreicher Initialisierung startet die Klasse {@link Application} die eigentliche Programmlogik und sorgt ganz am
 * Ende des Ablaufs für die Ausgabe der Ergebnisse.
 * 
 * @author Franceso Luciano und Tim Sahling und Marcel Baxmann
 */
public class Application {

    private static final Logger LOG = LogHelper.getLogger(Application.class
            .getName());

    private static final String ARG_SPLIT = "=";
    private static final String VALUE_SPLIT = ";";
    private static final String EMPTY = "";

    private static final String PROP_INPUTFILE_NAME_HITLIST = PropertyHelper.getProperty("inputFile.name.hitList");
    private static final String PROP_INPUTFILE_NAME_PRICELIST = PropertyHelper.getProperty("inputFile.name.priceList");
    private static final String PROP_INPUTFILE_NAME_RECIPELIST = PropertyHelper
            .getProperty("inputFile.name.recipeList");

    private static final String PROP_PARAM_HELP = PropertyHelper.getProperty("param.help");
    private static final String PROP_PARAM_INPUTFILES = PropertyHelper.getProperty("param.inputFiles");
    private static final String PROP_PARAM_PROPERTIES = PropertyHelper.getProperty("param.properties");

    private static final String PROP_APPLICATION_INPUTFILEPATH = PropertyHelper
            .getProperty("application.inputFilePath");
    
    private static final String PROP_APPLICATION_USAGEINFO = PropertyHelper.getProperty("application.usageInfo");

    private static final String PROP_MESSAGE_WRONGORMISSINGINPUTFILES_TITLE = PropertyHelper
            .getProperty("message.wrongOrMissingInputFiles.title");
    private static final String PROP_MESSAGE_WRONGORMISSINGINPUTFILES_MSG = PropertyHelper
            .getProperty("message.wrongOrMissingInputFiles.message");
    private static final String PROP_MESSAGE_WRONGORMISSINGINPUTFILES_EXCEPTION = PropertyHelper
            .getProperty("message.wrongOrMissingInputFiles.exception");

    private static final String PROP_MESSAGE_APPLICATIONSTART_MSG = PropertyHelper
            .getProperty("message.applicationStart.message");
    private static final String PROP_MESSAGE_APPLICATIONSTART_TITLE = PropertyHelper
            .getProperty("message.applicationStart.title");
    private static final String PROP_MESSAGE_APPLICATIONSTART_OPTION1 = PropertyHelper
            .getProperty("message.applicationStart.option1");
    private static final String PROP_MESSAGE_APPLICATIONSTART_OPTION2 = PropertyHelper
            .getProperty("message.applicationStart.option2");

    private HitListBase hitListBase;
    private RecipeBase recipeBase;
    private ProviderBase providerBase;

    private Canteen[] canteens;
    private ShoppingList shoppingList;
    private String inputFiles;
    
    private int startGui = 0;
    
    /**
     * Versetzt die Anwendung in einen initialen und konstanten Zustand und ruft nach erfolgreichem Einlesen der
     * Eingabedaten die Logiken der Applikationsroutine auf.
     * 
     * @param args Aufparameter der Applikation
     * @throws Exception Alle auftretenden Fehler in der Applikation
     */
    public void bootstrap(final String[] args) throws Exception {

        if (initInputArgs(args)) {

            boolean fillBases = fillBases();

            // Start der Applikation soll nur erfolgen wenn die Methode fillBases erfolgreich durchgelaufen ist, oder
            // sie nicht erfolgreich durchgelaufen ist und gleichzeitig ein GUI-Start vorgesehen ist
            if (fillBases || (!fillBases && this.startGui == 1)) {
                startApplication();
            }
            else {
                throw new IllegalArgumentException(PROP_MESSAGE_WRONGORMISSINGINPUTFILES_EXCEPTION);
            }
        }
    }

    /**
     * Verarbeitet die Aufparameter der Applikation.
     * 
     * @param args Aufparameter der Applikation
     * @return wahr/falsch, je nachdem ob die Verarbeitung erfolgreich war
     */
    private boolean initInputArgs(final String[] args) {

    	if(args.length == 0) {
            this.startGui = JOptionPane.showOptionDialog(null, PROP_MESSAGE_APPLICATIONSTART_MSG, PROP_MESSAGE_APPLICATIONSTART_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{ PROP_MESSAGE_APPLICATIONSTART_OPTION1, PROP_MESSAGE_APPLICATIONSTART_OPTION2 }, PROP_MESSAGE_APPLICATIONSTART_OPTION2);
    	}
    	
    	if (args.length == 1 && args[0].equals(PROP_PARAM_HELP)) {
            System.out.println(PROP_APPLICATION_USAGEINFO);
            return false;   		
    	}
    	
    	boolean inputFiles = false;
    	
    	for(String arg : args) {
    		
            if (arg.contains(PROP_PARAM_INPUTFILES)) {
                this.inputFiles = setApplicationInputFilesFromArgs(arg.split(ARG_SPLIT));
                inputFiles = true;
            }
            else if (arg.contains(PROP_PARAM_PROPERTIES)) {
                setApplicationParamsFromArgs(arg.substring(PROP_PARAM_PROPERTIES.length() + 1));
            }
    		
    	}
    	
    	if(getPropertyAppliationStartGui() != null && !getPropertyAppliationStartGui().equals(EMPTY)) {
    		if(isApplicationStartGui()) {
    			this.startGui = 1;
    		}   		
    	}
    	
    	if(!inputFiles) {
            if (this.startGui == 0) {
                this.inputFiles = JOptionPane.showInputDialog(null, PROP_MESSAGE_WRONGORMISSINGINPUTFILES_MSG, PROP_MESSAGE_WRONGORMISSINGINPUTFILES_TITLE, JOptionPane.QUESTION_MESSAGE);
            }
    	}

        return true;
    }

    /**
     * Füllt den String für die Eingabedaten auf Basis der übergebenen Aufparameter.
     * 
     * @param args Aufparameter für die Eingabedaten (z.B. ABC.csv;DEF.csv;)
     * @return String für die Eingabedaten
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
     * Setzt die Properties der Applikation auf Basis der übergebenen Aufparameter.
     * 
     * @param arg Aufparameter für die zu setzenden Properties
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
     * Füllt die Datenträgerklassen auf Basis der Eingabedaten.
     * 
     * @return wahr/falsch, je nachdem ob die Verarbeitung funktioniert
     * @throws IOException Alle auftretenden IO-Fehler in der Verarbeitung der Methode
     */
    public boolean fillBases() throws IOException {
        boolean wrongInputFile = false;

        cleanUp();

        if (this.inputFiles != null && !this.inputFiles.isEmpty()) {
            String[] inputFileSplit = this.inputFiles.split(VALUE_SPLIT);

            if (inputFileSplit.length > 0) {

                for (String inputFile : inputFileSplit) {

                    URL inputFileUrl = null;

                    // Aus dem String inputFile inkl. des input-Pfades wird versucht eine Datei zu instanziieren. Wenn
                    // diese Datei im Dateisystem existiert, wird diese als Input genommen.
                    File file = new File(PROP_APPLICATION_INPUTFILEPATH + inputFile);

                    if (file.exists()) {
                        inputFileUrl = file.toURI().toURL();
                    }

                    if (inputFileUrl != null) {

                        String fileName = inputFileUrl.getFile();

                        if (fileName.contains(PROP_INPUTFILE_NAME_HITLIST)) {
                            this.hitListBase = BaseHelper.readHitlist(inputFileUrl);
                        }
                        else if (fileName.contains(PROP_INPUTFILE_NAME_PRICELIST)) {
                            this.providerBase = BaseHelper.readPriceList(inputFileUrl, this.providerBase);
                        }
                        else if (fileName.contains(PROP_INPUTFILE_NAME_RECIPELIST)) {
                            this.recipeBase = BaseHelper.readRecipeList(inputFileUrl);
                        }
                    }
                    else {
                        wrongInputFile = true;
                        break;
                    }
                }

                if (!wrongInputFile && (validateHitListBase(this.hitListBase) && validateRecipeBase(this.recipeBase) && validateProviderBase(this.providerBase))) {
                    BaseHelper.addRankToRecipes(this.recipeBase, this.hitListBase);
                    BaseHelper.addIngredientTypeToIngredientsOfRecipes(this.recipeBase, this.providerBase);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Leeren, löschen und deinstanzieren aller erzeugten Objekte.
     */
    private void cleanUp() {
        this.hitListBase = null;
        this.providerBase = null;
        this.recipeBase = null;
        this.canteens = null;
        this.shoppingList = null;
    }

    /**
     * Validiert die Datenträgerklasse {@link HitListBase}.
     * 
     * @param hitListBase Die Datenträgerklasse {@link HitListBase}
     * @return wahr/falsch, je nachdem ob die Datenträgerklasse {@link HitListBase} richtig gefüllt wurde
     */
    private boolean validateHitListBase(HitListBase hitListBase) {
        return hitListBase != null && hitListBase.getHitListItems() != null && !hitListBase.getHitListItems().isEmpty();
    }

    /**
     * Validiert die Datenträgerklasse {@link RecipeBase}.
     * 
     * @param recipeBase Die Datenträgerklasse {@link RecipeBase}
     * @return wahr/falsch, je nachdem ob die Datenträgerklasse {@link RecipeBase} richtig gefüllt wurde
     */
    private boolean validateRecipeBase(RecipeBase recipeBase) {
        return recipeBase != null && recipeBase.getRecipes() != null && !recipeBase.getRecipes().isEmpty();
    }

    /**
     * Validiert die Datenträgerklasse {@link ProviderBase}.
     * 
     * @param providerBase Die Datenträgerklasse {@link ProviderBase}
     * @return wahr/falsch, je nachdem ob die Datenträgerklasse {@link ProviderBase} richtig gefüllt wurde
     */
    private boolean validateProviderBase(ProviderBase providerBase) {
        return providerBase != null && providerBase.getProvider() != null && !providerBase.getProvider().isEmpty();
    }

    /**
     * Diese Methode stellt dem User zur Auswahl ob die Anwendung in der Konsole
     * oder mit GUI-Unterstützung geladen werden soll
     * 
     * @throws IOException
     *             Alle auftretenden IO-Fehler in der Verarbeitung der Methode
     */
    private void startApplication() throws IOException {

        // Wenn startGui wahr ist dann generiere Benutzeroberfläche
        if (this.startGui == 1) {
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

    private String getPropertyAppliationStartGui() {
    	return PropertyHelper.getProperty("application.startGui");
    }
    
    private boolean isApplicationStartGui() {
    	return Boolean.parseBoolean(getPropertyAppliationStartGui());
    }
    
    /**
     * Start der Applikationslogik für die Erstellung der Speisepläne. Erstellt ein Objekt der Klasse
     * {@link MenuPlanBuilder} und erhält von dieser ein Array von {@link Canteen} zurück.
     * 
     */
    public void buildMenuPlan() {
        MenuPlanBuilder mpb = new MenuPlanBuilder(this.providerBase, this.recipeBase);
        this.canteens = mpb.buildMenuPlan();
    }

    /**
     * Start der Applikationslogik für die Erstellung der Einkaufsliste. Erstellt ein Objekt der Klasse
     * {@link ShoppingListBuilder} und erhält von diesem ein Objekt von {@link ShoppingList} zurück.
     * 
     */
    public void buildShoppingList() {
        ShoppingListBuilder slb = new ShoppingListBuilder(this.providerBase, this.canteens);
        this.shoppingList = slb.buildShoppingList();
    }

    /**
     * Methode in der die Ergebnisse der Builderklassen {@link MenuPlanBuilder} und {@link ShoppingListBuilder}
     * ausgegeben werden.
     * 
     * @throws IOException Alle auftretenden IO-Fehler in der Verarbeitung der Methode
     */
    public void outputApplicationResult() throws IOException {

        // Logging
        LOG.log(Level.INFO, "Beginne Aufbereitung Datenausgabe");

        // Erstellung Ausgabe-Objekt für CSV-Ausgabe
        FileOutput fileOutput = new FileOutput();
        // Erstellung Ausgabe-Objekt für HTML-Ausgabe
        HTMLOutput htmlOutput = new HTMLOutput();

        if (Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateCSV"))) {
            // Aufruf der Methode zum Erzeugen eines Menueplans als CSV je existierender Kantine
            int x = 1;
            for (Canteen canteen : this.canteens) {
                LOG.log(Level.INFO, "CSV-Menüplan wird erzeugt für Kantine " + x);
                fileOutput.outputMenuPlan(canteen);
                x++;
            }

            // Aufruf der Methode zum Erzeugen einer Einkaufsliste als CSV
            LOG.log(Level.INFO, "CSV-Einkaufsliste wird erzeugt");
            fileOutput.outputShoppingList(this.shoppingList);

            // Aufruf der Methode zum Erzeugen einer Kostenuebersicht als CSV
            LOG.log(Level.INFO, "CSV-Kostenuebersicht wird erzeugt");
            fileOutput.outputTotalCosts(this.shoppingList);
        }
        else {
            LOG.log(Level.INFO, "CSV soll nicht erzeugt werden");
        }

        if (Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateHTML"))) {
            // Aufruf der Methode zum Erzeugen eines Menueplans als HTML je existierender Kantine
            int x = 1;
            for (Canteen canteen : this.canteens) {
                LOG.log(Level.INFO, "HTML-Menüplan wird erzeugt für Kantine " + x);
                htmlOutput.outputMenuPlan(canteen);
                x++;
            }

            // Aufruf der Methode zum Erzeugen einer Einkaufsliste als CSV
            LOG.log(Level.INFO, "HTML-Einkaufsliste wird erzeugt");
            htmlOutput.outputShoppingList(this.shoppingList);

            // Aufruf der Methode zum Erzeugen einer Kostenuebersicht als CSV
            LOG.log(Level.INFO, "HTML-Kostenuebersicht wird erzeugt");
            htmlOutput.outputTotalCosts(this.shoppingList);
        }
        else {
            LOG.log(Level.INFO, "HTML soll nicht erzeugt werden");
        }

        LOG.log(Level.INFO, "Ausgabe erfolgreich abgeschlossen");
    }

    /**
     * Abfrage der Datenträgerklasse {@link HitListBase}.
     * 
     * @return Die Datenträgerklasse {@link HitListBase}
     */
    public HitListBase getHitListBase() {
        return hitListBase;
    }

    /**
     * Abfrage der Datenträgerklasse {@link RecipeBase}.
     * 
     * @return Die Datenträgerklasse {@link RecipeBase}
     */
    public RecipeBase getRecipeBase() {
        return recipeBase;
    }

    /**
     * Abfrage der Datenträgerklasse {@link ProviderBase}.
     * 
     * @return Die Datenträgerklasse {@link ProviderBase}
     */
    public ProviderBase getProviderBase() {
        return providerBase;
    }

    /**
     * Abfrage der generierten Ergebnisklassen {@link Canteen} als Array.
     * 
     * @return Ein Array der Ergebnisklasse {@link Canteen}
     */
    public Canteen[] getCanteens() {
        return canteens;
    }

    /**
     * Abfragen der generierten Ergebnisklasse {@link ShoppingList}.
     * 
     * @return Die Ergebnisklasse {@link ShoppingList}
     */
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    /**
     * Abfragen der eingelesenen Eingabedaten.
     * 
     * @return Die Eingabedaten als {@link String}
     */
    public String getInputFiles() {
        return inputFiles;
    }

    /**
     * Setzen der Eingabedaten.
     * 
     * @param inputFiles Die zu setzenden Eingabedaten
     */
    public void setInputFiles(String inputFiles) {
        this.inputFiles = inputFiles;
    }
}
