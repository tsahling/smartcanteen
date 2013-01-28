package de.osjava.smartcanteen.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Klasse {@lin FileHelper} bietet Methoden
 * für Tätigkeiten die hinischtlich dem Schreiben von Dateien anfallen.
 * Dazu zählt unter anderem die Formatierung der zu schreibenden Daten,
 * überprüfen der Dateinamen und das Schreiben der Daten in eine Datei
 * 
 * @author Marcel Baxmann
 */

public class FileHelper {

    private static final Logger LOG = LogHelper.getLogger(FileHelper.class.getName());

    // auslesen der Pfadangabe
    private static final String PATH = PropertyHelper.getProperty("outputData.saveTo");

    /**
     * Schreiben der übergebenen Daten in Datei
     * 
     * @param String für auszugebene Daten
     * @param String für die Bezeichnung des Dateinamens
     * @param Boolean Wert ob die Datei überschrieben werden soll oder angehangen wird
     * @return liefert Wahrheitswert zurück wenn abgeschlossen
     * @throws IOException
     * @author Marcel Baxmann
     */
    public static boolean ausgebenInDatei(String ausgabeDaten, String dateiname, boolean anhaengen) throws IOException {
        // TODO (Marcel Baxmann) Prüfung der Strings auf Zeichen die HTML nicht ausgeben kann
        // TODO (Tim Sahling) bräuchte hier deine Unterstützung: Prüfung der Strings auf Zeichen die HTML nicht ausgeben
        // kann

        File file = new File(dateiname);
        FileWriter writer = new FileWriter(file, anhaengen);

        // übergebener String wird in Datei geschrieben
        writer.write(ausgabeDaten);

        // schreiben der Daten in Stream in die Datei
        writer.flush();

        // schließen des Stream
        writer.close();
        return true;
    }

    /**
     * Es wird der Dateiname generiert, hierbei wird geprüft, ob bereits eine Datei mit dem Dateinamen
     * existiert. Ist dies der Fall wird solanger der Dateiname hochgezählt,
     * bis die Datei unter einem neuen Dateinamen gespeichert werden kann
     * 
     * @return
     */
    public static String generateFilename(String customName, String fileExt) {
        // setzen der übergebenen Parameter für den Filenamen
        String dateiname = PATH + customName + fileExt;

        // Anlage eines Objekts der Klasse File mit dem generierten Dateinamen
        File file = new File(dateiname);

        // Damit die bereits erstellten Ausgabeergebnisse nicht ueberschrieben werden
        // erfolgt ein Test, ob bereits eine Datei mit dem Dateinamen exisitert.
        // Ist dies der Fall wird solange der Dateiname hochgezählt,
        // bis die Datei unter einem neuen Dateinamen gespeichert werden kann

        // solange Datei mit gesetzten Dateiname exisitert wird die For-Schleife ausgeführt (Abbruch nach 100 versuchen)
        if (file.exists()) {
            for (int i = 1; file.exists(); i++) {
                // Dateiname ergaenzen um (i)
                dateiname = PATH + customName + " (" + i + ")" + fileExt;
                // Datei mit dem angepassten Dateinamen erstellen
                file = new File(dateiname);
                // nach 100 versuchen wird abgebrochen und die Datei mit dem Zusatz (101) überschrieben
                if (i == 100) {
                    LOG.log(Level.INFO,
                            "Abbruch: Sie haben mehr als " + i + " mal die Datei abgelegt. Bitte leeren Sie ihren Ausgabeordner");
                    break;
                }
            }
            // Logausgabe wie Datei jetzt heißt inkl. Pfadangabe
            LOG.log(Level.INFO, "Datei exisitiert bereits, Name wird angepasst auf: " + file.getAbsolutePath());
        }
        else {
            // Logausgabe der Pfadangabe
            LOG.log(Level.INFO, "Datei abgelegt unter: " + file.getAbsolutePath());
        }
        // Rückgabe angepasster Dateiname
        return dateiname;
    }

    /**
     * Methode um ein Datum zu formatieren
     * in das Format dd.mm.yyyy
     * 
     * @param date
     * @return String eines Datums im Format dd.mm.yyyy
     * @author Marcel Baxmann
     */
    public static String shortendDate(Date date) {
        // Typsisierung Rückgabewert als String
        String stringDate;

        // Dateformater anlegen und Formatstruktur auf dd.mm.yyyy festlegen
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);

        // anwenden der Formatierung auf uebergebenes Datum und speicher in Rueckgabewert
        stringDate = formatter.format(date);

        // Rückgabe des Werts mit angepassten Datum
        return stringDate;
    }

    /**
     * Methode um ein BigDecimal zu formatieren
     * in das Format ###,###.00
     * Für den Wert 99999900 entspricht Ausagbe: 999.999,00
     * 
     * @param BigDecimal
     * @return String eines im Format ###,###.00
     * @author Marcel Baxmann
     */
    public static String formatBD(BigDecimal input) {
        // Formatierungsformat festlegen
        NumberFormat df = new DecimalFormat("###,###.00");
        // Formatierung auf übergebene Variable anwenden
        String formatiert = df.format(input);
        // Rückgabe des formatierten Werts
        return formatiert;
    }

    /**
     * Die Methode prüft ob unter dem angegebenen Pfad
     * die Datei zu finden ist.
     * 
     * @param String des Pfads und Dateinamens
     * @return boolean Wahrheitswert ob Bild vorhanden
     * @author Marcel Baxmann
     */
    public static boolean foundPicture(String fullPath) {
        // Standardwert für Rueckgabe setzen
        boolean foundPicture = false;

        // neues Fileobjekt erzeugen
        File test = new File(fullPath);

        // prüfen ob Fileobjekt exisitert
        foundPicture = test.exists();

        // Rueckgabe des Wahrheitswerts
        return foundPicture;
    }

    /**
     * Die Methode liest die Dateinamen der CSV-Dateien aus dem Input-Ordner aus
     * *
     * 
     * @return String
     *         der Dateinamen der CSV-Dateiem im Input-Ordner
     * @author Marcel Baxmann
     */
    public static String lookupInputFiles() {
        // StringBuilder wird erzeugt
        StringBuilder inputFiles = new StringBuilder();

        // neues Fileobjekt erzeugen
        File test = new File(PropertyHelper.getProperty("application.inputFilePath"));

        // String Array mit einer Auflistung der Datein im Ordner
        String[] fileList = test.list(new FilenameFilter() {
            // Nur Dateien filtern die mit .csv enden
            @Override
            public boolean accept(File test, String name) {
                return name.endsWith(".csv");
            }
        });

        // Null Prüfung und Übergabe Daten aus Arraay in StringBuilder
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                inputFiles.append(fileList[i] + ";");
            }
        }

        // Rueckgabe des Strings
        return inputFiles.toString();
    }
}
