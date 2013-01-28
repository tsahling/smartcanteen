/**
 * Die Klasse {@link Application} versetzt das Programm anhand der uebergebenen Aufparameter in einen 
 * initialen und konstanten Zustand. Dabei werden zu Beginn die uebergebenen Aufparameter (in Form von 
 * Dateinamen) validiert und anhand der Klasse {@link CSVTokenizer} eingelesen sowie in die entsprechenden 
 * Containerklassen uebergeben.
 * 
 * Nach erfolgreicher Initialisierung startet die Klasse {@link Application} die eigentliche 
 * Programmlogik und sorgt ganz am Ende des Ablaufs fuer die Ausgabe der Ergebnisse.
 * 
 * @author Franceso Luciano & Tim Sahling
 */
public class Application {

    /**
     * Standardkonstruktor
     */
    public Application() {

    }

    /**
     * Versetzt die Anwendung in einen initialen und konstanten Zustand und ruft nach erfolgreichem 
     * Einlesen der Eingabedaten die Logiken der Applikationsroutine auf.
     * 
     * @param args Aufparameter der Applikation
     * @throws Exception Potenzielle Fehler in der Applikation
     */
    public void bootstrap(final String[] args) throws Exception {
    }
}