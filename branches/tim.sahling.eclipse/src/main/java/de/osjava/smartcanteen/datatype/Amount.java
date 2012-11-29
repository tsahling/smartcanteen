package de.osjava.smartcanteen.datatype;

import java.math.BigDecimal;

/**
 * 
 * @author Marcel Baxmann
 */
public class Amount {

    private BigDecimal value;
    private UnitOfMeasurement unit;

    /**
     * 
     * @param value
     * @param unit
     */
    public Amount(BigDecimal value, UnitOfMeasurement unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return the unit
     */
    public UnitOfMeasurement getUnit() {
        return unit;
    }

    /**
     * Sets the unit.
     * 
     * @param unit the unit to set
     */
    public void setUnit(UnitOfMeasurement unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

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

    @Override
    public String toString() {
        return "Amount [value=" + value + ", unit=" + unit + "]";
    }
}
