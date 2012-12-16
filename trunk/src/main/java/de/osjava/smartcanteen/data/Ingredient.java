package de.osjava.smartcanteen.data;

/**
 * Die Klasse {@link Ingredient} stellt ein Datenobjekt eines Lebensmittels dar.
 * Sie enthält ein Atrribut für den Namen des Lebensmittels.
 * 
 * @author Francesco Luciano
 */
public class Ingredient {

	private String name;

	/**
	 * Standardkonstruktor
	 * 
	 * @param name
	 *            Name des Lebensmittels
	 */
	public Ingredient(String name) {
		this.name = name;
	}

	/**
	 * Methode um den Namen des Lebensmittels abzufragen
	 * 
	 * @return Name des Lebensmittels
	 */
	public String getName() {
		return name;
	}

	/**
	 * Methode um den Namen des Lebensmittels zu setzen
	 */
	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Ingredient other = (Ingredient) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Erstellt die String-Representation des Objekts {@link Ingredient}.
	 * 
	 * @return Die String-Representation von {@link Ingredient}
	 */
	@Override
	public String toString() {
		return "Ingredient [name=" + name + "]";
	}

}
