PROJECT TITLE
SmartCanteen 

PURPOSE OF PROJECT
Ermittlung optimaler Speisepläne für die Kantinen der VAWi GmbH und Erstellung einer kostenoptimalen Einkaufs- und Kostenliste.

VERSION & DATE
Version 1.0 vom 30.01.2013

HOW TO START THIS PROJECT
java
-jar
SmartCanteen.jar
[--help] 
[--inputFiles=Filename;Filename;Filename]
[--props=PathToProperty=Value;PathToProperty=Value;PathToProperty=Value]

AUTHORS
Marcel Baxmann, Francesco Luciano und Tim Sahling

SHORT USER INSTRUCTIONS
Die Anwendung wird standardmäßig mit Benutzeroberfläche gestartet, kann aber auch grundsätzlich vollständig automatisch ablaufen und bei übergebenen Eingabedaten, die erwarteten Ergebnisse generieren.
Einige Einstellungen lassen sich bei einem Konsolenaufruf über den Parameter --props von außen in die Applikation übergeben, wodurch die Darstellung und das Ergebnis der Applikation individualisiert werden kann.
Einzulesende Dateien im Ordner “input” ablegen. Die Ausgabe erfolgt im Ordner “output”.

TIPS FOR USING
Ein kostenoptimaler Speiseplan kann, unter Berücksichtigung der Beispiel-Daten (im Input-Ordner) erreicht werden, indem die Parameter auf 
-“ingredient.priceForOneUnit.max” (in der GUI “max. Kosten pro Gramm je Zutat”)  auf 0,50 Euro und 
-“planingPeriod.planingMode” (in der Gui: Menü-Verteilung auf Tage”) auf Sequentiell gestellt werden

Auf diese Weise werden teure Zutaten, wie zum Beispiel Safran ausgefiltert und Gerichte die ohne Safran nicht gekocht werden können nicht im Speiseplan berücksichtigt.

Innerhalb der Verarbeitung der Beispiel-Daten möchten wir auf den Umstand hinweisen, dass bei der sequentiellen Abarbeitung die Kosten meistens unter den Kosten der 
randomisierten Variante liegen. Das liegt daran, dass die Priorisierung der Gerichte für jeden Tag im zufälligen Modus eine andere sein kann als im sequentiellen Modus. 
Dementsprechend kann es, auf Basis der Anforderung, dass das Gericht mit der höchsten Priorität öfter gekocht werden soll als die anderen beiden Gerichte an dem Tag, 
dazu kommen, dass teurere Gerichte im randomisierten Modus öfter mit oberster Priorität gekocht werden als im sequentiellen Modus.

PROPERTIES PARAMETER
Folgende Einstellungen sind verfügbar:
application.startGui=true|false|

-true: GUI wird geladen

-false: Konsolenanwendung starten
-keine Angabe: Abfrage ob Konsole oder GUI
planingPeriod.planingMode=sequential|random
-sequential: Tage der Planungsperiode werden nacheinander ausgewählt
-random: Tage der Planungsperiode werden zufällig ausgewählt
ingredient.priceForOneUnit.max=Zahlenwert
-Wert muss größer 50 (in Cent) sein und als Dezimalstelle muss ein Punkt [ . ] verwendet werden
outputData.generateCSV=true|false
-true: CSV-Dateien werden ausgegeben
-false: CSV-Dateien werden NICHT ausgegeben
outputData.generateHTML=true|false
-true: HTML-Dateien werden ausgegeben
-false: HTML-Dateien werden NICHT ausgegeben
outputData.dataSetSeperator=;|,
-Semikolon [ ; ]:  CSV-Daten werden mit einem Semikolon [ ; ] getrennt
-Komma [ , ]: CSV-Daten werden mit einem Komma [ , ] getrennt


LONG USER INSTRUCTIONS
Die Anwendung wird als ausführbare JAR-Datei geliefert und ist sowohl über die Konsole als auch über Doppelklick ausführbar. Im Applikationsordner „SmartCanteen“ sind zwei Ordner, die README.txt als auch die eigentliche Applikation „SmartCanteen.jar“ auffindbar. 
Im Ordner „input“ erfolgt die Ablage der einzulesenden Dateien, die die Software bearbeiten soll.
Im Ordner „output“ werden die Ergebnisse der Verarbeitung abgelegt. Für das Komfortfeature HTML-Ausgabe sind im Unterordner „images“ Bilder hinterlegt. Sollen die HTML-Ausgaben an andere Pfade im Dateisystem kopiert werden, ist es erforderlich den „images“-Ordner mitzukopieren. Andernfalls werden die Bilder in den HTML-Dokumenten nicht korrekt dargestellt.

1) Konsolenanwendung:
Die Applikation SmartCanteen läuft grundsätzlich vollständig automatisch ab und generiert, bei übergebenen Eingabedaten, die erwarteten Ergebnisse. Folgende Parameter beim Aufruf des Programms stehen zur Verfügung:

