
/**
 * Enumeration class UnitOfMeasurement - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum UnitOfMeasurement
{
    GRM("GRM", "Gramm", "g"),
    KGM("KGM", "Kilogramm", "kg"),
    TON("TON", "Tonne", "t"),
    MTR("MTR", "Meter", "m"),
    KMR("KMR", "Kilometer", "km"),
    MLR("MLR", "Milliliter", "ml"),
    LTR("LTR", "Liter", "l"),
    EUR("EUR", "Euro", "Eur");
    
    private final String code;
    private final String name;
    private final String shortName;
    
    private UnitOfMeasurement(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }
    
    /**
     * 
     * @return the {@link #code} value.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 
     * @return the {@link #name} value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @return the {@link #shortName} value.
     */
    public String getShortName() {
        return this.shortName;
    } 
}
