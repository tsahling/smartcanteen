//////////// ACHTUNG
//////////// SPÄTER AUS DRIVE IMPORTIEREN


PROJECT TITLE: 
SmartCanteen

PURPOSE OF PROJECT: 
Ermittlung optimaler Speisepläne für die Kantinen der VAWi GmbH und Erstellung einer kostenoptimalen Einkaufs – und Kostenliste

VERSION or DATE: 
1.0 am 30.01.2013

HOW TO START THIS PROJECT: 
java -jar SmartCanteen.jar [--help] [--inputFiles=Filename;Filename;Filename] [--props=PathToProperty=Value;PathToProperty=Value;PathToProperty=Value]

AUTHORS: 
Marcel Baxmann, Francesco Luciano und Tim Sahling

SHORT USER INSTRUCTIONS:
Die Anwendung wird standardmäßig mit Benutzeroberfläche gestartet, kann aber auch grundsätzlich vollständig automatisch ablaufen und bei übergebenen Eingabedaten, die erwarteten Ergebnisse generieren. 
Einige Einstellungen lassen sich bei einem Konsolenaufruf über den Parameter --props von außen in die Applikation übergeben, wodurch die Darstellung und das Ergebnis der Applikation individualisiert werden kann.
Folgende Einstellungen sind beispielsweise verfügbar:
application.startGui=true|false|
-	true: GUI wird geladen
-	false: Konsolenanwendung starten
planingPeriod.planingMode=sequential|random

Für weitere Einstellungen bitte die „LONG USER INSTRUCTIONS“ unter „2) Konsolenanwendung“ lesen.


LONG USER INSTRUCTIONS:
Die Anwendung wird als ausführbare Jar-Datei geliefert und ist sowohl über die Konsole als auch über Doppelklick ausführbar. Im Applikationsordner „SmartCanteen“ sind zwei Ordner, die Readme als auch die Applikation „SmartCanteen.jar“ auffindbar.
Im Ordner „input“ erfolgt die Ablage der einzulesenden Dateien, die die Software bearbeiten soll. 
Im Ordner „output“ werden die Ergebnisse der Verarbeitung abgelegt. Für das Komfortfeature HTML-Ausgabe sind im Unterordner „images“ Bilder hinterlegt. Sollen die HTML-Ausgaben an andere Pfade im Dateisystem kopiert werden, ist es erforderlich den „images“-Ordner mitzukopieren.

Einige Einstellungen lassen sich bei einem Konsolenaufruf über den Parameter --props von außen in die Applikation übergeben, wodurch die Darstellung und das Ergebnis der Applikation individualisiert werden kann. 
Eine der wichtigste Properties die bei Programmstart gesetzt werden kann ist:
application.startGui=true|false|
„true“ (standard) bewirkt, dass die Applikation mit einem Grafischen User-Interface gestartet wird
„false“ bewirkt, das die Applikation als Konsolenanwendung abläuft
„“ bewirkt, eine Dialog-Abfrage bei jedem Start ob die Software in der Konsole oder mit GUI-Unterstützung ablaufen soll.


1) Konsolenanwendung: 
Die Applikation SmartCanteen läuft grundsätzlich vollständig automatisch ab und generiert, bei übergebenen Eingabedaten, die erwarteten Ergebnisse.  
Folgende Einstellungen sind verfügbar:

Tim hier schreiben

planingPeriod.planingMode=sequential|random

outputData.LayoutMenuePlan=grouped|single
outputData.generateCSV=true|false
outputData.generateHTML=true|false
outputData.dataSetSeperator=;|,




2) GUI-Anwendung:
Das grafische User-Inferface (Benutzeroberfläche) ist in die Bereiche Option (links) und Voransicht (rechts) unterteilt. 

Optionen - Eingabe-Dateien: 
Bei den Input-Optionen ist es möglich die Dateien anzugeben, welche aus dem Input-Ordner in das Programm eingelesen werden sollen. Die Dateinamen müssen wie im folgenden Beispiel dargestellt angegeben werden: hitliste.csv;preisliste_1.csv;preisliste_2.csv;preisliste_3.csv;Rezepte.csv

