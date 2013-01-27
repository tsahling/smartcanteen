PROJECT TITLE: 
SmartCanteen

PURPOSE OF PROJECT: 
Ermittlung optimaler Speisepläne für die Kantinen der VAWi GmbH und Erstellung einer kostenoptimalen Einkaufsliste

VERSION or DATE: 
1.0 am 30.01.2013

HOW TO START THIS PROJECT: 
java -jar SmartCanteen.jar [--help] [--inputFiles=Filename;Filename;Filename] [--props=PathToProperty=Value;PathToProperty=Value;PathToProperty=Value]

AUTHORS: 
Marcel Baxmann, Francesco Luciano und Tim Sahling

USER INSTRUCTIONS:

Die Applikation SmartCanteen läuft grundsätzlich vollständig automatisch ab und generiert, bei übergebenen Eingabedaten, die 
erwarteten Ergebnisse. Einige Einstellungen lassen sich über den Parameter --props von außen in die Applikation übergeben, wodurch
das Ergebnis der Applikation individualisiert werden kann. Folgende Einstellungen sind verfügbar:

Konsolenanwendung:

Tim hier schreiben

planingPeriod.planingMode=sequential|random

outputData.LayoutMenuePlan=grouped|single
outputData.generateCSV=true|false
outputData.generateHTML=true|false
outputData.dataSetSeperator=;|,

GUI-Anwendung:

Marcel hier schreiben

FEATURELISTE (über Standardanforderungen hinaus):

Auf folgende Features der Applikation, die über die Standardanforderungen hinausgehen, möchten wir an dieser Stelle 
separat hinweisen:

- Generierung von HTML Dateien (Menueplan, Einkaufsliste, Kostenuebersicht)
- HTML-Dateien für Einkaufsliste und Kostenuebersicht mit zusaetzlichen Datenaufbereitungen und Inhaltsverzeichnis
- Prüfung vor Überschreibung von Dateien (automatische Anpassung des Dateinamens -> Vorbeugung vor Datenverlust)
- Zwei Varianten für die Ausgabe des Menüplans (nach Datum gruppiert oder zweispaltig)
- GUI bitte beschreiben

Wer hat welche Programmbereiche implementiert:
 
 Francesco hier schreiben