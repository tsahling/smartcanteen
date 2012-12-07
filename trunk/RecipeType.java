
/**
 * Die Klasse {@link RecipeType} stellt ein Detenobjekt vom Typ Enumeration dar. Durch die Klasse {@link RecipeType} wird der Typ (Fisch, Fleisch, Vegetarisch) eines Gericht deklariert.
 * 
 * @author Francesco Luciano
 */
public enum RecipeType {

    MEAT("MEAT", "Fleischgericht", "Fleisch"),
    FISH("FISH", "Fischgericht", "Fisch"),
    VEGETABLE("VEGETABLE", "Vegetarisches Gericht", "Vegetarisch");

    private final String code;
    private final String name;
    private final String shortName;

    /**
     * @param code
     * @param name
     * @param shortName
     */
    private RecipeType(String code, String name, String shortName) {
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
