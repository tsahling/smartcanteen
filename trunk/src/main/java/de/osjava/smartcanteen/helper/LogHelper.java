package de.osjava.smartcanteen.helper;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Klasse {@link LogHelper} bietet Methoden zur Ausgabe von Log-Meldungen,
 * die beliebig im Ablauf der Applikation je nach Prioritätsstufe ausgegeben
 * werden können. Die erzeugten Meldungen können direkt in die Konsole oder bei
 * Bedarf auch in eine externe .log-Datei geschrieben werden. Die Klasse dient
 * vorrangig der besseren Nachvollziehbarkeit und Dokumentation des Verhaltens
 * der Applikation für den jeweiligen Entwickler, gerade wenn später im Verlauf
 * aufgetretene Fehler nachvollzogen werden sollen.
 * 
 * @author Tim Sahling
 */
public class LogHelper {

	/**
	 * Erzeugt eine Logger-Instanz für einen bestimmten Klassennamen.
	 * 
	 * @param className
	 *            Der Klassenname für den die Logger-Instanz erzeugt werden soll
	 * @return Eine statische Instanz von {@link Logger}
	 */
	public static Logger getLogger(String className) {
		Logger logger = Logger.getLogger(className);
		logger.addHandler(new ConsoleHandler());
		return logger;
	}

	/**
	 * Erzeugt eine Logger-Instanz für einen bestimmten Klassennamen und
	 * speichert die Ausgabe des Logs in einer übergebenen Datei.
	 * 
	 * @param className
	 *            Der Klassenname für den die Logger-Instanz erzeugt werden soll
	 * @param logFilename
	 *            Der Dateiname in der die Ausgabe gespeichert werden soll
	 * @return Eine statische Instanz von {@link Logger}
	 */
	public static Logger getLogger(String className, String logFilename) {
		Logger logger = Logger.getLogger(className);
		try {
			logger.addHandler(new FileHandler(logFilename));
		} catch (SecurityException se) {
			logger.log(Level.SEVERE, null, se);
		} catch (IOException ioe) {
			logger.log(Level.SEVERE, null, ioe);
		}
		return logger;
	}
}
