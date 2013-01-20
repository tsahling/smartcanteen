package de.osjava.smartcanteen.application.gui;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import de.osjava.smartcanteen.application.Application;

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
     * 
     * @param canteens
     * @param shoppingList
     */
    public ApplicationGUI(final Application application) throws IOException {
        JFrame frame = new JFrame("SmartCanteen");
        frame.setSize(windowWidth, windowHeight);
        // frame.setLocation(getDisplayCenter());
        frame.addWindowListener(new DialogWindowClosingListener());
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
        startProcess.setEnabled(true);
        frame.add(startProcess);
        startProcess.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    application.buildMenuePlan();
                    application.buildShoppingList();
                    System.out.println("Erstellung Plaene erfolgreich");
                } catch (IOException e1) {
                    // TODO(Marcel) handle this exception properly
                    e1.printStackTrace();
                    System.out.println("Fehler");
                }

            }
        });

        JButton saveResults = new JButton("Speichern der Ergebnisse");
        frame.add(saveResults);
        saveResults.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    application.outputApplicationResult();
                    System.out.println("ok");
                } catch (IOException e1) {
                    // TODO(Marcel) handle this exception properly
                    e1.printStackTrace();
                    System.out.println("not");
                }
                // Application.outputApplicationResult(shoppingList, canteens);

            }
        });

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

    public class DialogWindowClosingListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event)
        {
            int option = JOptionPane.showConfirmDialog(null, "Applikation beenden?");
            if (option == JOptionPane.OK_OPTION)
                System.exit(0);
        }
    }
}
