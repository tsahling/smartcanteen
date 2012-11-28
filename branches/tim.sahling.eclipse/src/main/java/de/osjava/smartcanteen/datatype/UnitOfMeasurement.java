package de.osjava.smartcanteen.datatype;

public enum UnitOfMeasurement {

    GRM("GRM", "Gramm", "g"),
    KGM("KGM", "Kilogramm", "kg"),
    TON("TON", "Tonne", "t"),
    MTR("MTR", "Meter", "m"),
    KMR("KMR", "Kilometer", "km"),
    MLR("MLR", "Milliliter", "ml"),
    LTR("LTR", "Liter", "l"),
    STK("STK", "Stück", "stk"),
    EUR("EUR", "Euro", "Eur");

    private final String code;
    private final String name;
    private final String shortName;

    /**
     * 
     * @param code
     * @param name
     * @param shortName
     */
    private UnitOfMeasurement(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }
}
