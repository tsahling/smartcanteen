/**
 * 
 */
package de.osjava.smartcanteen.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.osjava.smartcanteen.helper.LogHelper;

/**
 * @author Tim Sahling
 * 
 */
public class SmartCanteen {

    private static final Logger LOG = LogHelper.getLogger(SmartCanteen.class.getName());

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            new Bootstrapper().bootstrap(args);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
