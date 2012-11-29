
public enum CanteenLocation
{
    ESSEN("ESSEN", "Essen", "Essen", 51.455644, 7.011554),
    MUELHEIM("MUELHEIM", "Mülheim an der Ruhr", "Mülheim", 51.430958, 6.880744);

    private final String code;
    private final String name;
    private final String shortName;
    private final double latitude;
    private final double longitude;

    /**
     * 
     * @param code
     * @param name
     * @param shortName
     * @param latitude
     * @param longitude
     */
    private CanteenLocation(String code, String name, String shortName, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
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
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
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