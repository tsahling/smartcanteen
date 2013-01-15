package de.osjava.smartcanteen.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Klasse {@link PropertyHelper} bietet Methoden zum Ein- und Auslesen von
 * Konfigurationsparametern der Applikation, die an diversen Stellen eingesetzt
 * werden (Fehlermeldungen, Pfade, Einstellungen, etc.). Diese Klasse greift auf
 * eine fest im Projektpfad hinterlegte .properties-Datei zu.
 * 
 * @author Tim Sahling
 */
public class PropertyHelper {

    private static final Logger LOG = LogHelper.getLogger(PropertyHelper.class
            .getName());

    private static final InputStream PROPERTIES_FILE = ClassLoader
            .getSystemResourceAsStream("properties/smartcanteen.properties");

    private static Properties properties;

    /**
     * Liest alle in der .properties-Datei vorhandenen Einstellungen.
     * 
     * @return Eine statische Instanz von {@link Properties}
     */
    public static Properties getProperties() {
        return loadProperties();
    }

    /**
     * Gibt einen Einstellungswert für einen übergebenen Einstellungsnamen
     * zurück.
     * 
     * @param propertyName
     *            Der Name der Einstellung die zurückgegeben werden soll
     * @return Der Wert der Einstellung
     */
    public static String getProperty(String propertyName) {
        return loadProperties().getProperty(propertyName);
    }

    /**
     * Gibt einen Einstellungswert für einen übergebenen Einstellungsnamen
     * zurück. Kann der übergebene Einstellungsname nicht gefunden werden, wird
     * der übergebene Standardwert zurückgegeben.
     * 
     * @param propertyName
     *            Der Name der Einstellung die zurückgegeben werden soll
     * @param defaultValue
     *            Der Standardwert der zurückgegeben werden soll wenn kein Wert
     *            für den übergebenen Einstellungsnamen gefunden wird
     * @return Der Wert der Einstellung oder der Standardwert
     */
    public static String getProperty(String propertyName, String defaultValue) {
        return loadProperties().getProperty(propertyName, defaultValue);
    }

    /**
     * Setzt einen Einstellungswert für einen übergebenen Einstellungsnamen.
     * 
     * @param propertyName
     * @param propertyValue
     */
    public static void setProperty(String propertyName, String propertyValue) {
        loadProperties().setProperty(propertyName, propertyValue);
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
