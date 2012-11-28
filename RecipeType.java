
/**
 * Enumeration class RecipeType - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum RecipeType
{
    MEAT("MEAT", "Fleischgericht", "Fleisch"),
    FISH("FISH", "Fischgericht", "Fisch"),
    VEGETABLE("VEGETABLE", "Vegetarisches Gericht", "Vegetarisch");
    
    private final String code;
    private final String name;
    private final String shortName;
    
    private RecipeType(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }
  
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getShortName() {
        return this.shortName;
    }
}
