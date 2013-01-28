
/**
 * Die zur Verfuegung gestellten CSV-Dateien werden mit Hilfe der Klasse {@link CSVTokenizer}
 * eingelesen. Die Klasse stellt Methoden zur Verfuegung, mit denen die Datei zeilenweise eingelesen 
 * werden kann. Die Methoden zum Einlesen werden eine rudimentaere Fehlerbehandlung beinhalten, 
 * welche das Einlesen von inkonsistenten Zeilen abfaengt. Die Logik der Zerlegung der 
 * eingelesenen Zeilen in die einzelnen Datenfelder wird die Klassenmethode splitLine() zur 
 * Verfuegung stellen.
 * 
 * @author Francesco Luciano
 */
public class InputFileHandler
{
    /**
     * Standardkonstruktor
     */
    public InputFileHandler()
    {
    }
    
    /**
     * Fragt ob es weitere Zeilen vorhanden sind
     * 
     * @return  wahr/falsch, je nachdem ob weitere Zeilen vorhanden sind
     */
    public boolean hasMoreLines() {
        return true;
    }
    
    /**
     * Liest die naechste Zeile ein.
     * 
     * @return Die naechste Zeile
     */
    public Object nextLine() {
        return null;
    }
    
    /**
     * Zerlegt die uebergebene Zeile in einzelne Datenfelder und gibt diese zurueck
     * 
     * @param line Eine Zeile die zerlegt werden solle
     * @return Ein Array mit Zeilenelementen
     */
    public Object[] splitLine(Object line) {
        return null;
    }
}
