
/**
 * Enumeration class CantineLocation - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum CanteenLocation
{
    ESSEN ("Essen", "Essen", 51.455644, 7.011554),
    MUELHEIM ("Mülheim", "Mülheim an der Ruhr", 51.430958, 6.880744);
    
    private final String name;
    private final String fullname;
    private final double latitude;
    private final double longitude;
    
    private CanteenLocation(String name, String fullname, double latitude, double longitude) {
        this.name = name;
        this.fullname = fullname;
        this.latitude = latitude;
        this.longitude = longitude;
    }
       
    public String getName() {
        return this.name;
    }
    
    public String getFullname() {
        return this.fullname;
    }
    
    public double getLatitude() {
        return this.latitude;
    }
    
    public double getLongitude() {
        return this.longitude;
    }
}
