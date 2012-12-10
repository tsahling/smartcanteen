import java.math.BigDecimal;

/**
 * Die Klasse {@link Amount} stellt ein Datenobjekt dar, mit dem Einheiten von irgendetwas beschrieben werden koennen
 * die bei Ihrer Generierung noch nicht genau definiert sind. So zum Beispiel nutzt {@link IngredientListItem} 
 * Amount um eine Quantitaet eines Guts festzulegen, dabei ist zur Kompilierung des Quellcodes noch nicht definiert
 * in welcher Menge gezaehlt wird - ob KG, Eier, Liter. Die Menge wird dabei als BigDecimal gespeichert und 
 * die Einheitsbeschreibung als UnitOfMeasurement. Der Enum UnitOfMeasurement unterstuetzt dieses Szenario indem
 * dieser verschiedene Werte anbietet wie z.B. eine Waehrung fuer Preise oder eine Mengeneinheit fuer Rezepte.
 * 
 * @author Marcel Baxmann
 */
public class Amount {

    private BigDecimal value;
    private UnitOfMeasurement unit;

    /**
     * Dem Standardkonstruktur werden Variablen übergeben
     * 
     * @param value  Menge von Irgendetwas
     * @param unit  Einheit von Irgendetwas
     */
    public Amount(BigDecimal value, UnitOfMeasurement unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Methode zum Abfragen des Werts / Menge
     * 
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Methode zum Setzen des Werts / Menge
     * 
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Methode zum Abfragen des Attributs Einheit
     * 
     * @return the unit
     */
    public UnitOfMeasurement getUnit() {
        return unit;
    }

    /**
     * Methode zum Setzen des Attributs Einheit
     * 
     * @param unit the unit to set
     */
    public void setUnit(UnitOfMeasurement unit) {
        this.unit = unit;
    }

    /**
     * Diese Methode gibt den HashCode-Wert fuer das Objekt zurueck, von dem die Methode aufgerufen 
     * wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
          return 0;
    }
    
    /**
     * Diese Methode prueft, ob das uebergebene Objekt gleich dem Objekt ist, von dem die Methode 
     * aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Amount}.
     * 
     * @return Die String-Representation von {@link Amount}
     */
    @Override
    public String toString() {
        return null;
    }
}