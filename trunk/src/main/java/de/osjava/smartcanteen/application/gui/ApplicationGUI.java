package de.osjava.smartcanteen.application.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import de.osjava.smartcanteen.application.Application;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.helper.FileHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

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
    int windowHeight = 600; // Angabe der Höhe des Fensters
    private static Application application;
    boolean finishedProcess = false;
    private JTextArea displayInputFiles;
    static JLabel lblPreviewText3;
    // Abfrage der Anzahl der Gerichte pro Tag und speichern in lokale Variable (geparstes int)
    static int mealsPerDay = Integer.parseInt(PropertyHelper.getProperty("planingPeriod.mealsPerDay"));
    static String[] tablePreviewHeader = new String[mealsPerDay + 2];
    static String[][] tablePreviewContent = null;
    private String[][] outputString;
    static JTable tblMenuPlan;

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
        // frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
        frame.setLocation(getDisplayCenter());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new DialogWindowClosingListener());

        frame.setLayout(new GridLayout(1, 2));

        // **** LAYOUT: OPTION/ANSICHT -- Aufteilen des Anzeigerahmens in Rechts (Output) und Links (Optionen) ****
        // Optionsbereichs
        JPanel pnlOptionArea = new JPanel();
        pnlOptionArea.setLayout(new GridLayout(4, 1));
        pnlOptionArea.setBorder(BorderFactory.createTitledBorder("Optionen"));
        frame.add(pnlOptionArea);

        // TODO (Marcel Baxmman) Entweder die Daten in Tabellen einpflegen oder Pane wieder rausnehmen
        // Ausagbebereich --> noch zu bauen
        JPanel pnlOutputArea = new JPanel();
        pnlOutputArea.setBorder(BorderFactory.createTitledBorder("Voransicht"));
        // Layout to be defined
        pnlOutputArea.setLayout(new GridLayout(4, 1));
        frame.add(pnlOutputArea);

        // **** GENERIERUNG: VORANSICHT ****
        // JTabbedPane tabPaneOutput = new JTabbedPane();
        // tabPaneOutput.addTab("Menü-Plan", null);
        // tabPaneOutput.addTab("Kosten-Plan", null);
        // tabPaneOutput.addTab("Einkaufs-Liste", null);
        // pnlOutputArea.add(tabPaneOutput);

        // Testtabelle
        JLabel lblPreviewText1 = new JLabel("Erstellter Menüplan:");
        pnlOutputArea.add(lblPreviewText1);

        tablePreviewHeader[0] = "Kantine";
        tablePreviewHeader[1] = "Datum";
        for (int i = 2; i < tablePreviewHeader.length; i++) {
            tablePreviewHeader[i] = "Gericht " + (i - 1);
        }

        DefaultTableModel model = new DefaultTableModel(tablePreviewContent, tablePreviewHeader);
        tblMenuPlan = new JTable(model);

        tblMenuPlan.setCellSelectionEnabled(false);
        tblMenuPlan.setEnabled(false);
        tblMenuPlan.setColumnSelectionAllowed(false);
        tblMenuPlan.setDragEnabled(false);

        JScrollPane scrlOutputArea = new JScrollPane(tblMenuPlan);
        pnlOutputArea.add(scrlOutputArea);

        // Label für die Ausgabe der Gesamtkosten
        JLabel lblPreviewText2 = new JLabel("Gesamtkosten:");
        pnlOutputArea.add(lblPreviewText2);
        lblPreviewText3 = new JLabel("Noch nicht berechnet");
        pnlOutputArea.add(lblPreviewText3);

        // **** LAYOUT: OPTIONSUNTERTEILUNG -- Unterteilen des Optionsbereich in die Kategorien Input-Option,
        // Process-Option und Output-Option ****
        // Input-Option
        JPanel pnlInputOptionArea = new JPanel();
        pnlInputOptionArea.setLayout(new GridLayout(2, 1));
        pnlInputOptionArea.setBorder(BorderFactory.createTitledBorder("Eingabe-Dateien"));
        pnlOptionArea.add(pnlInputOptionArea);

        // Process-Option
        JPanel pnlProcessOptionArea = new JPanel();
        pnlProcessOptionArea.setLayout(new GridLayout(1, 3));
        pnlProcessOptionArea.setBorder(BorderFactory.createTitledBorder("Verarbeitungs-Einstellungen"));
        pnlOptionArea.add(pnlProcessOptionArea);

        // Output-Option
        JPanel pnlOutputOptionArea = new JPanel();
        pnlOutputOptionArea.setLayout(new GridLayout(2, 3));
        pnlOutputOptionArea.setBorder(BorderFactory.createTitledBorder("Ausgabe-Einstellungen"));
        pnlOptionArea.add(pnlOutputOptionArea);

        // Kontroll-Panel
        JPanel pnlControlOptionArea = new JPanel();
        pnlControlOptionArea.setLayout(new GridLayout(1, 2));
        // pnlControlOptionArea.setBorder(BorderFactory.createTitledBorder("Ausgabe-Einstellungen"));
        pnlOptionArea.add(pnlControlOptionArea);

        // **** GENERIERUNG: OPTION-INPUT -- Füllen des Bereichs Input-Option mit Einstellmöglichkeiten ****
        JLabel lblInputText = new JLabel("Folgende Dateien wurden in das Programm geladen:");
        pnlInputOptionArea.add(lblInputText);

        final JTextArea displayInputFiles = new JTextArea(application.getInputFiles(), 2, 1);

        pnlInputOptionArea.add(displayInputFiles);
        displayInputFiles.setLineWrap(true);
        displayInputFiles.setFocusable(false);

        // **** GENERIERUNG: OPTION-PROCESS -- Füllen des Bereichs Process-Option mit Einstellmöglichkeiten ****

        // Verarbeitungsmethode: RadioButton für Abfragegenerieren
        JLabel lblProcessText = new JLabel("Verarbeitungs-Art:");
        pnlProcessOptionArea.add(lblProcessText);

        JRadioButton rbtnProcessType1 = new JRadioButton("Zufall");
        JRadioButton rbtnProcessType2 = new JRadioButton("Sequentiell");
        if (PropertyHelper.getProperty("planingPeriod.planingMode").matches("random")) {
            rbtnProcessType1.setSelected(true);
        }
        else {
            rbtnProcessType2.setSelected(true);
        }

        // generieren der Gruppe für die Radiobuttons, da immer nur einer ausgewählt werden darf
        ButtonGroup rbtnProcessTypeGroup = new ButtonGroup();
        rbtnProcessTypeGroup.add(rbtnProcessType1);
        rbtnProcessTypeGroup.add(rbtnProcessType2);
        pnlProcessOptionArea.add(rbtnProcessType1);
        pnlProcessOptionArea.add(rbtnProcessType2);

        // Anlegen eines Buttons für den Start der Verarbeitung
        final JButton btnStartProcess = new JButton("Verarbeitung starten");
        btnStartProcess.setEnabled(true);
        pnlControlOptionArea.add(btnStartProcess);

        // **** GENERIERUNG: OPTION-OUTPUT -- Füllen des Bereichs Output-Option mit Einstellmöglichkeiten ***

        // Ausagbeformat: Checkboxen generieren
        JLabel llbOutputText1 = new JLabel("Ausgabeformat:");
        pnlOutputOptionArea.add(llbOutputText1);

        JCheckBox cboxOutputFormat1 = new JCheckBox("CSV");
        cboxOutputFormat1.setSelected(Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateCSV")));
        pnlOutputOptionArea.add(cboxOutputFormat1);

        JCheckBox cboxOutputFormat2 = new JCheckBox("HTML");
        cboxOutputFormat2.setSelected(Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateHTML")));
        pnlOutputOptionArea.add(cboxOutputFormat2);

        // Trennzeichen: Radiobuttons für Auswahl des Trennzeichens in CSV-Datei
        JLabel lbOutputText2 = new JLabel("CSV-Trennzeichen:");
        pnlOutputOptionArea.add(lbOutputText2);

        final JRadioButton rbtnOutputSeperator1 = new JRadioButton("Komma [ , ]");
        final JRadioButton rbtnOutputSeperator2 = new JRadioButton("Semikolon [ ; ]");
        if (PropertyHelper.getProperty("outputData.dataSetSeperator").matches(",")) {
            rbtnOutputSeperator1.setSelected(true);
        }
        else {
            rbtnOutputSeperator2.setSelected(true);
        }

        // generieren der Gruppe für die Radiobuttons, da immer nur einer ausgewählt werden darf
        ButtonGroup rbtnOutputSeperatorGroup = new ButtonGroup();
        rbtnOutputSeperatorGroup.add(rbtnOutputSeperator1);
        rbtnOutputSeperatorGroup.add(rbtnOutputSeperator2);
        pnlOutputOptionArea.add(rbtnOutputSeperator1);
        pnlOutputOptionArea.add(rbtnOutputSeperator2);

        // Anlegen eines Buttons für den Start der Dateiausgabe und setzen des Actionlisteners
        final JButton btnSaveResults = new JButton("Speichern der Ergebnisse");
        btnSaveResults.setEnabled(false);
        pnlControlOptionArea.add(btnSaveResults);

        // ********* START DER LISTENER **************

        // Actionlistener für das Setzen der Verarbeitungsmethode "sequential"
        rbtnProcessType2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PropertyHelper.setProperty("planingPeriod.planingMode", "sequential");
            }
        });

        // Actionlistener für das Setzen der Verarbeitungsmethode "Random"
        rbtnProcessType1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PropertyHelper.setProperty("planingPeriod.planingMode", "random");
            }
        });

        // Actionlistener für das Setzen des Trennzeichens ","
        rbtnOutputSeperator1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PropertyHelper.setProperty("outputData.dataSetSeperator", ",");
            }
        });

        // Actionlistener für das Setzen des Trennzeichens ";"
        rbtnOutputSeperator2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PropertyHelper.setProperty("outputData.dataSetSeperator", ";");
            }
        });

        // Itemlistener für das Setzen der Checkbox für CSV
        cboxOutputFormat1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateCSV"))) {
                    PropertyHelper.setProperty("outputData.generateCSV", "false");
                    rbtnOutputSeperator1.setEnabled(false);
                    rbtnOutputSeperator2.setEnabled(false);
                }
                else {
                    PropertyHelper.setProperty("outputData.generateCSV", "true");
                    rbtnOutputSeperator1.setEnabled(true);
                    rbtnOutputSeperator2.setEnabled(true);
                }
            }
        });

        // Itemlistener für das Setzen der Checkbox für HTML
        cboxOutputFormat2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateHTML"))) {
                    PropertyHelper.setProperty("outputData.generateHTML", "false");
                }
                else {
                    PropertyHelper.setProperty("outputData.generateHTML", "true");
                }
            }
        });

        // Actionlistener für die Generierung des MenuPlans und der Einkaufsliste
        btnStartProcess.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO (Tim Sahling) wenn ich mich nicht täusche hast du das eingebaut, habe das Textfeld erstmal als
                // eingabemaske deaktiviert, das die Veränderung des Textinhalts nicht in die Varbeitung übernommen wird
                application.setInputFiles(displayInputFiles.getText());
                try {
                    if (application.fillBases()) {
                        application.buildMenuPlan();
                        application.buildShoppingList();

                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finishedProcess = true;
                ApplicationGUI.refreshCostLabel();
                ApplicationGUI.refreshPreviewTable();

                if (!btnSaveResults.isEnabled()) {
                    btnSaveResults.setEnabled(true);
                    btnStartProcess.setText("Neu berechnen");
                }

            }

        });

        // Actionlistener für die Ausgabe der Dateien
        btnSaveResults.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateHTML")) ||
                        Boolean.parseBoolean(PropertyHelper.getProperty("outputData.generateCSV"))) {
                    try {
                        application.outputApplicationResult();
                        JOptionPane.showMessageDialog(null,
                                "Die Speichern der Dateien war erfolgreich.", "Speichern erfolgreich",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        // TODO(Marcel) handle this exception properly
                        e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Bitte wählen sie mindestens ein Ausgabeformat!", "Kein Ausgabeformat angewählt",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        // ********* ENDE DER LISTENER *****************

        frame.setVisible(true);
        frame.toFront();
    }

    private static void refreshPreviewTable() {
        // TODO(Marcel) provide sensible implementation
        System.out.println("zu implementieren: Tabellenmodell aktualisieren");
        tablePreviewContent = generatePreviewContent();
        DefaultTableModel model = new DefaultTableModel(tablePreviewContent, tablePreviewHeader);
        tblMenuPlan.setModel(model);
    }

    private static void refreshCostLabel() {
        // TODO(Marcel) provide sensible implementation
        Amount calculateTotalPrice = application.getShoppingList().calculateTotalPrice();

        lblPreviewText3.setText(FileHelper.formatBD(calculateTotalPrice.getValue()) + " "
                + calculateTotalPrice.getUnit().getName());

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
        public void windowClosing(WindowEvent event) {

            int option = JOptionPane.showConfirmDialog(null, "Möchten Sie die Applikation beenden?", "Beenden",
                    JOptionPane.OK_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    public static String[][] generatePreviewContent() {
        Canteen[] canteens = application.getCanteens();
        int mealsInList = 0;
        int counterx = 0;
        int countery = 0;

        // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
        for (Canteen canteen : canteens) {
            List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();
            mealsInList = mealsInList + (mealsSortedByDate.size() / mealsPerDay);
        }
        // Erzeugen StringArrays in welches die Ergebnisse geschrieben werden
        String[][] outputString = new String[mealsInList][mealsPerDay + 2];

        for (Canteen canteen : canteens) {
            // auslesen des Kantinen-Namens
            String canteenName = canteen.getLocation().getName();
            countery = 0;

            // typisieren von Variablen für Datensätze der Gerichte und Datum
            String meal;
            String date;

            // List erstellen, in der die Gerichte nach Datum 1zu1 zugeordnet sind
            List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();

            // Defintion einer Variable für die Abfrage, ob bereits ein Datum durchlaufen wurde
            String previousDate = null;

            if (mealsSortedByDate != null) {
                // für jedes Gericht (meal) in der Liste werden das Datum und der Namen ausgelesen
                for (Meal sortedMeal : mealsSortedByDate) {
                    date = FileHelper.shortendDate(sortedMeal.getDate());
                    meal = sortedMeal.getRecipe().getName();
                    // wenn previusDate noch nicht gefüllt ist (null) starte erste Zeile in Ausgabedatei
                    // indem erstes Datum und erstes Gericht in Ausgabepuffer angehangen werden
                    if (previousDate == null) {
                        outputString[counterx][countery] = canteenName;
                        countery++;
                        outputString[counterx][countery] = date;
                        countery++;
                        outputString[counterx][countery] = meal;
                        countery++;
                        // setze Variable previousDate auf aktuelles Datum
                        previousDate = date;
                    }
                    // wenn previous Date bereits mit einem Datum gefüllt ist rufe folgenden Strang auf
                    else {
                        // wenn vorhergehendes Datum aktuellem Datum entspricht haenge
                        // aktuelles Gericht in gleiche Zeile in Ausgabepuffer an
                        if (date.equals(previousDate)) {
                            outputString[counterx][countery] = meal;
                            countery++;
                        }
                        // ansonsten
                        else {
                            // haenge neue Zeile an und starte mit neuem Datum und erstem Gericht
                            counterx++;
                            countery = 0;
                            outputString[counterx][countery] = canteenName;
                            countery++;
                            outputString[counterx][countery] = date;
                            countery++;
                            outputString[counterx][countery] = meal;
                            countery++;
                            // setze Variable previousDate auf aktuelles Datum
                            previousDate = date;
                        }
                    }
                }
                counterx++;
            }

        }

        return outputString;
    }

}
