import java.util.Properties;

/**
 * Die Klasse {@link PropertyHelper} bietet Methoden zum Ein- und Auslesen von 
 * Konfigurationsparametern der Applikation, die an diversen Stellen eingesetzt werden 
 * (Fehlermeldungen, Pfade, Einstellungen, etc.). Diese Klasse greift auf eine fest im Projektpfad 
 * hinterlegte .properties-Datei zu.
 * 
 * @author Tim Sahling
 */
public class PropertyHelper {

    /**
     * Liest alle in der .properties-Datei vorhandenen Einstellungen.
     * 
     * @return Eine statische Instanz von {@link Properties}
     */
    public static Properties getProperties() {
        return null;
    }

    /**
     * Gibt einen Einstellungswert fuer einen uebergebenen Einstellungsnamen zurueck.
     * 
     * @param propertyName Der Name der Einstellung die zurueckgegeben werden soll
     * @return Der Wert der Einstellung
     */
    public static String getProperty(String propertyName) {
        return null;
    }

    /**
     * Gibt einen Einstellungswert fuer einen uebergebenen Einstellungsnamen zurueck. Kann der
     * uebergebene Einstellungsname nicht gefunden werden, wird der uebergebene Standardwert
     * zurueckgegeben.
     * 
     * @param propertyName Der Name der Einstellung die zurueckgegeben werden soll
     * @param defaultValue Der Standardwert der zurueckgegeben werden soll wenn kein Wert fuer den
     * uebergebenen Einstellungsnamen gefunden wird
     * @return Der Wert der Einstellung oder der Standardwert
     */
    public static String getProperty(String propertyName, String defaultValue) {
        return null;
    }
}