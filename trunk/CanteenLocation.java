
/**
 * Enumeration class CanteenLocation - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum CanteenLocation
{
    ESSEN("Essen", "Essen", 51.455644, 7.011554),
    MUELHEIM("Mülheim", "Mülheim an der Ruhr", 51.430958, 6.880744);
    
    private final String code;
    private final String name;
    private final double latitude;
    private final double longitude;
    
    private CanteenLocation(String code, String name, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String getCode() {
        return this.code;
    }   
    
    public String getName() {
        return this.name;
    }    
    
    public double getLatitude() {
        return this.latitude;
    }
    
    public double getLongitude() {
        return this.longitude;
    }
}
