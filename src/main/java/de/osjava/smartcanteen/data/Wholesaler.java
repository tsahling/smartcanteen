package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.datatype.Amount;

/**
 * 
 * @author Tim Sahling
 */
public class Wholesaler extends AbstractProvider {

    private Amount transportFee;

    @Override
    protected Wholesaler createProvider() {
        return null;
    }

    @Override
    protected Wholesaler updateProvider() {
        return null;
    }

    @Override
    protected void deleteProvider() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((transportFee == null) ? 0 : transportFee.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Wholesaler other = (Wholesaler) obj;
        if (transportFee == null) {
            if (other.transportFee != null)
                return false;
        }
        else if (!transportFee.equals(other.transportFee))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Wholesaler [transportFee=" + transportFee + "]";
    }

}
