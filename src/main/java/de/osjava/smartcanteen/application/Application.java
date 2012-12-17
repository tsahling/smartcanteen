package de.osjava.smartcanteen.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import de.osjava.smartcanteen.data.item.HitListItem;
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
     * @param args
     *            Aufparameter der Applikation
     * @throws Exception
     *             Potenzielle Fehler in der Applikation
     */
    public void bootstrap(final String[] args) throws Exception {
        if (initInput(args)) {
            startApplication();
        }
    }

    /**
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
                        // TODO(Francesco Luciano) Einlesen der Dateien aus
                        // Dateisystem in die Verwaltungsklassen und erstellen
                        // der Verwaltungsklassen und zuweisen an
                        // Klassenattribute
                        readHitlist(inputFileUrl);

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
}
