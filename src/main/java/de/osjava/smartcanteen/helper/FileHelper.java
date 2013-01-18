package de.osjava.smartcanteen.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Die Klasse {@lin FileHelper} bietet Methoden
 * für Tätigkeiten die hinischtlich dem Schreiben von Dateien anfallen.
 * Dazu zählt unter anderem die Formatierung der zu schreibenden Daten,
 * überprüfen der Dateinamen und das Schreiben der Daten in eine Datei
 * 
 * @author Marcel Baxmann
 */

public class FileHelper {

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
        // TODO (Marcel Baxmann) kommentieren und auf Fehler prüfen

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
        // TODO (Marcel Baxmann) Prüfung vornehmen !!! FEHLERHANDLING FÜR path
        // auslesen der Pfadangabe
        String path = PropertyHelper.getProperty("outputData.saveTo");
        // setzen der übergebenen Parameter für den Filenamen
        String dateiname = path + customName + fileExt;

        // Anlage eines Objekts der Klasse File mit dem generierten Dateinamen
        File file = new File(dateiname);

        // Damit die bereits erstellten Ausgabeergebnisse nicht ueberschrieben werden
        // erfolgt ein Test, ob bereits eine Datei mit dem Dateinamen exisitert.
        // Ist dies der Fall wird solange der Dateiname hochgezählt,
        // bis die Datei unter einem neuen Dateinamen gespeichert werden kann

        // solange File Exisitert wird die For-Schleife ausgeführt
        if (file.exists()) {
            for (int i = 1; file.exists(); i++) {
                dateiname = path + customName + " (" + i + ")" + fileExt;
                file = new File(dateiname);
                // nach 100 versuchen wird abgebrochen und die Datei mit dem Zusatz (101) überschrieben
                if (i == 100) {
                    System.out
                            .println("Abbruch: Sie haben mehr als " + i + " mal die Datei abgelegt. Bitte leeren Sie ihren Ausgabeordner");
                    break;
                }
            }
            System.out.println("Datei exisitiert bereits, Name wird angepasst auf: " + file.getAbsolutePath());
        }
        else {
            System.out.println("Datei abgelegt unter: " + file.getAbsolutePath());
        }

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
}
