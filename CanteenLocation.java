
public enum CanteenLocation
{
    ESSEN("Essen", "Essen", 500, 51.455644, 7.011554),
    MUELHEIM("Mülheim", "Mülheim an der Ruhr", 300, 51.430958, 6.880744);

    private final String code;
    private final String name;
    private final int numberOfEmployees;
    private final double latitude;
    private final double longitude;

    /**
     * 
     * @param code
     * @param name
     * @param numberOfEmployees
     * @param latitude
     * @param longitude
     */
    private CanteenLocation(String code, String name, int numberOfEmployees, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
        this.latitude = latitude;
        this.longitude = longitude;
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
     * @return the numberOfEmployees
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
}