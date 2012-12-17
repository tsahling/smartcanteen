package de.osjava.smartcanteen.datatype;

/**
 * Die Klasse {@link IngredientType} stellt ein Datenobjekt vom Typ Enumeration dar.
 * Durch die Klasse {@link IngredientType} wird der Typ (Fisch, Fleisch,
 * Vegetarisch) eines Lebensmittel deklariert.
 * 
 * @author Francesco Luciano
 */
public enum IngredientType {

    MEAT("MEAT", "Fleischgericht", "Fleisch"), FISH("FISH", "Fischgericht",
            "Fisch"), VEGETABLE("VEGETABLE", "Vegetarisches Gericht",
            "Vegetarisch");

    private final String code;
    private final String name;
    private final String shortName;

    /**
     * Standardkonstruktor wird in diesem Fall gebraucht weil der Enum mit
     * ergänzenden Informationen deklariert wird. Damit die Methoden getCode(),
     * getName() und getShortName() funktionieren ist der Konstruktor notwendig
     * 
     * @param code
     *            Deklaration des ENUM z.B. MEAT
     * @param name
     *            Beschreibung des ENUM z.B. Fleischgericht
     * @param shortName
     *            Kurzbeschreibung des ENUM z.B. Fleisch
     */
    private IngredientType(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * @return Deklaration des ENUM
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Beschreibung des ENUM
     */
    public String getName() {
        return name;
    }

    /**
     * @return Kurzbeschreibung des ENUM
     */
    public String getShortName() {
        return shortName;
    }
}
