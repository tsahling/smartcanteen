package de.osjava.smartcanteen.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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
import de.osjava.smartcanteen.data.Recipe;
import de.osjava.smartcanteen.datatype.CanteenLocation;
import de.osjava.smartcanteen.datatype.IngredientType;
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
    private static final Integer PROP_PLANINGPERIOD_WEEKWORKDAYS = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.weekWorkdays"));
    private static final Integer PROP_PLANINGPERIOD_WEEKS = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.weeks"));

    private static final Integer PROP_PLANINGPERIOD_TOTALWEEKANDWORKDAYS = (PROP_PLANINGPERIOD_WEEKWORKDAYS * PROP_PLANINGPERIOD_WEEKS);
    private static final Integer PROP_PLANINGPERIOD_TOTALMEALS = (PROP_PLANINGPERIOD_MEALSPERDAY * PROP_PLANINGPERIOD_WEEKWORKDAYS * PROP_PLANINGPERIOD_WEEKS);

    private static final String PLANING_MODE_SEQUENTIAL = "sequential";
    private static final String PLANING_MODE_RANDOM = "random";

    private ProviderBase providerBase; // TODO(Tim Sahling) Berechnung auf Basis günstigster Gerichte
    private RecipeBase recipeBase;
    private Canteen[] canteens;

    /**
     * Der Standardkonstruktor der {@link MenuPlanBuilder} initialisiert die
     * Klasse beim Erstellen und nimmt einige wichtige Eingangsdaten entgegen.
     * 
     * @param providerBase
     *            Die {@link ProviderBase} Verwaltungs- bzw. Containerklasse
     * @param recipeBase
     *            Die {@link RecipeBase} Verwaltungs- bzw. Containerklasse
     */
    public MenuPlanBuilder(ProviderBase providerBase, RecipeBase recipeBase) {
        this.providerBase = providerBase;
        this.recipeBase = recipeBase;
        this.canteens = new Canteen[]{ new Canteen(CanteenLocation.ESSEN, Integer.valueOf(PropertyHelper
                .getProperty("canteen.essen.numberOfEmployees"))), new Canteen(CanteenLocation.MUELHEIM,
                Integer.valueOf(PropertyHelper.getProperty("canteen.muelheim.numberOfEmployees"))) };
    }

    /**
     * Die einzige öffentliche Methode der Klasse {@link MenuPlanBuilder} ruft
     * die Applikationslogik und den damit verbundenen Optimierungsalgorithmus
     * für die Generierung des {@link MenuPlan} und der {@link Meal}s auf.
     * 
     * @return Ein Array von Kantinen für die Verwendung im Output
     */
    public Canteen[] buildMenuPlan() {

        // Initalisiert die Planungsperiode
        Map<WeekWorkday, Set<Recipe>> planingPeriod = initPlaningPeriod();

        // Holt die Rezepte, sortiert nach Rang um die Zufriedenheit und Leistungsfähigkeit der Mitarbeiter zu steigern
        Set<Recipe> recipesSortedByRank = recipeBase.getRecipesSortedByRank();

        // Um den Kantinen einen größere Individualität zu geben wird der Algorithmus für den optimalen Speiseplan pro
        // Kantine durchlaufen.
        for (Canteen canteen : canteens) {

            // Fügt die Rezepte in die Struktur der Planungsperiode ein
            for (Recipe recipe : recipesSortedByRank) {
                addRecipeToPlaningPeriod(recipe, planingPeriod);
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

        return canteens;
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
        MenuPlan result = new MenuPlan();
        result.setMeals(new ArrayList<Meal>(PROP_PLANINGPERIOD_TOTALMEALS));
        return result;
    }

    /**
     * Erstellt ein {@link Meal}.
     * 
     * @param date
     * @param recipe
     * @return
     */
    private Meal createMeal(Date date, Recipe recipe) {
        Meal result = new Meal();
        result.setDate(date);
        result.setRecipe(recipe);
        return result;
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
        cal.setTime(new Date());

        int weekday = cal.get(Calendar.DAY_OF_WEEK);

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
                    addRecipeToPlaningPeriod(planingPeriod, weekAndWorkday, recipe);
                    break;
                }

                tempWeekWorkdays.add(weekAndWorkday);
            }
        }
    }

    /**
     * Fügt ein Rezept der Planungsperiode hinzu.
     * 
     * @param planingPeriod
     * @param weekAndWorkday
     * @param recipe
     */
    private void addRecipeToPlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod, WeekWorkday weekAndWorkday,
            Recipe recipe) {
        Set<Recipe> weekWorkDayRecipes = planingPeriod.get(weekAndWorkday);

        if (!weekWorkDayRecipes.contains(recipe)) {
            weekWorkDayRecipes.add(recipe);
        }
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

            // Wenn Rezept ein Fischrezept ist, muss überprüft werden ob es bereits ein Fischrezept diese Woche gibt
            if (recipe.isFishRecipe()) {

                if (existsFishRecipeInWeek(planingPeriod, weekAndWorkday)) {
                    return false;
                }
            }

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
            // Wenn Rezept ein Fischrezept ist, muss überprüft werden ob es bereits ein Fischrezept diese Woche gibt
            else if (recipe.isFishRecipe()) {

                if (existsFishRecipeInWeek(planingPeriod, weekAndWorkday)) {
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

            // Fischgericht darf nur eingefügt werden, wenn ein Fleischgericht und ein Fischgericht vorhanden sind
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
     * Überprüft ob in der übergebenen Planungsperiode und in der übergegebenen Woche bereits ein Fischrezept vorhanden
     * ist
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

                    if (recipe.isMeatRecipe() && ingredientType.equals(IngredientType.MEAT)) {
                        result++;
                    }
                    else if (recipe.isFishRecipe() && ingredientType.equals(IngredientType.FISH)) {
                        result++;
                    }
                    else if (recipe.isVegetableRecipe() && ingredientType.equals(IngredientType.VEGETABLE)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Zählt die Anzahl der Rezepte für einen übergebenen {@link IngredientType}.
     * 
     * @param planingPeriod
     * @param ingredientType
     * @return
     */
    private int countRecipesForIngredientType(Map<WeekWorkday, Set<Recipe>> planingPeriod, IngredientType ingredientType) {
        int result = 0;

        for (Entry<WeekWorkday, Set<Recipe>> entry : planingPeriod.entrySet()) {

            for (Recipe recipe : entry.getValue()) {

                if (recipe.isMeatRecipe() && ingredientType.equals(IngredientType.MEAT)) {
                    result++;
                }
                else if (recipe.isFishRecipe() && ingredientType.equals(IngredientType.FISH)) {
                    result++;
                }
                else if (recipe.isVegetableRecipe() && ingredientType.equals(IngredientType.VEGETABLE)) {
                    result++;
                }
            }

        }

        return result;
    }

    /**
     * 
     * @param planingPeriod
     */
    private void validatePlaningPeriod(Map<WeekWorkday, Set<Recipe>> planingPeriod) {
        int totalMeals = 0;

        for (Set<Recipe> recipes : planingPeriod.values()) {
            totalMeals = totalMeals + recipes.size();
        }

        if (totalMeals != PROP_PLANINGPERIOD_TOTALMEALS) {
            throw new RuntimeException(PropertyHelper.getProperty("message.notEnoughMealsInPeriod.exception"));
        }

        // Wenn nicht mindestens ein Fischgericht jede Woche vorhanden ist, muss dieses nachträglich eingefügt werden
        if (countRecipesForIngredientType(planingPeriod, IngredientType.FISH) != PROP_PLANINGPERIOD_WEEKS) {
            // TODO(Tim Sahling) Wochen auswählen in denen kein Fischgericht vorhanden ist und hinzufügen

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

            for (WeekWorkday weekWorkday : keys)
            {
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
                    public int compare(Recipe arg0, Recipe arg1) {
                        return Integer.valueOf(arg0.getRank()).compareTo(Integer.valueOf(arg1.getRank()));
                    }
                }));
            }
        }

        return result;
    }

    /**
     * Repräsentiert eine temporäre Datenhaltungsklasse, die eine Woche und einen Wochentag als Integer aufnehmen kann.
     * 
     * @author Tim Sahling
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
