package de.osjava.smartcanteen.helper;

import java.math.BigDecimal;

public class NumberHelper {

    /**
     * Vergleicht ob der erste übergebene Parameter größer oder gleich dem zweiten übergebenen Parameter ist.
     * 
     * @param value1 Parameter 1
     * @param value2 Parameter 2
     * @return WAHR/FALSCH, je nachdem ob der erste übergebene Parameter größer oder gleich dem zweiten übergebenen
     *         Parameter ist
     */
    public static boolean compareGreaterOrEqual(BigDecimal value1, BigDecimal value2) {

        if (value1 == null || value2 == null) {
            return false;
        }

        int compare = value1.compareTo(value2);

        if (compare == -1) {
            return false;
        }
        else if (compare == 0) {
            return true;
        }
        else {
            return true;
        }
    }

    /**
     * 
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        return value1.subtract(value2);
    }

    /**
     * 
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2);
    }

    /**
     * 
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2);
    }
}
