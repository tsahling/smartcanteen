package de.osjava.smartcanteen.enums;


 /**
 * @author Tim Sahling
 */
public enum MealType {

    MEAT ("Fleisch", "Fleischgericht"),
    FISH ("Fisch", "Fischgericht"),
    VEGETABLE ("Vegetarisch", "Vegetarisches Gericht");
    
    private final String code;
    private final String name;
    
    private MealType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
}
