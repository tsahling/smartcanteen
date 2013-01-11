package de.osjava.smartcanteen.datatype;

import java.math.BigDecimal;

import de.osjava.smartcanteen.data.item.IngredientListItem;

/**
 * Die Klasse {@link Amount} stellt ein Datenobjekt dar, mit dem Einheiten von irgendetwas beschrieben werden können,
 * die bei ihrer Generierung noch nicht genau definiert sind. So zum Beispiel nutzt {@link IngredientListItem} eine
 * Menge um eine Quantität eines Guts festzulegen. Dabei ist zur Kompilierung des Quellcodes noch nicht definiert in
 * welcher Menge gezählt wird - ob KG, Eier, Liter. Die Menge wird dabei als {@link BigDecimal} gespeichert und die
 * Einheitsbeschreibung als {@link UnitOfMeasurement}. Der Enum {@link UnitOfMeasurement} unterstützt dieses Szenario
 * indem dieser verschiedene Werte anbietet wie z.B. eine Währung für Preise oder eine Mengeneinheit für Rezepte.
 * 
 * @author Marcel Baxmann
 */
public class Amount implements Comparable<Amount> {

    private BigDecimal value;
    private UnitOfMeasurement unit;

    /**
     * Standardkonstruktur
     * 
     * @param value
     *            Menge von irgendetwas
     * @param unit
     *            Einheit von irgendetwas
     */
    public Amount(BigDecimal value, UnitOfMeasurement unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Der Kopier-Konstruktor erzeugt eine tiefe Kopie eines bereits existierenden Objekts
     * 
     * @param amount Das zu kopierende Objekt
     */
    public Amount(Amount amount) {
        this.value = amount.getValue();
        this.unit = amount.getUnit();
    }

    /**
     * Fügt der bestehenden Menge, sofern diese die gleiche Einheit haben, eine weitere Menge hinzu und gibt den
     * aktualisierten Wert zurück.
     * 
     * @param amount
     * @return
     */
    public Amount add(Amount amount) {
        if (this.unit.equals(amount.getUnit())) {
            setValue(addValue(amount.getValue()));
        }
        return this;
    }

    /**
     * Fügt der bestehenden Menge eine weitere Menge hinzu.
     * 
     * @param value
     * @return
     */
    private BigDecimal addValue(BigDecimal value) {
        return this.value.add(value);
    }

    /**
     * Substrahiert von der bestehenden Menge, sofern diese die gleiche Einheit haben, eine weitere Menge und gibt den
     * aktualisierten Wert zurück.
     * 
     * @param amount
     * @return
     */
    public Amount subtract(Amount amount) {
        if (this.unit.equals(amount.getUnit())) {
            setValue(subtractValue(amount.getValue()));
        }
        return this;
    }

    /**
     * Subtrahiert von der bestehenden Menge eine weitere Menge.
     * 
     * @param value
     * @return
     */
    private BigDecimal subtractValue(BigDecimal value) {
        return this.value.subtract(value);
    }

    /**
     * Methode zum Abfragen des Werts / Menge
     * 
     * @return Den Wert
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Methode zum Setzen des Werts / Menge
     * 
     * @param value
     *            Der zu setzende Wert
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Methode zum Abfragen des Attributs Einheit
     * 
     * @return Die Einheit
     */
    public UnitOfMeasurement getUnit() {
        return unit;
    }

    /**
     * Methode zum Setzen des Attributs Einheit
     * 
     * @param unit
     *            Die zu setzende Einheit
     */
    public void setUnit(UnitOfMeasurement unit) {
        this.unit = unit;
    }

    @Override
    public int compareTo(Amount a1) {

        if (this.unit.equals(a1.unit)) {
            return this.value.compareTo(a1.getValue());
        }

        return 0;
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
     * Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /**
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
     * dem die Methode aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Amount other = (Amount) obj;
        if (unit != other.unit)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        }
        else if (!value.equals(other.value))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link Amount}.
     * 
     * @return Die String-Representation von {@link Amount}
     */
    @Override
    public String toString() {
        return "Amount [value=" + value + ", unit=" + unit + "]";
    }
}