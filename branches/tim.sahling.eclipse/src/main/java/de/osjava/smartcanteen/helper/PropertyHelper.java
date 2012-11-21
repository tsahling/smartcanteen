package de.osjava.smartcanteen.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Tim Sahling
 * 
 */
public class PropertyHelper {

    private static final Logger LOG = LogHelper.getLogger(PropertyHelper.class.getName());

    private static final InputStream PROPERTIES_FILE = ClassLoader
            .getSystemResourceAsStream("properties/smartcanteen.properties");

    private static Properties properties;

    /**
     * 
     * @return
     */
    public static Properties getProperties() {
        return loadProperties();
    }

    /**
     * 
     * @param propertyName
     * @return
     */
    public static String getProperty(String propertyName) {
        return loadProperties().getProperty(propertyName);
    }

    /**
     * 
     * @param propertyName
     * @param defaultValue
     * @return
     */
    public static String getProperty(String propertyName, String defaultValue) {
        return loadProperties().getProperty(propertyName, defaultValue);
    }

    /**
     * 
     * @return
     */
    private static Properties loadProperties() {
        if (properties != null) {
            return properties;
        }

        Properties props = new Properties();
        try {
            props.load(PROPERTIES_FILE);
        } catch (FileNotFoundException fnfe) {
            LOG.log(Level.SEVERE, "Cannot find property file", fnfe);
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, "Cannot read property file", ioe);
        }
        return properties = props;
    }
}
