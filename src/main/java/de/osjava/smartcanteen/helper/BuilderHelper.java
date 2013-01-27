package de.osjava.smartcanteen.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.osjava.smartcanteen.data.Canteen;

/**
 * 
 * @author Tim Sahling
 */
public class BuilderHelper {

    private static final BigDecimal PROP_CANTEEN_MOREMEALSTHANEMPLOYEESFACTOR = new BigDecimal(
            PropertyHelper.getProperty("canteen.moreMealsThanEmployeesFactor"));

    private static final Integer PROP_PLANINGPERIOD_MEALSPERDAY = Integer.valueOf(PropertyHelper
            .getProperty("planingPeriod.mealsPerDay"));

    private static final BigDecimal PROP_CANTEEN_FAVMEALMULTIPLYFACTOR = new BigDecimal(
            PropertyHelper.getProperty("canteen.favMealMultiplyFactor"));

    /**
     * Kalkuliert die Gesamtmenge an Gerichten die für eine Kantine gekocht werden müssen, ausgehend von der
     * Anforderung, dass eine gewisse Anzahl bzw. ein gewisser Faktor an Gerichten pro Kantine mehr vorgehalten werden.
     * 
     * @param canteen
     * @return
     */
    public static BigDecimal calculateTotalMealsForCanteen(Canteen canteen) {
        return NumberHelper.multiply(BigDecimal.valueOf(canteen.getNumberOfEmployees()),
                PROP_CANTEEN_MOREMEALSTHANEMPLOYEESFACTOR);
    }

    /**
     * Kalkuliert die Gesamtmenge an Gerichten die, je nach Priorität des Gerichts, gekocht werden müssen, ausgehend von
     * der Anforderung, dass das beliebtestete Gericht auf Basis eines Faktors öfter gekocht wird als die anderen beiden
     * Gerichte.
     * 
     * @param position
     * @param totalMeals
     * @return
     */
    public static BigDecimal calculateMealMultiplyFactor(int position, BigDecimal totalMeals) {
        BigDecimal result = null;

        if (position == 0) {
            result = NumberHelper.multiply(totalMeals, PROP_CANTEEN_FAVMEALMULTIPLYFACTOR);
        }
        else {
            result = NumberHelper.divide(NumberHelper.multiply(totalMeals, PROP_CANTEEN_FAVMEALMULTIPLYFACTOR),
                    BigDecimal.valueOf(PROP_PLANINGPERIOD_MEALSPERDAY - 1)).setScale(0, RoundingMode.HALF_UP);
        }

        return result;
    }
}
