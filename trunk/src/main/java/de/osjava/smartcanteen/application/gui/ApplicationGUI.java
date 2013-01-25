package de.osjava.smartcanteen.application.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

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
    // TODO (Marcel Baxmann) Hieraus bitte noch Properties machen
    int windowWidth = 800; // Angabe der Breite des Fensters
    int windowHeight = 400; // Angabe der Höhe des Fensters
    // int procentOptionPane = 65;
    // int procentOutputPane = 35;
    private Application application;
    boolean finishedProcess = false;
    private JTextArea displayInputFiles;

    /**
     * Standardkonstruktor
     * 
     * @param canteens
     * @param shoppingList
     */
    public ApplicationGUI(final Application application) {
        this.application = application;
    }

    /**
     * Methode zum initalisieren der Benutzeroberfläche
     * 
     * @author Marcel Baxmann
     */
    public void initialize() {
        // Anlegen des Rahmens für die Anzeige
        JFrame frame = new JFrame("SmartCanteen");
        frame.setSize(windowWidth, windowHeight);
        frame.setLocation(getDisplayCenter());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new DialogWindowClosingListener());
        frame.setLayout(new GridLayout());

        // Aufteilen des Anzeigerahmens in Rechts (Output) und Links (Optionen)
        // Optionsbereichs
        JPanel pnlOptionArea = new JPanel();
        pnlOptionArea.setLayout(new GridLayout(3, 1));
        pnlOptionArea.setBorder(BorderFactory.createTitledBorder("Optionen"));
        frame.add(pnlOptionArea);

        // TODO (Marcel Baxmman) Entweder die Daten in Tabellen einpflegen oder Pane wieder rausnehmen
        // Ausagbebereich --> noch zu bauen
        JPanel pnlOutputArea = new JPanel();
        pnlOutputArea.setBorder(BorderFactory.createTitledBorder("Voransicht:"));
        // Layout to be defined
        pnlOutputArea.setLayout(new GridLayout());
        frame.add(pnlOutputArea);

        JTabbedPane tabPaneOutput = new JTabbedPane();
        tabPaneOutput.addTab("Menue Plan", null);
        tabPaneOutput.addTab("Kosten Plan", null);
        tabPaneOutput.addTab("Einkaufs Plan", null);
        pnlOutputArea.add(tabPaneOutput);

        // Unterteilen des Optionsbereich in die Kategorien Input-Option, Process-Option und Output-Option
        // Input-Option
        JPanel pnlInputOptionArea = new JPanel();
        pnlInputOptionArea.setLayout(new GridLayout(2, 1));
        pnlInputOptionArea.setBorder(BorderFactory.createTitledBorder("Eingabe-Dateien"));
        pnlOptionArea.add(pnlInputOptionArea);

        // Process-Option
        JPanel pnlProcessOptionArea = new JPanel();
        pnlProcessOptionArea.setLayout(new FlowLayout());
        pnlProcessOptionArea.setBorder(BorderFactory.createTitledBorder("Verarbeitungs-Einstellungen"));
        pnlOptionArea.add(pnlProcessOptionArea);

        // Output-Option
        JPanel pnlOutputOptionArea = new JPanel();
        pnlOutputOptionArea.setLayout(new FlowLayout());
        pnlOutputOptionArea.setBorder(BorderFactory.createTitledBorder("Ausgabe-Einstellungen"));
        pnlOptionArea.add(pnlOutputOptionArea);

        // Füllen des Bereichs Input-Option mit Content und Einstellmöglichkeiten
        JLabel lblInputText = new JLabel("Folgende Dateien wurden in das Programm geladen:");
        pnlInputOptionArea.add(lblInputText);

        displayInputFiles = new JTextArea(application.getInputFiles(), 2, 1);
        pnlInputOptionArea.add(displayInputFiles);
        displayInputFiles.setLineWrap(true);
        displayInputFiles.setFocusable(false);

        // Füllen des Bereichs Process-Option mit Content und Einstellmöglichkeiten
        JLabel lblProcessText = new JLabel("Verarbeitungs-Art einstellen:");
        pnlProcessOptionArea.add(lblProcessText);

        // TODO (Marcel Baxmman) Radiobuttons mit Funktion versehen
        ButtonGroup rbtnProcessTypeGroup = new ButtonGroup();
        // JPanel rbtnPanel = new JPanel();
        // rbtnPanel.setLayout(new FlowLayout());

        JRadioButton rbtnProcessType1 = new JRadioButton("Zufall");
        JRadioButton rbtnProcessType2 = new JRadioButton("Sequentiell");
        rbtnProcessTypeGroup.add(rbtnProcessType1);
        rbtnProcessTypeGroup.add(rbtnProcessType2);
        // rbtnPanel.add(rbtnProcessType1);
        // rbtnPanel.add(rbtnProcessType2);
        // pnlProcessOptionArea.add(rbtnPanel);
        pnlProcessOptionArea.add(rbtnProcessType1);
        pnlProcessOptionArea.add(rbtnProcessType2);

        // Anlegen eines Buttons für den Start der Verarbeitung und setzen des Actionlisteners
        final JButton btnStartProcess = new JButton("Verarbeitung starten");
        btnStartProcess.setEnabled(true);
        pnlProcessOptionArea.add(btnStartProcess);

        // TODO (Marcel Baxmman) Checkboxen mit Funktion versehen
        // Füllen des Bereichs Output-Option mit Content und Einstellmöglichkeiten
        JLabel llbOutputText = new JLabel("Ausgabeformat festlegen:");
        pnlOutputOptionArea.add(llbOutputText);
        JCheckBox cboxOutputFormat1 = new JCheckBox("CSV:");
        pnlOutputOptionArea.add(cboxOutputFormat1);
        JCheckBox cboxOutputFormat2 = new JCheckBox("HTML:");
        pnlOutputOptionArea.add(cboxOutputFormat2);

        // Anlegen eines Buttons für den Start der Dateiausgabe und setzen des Actionlisteners
        final JButton btnSaveResults = new JButton("Speichern der Ergebnisse");
        pnlOutputOptionArea.add(btnSaveResults);
        btnSaveResults.setEnabled(false);

        // Actionlistener für die Generierung des MenuPlans und der Einkaufsliste
        btnStartProcess.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                application.setInputFiles(displayInputFiles.getText());

                try {
                    if (application.fillBases()) {
                        application.buildMenuPlan();
                        application.buildShoppingList();
                        finishedProcess = true;
                        btnSaveResults.setEnabled(true);
                        btnStartProcess.setText("Neu berechnen");
                        System.out.println("Erstellung Plaene erfolgreich");
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Actionlistener für die Ausgabe der Dateien
        btnSaveResults.addActionListener(new ActionListener() {

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

            }
        });

        frame.setVisible(true);
        frame.toFront();
    }

    /**
     * ermittelt die Bildschirmmitte anhand der Auflösung und gibt diese als Point zurück
     * 
     * @return Point
     *         der Bildschrimmitte
     */
    private Point getDisplayCenter() {

        Dimension display = Toolkit.getDefaultToolkit().getScreenSize();

        Point displayCenter = new Point(0, 0);
        displayCenter.x = (int) ((display.getWidth() - windowWidth) / 2);
        displayCenter.y = (int) ((display.getHeight() - windowHeight) / 2);

        return displayCenter;
    }

    /**
     * Inner-Class zum Abfragen des Fenster-Schließ-Events
     */
    public class DialogWindowClosingListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event)
        {
            int option = JOptionPane.showConfirmDialog(null, "Möchten Sie die Applikation beenden?", "Beenden",
                    JOptionPane.OK_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

}
