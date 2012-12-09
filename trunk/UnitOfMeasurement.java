
/**
 * {@link UnitofMeasurement} stellt ein Datenobjekt vom Typ Enumeration dar. Ein "Enum" verhält sich 
 * wie eine Klasse mit Attributen und Methoden kann jedoch nicht vererben.
 * Durch die Klasse {@link Amount} wird der Typ (z.B. GRM oder EUR) eines Amount deklariert.
 * Die Werte werden final gespeichert und daher nur get-Methoden bereit gestellt.
 * 
 * @author Marcel Baxmann
 */
public enum UnitOfMeasurement {

    GRM("GRM", "Gramm", "g"),
    KGM("KGM", "Kilogramm", "kg"),
    TON("TON", "Tonne", "t"),
    MTR("MTR", "Meter", "m"),
    KMR("KMR", "Kilometer", "km"),
    MLR("MLR", "Milliliter", "ml"),
    LTR("LTR", "Liter", "l"),
    STK("STK", "Stueck", "stk"),
    EUR("EUR", "Euro", "Eur");

    private final String code;
    private final String name;
    private final String shortName;

    /**
     * Im Standardkonstruktor werden dem  Enum {@link UnitOfMeasurement} Variablen übergeben, die final gespeichert werden.
     * 
     * @param code   Deklaration des ENUM z.B. KGM
     * @param name    Beschreibung des ENUM z.B. Kilogramm
     * @param shortName   Kurzbeschreibung des ENUM z.B. kg
     */
    private UnitOfMeasurement(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * Methode zum Abrufen des Werts Code
     * 
     * @return code Deklaration 
     */
    public String getCode() {
        return code;
    }

    /**
     * Methode zum Abrufen des Werts Name
     * 
     * @return name Beschreibung
     */
    public String getName() {
        return name;
    }

    /**
     * Methode zum Abrufen des Werts ShortName
     * 
     * @return shortName Kurzbeschreibung 
     */
    public String getShortName() {
        return shortName;
    }
}
