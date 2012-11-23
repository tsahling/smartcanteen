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
    	initInput(args);    	
        startApplication();
    }
    
    /**
     * 
     * @param args
     */
    private void initInput(final String[] args) {
    	if(args != null && args.length > 0) {
    		
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
