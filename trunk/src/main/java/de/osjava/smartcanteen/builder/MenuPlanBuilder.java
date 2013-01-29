package de.osjava.smartcanteen.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import de.osjava.smartcanteen.base.ProviderBase;
import de.osjava.smartcanteen.base.RecipeBase;
import de.osjava.smartcanteen.builder.result.Meal;
import de.osjava.smartcanteen.builder.result.MenuPlan;
import de.osjava.smartcanteen.data.Canteen;
import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.data.item.IngredientListItem;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.datatype.IngredientType;
import de.osjava.smartcanteen.helper.BuilderHelper;
import de.osjava.smartcanteen.helper.NumberHelper;
import de.osjava.smartcanteen.helper.PropertyHelper;

/**
 * Die Klasse {@link MenuPlanBuilder} ist eine der Geschäftslogikklassen und für die Erstellung je eines optimalen
 * Speiseplans für die Kantinen in Essen und Muelheim zuständig. Als Ergebnis füllt die Klasse die {@link MenuPlan}
 * Attribute der {@link Canteen}, die dann im Output bzw. zur Weiterverarbeitung verwendet werden können.
 * 
 * @author Tim Sahling
 */
public class MenuPlanBuilder {

    private static final Integer PROP_CANTEEN_ESSEN_NUMBEROFEMPLOYEES = Integer.valueOf(PropertyHelper
            .getProperty("canteen.essen.numberOfEmployees"));

    private static final Integer PROP_CANTEEN_MUELHEIM_NUMBEROFEMPLOYEES = Integer.valueOf(PropertyHelper
            .getProperty("canteen.muelheim.numberOfEmployees"));

    private static final Integer PROP_PLANINGPERIOD_MEALSPERDAY = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.mealsPerDay"));
    private static final Integer PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.meatMealsPerDay.min"));
    private static final Integer PROP_PLANINGPERIOD_MEATMEALSPERDAY_MAX = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.meatMealsPerDay.max"));
    private static final Integer PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.vegetableMealsPerDay.min"));
    private static final Integer PROP_PLANINGPERIOD_FISHMEALSPERDAY_MAX = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.fishMealsPerDay.max"));
    private static final Integer PROP_PLANINGPERIOD_WEEKWORKDAYS = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.weekWorkdays"));
    private static final Integer PROP_PLANINGPERIOD_WEEKS = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.weeks"));

    private static final Integer PROP_PLANINGPERIOD_TOTALWEEKANDWORKDAYS = (PROP_PLANINGPERIOD_WEEKWORKDAYS * PROP_PLANINGPERIOD_WEEKS);
    private static final Integer PROP_PLANINGPERIOD_TOTALMEALS = (PROP_PLANINGPERIOD_MEALSPERDAY * PROP_PLANINGPERIOD_WEEKWORKDAYS * PROP_PLANINGPERIOD_WEEKS);

    private static final String PROP_MESSAGE_NOTENOUGHMEALSINPERIOD_EXCEPTION = PropertyHelper
            .getProperty("message.notEnoughMealsInPeriod.exception");

    private static final String PLANING_MODE_SEQUENTIAL = "sequential";
    private static final String PLANING_MODE_RANDOM = "random";

    private ProviderBase providerBase;
    private RecipeBase recipeBase;
    private Canteen[] canteens;

    private CanteenContext canteenContext;
    private Map<Ingredient, Amount> providerIngredientQuantities;

    /**
     * Der Standardkonstruktor der {@link MenuPlanBuilder} initialisiert die
     * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten entgegen.
     * 
     * @param providerBase Die {@link ProviderBase} Verwaltungs- bzw. Containerklasse
     * @param recipeBase Die {@link RecipeBase} Verwaltungs- bzw. Containerklasse
     */
    public MenuPlanBuilder(ProviderBase providerBase, RecipeBase recipeBase) {
        this.providerBase = providerBase;
        this.recipeBase = recipeBase;
        this.canteens = new Canteen[]{ new Canteen(CanteenLocation.ESSEN, PROP_CANTEEN_ESSEN_NUMBEROFEMPLOYEES), new Canteen(
                CanteenLocation.MUELHEIM, PROP_CANTEEN_MUELHEIM_NUMBEROFEMPLOYEES) };
    }

