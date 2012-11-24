package de.osjava.smartcanteen.application;

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

    private static final String SPLIT = ";";

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
        String inputFiles = null;

        if (args.length == 0) {
            inputFiles = JOptionPane.showInputDialog(null,
                    PropertyHelper.getProperty("message.missingInputFiles.message"),
                    PropertyHelper.getProperty("message.missingInputFiles.title"), JOptionPane.QUESTION_MESSAGE).trim();
        }
        else if (args.length == 1 && args[0].equals(PropertyHelper.getProperty("param.help"))) {
            System.out.println(PropertyHelper.getProperty("application.usageInfo"));
            return false;
        }
        else {
            for (String arg : args) {
                inputFiles = inputFiles.concat(SPLIT).concat(arg.trim());
            }
        }

        if (inputFiles != null) {
            String[] inputFileSplit = inputFiles.split(SPLIT);

            if (inputFileSplit.length > 0) {
                for (String inputFile : inputFileSplit) {

                    // einlesen in base dateien

                    System.out.println(inputFile.trim());
                }

                return true;
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
