PROJECT TITLE: SmartCanteen
PURPOSE OF PROJECT: Software um den Einkauf und die Gestaltung von Speiseplänen zu optimieren 
AUTHORS:Francesco Luciano


Rudimentäre Beschreibung des Klassendiagramm:

CanteenControll
----------------
Die Klasse CanteenControll ist die Kontroll-Klasse des Programm, hier laufen die Informationen aus den anderen Klassen zusammen und werden delegiert. In dieser Klasse können auch Methoden aus den anderen Klassen gesteuert werden wie z.b das erzeugen einer Speiseplan, das Erzeugen einer Einkaufsliste. Die Klasse hat selber keine Programmlogik sondern muss eher als zusammenlaufen vieler Schnittstellen gesehen werden und als Handler zwischen ihnen.

ConfigFileHelper
----------------

Klasse in der Methoden zur Verarbeitung von Konfigurationsdateien implementiert werden.


<abstract Canteen>
-------------------
Die Basisklassen Canteen enthält grundlegende Eigenschaften einer Kantine wie z.b Standort, Anzahl Mitarbeiter. Die abgeleiteten Unterklassen müssen diese Attribute/Methoden implementieren.

Muehlheim
----------
Abgeleitete Klasse der Basisklasse Canteen für den Standort Mühlheim

Essen
---------
Abgeleitete Klasse der Basisklasse Canteen für den Standort Essen

MealHitliste
-------------
Representation der beliebtesten Gerichten eines Standort.

Recipe
-------
Die Klasse Recipe ist die Repräsentation eines Kochrezept für ein Gericht. Ein Kochrezept ist eine planmäßige Anweisung 
zur Zubereitung mit Angaben zu Zutaten und ihren Mengen eines bestimmten Gerichts. 

<abstract> Meal
----------------
Die Basisklassen Meal enthält grundlegende Eigenschaften eines Gericht wie z.b Art, Preis. Die abgeleiteten Unterklassen müssen diese Attribute/Methoden implementieren. Jedes Gericht gehört zu einem Rezept.

MeatMeal
---------
Abgeleitete Klasse der Basisklasse Meal für ein Fleischgericht.

VegetableMeal
--------------
Abgeleitete Klasse der Basisklasse Meal für ein Vegetarisches Gericht.

FishMeal
-----------
Abgeleitete Klasse der Basisklasse Meal für ein Fischgericht.

Ingriedents
------------
Eine Klasse zur Repräsentation von Zutaten wie z.b Zucker, Kartoffeln, Rindfleisch.


MealPlanGenerator
-------------------
Diese Klasse beinhaltet den Algorithmus zur Erstellung eines optimierten Speiseplan, welcher die Anforderungen der drei Gericht Alternativen an einem Tag, Abwechslung der Gerichte und die Hitliste berücksichtig.

<abstract> FoodDistributer
---------------------------
Die Basisklassen FoodDistributer enthält grundlegende Eigenschaften eines Warenanbieter wie z.b Angebotene Rohstoffe, Preise, Transportkosten. Die abgeleiteten Unterklassen müssen diese Attribute/Methoden implementieren.

LocalFarmer
------------
Abgeleitete Klasse der Basisklasse FoodDistributer für einen lokalen Landwirt.

Wholesaler
------------
Abgeleitete Klasse der Basisklasse FoodDistributer für einen Großmarkt.

PurchasingOptimization
-----------------------
Die Klasse PurchasingOptimization vergleicht die Preise der angebotenen Waren der einzelnen Anbieter und liefert die kostengünstigste Alternative.

ShoppingListGenerator
----------------------
In dieser Klasse werden die benötigten Zutaten und Mengen aller zu kochenden Gerichte pro Kantine für einen Speiseplan als Einkaufsliste zusammengestellt. Die Einkaufsliste berücksichtigt die Informationen aus der Klasse PurchasingOptimization und druckt den jeweils günstigsten Händler pro Zutat ab.

CostGenerator
--------------
Diese Klasse kalkuliert die Kosten die für den Einkauf der Zutaten für einen Speiseplan anfallen.

<abstract> ViewGenerator
-------------------------
Die Basisklassen ViewGenerator enthält grundlegende Eigenschaften einer Ansicht wie z.b Datum, . Die abgeleiteten Unterklassen müssen diese Attribute/Methoden implementieren.


PDFOutput
----------
Klasse zur Erzeugung von PDF Dateien.

HTMLOutput
----------
Klasse zur Erzeugung von HMTL Dateien.

XMLOutput
----------
Klasse zur Erzeugung von XML Dateien.

FileOutput
----------
Klasse zur Erzeugung von Text Dateien.











