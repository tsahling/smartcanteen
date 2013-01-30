package de.osjava.smartcanteen.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Die Klasse {@link InputFileHandler} ist für das Einlesen und Verarbeiten der Input-Dateien zuständig.
 * {@link InputFileHandler} besitzt Methoden für das zeilenweise Einlesen einer Datei und das Aufteilen der
 * Zeilen in Datenfelder an einem definierten Trennzeichen. Die Methode hasMoreLines besitzt eine rudimentäre
 * Fehlerbehandlung, die das Einlesen von inkonsistenten Zeilen abfängt.
 * 
 * @author Francesco Luciano
 */
public class InputFileHandler {

    // Ein Buffered Reader ist ein Java Reader welcher ein Zeichen Datenstrom verarbeiten kann. Der BufferedReader
    // implementiert die Methode readline() welche die Moeglichkeit des Zeilenweise einlesen bietet.

    private BufferedReader reader;

    // Die Variable delimiter speichert das Trennzeichnen ab, das in den einzulesenden Datein verwendet wird.
    private char delimiter;

    // Die Variable nextLine speichert die naechste Zeile der Datei fuer die weiterverarbeitung ab.
    private String nextLine = null;

    // Die Variable countfields wird fuer Error Handling verwendet, es wird ueberprueft ob eine Zeile immer aus der
    // selben Anzahl Feldern besteht.
    // Sie wird auf -1 initialisert weil keine Zeile mit -1 Feldern existiert. Die erste Zeile der Datei
    // (Header) wird als Referenz fuer alle weiteren Zeilen genommen. Wenn der Header von der Feldlaenge bereits
    // inkonsistenz ist, dann funktioniert die weitere verabeitung nicht.
    private int countFields = -1;

    /**
     * Konstruktor der Pfad der Datei und ein Trennzeichen erwartet
     * 
     * @param filename Pfad
     * @param delimiter Trennzeichen der CSV Datei
     * @throws IOException Alle auftretenden IO-Fehler in der Methode
     */

    // Sollte die Datei nicht gefunden werden wird eine Exception ausgegeben
    public InputFileHandler(URL filename, char delimiter) throws IOException {

        // Variable reader wird eine neue Instanz von BufferedReader uebergeben
        // Der BufferedReader wird ein InpuStream mit der zu oeffenen Datei uebergeben
        // Laut JavaDoc ist dies das effizienteste Vorgehen eine Datei Zeilenweise einzulesen.
        this.reader = new BufferedReader(new InputStreamReader(filename.openStream()));

        // Trennzeichen wird gesetzt
        this.delimiter = delimiter;
    }

    /**
     * Die Methode hasMoreLines überpüft ob es noch eine Zeile in der Datei vorhanden ist. Wenn
     * eine eine nächste Zeile vorhanden ist, wird diese eingelesen und auf eine Variable gespeichert.
     * Die Feldgröße der Zeile gemessen zum Header wird überprüft, wird eine ikonsistente
     * Zeile entdeckt, dann ist die Fehlerbehandlung das überspringen der ikonsistenten Zeile. Die Zeile wird von der
     * Methode hasMoreLines Methode nicht verändert.
     * 
     * @return Liefert TRUE wenn noch eine Zeile vorhanden und FALSE keine mehr vorhanden ist
     * @throws IOException Alle auftretenden IO-Fehler in der Methode
     */
    public boolean hasMoreLines() throws IOException {
        // Array Variable vom Typ Array of String der den Inhalt der Felder einer Zeile aufnimmt.Um Fehler in der Datei
        // abzufangen ist es notwendig die Feldlaenge der Zeile zu ermitteln, dafuer muss die Zeile in einzelteile
        // zerlegt werden.
        String[] lineInFields = null;

        // Wenn die Variable nextline den Wert null hat dann wird in die Logik eingestiegen
        if (nextLine == null)

            // Der Inhalt der Zeile wird auf die Variable nextline gespeichert, solange der Reader nicht den Wert null
            // zurueckgibt. Der reader gibt den Wert null zurueck wenn das Ende der Datei erreicht wurde.

            while ((nextLine = reader.readLine()) != null) {

                // Das Array fields wird mithilfe der Methode splitline mit dem Inhalt der Zeile gefuellt
                lineInFields = splitLine(nextLine, delimiter);

                // Wenn die Variable countfields noch auf dem initialen Wert -1 steht, dann wird der Variable die Laenge
                // der Variable fields zugewiesen. Dieser Schritte wird nur einmal pro CSV Datei gemacht, die zeilen
                // werden mit der Feldlaenge der ersten Zeile verglichen.
                if (countFields == -1)
                    countFields = lineInFields.length;

                // Wenn die aktuelle Zeile nicht die selbe Feldlaenge wie die erste Zeile der CSV aufweist, dann wird
                // diese zeile uebersprungen. Das uebersprungen wird durch die durch die Anweisung continue realisiert,
                // das bewirkt ein zurueck springen in die while Schleife.
                if (countFields != lineInFields.length) {
                    continue;
                }

                // Ist die Zeile leer dann ueberspringen und wieder in die while springen
                if (!nextLine.equals(""))
                    break;
            }

        if (nextLine != null)
            return true;
        else
            return false;
    }

