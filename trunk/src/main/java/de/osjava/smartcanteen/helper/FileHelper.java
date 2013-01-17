package de.osjava.smartcanteen.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Die Klasse {@lin FileHelper} bietet Methoden
 * für Tätigkeiten die hinischtlich dem Schreiben von Dateien anfallen
 * Dies betrifft inbesondere das Schreiben von Dateien
 * 
 * @author Marcel Baxmann
 */

public class FileHelper {

    /**
     * Schreiben der übergebenen Daten in Datei
     * 
     * @param String für auszugebenen Daten für den Datenamen sowie Boolean Wert
     *            ob die Datei überschrieben werden soll oder angehangen wird
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
     * bis die Datei unter einem neuen Dateinamen gespeichert werden kann *
     * 
     * @return
     */
    public static String generateFilename(String customName, String fileExt) {
        // TODO(Marcel Baxmann) Prüfung vornehmen
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
        return dateiname;
    }

}
