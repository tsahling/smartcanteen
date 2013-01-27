package de.osjava.smartcanteen.application.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.math.BigDecimal;
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
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import de.osjava.smartcanteen.application.Application;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.helper.FileHelper;
import de.osjava.smartcanteen.helper.NumberHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die {@link AplicationGUI} wird von der Klasse {@link Aplication} verwendet.
 * Sie ist für die Generierung der grafischen Benutzeroberfläche zuständig.
 * Sie bietet Steuerungmöglichkeitn für den Start des Berechnungsvorgangs,
 * Eingabe von Variablen für die Verarbeitung als auch die Ausgabe.
 * In Tabellenform werden exemplarisch für die Ergebnisse der Berechnungsvorgänge
 * die erstellten Menüpläne sowie als Einzelangabe die Gesamtkosten angezeigt.
 * Nach einmaliger Generierung der Ergebnisse können erneut Pläne generiert werden,
 * es erfolgt eine automatische Aktualisierung der Daten in der Voransichgt.
 * * Weiterhin kann die Ausgabe der aktuell generierten und in der Voransicht
 * dargestellten Daten in Dateien anstossen.
 * 
 * @author Marcel Baxmann
 */
public class ApplicationGUI {
    // Angabe der Breite des Fensters aus Properties auslesen und speichern in lokale Variable (geparstes int)
    private static final int PROP_GUI_WINDOWWIDTH = Integer.parseInt(PropertyHelper.getProperty("gui.windowWidth"));

    // Angabe der Höhe des Fensters aus Properties auslesen und speichern in lokale Variable (geparstes int)
    private static final int PROP_GUI_WINDOWHEIGHT = Integer.parseInt(PropertyHelper.getProperty("gui.windowHeight"));

    // Anzahl der Gerichte pro Tag aus Properties auslesen und speichern in lokale Variable (geparstes int)
    private static final int PROP_PLANINGPERIOD_MEALSPERDAY = Integer.parseInt(PropertyHelper
            .getProperty("planingPeriod.mealsPerDay"));

    private static final String PROP_MESSAGE_CLOSEOPERATION_MSG = PropertyHelper
            .getProperty("message.closeOperation.message");
    private static final String PROP_MESSAGE_CLOSEOPERATION_TITLE = PropertyHelper
            .getProperty("message.closeOperation.title");

    private static final String PROP_MESSAGE_SAVEOPERATION_MSG = PropertyHelper
            .getProperty("message.saveOperation.message");
    private static final String PROP_MESSAGE_SAVEOPERATION_TITLE = PropertyHelper
            .getProperty("message.saveOperation.title");

    private static final String PROP_MESSAGE_NOOUTPUTFORMATSELECTED_MSG = PropertyHelper
            .getProperty("message.noOutputFormatSelected.message");
    private static final String PROP_MESSAGE_NOOUTPUTFORMATSELECTED_TITLE = PropertyHelper
            .getProperty("message.noOutputFormatSelected.title");

    private static final String PROP_MESSAGE_WRONGORMISSINGINPUTFILES_MESSAGE = PropertyHelper
            .getProperty("message.wrongOrMissingInputFiles.message");
    private static final String PROP_MESSAGE_WRONGORMISSINGINPUTFILES_TITLE = PropertyHelper
            .getProperty("message.wrongOrMissingInputFiles.title");

    private Application application;
    private JLabel lblPreviewText2;
    private JLabel lbProcessText4;
    private String[] tablePreviewHeader = new String[PROP_PLANINGPERIOD_MEALSPERDAY + 2];
    private String[][] tablePreviewContent = null;
    private JTable tblMenuPlan;

