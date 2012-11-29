package de.osjava.smartcanteen.application;

import java.net.URL;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.osjava.smartcanteen.helper.LogHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

// Hallo zusammen,
//
// die Eingabedateien stehen jetzt als Download bereit. Die Hitliste enthält eine laufende Nummer und das Gericht (d.h.
// Nummer 1 ist das beliebteste Gericht usw.). Die Rezepte enthalten in jeder Zeile Rezeptname, Menge, Einheit, und den
// Namen der Zutat. Bei den Preislisten enthält die erste Zeile die Angabe ob es ein Bauer oder Großhändler ist, den
// Namen des Anbieters, und die Angabe der Entfernung bzw. des Kostensatzes, je nachdem. Danach folgen die einzelnen
// Angebotenen Waren: Menge des Gebindes, Einheit, Name, dann ggf. f für Fisch und m für Fleisch, Preis des Gebindes,
// und Anzahl vorhandener Gebinde.
//
// Die Namen der Dateien soll man übergeben können, damit ich ihr Programm auch dann verwenden kann, wenn es z.B. mehr
// oder weniger Anbieter gibt.
//
// Kulinarische Grüße
//
// J. Gourmand

/**
 * 
 * @author Tim Sahling
 */
public class Application {

    private static final Logger LOG = LogHelper.getLogger(Application.class.getName());

    private static final String ARG_SPLIT = "=";
    private static final String INPUT_FILE_SPLIT = ";";
    private static final String EMPTY = "";

    /**
     * 
     */
    public Application() {

    }

    /**
     * Versetzt die Anwendung in einen initialen und konstanten Zustand und ruft nach erfolgreichem Einlesen der
     * Eingabedaten die Logiken der Applikationsroutine auf.
     * 
     * @param args
     * @throws Exception
     */
    public void bootstrap(final String[] args) throws Exception {
        if (initInput(args)) {
            startApplication();
        }
    }

    /**
     * 
     * @param args
     */
    private boolean initInput(final String[] args) {
        String inputFiles = EMPTY;

        if (args.length == 0) {
            inputFiles = JOptionPane.showInputDialog(null,
                    PropertyHelper.getProperty("message.missingInputFiles.message"),
                    PropertyHelper.getProperty("message.missingInputFiles.title"), JOptionPane.QUESTION_MESSAGE);
        }
        else if (args.length == 1 && args[0].equals(PropertyHelper.getProperty("param.help"))) {
            System.out.println(PropertyHelper.getProperty("application.usageInfo"));
            return false;
        }
        else {
            for (String arg : args) {

                if (arg.contains(PropertyHelper.getProperty("param.inputFiles"))) {
                    String[] argSplit = arg.split(ARG_SPLIT);

                    if (argSplit.length > 1) {
                        inputFiles = inputFiles.concat(argSplit[1].trim()).concat(INPUT_FILE_SPLIT);
                    }
                }
            }
        }

        boolean wrongInputFile = false;

        if (inputFiles != null && !inputFiles.equals(EMPTY)) {
            String[] inputFileSplit = inputFiles.split(INPUT_FILE_SPLIT);

            if (inputFileSplit.length > 0) {
                for (String inputFile : inputFileSplit) {

                    URL inputFileUrl = ClassLoader.getSystemResource(PropertyHelper
                            .getProperty("application.inputFilePath") + inputFile);

                    if (inputFileUrl != null) {
                        // TODO(Francesco Luciano) Einlesen der Dateien aus Dateisystem in die Verwaltungsklassen
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

        throw new IllegalArgumentException(PropertyHelper.getProperty("message.missingInputFiles.exception"));
    }

    /**
     * 
     */
    private void startApplication() {

    }

}
