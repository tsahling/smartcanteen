package de.osjava.smartcanteen.helper;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Tim Sahling
 * 
 */
public class LogHelper {

    /**
     * 
     * @param className
     * @return
     */
    public static Logger getLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.addHandler(new ConsoleHandler());
        return logger;
    }

    /**
     * 
     * @param className
     * @param logfileName
     * @return
     */
    public static Logger getLogger(String className, String logfileName) {
        Logger logger = Logger.getLogger(className);
        try {
            logger.addHandler(new FileHandler(logfileName));
        } catch (SecurityException se) {
            logger.log(Level.SEVERE, null, se);
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, null, ioe);
        }
        return logger;
    }
}
