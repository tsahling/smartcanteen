import java.math.BigDecimal;

/**
 * Die Klasse {@link Amount] stellt eine Datenobjekt dar, mit dem Einheiten von Irgendetwas beschrieben werden können
 * die bei Ihrer Generierung noch nicht genau definiert sind. So zum Beispiel nutzt {@link IngredientListItem} 
 * Amount um eine Quantität eines Guts festzulegen, dabei ist zur Kompilierung des Quellcodes noch nicht definiert
 * in welcher Menge gezählt wird - ob KG, Eier, Liter. Die Mengeneinheit wird dabei als BigDecimal gespeichert und 
 * die Einheitsbeschreibung als UnitOfMeasurement. Der Enum UnitOfMeasurement unterstützt dieses Szenario indem
 * dieser verschiedene Werte anbietet wie z.B. eine Währung für Preise oder eine Mengeneinheit für Rezepte.
 * 
 * @author Marcel Baxmann
 */
public class Amount {

    private BigDecimal value;
    private UnitOfMeasurement unit;

    /**
     * Den Standardkonstruktur werden Variablen übergeben
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