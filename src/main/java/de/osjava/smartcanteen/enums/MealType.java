package de.osjava.smartcanteen.enums;

/**
 * @author Tim Sahling
 */
public enum MealType {

    MEAT("Fleisch", "Fleischgericht"),
    FISH("Fisch", "Fischgericht"),
    VEGETABLE("Vegetarisch", "Vegetarisches Gericht");

    private final String name;
    private final String fullname;

    private MealType(String name, String fullname) {
        this.name = name;
        this.fullname = fullname;
    }

    public String getName() {
        return this.name;
    }

    public String getFullname() {
        return this.fullname;
    }
}