# --help -> für Instruktionen wie die Applikation zu bedienen ist
# --inputFiles=ABC.csv;DEF.csv; -> für die Übergabe der zu verarbeitenden Dateien
# --props=PathToProperty=Value; -> für das initiale Setzen von Applikationsparametern die das Verhalten der Applikation steuern und individualisieren können.

Für einen vollautomatischen Ablauf der Applikation mit Hilfe der Beispieldaten ist folgender Befehl zu verwenden:

java -jar SmartCanteen.jar
--inputFiles=hitliste.csv;preisliste_1.csv;preisliste_2.csv;preisliste_3.csv;preisliste_4.csv;preisliste_5.csv;preisliste_6.csv;preisliste_7.csv;Rezepte.csv;
--props=application.startGui=false


Es sind bewusst nicht alle Einstellungen der Anwendung von außen zur Laufzeit konfigurierbar (wird erreicht durch die Deklarierung als static final) 
um grundsätzlich einen reibungslosen Ablauf der Applikation zu gewährleisten. Trotzdem kann über eine geeignete IDE wie z.B. eclipse die smartcanteen.properties 
angepasst und damit das Ergebnis individualisiert werden.

2) GUI-Anwendung:
Das grafische User-Inferface (Benutzeroberfläche) ist in die Bereiche Option (links) und Voransicht (rechts) unterteilt. Dargestellt in der folgenden Abbildung.

Optionen - Eingabe-Dateien:
Bei den Input-Optionen ist es möglich die Dateien anzugeben, welche aus dem Input-Ordner in das Programm eingelesen werden sollen.
 
Die Dateinamen müssen wie im folgenden Beispiel dargestellt angegeben werden: hitliste.csv;preisliste_1.csv;preisliste_2.csv;preisliste_3.csv;Rezepte.csv

Optionen - Verarbeitungs-Einstellungen:
Es kann die „Menü-Verteilung auf Tage“ auf die Varianten „Zufall“ oder „Sequentiell“ eingestellt werden.
Bei „Sequentiell“ werden die Gerichte bei jeder Neu-Berechnung auf die gleiche Weise in die Menü-Pläne geschrieben. 
Es entstehen bei gleichen Eingabe-Dateien und Verarbeitungsstellung entsprechend die gleichen Ergebnisse.

Bei „Zufall“ werden die Gerichte nach Zufall auf die Tage verteilt, so dass bei jeder Neu-Berechnung unterschiedliche Ergebnisse entstehen.

Weitergehend kann ein Filter für die „maximalen Kosten pro Gramm je Zutat“ auf einer Skala von 0,50 bis 10,00 Euro gesetzt werden. Dies bewirkt, dass 
Zutaten die sehr teuer im Einkauf sind und den eingestellten Wert überschreiten nicht bei der Menü-Planung berücksichtigt werden.

Optionen - Ausgabe-Einstellung:
Der User kann das „Ausgabeformat“ festlegen und entscheiden, ob er CSV-Dateien und/oder HTML-Dateien als Ausgabe generieren möchte.

Wird „CSV“ angewählt kann er zudem zwischen dem Trennzeichen „Komma [ , ]“ und „Semikolon [ ; ]“ unterscheiden. Dies erhöht die Kompatibilität 
für Programme, da einige Softwareprodukte CSV-Dateien nur mit Komma und andere nur mit Semikolon öffnen können. Die Standard-Einstellung ist Semikolon, 
so dass die Ergebnisse in Excel korrekt (getrennt in Spalten) dargestellt werden.

Optionen - Schaltflächen:
Über die Schaltfläche „Verarbeitung starten“ kann, sofern die Eingabedateien korrekt eingelesen werden konnten und diese für die Generierung 
eines kompletten Menü-Plans für alle Standorte ausreichen, gestartet werden.

Nach einem erfolgreichen Durchgang wird die Schaltfläche in „Neu berechnen“ umbenannt. Der User kann Einstellungen anpassen und zum Beispiel weitere 
Anbieter zu Berechnung hinzuziehen und danach über die Beätigung „Neu berechnen“ die Generierung erneut starten.

Hat der User einen Menü-Plan generiert der einem zusagt hat er die Möglichkeit die Ergebnisse über die Schaltfläche „Speichern der Ergebnisse“ in die 
definierten Ausgabeformate zu speichern. Die Ergebnisse werden in den Ordner „output“ abgelegt.

Voransicht:
Im Bereich Vor-Ansicht werden die generierten Menü-Pläne für alle Kantinen als auch die gesamten Einkaufskosten je Berechnungs-Vorgang angezeigt. 
So hat der User die Möglichkeit bereits vor dem Ablegen der Ergebnisse in Form von Dateien auf dem Dateisystem, einen Einblick in die berechneten Ergebnisse zu erlangen.