    /**
     * Die einzige öffentliche Methode der Klasse {@link MenuPlanBuilder} ruft die Applikationslogik und den damit
     * verbundenen Optimierungsalgorithmus für die Generierung des {@link MenuPlan} und der {@link Meal}s auf.
     * 
     * @return Ein Array von Kantinen für die Verwendung im Output
     */
    public Canteen[] buildMenuPlan() {

        // Summiert alle Mengen einer Zutat von allen Anbietern um später überprüfen zu können ob ein Rezept in der
        // jeweils benötigten Menge beschaffbar ist
        this.providerIngredientQuantities = this.providerBase.sumIngredientQuantities();

        // Initalisiert die Planungsperiode
        Map<WeekWorkday, Set<Recipe>> planingPeriod = initPlaningPeriod();

        // Ermittelt die Rezepte, sortiert nach Rang, um die Zufriedenheit und Leistungsfähigkeit der Mitarbeiter zu
        // steigern
        Set<Recipe> recipesSortedByRank = this.recipeBase.getRecipesSortedByRank(null);

        // Um den Kantinen einen größere Individualität zu geben wird der Algorithmus für den optimalen Speiseplan pro
        // Kantine durchlaufen.
        for (Canteen canteen : this.canteens) {

            this.canteenContext = new CanteenContext(canteen);

            // Fügt die Rezepte in die Struktur der Planungsperiode ein
            for (Recipe recipe : recipesSortedByRank) {

                // Überprüfung ob der Algorithmus vorzeitig beendet werden kann, wenn benötigte Rezepte komplett sind
                if (this.canteenContext.getRecipes().size() == PROP_PLANINGPERIOD_TOTALMEALS) {
                    break;
                }

                // Inititale Überprüfung ob ein Rezept valide ist
                if (validateRecipe(recipe)) {
                    addRecipeToPlaningPeriod(recipe, planingPeriod);
                }
            }

            // Überprüft ob alle Regeln eingehalten wurden und die Speisepläne valide sind
            validatePlaningPeriod(planingPeriod);

            // Erstellt einen Speiseplan für die Kantinen
            MenuPlan menuPlan = createMenuPlan();

            // Füllt den Speiseplan mit Gerichten, die an einem Datum serviert werden sollen
            for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

                Date mealDate = createMealDate(entry.getKey());

                for (Recipe recipe : entry.getValue()) {
                    menuPlan.getMeals().add(createMeal(mealDate, recipe));
                }
            }

            canteen.setMenuPlan(menuPlan);

            clearPlaningPeriod(planingPeriod);
        }

