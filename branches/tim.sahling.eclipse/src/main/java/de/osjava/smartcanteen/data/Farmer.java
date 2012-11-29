package de.osjava.smartcanteen.data;

import de.osjava.smartcanteen.datatype.Amount;

public class Farmer extends AbstractProvider {

	private Amount distanceToCentral;

	public Farmer() {

	}

	@Override
	protected Farmer createProvider() {
		// TODO(Tim Sahling) provide sensible implementation
		return null;
	}

	@Override
	protected Farmer updateProvider() {
		// TODO(Tim Sahling) provide sensible implementation
		return null;
	}

	@Override
	protected void deleteProvider() {
		// TODO(Tim Sahling) provide sensible implementation

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((distanceToCentral == null) ? 0 : distanceToCentral
						.hashCode());
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
		Farmer other = (Farmer) obj;
		if (distanceToCentral == null) {
			if (other.distanceToCentral != null)
				return false;
		} else if (!distanceToCentral.equals(other.distanceToCentral))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Farmer [distanceToCentral=" + distanceToCentral + "]";
	}

}