Bei der Betätigung der Schaltfläche „Neu berechnen“ werden die Daten in der Tabelle als auch die Kosten aktualisiert.

FEATURES
Auf folgende Features der Applikation, die über die Standardanforderungen hinausgehen, möchten wir an dieser Stelle gesondert hinweisen:

-Generierung von HTML Dateien (Menüplan, Einkaufsliste, Kostenübersicht)
-HTML-Dateien für Einkaufsliste und Kostenübersicht mit zusätzlichen Datenaufbereitungen und Inhaltsverzeichnis über Anker
-HTML-Dateien für Menüplan mit Einbindung von Bildern sofern diese im Output-Ordner unter dem angegebenen Unterpfad für die Bilder gefunden werden können
-Prüfung vor Überschreibung von Ausgabe-Dateien
	*automatische Anpassung des Dateinamens
	*Vorbeugung vor Datenverlust
-Implementierung einer GUI mit Swing
	*Features wie unter „LONG USER INSTRUCTION“ beschrieben
-Durch die Gui wird eine Abfrage ausgeführt, welche Dateien im Dateisystem liegen und diese im Textfenster ausgegeben, 
	so dass der User nicht selbst die Dateinamen alleine angeben muss. Sind Parameter an die Anwendung übergeben worden werden stattdessen 
	diese angezeigt. Wurden weder Parameter übergeben noch Dateien im Input-Ordner gefunden wird der User benachrichtigt.
-Globale Applikationseinstellungen, die aus einer externen Datei gelesen werden und dort auch überschrieben werden können. Dementsprechend kann 
	die Anwendung sehr komfortabel und einfach an individuelle Bedürfnisse oder Anforderungen angepasst werden.
-Über die GUI Trennzeichen für die Erzeugung der CSV-Dateien „Komma [ , ]“ und „Semikolon [ ; ]“ auswählbar. 
-Logging-Konzept für ein einfacheres Debugging und eine formatierte Ausgabe von Meldungen der Applikation
-Verschiedene Modi für die Generierung der Speisepläne bzw. der Auswahl des Tages der Planungsperiode an dem ein Rezept gekocht werden soll. Die Modi sind:
	*Sequentiell -> für ein deterministisches Ergebnis unter der Voraussetzung gleicher Eingangsdateien. Tage der Planungsperiode werden von 
	Tag 1 bis Tag 15 nacheinander durchlaufen
	*Zufall -> für die zufällige Auswahl eines Tages. Es wird versucht an zufälligen Tagen der Planungsperiode ein Rezept einzufügen. 
	Dadurch kann das Ergebnis sehr gut individualisiert werden, wenn es dem Planer nicht gefällt.
-Ökonomische Begrenzung der Kosten eines Speiseplans indem Rezepte gefiltert werden können, deren Einzelzutaten dem Planer zu teuer sind (bspw. Safran).
-Um die Leistungsfähigkeit und Zufriedenheit der Mitarbeiter hochzuhalten, gehen wir bei der Speiseplangenerierung nach den beliebtesten Rezepten 
	und versuchen diese in die Planungsperiode einzufügen

KLASSENDIAGRAMM

WHO & WHAT
Wir haben gemeinsam als Gruppe drei Teilbereiche innerhalb des Softwareprojekts identifiziert. Die identifizierten Teilbereiche  und die Zuständigkeit liegt bei:

1. Einlesen der Rohdaten und erstellen von fachspezifischen Objekten (Francesco Luciano).
2. Implementierung der Optimierungsalgorithmen zur Erstellung optimaler Speisepläne und kostenoptimaler Einkaufs- und Kostenlisten (Tim Sahling).
3. Darstellung & Ausgabe der Ergebnisse in verschiedene Formate (Marcel Baxmann)

Es gibt an einigen Stellen kleine Abweichungen bei den Klassenzugehörigkeiten die über den Tag @author im JavaDoc festgehalten sind.

PERSPECTIVES
In einer zweiten Version der Applikation könnten wir uns weitere Features vorstellen wie z.B.:

-Aufteilung der Zutaten und Mengen aus dem Menüplan in alle möglichen Anbieterkombinationen um danach temporäre Einkaufslisten zu generieren, 
	die auf Basis des Preises miteinander verglichen werden können und die günstigste Alternative ausgewählt wird.
-Festlegen eines Maximalpreises pro Gericht
-Anbindung externer Systeme von Anbietern um Bestellvorgänge automatisch auslösen zu können
-Ausweitung der GUI
	*um zusätzliche Steuerungsmöglichkeiten für weitere Parameter zur Individualisierung
	*Weitere Ausgabeformate, wie XML und PDF zum Import in andere Programme anbieten
	*Abfragedialog für den Speicherort der Ausgabedateien
	*Auswahlbox der im Dateisystem gefundenen Input-Dateien, damit der Benutzer nicht mehr manuell eingreifen muss 
	und damit weitere Fehlerquellen automatisch zu beseitigen
