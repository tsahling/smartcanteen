package de.osjava.smartcanteen.application.gui;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Die {@link AplicationGUI} ist keine Basis-Anforderung an das System. Sie
 * wurde jedoch vor dem Ziel diese zu implementieren, sofern ausreichend Zeit
 * zur Verfügung steht, in das Klassensystem aufgenommen, um die Einstiegspunkte
 * und Benutzungsbezieheung aufzuzeigen. Die Klasse wird von der Klasse {@link Aplication} verwendet. Sie ist für die
 * Generierung der grafischen
 * Benutzeroberfläche zuständig. Geplant sind eine Steuerungmöglichkeit für den
 * Datei-Import, den Start des Berechnungsvorgangs, Eingabe von Variablen sowie
 * für die Auslösung der Ausgabe der von der Software generierten Daten. Denkbar
 * ist hier eine Voransicht der Daten in Tabellenform in der GUI. Derzeit noch
 * nicht planbar ist, ob weitere Klassen für die Umsetzung der GUI benötigt
 * werden.
 * 
 * @author Marcel Baxmann
 */
public class ApplicationGUI {
    int windowWidth = 300; // Angabe der Breite des Fensters
    int windowHeight = 500; // Angabe der Höhe des Fensters

    /**
     * Standardkonstruktor
     */
    public ApplicationGUI() {

        JFrame frame = new JFrame("SmartCanteen");
        frame.setSize(windowWidth, windowHeight);
        // frame.setLocation(getDisplayCenter());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));
        frame.setVisible(true);
        frame.toFront();

        JLabel input = new JLabel("Input-Dateien:");
        frame.add(input);

        JLabel process = new JLabel("Verarbeitungs-Einstellungen:");
        frame.add(process);

        JLabel output = new JLabel("Ausgabe-Einstellungen:");
        frame.add(output);

        JButton startProcess = new JButton("Verarbeitung starten");
        frame.add(startProcess);

        JButton saveResults = new JButton("Speichern der Ergebnisse");
        frame.add(saveResults);

    }

    /**
     * ermittelt die Bildschirmmitte anhand der Auflösung
     * 
     * @return Point
     *         der Bildschrimmitte
     */
    // TODO (Marcel) Position korrgieren
    private Point getDisplayCenter() {
        Point displayCenter = new Point(0, 0);
        displayCenter.x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - windowWidth) / 2;
        displayCenter.y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - windowWidth) / 2;
        return displayCenter;
    }

}
