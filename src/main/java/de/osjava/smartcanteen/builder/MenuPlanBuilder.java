package de.osjava.smartcanteen.builder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private ProviderBase providerBase;
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
        this.canteens = new Canteen[]{
                new Canteen(
                        CanteenLocation.ESSEN,
                        Integer.valueOf(PropertyHelper
                                .getProperty("canteen.essen.numberOfEmployees"))),
                new Canteen(
                        CanteenLocation.MUELHEIM,
                        Integer.valueOf(PropertyHelper
                                .getProperty("canteen.muelheim.numberOfEmployees"))) };
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
        Map<Integer, Map<Integer, Set<Recipe>>> planingPeriod = initPlaningPeriod();

        // Holt die Rezepte sortiert nach Rang
        Set<Recipe> recipesSortedByRank = recipeBase.getRecipesSortedByRank();

        for (Recipe recipe : recipesSortedByRank) {
            Integer randomWeek = getRandomWeek(planingPeriod);
            Integer randomWorkday = getRandomWeekWorkday(planingPeriod, randomWeek);

            planingPeriod.get(randomWeek).get(randomWorkday).add(recipe);
        }

        // Variante 1:
        // Alle Menüs nach Hitliste zufällig auf Tage/Wochen verteilen (Regeln beachten)
        // danach gucken wo man es am günstigsten einkaufen kann

        // Zusatzfeatures

        // preisgünstige Varianten ermitteln für alle Tage und alle Gerichtskonstellationen

        // Variable = wie hoch ist der Faktor der Essensanzahl die je Gericht am Kantinenstandort festgelegt werden
        // sollen

        // preisliste durchsuchen und auswahl günstiger zutaten durch el chefkoch

        return canteens;
    }

    private Map<Integer, Map<Integer, Set<Recipe>>> initPlaningPeriod() {
        Map<Integer, Map<Integer, Set<Recipe>>> result = new HashMap<Integer, Map<Integer, Set<Recipe>>>();

        Integer weeks = Integer.valueOf(PropertyHelper.getProperty("planingPeriod.weeks"));
        Integer workdays = Integer.valueOf(PropertyHelper.getProperty("planingPeriod.weekWorkdays"));

        for (int x = 1; x <= weeks; x++) {

            result.put(x, new HashMap<Integer, Set<Recipe>>());

            for (int y = 1; y <= workdays; y++) {

                result.get(x).put(y, new TreeSet<Recipe>(new Comparator<Recipe>() {

                    @Override
                    public int compare(Recipe arg0, Recipe arg1) {
                        return Integer.valueOf(arg0.getRank()).compareTo(Integer.valueOf(arg1.getRank()));
                    }
                }));
            }
        }

        return result;
    }

    public Integer getRandomWeek(Map<Integer, Map<Integer, Set<Recipe>>> planingPeriod) {
        List<Integer> keys = new ArrayList<Integer>(planingPeriod.keySet());
        return keys.get(new Random().nextInt(keys.size()));
    }

    public Integer getRandomWeekWorkday(Map<Integer, Map<Integer, Set<Recipe>>> planingPeriod, Integer week) {
        List<Integer> keys = new ArrayList<Integer>(planingPeriod.get(week).keySet());
        return keys.get(new Random().nextInt(keys.size()));
    }
}
