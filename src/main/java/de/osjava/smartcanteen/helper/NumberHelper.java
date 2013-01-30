package de.osjava.smartcanteen.helper;

import java.math.BigDecimal;

/**
 * Die Klasse {@link NumberHelper} bietet Methoden zum Rechnen mit {@link BigDecimal}s und einige andere nummerische
 * Hilfsmethoden, die an diversen Stellen in der Applikation eingesetzt werden.
 * 
 * @author Tim Sahling
 */
public class NumberHelper {

    /**
     * Vergleicht ob der erste übergebene Parameter größer oder gleich dem zweiten übergebenen Parameter ist.
     * 
     * @param value1 Parameter 1
     * @param value2 Parameter 2
     * @return wahr/falsch, je nachdem ob der erste übergebene Parameter größer oder gleich dem zweiten übergebenen
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
     * Subtrahiert übergebenen Wert 2 von Wert 1.
     * 
     * @param value1 Wert 1
     * @param value2 Wert 2
     * @return Subtraktion aus Wert 1 und Wert 2
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        return value1.subtract(value2);
    }

    /**
     * Multipliziert zwei übergebene Werte miteinander.
     * 
     * @param value1 Wert 1
     * @param value2 Wert 2
     * @return Produkt aus Wert 1 und Wert 2
     */
    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2);
    }

    /**
     * Dividiert zwei übergebene Werte miteinander.
     * 
     * @param value1 Wert 1
     * @param value2 Wert 2
     * @return Division aus Wert 1 und Wert 2
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2);
    }
}
