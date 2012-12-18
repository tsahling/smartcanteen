package de.osjava.smartcanteen.datatype;

import de.osjava.smartcanteen.data.Canteen;

/**
 * {@link CanteenLocation} stellt ein Datenobjekt vom Typ Enumeration dar. Ein
 * Enum verhält sich wie eine Klasse mit Attributen und Methoden kann jedoch
 * nicht vererben. Durch die Klasse {@link Canteen} wird der Typ (z.B. Essen,
 * Muehlheim) einer Kantine deklariert. Die Werte werden final gespeichert und
 * daher nur get-Methoden bereit gestellt.
 * 
 * @author Marcel Baxmann
 */
public enum CanteenLocation {

    ESSEN("ESSEN", "Essen", "Essen", 51.455644, 7.011554),
    MUELHEIM("MUELHEIM", "Muelheim an der Ruhr", "Muelheim", 51.430958, 6.880744);

    private final String code;
    private final String name;
    private final String shortName;
    private final double latitude;
    private final double longitude;

    /**
     * Im Standardkonstruktor werden dem Enum {@link CanteenLocation} Variablen
     * übergeben, die final gespeichert werden.
     * 
     * @param code
     *            Deklaration des ENUM z.B. ESSEN
     * @param name
     *            Beschreibung des ENUM z.B. Stadt Essen
     * @param shortName
     *            Kurzbeschreibung des ENUM z.B. Essen
     * @param latitude
     *            Breitengrad der Ortes
     * @param longitude
     *            Längengrad des Ortes
     */
    private CanteenLocation(String code, String name, String shortName,
            double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Methode zum Abrufen des Werts Code
     * 
     * @return code Deklaration
     */
    public String getCode() {
        return code;
    }

    /**
     * Methode zum Abrufen des Werts Name
     * 
     * @return name Beschreibung
     */
    public String getName() {
        return name;
    }

    /**
     * Methode zum Abrufen des Werts ShortName
     * 
     * @return shortName Kurzbeschreibung
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Methode zum Abrufen des Werts Latitude
     * 
     * @return latitude Breitengrad
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Methode zum Abrufen des Werts Longitude
     * 
     * @return longitude Laengengrad
     */
    public double getLongitude() {
        return longitude;
    }
}
