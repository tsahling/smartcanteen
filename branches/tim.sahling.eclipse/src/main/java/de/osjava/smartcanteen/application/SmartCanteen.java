/**
 * 
 */
package de.osjava.smartcanteen.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.osjava.smartcanteen.helper.LogHelper;

public class SmartCanteen {

    private static final Logger LOG = LogHelper.getLogger(SmartCanteen.class.getName());

    // Hallo zusammen,
    //
    // die Eingabedateien stehen jetzt als Download bereit. Die Hitliste enthält eine laufende Nummer und das Gericht
    // (d.h. Nummer 1 ist das beliebteste Gericht usw.). Die Rezepte enthalten in jeder Zeile Rezeptname, Menge,
    // Einheit, und den Namen der Zutat. Bei den Preislisten enthält die erste Zeile die Angabe ob es ein Bauer oder
    // Großhändler ist, den Namen des Anbieters, und die Angabe der Entfernung bzw. des Kostensatzes, je nachdem. Danach
    // folgen die einzelnen Angebotenen Waren: Menge des Gebindes, Einheit, Name, dann ggf. f für Fisch und m für
    // Fleisch, Preis des Gebindes, und Anzahl vorhandener Gebinde.
    //
    // Die Namen der Dateien soll man übergeben können, damit ich ihr Programm auch dann verwenden kann, wenn es z.B.
    // mehr oder weniger Anbieter gibt.
    //
    // Kulinarische Grüße
    //
    // J. Gourmand

    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        try {
            new Application().bootstrap(args);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
