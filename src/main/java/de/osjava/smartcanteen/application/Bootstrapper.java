package de.osjava.smartcanteen.application;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.osjava.smartcanteen.helper.LogHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * 
 * @author Tim Sahling
 * 
 */
public class Bootstrapper {

    private static final Logger LOG = LogHelper.getLogger(Bootstrapper.class.getName());

    private static final String ARG_SPLIT = "=";
    private static final String INPUT_FILE_SPLIT = ";";
    private static final String EMPTY = "";

    /**
     * 
     */
    public Bootstrapper() {

    }

    /**
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

                    File file = new File(inputFile);

                    if (file.exists()) {

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

        String applicationName = PropertyHelper.getProperty("application.name");

        System.out.println(applicationName);

    }

}
