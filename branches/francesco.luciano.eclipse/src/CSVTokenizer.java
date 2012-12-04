import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Vector;

/**
 * Klasse zum Lesen und Parsen von CSV-Dateien
 */

public class CSVTokenizer {

	private BufferedReader reader;
	private char delimiter;
	private String nextLine = null;
	private int countFields = -1;
	public int countLines = 0;

	public CSVTokenizer(String filename, char delimiter)
			throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(filename));
		this.delimiter = delimiter;
	}

	public CSVTokenizer(Reader reader, char delimiter) {
		this.reader = new BufferedReader(reader);
		this.delimiter = delimiter;
	}

	/**
	 * Liest die nächste Zeile lesen und speichert sie in nextLine Liefert im
	 * Erfolgsfall true zurück und false, wenn keine Zeile mehr verfügbar.
	 * Leerzeilen werden übersprungen
	 */

	public boolean hasMoreLines() {
		
		String[] fields = null;

		if (nextLine == null)
			try {
				while ((nextLine = reader.readLine()) != null) {
					nextLine = nextLine.trim(); // Wenn nicht leere Zeile
					// Felder aus Zeile extrahieren
					fields = splitLine(nextLine, delimiter);
					
					// Wenn erste Zeile, dann Anzahl Felder abspeichern
					if (countFields == -1)
						countFields = fields.length;
					
					// Sicherstellen, dass alle Zeilen die gleiche Anzahl Felder haben
					if (countFields != fields.length) {
						continue;
					}
					
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
	 * Liefert ein String-Array mit den Feldern der nächsten Zeile zurück
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
