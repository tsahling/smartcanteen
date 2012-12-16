package de.osjava.smartcanteen.application;

/**
 * Die zur Verfügung gestellten CSV-Dateien werden mit Hilfe der Klasse
 * {@link CSVTokenizer} eingelesen. Die Klasse stellt Methoden zur Verfügung,
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
	 * Standardkonstruktor
	 */
	public CSVTokenizer() {
	}

	/**
	 * Prüft ob weitere Zeilen vorhanden sind
	 * 
	 * @return wahr/falsch, je nachdem ob weitere Zeilen vorhanden sind
	 */
	public boolean hasMoreLines() {
		return true;
	}

	/**
	 * Liest die nächste Zeile ein.
	 * 
	 * @return Die nächste Zeile
	 */
	public Object nextLine() {
		return null;
	}

	/**
	 * Zerlegt die übergebene Zeile in einzelne Datenfelder und gibt diese
	 * zurück
	 * 
	 * @param line
	 *            Eine Zeile die zerlegt werden soll
	 * @return Ein Array mit Zeilenelementen
	 */
	public Object[] splitLine(Object line) {
		return null;
	}
}
