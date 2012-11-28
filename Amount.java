import java.math.BigDecimal;

/**
 * Write a description of class Amount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Amount
{
    private BigDecimal value;
    private UnitOfMeasurement unit;


    /**
     * 
     */
    public Amount(BigDecimal value, UnitOfMeasurement unit)
    {
        this.value = value;
        this.unit = unit;
    }

    /**
     * 
     * @return the {@link #value} value.
     */
    public BigDecimal getValue() {
        return this.value;
    }

    /**
     * 
     * @param value
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * 
     * @return the {@link #unit} value.
     */
    public UnitOfMeasurement getUnit() {
        return this.unit;
    }

    /**
     * 
     * @param unit
     */
    public void setUnit(UnitOfMeasurement unit) {
        this.unit = unit;
    }
}
