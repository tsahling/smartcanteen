package de.osjava.smartcanteen.data;

import java.util.Set;

import de.osjava.smartcanteen.data.item.PriceListItem;

public abstract class AbstractProvider {

	private String name;
	private Set<PriceListItem> priceList;

	protected abstract AbstractProvider createProvider();

	protected abstract AbstractProvider updateProvider();

	protected abstract void deleteProvider();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((priceList == null) ? 0 : priceList.hashCode());
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
		AbstractProvider other = (AbstractProvider) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priceList == null) {
			if (other.priceList != null)
				return false;
		} else if (!priceList.equals(other.priceList))
			return false;
		return true;
	}

}