    /**
     * Standardkonstruktor
     * Übergabe des Objekts application in eine lokale Variable
     * 
     * @param application
     */
    public ApplicationGUI(Application application) {
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
        frame.setSize(PROP_GUI_WINDOWWIDTH, PROP_GUI_WINDOWHEIGHT);
        frame.setMinimumSize(new Dimension(PROP_GUI_WINDOWWIDTH, PROP_GUI_WINDOWHEIGHT));
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

        // Ausagbebereich
        JPanel pnlOutputArea = new JPanel();
        pnlOutputArea.setBorder(BorderFactory.createTitledBorder("Voransicht"));
        pnlOutputArea.setLayout(new FlowLayout());
        frame.add(pnlOutputArea);

        // Generierung der Tabelle in der Voransicht
        JLabel lblPreviewText1 = new JLabel("Erstellter Menüplan für alle Kantinen nach Datum:");
        pnlOutputArea.add(lblPreviewText1);
        // Überschrift Spalte der Tabelle generieren
        tablePreviewHeader[0] = "Kantine";
        tablePreviewHeader[1] = "Datum";
        for (int i = 2; i < tablePreviewHeader.length; i++) {
            tablePreviewHeader[i] = "Gericht " + (i - 1);
        }
        // StandardModell mit vorgegebenen Daten füllen
        DefaultTableModel model = new DefaultTableModel(tablePreviewContent, tablePreviewHeader);
        // Tabelle initalisieren und Modell übergeben
        tblMenuPlan = new JTable(model);

        // Deaktivierung von Interaktionsmäglichkeiten mit der Tabelle
        tblMenuPlan.setCellSelectionEnabled(false);
        tblMenuPlan.setEnabled(false);
        tblMenuPlan.setColumnSelectionAllowed(false);
        tblMenuPlan.setDragEnabled(false);
        tblMenuPlan.getTableHeader().setReorderingAllowed(false);

        // Tabelle mit Scroll-Balken versehen und dem Panel für die Voransicht hinzufügen
        JScrollPane scrlOutputArea = new JScrollPane(tblMenuPlan);
        pnlOutputArea.add(scrlOutputArea);

        // Label für die Ausgabe der Gesamtkosten erstellen dun auf Panel für Voransicht einfügen
        lblPreviewText2 = new JLabel("Gesamtkosten für den Einkauf: noch nicht berechnet");
        pnlOutputArea.add(lblPreviewText2);

        // **** LAYOUT: OPTIONSUNTERTEILUNG -- Unterteilen des Optionsbereich in die Kategorien Input-Option,
        // Process-Option und Output-Option ****
        // Input-Option
        JPanel pnlInputOptionArea = new JPanel();
        pnlInputOptionArea.setLayout(new GridLayout(2, 1));
        pnlInputOptionArea.setBorder(BorderFactory.createTitledBorder("Eingabe-Dateien"));
        pnlOptionArea.add(pnlInputOptionArea);

        // Process-Option
        JPanel pnlProcessOptionArea = new JPanel();
        pnlProcessOptionArea.setLayout(new GridLayout(2, 3));
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
        JLabel lblInputText = new JLabel(
                "<html>Bitte geben Sie die Eingabedateien, die für die<br> Datenverarbeitung herangezogen werden sollen, wie folgt ein: " +
                        "ABC.csv;DEF.csv;XYZ.csv;</html>");
        pnlInputOptionArea.add(lblInputText);

        final JTextArea displayInputFiles = new JTextArea(application.getInputFiles(), 2, 1);

        pnlInputOptionArea.add(displayInputFiles);
        displayInputFiles.setLineWrap(true);
        displayInputFiles.setFocusable(true);

        // **** GENERIERUNG: OPTION-PROCESS -- Füllen des Bereichs Process-Option mit Einstellmöglichkeiten ****

        // Verarbeitungsmethode: RadioButton für Abfragegenerieren
        JLabel lblProcessText = new JLabel("<html>Menü-Verteilung auf Tage:</html>");
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

        // generieren JSlider für das Setzen des Wertes maximale Kosten je Position 1G
        JLabel lbProcessText3 = new JLabel(
                "<html>max. Kosten <br>pro Gramm je Zutat:<br>(z.B. Filterung Safran)</html>");
        pnlProcessOptionArea.add(lbProcessText3);

        BigDecimal priceForOneUnit = NumberHelper.multiply(
                new BigDecimal(PropertyHelper.getProperty("ingredient.priceForOneUnit.max")), BigDecimal.valueOf(100));

        final JSlider sldPriceForOneUnitMax = new JSlider(JSlider.HORIZONTAL, 50, 1000, priceForOneUnit.intValue());

        // Lininen in Slider anzeigen (Major bei jedem 5ten Euro und Minor bei jedem Euro)
        sldPriceForOneUnitMax.setMajorTickSpacing(500);
        sldPriceForOneUnitMax.setMinorTickSpacing(100);
        sldPriceForOneUnitMax.setPaintTicks(true);

        pnlProcessOptionArea.add(sldPriceForOneUnitMax);

        // generieren Label für gesetzten Wert in Euro
        lbProcessText4 = new JLabel(FileHelper.formatBD(new BigDecimal(PropertyHelper
                .getProperty("ingredient.priceForOneUnit.max"))) + " Euro");
        pnlProcessOptionArea.add(lbProcessText4);

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

        // ChangeListener für den Slider
        sldPriceForOneUnitMax.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                int value = sldPriceForOneUnitMax.getValue();
                BigDecimal bgValue = BigDecimal.valueOf(value);
                bgValue = bgValue.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN);
                lbProcessText4.setText(FileHelper.formatBD(bgValue) + " Euro");
                PropertyHelper.setProperty("ingredient.priceForOneUnit.max", bgValue.toString());
            }
        });

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
                // auslesen des Textfelds von der GUI
                String inputFilesFromGui = displayInputFiles.getText();

                // wenn das Textfeld leer oder null ist wird eine Fehlermeldung als Dialog ausgegeben
                if (inputFilesFromGui != null && !inputFilesFromGui.isEmpty()) {
                    // wenn Daten in Textefeld sind werden diese als Eingabeparameter an das Objekt Application
                    // übergeben
                    application.setInputFiles(inputFilesFromGui);

                    // es wird FillBases ausgeführt (Verarbeitung der Eingabeparameter)
                    // wenn korrekt verarbeitet wird True zurückgeliefert und die Bearbeitung gestartet
                    try {
                        if (application.fillBases()) {
                            application.buildMenuPlan();
                            application.buildShoppingList();
                            refreshCostLabel();
                            refreshPreviewTable();

                            if (!btnSaveResults.isEnabled()) {
                                btnSaveResults.setEnabled(true);
                                btnStartProcess.setText("Neu berechnen");
                            }
                        }
                        // wenn nicht wird Fehler-Dialog ausgegeben
                        else {
                            wrongFilesDialog();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (IllegalArgumentException iae) {
                        iae.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    wrongFilesDialog();
                }

            }

            private void wrongFilesDialog() {
                JOptionPane.showMessageDialog(null,
                        PROP_MESSAGE_WRONGORMISSINGINPUTFILES_MESSAGE, PROP_MESSAGE_WRONGORMISSINGINPUTFILES_TITLE,
                        JOptionPane.ERROR_MESSAGE);
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
                                PROP_MESSAGE_SAVEOPERATION_MSG, PROP_MESSAGE_SAVEOPERATION_TITLE,
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        // TODO(Marcel) handle this exception properly
                        e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            PROP_MESSAGE_NOOUTPUTFORMATSELECTED_MSG, PROP_MESSAGE_NOOUTPUTFORMATSELECTED_TITLE,
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        // ********* ENDE DER LISTENER *****************

        frame.setVisible(true);
        frame.toFront();
    }

    /**
     * Methode die den JTable auf mit einem neuen model füllt
     */
    private void refreshPreviewTable() {
        tablePreviewContent = generatePreviewContent();
        DefaultTableModel model = new DefaultTableModel(tablePreviewContent, tablePreviewHeader);
        tblMenuPlan.setModel(model);
    }

    /**
     * Methode die das Label für die Ausgabe der Gesamtkosten neu setzt
     */
    private void refreshCostLabel() {
        Amount calculateTotalPrice = this.application.getShoppingList().calculateTotalPrice();

        lblPreviewText2.setText("<html>Gesamtkosten für den Einkauf: <u>" + FileHelper.formatBD(calculateTotalPrice
                .getValue()) + " " + calculateTotalPrice.getUnit().getName() + " </u></html>");

    }

    /**
     * ermittelt die Bildschirmmitte anhand der Auflösung und gibt diese als Point zurück
     * 
     * @return Point
     *         der Bildschrimmitte
     */
    private Point getDisplayCenter() {

        // auslesen der Bildschirmgröße
        Dimension display = Toolkit.getDefaultToolkit().getScreenSize();

        // Erstellung eines Punkt-OBjekts
        Point displayCenter = new Point(0, 0);
        // Berechnung der Bildschrimmitte
        displayCenter.x = (int) ((display.getWidth() - PROP_GUI_WINDOWWIDTH) / 2);
        displayCenter.y = (int) ((display.getHeight() - PROP_GUI_WINDOWHEIGHT) / 2);

        // Rückggabe Point-Objekt mit Bildschirmmitte
        return displayCenter;
    }

    /**
     * Inner-Class zum Abfragen des Fenster-Schließ-Events
     */
    private class DialogWindowClosingListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            // Öffenen eines Abfragedialogs ob die Applikation wirklich beendet werden soll
            int option = JOptionPane.showConfirmDialog(null, PROP_MESSAGE_CLOSEOPERATION_MSG,
                    PROP_MESSAGE_CLOSEOPERATION_TITLE,
                    JOptionPane.OK_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * Methode um auf Basis des aktuellen Objektzustands von application
     * ein Array zu generieren welches die Daten für die Menüpläne aller
     * vorhandenen Kantinen enthält
     * 
     * @return String[][]
     *         ein Array mit Daten für die Menüpläne aller
     *         vorhandenen Kantinen
     */
    private String[][] generatePreviewContent() {
        Canteen[] canteens = application.getCanteens();
        int mealsInList = 0;
        int aryPosX = 0;
        int aryPosY = 0;

        // aufsummieren, wie viele Zeilen das Array für alle Kantinen benötigt
        for (Canteen canteen : canteens) {
            List<Meal> mealsSortedByDate = canteen.getMenuPlan().getMealsSortedByDate();
            mealsInList = mealsInList + (mealsSortedByDate.size() / PROP_PLANINGPERIOD_MEALSPERDAY);
        }
        // Erzeugen 2D-StringArray in welches die Ergebnisse geschrieben werden
        String[][] outputString = new String[mealsInList][PROP_PLANINGPERIOD_MEALSPERDAY + 2];

        for (Canteen canteen : canteens) {
            // auslesen des Kantinen-Namens
            String canteenName = canteen.getLocation().getName();
            // für jede neue Kantine den Spaltenzähler zurücksetzen
            aryPosY = 0;

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
                        // schreibe Kantinenname in erste Spalte des Arrays und gehe zu nächster Spalte
                        outputString[aryPosX][aryPosY] = canteenName;
                        aryPosY++;
                        // schreibe Datum in zweite Spalte des Arrays und gehe zu nächster Spalte
                        outputString[aryPosX][aryPosY] = date;
                        aryPosY++;
                        // schreibe Gericht in dritte Spalte des Arrays und gehe zu nächster Spalte
                        outputString[aryPosX][aryPosY] = meal;
                        aryPosY++;
                        // setze Variable previousDate auf aktuelles Datum
                        previousDate = date;
                    }
                    // wenn previous Date bereits mit einem Datum gefüllt ist rufe folgenden Strang auf
                    else {
                        // wenn vorhergehendes Datum aktuellem Datum entspricht
                        if (date.equals(previousDate)) {
                            // schreibe Gericht in nächste Spalte des Arrays und gehe zu nächster Spalte
                            outputString[aryPosX][aryPosY] = meal;
                            aryPosY++;
                        }
                        // ansonsten
                        else {
                            // haenge wechsele in nächste Zeile und setze Spaltenzähler zurück
                            aryPosX++;
                            aryPosY = 0;
                            // schreibe Kantinenname in erste Spalte des Arrays und gehe zu nächster Spalte
                            outputString[aryPosX][aryPosY] = canteenName;
                            aryPosY++;
                            outputString[aryPosX][aryPosY] = date;
                            // schreibe Datum in zweite Spalte des Arrays und gehe zu nächster Spalte
                            aryPosY++;
                            // schreibe Gericht in dritte Spalte des Arrays und gehe zu nächster Spalte
                            outputString[aryPosX][aryPosY] = meal;
                            aryPosY++;
                            // setze Variable previousDate auf aktuelles Datum
                            previousDate = date;
                        }
                    }
                }
                // wenn alle Daten der Liste einer Kantine durchlaufen sind setze auf näcshte Zeile in Array
                aryPosX++;
            }

        }
        // Liefere das generierte Array zurück
        return outputString;
    }

}
