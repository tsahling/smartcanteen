package de.osjava.smartcanteen.datatype;

public enum CanteenLocation {

    ESSEN("ESSEN", "Essen", "Essen", 51.455644, 7.011554),
    MUELHEIM("MUELHEIM", "Muelheim an der Ruhr", "Muelheim", 51.430958, 6.880744);

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

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