Optionen - Verarbeitungs-Einstellungen:
Es kann die „Menü-Verteilung auf Tage“ auf die Varianten „Zufall“ oder „Sequentiell“ eingestellt werden. 
•	Bei „Sequentiell“ werden die Gerichte bei jeder Neu-Berechnung auf die gleiche Weise in die Menü-Pläne geschrieben. Es entstehen bei gleichen Eingabe-Dateien und Verarbeitungsstellung entsprechend die gleichen Ergebnisse.
•	Bei „Zufall“ werden die Gerichte nach Zufall auf die Tage verteilt, so dass bei jeder Neu-Berechnung unterschiedliche Ergebnisse entstehen
Weitergehend kann ein Filter für die „maximalen Kosten pro Gramm je Zutat“ auf einer Skala von 0,50 bis 10,00 Euro gesetzt werden. Dies bewirkt, dass Zutaten die sehr teuer im Einkauf sind und den eingestellten Wert überschreiten nicht bei der Menü-Planung berücksichtigt werden 
Ausgabe-Einstellung:
Der User kann das „Ausgabeformat“ festlegen und entscheiden, ob er CSV-Dateien und/oder HTML-Dateien als Ausgabe generieren möchte.
Wird „CSV“ angwählt kann er zudem zwischen dem Trennzeichen „Komma [ , ]“ und „Semikolon [ ; ]“ unterscheiden. Dies erhöht die Kompatibilität für Programme, da einige Softwareprodukte CSV-Dateien nur mit Komma und andere nur mit Semikolon öffnen können. Die Standard-Einstellung ist Semikolon, so dass die Ergebnisse in Excel korrekt (getrennt in Spalten) dargestellt werden.

Optionen - Schaltflächen:
Über die Schaltfläche „Verarbeitung starten“ kann, sofern die Eingabedateien korrekt eingelesen werden konnten und diese für die Generierung eines kompletten Menü-Plans für alle Standorte ausreichen, gestartet werden.
Nach einem erfolgreichen Durchgang wird die Schaltfläche in „Neu berechnen“ umbenannt. Der User kann Einstellungen anpassen und zum Beispiel weitere Anbieter zu Berechnung hinzuziehen und danach über die Beätigung „Neu berechnen“ die Generierung erneut starten.

Hat der User einen Menü-Plan generiert der einem zusagt hat er die Möglichkeit die Ergebnisse über die Schaltfläche „Speichern der Ergebnisse“ in die definierten Ausgabeformate zu speichern. Die Ergebnisse werden in den Ordner „output“ abgelegt.

Voransicht:
Im Bereich Vor-Ansicht werden die generierten Menü-Pläne für alle Kantinen als auch die gesamten Einkaufskosten je Berechnungs-Vorgang angezeigt. So hat der User die Möglichkeit bereits vor dem Ablegen der Ergebnisse in Form von Dateien auf dem Dateisystem, einen Einblick in die berechneten Ergebnisse zu erlangen.
Bei der Betätigung der Schaltfläche „Neu berechnen“ werden die Daten in der Tabelle als auch die Kosten aktualisiert.


FEATURELISTE (über Standardanforderungen hinaus): 
Auf folgende Features der Applikation, die über die Standardanforderungen hinausgehen, möchten wir an dieser Stelle 
separat hinweisen:

- Generierung von HTML Dateien (Menueplan, Einkaufsliste, Kostenuebersicht)
- HTML-Dateien für Einkaufsliste und Kostenuebersicht mit zusaetzlichen Datenaufbereitungen und Inhaltsverzeichnis sowie mit Einbindung von Bildern
- Prüfung vor Überschreibung von Dateien (automatische Anpassung des Dateinamens -> Vorbeugung vor Datenverlust)
- GUI wie unter „LONG USER INSTRUCTION“ beschrieben
- WEITERE????


Wer hat welche Programmbereiche implementiert :
 

