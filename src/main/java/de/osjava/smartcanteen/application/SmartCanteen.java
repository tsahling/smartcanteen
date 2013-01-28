package de.osjava.smartcanteen.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.osjava.smartcanteen.helper.LogHelper;

/**
 * Die Klasse {@link SmartCanteen} ist der Einstiegspunkt in das Klassensystem und damit auch in die Applikation.
 * 
 * @author Tim Sahling
 */
public class SmartCanteen {

    private static final Logger LOG = LogHelper.getLogger(SmartCanteen.class
            .getName());

    /**
     * Die Methode wird zum Starten der Applikation benötigt und behandelt ggf. auftretende Fehler in der Applikation,
     * die in letzter Instanz mitgeloggt mit Hilfe der Klasse {@link LogHelper} werden.
     * 
     * @param args Aufparameter der Applikation
     */
    public static void main(final String[] args) {
        try {
            new Application().bootstrap(args);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
