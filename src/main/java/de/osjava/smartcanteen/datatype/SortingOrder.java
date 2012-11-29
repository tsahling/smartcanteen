package de.osjava.smartcanteen.datatype;

/**
 * 
 * @author Marcel Baxmann
 */
public enum SortingOrder {

    ASC("ASC", "Ascending"),
    DESC("DESC", "Descending");

    private final String code;
    private final String name;

    /**
     * 
     * @param code
     * @param name
     * @param shortName
     */
    private SortingOrder(String code, String name) {
        this.code = code;
        this.name = name;

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
}
