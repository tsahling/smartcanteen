package de.osjava.smartcanteen.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Die zur Verfügung gestellten CSV-Dateien werden mit Hilfe der Klasse {@link CSVTokenizer} eingelesen. Die Klasse
 * stellt Methoden zur Verfügung,
 * mit denen die Datei zeilenweise eingelesen werden kann. Die Methoden zum
 * Einlesen werden eine rudimentäre Fehlerbehandlung beinhalten, welche das
 * Einlesen von inkonsistenten Zeilen abfängt. Die Logik der Zerlegung der
 * eingelesenen Zeilen in die einzelnen Datenfelder wird die Klassenmethode
 * splitLine() zur Verfügung stellen.
 * 
 * @author Francesco Luciano
 */
public class CSVTokenizer {

    /**
     * Konstruktor der Pfad der Datei und ein Trennzeichen erwartet
     * 
     * @param filename Pfad & name der Datei
     * @param delimiter Trennzeichen der CSV Datei
     * @throws IOException
     */

    public CSVTokenizer(URL filename, char delimiter) throws IOException {
        /* Solle die Datei nicht gefunden werden wird eine Exception ausgegeben */

        /* Variable reader wird eine neue Instanz von BufferedReader
         * mit einem Filereader der die in den Parametern übergebende
         * als Inhalt besitzt. */
        this.reader = new BufferedReader(new InputStreamReader(filename.openStream()));

        /* der Klassen Variable delimiter wird der Wert des Parameter delimieter übergeben */
        this.delimiter = delimiter;
    }

    /* Ein Buffered Reader ist ein Java Reader welcher ein Zeichen Datenstrom verarbeiten kann.
     * Dieser Reader implementiert die Methode readline()
     * welche die Möglichkeit des Zeilenweise einlesen bietet. */
    private BufferedReader reader;

    /* Die Variable delimiter repräsentiert das Trennzeichen welches in der CSV Datei benutzt wird */
    private char delimiter;

    /* Auf der Variable nextline wird der Inhalt der eingelesenen Zeile des Reader zur weiterverarbeitung
     * zwischengespeichert */
    private String nextLine = null;

    /* Die Variable countfields ist für das Error Handling zuständig, es wird überprüft ob die Feldlänge immer gleich
     * ist.
     * Sie wird auf -1 initialisert weil keine Zeile mit -1 Feldern existiert.
     * Die erste Zeile der CSV Datei (Header) wird als Referenz für alle weiteren Zeilen genommen.
     * Wenn der Header von der Feldlänge bereits inkonsistenz ist, dann funktioniert die weitere verabeitung nicht. */
    private int countFields = -1;

    /**
     * Liest die nächste Zeile in der Datei und speichert sie in nextLine <br>
     * Leerzeilen werden übersprungen <br>
     * Zeilen mit einer abweichenden Feldgrößen gemessen zum Header werden übersprungen
     * 
     * @return Liefert TRUE wenn noch eine Zeile vorhanden und FALSE wenn nicht
     */
    public boolean hasMoreLines() {
        /* Array Variable vom Typ String der den Inhalt der Felder einer Zeile aufnimmt.
         * Der Inhalt der felder wird in dieser Methode eigentlich noch nicht gebraucht,
         * um jedoch Syntax Fehler in der CSV abzufangen ist es notwendig die Feldlänge der Zeile zu
         * ermitteln. Dafür muss die Zeile in einzelteile zerlegt werden. */
        String[] fields = null;

        /* Wenn die Variable nextline den Wert null hat dann wird in die Logik eingestiegen */
        if (nextLine == null)

            /* Der try Block wird benötigt das es zu Fehlern beim Lesen einer Zeile kommen kann. */
            try {

                /* Der Inhalt der Zeile wird auf die Variable nextline gespeichert, solange der Reader nicht den Wert
                 * null zurückgibt
                 * Der reader gibt den Wert null zurück wenn das Ende der Datei erreicht wurde. */
                while ((nextLine = reader.readLine()) != null) {
                    /* Die Leerzeichen aus der Variable nextline werden durch .trim() entfernt */
                    nextLine = nextLine.trim();

                    /* Das Array fields wird mithilfe der Methode splitline mit dem Inhalt der Zeile gefüllt */
                    fields = splitLine(nextLine, delimiter);

                    /* Wenn die Variable countfields noch auf dem initialen Wert -1 steht,
                     * dann wird der Variable die Länge der Variable fields zugewiesen.
                     * Dieser Schritte wird nur einmal pro CSV Datei gemacht, die zeilen werden mit der Feldgröße
                     * der ersten Zeile verglichen. */
                    if (countFields == -1)
                        countFields = fields.length;

                    /* Wenn die aktuelle Zeile nicht die selbe Feldgröße wie die erste Zeile der CSV aufweist,
                     * dann wird diese zeile übersprungen. Das überspringen wird durch die durch die Anweisung continue
                     * realisiert, das bewirkt ein zurück springen in die while Schleife. */
                    if (countFields != fields.length) {
                        continue;
                    }
                    // TODO: Wird hier genau geprüft Leerzeile? Wieso break?
                    if (!nextLine.equals("")) // Schleife beenden
                        break;
                }
            } catch (IOException e) {
            }

        if (nextLine != null)
            return true;
        else
            return false;
    }

    /**
     * * Liefert ein String-Array mit den Feldern der nächsten Zeile zurück
     * 
     * @return Array mit Inhalt der Zeile
     */
    public String[] nextLine() throws ParseException {

        String[] fields = null;
        String line;

        // Nächste Zeile in nextLine einlesen lassen
        if (!hasMoreLines())
            return null;

        // Felder aus Zeile extrahieren
        fields = splitLine(nextLine, delimiter);

        // nextLine zurück auf null setzen
        nextLine = null;
        return fields;
    }

    /**
     * Zeile in Felder zerlegen, wird von nextLine() aufgerufen
     * 
     * 
     * @param line Eine Zeile die zerlegt werden soll
     * @param delimiter Trennzeichen in der CSV
     * @return Ein Array mit Zeilenelementen
     */
    private String[] splitLine(String line, char delimiter) {
        Vector<String> fields = new Vector<String>();

        int len = line.length(); // Anzahl Zeichen in Zeile
        int i = 0; // aktuelle Indexposition
        char c; // aktuelles Zeichen
        int start, end; // Anfang und Ende des aktuellen Feldes
        boolean quote; // Delimiter in Anführungszeichen

        // Zeile Zeichen dür Zeichen durchgehen

        while (i < len) {

            start = i;
            quote = false;

            // Ende des aktuellen Feldes finden

            while (i < len) {
                c = line.charAt(i);

                // Im Falle eines Anführungszeichen quote umschalten
                if (c == '"')
                    quote = !quote;

                // Wenn c gleich dem Begrenzungszeichen und quote gleich false
                // dann Feldende gefunden.

                if (c == delimiter && quote == false)
                    break;
                i++;
            }
            end = i; // Letztes Zeichen des Feldes

            // Eventuelle Anführungszeichen am Anfang und am Ende verwerfen
            if (line.charAt(start) == '"' && line.charAt(end - 1) == '"') {
                start++;
                end--;
            }

            // Feld speichern
            fields.add(line.substring(start, end));
            i++;
        }

        // Wenn letztes Feld leer (Zeile endet mit Trennzeichen),
        // leeren String einfügen

        if (line.charAt(line.length() - 1) == delimiter)
            fields.add("");

        // Vector-Collection als String-Array zurückliefern
        String[] type = new String[0];
        return fields.toArray(type);

    }
}
