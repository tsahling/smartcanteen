import java.util.logging.Logger;

/**
 * Die Klasse {@link LogHelper} bietet Methoden zur Ausgabe von Log-Meldungen, die beliebig im Ablauf 
 * der  Applikation je nach Prioritaetsstufe ausgegeben werden koennen. Die erzeugten Meldungen 
 * koennen direkt in die Konsole oder bei Bedarf auch in eine externe .log-Datei geschrieben werden. 
 * 
 * Die Klasse dient vorrangig der besseren Nachvollziehbarkeit und Dokumentation des Verhaltens der 
 * Applikation für den jeweiligen Entwickler, gerade wenn spaeter im Verlauf aufgetretene Fehler 
 * nachvollzogen werden sollen.
 * 
 * @author Tim Sahling
 */
public class LogHelper {

    /**
     * Erzeugt eine Logger-Instanz fuer einen bestimmten Klassennamen.
     * 
     * @param className Der Klassenname fuer den die Logger-Instanz erzeugt werden soll
     * @return Eine statische Instanz von {@link Logger}
     */
    public static Logger getLogger(String className) {
        return null;
    }

    /**
     * Erzeugt eine Logger-Instanz fuer einen bestimmten Klassennamen und speichert die Ausgabe des 
     * Logs in einer uebergebenen Datei.
     * 
     * @param className Der Klassenname fuer den die Logger-Instanz erzeugt werden soll
     * @param logFilename Der Dateiname in der die Ausgabe gespeichert werden soll
     * @return Eine statische Instanz von {@link Logger}
     */
    public static Logger getLogger(String className, String logFilename) {
        return null;
    }
}
