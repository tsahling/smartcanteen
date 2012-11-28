package de.osjava.smartcanteen.datatype;

import java.math.BigDecimal;

public class Amount {

    private BigDecimal value;
    private UnitOfMeasurement unit;

    /**
     * 
     * @param value
     * @param unit
     */
    public Amount(BigDecimal value, UnitOfMeasurement unit)
    {
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
}
