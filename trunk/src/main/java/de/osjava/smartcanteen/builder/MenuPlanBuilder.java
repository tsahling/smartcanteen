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
 * Die Klasse {@link MenuPlanBuilder} ist eine der Geschäftslogikklassen und für
 * die Erstellung je eines optimalen Speiseplans für die Kantinen in Essen und
 * Muelheim zuständig. Als Ergebnis füllt die Klasse die {@link MenuPlan} Attribute der {@link Canteen}, die dann im
 * Output bzw. zur Weiterverarbeitung
 * verwendet werden können.
 * 
 * @author Tim Sahling
 */
public class MenuPlanBuilder {

    private static final Integer PROP_CANTEEN_ESSEN_NUMBEROFEMPLOYEES = Integer.valueOf(PropertyHelper
            .getProperty("canteen.essen.numberOfEmployees"));

    private static final Integer PROP_CANTEEN_MUELHEIM_NUMBEROFEMPLOYEES = Integer.valueOf(PropertyHelper
            .getProperty("canteen.muelheim.numberOfEmployees"));

    private static final String PROP_PLANINGPERIOD_PLANINGMODE = PropertyHelper
            .getProperty("planingPeriod.planingMode");

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

    private static final BigDecimal PROP_INGREDIENT_PRICEFORONEUNIT_MAX = new BigDecimal(
            PropertyHelper.getProperty("ingredient.priceForOneUnit.max"));

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
     * Die einzige öffentliche Methode der Klasse {@link MenuPlanBuilder} ruft
     * die Applikationslogik und den damit verbundenen Optimierungsalgorithmus
     * für die Generierung des {@link MenuPlan} und der {@link Meal}s auf.
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
     * @param recipe
     * @return
     */
    private boolean validateRecipe(Recipe recipe) {
        // Überprüfung ob ein Zutatentyp nicht gefüllt ist
        for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {
            if (ingredientListItem.getIngredient().getIngredientType() == null) {
                return false;
            }
        }

        // Überprüfung ob eine Zutat eines Gerichts über einem festgelegten Maximalwert liegt. Wenn ja, wird dieses
        // Gericht nicht bestellt, um die Kosten des Speiseplans ökonomisch sinnvoll zu begrenzen
        for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

            Amount bestPriceForOneUnitOfIngredient = this.providerBase
                    .findBestPriceForOneUnitOfIngredient(ingredientListItem.getIngredient());

            if (bestPriceForOneUnitOfIngredient != null) {
                if (NumberHelper.compareGreaterOrEqual(bestPriceForOneUnitOfIngredient.getValue(),
                        PROP_INGREDIENT_PRICEFORONEUNIT_MAX)) {
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
     * @return
     */
    private MenuPlan createMenuPlan() {
        return new MenuPlan(new ArrayList<Meal>(PROP_PLANINGPERIOD_TOTALMEALS));
    }

    /**
     * Erstellt ein {@link Meal}.
     * 
     * @param date
     * @param recipe
     * @return
     */
    private Meal createMeal(Date date, Recipe recipe) {
        return new Meal(recipe, date);
    }

    /**
     * Methode ermittelt den nächsten Montag und addiert daraufhin auf Basis des übergebenen {@link WeekWorkday}, die
     * entsprechende Woche und den jeweiligen Tag. Annahme ist, dass es immer eine Woche dauert bis alle Zutaten
     * beschafft werden konnten. Feiertage werden nicht berücksichtigt.
     * 
     * @param weekWorkday
     * @return
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
     * @return
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
     * @param recipe
     * @param planingPeriod
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
     * Fügt der Planungsperiode ein {@link Recipe} hinzu.
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @param recipe
     */
    private boolean addRecipeToPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod, WeekWorkday weekAndWorkday,
            Recipe recipe) {
        Set<Recipe> weekWorkDayRecipes = planingPeriod.get(weekAndWorkday);

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
     * Überprüft ob ein {@link Recipe} bei den Anbietern beschaffbar ist.
     * 
     * @return
     */
    private boolean isRecipeObtainable(Set<Recipe> weekWorkDayRecipes, Recipe recipe) {
        boolean result = true;

        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(weekWorkDayRecipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    Amount providerIngredientQuantity = this.providerIngredientQuantities.get(ingredient);

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
     * Ermittelt die Position des {@link Recipe} innerhalb eines {@link WeekWorkday}. Die Position stellt gleichzeitig
     * die Priorität des {@link Recipe} dar, weil das übergebene Set nach dem Rang des {@link Recipe} sortiert ist.
     * 
     * @param weekWorkDayRecipes
     * @param recipe
     * @return Die Position des {@link Recipe} innerhalb eines {@link WeekWorkday}
     */
    private Integer getWeekWorkdayPositionOfRecipe(Set<Recipe> weekWorkDayRecipes, Recipe recipe) {
        Integer result = null;

        Iterator<Recipe> iterator = weekWorkDayRecipes.iterator();
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
     * Überprüft anhand der vorgegebenen Regeln ob ein Gericht in die übergebene Planungsperiode zu dem übergebenen
     * {@link WeekWorkday} hinzugefügt werden darf.
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @param recipe
     * @return
     */
    private boolean validateRecipeBeforeInsertIntoWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekAndWorkday,
            Recipe recipe) {
        Set<Recipe> recipes = planingPeriod.get(weekAndWorkday);

        // Bisher keine Rezepte für diesen Tag vorhanden
        if (recipes.isEmpty()) {
            return true;
        }
        else { // Bereits Rezepte für diesen Tag vorhanden

            // Wenn Rezept ein Fleischgericht ist, muss überprüft werden ob bereits die maximale Anzahl an
            // Fleischgerichten für diesen Tag erreicht ist
            if (recipe.isMeatRecipe()) {

                if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekAndWorkday, IngredientType.MEAT) == PROP_PLANINGPERIOD_MEATMEALSPERDAY_MAX) {
                    return false;
                }
            }
            // Wenn Rezept ein Fischgericht ist, muss überprüft werden ob bereits die maximale Anzahl an Fischgerichten
            // für diesen Tag erreicht ist. Diese Abfrage resultiert aus der Anforderung, dass an einem Tag immer
            // mindestens ein vegetarisches Gericht und ein Fleischgericht angeboten werden müssen
            else if (recipe.isFishRecipe()) {

                if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekAndWorkday, IngredientType.FISH) == PROP_PLANINGPERIOD_FISHMEALSPERDAY_MAX) {
                    return false;
                }
            }

            Set<Recipe> weekWorkdayRecipes = planingPeriod.get(weekAndWorkday);

            // Wenn nur noch ein Platz für ein Gericht an dem ausgewählten Tag frei ist, muss eine gesonderte Prüfung
            // stattfinden
            if (weekWorkdayRecipes.size() == (PROP_PLANINGPERIOD_MEALSPERDAY - 1)) {
                return validateLastRecipeForWeekWorkday(planingPeriod, weekAndWorkday, recipe);
            }
            else { // Gericht darf eingefügt werden
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekAndWorkday);
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
     * @param planingPeriod
     * @param weekAndWorkday
     * @param recipe
     * @return
     */
    private boolean validateLastRecipeForWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekAndWorkday, Recipe recipe) {

        if (recipe.isMeatRecipe()) {

            // Fleischgericht darf nur eingefügt werden, wenn mindestens ein vegetarisches Gericht vorhanden ist
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekAndWorkday, IngredientType.VEGETABLE) >= PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekAndWorkday);
            }
            else {
                return false;
            }
        }
        else if (recipe.isFishRecipe()) {

            // Fischgericht darf nur eingefügt werden, wenn ein Fleischgericht und ein vegetarisches Gericht vorhanden
            // sind
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekAndWorkday, IngredientType.MEAT) == PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN && countRecipesForWeekWorkdayAndIngredientType(
                    planingPeriod, weekAndWorkday, IngredientType.VEGETABLE) == PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekAndWorkday);
            }
            else {
                return false;
            }
        }
        else if (recipe.isVegetableRecipe()) {

            // Vegetarisches Gericht darf nur eingefügt werden, wenn mindestens ein Fleischgericht vorhanden ist
            if (countRecipesForWeekWorkdayAndIngredientType(planingPeriod, weekAndWorkday, IngredientType.MEAT) >= PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN) {
                return validateMaxRecipesForWeekWorkday(planingPeriod, weekAndWorkday);
            }
            else {
                return false;
            }
        }

        return false;
    }

    /**
     * Für den Fall, dass {@link MenuPlanBuilder#validateRecipeBeforeInsertIntoWeekWorkday(Map, WeekWorkday, Recipe)}
     * WAHR zurückgibt, findet in dieser Methode die finale Überprüfung statt ob ein {@link Recipe} wirklich hinzugefügt
     * werden darf oder nicht. Das {@link Recipe} darf nur dann hinzugefügt werden wenn bisher kein Gericht diesem Tag
     * zugeordnet ist oder die Anzahl der Gerichte an diesem Tag kleiner der maximal erlaubten Anzahl an Gerichten für
     * einen Tag ist.
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @return
     */
    private boolean validateMaxRecipesForWeekWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekAndWorkday) {
        return planingPeriod.get(weekAndWorkday).isEmpty() ? true : planingPeriod.get(weekAndWorkday).size() < PROP_PLANINGPERIOD_MEALSPERDAY ? true : false;
    }

    /**
     * Zählt die Anzahl der Rezepte für einen übergebenen {@link WeekWorkday} und einen {@link IngredientType}.
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @param ingredientType
     * @return
     */
    private int countRecipesForWeekWorkdayAndIngredientType(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            WeekWorkday weekAndWorkday, IngredientType ingredientType) {
        int result = 0;

        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            if (entry.getKey().equals(weekAndWorkday)) {

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
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @param ingredientType
     * @return
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
     * @param planingPeriod
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
     * 
     * @param planingPeriod
     * @param weeksWithoutFishRecipes
     */
    private void addMissingFishRecipesToPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<Integer> weeksWithoutFishRecipes) {

        for (Integer week : weeksWithoutFishRecipes) {
            boolean fishRecipeForWeekAdded = false;

            Set<WeekWorkday> weekWorkdays = getWeekAndWorkdaysByWeek(planingPeriod, week);

            Set<Recipe> usedFishRecipes = this.canteenContext.getFishRecipes();

            Set<Recipe> availableFishRecipes = this.recipeBase
                    .getRecipesForIngredientTypeSortedByRank(IngredientType.FISH);

            if (availableFishRecipes != null && !availableFishRecipes.isEmpty()) {

                if (usedFishRecipes != null && !usedFishRecipes.isEmpty()) {
                    availableFishRecipes.removeAll(usedFishRecipes);
                }

                for (WeekWorkday weekWorkday : weekWorkdays) {

                    Set<Recipe> weekWorkdayRecipes = planingPeriod.get(weekWorkday);

                    int meatRecipes = countRecipesForIngredientType(weekWorkdayRecipes, IngredientType.MEAT);
                    int vegetableRecipes = countRecipesForIngredientType(weekWorkdayRecipes, IngredientType.VEGETABLE);

                    if (meatRecipes >= PROP_PLANINGPERIOD_MEATMEALSPERDAY_MIN && vegetableRecipes >= PROP_PLANINGPERIOD_VEGETABLEMEALSPERDAY_MIN) {

                        Recipe recipeToRemove;

                        // Wenn es mehr Fleischgerichte als vegetarische Gerichte an dem Tag gibt, wird ein
                        // Fleischgericht entfernt...
                        if (meatRecipes > vegetableRecipes) {
                            recipeToRemove = removeRecipeFromWeekWorkday(weekWorkdayRecipes, IngredientType.MEAT);
                        }
                        // ...ansonsten ein vegetarisches Gericht
                        else {
                            recipeToRemove = removeRecipeFromWeekWorkday(weekWorkdayRecipes, IngredientType.VEGETABLE);
                        }

                        for (Recipe fishRecipe : availableFishRecipes) {

                            if (addRecipeToPlaningPeriod(planingPeriod, weekWorkday, fishRecipe)) {
                                removeQuantityFromProviderIngredientQuantities(weekWorkdayRecipes, fishRecipe);
                                fishRecipeForWeekAdded = true;
                                break;
                            }
                        }

                        if (fishRecipeForWeekAdded) {
                            break;
                        }
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
     * @param planingPeriod
     * @return
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
     * @param planingPeriod
     * @param weekAndWorkday
     * @return
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
     * 
     * @param planingPeriod
     * @param week
     * @return
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
     * 
     * @param recipes
     * @param ingredientType
     */
    private Recipe removeRecipeFromWeekWorkday(Set<Recipe> weekWorkdayRecipes, IngredientType ingredientType) {
        Recipe result = null;

        // Rezepte für den übergebenen Wochentag werden nach Rang sortiert, allerdings in umgekehrter Reihenfolge um
        // danach das unbeliebteste zu entfernen
        Set<Recipe> sortedRecipes = new TreeSet<Recipe>(new Comparator<Recipe>() {

            @Override
            public int compare(Recipe r1, Recipe r2) {
                return Integer.valueOf(r2.getRank()).compareTo(Integer.valueOf(r1.getRank()));
            }
        });

        sortedRecipes.addAll(weekWorkdayRecipes);

        // Das erste Rezept welches für den übergebenen IngredientType gefunden wird, wird aus dem Wochentag entfernt
        for (Recipe recipe : sortedRecipes) {

            if (ingredientType.equals(recipe.getIngredientType())) {

                // Hinzufügen der Zutatenmengen des Rezepts in die Gesamtmenge
                addQuantityToProviderIngredientQuantities(weekWorkdayRecipes, recipe);

                // Entfernen des Rezepts aus dem Wochentag
                weekWorkdayRecipes.remove(recipe);

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
     * 
     * @param weekWorkdayRecipes
     * @param recipe
     */
    private void undoRemoveRecipeFromWeekWorkday(Set<Recipe> weekWorkdayRecipes, Recipe recipe) {
        // Hinzufügen des Rezepts in den Wochentag
        weekWorkdayRecipes.add(recipe);

        // Hinzufügen des Rezepts in den Kantinenkontext
        this.canteenContext.getRecipes().add(recipe);

        // Entfernen der Zutatenmengen des Rezepts aus der Gesamtmenge
        removeQuantityFromProviderIngredientQuantities(weekWorkdayRecipes, recipe);
    }

    /**
     * 
     * @param weekWorkdayRecipes
     * @param recipe
     */
    private void addQuantityToProviderIngredientQuantities(Set<Recipe> weekWorkdayRecipes, Recipe recipe) {

        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(weekWorkdayRecipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    BigDecimal quantityForIngredient = NumberHelper.multiply(ingredientQuantity.getValue(),
                            mealMultiplyFactor);

                    this.providerIngredientQuantities.get(ingredient).add(
                            new Amount(quantityForIngredient, ingredientQuantity.getUnit()));
                }
            }
        }
    }

    /**
     * 
     * @param weekWorkdayRecipes
     * @param recipe
     */
    private void removeQuantityFromProviderIngredientQuantities(Set<Recipe> weekWorkdayRecipes, Recipe recipe) {

        Integer weekWorkdayPositionOfRecipe = getWeekWorkdayPositionOfRecipe(weekWorkdayRecipes, recipe);

        if (weekWorkdayPositionOfRecipe != null) {

            BigDecimal mealMultiplyFactor = BuilderHelper.calculateMealMultiplyFactor(weekWorkdayPositionOfRecipe,
                    this.canteenContext.getTotalMealsForCanteen());

            for (IngredientListItem ingredientListItem : recipe.getIngredientList()) {

                Ingredient ingredient = ingredientListItem.getIngredient();
                Amount ingredientQuantity = ingredientListItem.getQuantity();

                if (this.providerIngredientQuantities.containsKey(ingredient)) {

                    BigDecimal quantityForIngredient = NumberHelper.multiply(ingredientQuantity.getValue(),
                            mealMultiplyFactor);

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
     * @param planingPeriod
     * @param weekWorkdays
     * @return
     */
    public WeekWorkday getWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod, Set<WeekWorkday> weekWorkdays) {

        if (PROP_PLANINGPERIOD_PLANINGMODE.equals("") || PROP_PLANINGPERIOD_PLANINGMODE.equals(PLANING_MODE_SEQUENTIAL)) {
            return getSequentialWeekAndWorkday(planingPeriod, weekWorkdays);
        }
        else if (PROP_PLANINGPERIOD_PLANINGMODE.equals(PLANING_MODE_RANDOM)) {
            return getRandomWeekAndWorkday(planingPeriod, weekWorkdays);
        }

        return null;
    }

    /**
     * 
     * @param planingPeriod
     * @param weekWorkdays
     * @return
     */
    private WeekWorkday getSequentialWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<WeekWorkday> weekWorkdays) {

        if (weekWorkdays.isEmpty()) {
            return planingPeriod.keySet().iterator().next();
        }

        if (weekWorkdays.size() + 1 > PROP_PLANINGPERIOD_TOTALWEEKANDWORKDAYS) {
            return null;
        }

        WeekWorkday lastWeekWorkday = new LinkedList<WeekWorkday>(weekWorkdays).getLast();

        Iterator<WeekWorkday> iterator = planingPeriod.keySet().iterator();

        while (iterator.hasNext()) {
            WeekWorkday weekWorkday = iterator.next();

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
     * 
     * @param planingPeriod
     * @param weekWorkdays
     * @return
     */
    private WeekWorkday getRandomWeekAndWorkday(Map<WeekWorkday, Set<Recipe>> planingPeriod,
            Set<WeekWorkday> weekWorkdays) {
        List<WeekWorkday> keys = new ArrayList<WeekWorkday>(planingPeriod.keySet());

        if (!keys.isEmpty() && !weekWorkdays.isEmpty()) {
            keys.removeAll(weekWorkdays);
        }

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
     * einem Set von {@link Recipe}.
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
            this.totalMealsForCanteen = BuilderHelper.calculateTotalMealsForCanteen(canteen);
        }

        /**
         * Ermittelt die zu kochenden Fischrezepte für eine Kantine.
         * 
         * @return
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

        public Canteen getCanteen() {
            return canteen;
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
