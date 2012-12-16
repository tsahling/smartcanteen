package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.datatype.Amount;

/**
 * Die Klasse {@link Wholesaler} ist eine Spezialisierung der Fach- bzw.
 * Datenträgerklasse {@link AbstractProvider} und stellt einen Grosshändler dar.
 * 
 * @author Tim Sahling
 */
public class Wholesaler extends AbstractProvider {

	private Amount transportFee;

	/**
	 * Standardkonstruktor
	 */
	public Wholesaler(String name, Amount transportFee) {
		super(name);
		this.transportFee = transportFee;
	}

	@Override
	protected AbstractProvider createProvider(AbstractProvider provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractProvider updateProvider(AbstractProvider provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteProvider(AbstractProvider provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * Liefert die Versandkostenpauschale des Grosshändlers
	 * 
	 * @return Die Versandkostenpauschale des Grosshändlers
	 */
	public Amount getTransportFee() {
		return transportFee;
	}

	/**
	 * Setzt die Versandkostenpauschale des Grosshändlers.
	 * 
	 * @param transportFee
	 *            Die zu setzende Versandkostenpauschale des Grosshändlers
	 */
	public void setTransportFee(Amount transportFee) {
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
		int result = super.hashCode();
		result = prime * result
				+ ((transportFee == null) ? 0 : transportFee.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wholesaler other = (Wholesaler) obj;
		if (transportFee == null) {
			if (other.transportFee != null)
				return false;
		} else if (!transportFee.equals(other.transportFee))
			return false;
		return true;
	}

	/**
	 * Erstellt die String-Representation des Objekts {@link Wholesaler}.
	 * 
	 * @return Die String-Representation von {@link Wholesaler}
	 */
	@Override
	public String toString() {
		return "Wholesaler [transportFee=" + transportFee + "]";
	}

}