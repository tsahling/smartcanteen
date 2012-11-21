package de.osjava.smartcanteen.application;

import java.util.logging.Logger;

import de.osjava.smartcanteen.helper.LogHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * 
 * @author Tim Sahling
 * 
 */
public class Bootstrapper {

    private static final Logger LOG = LogHelper.getLogger(Bootstrapper.class.getName());

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

        // Read input files and put into base

        startApplication();
    }

    /**
     * 
     */
    private void startApplication() {

        String property = PropertyHelper.getProperty("outputPath");

        System.out.println(property);

        property = PropertyHelper.getProperty("outputPath", "1");
    }

}