    /**
     * 
     * Die Methode nextLine ist für die Verarbeitung der nächsten Zeile verantwortlich. In
     * dieser Methode nextLine wird die Original eingelesene Zeile verändert z.b. werden Leerzeichen am Anfang
     * und Ende wegeschnitten und ein Unicode Zeichen ersetzt. Die Methode ruft die private Klassenmethode
     * splitLine zum trennen der Datenfelder auf.
     * 
     * @return lineInfields Array of String mit den Elementen der Zeile
     * @throws IOException Alle auftretenden IO-Fehler in der Methode
     */
    public String[] nextLine() throws IOException {

        // Die Leerzeichen am Anfang und am Ende werden weggeschnitten, die inneren Leerzeichen bleiben.
        nextLine = nextLine.trim();

        // Es gab probleme mit einem emdash, das wird hier ersetzt gegen ein - Zeichen
        nextLine = nextLine.replace((char) 8211, (char) '-');

        // Variable vom Typ String[]
        String[] lineInfields = null;

        // Klassenmethode splitLine aufrufen um Zeile zu zerlegen
        lineInfields = splitLine(nextLine, delimiter);

        // nextLine auf null setzen
        nextLine = null;
        // Array mit Elementen zurückgeben
        return lineInfields;
    }

    /**
     * Die Methode splitLine durchlaeuft die uebergebene Zeile zeichenweise
     * und zerlegt sie an den Stellen wo das uebergebene Trennzeichen (delimiter) auftritt.
     * Trennzeichen, die innerhalb von Anfuehrungszeichen stehen, werden ignoriert.
     * Endet die Zeile mit einem Trennzeichen, wird nach durchlaufen der Zeile
     * noch ein leerer String fuer das letzte Feld angehaengt. Die Teilstrings fuer die Felder
     * werden in einer List-Collection gesammelt und zum Schluss in ein String-Array umgewandelt und zurueckgeliefert.
     * 
     * 
     * @param line Eine Zeile die zerlegt werden soll
     * @param delimiter Trennzeichen
     * @return Ein Array mit Elementen der Zeile
     */
    private String[] splitLine(String line, char delimiter) {

        // Es wird eine List benutzt, da das Handling dynamischer gegenüber eines Array ist
        List<String> fields = new ArrayList<String>();

        // Anzahl Zeichen in Zeile
        int numberOfCharacterInLine = line.length();
        // aktuelle Indexposition
        int pointer = 0;
        // aktuelles Zeichen
        char currentCharacter;
        // Anfang und Ende des aktuellen Feldes
        int start, end;
        // Delimiter in Anfuehrungszeichen
        boolean insideQuote;

        // Solange die Variable pointer kleiner ist als die Variable numberOfCharacterInLine
        // die Schleife nicht verlassen
        while (pointer < numberOfCharacterInLine) {

            // Variable start aud den Wert von pointer setzen
            start = pointer;

            // Variable um zu pruefen ob man in einem Feld ist
            insideQuote = false;

            // In dieser Schleife wird der Bereich eines Wertfeldes geprueft
            // Die Schleife wird erst verlassen wenn die Variable insideQuote== false (nicht mehr im Feld) und
            // currentCharacter == delimiter ist
            // Dieser Bereich wird gebraucht damit mit der Methode substring der String beschnitten werden kann
            while (pointer < numberOfCharacterInLine) {

                // Ein String ist ein Array of Char und kann somit wie ein Array per Index zugriffen werden
                // Zeichen des String line mit dem Wert pointer lesen
                // und auf die Variable currentCharacter schreiben
                currentCharacter = line.charAt(pointer);

                // Innerhalb von CSV Datein werden Werte in Anfuehrungszeichen gesetzt.
                // Wenn ein Anfuehrungszeichen entdeckt wird, bedeutet das ein Feld anfaengt oder aufhaert
                // Wenn die Variable insideQuote=true ist ein Feld angefangen
                // Wenn die Variable insideQuote=false ist ein Feld aufgehoert
                // Es ist wichtig das geprueft wird ob man gerade in einem Feld ist, da es vorkommt das Trennzeichen
                // in einem Wert vorkommen, die sollen aber als Zeichen behandelt werden und nicht als Trennzeichen
                if (currentCharacter == '"')
                    insideQuote = !insideQuote;

                // Wenn currentCharacter gleich dem Trennzeichen und Variable insideQuote== false
                // innere Schleife verlassen
                // Wenn currentCharacter ungleich dem Trennzeichen oder Variable insideQuote== true
                // pointer incrementieren

                if (currentCharacter == delimiter && insideQuote == false) {
                    break;
                }
                else {
                    pointer++;
                }

            }

            // Letzes Zeichen des Wertfeldes ist der Index von aktuellen Pointer
            end = pointer; // Letztes Zeichen des Feldes

            // Wenn das Zeichen an der Index Stelle von Start ein Anfueherungszeichen ist und
            // Wenn das Zeichen an der Index Stelle von end - 1 ein Anfuehrungszeichen
            // -1 weil das Trennzeichen nach den Anfuehrnugszeichen mit eingelesen wurde
            // Dann incremetiere start um 1 und decemrementiere end um 1, schneidet die Anfueherungszeichen weg
            if (line.charAt(start) == '"' && line.charAt(end - 1) == '"') {
                start++;
                end--;
            }

            // Schneide den Teil String aus line mit dem Anfang und End index aus
            fields.add(line.substring(start, end));
            // Point hochzaehlen
            pointer++;
        }

        // Wenn letztes Feld leer ist (Zeile endet mit Trennzeichen),
        // leeren String einfuegen

        if (line.charAt(line.length() - 1) == delimiter)
            fields.add("");

        // List-Collection als String-Array zurueckliefern
        // In der Liste fields stehen nur Strings.
        // Nach JavaDoc List ist dies das vorgehen,
        // um die Liste in einem neu zugewiesenen Array von String umzuwandeln.
        String[] type = new String[0];
        return fields.toArray(type);

    }
}