        return this.canteens;
    }

    /**
     * Überprüft initial ob ein Rezept valide ist. Ein Rezept ist nicht valide wenn es bereits ausgewählt wurde oder
     * wenn nicht alle Zutaten auch einen {@link IngredientType} haben oder wenn eine Zutat eines Rezepts einen
     * bestimmten Maximalwert überschreitet. Ein fehlender {@link IngredientType} weist darauf hin, dass diese Zutat bei
     * gegebenen Preislisten bzw. Anbietern nicht beschaffbar ist, da die Typen erst nach Einlesen der Anbieter gesetzt
     * werden. Dies muss erfolgen, da in der Rezepte Eingangsdatei keine Typen definiert sind, sondern nur in den Listen
     * der Anbieter.
     * 
     * @param recipe Das zu überprüfende {@link Recipe}
     * @return wahr/falsch, je nachdem ob das {@link Recipe} valide ist
     */
    private boolean validateRecipe(Recipe recipe) {
        // Überprüfung ob ein Zutatentyp nicht gefüllt ist
        for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {
            if (ingredientListItem.getIngredient().getIngredientType() == null) {
                return false;
            }
        }

        BigDecimal propIngredientPriceForOneUnitMax = new BigDecimal(
                PropertyHelper.getProperty("ingredient.priceForOneUnit.max"));

        // Überprüfung ob eine Zutat eines Gerichts über einem festgelegten Maximalwert liegt. Wenn ja, wird dieses
        // Gericht nicht bestellt, um die Kosten des Speiseplans ökonomisch sinnvoll zu begrenzen
        for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

            Amount bestPriceForOneUnitOfIngredient = this.providerBase
                    .findBestPriceForOneUnitOfIngredient(ingredientListItem.getIngredient());

            if (bestPriceForOneUnitOfIngredient != null) {
                if (NumberHelper.compareGreaterOrEqual(bestPriceForOneUnitOfIngredient.getValue(),
                        propIngredientPriceForOneUnitMax)) {
                    return false;
                }
            }
        }

        // Überprüfung ob ein Gericht bereits vorhanden ist
        if (this.canteenContext.getRecipes().contains(recipe)) {
            return false;
        }

        return true;
    }

    /**
     * Leert die Ergebnisse einer Planungsperiode
     * 
     * @param planingPeriod Die Planungsperiode
     */
    private void clearPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod) {
        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                entry.getValue().clear();
            }
        }
    }

    /**
     * Erstellt einen {@link MenuPlan}.
     * 
     * @return Der erstellte {@link MenuPlan}
     */
    private MenuPlan createMenuPlan() {
        return new MenuPlan(new ArrayList<Meal>(PROP_PLANINGPERIOD_TOTALMEALS));
    }

    /**
     * Erstellt ein {@link Meal}.
     * 
     * @param date Das Datum an dem das {@link Meal} serviert werden soll
     * @param recipe Das {@link Recipe}, dass gekocht werden soll
     * @return Das erzeugte {@link Meal}
     */
    private Meal createMeal(Date date, Recipe recipe) {
        return new Meal(recipe, date);
    }

    /**
     * Methode ermittelt den nächsten Montag und addiert daraufhin auf Basis des übergebenen {@link WeekWorkday}, die
     * entsprechende Woche und den jeweiligen Tag. Annahme ist, dass es immer eine Woche dauert bis alle Zutaten
     * beschafft werden konnten. Feiertage werden nicht berücksichtigt.
     * 
     * @param weekWorkday Der {@link WeekWorkday} der zur Berechnung der Daten genutzt wird
     * @return Ein berechnetes {@link Date}
     */
    private Date createMealDate(WeekWorkday weekWorkday) {
        Date nextMonday = getNextMonday();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextMonday);
        cal.add(Calendar.WEEK_OF_MONTH, weekWorkday.getWeek());
        cal.add(Calendar.DAY_OF_WEEK, (weekWorkday.getWeekWorkday() - 1));
        return cal.getTime();
    }

    /**
     * Ermittelt den nächsten Montag, ausgehend vom derzeitigen Datum.
     * 
     * @return Der nächste Montag im Kalender
     */
    private Date getNextMonday() {
        Calendar cal = Calendar.getInstance();

        // Ermitteln aktueller Wochentag
        int weekday = cal.get(Calendar.DAY_OF_WEEK);

        // Wenn aktueller Wochentag nicht Montag ist, muss der nächste Montag ermittelt werden
        if (weekday != Calendar.MONDAY) {
            int days = (Calendar.SATURDAY - weekday + 2) % 7;
            cal.add(Calendar.DAY_OF_YEAR, days);
        }

        return cal.getTime();
    }

    /**
     * Versucht auf Basis verschiedener {@link WeekWorkday}s, Gerichte in die Planungsperiode einzufügen.
     * 
     * @param recipe Das hinzuzufügende {@link Recipe}
     * @param planingPeriod Die Planungsperiode
     */
    private void addRecipeToPlaningPeriod(Recipe recipe, Map<WeekWorkday, Set<Recipe>> planingPeriod) {

        Set<WeekWorkday> tempWeekWorkdays = new LinkedHashSet<WeekWorkday>();

        while (true) {
            WeekWorkday weekAndWorkday = getWeekAndWorkday(planingPeriod, tempWeekWorkdays);

            if (weekAndWorkday == null) {
                break;
            }
            else {
                if (validateRecipeBeforeInsertIntoWeekWorkday(planingPeriod, weekAndWorkday, recipe)) {

                    if (addRecipeToPlaningPeriod(planingPeriod, weekAndWorkday, recipe)) {

                        Set<Recipe> weekWorkdayRecipes = planingPeriod.get(weekAndWorkday);

                        // Entfernen der benötigten Menge der Zutaten eines Rezepts aus der Gesamtmenge aller Zutaten
                        removeQuantityFromProviderIngredientQuantities(weekWorkdayRecipes, recipe);

                        break;
                    }
                }

                tempWeekWorkdays.add(weekAndWorkday);
            }
        }
    }

    /**
     * Fügt der Planungsperiode ein {@link Recipe} hinzu, vorausgesetzt dieses ist auch beschaffbar.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkday Der {@link WeekWorkday} an dem das {@link Recipe} hinzugefügt werden soll
     * @param recipe Das {@link Recipe}, dass hinzugefügt werden soll
     */
    private boolean addRecipeToPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod, WeekWorkday weekWorkday,
            Recipe recipe) {
        Set<Recipe> weekWorkDayRecipes = planingPeriod.get(weekWorkday);

        if (!weekWorkDayRecipes.contains(recipe)) {
            weekWorkDayRecipes.add(recipe);

            // Wenn Rezept beschaffbar ist wird es hinzugefügt
            if (isRecipeObtainable(weekWorkDayRecipes, recipe)) {
                this.canteenContext.getRecipes().add(recipe);
            }
            // Wenn ein Rezept nicht beschaffbar ist, wird es wieder aus dem Wochentag entfernt
            else {
                weekWorkDayRecipes.remove(recipe);
                return false;
            }
        }

        return true;
    }

    /**
     * Überprüft ob ein {@link Recipe} bei den Anbietern in der benötigten Menge beschaffbar ist.
     * 
     * @return wahr/falsch, je nachdem ob das {@link Recipe} bei den Anbietern in der benötigten Menge beschaffbar ist
     */
    private boolean isRecipeObtainable(Set<Recipe> weekWorkDayRecipes, Recipe recipe) {
        boolean result = true;

        // Ermittelt die Position des Gerichts an dem Wochentag, da diese ausschlaggend für die zu kochende Menge und
        // damit auch die benötigte Zutatenmenge ist
        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(weekWorkDayRecipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            // Berechnet den Gerichtprioritätsfaktor
            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    // Ermittelt die Gesamtmenge aller Zutaten von den Anbietern auf Basis der benötigten Zutat
                    Amount providerIngredientQuantity = this.providerIngredientQuantities.get(ingredient);

                    // Multipliziert die Menge der Zutat mit dem Gerichtprioritätsfaktor
                    BigDecimal quantityForIngredient = NumberHelper.multiply(ingredientQuantity.getValue(),
                            mealMultiplyFactor);

                    // Wenn die vorhandene Gesamtmenge der Zutat nicht größer ist als die benötigte Menge der Zutat, ist
                    // das Rezept nicht beschaffbar
                    if (!NumberHelper.compareGreaterOrEqual(providerIngredientQuantity.getValue(),
                            quantityForIngredient)) {
                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Ermittelt die Position des {@link Recipe} innerhalb einer Menge von {@link Recipe}s eines {@link WeekWorkday}.
     * Die Position stellt gleichzeitig die Priorität des {@link Recipe} dar, weil die übergebene Menge nach dem Rang
     * des {@link Recipe} sortiert ist.
     * 
     * @param recipes Die {@link Recipe}s, die durchlaufen werden sollen
     * @param recipe Das {@link Recipe} nach dem gesucht wird
     * @return Die Position des {@link Recipe} innerhalb einer Menge von {@link Recipe}s
     */
    private Integer getWeekWorkdayPositionOfRecipe(Set<Recipe> recipes, Recipe recipe) {
        Integer result = null;

        Iterator<Recipe> iterator = recipes.iterator();
        int index = 0;

        while (iterator.hasNext()) {

            if (iterator.next().equals(recipe)) {
                result = Integer.valueOf(index);
                break;
            }

            index++;
        }

        return result;
    }

    /**
     * Überprüft anhand der vorgegebenen Regeln ob ein {@link Recipe} in die übergebene Planungsperiode zu dem
     * übergebenen {@link WeekWorkday} hinzugefügt werden darf.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkday Der {@link WeekWorkday} an dem das {@link Recipe} eingefügt werden soll
     * @param recipe Das {@link Recipe}, dass eingefügt werden soll
     * @return wahr/falsch, je nachdem ob das {@link Recipe} in den {@link WeekWorkday} eingefügt werden konnte
     */
    private boolean validateRecipeBeforeInsertIntoWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekWorkday, Recipe recipe) {
        Set<Recipe> recipes = planingPeriod.get(weekWorkday);

        // Bisher keine Rezepte für diesen Tag vorhanden
        if (recipes.isEmpty()) {
            return true;
        }
        // Bereits Rezepte für diesen Tag vorhanden
        else {

            // Wenn Rezept ein Fleischgericht ist, muss überprüft werden ob bereits die maximale Anzahl an
            // Fleischgerichten für diesen Tag erreicht ist
            if (recipe.isMeatRecipe()) {

                if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekWorkday, IngredientType.MEAT) == PROP_PLANINGPERIOD_MEATMEALSPERDAY_MAX) {
                    return false;
                }
            }
            // Wenn Rezept ein Fischgericht ist, muss überprüft werden ob bereits die maximale Anzahl an Fischgerichten
            // für diesen Tag erreicht ist. Diese Abfrage resultiert aus der Anforderung, dass an einem Tag immer
            // mindestens ein vegetarisches Gericht und ein Fleischgericht angeboten werden müssen
            else if (recipe.isFishRecipe()) {

                if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekWorkday, IngredientType.FISH) == PROP_PLANINGPERIOD_FISHMEALSPERDAY_MAX) {
                    return false;
                }
            }

            Set<Recipe> weekWorkdayRecipes = planingPeriod.get(weekWorkday);

            // Wenn nur noch ein Platz für ein Gericht an dem ausgewählten Tag frei ist, muss eine gesonderte Prüfung
            // stattfinden
            if (weekWorkdayRecipes.size() == (PROP_PLANINGPERIOD_MEALSPERDAY - 1)) {
                return validateLastRecipeForWeekWorkday(planingPeriod, weekWorkday, recipe);
            }
            // Gericht darf eingefügt werden
            else {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekWorkday);
            }
        }
    }

    /**
     * Wenn nur noch ein Platz für ein Gericht an dem ausgewählten Tag frei ist, müssen folgende Überprüfungen
     * stattfinden: Bei einem Fleischgericht muss geprüft werden, ob mindestens ein vegetarisches Gericht vorhanden ist.
     * Bei einem Fischgericht muss geprüft werden, ob mindestens bereits ein Fleischgericht und ein vegetarisches
     * Gericht vorhanden sind. Bei einem vegetarischen Gericht muss geprüft werden, ob mindestens ein Fleischgericht
     * vorhanden ist.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkday Der {@link WeekWorkday} an dem das {@link Recipe} eingefügt werden soll
     * @param recipe Das {@link Recipe}, dass eingefügt werden soll
     * @return wahr/falsch, je nachdem ob das übergebene {@link Recipe} als letztes {@link Recipe} des
     *         {@link WeekWorkday} gültig ist
     */
    private boolean validateLastRecipeForWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekWorkday, Recipe recipe) {

        if (recipe.isMeatRecipe()) {

            // Fleischgericht darf nur eingefügt werden, wenn mindestens ein vegetarisches Gericht vorhanden ist
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekWorkday, IngredientType.VEGETABLE) >= PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekWorkday);
            }
            else {
                return false;
            }
        }
        else if (recipe.isFishRecipe()) {

            // Fischgericht darf nur eingefügt werden, wenn ein Fleischgericht und ein vegetarisches Gericht vorhanden
            // sind
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekWorkday, IngredientType.MEAT) == PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN && countRecipesForWeekWorkdayAndIngredientType(
                    planingPeriod, weekWorkday, IngredientType.VEGETABLE) == PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekWorkday);
            }
            else {
                return false;
            }
        }
        else if (recipe.isVegetableRecipe()) {

            // Vegetarisches Gericht darf nur eingefügt werden, wenn mindestens ein Fleischgericht vorhanden ist
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekWorkday, IngredientType.MEAT) >= PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekWorkday);
            }
            else {
                return false;
            }
        }

        return false;
    }

    /**
     * Für den Fall, dass {@link MenuPlanBuilder#validateRecipeBeforeInsertIntoWeekWorkday(Map, WeekWorkday, Recipe)}
     * "wahr" zurückgibt, findet in dieser Methode die finale Überprüfung statt ob ein {@link Recipe} wirklich
     * hinzugefügt
     * werden darf oder nicht. Das {@link Recipe} darf nur dann hinzugefügt werden wenn bisher kein Gericht diesem Tag
     * zugeordnet ist oder die Anzahl der Gerichte an diesem Tag kleiner der maximal erlaubten Anzahl an Gerichten für
     * einen Tag ist.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkday Der {@link WeekWorkday}, der überprüft werden soll
     * @return wahr/falsch, je nachdem ob der {@link WeekWorkday} valide ist
     */
    private boolean validateMaxRecipesForWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekWorkday) {
        return planingPeriod.get(weekWorkday).isEmpty() ? true : planingPeriod.get(weekWorkday).size() < PROP_PLANINGPERIOD_MEALSPERDAY ? true : false;
    }

    /**
     * Zählt die Anzahl der Rezepte für einen übergebenen {@link WeekWorkday} und einen {@link IngredientType}.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkday Der {@link WeekWorkday}, der überprüft werden soll
     * @param ingredientType Der {@link IngredientType} nach dem überprüft werden soll
     * @return Anzahl der gezählten {@link Recipe}
     */
    private int countRecipesForWeekWorkdayAndIngredientType(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekWorkday, IngredientType ingredientType) {
        int result = 0;

        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            if (entry.getKey().equals(weekWorkday)) {

                for (Recipe recipe : entry.getValue()) {

                    if (ingredientType.equals(recipe.getIngredientType())) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Zählt die {@link Recipe} für einen bestimmten {@link IngredientType}.
     * 
     * @param recipes Die {@link Recipe}s, die überprüft werden sollen
     * @param ingredientType Der {@link IngredientType} nach dem überprüft werden soll
     * @return Anzahl der gezählten {@link Recipe}
     */
    private int countRecipesForIngredientType(Set<Recipe> recipes, IngredientType ingredientType) {
        int result = 0;

        for (Recipe recipe : recipes) {

            if (ingredientType.equals(recipe.getIngredientType())) {
                result++;
            }

        }

        return result;
    }

    /**
     * Validiert die Planungsperiode abschließend.
     * 
     * @param planingPeriod Die Planungsperiode
     */
    private void validatePlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod) {

        // Wenn nicht mindestens ein Fischgericht jede Woche vorhanden ist, muss dieses nachträglich eingefügt werden
        Set<Integer> weeksWithoutFishRecipes = getWeeksWithoutFishRecipes(planingPeriod);

        if (weeksWithoutFishRecipes != null && weeksWithoutFishRecipes.size() > 0) {
            addMissingFishRecipesToPlaningPeriod(planingPeriod, weeksWithoutFishRecipes);
        }

        // Überprüfung ob genug Menüs für eine Planungsperiode generiert wurden
        int totalMeals = 0;

        for (Set<Recipe> recipes : planingPeriod.values()) {
            totalMeals += recipes.size();
        }

        if (totalMeals != PROP_PLANINGPERIOD_TOTALMEALS) {
            throw new RuntimeException(PROP_MESSAGE_NOTENOUGHMEALSINPERIOD_EXCEPTION);
        }
    }

    /**
     * Fügt der Planungsperiode die fehlenden Fischrezepte hinzu.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weeksWithoutFishRecipes Die Wochen ohne Fischrezepte
     */
    private void addMissingFishRecipesToPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<Integer> weeksWithoutFishRecipes) {

        for (Integer week : weeksWithoutFishRecipes) {
            boolean fishRecipeForWeekAdded = false;

            Set<WeekWorkday> weekWorkdays = getWeekAndWorkdaysByWeek(planingPeriod, week);

            // Ermittelt die bereits ausgewählten Fischrezepte
            Set<Recipe> usedFishRecipes = this.canteenContext.getFishRecipes();

            // Ermittelt alle Fischrezepte sortiert nach Rang
            Set<Recipe> availableFishRecipes = this.recipeBase
                    .getRecipesForIngredientTypeSortedByRank(IngredientType.FISH);

            if (availableFishRecipes != null && !availableFishRecipes.isEmpty()) {

                // Subtrahiert von der Menge der verfügbaren Fischrezepte die bereits ausgewählten Fischrezepte
                if (usedFishRecipes != null && !usedFishRecipes.isEmpty()) {
                    availableFishRecipes.removeAll(usedFishRecipes);
                }

                for (WeekWorkday weekWorkday : weekWorkdays) {

                    Set<Recipe> weekWorkdayRecipes = planingPeriod.get(weekWorkday);

                    // Zählt die Fleischrezepte für den Wochentag
                    int meatRecipes = countRecipesForIngredientType(weekWorkdayRecipes, IngredientType.MEAT);

                    // Zählt die vegetarischen Rezepte für den Wochentag
                    int vegetableRecipes = countRecipesForIngredientType(weekWorkdayRecipes, IngredientType.VEGETABLE);

                    // Es kommen nur Wochentage in Frage, die bereits mindestens ein Fleischgericht und ein
                    // vegetarisches Gericht haben
                    if (meatRecipes >= PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN && vegetableRecipes >= PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {

                        Recipe recipeToRemove;

                        // Wenn es mehr Fleischgerichte als vegetarische Gerichte an dem Wochentag gibt, wird ein
                        // Fleischgericht entfernt...
                        if (meatRecipes > vegetableRecipes) {
                            recipeToRemove = removeRecipeFromWeekWorkday(weekWorkdayRecipes, IngredientType.MEAT);
                        }
                        // ...ansonsten ein vegetarisches Gericht
                        else {
                            recipeToRemove = removeRecipeFromWeekWorkday(weekWorkdayRecipes, IngredientType.VEGETABLE);
                        }

                        // Verfügbare Rezepte werden durchlaufen...
                        for (Recipe fishRecipe : availableFishRecipes) {

                            // ...und versucht das Rezept in den Wochentag einzufügen
                            if (addRecipeToPlaningPeriod(planingPeriod, weekWorkday, fishRecipe)) {
                                // Entfernen der benötigten Menge der Zutaten eines Rezepts aus der Gesamtmenge aller
                                // Zutaten
                                removeQuantityFromProviderIngredientQuantities(weekWorkdayRecipes, fishRecipe);
                                fishRecipeForWeekAdded = true;
                                break;
                            }
                        }

                        // Wenn ein Fischrezept hinzugefügt werden konnte, wird der Ablauf unterbrochen...
                        if (fishRecipeForWeekAdded) {
                            break;
                        }
                        // ...ansonsten wird das vorherige Löschen des Rezept rückgängig gemacht
                        else {
                            undoRemoveRecipeFromWeekWorkday(weekWorkdayRecipes, recipeToRemove);
                        }
                    }
                }

                if (fishRecipeForWeekAdded) {
                    break;
                }
            }
        }
    }

    /**
     * Ermittelt die Wochen in denen kein Fischrezept vorhanden ist.
     * 
     * @param planingPeriod Die Planungsperiode
     * @return Eine Menge mit Wochen in denen kein Fischrezept vorhanden ist
     */
    private Set<Integer> getWeeksWithoutFishRecipes(Map<WeekWorkday, Set<Recipe>> planingPeriod) {
        Set<Integer> result = new HashSet<Integer>();

        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            if (!result.contains(entry.getKey().getWeek()) && !existsFishRecipeInWeek(planingPeriod, entry.getKey())) {
                result.add(entry.getKey().getWeek());
            }
        }

        return result;
    }

    /**
     * Überprüft ob in der übergebenen Planungsperiode und in der übergegebenen Woche bereits ein Fischrezept vorhanden
     * ist.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekAndWorkday Der {@link WeekWorkday}, der überprüft werden soll
     * @return wahr/falsch, je nachdem ob in der {@link WeekWorkday} bereits ein Fischgericht existiert
     */
    private boolean existsFishRecipeInWeek(Map<WeekWorkday, Set<Recipe>> planingPeriod, WeekWorkday weekAndWorkday) {
        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            if (entry.getKey().getWeek().equals(weekAndWorkday.getWeek())) {

                for (Recipe recipe : entry.getValue()) {

                    if (recipe.isFishRecipe()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Ermittelt alle Wochentage für eine Woche.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param week Die Woche zu der die Wochentage ermittelt werden sollen
     * @return Eine Menge von Wochentagen, die in einer Woche vorkommen
     */
    private Set<WeekWorkday> getWeekAndWorkdaysByWeek(Map<WeekWorkday, Set<Recipe>> planingPeriod, Integer week) {
        Set<WeekWorkday> result = new HashSet<WeekWorkday>();

        Set<WeekWorkday> keys = planingPeriod.keySet();

        if (keys != null && !keys.isEmpty()) {

            for (WeekWorkday key : keys) {

                if (key.getWeek().equals(week)) {
                    result.add(key);
                }
            }
        }

        return result;
    }

    /**
     * Entfernt ein {@link Recipe} von einem {@link WeekWorkday}. Dabei wird versucht das unbeliebteste der
     * {@link Recipe}s zu entfernen.
     * 
     * @param recipes Die {@link Recipe}s, die für einen {@link WeekWorkday} ausgewählt wurden
     * @param ingredientType Der {@link IngredientType}, den ein {@link Recipe} haben muss um entfernt zu werden
     */
    private Recipe removeRecipeFromWeekWorkday(Set<Recipe> recipes, IngredientType ingredientType) {
        Recipe result = null;

        // Rezepte für den übergebenen Wochentag werden nach Rang sortiert, allerdings in umgekehrter Reihenfolge um
        // danach das unbeliebteste zu entfernen
        Set<Recipe> sortedRecipes = new TreeSet<Recipe>(new Comparator<Recipe>() {

            @Override
            public int compare(Recipe r1, Recipe r2) {
                return Integer.valueOf(r2.getRank()).compareTo(Integer.valueOf(r1.getRank()));
            }
        });

        sortedRecipes.addAll(recipes);

        // Das erste Rezept welches für den übergebenen IngredientType gefunden wird, wird aus dem Wochentag entfernt
        for (Recipe recipe : sortedRecipes) {

            if (ingredientType.equals(recipe.getIngredientType())) {

                // Hinzufügen der Zutatenmengen des Rezepts in die Gesamtmenge
                addQuantityToProviderIngredientQuantities(recipes, recipe);

                // Entfernen des Rezepts aus dem Wochentag
                recipes.remove(recipe);

                // Entfernen des Rezepts auf dem Kantinenkontext
                this.canteenContext.getRecipes().remove(recipe);

                // Rückgabe des gelöschten Rezepts
                result = recipe;
                break;
            }
        }

        return result;
    }

    /**
     * Macht ein Entfernen eines {@link Recipe} aus einer Menge von {@link Recipe}s rückgängig.
     * 
     * @param recipes Die {@link Recipe}s, die durchlaufen werden sollen
     * @param recipe Das {@link Recipe}, welches wieder eingefügt werden soll
     */
    private void undoRemoveRecipeFromWeekWorkday(Set<Recipe> recipes, Recipe recipe) {
        // Hinzufügen des Rezepts in den Wochentag
        recipes.add(recipe);

        // Hinzufügen des Rezepts in den Kantinenkontext
        this.canteenContext.getRecipes().add(recipe);

        // Entfernen der Zutatenmengen des Rezepts aus der Gesamtmenge
        removeQuantityFromProviderIngredientQuantities(recipes, recipe);
    }

    /**
     * Fügt der Gesamtmenge der Zutaten die Menge der Zutaten des übergebenen {@link Recipe} hinzu.
     * 
     * @param recipes Die {@link Recipe}s, die durchlaufen werden sollen
     * @param recipe Das {@link Recipe}, dessen Zutaten in die Gesamtmenge hinzugefügt werden sollen
     */
    private void addQuantityToProviderIngredientQuantities(Set<Recipe> recipes, Recipe recipe) {

        // Ermittelt die Position des Gerichts an dem Wochentag, da diese ausschlaggend für die zu kochende Menge und
        // damit auch die benötigte Zutatenmenge ist
        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(recipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            // Berechnet den Gerichtprioritätsfaktor
            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    // Multipliziert die Menge der Zutat mit dem Gerichtprioritätsfaktor
                    BigDecimal quantityForIngredient = NumberHelper.multiply(ingredientQuantity.getValue(),
                            mealMultiplyFactor);

                    // Fügt der Gesamtmenge aller Zutaten die entsprechende Menge der Zutat hinzu
                    this.providerIngredientQuantities.get(ingredient).add(
                            new Amount(quantityForIngredient, ingredientQuantity.getUnit()));
                }
            }
        }
    }

    /**
     * Entfernt die Menge der Zutaten des übergebenen {@link Recipe} von der Gesamtmenge der Zutaten.
     * 
     * @param recipes Die {@link Recipe}s, die durchlaufen werden sollen
     * @param recipe Das {@link Recipe}, dessen Zutaten von der Gesamtmenge subtrahiert werden sollen
     */
    private void removeQuantityFromProviderIngredientQuantities(Set<Recipe> recipes, Recipe recipe) {

        // Ermittelt die Position des Gerichts an dem Wochentag, da diese ausschlaggend für die zu kochende Menge und
        // damit auch die benötigte Zutatenmenge ist
        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(recipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            // Berechnet den Gerichtprioritätsfaktor
            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    // Multipliziert die Menge der Zutat mit dem Gerichtprioritätsfaktor
                    BigDecimal quantityForIngredient = NumberHelper.multiply(ingredientQuantity.getValue(),
                            mealMultiplyFactor);

                    // Subtrahiert die entsprechende Menge der Zutat von der Gesamtmenge aller Zutaten
                    this.providerIngredientQuantities.get(ingredient).subtract(
                            new Amount(quantityForIngredient, ingredientQuantity.getUnit()));

                }
            }
        }
    }

    /**
     * Ermittelt eine Woche und einen Wochentag in dem ein Gericht hinzugefügt werden soll. Dafür gibt es zwei
     * verschiedene Modi. Zum einen den sequentiellen Modus, bei dem die Wochen und Wochentage sequentiell, also
     * aufeinanderfolgend durchlaufen werden. Zum anderen den Zufallsmodus, bei dem die Woche und der Wochentag zufällig
     * ermittelt werden. Bei dem Zufallsmodus wird außerdem darauf geachtet, dass keine Woche/Wochentag-Kombination
     * doppelt ausgewählt wird.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkdays Die bereits ausgewählten {@link WeekWorkday}s
     * @return Einen gefundenen {@link WeekWorkday} oder null, wenn keiner mehr verfügbar ist
     */
    private WeekWorkday getWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod, Set<WeekWorkday> weekWorkdays) {

        String propPlaningPeriodPlaningMode = PropertyHelper.getProperty("planingPeriod.planingMode");

        if (propPlaningPeriodPlaningMode.equals("") || propPlaningPeriodPlaningMode.equals(PLANING_MODE_SEQUENTIAL)) {
            return getSequentialWeekAndWorkday(planingPeriod, weekWorkdays);
        }
        else if (propPlaningPeriodPlaningMode.equals(PLANING_MODE_RANDOM)) {
            return getRandomWeekAndWorkday(planingPeriod, weekWorkdays);
        }

        return null;
    }

    /**
     * Bei dem sequentiellen Modus werden die Wochen und Wochentage sequentiell, also aufeinanderfolgend durchlaufen.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkdays Die bereits ausgewählten {@link WeekWorkday}s
     * @return Einen gefundenen {@link WeekWorkday} oder null, wenn keiner mehr verfügbar ist
     */
    private WeekWorkday getSequentialWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<WeekWorkday> weekWorkdays) {

        // Wenn noch kein Wochentag ausgewählt wurde, kann einfach der erste Tag der Planungsperiode genommen werden
        if (weekWorkdays.isEmpty()) {
            return planingPeriod.keySet().iterator().next();
        }

        // Wenn die Anzahl an bereits ausgewählten Wochentagen + 1 größer ist als die Gesamtzahl der Wochentage, kann
        // die Verarbeitung abgebrochen werden da kein freier Wochentag mehr verfügbar ist
        if (weekWorkdays.size() + 1 > PROP_PLANINGPERIOD_TOTALWEEKANDWORKDAYS) {
            return null;
        }

        // Letzter ausgewählter Wochentag wird ausgewählt
        WeekWorkday lastWeekWorkday = new LinkedList<WeekWorkday>(weekWorkdays).getLast();

        Iterator<WeekWorkday> iterator = planingPeriod.keySet().iterator();

        while (iterator.hasNext()) {
            WeekWorkday weekWorkday = iterator.next();

            // Wenn der Wochentag aus der Planungsperiode übereinstimmt, wird versucht den nächsten Wochentag
            // auszuwählen, vorausgesetzt es gibt noch einen
            if (weekWorkday.equals(lastWeekWorkday)) {

                if (iterator.hasNext()) {
                    return iterator.next();
                }
                else {
                    return weekWorkday;
                }
            }
        }

        return null;
    }

    /**
     * Bei dem zufälligen Modus werden die Wochen und Wochentage zufällig ermittelt, wobei darauf geachtet wird, dass
     * kein Wochentag doppelt ausgewählt wird.
     * 
     * @param planingPeriod Die Planungsperiode
     * @param weekWorkdays Die bereits ausgewählten {@link WeekWorkday}s
     * @return Einen gefundenen {@link WeekWorkday} oder null, wenn keiner mehr verfügbar ist
     */
    private WeekWorkday getRandomWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<WeekWorkday> weekWorkdays) {

        // In der Planungsperiode vorhandene Wochentage werden gespeichert
        List<WeekWorkday> keys = new ArrayList<WeekWorkday>(planingPeriod.keySet());

        // Danach werden die bereits ausgewählten Wochentage aus der Zwischenspeicherung der Wochentage gelöscht
        if (!keys.isEmpty() && !weekWorkdays.isEmpty()) {
            keys.removeAll(weekWorkdays);
        }

        // Solange noch Werte in der Zwischenspeicherung vorhanden sind, wird ein zufälliger Wert auf Basis der Größe
        // der Zwischenspeicherung ausgewählt
        if (!keys.isEmpty()) {
            int size = keys.size();
            int item = new Random().nextInt(size);
            int i = 0;

            for (WeekWorkday weekWorkday : keys) {
                if (i == item) {
                    return weekWorkday;
                }

                i++;
            }
        }

        return null;
    }

    /**
     * Initalisiert die Planungsperiode und erstellt eine entsprechende Datenstruktur mit einem {@link WeekWorkday} und
     * einem Set von {@link Recipe}, welches automatisch beim Einfügen sortiert wird nach dem jeweiligen Rang.
     * 
     * @return Die initiale Struktur für die Planungsperiode
     */
    private Map<WeekWorkday, Set<Recipe>> initPlaningPeriod() {
        Map<WeekWorkday, Set<Recipe>> result = new LinkedHashMap<WeekWorkday, Set<Recipe>>();

        for (int week = 1; week <= PROP_PLANINGPERIOD_WEEKS; week++) {

            for (int workday = 1; workday <= PROP_PLANINGPERIOD_WEEKWORKDAYS; workday++) {

                result.put(new WeekWorkday(week, workday), new TreeSet<Recipe>(new Comparator<Recipe>() {

                    @Override
                    public int compare(Recipe r1, Recipe r2) {
                        return Integer.valueOf(r1.getRank()).compareTo(Integer.valueOf(r2.getRank()));
                    }
                }));
            }
        }

        return result;
    }

    /**
     * Repräsentiert eine temporäre kantinenspezifische Contextklasse, die bestimmte Werte innerhalb der
     * Algorithmusverarbeitung aufnehmen kann.
     * 
     */
    private static final class CanteenContext {
        private Canteen canteen;
        private Set<Recipe> recipes;
        private BigDecimal totalMealsForCanteen;

        public CanteenContext(Canteen canteen) {
            this.canteen = canteen;
            this.recipes = new LinkedHashSet<Recipe>();
            this.totalMealsForCanteen = BuilderHelper.calculateTotalMealsForCanteen(this.canteen);
        }

        /**
         * Ermittelt die zu kochenden Fischrezepte für eine {@link Canteen}.
         * 
         * @return Eine Menge von Fischrezepten
         */
        public Set<Recipe> getFishRecipes() {
            Set<Recipe> result = new HashSet<Recipe>();

            if (!this.recipes.isEmpty()) {

                for (Recipe recipe : this.recipes) {

                    if (recipe.isFishRecipe()) {
                        result.add(recipe);
                    }
                }
            }

            return result;
        }

        public Set<Recipe> getRecipes() {
            return recipes;
        }

        public BigDecimal getTotalMealsForCanteen() {
            return totalMealsForCanteen;
        }
    }

    /**
     * Repräsentiert eine temporäre Datenhaltungsklasse, die eine Woche und einen Wochentag als Integer aufnehmen kann.
     * 
     */
    private static final class WeekWorkday {
        private Integer week;
        private Integer weekWorkday;

        public WeekWorkday(Integer week, Integer weekWorkday) {
            this.week = week;
            this.weekWorkday = weekWorkday;
        }

        public Integer getWeek() {
            return week;
        }

        public Integer getWeekWorkday() {
            return weekWorkday;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((week == null) ? 0 : week.hashCode());
            result = prime * result + ((weekWorkday == null) ? 0 : weekWorkday.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            WeekWorkday other = (WeekWorkday) obj;
            if (week == null) {
                if (other.week != null)
                    return false;
            }
            else if (!week.equals(other.week))
                return false;
            if (weekWorkday == null) {
                if (other.weekWorkday != null)
                    return false;
            }
            else if (!weekWorkday.equals(other.weekWorkday))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "WeekWorkday [week=" + week + ", weekWorkday=" + weekWorkday + "]";
        }
    }
}
