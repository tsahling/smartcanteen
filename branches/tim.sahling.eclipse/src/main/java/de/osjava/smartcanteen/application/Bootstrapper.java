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

    private static final String STRING_EMPTY = " ";

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
        initInput(args);
        startApplication();
    }

    /**
     * 
     * @param args
     */
    private void initInput(final String[] args) {
        String inputFiles = null;

        if (args == null || (args != null && args.length == 0)) {
            inputFiles = JOptionPane.showInputDialog(null,
                    PropertyHelper.getProperty("message.missingInputFiles.message"),
                    PropertyHelper.getProperty("message.missingInputFiles.title"), JOptionPane.QUESTION_MESSAGE);
        }
        else {
            for (String arg : args) {
                inputFiles = inputFiles.concat(STRING_EMPTY).concat(arg);
            }
        }

        if (inputFiles == null || (inputFiles != null && inputFiles.length() == 0)) {
            throw new IllegalArgumentException(PropertyHelper.getProperty("message.missingInputFiles.exception"));
        }

        String[] inputFileSplit = inputFiles.split(STRING_EMPTY);

        for (String inputFile : inputFileSplit) {

            // einlesen in base dateien

            System.out.println(inputFile);
        }
    }

    /**
     * 
     */
    private void startApplication() {

        String applicationName = PropertyHelper.getProperty("application.name");

        System.out.println(applicationName);

    }

}
